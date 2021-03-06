package com.huishu.ait.controller.user;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huishu.ait.common.conf.ConfConstant;
import com.huishu.ait.common.conf.ImgConstant;
import com.huishu.ait.common.util.ReadExcelUtil;
import com.huishu.ait.controller.BaseController;
import com.huishu.ait.entity.GardenData;
import com.huishu.ait.entity.common.AjaxResult;
import com.huishu.ait.entity.common.EditResult;
import com.huishu.ait.entity.common.ImageSrc;
import com.huishu.ait.es.entity.AITInfo;
import com.huishu.ait.service.data.DataService;
import com.huishu.ait.service.garden.GardenService;

/**
 * 文件上传类
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("apis")
public class UploadController extends BaseController {
	
	@Autowired
	private GardenService gardenService;
	@Autowired
    private DataService dataService;

	private static final Logger LOGGER = Logger.getLogger(UploadController.class);

	private static final HashMap<String, String> TypeMap = new HashMap<String, String>();
	// 设置文件允许上传的类型
	static {
		TypeMap.put("image", "gif,jpg,jpeg,png,bmp");
		TypeMap.put("flash", "swf,flv");
		TypeMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		TypeMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,dwg,pdf");
	}

	// 设置文件上传大小
	public static long fileSize = 3 * 1024 * 1024;

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editUpload.do", method = RequestMethod.POST)
	public EditResult editUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("file name is :" + file.getOriginalFilename());
		EditResult result = new EditResult();
		if (!file.isEmpty()) {
			if (file.getSize() > fileSize) {
				 result.setCode(1);
				 result.setMessage("文件超过上传大小");
				 return result;
			}
			String OriginalFilename = file.getOriginalFilename();
			String fileSuffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.asList(TypeMap.get("image").split(",")).contains(fileSuffix)) {
				result.setCode(1);
				 result.setMessage("文件格式错误");
				 return result;
			}
			if (!ServletFileUpload.isMultipartContent(request)) {
				result.setCode(1);
				 result.setMessage("没有文件上传");
				 return result;
			}
			File uploadDir = new File("images");
			if (!uploadDir.isDirectory()) {
				if (!uploadDir.mkdir()) {
					result.setCode(1);
					 result.setMessage("上传文件路径非法");
					 return result;
				}
			}
			if (!uploadDir.canWrite()) {
				result.setCode(1);
				 result.setMessage("上传目录没有写权限");
				 return result;
			}
			String newname = "";
			newname += UUID.randomUUID() + "." + fileSuffix;
			try {
				String url = request.getSession().getServletContext().getRealPath("/") + ConfConstant.DEFAULT_URL;
				File saveFile = new File(url, newname);
				if(!new File(url).exists())   {
				    new File(url).mkdirs();
				}
				file.transferTo(saveFile);
				result.setCode(0);
				ImageSrc object = new ImageSrc();
				object.setSrc(ImgConstant.IP_PORT+ConfConstant.DEFAULT_URL + "/" + newname);
				object.setTitle(newname);
				result.setData(object);
				return result;
			} catch (Exception e) {
				LOGGER.error("imageUpload失败！", e);
				result.setCode(1);
				 result.setMessage("上传失败");
				 return result;
			}
		} else {
			result.setCode(1);
			 result.setMessage("没有文件上传");
			 return result;
		}
	}
	
	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/imageUpload.do", method = RequestMethod.POST)
	public AjaxResult imageUpload(@RequestParam("file") MultipartFile file,
			@RequestParam(required = false) String id, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("file name is :" + file.getOriginalFilename());
		if (!file.isEmpty()) {
			if (file.getSize() > fileSize) {
				return error("文件超过上传大小");
			}
			String OriginalFilename = file.getOriginalFilename();
			String fileSuffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.asList(TypeMap.get("image").split(",")).contains(fileSuffix)) {
				return error("文件格式错误");
			}
			if (!ServletFileUpload.isMultipartContent(request)) {
				return error("没有文件上传");
			}
			File uploadDir = new File("images");
			if (!uploadDir.isDirectory()) {
				if (!uploadDir.mkdir()) {
					return error("上传文件路径非法");
				}
			}
			if (!uploadDir.canWrite()) {
				return error("上传目录没有写权限");
			}
			String newname = "";
			newname += UUID.randomUUID() + "." + fileSuffix;
			try {
				String url = request.getSession().getServletContext().getRealPath("/") + ConfConstant.DEFAULT_URL;
				File saveFile = new File(url, newname);
				if(!new File(url).exists())   {
				    new File(url).mkdirs();
				}
				file.transferTo(saveFile);
				GardenData garden = gardenService.findGarden(Integer.valueOf(id));
				garden.setGardenPicture(ImgConstant.IP_PORT+ConfConstant.DEFAULT_URL + "/" + newname);
				gardenService.changeGarden(garden);
				return success(ImgConstant.IP_PORT+ConfConstant.DEFAULT_URL + "/" + newname).setMessage("上传成功");
			} catch (Exception e) {
				LOGGER.error("imageUpload失败！", e);
				return error("上传失败");
			}
		} else {
			return error("没有文件上传");
		}
	}
	
	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logoUpload.do", method = RequestMethod.POST)
	public AjaxResult logoUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("file name is :" + file.getOriginalFilename());
		if (!file.isEmpty()) {
			if (file.getSize() > fileSize) {
				return error("文件超过上传大小");
			}
			String OriginalFilename = file.getOriginalFilename();
			String fileSuffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.asList(TypeMap.get("image").split(",")).contains(fileSuffix)) {
				return error("文件格式错误");
			}
			if (!ServletFileUpload.isMultipartContent(request)) {
				return error("没有文件上传");
			}
			File uploadDir = new File("images");
			if (!uploadDir.isDirectory()) {
				if (!uploadDir.mkdir()) {
					return error("上传文件路径非法");
				}
			}
			if (!uploadDir.canWrite()) {
				return error("上传目录没有写权限");
			}
			String newname = "";
			newname += UUID.randomUUID() + "." + fileSuffix;
			try {
				String url = request.getSession().getServletContext().getRealPath("/") + ConfConstant.DEFAULT_LOGOURL;
				File saveFile = new File(url, newname);
				if(!new File(url).exists())   {
				    new File(url).mkdirs();
				}
				file.transferTo(saveFile);
				return success(ImgConstant.IP_PORT+ConfConstant.DEFAULT_LOGOURL + "/" + newname).setMessage("上传成功");
			} catch (Exception e) {
				LOGGER.error("imageUpload失败！", e);
				return error("上传失败");
			}
		} else {
			return error("没有文件上传");
		}
	}
	
	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/dataUpload.do", method = RequestMethod.POST)
	public AjaxResult dataUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("file name is :" + file.getOriginalFilename());
		if (!file.isEmpty()) {
			if (file.getSize() > fileSize) {
				return error("文件超过上传大小");
			}
			String OriginalFilename = file.getOriginalFilename();
			String fileSuffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1).toLowerCase();
			if (!ServletFileUpload.isMultipartContent(request)) {
				return error("没有文件上传");
			}
			File uploadDir = new File("images");
			if (!uploadDir.isDirectory()) {
				if (!uploadDir.mkdir()) {
					return error("上传文件路径非法");
				}
			}
			if (!uploadDir.canWrite()) {
				return error("上传目录没有写权限");
			}
			String newname = UUID.randomUUID() + "." + fileSuffix;
			try {
				String url = "e:/excel";
				File saveFile = new File(url, newname);
				if(!new File(url).exists())   {
				    new File(url).mkdirs();
				}
				file.transferTo(saveFile);
				new Thread(new Runnable(){
					@Override
					public void run() {
						try {
							ReadExcelUtil util = new ReadExcelUtil();
							List<String> map = util.readExcel("e:/excel/" + newname, newname);
							List<String> listPage = map.subList(0, 1);
							if(dataService.checkString(listPage.get(0))){
								map.subList(0, 1).clear();
								for (String value : map) {
									AITInfo info = dataService.transformData(value);
									dataService.addData(info);
								}
								dataService.printLog(OriginalFilename, "数据存库完成");
							}else{
								LOGGER.error(OriginalFilename+"表格格式错误！");
								dataService.printLog(OriginalFilename, "表格格式错误！");
							}
						} catch (Exception e) {
							LOGGER.error("存储数据失败！", e);
							dataService.printLog(OriginalFilename, e.toString());
						}
					}
				}).start();  
				return success(null).setMessage("上传成功");
			} catch (Exception e) {
				LOGGER.error("dataUpload失败！", e);
				return error("上传失败");
			}
		} else {
			return error("没有文件上传");
		}
	}
}


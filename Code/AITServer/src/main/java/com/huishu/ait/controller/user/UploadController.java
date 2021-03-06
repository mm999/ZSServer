package com.huishu.ait.controller.user;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huishu.ait.common.conf.ConfConstant;
import com.huishu.ait.controller.BaseController;
import com.huishu.ait.entity.common.AjaxResult;

/**
 * 文件上传类
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("apis")
public class UploadController extends BaseController {

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
	 * @param filePre
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/imageUpload.do", method = RequestMethod.POST)
	public AjaxResult imageUpload(@RequestParam("file") MultipartFile file,
			@RequestParam(required = false) String filePre, HttpServletRequest request, HttpServletResponse response) {
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
			if (null != filePre) {
				newname += filePre;// 对应模块上传的文件名前缀
			}
			newname += UUID.randomUUID() + "." + fileSuffix;
			try {
				String url = request.getSession().getServletContext().getRealPath("/") + ConfConstant.DEFAULT_URL;
				File saveFile = new File(url, newname);
				file.transferTo(saveFile);
				return success(ConfConstant.DEFAULT_URL + "/" + newname).setMessage("上传成功");
			} catch (Exception e) {
				LOGGER.error("imageUpload失败！", e);
				return error("上传失败");
			}
		} else {
			return error("没有文件上传");
		}
	}
}
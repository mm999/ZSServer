package com.huishu.ZSServer.controller.report;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huishu.ZSServer.common.AjaxResult;
import com.huishu.ZSServer.common.conf.MsgConstant;
import com.huishu.ZSServer.common.util.StringUtil;
import com.huishu.ZSServer.controller.BaseController;
import com.huishu.ZSServer.entity.FilePdf;
import com.huishu.ZSServer.entity.dto.ReportSearchDTO;
import com.huishu.ZSServer.service.report.ReportService;

/**
 * 招商报告
 * 
 * @author yindq
 * @date 2017年11月10日
 */
@Controller
@RequestMapping("/apis/report")
public class ReportController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;
	
	/**
	 * 直接跳转页面
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String show(@PathVariable String page) {
		return "/report/"+page;
	}
	
	/**
	 * 获取报告筛选项
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getScreeningItem.json", method = RequestMethod.GET)
	public AjaxResult getScreeningItem() {
		try {
			return success(reportService.getScreeningItem());
		} catch (Exception e) {
			LOGGER.error("获取报告筛选项失败：", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 获取招商报告PDF列表
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getExpertReport.json", method = RequestMethod.POST)
	public AjaxResult getExpertReport(@RequestBody ReportSearchDTO dto) {
		if(dto==null||StringUtil.isEmpty(dto.getType())||StringUtil.isEmpty(dto.getYear())){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			Page<FilePdf> page = reportService.getExpertReport(dto);
			return successPage(page,dto.getPageNumber()+1);
		} catch (Exception e) {
			LOGGER.error("查询失败：", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 获取用户下载的报告筛选项
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUserScreeningItem.json", method = RequestMethod.GET)
	public AjaxResult getUserScreeningItem() {
		try {
			return success(reportService.getUserScreeningItem(getUserId()));
		} catch (Exception e) {
			LOGGER.error("获取报告筛选项失败：", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 获取用户下载PDF列表
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUserExpertReport.json", method = RequestMethod.POST)
	public AjaxResult getUserExpertReport(@RequestBody ReportSearchDTO dto) {
		if(dto==null||StringUtil.isEmpty(dto.getType())||StringUtil.isEmpty(dto.getYear())){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			Page<FilePdf> page = reportService.getUserExpertReport(getUserId(),dto);
			return successPage(page,dto.getPageNumber()+1);
		} catch (Exception e) {
			LOGGER.error("查询失败：", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}
}
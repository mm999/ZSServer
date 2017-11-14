package com.huishu.ZSServer.controller.report;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.huishu.ZSServer.common.AjaxResult;
import com.huishu.ZSServer.common.conf.MsgConstant;
import com.huishu.ZSServer.controller.BaseController;
import com.huishu.ZSServer.entity.FilePdf;
import com.huishu.ZSServer.service.report.ReportService;

/**
 * 招商报告
 * 
 * @author yindq
 * @date 2017年11月10日
 */
@RestController
@RequestMapping("/apis/report")
public class ReportController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;
	
	/**
	 * 获取招商报告PDF列表
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "getExpertReport.json", method = RequestMethod.GET)
	public AjaxResult getExpertReport(Integer pageNum) {
		if(pageNum!=null&&pageNum<0){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			if(pageNum==null) pageNum=0;
			PageRequest pageRequest = new PageRequest(pageNum, 8,new Sort(Direction.DESC, "createTime"));
			Page<FilePdf> page = reportService.getExpertReport(pageRequest);
			return this.success(page);
		} catch (Exception e) {
			LOGGER.error("查询失败：", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}
}
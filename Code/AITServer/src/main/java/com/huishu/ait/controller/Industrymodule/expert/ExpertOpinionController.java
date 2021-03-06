package com.huishu.ait.controller.Industrymodule.expert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.common.conf.MsgConstant;
import com.huishu.ait.common.util.ConcersUtils;
import com.huishu.ait.common.util.StringUtil;
import com.huishu.ait.controller.BaseController;
import com.huishu.ait.entity.FilePdf;
import com.huishu.ait.entity.Specialist;
import com.huishu.ait.entity.common.AjaxResult;
import com.huishu.ait.es.entity.ExpertOpinionDTO;
import com.huishu.ait.service.ExpertOpinion.ExpertOpinionService;
import com.huishu.ait.service.Specialist.SpecialistService;

/**
 * @author yxq 查询专家观点
 */
@RestController
@RequestMapping(value = "/apis/expert")
public class ExpertOpinionController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ExpertOpinionController.class);

	@Autowired
	private ExpertOpinionService expertOpinionService;
	@Autowired
	private SpecialistService specialistService;

	/**
	 * 获取专家观点PDF列表
	 * @return
	 */
	@RequestMapping(value = "getExpertReport.json", method = RequestMethod.GET)
	public AjaxResult getExpertReport() {
		try {
			Page<FilePdf> page = expertOpinionService.getExpertReport();
			return this.success(page);
		} catch (Exception e) {
			log.error("查询失败：", e.getMessage());
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}
	
	/**
	 * @param dto
	 *            初始化分页的方法
	 */
	private ExpertOpinionDTO initPage(ExpertOpinionDTO dto) {
		if (dto.getPageNumber() == null) {
			dto.setPageNumber(ConcersUtils.ES_MIN_PAGENUMBER);
		}
		if (dto.getPageSize() == null) {
			dto.setPageSize(ConcersUtils.PAGE_SIZE);
		}
		if (dto.getPageNumber() > ConcersUtils.ES_MAX_PAGENUMBER) {
			dto.setPageNumber(ConcersUtils.ES_MAX_PAGENUMBER);
		}
		return dto;
	}

	/**
	 * @return 获取专家信息列表
	 */
	@RequestMapping(value = "getSpecialist.json", method = RequestMethod.POST)
	public AjaxResult getSpecialist(@RequestBody ExpertOpinionDTO dto) {
		try {
			Page<Specialist> findAll = specialistService.findAllOrderById(dto);
			return this.success(findAll);
		} catch (Exception e) {
			log.error("查询失败：", e.getMessage());
			return error(MsgConstant.ILLEGAL_PARAM);
		}
	}

	/**
	 * @param requestParam
	 * @return 查询百家论信息
	 */
	@RequestMapping(value = "findaExpertOpinion.json", method = RequestMethod.POST)
	public AjaxResult getExpertOpinion(@RequestBody ExpertOpinionDTO requestParam) {
		try {
			requestParam = initPage(requestParam);
			String[] msg = requestParam.getMsg();
			if (null != msg && msg.length > 0) {
				if (null != requestParam.getMsg()[0]) {
					requestParam.setIndustry(requestParam.getMsg()[0]);
				}
				if (null != requestParam.getMsg()[1]) {
					requestParam.setIndustryLabel(requestParam.getMsg()[1]);
				}
				/*
				 * if (null != requestParam.getMsg()[2]) {
				 * requestParam.setTimeFlag(requestParam.getMsg()[2]); }
				 */
			}
			JSONArray jsonArray = expertOpinionService.getExertOpinionList(requestParam);
			return this.success(jsonArray);
		} catch (Exception e) {
			log.error("查询失败：", e.getMessage());
			return error(MsgConstant.ILLEGAL_PARAM);
		}
	}

	/**
	 * @param requestParam
	 * @return 根据作者名称查询专家论列表
	 */
	@RequestMapping(value = "findExpertOpinionByAuthor.json", method = RequestMethod.POST)
	public AjaxResult getExpertOpinionByAuthor(@RequestBody ExpertOpinionDTO requestParam) {
		try {
			requestParam = initPage(requestParam);
			JSONArray jsonArray = expertOpinionService.findExpertOpinionByAuthor(requestParam);
			return this.success(jsonArray);
		} catch (Exception e) {
			log.error("查询失败：", e.getMessage());
			return error(MsgConstant.ILLEGAL_PARAM);
		}
	}

	/**
	 * @param requestParam
	 * @return 收藏专家观点
	 */
	@RequestMapping(value = "collectExpertOpinion.json", method = RequestMethod.GET)
	public AjaxResult collectExpertOpinion(String id) {
		try {
			if (StringUtil.isEmpty(id) || getUserId() == null) {
				return error(MsgConstant.ILLEGAL_PARAM);
			}
			JSONObject json = expertOpinionService.expertOpinionCollect(id, getUserId());
			return success(json);
		} catch (Exception e) {
			log.error("收藏失败：", e.getMessage());
			return null;
		}
	}

	/**
	 * @param requestParam
	 * @return 取消收藏专家观点
	 */
	@RequestMapping(value = "cancelCollectExpertOpinion.json", method = RequestMethod.GET)
	public AjaxResult cancelCollectExpertOpinion(String id) {
		try {
			if (StringUtil.isEmpty(id) || getUserId() == null) {
				return error(MsgConstant.ILLEGAL_PARAM);
			}
			JSONObject json = expertOpinionService.cancelExpertOpinionCollect(id, getUserId());
			return success(json);
		} catch (Exception e) {
			log.error("取消收藏失败：", e.getMessage());
			return null;
		}
	}

}

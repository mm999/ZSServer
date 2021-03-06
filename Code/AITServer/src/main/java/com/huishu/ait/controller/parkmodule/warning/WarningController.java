package com.huishu.ait.controller.parkmodule.warning;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.common.conf.MsgConstant;
import com.huishu.ait.common.util.StringUtil;
import com.huishu.ait.controller.BaseController;
import com.huishu.ait.entity.ChangeInfo;
import com.huishu.ait.entity.common.AjaxResult;
import com.huishu.ait.entity.dto.AreaSearchDTO;
import com.huishu.ait.entity.dto.InformationSearchDTO;
import com.huishu.ait.es.entity.AITInfo;
//gitlab.junquan.com.cn/hskj/pd_210_merchantssys.git
import com.huishu.ait.service.warning.WarningService;

/**
 * 园区预警
 * 
 * @author yindq
 * @date 2017年8月3日
 */
@Controller
@RequestMapping(value = "/apis/warning")
public class WarningController extends BaseController {
	private static Logger LOGGER = LoggerFactory.getLogger(WarningController.class);

	@Autowired
	private WarningService warningService;

	/**
	 * 企业疑似外流列表
	 * 
	 * @param searchModel
	 *            查询条件
	 * @return
	 */
	@RequestMapping(value = "getBusinessOutflowList.json", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult getBusinessOutflowList(@RequestBody AreaSearchDTO searchModel) {
		if (StringUtil.isEmpty(getUserPark())) {
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		searchModel.setPark(getUserPark());
		if (null == searchModel || null == searchModel.getPark()) {
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			Page<AITInfo> page = warningService.getBusinessOutflowList(searchModel);
			return success(page);
		} catch (Exception e) {
			LOGGER.error("getBusinessOutflowList查询失败！", e);
			return error(MsgConstant.ILLEGAL_PARAM);
		}
	}

	/**
	 * 信息变更预警列表
	 * 
	 * @param searchModel
	 *            查询条件
	 * @return
	 */
	@RequestMapping(value = "getInformationChangeList.json", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult getInformationChangeList(@RequestBody InformationSearchDTO searchModel) {
		searchModel.setPark(getUserPark());
		if (null == searchModel || null == searchModel.getPark()) {
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			Page<ChangeInfo> findAll = warningService.getInformationChangeList(searchModel);
			return success(findAll);
		} catch (Exception e) {
			LOGGER.error("getBusinessOutflowList查询失败！", e);
			return error(MsgConstant.ILLEGAL_PARAM);
		}
	}

	/**
	 * 信息变更预警详情
	 * 
	 * @param id
	 *            政策ID
	 * @return
	 */
	@RequestMapping(value = "getInformationChangeById.json", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult getInformationChangeById(String id) {
		if (null == id) {
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			return success(warningService.getInformationChangeById(id));
		} catch (Exception e) {
			LOGGER.error("getBusinessOutflowById查询失败！", e);
			return error(MsgConstant.ILLEGAL_PARAM);
		}
	}

	/**
	 * 获取辖区预警数量
	 */
	@RequestMapping(value = "getGardenWarningCout.json", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult getGardenWarningCout() {
		AreaSearchDTO searchModel = new AreaSearchDTO();
		searchModel.setPark(getUserPark());
		JSONObject obj = new JSONObject();
		List<ChangeInfo> changeList = warningService.getChangeInfo(getUserPark());
		List<AITInfo> exList = warningService.getExternalFlow(getUserPark(), "true");
		// obj.put("count", page.getTotalElements());
		int count = changeList.size() + exList.size();
		obj.put("count", count);
		return success(obj);
	}

	/**
	 * 删除预警信息
	 */
	@RequestMapping(value = "deleteWarning.json", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult deleteWarning(String id) {
		boolean flag = warningService.deleteWarning(id);
		return success(flag);
	}
}

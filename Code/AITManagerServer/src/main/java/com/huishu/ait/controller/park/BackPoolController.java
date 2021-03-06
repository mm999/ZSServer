package com.huishu.ait.controller.park;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.common.conf.MsgConstant;
import com.huishu.ait.controller.BaseController;
import com.huishu.ait.entity.PoolCompany;
import com.huishu.ait.entity.common.AjaxResult;
import com.huishu.ait.entity.dto.CompanySearchDTO;
import com.huishu.ait.service.user.DemandPoolService;

/**
 * 后台管理-需求池
 *
 * @author yindq
 * @date 2017/9/21
 */
@RestController
@RequestMapping(value = "/apis/back/pool")
public class BackPoolController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(BackPoolController.class);
    @Autowired
    private DemandPoolService demandPoolService;

    /**
     * 查看需求池中公司列表
     * @param searchModel
     * @return
     */
    @RequestMapping(value = "getCompanyList.json", method = RequestMethod.POST)
    public AjaxResult getCompanyList(@RequestBody CompanySearchDTO searchModel) {
        if (null==searchModel || null==searchModel.getStatus()) {
            return error(MsgConstant.ILLEGAL_PARAM);
        }
        try {
        	List<PoolCompany> list = demandPoolService.getCompanyList(searchModel);
            return success(list);
        } catch (Exception e) {
            LOGGER.error("getCompanyList查询失败！",e);
            return error(MsgConstant.SYSTEM_ERROR);
        }
    }
    @RequestMapping(value="getKindCount.json")
    public AjaxResult getKindCount(String park){
    	JSONObject obj = new JSONObject();
    	List<Object[]> kindCount = demandPoolService.findKindCount(park);
    	Integer totalCount = 0;
    	for (Object[] arr : kindCount) {
    		BigInteger v = (BigInteger) arr[1];
    		totalCount += v.intValue();
		}
    	obj.put("kindCount", kindCount);
    	obj.put("totalCount", totalCount);
    	return success(obj);
    }
}

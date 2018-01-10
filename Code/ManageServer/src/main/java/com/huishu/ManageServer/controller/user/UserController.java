package com.huishu.ManageServer.controller.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huishu.ManageServer.common.AjaxResult;
import com.huishu.ManageServer.common.conf.MsgConstant;
import com.huishu.ManageServer.common.util.StringUtil;
import com.huishu.ManageServer.controller.BaseController;
import com.huishu.ManageServer.entity.dbFirst.UserBase;
import com.huishu.ManageServer.entity.dto.AbstractDTO;
import com.huishu.ManageServer.entity.dto.UserBaseDTO;
import com.huishu.ManageServer.service.user.UserService;

/**
 * 用户管理模块
 *
 * @author yindq
 * @date 2018/1/4
 */
@Controller
@RequestMapping("/apis/user")
public class UserController extends BaseController{
	@Autowired
	private UserService userService;

	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	/**
	 * 页面跳转
	 * @param page
	 * @return
	 */
	@RequestMapping(value = { "{page}" }, method = RequestMethod.GET)
	public String pageJump(@PathVariable String page,String id,Model model) {
		if("editUserBase".equals(page)){
			model.addAttribute("id",id);
		}
		return "/user/" + page;
	}

	/**
	 * 分页查看用户列表
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "listUserBase.json", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult listUserBase(@RequestBody AbstractDTO dto) {
		try {
			Page<UserBase> page = userService.listUserBase(dto);
			return successPage(page,dto.getPageNum()+1);
		}catch (Exception e){
			LOGGER.error("分页查看用户列表失败!", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}

	/**
	 * 添加/修改用户
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "saveUserBase.json", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult saveUserBase(@RequestBody UserBaseDTO dto) {
		if(dto==null||StringUtil.isEmpty(dto.getTelphone())||StringUtil.isEmpty(dto.getUserPark())||StringUtil.isEmpty(dto.getUserType())
				||StringUtil.isEmpty(dto.getUserEmail())||StringUtil.isEmpty(dto.getUserTime())){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			Boolean flag = userService.saveUserBase(dto);
			if (flag) {
				return success(MsgConstant.OPERATION_SUCCESS);
			} else {
				return error(MsgConstant.OPERATION_ERROR);
			}
		}catch (Exception e){
			LOGGER.error("添加/修改用户失败!", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 查看用户详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getUserBase.json", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult getUserBase(String id) {
		if(id==null|| StringUtil.isEmpty(id)){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		Long aLong;
		try{
			aLong = Long.valueOf(id);
		}catch (Exception e){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			UserBase base = userService.findById(aLong);
			return success(base);
		}catch (Exception e){
			LOGGER.error("分页查看用户列表失败!", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "dropUserBase.json", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult dropUserBase(String id) {
		if(id==null|| StringUtil.isEmpty(id)){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		Long aLong;
		try{
			aLong = Long.valueOf(id);
		}catch (Exception e){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		try {
			Boolean flag = userService.dropUserBase(aLong);
			if (flag) {
				return success(MsgConstant.OPERATION_SUCCESS);
			} else {
				return error(MsgConstant.OPERATION_ERROR);
			}
		}catch (Exception e){
			LOGGER.error("删除用户失败!", e);
			return error(MsgConstant.SYSTEM_ERROR);
		}
	}
}
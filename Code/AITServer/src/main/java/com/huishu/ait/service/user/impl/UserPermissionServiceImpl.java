package com.huishu.ait.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huishu.ait.entity.UserBase;
import com.huishu.ait.repository.user.UserBaseRepository;
import com.huishu.ait.repository.user.UserPermissionRepository;
import com.huishu.ait.service.user.UserPermissionService;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	private UserPermissionRepository userPermissionRepository;
	@Autowired
	private UserBaseRepository userBaseRepository;

	@Override
	public List<Long> getPermissionIdsByUserId(Long id) {
		UserBase base = userBaseRepository.findOne(id);
		return userPermissionRepository.findPermissionIdByUserLevel(base.getUserLevel());
	}

}

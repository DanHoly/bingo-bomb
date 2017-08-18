/*
 * Copyright (C) 2013 Shanghai bingo soft Co., Ltd
 *
 * All copyrights reserved by bingo.com
 * Any copying, transferring or any other usage is prohibited.
 * Or else, Shanghai bingo possesses the right to require legal 
 * responsibilities from the violator.
 * All third-party contributions are distributed under license by
 * bingo soft Co., Ltd.
 */
package org.bingo.bomb.chaos.rpc.service.user;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bingo.bomb.chaos.repository.domain.system.RoleEntity;
import org.bingo.bomb.chaos.repository.domain.system.UserEntity;
import org.bingo.bomb.chaos.rpc.api.system.IUserRpcService;
import org.bingo.bomb.chaos.rpc.api.vo.system.UserRpcVo;
import org.bingo.bomb.chaos.service.system.IRoleService;
import org.bingo.bomb.chaos.service.system.IUserService;
import org.bingo.bomb.commons.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:46:55
 * @since JDK 1.7	
 */
@Service
public class UserRpcServiceImpl implements IUserRpcService {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Override
	public UserRpcVo findById(BigInteger id) {
		UserEntity entity = userService.findById(id);
		if(entity != null){
			UserRpcVo userRpcVo = new UserRpcVo();
			Utils.copyObject(entity, userRpcVo);
			return userRpcVo;
		}
		return null;
	}

	@Override
	public UserRpcVo findByUserName(String userName) {
		UserEntity entity = new UserEntity();
		entity.setUserName(userName);
		entity = userService.findByCondition(entity);
		UserRpcVo userRpcVo = new UserRpcVo();
		Utils.copyObject(entity, userRpcVo);
		return userRpcVo;
	}

	@Override
	public Set<String> findRoles(String userName) {
		UserRpcVo vo = findByUserName(userName);
		Set<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(vo.getRoleIds().split(",")));
		return set;
	}

	@Override
	public Set<String> findPermissions(String userName) {
		UserRpcVo vo = findByUserName(userName);
		Set<String> set = new HashSet<String>();
		for(String roleId : vo.getRoleIds().split(",")){
			RoleEntity role  = roleService.findById(new BigInteger(roleId));
			set.addAll(Arrays.asList(role.getResourceIds().split(",")));
		}
		return set;
	}

}

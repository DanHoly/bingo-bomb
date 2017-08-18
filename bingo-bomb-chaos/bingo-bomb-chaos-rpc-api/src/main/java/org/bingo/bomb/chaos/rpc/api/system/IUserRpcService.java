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
package org.bingo.bomb.chaos.rpc.api.system;

import java.math.BigInteger;
import java.util.Set;

import org.bingo.bomb.chaos.rpc.api.vo.system.UserRpcVo;

/**
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:17:18
 * @since JDK 1.7	
 */
public interface IUserRpcService {
	
	/**
	 * 根据ID查找用户
	 * @param id
	 * @return
	 */
	public UserRpcVo findById(BigInteger id);
	
	/**
	 * 根据用户名查询用户
	 * @param userName
	 * @return
	 */
	public UserRpcVo findByUserName(String userName);
	
	/**
	 * 根据用户名查询角色 
	 * @param userName
	 * @return
	 */
	public Set<String> findRoles(String userName);
	
	/**
	 * 根据用户名权限 
	 * @param userName
	 * @return
	 */
	public Set<String> findPermissions(String userName);
	
}

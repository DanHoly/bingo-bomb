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
package org.bingo.bomb.chaos.rpc.api.user;

import java.math.BigInteger;

import org.bingo.bomb.chaos.rpc.api.vo.user.UserRpcVo;

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
}

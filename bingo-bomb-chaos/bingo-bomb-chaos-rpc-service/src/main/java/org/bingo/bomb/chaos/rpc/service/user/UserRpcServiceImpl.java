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

import org.bingo.bomb.chaos.repository.domain.user.UserEntity;
import org.bingo.bomb.chaos.rpc.api.user.IUserRpcService;
import org.bingo.bomb.chaos.rpc.api.vo.user.UserRpcVo;
import org.bingo.bomb.chaos.service.user.IUserService;
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

}

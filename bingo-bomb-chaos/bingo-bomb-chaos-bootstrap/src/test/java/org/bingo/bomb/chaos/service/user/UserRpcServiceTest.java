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
package org.bingo.bomb.chaos.service.user;

import java.math.BigInteger;

import org.bingo.bomb.chaos.bootstrap.ConfigMainTest;
import org.bingo.bomb.chaos.rpc.api.system.IUserRpcService;
import org.bingo.bomb.chaos.rpc.api.vo.system.UserRpcVo;
import org.bingo.bomb.commons.utils.Utils;
import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午3:18:05
 * @since JDK 1.7	
 */
public class UserRpcServiceTest extends ConfigMainTest {
	
	@Reference
	private IUserRpcService userRpcService;
	
	@Test
	public void testFindById(){
		UserRpcVo userRpcVo = userRpcService.findById(new BigInteger("1"));
		if(userRpcVo != null){
			logger.info("userRpcVo:" + Utils.json(userRpcVo));
		}
	}
}

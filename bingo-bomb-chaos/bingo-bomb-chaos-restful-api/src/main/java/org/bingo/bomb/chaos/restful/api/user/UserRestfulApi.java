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
package org.bingo.bomb.chaos.restful.api.user;

import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.bingo.bomb.chaos.rpc.api.user.IUserRpcService;
import org.bingo.bomb.chaos.rpc.api.vo.user.UserRpcVo;
import org.bingo.bomb.commons.utils.ObjectId;
import org.bingo.bomb.commons.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午5:32:18
 * @since JDK 1.7	
 */
@Controller
@RequestMapping("/userRest")
public class UserRestfulApi {
	
	private static final Logger logger = Logger.getLogger(ObjectId.class);
	
	@Reference
	private IUserRpcService userRpcService;
	
	@RequestMapping("/findByUserId/{userId}")
	@ResponseBody
	public String findUserById(@PathVariable("userId") BigInteger userId){
		
		UserRpcVo userRpcVo = userRpcService.findById(userId);
		
		logger.debug("test logger error");
		
		return userRpcVo == null ? "user is not exist" : Utils.json(userRpcVo);
	} 
	
}

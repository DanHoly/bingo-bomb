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

import java.util.List;
import java.util.Set;

import org.bingo.bomb.chaos.rpc.api.system.IResourceRpcService;
import org.bingo.bomb.chaos.rpc.api.vo.system.ResourceRpcVo;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 资源服务实现
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:46:55
 * @since JDK 1.7	
 */
@Service
public class ResourceRpcServiceImpl implements IResourceRpcService{

	@Override
	public List<ResourceRpcVo> findMenus(Set<String> permissions) {
		return null;
	}
	

}

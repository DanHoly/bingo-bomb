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

import java.util.List;
import java.util.Set;

import org.bingo.bomb.chaos.rpc.api.vo.system.ResourceRpcVo;

/**
 * 资源RPC服务
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:17:18
 * @since JDK 1.7	
 */
public interface IResourceRpcService {
	
	/**
	 * 根据配置查询菜单
	 * @param permissions
	 * @return
	 */
	public List<ResourceRpcVo> findMenus(Set<String> permissions);
	
}

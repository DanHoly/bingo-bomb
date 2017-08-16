package org.bingo.bomb.chaos.service;

import java.io.Serializable;

/**
 * 服务结果
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:07:47
 * @since JDK 1.7
 */
public class ServiceResult<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum ServiceType {
		SAVE, UPDATE;
	}

	/**
	 * 保存时得到id
	 */
	private ID id;

	/**
	 * 更新时得到更新数
	 */
	private Long count;

	/**
	 * 更新还是保存
	 */
	private ServiceType serviceType;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

}

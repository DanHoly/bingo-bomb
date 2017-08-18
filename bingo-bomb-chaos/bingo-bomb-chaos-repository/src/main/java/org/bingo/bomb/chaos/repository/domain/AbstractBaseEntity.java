package org.bingo.bomb.chaos.repository.domain;


import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.bingo.bomb.commons.utils.DigitConstant;
import org.bingo.bomb.commons.utils.Utils;

/**
 * Entity基类
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午1:43:38
 * @since JDK 1.7
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	@org.springframework.data.annotation.Id
	private BigInteger id;

	/**
	 * 0已删除 1有效
	 */
	@Column(name = "active")
	private short active = DigitConstant.SHORT_ONE;

	/**
	 * 数据版本
	 */
	@Column(name = "version")
	private Long version;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 创建人Id
	 */
	@Column(name = "create_user_id")
	private BigInteger createUserId;

	/**
	 * 更新时间
	 */
	@Column(name = "modify_time")
	private Date modifyTime;

	/**
	 * 更新人Id
	 */
	@Column(name = "modify_user_id")
	private BigInteger modifyUserId;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public short getActive() {
		return active;
	}

	public void setActive(short active) {
		this.active = active;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigInteger getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(BigInteger createUserId) {
		this.createUserId = createUserId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public BigInteger getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(BigInteger modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Override
	public String toString() {
		return Utils.json(this);
	}
}

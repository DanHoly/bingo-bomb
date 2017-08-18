package org.bingo.bomb.chaos.rpc.api.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 用户
 * 
 * @author xpp
 * @date 2017年3月23日 下午4:12:54
 */
public class BaseRpcVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private BigInteger id;

	/**
	 * 0已删除 1有效
	 */
	private int active = 1;

	/**
	 * 数据版本
	 */
	private Long version;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人Id
	 */
	private BigInteger createUserId;

	/**
	 * 更新时间
	 */
	private Date modifyTime;

	private BigInteger modifyUserId;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
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
		return JSON.toJSONString(this);
	}

}

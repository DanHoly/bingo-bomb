package org.bingo.bomb.chaos.rpc.api.vo.system;

import org.bingo.bomb.chaos.rpc.api.vo.BaseRpcVo;

/**
 * 用户
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午1:58:15
 * @since JDK 1.7
 */
public class UserRpcVo extends BaseRpcVo{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 用户类型
	 */
	private Short type;
	
	/**
	 * 角色ID,多个角色逗号分隔
	 */
	private String roleIds;



	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
	public String getRoleIds() {
		return roleIds;
	}
	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

}

package org.bingo.bomb.chaos.rpc.api.vo.user;

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
	private String username;

	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 手机设备识别码
	 */
	private String imei;

	/**
	 * 邮箱
	 */
	private String email;


	/**
	 * 密码
	 */
	private String password;

	/**
	 * 用户类型
	 */
	private Short type;

	/**
	 * 用户状态(0.新建,1.正常,2.冻结)
	 */
	private Short status;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	
}

package org.bingo.bomb.chaos.repository.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.bingo.bomb.chaos.repository.domain.AbstractBaseEntity;

/**
 * 用户
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午1:58:15
 * @since JDK 1.7
 */
@Entity
@Table(name = "oj_user")
public class UserEntity extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@Column(name = "username")
	private String username;

	/**
	 * 盐
	 */
	@Column(name = "salt")
	private String salt;
	/**
	 * 手机号
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 手机设备识别码
	 */
	@Column(name = "imei")
	private String imei;

	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;


	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 用户状态(0.新建,1.正常,2.冻结)
	 */
	@Column(name = "status")
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
}

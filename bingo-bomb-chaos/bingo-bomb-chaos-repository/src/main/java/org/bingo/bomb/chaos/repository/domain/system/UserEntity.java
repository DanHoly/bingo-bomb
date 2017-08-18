package org.bingo.bomb.chaos.repository.domain.system;

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
@Table(name = "sys_user")
public class UserEntity extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 手机号
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 用户名
	 */
	@Column(name = "user_name")
	private String userName;
	
	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;
	
	/**
	 * 密码盐
	 */
	@Column(name = "salt")
	private String salt;
	
	/**
	 * 角色ID,多个角色逗号分隔
	 */
	@Column(name = "role_ids")
	private String roleIds;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	
	
}

package org.bingo.bomb.chaos.repository.domain.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.bingo.bomb.chaos.repository.domain.AbstractBaseEntity;

/**
 * 角色
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午1:58:15
 * @since JDK 1.7
 */
@Entity
@Table(name = "sys_role")
public class RoleEntity extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色名称
	 */
	@Column(name = "role_name")
	private String roleName;
	
	/**
	 * 描述
	 */
	@Column(name = "description")
	private String description;
	
	/**
	 * 资源ID多个逗号分隔
	 */
	@Column(name = "resource_ids")
	private String resourceIds;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

}

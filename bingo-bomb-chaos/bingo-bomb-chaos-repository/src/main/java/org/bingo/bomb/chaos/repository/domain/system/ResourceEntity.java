package org.bingo.bomb.chaos.repository.domain.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.bingo.bomb.chaos.repository.domain.AbstractBaseEntity;
import org.bingo.bomb.commons.enums.ResourceTypeEnum;

/**
 * 资源
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午1:58:15
 * @since JDK 1.7
 */
@Entity
@Table(name = "sys_resource")
public class ResourceEntity extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 资源名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 类型 1菜单 2 按钮 3 超链接
	 * {@link ResourceTypeEnum}
	 */
	@Column(name = "type")
	@Enumerated(EnumType.ORDINAL)
	private ResourceTypeEnum type = ResourceTypeEnum.MENU;
	
	/**
	 * 资源路径
	 */
	@Column(name = "url")
	private String url;
	
	/**
	 * 父ID
	 */
	@Column(name = "parent_id")
	private long parentd;
	
	/**
	 * 关联关系
	 */
	@Column(name = "parent_ids")
	private String parentIds;
	
	/**
	 * 权限
	 */
	@Column(name = "permission")
	private String permission;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResourceTypeEnum getType() {
		return type;
	}

	public void setType(ResourceTypeEnum type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getParentd() {
		return parentd;
	}

	public void setParentd(long parentd) {
		this.parentd = parentd;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	
	
}

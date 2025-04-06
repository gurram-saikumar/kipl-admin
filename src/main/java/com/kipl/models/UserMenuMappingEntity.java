package com.kipl.models;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_MENU_MAPPING")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMenuMappingEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public UserMenuMappingEntity() {

	}

	public UserMenuMappingEntity(Long id) {
		this.id = id;
	}

	public UserMenuMappingEntity(String id) {
		this.id = Long.parseLong(id);
	}

	@ManyToOne
	@JoinColumn(name = "MENU_ID", columnDefinition = "bigint", unique = false)
	private MenuMaster menuId;

	@ManyToOne
	@JoinColumn(name = "SUB_MENU_ID", columnDefinition = "bigint", unique = false)
	private SubMenuMaster subMenuId;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", columnDefinition = "bigint", unique = false)
	private RoleMaster roleId;

//	@ManyToOne
//	@JoinColumn(name = "STATE_ID", columnDefinition = "bigint", unique = false)
//	private StateMasterEntity stateId;

	@Column(name = "EDITABLE", nullable = false, columnDefinition = "bit")
	private boolean isEdit;

	@Column(name = "VIEWABLE", nullable = false, columnDefinition = "bit")
	private boolean isView;

	@Column(name = "DELETABLE", nullable = false, columnDefinition = "bit")
	private boolean isDelete;

	@Column(name = "NAME")
	private String name;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "DISPLAY_ORDER")
	private int displayOrder;

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public MenuMaster getMenuId() {
		return menuId;
	}

	public void setMenuId(MenuMaster menuId) {
		this.menuId = menuId;
	}

	public SubMenuMaster getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(SubMenuMaster subMenuId) {
		this.subMenuId = subMenuId;
	}

	public RoleMaster getRoleId() {
		return roleId;
	}

	public void setRoleId(RoleMaster roleId) {
		this.roleId = roleId;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

package com.kipl.dto;

public class SubMenuControllerDTO {

	private boolean isEditable;
	private boolean isDelete;
	private boolean isView;
	private Long menuControllerId;
	private Long userId;
	private boolean status;
	private String name;
	private String title;
	private String description;
	private String icon;
	private String type;
	private String remarks;

	private String fontColour;
	private String backGroundColour;

	private Long webMenuControllerId;

	private Long id;
	private int displayOrder;

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}

	public Long getMenuControllerId() {
		return menuControllerId;
	}

	public void setMenuControllerId(Long menuControllerId) {
		this.menuControllerId = menuControllerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFontColour() {
		return fontColour;
	}

	public void setFontColour(String fontColour) {
		this.fontColour = fontColour;
	}

	public String getBackGroundColour() {
		return backGroundColour;
	}

	public void setBackGroundColour(String backGroundColour) {
		this.backGroundColour = backGroundColour;
	}

	public Long getWebMenuControllerId() {
		return webMenuControllerId;
	}

	public void setWebMenuControllerId(Long webMenuControllerId) {
		this.webMenuControllerId = webMenuControllerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

package com.kipl.models;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SUB_MENU_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubMenuMaster extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	public SubMenuMaster() {

	}

	public SubMenuMaster(Long id) {
		this.id = id;
	}

	public SubMenuMaster(String id) {
		this.id = Long.parseLong(id);
	}
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ICON")
	private String icon;
	
	@Column(name = "PATH")
	private String path;
	
	@Column(name = "ELEMENT")
	private String element;
	
	@Column(name = "FONT_COLOR")
	private String fontColor;
	
	@ManyToOne
	@JoinColumn(name = "MENU_ID", columnDefinition = "bigint", unique = false)
	private MenuMaster menuId;

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

	public MenuMaster getMenuId() {
		return menuId;
	}

	public void setMenuId(MenuMaster menuId) {
		this.menuId = menuId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	
	
	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	@Override
	public String toString() {
		return "SubMenuMaster [title=" + title + ", description=" + description + ", icon=" + icon + ", path=" + path
				+ ", element=" + element + ", fontColor=" + fontColor + ", menuId=" + menuId + "]";
	}


	
	
}

package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "MENU_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public MenuMaster() {

	}

	public MenuMaster(Long id) {
		this.id = id;
	}

	public MenuMaster(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ICON")
	private String icon;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "FONT_COLOUR")
	private String fontColour;

	@Column(name = "BACKGROUND_COLOUR")
	private String backGroundColour;
	
	@Column(name = "DISPLAY_ORDER")
	private int displayOrder;

	@Column(name = "PATH")
	private String path;
	
	@Column(name = "ELEMENT")
	private String element;

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

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	


	
	

}

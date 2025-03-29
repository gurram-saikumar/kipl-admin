package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MATERIAL_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialTypeMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public MaterialTypeMaster() {

	}

	public MaterialTypeMaster(Long id) {
		this.id = id;
	}

	public MaterialTypeMaster(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "MATERIAL_NAME")
	private String materialName;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "MATERIAL_CODE")
	private String materialCode;

	@ManyToOne
	@JoinColumn(name = "MATERIAL_TYPE_ID")
	private MaterialTypeMaster materialTypeId;

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

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public MaterialTypeMaster getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(MaterialTypeMaster materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@Override
	public String toString() {
		return "MaterialTypeMaster [materialName=" + materialName + ", title=" + title + ", description=" + description
				+ ", materialCode=" + materialCode + ", materialTypeId=" + materialTypeId + "]";
	}

	
	
	
}

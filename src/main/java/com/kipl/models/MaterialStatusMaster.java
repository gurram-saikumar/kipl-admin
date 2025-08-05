package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "MATERIAL_STATUS_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialStatusMaster  extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public MaterialStatusMaster() {

	}

	public MaterialStatusMaster(Long id) {
		this.id = id;
	}

	public MaterialStatusMaster(String id) {
		this.id = Long.parseLong(id);
	}
	
	@Column(name = "NAME")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MaterialStatusMaster [name=" + name + "]";
	}
	
	

}

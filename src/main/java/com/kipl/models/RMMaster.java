package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "RM_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RMMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public RMMaster() {

	}

	public RMMaster(Long id) {
		this.id = id;
	}

	public RMMaster(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "RM_SIZE")
	private String rmSize;

	public String getRmSize() {
		return rmSize;
	}

	public void setRmSize(String rmSize) {
		this.rmSize = rmSize;
	}

	@Override
	public String toString() {
		return "RMMaster [rmSize=" + rmSize + "]";
	}
	
	
	
	
}

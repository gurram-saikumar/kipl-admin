package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "COLOUR_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColourMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public ColourMaster() {

	}

	public ColourMaster(Long id) {
		this.id = id;
	}

	public ColourMaster(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "COLOUR")
	private String colour;

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	@Override
	public String toString() {
		return "ColourMaster [colour=" + colour + "]";
	}
	
	

}

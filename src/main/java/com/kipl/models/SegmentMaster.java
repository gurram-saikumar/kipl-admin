package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SEGMENT_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentMaster  extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public SegmentMaster() {

	}

	public SegmentMaster(Long id) {
		this.id = id;
	}

	public SegmentMaster(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "SEGMENT")
	private String segment;

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	@Override
	public String toString() {
		return "SegmentMaster [segment=" + segment + "]";
	}
	
	

}

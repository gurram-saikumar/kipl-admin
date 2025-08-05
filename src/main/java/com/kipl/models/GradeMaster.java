package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "GRADE_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GradeMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public GradeMaster() {

	}

	public GradeMaster(Long id) {
		this.id = id;
	}

	public GradeMaster(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "GRADE")
	private String grade;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "GradeMaster [grade=" + grade + "]";
	}
	
	
}

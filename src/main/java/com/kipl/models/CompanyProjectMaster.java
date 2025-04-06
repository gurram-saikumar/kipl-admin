package com.kipl.models;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMPANY_PROJECTS_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyProjectMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public CompanyProjectMaster() {

	}

	public CompanyProjectMaster(Long id) {
		this.id = id;
	}

	public CompanyProjectMaster(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "TITLE")
	private String title;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "CUSTOMER_ADDRESS")
	private String customerAddress;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	
	
}

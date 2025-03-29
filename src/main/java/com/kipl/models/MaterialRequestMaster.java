package com.kipl.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MATRIAL_REQUEST_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialRequestMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public MaterialRequestMaster() {

	}

	public MaterialRequestMaster(Long id) {
		this.id = id;
	}

	public MaterialRequestMaster(String id) {
		this.id = Long.parseLong(id);
	}
	
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	private CompanyProjectMaster projectId;
	
	@ManyToOne
	@JoinColumn(name = "REQUESTER_ID")
	private UserMaster requesterId;
	
	@Column(name = "REQUIRED_DATE")
	private Timestamp requiredDate;
	
	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;
	
	@Column(name = "REQUEST_STATUS")
	private String requestStatus;
	
	@Column(name = "REQUIRED_LOCATION")
	private String requiredLocation;
	
	@Column(name = "COMMENTS")
	private String comments;

	public CompanyProjectMaster getProjectId() {
		return projectId;
	}

	public void setProjectId(CompanyProjectMaster projectId) {
		this.projectId = projectId;
	}

	public UserMaster getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(UserMaster requesterId) {
		this.requesterId = requesterId;
	}

	public Timestamp getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Timestamp requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequiredLocation() {
		return requiredLocation;
	}

	public void setRequiredLocation(String requiredLocation) {
		this.requiredLocation = requiredLocation;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "MaterialRequestMaster [projectId=" + projectId + ", requesterId=" + requesterId + ", requiredDate="
				+ requiredDate + ", serialNumber=" + serialNumber + ", requestStatus=" + requestStatus
				+ ", requiredLocation=" + requiredLocation + ", comments=" + comments + "]";
	}
	
	
	
}

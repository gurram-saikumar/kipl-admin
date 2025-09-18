package com.kipl.models;

import java.sql.Timestamp;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MATERIAL_REQUEST_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialRequestMaster extends BaseEntity {

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

	@Column(name = "PROJECT_NAME")
	private String projectName;

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

	@Column(name = "REQUEST_ID")
	private Long requestId;

	@Column(name = "TOTAL_ORDER_VALUE")
	private Double totalOrderValue;

	@Column(name = "PRODUCT_REQUIRED_LOCATION_NAME")
	private String productRequiredLocationName;

	@Column(name = "PRODUCT_REQUIRED_LOCATION_ID")
	private Long productRequiredLocationId;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "ISSUER_REMARKS")
	private String issuedRemarks;

	@Column(name = "ISSUED_DATE")
	private Timestamp issuedDate;

	@Column(name = "REQUESTOR_REMARKS")
	private String requesterRemarks;

	public CompanyProjectMaster getProjectId() {
		return projectId;
	}

	public void setProjectId(CompanyProjectMaster projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Double getTotalOrderValue() {
		return totalOrderValue;
	}

	public void setTotalOrderValue(Double totalOrderValue) {
		this.totalOrderValue = totalOrderValue;
	}

	public String getProductRequiredLocationName() {
		return productRequiredLocationName;
	}

	public void setProductRequiredLocationName(String productRequiredLocationName) {
		this.productRequiredLocationName = productRequiredLocationName;
	}

	public Long getProductRequiredLocationId() {
		return productRequiredLocationId;
	}

	public void setProductRequiredLocationId(Long productRequiredLocationId) {
		this.productRequiredLocationId = productRequiredLocationId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIssuedRemarks() {
		return issuedRemarks;
	}

	public void setIssuedRemarks(String issuedRemarks) {
		this.issuedRemarks = issuedRemarks;
	}

	public String getRequesterRemarks() {
		return requesterRemarks;
	}

	public void setRequesterRemarks(String requesterRemarks) {
		this.requesterRemarks = requesterRemarks;
	}

	public Timestamp getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Timestamp issuedDate) {
		this.issuedDate = issuedDate;
	}

}

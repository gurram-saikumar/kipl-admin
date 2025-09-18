package com.kipl.dto;

import java.util.List;

import jakarta.persistence.Column;

public class MaterialRequestDto {
	private Long materialRequestId;
	private Long projectId;
	private String projectName;
	private Long meterialId;
	private String materialName;
	private String remarks;
	private String serialNumber;
	private Long requesterId;
	private String requestedDate;
	private List<MaterialRequestListDto> materialRequestList;
	private String issuedDate;
	private Long issuerId;
	private Double issuedQuantity;
	private Long requestId;
	private Long materialRequestHistoryId;
	private String requesterName;
	private String issuerName;
	private Double issuedWeight;

	private Double totalOrderValue;
	private String requiredDate;
	private String productRequiredLocationName;
	private Long productRequiredLocationId;

	private String issuedRemarks;
	private String requesterRemarks;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getMeterialId() {
		return meterialId;
	}

	public void setMeterialId(Long meterialId) {
		this.meterialId = meterialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(Long requesterId) {
		this.requesterId = requesterId;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public List<MaterialRequestListDto> getMaterialRequestList() {
		return materialRequestList;
	}

	public void setMaterialRequestList(List<MaterialRequestListDto> materialRequestList) {
		this.materialRequestList = materialRequestList;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public Double getIssuedQuantity() {
		return issuedQuantity;
	}

	public void setIssuedQuantity(Double issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getMaterialRequestHistoryId() {
		return materialRequestHistoryId;
	}

	public void setMaterialRequestHistoryId(Long materialRequestHistoryId) {
		this.materialRequestHistoryId = materialRequestHistoryId;
	}

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public Long getMaterialRequestId() {
		return materialRequestId;
	}

	public void setMaterialRequestId(Long materialRequestId) {
		this.materialRequestId = materialRequestId;
	}

	public Double getIssuedWeight() {
		return issuedWeight;
	}

	public void setIssuedWeight(Double issuedWeight) {
		this.issuedWeight = issuedWeight;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getTotalOrderValue() {
		return totalOrderValue;
	}

	public void setTotalOrderValue(Double totalOrderValue) {
		this.totalOrderValue = totalOrderValue;
	}

	public String getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(String requiredDate) {
		this.requiredDate = requiredDate;
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

}

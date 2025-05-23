package com.kipl.dto;

public class MaterialRequestListDto {

	private String materialName;
	private Long materialId;
	private Double requiredQuantity;
	private Double totalOrderValue;
	private String productRequiredLocation;
	private Long projectId;
	private String requiredDate;
	private String uom;
	private Long materialRequestHistoryId;
	private String projectName;
	private String issuerName;
	private String requestStatus;
	private Double issuedQuantity;
	private Double requiredWeight;

	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Double getRequiredQuantity() {
		return requiredQuantity;
	}
	public void setRequiredQuantity(Double requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}
	public Double getTotalOrderValue() {
		return totalOrderValue;
	}
	public void setTotalOrderValue(Double totalOrderValue) {
		this.totalOrderValue = totalOrderValue;
	}
	public String getProductRequiredLocation() {
		return productRequiredLocation;
	}
	public void setProductRequiredLocation(String productRequiredLocation) {
		this.productRequiredLocation = productRequiredLocation;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getRequiredDate() {
		return requiredDate;
	}
	public void setRequiredDate(String requiredDate) {
		this.requiredDate = requiredDate;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public Long getMaterialRequestHistoryId() {
		return materialRequestHistoryId;
	}
	public void setMaterialRequestHistoryId(Long materialRequestHistoryId) {
		this.materialRequestHistoryId = materialRequestHistoryId;
	}
	public Double getIssuedQuantity() {
		return issuedQuantity;
	}
	public void setIssuedQuantity(Double issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}
	public Double getRequiredWeight() {
		return requiredWeight;
	}
	public void setRequiredWeight(Double requiredWeight) {
		this.requiredWeight = requiredWeight;
	}
	@Override
	public String toString() {
		return "MaterialRequestListDto [materialName=" + materialName + ", materialId=" + materialId
				+ ", requiredQuantity=" + requiredQuantity + ", totalOrderValue=" + totalOrderValue
				+ ", productRequiredLocation=" + productRequiredLocation + ", projectId=" + projectId
				+ ", requiredDate=" + requiredDate + ", uom=" + uom + ", materialRequestHistoryId="
				+ materialRequestHistoryId + ", projectName=" + projectName + ", issuerName=" + issuerName
				+ ", requestStatus=" + requestStatus + ", issuedQuantity=" + issuedQuantity + ", requiredWeight="
				+ requiredWeight + "]";
	}
	
	
}

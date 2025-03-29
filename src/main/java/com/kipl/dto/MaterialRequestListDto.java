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
	@Override
	public String toString() {
		return "MaterialRequestListDto [materialName=" + materialName + ", materialId=" + materialId
				+ ", requiredQuantity=" + requiredQuantity + ", totalOrderValue=" + totalOrderValue
				+ ", productRequiredLocation=" + productRequiredLocation + ", projectId=" + projectId
				+ ", requiredDate=" + requiredDate + ", uom=" + uom + "]";
	}
	
	
	
}

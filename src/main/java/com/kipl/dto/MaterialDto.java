package com.kipl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialDto {

	private String rmSize;
	private String segment;
	private String colour;
	private String grade;
	private String mpa;
	private String section;
	private String customer;
	private String materialDescrption;
	private String length;
	private String width;
	private String thick;
	private String uom;
	private Double weight;
	private String location;
	private String remarks;
	private String price;
	private String materialCode;
	private String materialType;
	private Double totalQuantity;
	private Double availableQuantity;
	private Double quantity;
	private String materialName;
	
	private Double totalWeight;
	private Double availableWeight;
	
	private Double issuedQuantity;
	private Double issuedWeight;
	
	private Long inventoryMasterId;
	
	public String getRmSize() {
		return rmSize;
	}
	public void setRmSize(String rmSize) {
		this.rmSize = rmSize;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getMpa() {
		return mpa;
	}
	public void setMpa(String mpa) {
		this.mpa = mpa;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getMaterialDescrption() {
		return materialDescrption;
	}
	public void setMaterialDescrption(String materialDescrption) {
		this.materialDescrption = materialDescrption;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getThick() {
		return thick;
	}
	public void setThick(String thick) {
		this.thick = thick;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public Double getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public Double getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(Double availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Long getInventoryMasterId() {
		return inventoryMasterId;
	}
	public void setInventoryMasterId(Long inventoryMasterId) {
		this.inventoryMasterId = inventoryMasterId;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Double getAvailableWeight() {
		return availableWeight;
	}
	public void setAvailableWeight(Double availableWeight) {
		this.availableWeight = availableWeight;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Double getIssuedQuantity() {
		return issuedQuantity;
	}
	public void setIssuedQuantity(Double issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}
	public Double getIssuedWeight() {
		return issuedWeight;
	}
	public void setIssuedWeight(Double issuedWeight) {
		this.issuedWeight = issuedWeight;
	}
	@Override
	public String toString() {
		return "MaterialDto [rmSize=" + rmSize + ", segment=" + segment + ", colour=" + colour + ", grade=" + grade
				+ ", mpa=" + mpa + ", section=" + section + ", customer=" + customer + ", materialDescrption="
				+ materialDescrption + ", length=" + length + ", width=" + width + ", thick=" + thick + ", uom=" + uom
				+ ", weight=" + weight + ", location=" + location + ", remarks=" + remarks + ", price=" + price
				+ ", materialCode=" + materialCode + ", materialType=" + materialType + ", totalQuantity="
				+ totalQuantity + ", availableQuantity=" + availableQuantity + ", quantity=" + quantity
				+ ", materialName=" + materialName + ", totalWeight=" + totalWeight + ", availableWeight="
				+ availableWeight + ", issuedQuantity=" + issuedQuantity + ", issuedWeight=" + issuedWeight
				+ ", inventoryMasterId=" + inventoryMasterId + "]";
	}
	

}

package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "INVENTORY_HISTORY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryHistory extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public InventoryHistory() {

	}

	public InventoryHistory(Long id) {
		this.id = id;
	}

	public InventoryHistory(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "SEGMENTS")
	private String segment;
	
	@Column(name = "RM_SIZE")
	private String rmSize;
		
	@Column(name = "COLOUR")
	private String colour;
		
	@Column(name = "GRADE")
	private String grade;
		
	@Column(name = "MPA")
	private String mpa;
		
	@Column(name = "SECTION")
	private String section;
		
	@Column(name = "CUSTOMER")
	private String customer;
		
	@Column(name = "MATERIAL_DESCPRITION")
	private String materialDescrption;
		
	@Column(name = "TOTAL_QUANTITY")
	private Double totalQuantity;
	
	@Column(name = "AVAILABLE_QUANTITY")
	private Double availableQuantity;
		
	@Column(name = "LENGTH")
	private String length;
		
	@Column(name = "WIDTH")
	private String width;
		
	@Column(name = "THCIK")
	private String thick;
		
	@Column(name = "UOM")
	private String uom;
		
	@Column(name = "WEIGHT")
	private Double weight;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "PRICE")
	private String price;
	
	@Column(name = "MATERIAL_CODE")
	private String materialCode;
	
	@Column(name = "MATERIAL_TYPE")
	private String materialType;

	@Column(name = "MATERIAL_ID")
	private String materialId;
	
	@ManyToOne
	@JoinColumn(name= "INVENTORY_MASTER_ID")
	private InventoryMaster inventoryMasterId;

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getRmSize() {
		return rmSize;
	}

	public void setRmSize(String rmSize) {
		this.rmSize = rmSize;
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

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public InventoryMaster getInventoryMasterId() {
		return inventoryMasterId;
	}

	public void setInventoryMasterId(InventoryMaster inventoryMasterId) {
		this.inventoryMasterId = inventoryMasterId;
	}

	@Override
	public String toString() {
		return "InventoryHistory [segment=" + segment + ", rmSize=" + rmSize + ", colour=" + colour + ", grade=" + grade
				+ ", mpa=" + mpa + ", section=" + section + ", customer=" + customer + ", materialDescrption="
				+ materialDescrption + ", totalQuantity=" + totalQuantity + ", availableQuantity=" + availableQuantity
				+ ", length=" + length + ", width=" + width + ", thick=" + thick + ", uom=" + uom + ", weight=" + weight
				+ ", location=" + location + ", remarks=" + remarks + ", price=" + price + ", materialCode="
				+ materialCode + ", materialType=" + materialType + ", materialId=" + materialId
				+ ", inventoryMasterId=" + inventoryMasterId + "]";
	}
	
	

}

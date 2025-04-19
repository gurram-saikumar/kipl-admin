package com.kipl.models;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "MATERIAL_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public MaterialMaster() {

	}

	public MaterialMaster(Long id) {
		this.id = id;
	}

	public MaterialMaster(String id) {
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
		
	@Column(name = "LENGTH")
	private String length;
		
	@Column(name = "WIDTH")
	private String width;
		
	@Column(name = "THCIK")
	private String thick;
		
	@Column(name = "UOM")
	private String uom;
	
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

	@Override
	public String toString() {
		return "MaterialMaster [segment=" + segment + ", rmSize=" + rmSize + ", colour=" + colour + ", grade=" + grade
				+ ", mpa=" + mpa + ", section=" + section + ", customer=" + customer + ", materialDescrption="
				+ materialDescrption + ", length=" + length + ", width=" + width + ", thick=" + thick + ", uom=" + uom
				+ ", location=" + location + ", remarks=" + remarks + ", price=" + price + ", materialCode="
				+ materialCode + ", materialType=" + materialType + "]";
	}

	
}

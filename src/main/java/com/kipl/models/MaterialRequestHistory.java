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
@Table(name = "MATERIAL_REQUEST_HISTORY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialRequestHistory extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public MaterialRequestHistory() {

	}

	public MaterialRequestHistory(Long id) {
		this.id = id;
	}

	public MaterialRequestHistory(String id) {
		this.id = Long.parseLong(id);
	}
	
	@Column(name="REQUIRED_DATE")
	private Timestamp requiredDate;
	
	
	@Column(name="SERIAL_NMUBER")
	private String serialNmuber;
	
	@ManyToOne
	@JoinColumn(name = "REQUEST_ID")
	private MaterialRequestMaster requestId;
	
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	private CompanyProjectMaster projectId;
	
	@ManyToOne
	@JoinColumn(name = "MATERIAL_TYPE_ID")
	private MaterialTypeMaster materialTypeId;
	
	@ManyToOne
	@JoinColumn(name = "MATERIAL_ID")
	private MaterialMaster materialId;
	
	@Column(name = "REQUIRED_QUANTITY")
	private Double requiredQuantity;
	
	@Column(name = "TOTAL_ORDER_VALUE")
	private Double totalOrderValue;
	
	@Column(name = "REQUEST_LOCATION")
	private String requestLocation;
	
	@Column(name = "UOM")
	private String uom;
	
	public Timestamp getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Timestamp requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getSerialNmuber() {
		return serialNmuber;
	}

	public void setSerialNmuber(String serialNmuber) {
		this.serialNmuber = serialNmuber;
	}

	public CompanyProjectMaster getProjectId() {
		return projectId;
	}

	public void setProjectId(CompanyProjectMaster projectId) {
		this.projectId = projectId;
	}

	public MaterialTypeMaster getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(MaterialTypeMaster materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public MaterialMaster getMaterialId() {
		return materialId;
	}

	public void setMaterialId(MaterialMaster materialId) {
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

	public String getRequestLocation() {
		return requestLocation;
	}

	public void setRequestLocation(String requestLocation) {
		this.requestLocation = requestLocation;
	}

	public MaterialRequestMaster getRequestId() {
		return requestId;
	}

	public void setRequestId(MaterialRequestMaster requestId) {
		this.requestId = requestId;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	@Override
	public String toString() {
		return "MaterialRequestHistory [requiredDate=" + requiredDate + ", serialNmuber=" + serialNmuber
				+ ", requestId=" + requestId + ", projectId=" + projectId + ", materialTypeId=" + materialTypeId
				+ ", materialId=" + materialId + ", requiredQuantity=" + requiredQuantity + ", totalOrderValue="
				+ totalOrderValue + ", requestLocation=" + requestLocation + ", uom=" + uom + "]";
	}



	

}


package com.kipl.dto;

import java.util.List;

public class MaterialRequestDto {
	private Long projectId;
	private Long meterialId;
	private String materialName;
	private String remarks;
	private String serialNumber;
	private Long requesterId;
	private String requestedDate;
	private List<MaterialRequestListDto> materialRequestList;
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
	@Override
	public String toString() {
		return "MaterialRequestDto [projectId=" + projectId + ", meterialId=" + meterialId + ", materialName="
				+ materialName + ", remarks=" + remarks + ", serialNumber=" + serialNumber + ", requesterId="
				+ requesterId + ", requestedDate=" + requestedDate + ", materialRequestList=" + materialRequestList
				+ "]";
	}
	
	
	
}

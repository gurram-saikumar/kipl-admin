package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_RETAILER_MAPPING_HISTORY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReMappingHistory extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public ReMappingHistory() {
	}

	public ReMappingHistory(Long id) {
		this.id = id;
	}

	public ReMappingHistory(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "NAME")
	private String name;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private UserMaster employee;

	@Column(name = "EXISTING_ROLE_NAME")
	private String existingRoleName;

	@Column(name = "EXISTING_ZONE_ID")
	private Long existingZone;

	@Column(name = "EXISTING_STATE_ID")
	private Long existingState;

	@Column(name = "EXISTING_REGION_ID")
	private Long existingRegion;

	@Column(name = "EXISTING_TERRITORY_ID")
	private Long existingTerritory;

	@Column(name = "EXISTING_DISTRICT_ID")
	private Long existingDistrict;

	@Column(name = "EXISTING_HEADQUARTER_ID")
	private Long existingHeadquarter;

	@Column(name = "UPDATED_ROLE_NAME")
	private String updatedRoleName;

	@Column(name = "UPDATED_ZONE_ID")
	private Long updatedZone;

	@Column(name = "UPDATED_STATE_ID")
	private Long updatedState;

	@Column(name = "UPDATED_REGION_ID")
	private Long updatedRegion;

	@Column(name = "UPDATED_TERRITORY_ID")
	private Long updatedTerritory;

	@Column(name = "UPDATED_DISTRICT_ID")
	private Long updatedDistrict;

	@Column(name = "UPDATED_HEADQUARTER_ID")
	private Long updatedHeadquarter;

	@Column(name = "PREVIUOS_EMPLOYEE_USER_ID")
	private Long previousEmployeeUserId;

	@Column(name = "MODIFIED_BY")
	private Long modifiedBy;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public UserMaster getEmployee() {
		return employee;
	}

	public void setEmployee(UserMaster employee) {
		this.employee = employee;
	}

	public String getExistingRoleName() {
		return existingRoleName;
	}

	public void setExistingRoleName(String existingRoleName) {
		this.existingRoleName = existingRoleName;
	}

	public Long getExistingZone() {
		return existingZone;
	}

	public void setExistingZone(Long existingZone) {
		this.existingZone = existingZone;
	}

	public Long getExistingState() {
		return existingState;
	}

	public void setExistingState(Long existingState) {
		this.existingState = existingState;
	}

	public Long getExistingRegion() {
		return existingRegion;
	}

	public void setExistingRegion(Long existingRegion) {
		this.existingRegion = existingRegion;
	}

	public Long getExistingTerritory() {
		return existingTerritory;
	}

	public void setExistingTerritory(Long existingTerritory) {
		this.existingTerritory = existingTerritory;
	}

	public Long getExistingDistrict() {
		return existingDistrict;
	}

	public void setExistingDistrict(Long existingDistrict) {
		this.existingDistrict = existingDistrict;
	}

	public Long getExistingHeadquarter() {
		return existingHeadquarter;
	}

	public void setExistingHeadquarter(Long existingHeadquarter) {
		this.existingHeadquarter = existingHeadquarter;
	}

	public String getUpdatedRoleName() {
		return updatedRoleName;
	}

	public void setUpdatedRoleName(String updatedRoleName) {
		this.updatedRoleName = updatedRoleName;
	}

	public Long getUpdatedZone() {
		return updatedZone;
	}

	public void setUpdatedZone(Long updatedZone) {
		this.updatedZone = updatedZone;
	}

	public Long getUpdatedState() {
		return updatedState;
	}

	public void setUpdatedState(Long updatedState) {
		this.updatedState = updatedState;
	}

	public Long getUpdatedRegion() {
		return updatedRegion;
	}

	public void setUpdatedRegion(Long updatedRegion) {
		this.updatedRegion = updatedRegion;
	}

	public Long getUpdatedTerritory() {
		return updatedTerritory;
	}

	public void setUpdatedTerritory(Long updatedTerritory) {
		this.updatedTerritory = updatedTerritory;
	}

	public Long getUpdatedDistrict() {
		return updatedDistrict;
	}

	public void setUpdatedDistrict(Long updatedDistrict) {
		this.updatedDistrict = updatedDistrict;
	}

	public Long getUpdatedHeadquarter() {
		return updatedHeadquarter;
	}

	public void setUpdatedHeadquarter(Long updatedHeadquarter) {
		this.updatedHeadquarter = updatedHeadquarter;
	}

	public Long getPreviousEmployeeUserId() {
		return previousEmployeeUserId;
	}

	public void setPreviousEmployeeUserId(Long previousEmployeeUserId) {
		this.previousEmployeeUserId = previousEmployeeUserId;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}

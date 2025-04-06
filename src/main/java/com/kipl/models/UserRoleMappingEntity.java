package com.kipl.models;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_ROLE_MAPPING")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRoleMappingEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public UserRoleMappingEntity() {

	}

	public UserRoleMappingEntity(Long id) {
		this.id = id;
	}

	public UserRoleMappingEntity(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "USER_ID")
	private long userId;

	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@Column(name = "ROLE_ID")
	private long roleId;
	
	@Column(name = "ZONE_ID")
	private long zoneId;
	
	@Column(name = "STATE_ID")
	private long stateId;
	
	@Column(name = "REGION_ID")
	private long regionId;
	
	@Column(name = "TERRITORY_ID")
	private long territoryId;
	
	@Column(name = "DISTRICT_ID")
	private long districtId;
	
	@Column(name = "HEADQUARETER_ID")
	private long headquarterId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getZoneId() {
		return zoneId;
	}

	public void setZoneId(long zoneId) {
		this.zoneId = zoneId;
	}

	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public long getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(long territoryId) {
		this.territoryId = territoryId;
	}

	public long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	public long getHeadquarterId() {
		return headquarterId;
	}

	public void setHeadquarterId(long headquarterId) {
		this.headquarterId = headquarterId;
	}

	

	
}
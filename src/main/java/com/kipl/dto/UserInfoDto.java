package com.kipl.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class UserInfoDto {

	private String name;
	private String password;
	private Long roleId;
	private String roleName;
	private String stateName;
	private String districtName;

	private String mobileNumber;
	private Date dateOfBirth;
	private String emailId;
	private String gender;
	private String deviceId;
	private String deviceType;
	private String deviceToken;
	private String fcmToken;
	private String profilePicUrl;
	private Long stateId;
	private Long districtId;
	private Long countryId;
	private String countryName;
	private String location;
	private String aadharId;
	private String village;
	private String block;
	private String pinCode;
	private String address;
	private Long zoneId;
	private Long territoryId;
	private Long regionId;
	private Long headquarterId;

	private Timestamp modifiedOn;
	private Timestamp createdOn;
	private boolean status;
	private Long id;

	private String addressLine;
	private String alternativeMobileNumber;
	private String city;
	private String landMark;
	private double latitude;
	private double longitude;

	private long code;
	private String otp;
	private boolean forceUpdate;
	private boolean mandatoryUpdate;
	private boolean isdefaultRole;

	private List<SubMenuControllerDTO> userMenuControl;

	private List<Long> userPointsEarned;
	private List<Long> userPointsReedemed;

	private String employeeId;
	private String loginId;

	private String zoneName;
	private String regionName;
	private String territoryName;
	private String headQuarterName;

	private String roleLocation;
	
	private String companyName;
	private String companyCode;
	private String companyLogoPath;
	private String primaryColor;
	private String secondaryColor;
	private String textColor;
	private String iconPrimaryColor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAadharId() {
		return aadharId;
	}

	public void setAadharId(String aadharId) {
		this.aadharId = aadharId;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(Long territoryId) {
		this.territoryId = territoryId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getHeadquarterId() {
		return headquarterId;
	}

	public void setHeadquarterId(Long headquarterId) {
		this.headquarterId = headquarterId;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getAlternativeMobileNumber() {
		return alternativeMobileNumber;
	}

	public void setAlternativeMobileNumber(String alternativeMobileNumber) {
		this.alternativeMobileNumber = alternativeMobileNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public boolean isMandatoryUpdate() {
		return mandatoryUpdate;
	}

	public void setMandatoryUpdate(boolean mandatoryUpdate) {
		this.mandatoryUpdate = mandatoryUpdate;
	}

	public List<SubMenuControllerDTO> getUserMenuControl() {
		return userMenuControl;
	}

	public void setUserMenuControl(List<SubMenuControllerDTO> userMenuControl) {
		this.userMenuControl = userMenuControl;
	}

	public List<Long> getUserPointsEarned() {
		return userPointsEarned;
	}

	public void setUserPointsEarned(List<Long> userPointsEarned) {
		this.userPointsEarned = userPointsEarned;
	}

	public List<Long> getUserPointsReedemed() {
		return userPointsReedemed;
	}

	public void setUserPointsReedemed(List<Long> userPointsReedemed) {
		this.userPointsReedemed = userPointsReedemed;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getTerritoryName() {
		return territoryName;
	}

	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	public String getHeadQuarterName() {
		return headQuarterName;
	}

	public void setHeadQuarterName(String headQuarterName) {
		this.headQuarterName = headQuarterName;
	}

	public String getRoleLocation() {
		return roleLocation;
	}

	public void setRoleLocation(String roleLocation) {
		this.roleLocation = roleLocation;
	}

	public boolean isIsdefaultRole() {
		return isdefaultRole;
	}

	public void setIsdefaultRole(boolean isdefaultRole) {
		this.isdefaultRole = isdefaultRole;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLogoPath() {
		return companyLogoPath;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public String getSecondaryColor() {
		return secondaryColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setCompanyLogoPath(String companyLogoPath) {
		this.companyLogoPath = companyLogoPath;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getIconPrimaryColor() {
		return iconPrimaryColor;
	}

	public void setIconPrimaryColor(String iconPrimaryColor) {
		this.iconPrimaryColor = iconPrimaryColor;
	}

}
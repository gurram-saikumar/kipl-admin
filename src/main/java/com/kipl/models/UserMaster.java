package com.kipl.models;

import java.sql.Date;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "USER_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMaster  extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public UserMaster() {

	}

	public UserMaster(Long id) {
		this.id = id;
	}

	public UserMaster(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "NAME")
	private String name;

	@Column(name = "PASSWORD", length = 255)
	private String password;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private RoleMaster role;

	@Column(name = "ROLE_NAME")
	private String roleName;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "DEVICE_ID")
	private String deviceId;

	@Column(name = "DEVICE_TYPE")
	private String deviceType;

	@Column(name = "DEVICE_TOKEN")
	private String deviceToken;

	@Column(name = "FCM_TOKEN")
	private String fcmToken;

	@Column(name = "PROFILE_PIC_URL")
	private String profilePicUrl;

	@Column(name = "STATE_NAME")
	private String stateName;

	@Column(name = "DISTRICT_NAME")
	private String districtName;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@Column(name = "LOCATION", length = 4000)
	private String location;

	@Column(name = "EMPLOYEE_ID")
	private String employeeId;

	@Column(name = "LOGIN_ID")
	private String loginId;

	@Column(name = "ADDRESS", length = 4000)
	private String address;

	@Column(name = "VILLAGE")
	private String village;

	@Column(name = "BLOCK")
	private String block;

	@Column(name = "PINCODE")
	private String pinCode;

	@Column(name = "LAND_MARK")
	private String landMark;

	@Column(name = "CITY")
	private String city;

	@Column(name = "ALTERNATIVE_MOBILE_NUMBER")
	private String alternativeMobileNumber;

	@Column(name = "REMARKS", length = 255)
	private String remarks;

	@Column(name = "OPT_IN_WHATSAPP")
	private Boolean optInWhatsApp;
	
	@Column(name = "PASS_CODE")
	private String passcode;

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

	public RoleMaster getRole() {
		return role;
	}

	public void setRole(RoleMaster role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAlternativeMobileNumber() {
		return alternativeMobileNumber;
	}

	public void setAlternativeMobileNumber(String alternativeMobileNumber) {
		this.alternativeMobileNumber = alternativeMobileNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getOptInWhatsApp() {
		return optInWhatsApp;
	}

	public void setOptInWhatsApp(Boolean optInWhatsApp) {
		this.optInWhatsApp = optInWhatsApp;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	@Override
	public String toString() {
		return "UserMaster [name=" + name + ", password=" + password + ", role=" + role + ", roleName=" + roleName
				+ ", mobileNumber=" + mobileNumber + ", dateOfBirth=" + dateOfBirth + ", emailId=" + emailId
				+ ", gender=" + gender + ", deviceId=" + deviceId + ", deviceType=" + deviceType + ", deviceToken="
				+ deviceToken + ", fcmToken=" + fcmToken + ", profilePicUrl=" + profilePicUrl + ", stateName="
				+ stateName + ", districtName=" + districtName + ", countryName=" + countryName + ", location="
				+ location + ", employeeId=" + employeeId + ", loginId=" + loginId + ", address=" + address
				+ ", village=" + village + ", block=" + block + ", pinCode=" + pinCode + ", landMark=" + landMark
				+ ", city=" + city + ", alternativeMobileNumber=" + alternativeMobileNumber + ", remarks=" + remarks
				+ ", optInWhatsApp=" + optInWhatsApp + ", passcode=" + passcode + "]";
	}
	
	

}

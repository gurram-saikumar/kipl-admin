package com.kipl.models;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "NOTIFICATIONS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notifications extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public Notifications() {

	}

	public Notifications(Long id) {
		this.id = id;
	}

	public Notifications(String id) {
		this.id = Long.parseLong(id);
	}

	@Column(name = "NOTIFICATION_TYPE")
	private String notificationType;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster user;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "DEVICE_ID")
	private String deviceId;

	@Column(name = "REQUEST_ID")
	private String requestId;

	@Column(name = "FCM_TOKEN")
	private String fcmToken;

	@Column(name = "ICON")
	private String icon;

	@Column(name = "EKYC_ID")
	private Long ekycId;

	@Column(name = "IS_ACTIVE", columnDefinition = "bit")
	private boolean active;

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	

	public UserMaster getUser() {
		return user;
	}

	public void setUser(UserMaster user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getEkycId() {
		return ekycId;
	}

	public void setEkycId(Long ekycId) {
		this.ekycId = ekycId;
	}

}
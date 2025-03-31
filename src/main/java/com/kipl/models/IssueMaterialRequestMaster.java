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
@Table(name ="ISSUE_MATERIAL_REQUEST_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueMaterialRequestMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public IssueMaterialRequestMaster() {

	}

	public IssueMaterialRequestMaster(Long id) {
		this.id = id;
	}

	public IssueMaterialRequestMaster(String id) {
		this.id = Long.parseLong(id);
	}
	
	@ManyToOne
	@JoinColumn(name = "REQUEST_ID")
	private MaterialRequestMaster requestId;
	
	@ManyToOne
	@JoinColumn(name = "REQUEST_HISTORY_ID")
	private MaterialRequestHistory requestHistoryId;
	
	@ManyToOne
	@JoinColumn(name = "ISSEUER_ID")
	private UserMaster issuerId;
	
	@Column(name ="ISSUED_QUANTITY")
	private Double issuedQuantity;
	
	@Column(name ="ISSUED_DATE")
	private Timestamp issuedDate;

	public MaterialRequestMaster getRequestId() {
		return requestId;
	}

	public void setRequestId(MaterialRequestMaster requestId) {
		this.requestId = requestId;
	}

	public MaterialRequestHistory getRequestHistoryId() {
		return requestHistoryId;
	}

	public void setRequestHistoryId(MaterialRequestHistory requestHistoryId) {
		this.requestHistoryId = requestHistoryId;
	}

	public UserMaster getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(UserMaster issuerId) {
		this.issuerId = issuerId;
	}

	public Double getIssuedQuantity() {
		return issuedQuantity;
	}

	public void setIssuedQuantity(Double issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}

	public Timestamp getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Timestamp issuedDate) {
		this.issuedDate = issuedDate;
	}

	@Override
	public String toString() {
		return "IssueMaterialRequestMaster [requestId=" + requestId + ", requestHistoryId=" + requestHistoryId
				+ ", issuerId=" + issuerId + ", issuedQuantity=" + issuedQuantity + ", issuedDate=" + issuedDate + "]";
	}
	
	
}

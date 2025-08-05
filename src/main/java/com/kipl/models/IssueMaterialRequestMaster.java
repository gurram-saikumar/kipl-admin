package com.kipl.models;

import java.sql.Timestamp;

import com.kipl.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	@JoinColumn(name = "MATERIAL_REQUEST_MASTER_ID")
	private MaterialRequestMaster materialRequestMasterId;
	
	@ManyToOne
	@JoinColumn(name = "MATERIAL_REQUEST_HISTORY_ID")
	private MaterialRequestHistory materialRequestHistoryId;
	
	@ManyToOne
	@JoinColumn(name = "ISSEUER_ID")
	private UserMaster issuerId;
	
	@Column(name ="ISSUED_QUANTITY")
	private Double issuedQuantity;
	
	@Column(name ="ISSUED_WEIGHT")
	private Double issuedWeight;
	
	@Column(name ="ISSUED_DATE")
	private Timestamp issuedDate;
	
	@Column(name="ISSUE_STATUS")
	private String issueStatus;
	
	@Column(name ="WIP_QUANTITY")
	private Double wipQuantity;
	
	@Column(name ="WIP_WEIGHT")
	private Double wipWeight;

	

	public MaterialRequestMaster getMaterialRequestMasterId() {
		return materialRequestMasterId;
	}

	public void setMaterialRequestMasterId(MaterialRequestMaster materialRequestMasterId) {
		this.materialRequestMasterId = materialRequestMasterId;
	}

	public MaterialRequestHistory getMaterialRequestHistoryId() {
		return materialRequestHistoryId;
	}

	public void setMaterialRequestHistoryId(MaterialRequestHistory materialRequestHistoryId) {
		this.materialRequestHistoryId = materialRequestHistoryId;
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

	public String getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}

	public Double getIssuedWeight() {
		return issuedWeight;
	}

	public void setIssuedWeight(Double issuedWeight) {
		this.issuedWeight = issuedWeight;
	}

	@Override
	public String toString() {
		return "IssueMaterialRequestMaster [materialRequestMasterId=" + materialRequestMasterId
				+ ", materialRequestHistoryId=" + materialRequestHistoryId + ", issuerId=" + issuerId
				+ ", issuedQuantity=" + issuedQuantity + ", issuedWeight=" + issuedWeight + ", issuedDate=" + issuedDate
				+ ", issueStatus=" + issueStatus + "]";
	}



	
	
}

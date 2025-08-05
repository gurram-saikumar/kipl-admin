package com.kipl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "BILL_OF_MATERIAL_MASTER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillOfMaterialMaster extends BaseEntity{

		private static final long serialVersionUID = 1L;

		public BillOfMaterialMaster() {

		}

		public BillOfMaterialMaster(Long id) {
			this.id = id;
		}

		public BillOfMaterialMaster(String id) {
			this.id = Long.parseLong(id);
		}

		@Column(name = "PROJECT")
		private String project;
		
		@Column(name = "JOB_ID")
		private String jobId;
		
		@Column(name = "AB_AND_TEMPLATE")
		private Double abAndTemplate;
		
		@Column(name = "BUILD_UP")
		private Double buildUp;
		
		@Column(name = "COLD_FORM")
		private Double coldForm;
		
		@Column(name = "HOT_ROLLED")
		private Double hotRolled;
		
		@Column(name = "MISC")
		private Double misc;
		
		@Column(name = "SSR_SHEETING")
		private Double ssrSheeting;
		
		@Column(name = "KR_ROOF_SHEETING")
		private Double krRoofSheeting;
		
		@Column(name = "KR_WALL_SHEETING")
		private Double krWallSheeting;
		
		@Column(name = "FLASHINGS")
		private Double flashings;
		
		@Column(name = "DECK_SHEET")
		private Double deckSheet;
		
		@Column(name = "BUY_OUTS")
		private Double buyOuts;
		
		@Column(name = "TOTAL_WEIGHT_IN_MT")
		private Double totalWeightInMT;
		
		@Column(name = "PUFF_PANEL_AREA")
		private Double puffPanelArea;
		
		@Column(name = "RED_SHEET_SURFACE_AREA")
		private Double redSheetSurfaceArea;

		public String getProject() {
			return project;
		}

		public void setProject(String project) {
			this.project = project;
		}

		public String getJobId() {
			return jobId;
		}

		public void setJobId(String jobId) {
			this.jobId = jobId;
		}

		public Double getAbAndTemplate() {
			return abAndTemplate;
		}

		public void setAbAndTemplate(Double abAndTemplate) {
			this.abAndTemplate = abAndTemplate;
		}

		public Double getBuildUp() {
			return buildUp;
		}

		public void setBuildUp(Double buildUp) {
			this.buildUp = buildUp;
		}

		public Double getColdForm() {
			return coldForm;
		}

		public void setColdForm(Double coldForm) {
			this.coldForm = coldForm;
		}

		public Double getHotRolled() {
			return hotRolled;
		}

		public void setHotRolled(Double hotRolled) {
			this.hotRolled = hotRolled;
		}

		public Double getMisc() {
			return misc;
		}

		public void setMisc(Double misc) {
			this.misc = misc;
		}

		public Double getSsrSheeting() {
			return ssrSheeting;
		}

		public void setSsrSheeting(Double ssrSheeting) {
			this.ssrSheeting = ssrSheeting;
		}

		public Double getKrRoofSheeting() {
			return krRoofSheeting;
		}

		public void setKrRoofSheeting(Double krRoofSheeting) {
			this.krRoofSheeting = krRoofSheeting;
		}

		public Double getKrWallSheeting() {
			return krWallSheeting;
		}

		public void setKrWallSheeting(Double krWallSheeting) {
			this.krWallSheeting = krWallSheeting;
		}

		public Double getFlashings() {
			return flashings;
		}

		public void setFlashings(Double flashings) {
			this.flashings = flashings;
		}

		public Double getDeckSheet() {
			return deckSheet;
		}

		public void setDeckSheet(Double deckSheet) {
			this.deckSheet = deckSheet;
		}

		public Double getBuyOuts() {
			return buyOuts;
		}

		public void setBuyOuts(Double buyOuts) {
			this.buyOuts = buyOuts;
		}

		public Double getTotalWeightInMT() {
			return totalWeightInMT;
		}

		public void setTotalWeightInMT(Double totalWeightInMT) {
			this.totalWeightInMT = totalWeightInMT;
		}

		public Double getPuffPanelArea() {
			return puffPanelArea;
		}

		public void setPuffPanelArea(Double puffPanelArea) {
			this.puffPanelArea = puffPanelArea;
		}

		public Double getRedSheetSurfaceArea() {
			return redSheetSurfaceArea;
		}

		public void setRedSheetSurfaceArea(Double redSheetSurfaceArea) {
			this.redSheetSurfaceArea = redSheetSurfaceArea;
		}

		@Override
		public String toString() {
			return "BillOfMaterialMaster [project=" + project + ", jobId=" + jobId + ", abAndTemplate=" + abAndTemplate
					+ ", buildUp=" + buildUp + ", coldForm=" + coldForm + ", hotRolled=" + hotRolled + ", misc=" + misc
					+ ", ssrSheeting=" + ssrSheeting + ", krRoofSheeting=" + krRoofSheeting + ", krWallSheeting="
					+ krWallSheeting + ", flashings=" + flashings + ", deckSheet=" + deckSheet + ", buyOuts=" + buyOuts
					+ ", totalWeightInMT=" + totalWeightInMT + ", puffPanelArea=" + puffPanelArea
					+ ", redSheetSurfaceArea=" + redSheetSurfaceArea + "]";
		}

}

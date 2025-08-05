package com.kipl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BOMDto {

	private String project;
	private String jobId;
	private Double abAndTemplate;
	private Double buildUp;
	private Double coldForm;
	private Double hotRolled;
	private Double misc;
	private Double ssrSheeting;
	private Double krRoofSheeting;
	private Double krWallSheeting;
	private Double flashings;
	private Double deckSheet;
	private Double buyOuts;
	private Double totalWeightInMT;
	private Double puffPanelArea;
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
		return "BOMDto [project=" + project + ", jobId=" + jobId + ", abAndTemplate=" + abAndTemplate + ", buildUp="
				+ buildUp + ", coldForm=" + coldForm + ", hotRolled=" + hotRolled + ", misc=" + misc + ", ssrSheeting="
				+ ssrSheeting + ", krRoofSheeting=" + krRoofSheeting + ", krWallSheeting=" + krWallSheeting
				+ ", flashings=" + flashings + ", deckSheet=" + deckSheet + ", buyOuts=" + buyOuts
				+ ", totalWeightInMT=" + totalWeightInMT + ", puffPanelArea=" + puffPanelArea + ", redSheetSurfaceArea="
				+ redSheetSurfaceArea + "]";
	}
	
	
}

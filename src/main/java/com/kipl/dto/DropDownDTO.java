package com.kipl.dto;

public class DropDownDTO {
	private Long id;
	private String name;
	private String availableQuantity;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(String availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	@Override
	public String toString() {
		return "DropDownDTO [id=" + id + ", name=" + name + ", availableQuantity=" + availableQuantity + "]";
	}
	
	
}
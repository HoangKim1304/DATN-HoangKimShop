package com.shoppingwithme.hoangkimshop.dto;

public class FormSupplier {

	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String name;
	private String address;
	private String logo;
	private String phoneNumber;
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getLogo() {
		return logo;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

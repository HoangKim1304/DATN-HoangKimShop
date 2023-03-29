package com.shoppingwithme.hoangkimshop.dto.response;


public class ProductDTOResponseUser {

	private int id;
	private String name;
	private Double price;
	private String thumbnail;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Double getPrice() {
		return price;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}

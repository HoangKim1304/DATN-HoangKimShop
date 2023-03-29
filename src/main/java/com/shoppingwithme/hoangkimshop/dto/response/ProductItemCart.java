package com.shoppingwithme.hoangkimshop.dto.response;

public class ProductItemCart {

	private int id;
	private String name;
	private int status;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getStatus() {
		return status;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	private double price;
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}

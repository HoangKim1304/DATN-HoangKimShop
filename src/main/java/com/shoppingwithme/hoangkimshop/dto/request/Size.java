package com.shoppingwithme.hoangkimshop.dto.request;


public class Size {

	private Long id;
	private int quantity;
	public Long getId() {
		return id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Size [id=" + id + ", quantity=" + quantity + ", name=" + name + "]";
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

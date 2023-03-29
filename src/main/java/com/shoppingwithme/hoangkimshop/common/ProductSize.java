package com.shoppingwithme.hoangkimshop.common;

public class ProductSize {

	private Long size_id;
	public Long getSize_id() {
		return size_id;
	}
	public void setSize_id(Long size_id) {
		this.size_id = size_id;
	}
	private int quantity;
	public int getQuantity() {
		return quantity;
	}
	@Override
	public String toString() {
		return "ProductSize [size_id=" + size_id + ", quantity=" + quantity + "]";
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

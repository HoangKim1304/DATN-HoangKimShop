package com.shoppingwithme.hoangkimshop.dto.response;

import java.util.Objects;

public class ProductSizeDtOResponse {

	private Long size_id;
	private int product_id;
	@Override
	public int hashCode() {
		return Objects.hash(product_id, size_id);
	}
	@Override
	public boolean equals(Object obj) {
		
		ProductSizeDtOResponse other = (ProductSizeDtOResponse) obj;
		return product_id == other.product_id && Objects.equals(size_id, other.size_id);
	}
	public Long getSize_id() {
		return size_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	@Override
	public String toString() {
		return "ProductSizeDtOResponse [size_id=" + size_id + ", product_id=" + product_id + ", quantity=" + quantity
				+ ", nameSize=" + nameSize + ", id=" + id + "]";
	}
	public int getQuantity() {
		return quantity;
	}
	public void setSize_id(Long size_id) {
		this.size_id = size_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int quantity;
	private String nameSize;
	public String getNameSize() {
		return nameSize;
	}
	public void setNameSize(String nameSize) {
		this.nameSize = nameSize;
	}
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

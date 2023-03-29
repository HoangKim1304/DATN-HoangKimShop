package com.shoppingwithme.hoangkimshop.dto.request;

public class ProductDTORequest {

	private String name;
	private String category_id;
	private String supplier_id;
	public String getName() {
		return name;
	}
	public String getCategory_id() {
		return category_id;
	}
	public String getSupplier_id() {
		return supplier_id;
	}
	@Override
	public String toString() {
		return "ProductDTORequest [name=" + name + ", category_id=" + category_id + ", supplier_id=" + supplier_id
				+ "]";
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}
}

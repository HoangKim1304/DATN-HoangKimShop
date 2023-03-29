package com.shoppingwithme.hoangkimshop.dto.response;

public class ProductDTOResponse {

	private String name;
	private Double origanalprice;
	private Double price;
	public String getName() {
		return name;
	}
	public Double getOriganalprice() {
		return origanalprice;
	}
	public Double getPrice() {
		return price;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getCategory() {
		return category;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOriganalprice(Double origanalprice) {
		this.origanalprice = origanalprice;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	private String supplier;
	private String category;
	private int sumQuantityByProductId;
	private int sumQuantitySoldeByProductId;
	public int getSumQuantityByProductId() {
		return sumQuantityByProductId;
	}
	public int getSumQuantitySoldeByProductId() {
		return sumQuantitySoldeByProductId;
	}
	public void setSumQuantityByProductId(int sumQuantityByProductId) {
		this.sumQuantityByProductId = sumQuantityByProductId;
	}
	public void setSumQuantitySoldeByProductId(int sumQuantitySoldeByProductId) {
		this.sumQuantitySoldeByProductId = sumQuantitySoldeByProductId;
	}
	
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}

package com.shoppingwithme.hoangkimshop.dto.response;

public class CartItem {

	private int quantity;
	private double totalPrice;
	private ProductItemCart productItemCart;
	public int getQuantity() {
		return quantity;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public ProductItemCart getProductItemCart() {
		return productItemCart;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setProductItemCart(ProductItemCart productItemCart) {
		this.productItemCart = productItemCart;
	}
	public CartItem(int quantity, double totalPrice, ProductItemCart productItemCart) {
		super();
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.productItemCart = productItemCart;
	}
	
	public CartItem() {
		
	}
	
	
	
}

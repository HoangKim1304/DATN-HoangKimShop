package com.shoppingwithme.hoangkimshop.dto.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;

@Repository
public class Cart {

	@Autowired
	private ProductServiceImpl productService;
	
	public HashMap<Integer, CartItem> addCart(int productid, HashMap<Integer, CartItem> listCartItem) {
		CartItem cartItem= new CartItem();
		ProductEntity product=productService.findById(productid);
		ProductItemCart productItemCart= new ProductItemCart();
		if(product !=null && listCartItem.containsKey(productid)) {
			
			cartItem=listCartItem.get(productid);
			cartItem.setQuantity(cartItem.getQuantity()+1);
			cartItem.setTotalPrice(cartItem.getQuantity()*product.getPrice());
			
			productItemCart.setStatus(product.getStatus());
			
			
			listCartItem.put(productid, cartItem);
			
		}else if(product !=null) {
			productItemCart.setId(productid);
			productItemCart.setName(product.getName());
			productItemCart.setStatus(product.getStatus());
			productItemCart.setPrice(product.getPrice());
			
			cartItem.setProductItemCart(productItemCart);
			cartItem.setQuantity(1);
			cartItem.setTotalPrice(product.getPrice());
			
			listCartItem.put(productid, cartItem);
		}
				
		return listCartItem;
	}
	
	public HashMap<Integer, CartItem> updateCart(int productid, int quantity ,HashMap<Integer, CartItem> listCartItem) {
		
		if(listCartItem==null) {
			return listCartItem;
		}
		CartItem cartItem= new CartItem();
		if(listCartItem.containsKey(productid)) {
			cartItem= listCartItem.get(productid);
			cartItem.setQuantity(quantity);
			cartItem.setTotalPrice(cartItem.getQuantity()*cartItem.getProductItemCart().getPrice());
			
			listCartItem.put(productid, cartItem);
			
		}

		return listCartItem;
	}
	
	public HashMap<Integer, CartItem> deleteCart(int productid, HashMap<Integer, CartItem> listCartItem) {
	
		if(listCartItem==null)
		{
			return listCartItem;
		}
		if(listCartItem.containsKey(productid)) {
				
			listCartItem.remove(productid);
				
		}
	
			return listCartItem;
	}
	
	public int totalQuantity(HashMap<Integer, CartItem> listCartItem) {
		int totalQuantity=0;
		for(Map.Entry<Integer, CartItem>cartItem :listCartItem.entrySet()) {
			totalQuantity+=cartItem.getValue().getQuantity();
		}
		return totalQuantity;
		
	}
	
	public int totalPrice(HashMap<Integer, CartItem> listCartItem) {
		int totalPrice=0;
		for(Map.Entry<Integer, CartItem>cartItem :listCartItem.entrySet()) {
			totalPrice+=cartItem.getValue().getTotalPrice();
		}
		return totalPrice;
		
	}
}

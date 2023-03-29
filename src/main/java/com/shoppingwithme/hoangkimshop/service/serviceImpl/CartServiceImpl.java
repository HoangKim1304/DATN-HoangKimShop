package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.dto.response.Cart;
import com.shoppingwithme.hoangkimshop.dto.response.CartItem;
import com.shoppingwithme.hoangkimshop.service.ICartService;

@Service
public class CartServiceImpl implements ICartService{

	@Autowired
	private Cart cart_reop;

	@Override
	public HashMap<Integer, CartItem> addCart(int productid, HashMap<Integer, CartItem> listCartItem) {
		return cart_reop.addCart(productid, listCartItem);
	}

	@Override
	public HashMap<Integer, CartItem> updateCart(int productid, int quantity, HashMap<Integer, CartItem> listCartItem) {
		return cart_reop.updateCart(productid, quantity, listCartItem);
	}

	@Override
	public HashMap<Integer, CartItem> deleteCart(int productid, HashMap<Integer, CartItem> listCartItem) {
		return cart_reop.deleteCart(productid, listCartItem);
	}

	@Override
	public int totalQuantity(HashMap<Integer, CartItem> listCartItem) {
		return cart_reop.totalPrice(listCartItem);
	}

	@Override
	public int totalPrice(HashMap<Integer, CartItem> listCartItem) {
		return cart_reop.totalPrice(listCartItem);
	}
}

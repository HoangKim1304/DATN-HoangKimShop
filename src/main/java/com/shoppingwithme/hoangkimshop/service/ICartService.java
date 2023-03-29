package com.shoppingwithme.hoangkimshop.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.dto.response.CartItem;

@Service
public interface ICartService {

	public HashMap<Integer, CartItem> addCart(int productid, HashMap<Integer, CartItem> listCartItem);
	public HashMap<Integer, CartItem> updateCart(int productid, int quantity ,HashMap<Integer, CartItem> listCartItem);
	public HashMap<Integer, CartItem> deleteCart(int productid, HashMap<Integer, CartItem> listCartItem);
	public int totalQuantity(HashMap<Integer, CartItem> listCartItem);
	public int totalPrice(HashMap<Integer, CartItem> listCartItem);
}

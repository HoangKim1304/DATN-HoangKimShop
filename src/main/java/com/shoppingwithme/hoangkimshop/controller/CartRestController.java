package com.shoppingwithme.hoangkimshop.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingwithme.hoangkimshop.dto.response.CartItem;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.CartServiceImpl;

@RestController
public class CartRestController {
	@Autowired
	private CartServiceImpl cartService;
	@GetMapping("/shopping-with-me/cart/add-item/{productid}")
	public int addItemToCart(HttpSession session, @PathVariable(name = "productid") int productid, Model model) {
		HashMap<Integer, CartItem> listCartItem=(HashMap<Integer, CartItem>) session.getAttribute("listCartItem");
		
		if(listCartItem==null) {
			listCartItem= new HashMap<>();
			
		}
		listCartItem=cartService.addCart(productid, listCartItem);
		session.setAttribute("listCartItem", listCartItem);
		session.setAttribute("sizecart", listCartItem.size());
		model.addAttribute("cart", listCartItem);
//		model.addAttribute("sizecart", listCartItem.size());
		return listCartItem.size();
	}
}

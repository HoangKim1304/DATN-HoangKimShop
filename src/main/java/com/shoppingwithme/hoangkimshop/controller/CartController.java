package com.shoppingwithme.hoangkimshop.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {


	
	@Secured("USER")
	@GetMapping("/shopping-with-me/cart")
	public String gePageListCart() {
		return "shop-cart";
	}
	
	
	
}

package com.shoppingwithme.hoangkimshop.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@Secured("ADMIN") 
	@GetMapping("/admin")
	public String getHomePageAdmin()
	{
		
			return "admin/index";
		
		
	}
}

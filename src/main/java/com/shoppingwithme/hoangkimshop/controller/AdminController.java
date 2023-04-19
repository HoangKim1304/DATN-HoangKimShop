package com.shoppingwithme.hoangkimshop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.shoppingwithme.hoangkimshop.security.MyUserDeatils;

@Controller
public class AdminController {

	@Autowired
	private HttpSession session;
	@Secured("ADMIN") 
	@GetMapping("/admin")
	public String getHomePageAdmin(@AuthenticationPrincipal MyUserDeatils user)
	{
			if(user !=null) {
			
			session.setAttribute("userId", user.getInforUser().getId());
			session.setAttribute("username", user.getUsername());
			System.out.println("username: "+user.getUsername());
			}
			return "admin/index";
		
		
	}
}

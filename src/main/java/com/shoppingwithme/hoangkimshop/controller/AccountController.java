package com.shoppingwithme.hoangkimshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingwithme.hoangkimshop.service.serviceImpl.UserService;

@RestController
public class AccountController {

	/*
	 * @Autowired private MyUserDeatils myUserDeatils;
	 * @AuthenticationPrincipal MyUserDeatils user
	 */
	@Autowired
	private UserService userService;
	
	@GetMapping("/shopping-with-me/users/exists-username/{username}")
	public boolean existsByUsername(@PathVariable(name = "username") String username) {
		System.out.println("Uername: "+username);
		return userService.existsByUsername(username);
		
		
	}
}

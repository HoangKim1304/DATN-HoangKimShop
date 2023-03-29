package com.shoppingwithme.hoangkimshop.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shoppingwithme.hoangkimshop.common.UploadFile;
import com.shoppingwithme.hoangkimshop.dto.FormRegisterCustomer;
import com.shoppingwithme.hoangkimshop.entity.UserEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.RoleServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userservice;

	@Autowired
	private RoleServiceImpl roleService;

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/register")
	public String register(@ModelAttribute("checkUsername") String checkUsername,
							@ModelAttribute("checkEmail") String checkEmail,
							@ModelAttribute("checkPhone") String checkPhone,
							Model model) {
		 if(checkUsername !=null || checkUsername !="") {
			 model.addAttribute("checkUsername", checkUsername);
		 }
		 if(checkEmail !=null || checkEmail !="") {
			 model.addAttribute("checkEmail", checkEmail);
		 }
		 if(checkPhone !=null || checkPhone !="") {
			 model.addAttribute("checkPhone", checkPhone);
		 }
		 
		return "register";
	}

	@PostMapping("/shopping-with-me/users/exists-username")
	public String register(@ModelAttribute(name = "formRegisterCustomer") FormRegisterCustomer formRegisterCustomer,
			@RequestParam("fileavata") MultipartFile fileavata, RedirectAttributes ra)
			throws IOException, ParseException {

		
		boolean checkValiadte=false;
		if(userservice.existsByUsername(formRegisterCustomer.getUsername())) {
			ra.addFlashAttribute("checkUsername","Username was used");
			
			checkValiadte=true;
		}
		if(userservice.existsByEmail(formRegisterCustomer.getEmail())) {
			ra.addFlashAttribute("checkEmail","Email was used");
			
			
			checkValiadte=true;
		}
		if(userservice.existsByPhonenumber(formRegisterCustomer.getPhoneNumber())) {
			ra.addFlashAttribute("checkPhone","Phonenumber was used");
			
			
			checkValiadte=true;
		}
		if(checkValiadte) {
			
			return "redirect:/register";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date parsed = format.parse(formRegisterCustomer.getBirthday());

		ModelMapper mapper = new ModelMapper();

		UserEntity newUser = mapper.map(formRegisterCustomer, UserEntity.class);
		newUser.setBirthday(parsed);
		newUser.setAvata(fileavata.getOriginalFilename());
		newUser.setRoles(roleService.getListRole("USER"));
		Date date = new Date();
		System.out.println(date);
		newUser.setCreateday(date);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		newUser.setPassword(passwordEncoder.encode(formRegisterCustomer.getPassword()));
		
		if (userservice.saveUser(newUser)) {
			Boolean Flag = UploadFile.uploadImage(fileavata);
			if (!Flag)
				return "ErrorPage";
			else
				return "index";
		} else {
			return "ErrorPage";
		}

	}
}

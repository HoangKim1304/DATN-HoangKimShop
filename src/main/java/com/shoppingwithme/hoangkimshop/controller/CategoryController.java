package com.shoppingwithme.hoangkimshop.controller;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoppingwithme.hoangkimshop.dto.request.FormCategory;
import com.shoppingwithme.hoangkimshop.entity.ProductCategoryEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductCategoryServiceImpl;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private ProductCategoryServiceImpl service;
	@GetMapping("/categories")
	public String Categories(Model model)
	{
		model.addAttribute("listCategory", service.getAllCategory());
		return "admin/ManageCategory";
	}
	
	@PostMapping("/category")
	public String saveCategory(@ModelAttribute(name = "category") FormCategory category) {
		ModelMapper mapper=new ModelMapper();
		
		ProductCategoryEntity entity=mapper.map(category, ProductCategoryEntity.class);
		entity.setCreateday(new Date());
		entity.setDeleteflag(false);
		entity.setModifydate(new Date());
		
		ProductCategoryEntity check=  service.saveCategory(entity);
		if(check!=null)
		{
			return "admin/index";
		}else {
			return "ErrorPage";
		}
		
	}
}

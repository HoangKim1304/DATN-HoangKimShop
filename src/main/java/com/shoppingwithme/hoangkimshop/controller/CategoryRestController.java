package com.shoppingwithme.hoangkimshop.controller;

import com.shoppingwithme.hoangkimshop.dto.request.FormCategory;
import com.shoppingwithme.hoangkimshop.entity.ProductCategoryEntity;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductCategoryServiceImpl;

@RestController
public class CategoryRestController {

	@Autowired
	private ProductCategoryServiceImpl categoryService;
	
	@GetMapping("/admin/category/list")
	public List<ProductCategoryEntity> getAllCategory(){
		System.out.println("Size of list: "+categoryService.getAllCategory().size());
		return categoryService.getAllCategory();
	}
	
	@PostMapping("/admin/product-category")
	public String saveCategory(@ModelAttribute("category") FormCategory category)
	{
		ProductCategoryEntity entity= new ProductCategoryEntity();
		
		ModelMapper mapper=new ModelMapper();
		
		entity=mapper.map(category, ProductCategoryEntity.class);
		entity.setCreateday(new Date());
		entity.setDeleteflag(false);
		entity.setModifydate(new Date());
		categoryService.saveCategory(entity);
		return "{\"status\":\"success\"}";
	}
	
	@PostMapping("/admin/product-category/{id}")
	public String updateCategory(@PathVariable(name = "id") Long id,@ModelAttribute("category") FormCategory category) {

		ProductCategoryEntity entity= new ProductCategoryEntity();
		
		ModelMapper mapper=new ModelMapper();
		entity=mapper.map(category, ProductCategoryEntity.class);
		entity.setModifydate(new Date());
		return "{\"status\":\"success\"}";
	}
	
	@GetMapping("/admin/product-category/delete/{categoryId}")
	public boolean updateDeleteFlage(@PathVariable(name = "categoryId") Long categoryId) {
		System.out.println("Cate gory id update delete flag: "+categoryId);
		ProductCategoryEntity deleteCategory=categoryService.getCategorybyId(categoryId).get();
		deleteCategory.setDeleteflag(true);
		ProductCategoryEntity check=categoryService.saveCategory(deleteCategory);
		if(check!=null)
			return true;
		return false;
		
	}
}

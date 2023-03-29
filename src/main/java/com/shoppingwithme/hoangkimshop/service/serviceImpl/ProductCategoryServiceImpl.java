package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.entity.ProductCategoryEntity;
import com.shoppingwithme.hoangkimshop.repository.CategoryProductRepository;
import com.shoppingwithme.hoangkimshop.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	private CategoryProductRepository reop;

	@Override
	public List<ProductCategoryEntity> getAllCategory() {
		return reop.findAll();
	}


	@Override
	public ProductCategoryEntity saveCategory(ProductCategoryEntity categoryEntity) {
		return reop.save(categoryEntity);
	}


	@Override
	public Optional<ProductCategoryEntity> getCategorybyId(Long id) {
		return reop.findById(id);
	}
}

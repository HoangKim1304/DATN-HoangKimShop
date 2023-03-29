package com.shoppingwithme.hoangkimshop.service;

import java.util.List;
import java.util.Optional;

import com.shoppingwithme.hoangkimshop.entity.ProductCategoryEntity;

public interface ProductCategoryService {

	List<ProductCategoryEntity> getAllCategory();
	ProductCategoryEntity saveCategory(ProductCategoryEntity categoryEntity);
	Optional<ProductCategoryEntity> getCategorybyId(Long id);
}

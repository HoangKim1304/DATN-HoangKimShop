package com.shoppingwithme.hoangkimshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shoppingwithme.hoangkimshop.entity.ProductEntity;

public interface ProductService {

	public ProductEntity saveProduct(ProductEntity entity);
	Page<ProductEntity> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
	Page<ProductEntity> findAll( Pageable pageable);
	
	int countByCategory_id(Long category_id);
	 
	int countBySupplier_id(Long supplier_id);
	
	Page<ProductEntity> findByStatus(int status, Pageable pageable);
	List<ProductEntity> findByStatus(int status);
	
	ProductEntity findById(int id);
}

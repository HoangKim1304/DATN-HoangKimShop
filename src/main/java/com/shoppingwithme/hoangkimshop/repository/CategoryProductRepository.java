package com.shoppingwithme.hoangkimshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingwithme.hoangkimshop.entity.ProductCategoryEntity;

@Repository
public interface CategoryProductRepository extends JpaRepository<ProductCategoryEntity, Long>{

	
	
}

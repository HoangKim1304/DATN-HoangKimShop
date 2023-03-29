package com.shoppingwithme.hoangkimshop.service;

import java.util.List;

import com.shoppingwithme.hoangkimshop.entity.ProductSizeEntity;

public interface ProductSizeService {

	public ProductSizeEntity saveProductSizeEntity( ProductSizeEntity entity);
	 
	 int getQuantityByProduct( int productid);

	 int getQuantitySoldByProduct( int productid);
	 
	 List<ProductSizeEntity> findByProductid(int id);
	 
	 ProductSizeEntity findById(int id);
	 
	 void deleteById(int id);
	 
	 void deleteAllByProductid (int id);
	 int deleteByProductid(int productId);
	
}

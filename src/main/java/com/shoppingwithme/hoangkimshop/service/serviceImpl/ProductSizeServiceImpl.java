package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.entity.ProductSizeEntity;
import com.shoppingwithme.hoangkimshop.repository.ProductSizeReponsitory;
import com.shoppingwithme.hoangkimshop.service.ProductSizeService;

@Service
public class ProductSizeServiceImpl implements ProductSizeService{

	@Autowired
	private ProductSizeReponsitory reop;
	@Override
	public ProductSizeEntity saveProductSizeEntity(ProductSizeEntity entity) {
		return reop.save(entity);
	}
	@Override
	public int getQuantityByProduct(int productid) {
		int sumQuantityByProductId=0;
		if(reop.getQuantityByProduct(productid) !=null) {
			sumQuantityByProductId=reop.getQuantityByProduct(productid);
		}
		return sumQuantityByProductId;
	}
	@Override
	public int getQuantitySoldByProduct(int productid) {
		int sumQuantitySoldeByProductId=0;
		if(reop.getQuantitySoldByProduct(productid) !=null)
		{
			sumQuantitySoldeByProductId=reop.getQuantitySoldByProduct(productid);
		}
		return sumQuantitySoldeByProductId;
	}
	@Override
	public List<ProductSizeEntity> findByProductid(int id) {
		return reop.findByProductid(id);
	}
	@Override
	public ProductSizeEntity findById(int id) {
		return reop.findById(id).get();
	}
	@Override
	public void deleteById(int id) {
		
		reop.deleteById(id);
		
	}
	@Override
	public  void deleteAllByProductid(int id) {
		 reop.deleteAllByProductid(id);
		
	}
	@Override
	public int deleteByProductid(int productId) {
		try {
			reop.deleteByProductid(productId);
			return 0;
		} catch (Exception e) {
			return 1;
		}
		
		
	}
	
	

}

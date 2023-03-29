package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.repository.ProductPageableSort;
import com.shoppingwithme.hoangkimshop.repository.ProductRepository;
import com.shoppingwithme.hoangkimshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository reop;
	
	@Autowired
	private ProductPageableSort reopPaging;
	@Override
	public ProductEntity saveProduct(ProductEntity entity) {
		return reop.save(entity);
	}
	@Override
	public Page<ProductEntity> findByNameContainingIgnoreCase(String keyword, Pageable pageable) {
		
		return reop.findByNameContainingIgnoreCase(keyword, pageable);
	}
	@Override
	public Page<ProductEntity> findAll(Pageable pageable) {
		
		return reopPaging.findAll(pageable);
	}
	@Override
	public int countByCategory_id(Long category_id) {
		return reop.countByCategoryid(category_id);
	}
	@Override
	public int countBySupplier_id(Long supplier_id) {
		return reop.countBySupplier_id(supplier_id);
	}
	@Override
	public Page<ProductEntity> findByStatus(int status, Pageable pageable) {
		return reop.findByStatus(status, pageable);
	}
	@Override
	public List<ProductEntity> findByStatus(int status) {
		return reop.findByStatus(status);
	}
	@Override
	public ProductEntity findById(int id) {
		// TODO Auto-generated method stub
		return reop.findById(id).get();
	}
	

	
}

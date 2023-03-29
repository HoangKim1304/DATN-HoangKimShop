package com.shoppingwithme.hoangkimshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoppingwithme.hoangkimshop.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

	 Page<ProductEntity> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
	 
	 @Query(value = "SELECT COUNT(*) FROM product p WHERE p.category_id = :category_id", nativeQuery = true)
	 int countByCategoryid(@Param("category_id") Long category_id);
	 
	 @Query(value = "SELECT COUNT(*) FROM product p WHERE p.supplier_id = :supplier_id", nativeQuery = true)
	 int countBySupplier_id(@Param("supplier_id") Long supplier_id);
	 
	 Page<ProductEntity> findByStatus(int status, Pageable pageable);
	 
	 List<ProductEntity> findByStatus(int status);

	 
}

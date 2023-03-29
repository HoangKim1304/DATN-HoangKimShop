package com.shoppingwithme.hoangkimshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoppingwithme.hoangkimshop.entity.ProductSizeEntity;

@Repository
public interface ProductSizeReponsitory extends JpaRepository<ProductSizeEntity, Integer>{

	 @Query(value = "SELECT SUM(quantity) FROM productsize ps WHERE ps.productid = :productid", nativeQuery = true)
	 Integer getQuantityByProduct( @Param("productid") int productid);
	 
	 @Query(value = "SELECT SUM(quantitysold) FROM productsize ps WHERE ps.productid = :productid", nativeQuery = true)
	 Integer getQuantitySoldByProduct( @Param("productid") int productid);
	 
	 List<ProductSizeEntity> findByProductid(int id);
	 
	 void deleteAllByProductid (int id);
	 
	 @Query(value = "DELETE FROM productsize WHERE productsize.productid =:productid", nativeQuery = true)
	 void deleteByProductid(@Param("productid") int productid);
	 
	 
}

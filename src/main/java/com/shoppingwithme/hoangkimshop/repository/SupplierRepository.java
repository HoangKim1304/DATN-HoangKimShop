package com.shoppingwithme.hoangkimshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoppingwithme.hoangkimshop.entity.SupplierEntity;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long>{

	@Query(nativeQuery = true, value =  "select * from supplier where supplier.deleteflag=0")
	List<SupplierEntity> findByDeleteflagIsFalse();
	
	@Query(nativeQuery = true, value =  "select * from supplier where supplier.deleteflag=1")
	List<SupplierEntity> findByDeleteflagIsTrue();
	
	
	@Query(nativeQuery = true, value =  "update supplier set supplier.deleteflag=1 where supplier.id =:id")
	SupplierEntity changeStatusOffSupplier(@Param("id") Long id);
	
	@Query(nativeQuery = true, value =  "update supplier set supplier.deleteflag=0 where supplier.id =:id")
	SupplierEntity changeStatusOnSupplier(@Param("id") Long id);
}

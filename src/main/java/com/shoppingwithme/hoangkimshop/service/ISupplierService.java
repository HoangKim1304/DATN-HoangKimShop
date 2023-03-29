package com.shoppingwithme.hoangkimshop.service;

import java.util.List;
import java.util.Optional;

import com.shoppingwithme.hoangkimshop.entity.SupplierEntity;

public interface ISupplierService {

	List<SupplierEntity> listAllSupplierEntity();
	List<SupplierEntity> findByDeleteflagIsFalse();
	Optional<SupplierEntity> getSupplierById(Long id);
	SupplierEntity changeStatusOnSupplier(Long id);
	SupplierEntity changeStatusOffSupplier(Long id);
	List<SupplierEntity> findByDeleteflagIsTrue();
}

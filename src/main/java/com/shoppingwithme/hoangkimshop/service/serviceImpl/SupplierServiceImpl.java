package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.entity.SupplierEntity;
import com.shoppingwithme.hoangkimshop.repository.SupplierRepository;
import com.shoppingwithme.hoangkimshop.service.ISupplierService;

@Service
public class SupplierServiceImpl implements ISupplierService{

	@Autowired
	private SupplierRepository reop;
	
	public SupplierEntity saveSupplier(SupplierEntity supplier) {
		return reop.save(supplier);
	}

	@Override
	public List<SupplierEntity> listAllSupplierEntity() {
		return reop.findAll();
	}

	@Override
	public List<SupplierEntity> findByDeleteflagIsFalse() {
		return reop.findByDeleteflagIsFalse();
	}

	@Override
	public Optional<SupplierEntity> getSupplierById(Long long1) {
		return reop.findById(long1);
	}

	@Override
	public SupplierEntity changeStatusOnSupplier(Long id) {
		return reop.changeStatusOnSupplier(id);
	}

	@Override
	public SupplierEntity changeStatusOffSupplier(Long id) {
		return reop.changeStatusOffSupplier(id);
	}

	@Override
	public List<SupplierEntity> findByDeleteflagIsTrue() {
		return reop.findByDeleteflagIsTrue();
	}

	
}

package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.entity.SizeEntity;
import com.shoppingwithme.hoangkimshop.repository.SizeReonsitory;
import com.shoppingwithme.hoangkimshop.service.ISizeService;

@Service
public class SizeServiceImpl implements ISizeService{

	@Autowired
	private SizeReonsitory reop;
	@Override
	public List<SizeEntity> loadAllSize() {
		return reop.findAll();
	}

}

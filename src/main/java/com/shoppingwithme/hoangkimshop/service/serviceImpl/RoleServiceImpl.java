package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.entity.RoleEntity;
import com.shoppingwithme.hoangkimshop.repository.RoleReponsitory;

@Service
public class RoleServiceImpl {

	@Autowired
	private RoleReponsitory reponsitory;
	
	public Set<RoleEntity> getListRole(String roleName){
		List<Integer>lissId=Arrays.asList(reponsitory.findOneByName(roleName).getId());
		Set<RoleEntity> roles=new HashSet<>();
		roles=reponsitory.findAllByIdIn(lissId);
		return roles;
	}
}

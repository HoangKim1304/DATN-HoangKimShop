package com.shoppingwithme.hoangkimshop.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingwithme.hoangkimshop.entity.RoleEntity;

public interface RoleReponsitory extends JpaRepository<RoleEntity, Integer>{

	RoleEntity findOneByName(String roleName);
	Set<RoleEntity> findAllByIdIn(List<Integer> listId);
}

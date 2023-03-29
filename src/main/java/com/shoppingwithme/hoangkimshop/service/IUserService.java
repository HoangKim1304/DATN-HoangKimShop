package com.shoppingwithme.hoangkimshop.service;

import java.util.Optional;

import com.shoppingwithme.hoangkimshop.entity.UserEntity;

public interface IUserService {

	public Optional<UserEntity> findByUsername(String username);
	public Boolean existsByUsername(String username);
	
	
}

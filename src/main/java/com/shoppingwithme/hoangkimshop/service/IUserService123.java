package com.shoppingwithme.hoangkimshop.service;

import java.util.Optional;

import com.shoppingwithme.hoangkimshop.entity.UserEntity;

public interface IUserService123 {

	public Optional<UserEntity> findByUsername(String username);
	public Boolean existsByUsername(String username);
	public Boolean existsByEmail(String email);
	public Boolean existsByPhonenumber(String phonenumber);
}

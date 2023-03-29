package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.entity.UserEntity;
import com.shoppingwithme.hoangkimshop.repository.UserRepository;
import com.shoppingwithme.hoangkimshop.service.IUserService;

@Service
public class UserDeatilsServiceImpl implements IUserService{

	@Autowired
	private UserRepository repository;
	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}

	

	

}

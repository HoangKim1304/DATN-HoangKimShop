package com.shoppingwithme.hoangkimshop.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingwithme.hoangkimshop.entity.UserEntity;
import com.shoppingwithme.hoangkimshop.repository.UserRepository;
import com.shoppingwithme.hoangkimshop.service.IUserService123;

@Service
public class UserService implements IUserService123{

	@Autowired 
	private UserRepository repository;
	
	
	public Boolean saveUser(UserEntity user) {
		UserEntity u=repository.save(user);
		if(u ==null)
			return false;
		return true;
		
	}


	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return repository.findByUsername(username);
	}


	@Override
	public Boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}


	@Override
	public Boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}


	@Override
	public Boolean existsByPhonenumber(String phonenumber) {
		return repository.existsByPhonenumber(phonenumber);
		
	}

}

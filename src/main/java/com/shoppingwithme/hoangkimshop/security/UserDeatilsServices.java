package com.shoppingwithme.hoangkimshop.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shoppingwithme.hoangkimshop.entity.UserEntity;
import com.shoppingwithme.hoangkimshop.repository.UserRepository;

public class UserDeatilsServices implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user=repository.findByUsername(username);
		return user.map(MyUserDeatils::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}

}

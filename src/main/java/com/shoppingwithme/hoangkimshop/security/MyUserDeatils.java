package com.shoppingwithme.hoangkimshop.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shoppingwithme.hoangkimshop.entity.RoleEntity;
import com.shoppingwithme.hoangkimshop.entity.UserEntity;

public class MyUserDeatils implements UserDetails{

	/**
	 * 
	 */
	/* private static final long serialVersionUID = 1L; */
	private UserEntity user;
	
	public MyUserDeatils(UserEntity user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<RoleEntity>roles=user.getRoles();
		List<SimpleGrantedAuthority>authorities=new ArrayList<>();
		for(RoleEntity role:roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public UserEntity getInforUser() {
		return this.user;
	}
	
	

}

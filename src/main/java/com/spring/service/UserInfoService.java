package com.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.dto.UserInfoDetails;
import com.spring.entity.UserInfo;
import com.spring.repository.UserInfoRepository;

public class UserInfoService implements UserDetailsService {

	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo >userInfo = this.userInfoRepository.findByName(username);
		return userInfo.map(UserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found "+username));
	}
	
	public UserInfo getUserByUsername(String username) {
		
		Optional<UserInfo >userInfo = this.userInfoRepository.findByName(username);
		
		if(userInfo.isEmpty()) {
			return null;
		}
		return userInfo.get();
		
	}
	
	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(this.passwordEncoder.encode(userInfo.getPassword()));
		this.userInfoRepository.save(userInfo);
		return "User Added Successfully";
	}

}

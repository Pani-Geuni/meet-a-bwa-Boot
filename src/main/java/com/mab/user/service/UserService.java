package com.mab.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mab.user.model.UserVO;
import com.mab.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user = repository.findByUser_id(username);
		log.info("user:{}",user);
		
		if(user==null) throw new UsernameNotFoundException("Not founc account.");
		
		return user;
	}
	
	
	//로그인
	public UserVO user_login_info(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	//아이디 찾기-사용자 이메일 불러오기
	public UserVO user_email_select(UserVO uvo) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

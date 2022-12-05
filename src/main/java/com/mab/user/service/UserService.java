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
	
	//spring security - UserDetailsService
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("username:{}",username);
		UserVO user = repository.findByUser_id(username);
		log.info("user:{}",user);
		
		if(user==null) throw new UsernameNotFoundException("Not founc account.");
		
		return user;
	}
	
	//로그인
	public UserVO user_login_info(String username) {
		log.info("user_login_info()....");
		return repository.user_login_info(username);
	}

	//아이디 찾기-사용자 이메일 불러오기
	public UserVO user_email_select(UserVO uvo) {
		log.info("user_id_email_select()....");
		log.info("uvo: {}", uvo);
		return repository.user_email_select(uvo.getUser_email());
	}
	
	
	public UserVO select_user_info(String user_no) {
		UserVO vo = repository.SQL_SELECT_ONE(user_no);
		
		return vo;
	}

	//비밀번호 찾기 - 아이디 이메일 불러오기
	public UserVO user_id_email_select(UserVO uvo) {
		log.info("user_id_email_select()....");
		log.info("uvo: {}", uvo);
		return repository.user_id_email_select(uvo.getUser_id(), uvo.getUser_email());
	}

	//비밀번호 찾기 - 비번 초기화 저장
	public int user_pw_init(UserVO uvo) {
		log.info("user_pw_init()....");
		log.info("uvo: {}", uvo);

		return repository.user_pw_init(uvo.getPassword(), uvo.getUser_id());
	}
	
	
}//end class

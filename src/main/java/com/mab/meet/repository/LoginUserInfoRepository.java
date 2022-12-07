package com.mab.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.meet.model.LoginUserInfoVO;

public interface LoginUserInfoRepository extends JpaRepository<LoginUserInfoVO, Object>{

	@Query(nativeQuery = true, value = "select user_no, user_birth, user_gender from userinfo where user_no=?1")
	public LoginUserInfoVO select_one_now_login_user(String user_no);
}

/**
 * @author 강경석
*/

package com.mab.user.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import com.mab.user.model.UserVO;

public interface UserRepository extends JpaRepository<UserVO, Object> {

	//로그인을 위해 user정보 불러오기
	@Query(nativeQuery = true, value = "select * from userinfo where user_state='Y' and user_id= ?1")
	public UserVO findByUser_id(String user_id);
	
	//로그인
	@Query(nativeQuery = true, value = "SELECT * from userinfo where user_id=?1 and user_state !='N'")
	public UserVO user_login_info(String username);
	
	@Query(nativeQuery = true, value = "SELECT * from userinfo where user_no=?1")
	public UserVO SQL_SELECT_ONE(String user_no);
	
	//아이디 찾기 - 이메일 불러오기 
	@Query(nativeQuery = true, value = "select * from userinfo where user_state='Y' and user_email= ?1")
	public UserVO user_email_select(String user_email);
	

	//비밀번호 찾기 - 아이디 이메일 불러오기
	@Query(nativeQuery = true, value = "select * from userinfo where user_state='Y' and user_id=?1 and user_email=?2")
	public UserVO user_id_email_select(String user_id, String user_email);

	//비밀번호 찾기 - 비밀번호 저장
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update userinfo set user_pw=?1 where user_no=?2")
	public int user_pw_init(String password, String user_no);

	//회원가입 - 아이디 중복체크
	@Query(nativeQuery = true, value = "select * from userinfo where user_id=?1")
	public UserVO idCheckOK(String user_id);

	//마이페이지- 회원 정보 불러오기
	@Query(nativeQuery = true, value = "select * from userinfo where user_no=?1")
	public UserVO user_mypage_select(String user_no);


	
	
	
	
	
}//end class

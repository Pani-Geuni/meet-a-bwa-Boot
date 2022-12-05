/**
 * @author 강경석
*/

package com.mab.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.user.model.UserVO;

public interface UserRepository extends JpaRepository<UserVO, Object> {

	//로그인을 위해 
	@Query(nativeQuery = true, value = "select * from userinfo where user_state='Y' and user_id= ?1")
	public UserVO findByUser_id(String user_id);
	
	//로그인
	@Query(nativeQuery = true, value = "SELECT * from userinfo where user_id=?1 and user_state !='N'")
	public UserVO user_login_info(String username);

	@Query(nativeQuery = true, value = "SELECT * from userinfo where user_no=?1")
	public UserVO SQL_SELECT_ONE(String user_no);
	
	
	
	
}//end class

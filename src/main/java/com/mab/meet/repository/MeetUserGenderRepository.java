/**
 * @author 전판근
 */

package com.mab.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.meet.model.MeetInfoVO;
import com.mab.user.model.UserVO;

public interface MeetUserGenderRepository extends JpaRepository<UserVO, Object> {

	@Query(nativeQuery = true, 
			value="select * from meet_join_user_view where meet_no=?1")
	public MeetInfoVO select_one_meet_info(String meet_no);

	@Query(nativeQuery = true, value="select * from userinfo where user_no=?1")
	public UserVO select_one_meet_user_info(String user_no);
}

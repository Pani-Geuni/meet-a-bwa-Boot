package com.mab.meet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.meet.model.MeetUserVO;

public interface MeetUserRepository extends JpaRepository<MeetUserVO, Object> {
	
	
	@Query(nativeQuery = true, 
			value="select register_no, meet_no, mr.user_no, user_nickname, oper_state "
					+ "from meetregisteredmember mr join userinfo u "
					+ "on mr.user_no = u.user_no "
					+ "where meet_no=?1")
	public List<MeetUserVO> select_all_meet_registered_member(String meet_no);
}

/**
 * @author 최진실
 */
package com.mab.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mab.meet.model.MeetRegisteredMemberVO;

public interface MeetRegisteredRepository extends JpaRepository<MeetRegisteredMemberVO, Object> {

	// 모임 가입
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="INSERT INTO meetregisteredmember(register_no, meet_no, user_no, oper_state)"
				+  "values('MR'||SEQ_MEETREGISTEREDMEMBER.nextval, ?1, ?2, 'T')")
	public int meet_application(String meet_no, String user_no);

}

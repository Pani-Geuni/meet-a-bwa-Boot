package com.mab.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mab.activity.model.ActivityRegisteredMemberVO;

public interface ActivityRegisteredRepository extends JpaRepository<ActivityRegisteredMemberVO, Object> {

	// 액티비티 가입
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="INSERT INTO activityregisteredmember(register_no, activity_no, user_no, oper_state)"
				+  "values('AR'||SEQ_ACTIVITYREGISTEREDMEMBER.nextval, ?1, ?2, 'T')")
	public int activity_application(String activity_no, String user_no);

	// 액티비티에 가입된 회원 모두 삭제
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="delete from activityregisteredmember where activity_no=?")
	public int activity_member_delete(String activity_no);

	// 액티비티 회원 자진 탈퇴
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="update activityregisteredmember set oper_state='F' where activity_no=? and user_no=?2")
	public int activity_withdrawal(String activity_no, String user_no);

//	@Query(nativeQuery = true, value="select user_no from userinfo where user_id=?1")
//	public String select_user_no(String username);

}

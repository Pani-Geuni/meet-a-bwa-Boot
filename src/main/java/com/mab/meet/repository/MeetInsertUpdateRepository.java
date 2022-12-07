/**
 * @author 최진실
 */
package com.mab.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mab.meet.model.MeetVO;

public interface MeetInsertUpdateRepository  extends JpaRepository<MeetVO, Object>{

	// 모임 생성
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="INSERT INTO meet(meet_no, meet_image, meet_emblem, meet_name, meet_info, meet_city, meet_county, meet_interest_name, meet_gender, meet_nop, meet_age, meet_date, user_no, private_state) "
			+ "values('M'||seq_meet.nextval, :#{#mvo?.meet_image}, :#{#mvo?.meet_emblem}, :#{#mvo?.meet_name}, :#{#mvo?.meet_info}, :#{#mvo?.meet_city}, :#{#mvo?.meet_county}, :#{#mvo?.meet_interest_name}, :#{#mvo?.meet_gender},:#{#mvo?.meet_nop}, :#{#mvo?.meet_age}, current_date , :#{#mvo?.user_no}, :#{#mvo?.private_state})")
	public int meet_insert(MeetVO mvo);

	// 모임 개설 후 모임 정보 가져오기
	@Query(nativeQuery = true, value="select * from(select * from meet ORDER BY ROWNUM DESC) where user_no=?1 and ROWNUM = 1")
	public MeetVO select_one_meet_no(String user_no);
	
	// 모임 정보 가져오기
	@Query(nativeQuery = true, value="select * from meet where meet_no=?1")
	public MeetVO select_one_meet_info(String meet_no);

	// 모임 수정
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="update meet set meet_name=:#{#mvo?.meet_name}, meet_image=:#{#mvo?.meet_image}, meet_emblem=:#{#mvo?.meet_emblem}, meet_info=:#{#mvo?.meet_info}, meet_city=:#{#mvo?.meet_city}, meet_county=:#{#mvo?.meet_county}, meet_interest_name=:#{#mvo?.meet_interest_name}, meet_gender=:#{#mvo?.meet_gender}, meet_nop=:#{#mvo?.meet_nop}, meet_age=:#{#mvo?.meet_age} where meet_no=:#{#mvo?.meet_no}")
	public int meet_update(MeetVO mvo);


}

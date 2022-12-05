package com.mab.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.meet.model.MeetVO;

public interface MeetSelectRepository  extends JpaRepository<MeetVO, Object>{

	@Query(nativeQuery = true, value="select * from meet where meet_no=?1")
	public MeetVO activity_select_meet_private_state(String meet_no);

}

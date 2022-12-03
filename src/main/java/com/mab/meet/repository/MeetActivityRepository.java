/**
 * @author 전판근
 */


package com.mab.meet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.meet.model.MeetActivityVO;

public interface MeetActivityRepository extends JpaRepository<MeetActivityVO, Object> {
	
	@Query(nativeQuery = true, 
			value="select * from activity_join_view where meet_no=?1 order by activity_no desc")
	public List<MeetActivityVO> select_all_activity_for_feed(String meet_no);

}

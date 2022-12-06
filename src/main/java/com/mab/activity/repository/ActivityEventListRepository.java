package com.mab.activity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.activity.model.ActivityVoteListView;
import com.mab.event.model.EventVO;

public interface ActivityEventListRepository extends JpaRepository<EventVO, Object>{

	// 액티비티 이벤트 리스트
	@Query(nativeQuery = true, value="select * from event where activity_no=?1")
	public List<EventVO> select_activity_event_list(String activity_no);

}
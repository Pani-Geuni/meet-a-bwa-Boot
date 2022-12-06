package com.mab.activity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.activity.model.ActivityVoteListView;
import com.mab.meet.model.MeetVO;

public interface ActivityVoteListRepository  extends JpaRepository<ActivityVoteListView, Object>{

	// 액티비티 투표 리스트
	@Query(nativeQuery = true, value="select * from (activity_vote_list_view)A where A.no=1 and activity_no=?1")
	public List<ActivityVoteListView> select_activity_vote_list(String activity_no);

}

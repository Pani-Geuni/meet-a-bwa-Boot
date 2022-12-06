package com.mab.list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.main.model.MainMeetViewVO;

public interface MeetListRepository extends JpaRepository<MainMeetViewVO, Object> {
	
	@Query(nativeQuery = true, 
			value = "select * from MAINMEETVIEW "
					+ "where meet_name like ?1 "
					+ "order by like_cnt desc, user_cnt desc, meet_name")
	public List<MainMeetViewVO> select_all_more_like(String searchWord);
	
	@Query(nativeQuery = true, 
			value = "select * from MAINMEETVIEW "
					+ "where meet_interest_name = ?1 and meet_name like ?2 "
					+ "order by like_cnt desc, user_cnt desc, meet_name")
	public List<MainMeetViewVO> select_all_more_interest(String typeData, String searchWord);
	
	@Query(nativeQuery = true, 
			value = "select * from MAINMEETVIEW "
					+ "where meet_county = ?1 and meet_name like ?2 "
					+ "order by like_cnt desc, user_cnt desc, meet_name")
	public List<MainMeetViewVO> select_all_more_county(String typeData, String searchWord);
}

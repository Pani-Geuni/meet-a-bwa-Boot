package com.mab.list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.main.model.MainActivityViewVO;

public interface ActivityListRepository extends JpaRepository<MainActivityViewVO, Object> {

	
	@Query(nativeQuery = true, 
			value = "select * from MAINACTIVITYVIEW "
					+ "where activity_name like ?1 "
					+ "order by like_cnt desc, activity_no asc")
	public List<MainActivityViewVO> select_all_more_like_acti(String searchWord);
	
	@Query(nativeQuery = true, 
			value = "select * from MAINACTIVITYVIEW "
					+ "where activity_interest = ?1 and activity_name like ?2 "
					+ "order by like_cnt desc, activity_no asc")
	public List<MainActivityViewVO> select_all_more_interest_acti(String category, String searchWord);
	
}

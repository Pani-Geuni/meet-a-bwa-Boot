/**
 * @author 김예은
 */

package com.mab.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.activity.model.ActivityLikeVO;
import com.mab.main.model.MainActivityViewVO;
import com.mab.main.repository.MainActivityRepository;
import com.mab.meet.model.MeetLikeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MainActivityService {
	@Autowired
	MainActivityRepository activity_repo;
	
	
	public MainActivityService(){
		log.info("MainActivityService()...");
	}
	
	public int insert_activity_like(ActivityLikeVO vo){
		int result = activity_repo.SQL_INSERT_ACTIVITY_LIKE(vo);
		
		return result;
	}
	
	public int delete_activity_like(ActivityLikeVO vo){
		int result = activity_repo.SQL_DELETE_ACTIVITY_LIKE(vo);
		
		return result;
	}
	
	public List<MainActivityViewVO> SQL_SELECT_ALL_6(){
		List<MainActivityViewVO> list = activity_repo.SQL_SELECT_ALL_6();
		
		return list;
	}
	
	public List<MainActivityViewVO> SQL_SELECT_IMMINENT_DEADLINE(){
		List<MainActivityViewVO> list = activity_repo.SQL_SELECT_IMMINENT_DEADLINE();
		
		return list;
	}
	
	public List<MainActivityViewVO> SQL_SELECT_CATEGORY_6(String category){
		List<MainActivityViewVO> list = activity_repo.SQL_SELECT_CATEGORY_6(category);
		
		return list;
	}
	
	public List<String> SELECT_ALL_LIKE_USER_NO(String user_no){
		List<String> list = activity_repo.SQL_SELECT_ALL_LIKE_USER_NO(user_no);
		
		return list;
	}
}

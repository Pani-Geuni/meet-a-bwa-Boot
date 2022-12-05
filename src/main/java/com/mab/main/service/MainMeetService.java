/**
 * @author 김예은
 */

package com.mab.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.main.model.MainMeetViewVO;
import com.mab.main.repository.MainMeetRepository;
import com.mab.meet.model.MeetLikeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MainMeetService {
	@Autowired
	MainMeetRepository meet_repo;
	
	
	public MainMeetService(){
		log.info("MainMeetService()...");
	}
	
	
	public int insert_meet_like(MeetLikeVO vo){
		int result = meet_repo.SQL_INSERT_MEET_LIKE(vo);
		
		return result;
	}
	
	public int delete_meet_like(MeetLikeVO vo){
		int result = meet_repo.SQL_DELETE_MEET_LIKE(vo);
		
		return result;
	}
	
	public List<MainMeetViewVO> SQL_SELECT_ALL_LIKE(){
		List<MainMeetViewVO> list = meet_repo.SQL_SELECT_ALL_LIKE();
		
		return list;
	}
	
	public List<MainMeetViewVO> SQL_SELECT_ALL_COUNTY(String country){
		List<MainMeetViewVO> list = meet_repo.SQL_SELECT_ALL_COUNTY(country);
		
		return list;
	}
	
	public List<String> SELECT_ALL_LIKE_USER_NO(String user_no){
		List<String> list = meet_repo.SQL_SELECT_ALL_LIKE_USER_NO(user_no);
		
		return list;
	}
}

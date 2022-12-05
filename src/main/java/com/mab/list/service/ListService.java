package com.mab.list.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.list.repository.ActivityListRepository;
import com.mab.list.repository.MeetListRepository;
import com.mab.main.model.MainActivityViewVO;
import com.mab.main.model.MainMeetViewVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ListService {
	
	@Autowired
	MeetListRepository meetListRepository;
	
	@Autowired
	ActivityListRepository actiListRepository;
	
	public ListService() {
		log.info("ListService()...");
	}

	// *********
	// MEET LIST
	// *********
	public List<MainMeetViewVO> select_all_more_like(String searchWord) {
		String likeSearchWord = "%" + searchWord + "%";
		log.info("likeSearchWord : {}", likeSearchWord);
		
		List<MainMeetViewVO> vos = meetListRepository.select_all_more_like(likeSearchWord);
		
		return vos;
	}
	
	public List<MainMeetViewVO> select_all_more_interest(String typeData, String searchWord) {
		String likeSearchWord = "%" + searchWord + "%";
		log.info("likeSearchWord : {}", likeSearchWord);
		
		List<MainMeetViewVO> vos = meetListRepository.select_all_more_interest(typeData, likeSearchWord);
		
		return vos;
	}
	
	public List<MainMeetViewVO> select_all_more_county(String typeData, String searchWord) {
		String likeSearchWord = "%" + searchWord + "%";
		log.info("likeSearchWord : {}", likeSearchWord);
		
		List<MainMeetViewVO> vos = meetListRepository.select_all_more_county(typeData, likeSearchWord);
		
		return vos;
	}
	

	// *************
	// ACTIVITY LIST
	// *************
	public List<MainActivityViewVO> select_all_more_like_acti(String searchWord) {
		String likeSearchWord = "%" + searchWord + "%";
		
		List<MainActivityViewVO> vos = actiListRepository.select_all_more_like_acti(likeSearchWord);
		
		return vos;
	}
	
	public List<MainActivityViewVO> select_all_more_interest_acti(String typeData, String searchWord) {
		String likeSearchWord = "%" + searchWord + "%";
		
		List<MainActivityViewVO> vos = actiListRepository.select_all_more_interest_acti(typeData, likeSearchWord);
		
		return vos;
	}
}

/**
 * @author 김예은
 */

package com.mab.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.event.model.EventVO;
import com.mab.event.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventService {
	
	@Autowired
	EventRepository repo;
	
	
	public EventService(){
		log.info("EventService()...");
	}
	
	
	public int SQL_INSERT_EVENT(EventVO vo) {
		int result = repo.SQL_INSERT_EVENT(vo);
		
		return result;
	}
	
	public int SQL_DELETE_EVENT(EventVO vo) {
		int result = repo.SQL_DELETE_EVENT(vo);
		
		return result;
	}
	
	public int SQL_UPDATE_EVENT(EventVO vo) {
		int result = repo.SQL_UPDATE_EVENT(vo);
		
		return result;
	}
	
	public List<EventVO> SQL_EVENT_SELECT_ALL(EventVO vo){
		List<EventVO> list = repo.SQL_EVENT_SELECT_ALL(vo);
		
		return list;
	}
	
	public EventVO SQL_EVENT_SELECT_ONE(EventVO vo){
		EventVO vo2 = repo.SQL_EVENT_SELECT_ONE(vo);
		
		return vo2;
	}
}

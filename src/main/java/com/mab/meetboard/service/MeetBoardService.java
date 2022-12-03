/**
 * @author 전판근
 */


package com.mab.meetboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.meet.repository.MeetActivityRepository;
import com.mab.meetboard.model.MBUserVO;
import com.mab.meetboard.repository.MeetBoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetBoardService {

	@Autowired
	MeetBoardRepository boardRepository;
	
	
	public MeetBoardService() {
		log.info("Meet Board Service");
	}
	
	public List<MBUserVO> select_all_board_feed(String meet_no) {
		List<MBUserVO> vos = boardRepository.select_all_board_feed(meet_no);
		
		return vos;
	}
	
	
	
}

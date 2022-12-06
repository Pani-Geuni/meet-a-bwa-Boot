/**
 * @author 전판근
 */


package com.mab.meetboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.meetboard.model.MBUserVO;
import com.mab.meetboard.repository.MeetBoardRepository;
import com.mab.meetboard.repository.MeetBoardUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetBoardService {

	@Autowired
	MeetBoardRepository boardRepository;
	
	@Autowired
	MeetBoardUserRepository boardUserRepository;
	
	
	public MeetBoardService() {
		log.info("Meet Board Service");
	}
	
	public List<MBUserVO> select_all_board_feed(String meet_no) {
		List<MBUserVO> vos = boardUserRepository.select_all_board_feed(meet_no);
		
		return vos;
	}
	
	public MBUserVO select_one_post_detail(String board_no) {
		MBUserVO vo = boardUserRepository.select_one_post_detail(board_no);
		
		return vo;
	}
	
	public int insert_post(String board_title, String board_content, String user_no, String meet_no) {
		int result = boardRepository.insert_post(board_title, board_content, user_no, meet_no);
		
		return result;
	}
	
	public int delete_post(String board_no) {
		int result = boardRepository.delete_post(board_no);
		
		return result;
	}
	
}

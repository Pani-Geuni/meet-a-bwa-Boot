package com.mab.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.comment.model.CommentUserVO;
import com.mab.comment.repository.CommentRepository;
import com.mab.comment.repository.CommentUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	CommentUserRepository commentUserRepository;
	
	public CommentService() {
		log.info("CommentService()...");
	}
	
	public int insert_comment(String mother_no, String comment_content, String board_no, String user_no, String meet_no) {
		log.info("{} {} {} {} {}", mother_no, comment_content, board_no, meet_no);
		int result = commentRepository.insert_comment(mother_no, comment_content, board_no, user_no, meet_no);
		
		return result;
	}
	
	public int update_comment(String comment_no) {
		int result = commentRepository.update_comment(comment_no);
		
		return result;
	}
	
	public List<CommentUserVO> select_all_board_comment(String board_no) {
		List<CommentUserVO> vos = commentUserRepository.select_all_post_comment(board_no);
		
		return vos;
	}
}

package com.mab.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mab.comment.model.CommentVO;

public interface CommentRepository extends JpaRepository<CommentVO, Object> {
	
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, 
			value = "INSERT INTO BOARDCOMMENT(COMMENT_NO, MOTHER_NO, COMMENT_DATE, COMMENT_CONTENT, BOARD_NO, USER_NO, MEET_NO) "
					+ "VALUES ('C'||SEQ_COMMENT.nextval, ?1, CURRENT_DATE, ?2, ?3, ?4, ?5)")
	public int insert_comment(String mother_no, String comment_content, String board_no, String user_no, String meet_no);

}

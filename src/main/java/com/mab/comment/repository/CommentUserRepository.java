package com.mab.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.comment.model.CommentUserVO;

public interface CommentUserRepository extends JpaRepository<CommentUserVO, Object> {
	
	@Query(nativeQuery = true, value = "select * from comment_view where board_no=?1 order by comment_no desc")
	public List<CommentUserVO> select_all_post_comment(String board_no);
	
	@Query(nativeQuery = true, value = "select count(*) as comment_cnt from comment_view where board_no=?1 and delete_state='F'")
	public int select_comment_cnt(String board_no);
}

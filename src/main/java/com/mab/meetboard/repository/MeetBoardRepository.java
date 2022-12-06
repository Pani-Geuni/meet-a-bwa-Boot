package com.mab.meetboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mab.meetboard.model.MeetBoardVO;

public interface MeetBoardRepository extends JpaRepository<MeetBoardVO, Object> {

	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, 
			value = "insert into meetboard(board_no, board_title, board_content, board_image, board_date, notice_state, user_no, meet_no) "
					+ "values('B'||SEQ_MEETBOARD.nextval, ?1, ?2, NULL, CURRENT_DATE, 'F', ?3, ?4)")
	public int insert_post(String board_title, String board_content, String user_no, String meet_no);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, 
			value = "update meetboard "
					+ "set board_title=?1, board_content=?2, board_date=CURRENT_DATE "
					+ "where user_no=?3 and meet_no=?4 and board_no=?5")
	public int update_post(String board_title, String board_content, String user_no, String meet_no, String board_no);
	
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "delete from meetboard where board_no=?1")
	public int delete_post(String board_no);
}

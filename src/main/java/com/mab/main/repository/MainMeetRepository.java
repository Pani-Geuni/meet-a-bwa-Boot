/**
 * @author 김예은
*/
package com.mab.main.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mab.main.model.MainMeetViewVO;
import com.mab.meet.model.MeetLikeVO;

public interface MainMeetRepository extends JpaRepository<MainMeetViewVO, Object> {

	
	@Query(nativeQuery = true, value = 
			"SELECT * FROM("
			+ "SELECT * FROM MAINMEETVIEW ORDER BY LIKE_CNT DESC, MEET_NO ASC"
			+ ")"
			+ "WHERE ROWNUM BETWEEN 1 AND 6")
	public List<MainMeetViewVO> SQL_SELECT_ALL_LIKE();

	@Query(nativeQuery = true, value = 
			"SELECT * FROM("
			+ "SELECT * FROM MAINMEETVIEW WHERE meet_county = ?1 ORDER BY LIKE_CNT DESC, MEET_NO ASC "
			+ ")"
			+ "WHERE ROWNUM BETWEEN 1 AND 6")
	public List<MainMeetViewVO> SQL_SELECT_ALL_COUNTY(String country);
	
	@Query(nativeQuery = true, value = 
			"SELECT * FROM("
			+ "SELECT * FROM MAINMEETVIEW WHERE MEET_INTEREST_NAME LIKE ?1 ORDER BY LIKE_CNT DESC, MEET_NO ASC "
			+ ")"
			+ "WHERE ROWNUM BETWEEN 1 AND 6")
	public List<MainMeetViewVO> SQL_SELECT_ALL_INTEREST(String interest);
	
	@Query(nativeQuery = true, value = 
			"SELECT MEET_NO FROM MEETLIKE WHERE user_no = ?1")
	public List<String> SQL_SELECT_ALL_LIKE_USER_NO(String user_no);
	

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = 
			"insert into meetlike(meet_like_no, meet_no, user_no)"
			+ "values ('ML'||SEQ_MEET_L.NEXTVAL, :#{#vo?.meet_no}, :#{#vo?.user_no})")
	public int SQL_INSERT_MEET_LIKE(@Param("vo") MeetLikeVO vo);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = 
			"DELETE FROM meetlike WHERE meet_no = :#{#vo?.meet_no} AND user_no = :#{#vo?.user_no}")
	public int SQL_DELETE_MEET_LIKE(@Param("vo") MeetLikeVO vo);

}
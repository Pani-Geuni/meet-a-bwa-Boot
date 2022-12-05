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

public interface MainMeetRepository extends JpaRepository<MainMeetViewVO, Object> {

	
	@Query(nativeQuery = true, value = 
			"SELECT * FROM("
			+ "SELECT * FROM MAINMEETVIEW ORDER BY LIKE_CNT DESC, MEET_NO ASC"
			+ ")"
			+ "WHERE ROWNUM BETWEEN 1 AND 6")
	public List<MainMeetViewVO> SQL_SELECT_ALL_LIKE();

	@Query(nativeQuery = true, value = 
			"SELECT * FROM("
			+ "SELECT * FROM MAINMEETVIEW ORDER BY LIKE_CNT DESC, MEET_NO ASC"
			+ ")"
			+ "WHERE MEET_COUNTY = ?1 AND ROWNUM BETWEEN 1 AND 6")
	public List<MainMeetViewVO> SQL_SELECT_ALL_COUNTY(String country);
	
	@Query(nativeQuery = true, value = 
			"SELECT MEET_NO FROM MEETLIKE WHERE USER_NO = ?")
	public List<String> SQL_SELECT_ALL_LIKE_USER_NO(String user_no);
	

//	@Transactional
//	@Modifying
//	@Query(nativeQuery = true, value = 
//			"insert into comments(comment_no, mother_no, comment_content, comment_date, room_no, backoffice_no, user_no, host_no, is_secret) "
//			+ "		values('C'||SEQ_COMMENTS.nextval, null, :#{#vo2?.comment_content}, current_date, :#{#vo2?.room_no}, :#{#vo2?.backoffice_no}, :#{#vo2?.user_no}, null, :#{#vo2?.is_secret})")
//	public int insert_question(@Param("vo2") Comment_EntityVO vo2);

}
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

import com.mab.activity.model.ActivityLikeVO;
import com.mab.main.model.MainActivityViewVO;
import com.mab.meet.model.MeetLikeVO;

public interface MainActivityRepository extends JpaRepository<MainActivityViewVO, Object> {
	
	@Query(nativeQuery = true, value
			= "SELECT * FROM ("
				+ "SELECT * FROM MAINACTIVITYVIEW ORDER BY LIKE_CNT DESC, ACTIVITY_NO DESC" 
			+ ")"
			+ "WHERE ROWNUM BETWEEN 1 AND 6")
	public List<MainActivityViewVO> SQL_SELECT_ALL_6();
	
	@Query(nativeQuery = true, value
			= "SELECT * FROM ("
				+ "SELECT * FROM MAINACTIVITYVIEW "
				+ "WHERE ACTIVITY_INTEREST = :category "
				+ "ORDER BY LIKE_CNT DESC, ACTIVITY_NO DESC" 
			+ ")"
			+ "WHERE ROWNUM BETWEEN 1 AND 6")
	public List<MainActivityViewVO> SQL_SELECT_CATEGORY_6(String category);
	
	@Query(nativeQuery = true, value
			= "SELECT * FROM ( "
			+ "  SELECT * FROM MAINACTIVITYVIEW where RECRUITMENT_ETIME > current_date ORDER BY (RECRUITMENT_ETIME - current_date) asc "
			+ ")"
			+ "WHERE ROWNUM BETWEEN 1 AND 6")
	public List<MainActivityViewVO> SQL_SELECT_IMMINENT_DEADLINE();
	
	@Query(nativeQuery = true, value = 
			"SELECT ACTIVITY_NO FROM ACTIVITY_LIKE WHERE user_no = ?1")
	public List<String> SQL_SELECT_ALL_LIKE_USER_NO(String user_no);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = 
			"insert into ACTIVITY_LIKE(activity_like_no, activity_no, user_no)"
			+ "values ('AL'||SEQ_ACTIVITY_L.NEXTVAL, :#{#vo?.activity_no}, :#{#vo?.user_no})")
	public int SQL_INSERT_ACTIVITY_LIKE(@Param("vo") ActivityLikeVO vo);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = 
			"DELETE FROM ACTIVITY_LIKE WHERE activity_no = :#{#vo?.activity_no} AND user_no = :#{#vo?.user_no}")
	public int SQL_DELETE_ACTIVITY_LIKE(@Param("vo") ActivityLikeVO vo);
	
}
/**
 * @author 김예은
*/
package com.mab.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.main.model.MainActivityViewVO;

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
	
	@Query(nativeQuery = true, value = 
			"SELECT ACTIVITY_NO FROM ACTIVITY_LIKE WHERE USER_NO = ?1")
	public List<String> SQL_SELECT_ALL_LIKE_USER_NO(String user_no);
	
}
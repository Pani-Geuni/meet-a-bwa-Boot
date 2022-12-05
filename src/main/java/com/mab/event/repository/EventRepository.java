/**
 * @author 김예은
*/
package com.mab.event.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mab.event.model.EventVO;

public interface EventRepository extends JpaRepository<EventVO, Object> {
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value =
			"insert into EVENT(EVENT_NO, EVENT_TITLE, EVENT_INFO, EVENT_DATE, EVENT_D_DAY, ACTIVITY_NO, USER_NO) "
			+ "values ('E'||SEQ_EVENT.NEXTVAL, :#{#vo?.EVENT_TITLE} , :#{#vo?.EVENT_INFO} , current_date, :#{#vo?.EVENT_D_DAY}, :#{#vo?.ACTIVITY_NO}, :#{#vo?.USER_NO})")
	public int SQL_INSERT_EVENT(@Param("vo") EventVO vo);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = 
			"DELETE FROM EVENT WHERE EVENT_NO = :#{#vo?.EVENT_NO}")
	public int SQL_DELETE_EVENT(@Param("vo") EventVO vo);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value 
		= "UPDATE EVENT SET EVENT_TITLE = :#{#vo?.EVENT_TITLE}, EVENT_INFO = :#{#vo?.EVENT_INFO}, EVENT_D_DAY = :#{#vo?.EVENT_D_DAY}"
		+ "WHERE EVENT_NO = :#{#vo?.EVENT_NO}")
	public int SQL_UPDATE_EVENT(@Param("vo") EventVO vo);
	
	
	@Query(nativeQuery = true, value = 
			"SELECT EVENT_NO, EVENT_TITLE, EVENT_INFO, EVENT_DATE , EVENT_D_DAY, ACTIVITY_NO, USER_NO "
			+ "FROM EVENT "
			+ "WHERE ACTIVITY_NO = :#{#vo?.ACTIVITY_NO} ORDER BY EVENT_NO DESC")
	public List<EventVO> SQL_EVENT_SELECT_ALL(@Param("vo") EventVO vo);
	
	@Query(nativeQuery = true, value = 
			"SELECT * FROM TEST_EVENT WHERE EVENT_NO = #{#vo?.EVENT_NO}")
	public EventVO SQL_EVENT_SELECT_ONE(@Param("vo") EventVO vo);

}
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
			"insert into EVENT(EVENT_NO, EVENT_TITLE, event_content, EVENT_DATE, EVENT_D_DAY, ACTIVITY_NO, USER_NO) "
			+ "values ('E'||SEQ_EVENT.NEXTVAL, :#{#vo?.event_title} , :#{#vo?.event_content} , current_date, :#{#vo?.event_d_day_sql}, :#{#vo?.activity_no}, :#{#vo?.user_no})")
	public int SQL_INSERT_EVENT(@Param("vo") EventVO vo);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = 
			"DELETE FROM EVENT WHERE EVENT_NO = :#{#vo?.event_no}")
	public int SQL_DELETE_EVENT(@Param("vo") EventVO vo);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value 
		= "UPDATE EVENT SET EVENT_TITLE = :#{#vo?.event_title}, event_content = :#{#vo?.event_content}, EVENT_D_DAY = :#{#vo?.event_d_day_sql} "
		+ "WHERE EVENT_NO = :#{#vo?.event_no}")
	public int SQL_UPDATE_EVENT(@Param("vo") EventVO vo);
	
	
	@Query(nativeQuery = true, value = 
			"SELECT EVENT_NO, EVENT_TITLE, EVENT_INFO, EVENT_DATE , EVENT_D_DAY, ACTIVITY_NO, USER_NO "
			+ "FROM EVENT "
			+ "WHERE ACTIVITY_NO = :#{#vo?.activity_no} ORDER BY EVENT_NO DESC")
	public List<EventVO> SQL_EVENT_SELECT_ALL(@Param("vo") EventVO vo);
	
	@Query(nativeQuery = true, value = 
			"SELECT * FROM EVENT WHERE EVENT_NO = :#{#vo?.event_no}")
	public EventVO SQL_EVENT_SELECT_ONE(@Param("vo") EventVO vo);

}
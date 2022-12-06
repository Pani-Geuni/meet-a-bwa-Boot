/**
 * @author 김예은
*/

package com.mab.vote.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mab.vote.model.VoteContentVO;
import com.mab.vote.model.VoteResultVO;
import com.mab.vote.model.VoteVO;
import com.mab.vote.model.VoteViewVO;

public interface VoteRepository extends JpaRepository<VoteViewVO, Object> {

	@Query(nativeQuery = true, value 
		= "SELECT * FROM VOTEVIEW" + "WHERE VOTE_NO = :#{#vo2?.comment_content}"
		+ "ORDER BY CONTENT_NO")
	public List<VoteViewVO> select_all_vote_content(@Param("vo2") VoteVO vo2);

	@Query(nativeQuery = true, value
		= "SELECT CONTENT_NO, COUNT(*) AS CNT FROM ("
		+ "SELECT * FROM VOTERESULT ORDER BY CONTENT_NO" + ") " 
		+ "WHERE VOTE_NO = ?1 GROUP BY CONTENT_NO")
	public List<Object> select_one_vote_result(String vote_no);

	@Query(nativeQuery = true, value = "SELECT * FROM TEST_VOTE_RESULT WHERE VOTE_NO = ?1 AND USER_NO = ?2")
	public String select_one_my_vote_result(String vote_no, String user_no);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value 
		= "INSERT INTO VOTE(VOTE_NO, VOTE_TITLE, VOTE_INFO, VOTE_EOD, VOTE_STATE, USER_NO, MEET_NO, ACTIVITY_NO, PRIVATE_STATE)"
		+ "VALUES ('V'||SEQ_VOTE.NEXTVAL, :#{#vo2?.vote_title}, :#{#vo2?.vote_info}, :#{#vo2?.vote_eod_sql}, 'Y', :#{#vo2?.user_no},"
		+ ":#{#vo2?.meet_no}, :#{#vo2?.activity_no}, :#{#vo2?.private_state})")
	public int SQL_INSERT_VOTE(@Param("vo2") VoteVO vo2);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value 
		= "INSERT INTO VOTE_CONTENT(CONTENT_NO, VOTE_NO, VOTE_CONTENT) "
		+ "VALUES ('VC'||SEQ_VOTE_CONTENT.NEXTVAL, :#{#vo2?.vote_no}, :#{#vo2?.vote_content})")
	public int SQL_INSERT_VOTE_CONTENT(@Param("vo2") VoteContentVO vo2);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value 
		= "UPDATE VOTE SET VOTE_TITLE = :#{#vo2?.vote_title}, VOTE_INFO = :#{#vo2?.vote_info}, VOTE_EOD = :#{#vo2?.vote_eod_sql}"
		+ "WHERE VOTE_NO = :#{#vo2?.vote_no}")
	public int SQL_UPDATE_VOTE(@Param("vo2") VoteVO vo2);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value 
		= "UPDATE TEST_VOTE SET VOTE_STATE = 'N' WHERE VOTE_NO = :#{#vo2?.vote_no}")
	public int SQL_UPDATE_VOTE_STATE(@Param("vo2") VoteVO vo2);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM TEST_VOTE WHERE VOTE_NO = :#{#vo2?.vote_no}")
	public int SQL_DELETE_VOTE(@Param("vo2") VoteVO vo2);
	

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value 
		= "INSERT INTO VOTERESULT(VOTE_RESULT_NO, VOTE_NO, USER_NO, CONTENT_NO)"
		+ "VALUES ('VR'||SEQ_VOTE_RESULT.NEXTVAL, :#{#vo2?.vote_no}, :#{#vo2?.user_no}, :#{#vo2?.content_no})")
	public int SQL_INSERT_VOTE_RESULT(@Param("vo2") VoteResultVO vo2);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value 
		= "UPDATE VOTERESULT SET CONTENT_NO = :#{#vo2?.content_no}"
		+ "WHERE VOTE_NO = :#{#vo2?.vote_no} AND USER_NO = :#{#vo2?.user_no}")
	public int SQL_UPDATE_VOTE_RESULT(@Param("vo2") VoteResultVO vo2);

	@Query(nativeQuery = true, value 
		= "SELECT VOTE_NO FROM ( "
		+ "	SELECT VOTE_NO FROM VOTE ORDER BY VOTE_NO DESC"
		+ ")"
		+ "WHERE ROWNUM = 1")
	public String SQL_SELECT_ONE_LAST_NO();

}
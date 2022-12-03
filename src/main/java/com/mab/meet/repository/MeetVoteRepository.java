/**
 * @author 전판근
 */


package com.mab.meet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.meet.model.MeetVoteVO;

public interface MeetVoteRepository extends JpaRepository<MeetVoteVO, Object> {

	
	@Query(nativeQuery = true,
			value="select vote_no, vote_title, user_no, meet_no "
					+ "from vote where meet_no=?1 order by vote_no desc")
	public List<MeetVoteVO> select_all_vote_meet(String meet_no);
}

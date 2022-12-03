/**
 * @author 전판근
 */


package com.mab.meetboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.meetboard.model.MBUserVO;

public interface MeetBoardRepository extends JpaRepository<MBUserVO, Object> {

	
	@Query(nativeQuery = true,
			value="select * from meetboard_user_view where meet_no=?1 order by board_no desc")
	public List<MBUserVO> select_all_board_feed(String meet_no);
}

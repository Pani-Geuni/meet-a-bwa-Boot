/**
 * @author 전판근
 */

package com.mab.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.meet.model.MeetInfoVO;

public interface MeetInfoRepository extends JpaRepository<MeetInfoVO, Object> {

	@Query(nativeQuery = true, 
			value="select * from meet_join_user_view where meet_no=?1")
	public MeetInfoVO select_one_meet_info(String meet_no);
}

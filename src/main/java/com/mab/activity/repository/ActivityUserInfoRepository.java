package com.mab.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.activity.model.ActivityUserVO;

public interface ActivityUserInfoRepository extends JpaRepository<ActivityUserVO, Object>{

	@Query(nativeQuery = true, value="select * from userinfo where user_no=?1")
	public ActivityUserVO select_one_activity_user_info(String user_no);

}

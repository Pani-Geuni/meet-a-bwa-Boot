package com.mab.activity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mab.activity.model.ActivityVO;
import com.mab.activity.model.ActivityView;

public interface ActivityFeedRepository extends JpaRepository<ActivityView, Object>{

	// 액티비티 기본 정보
	@Query(nativeQuery = true, value="select * from activity_main_view where activity_no=?1")
	public ActivityView select_one_activity_feed_info(String activity_no);

	// 액티비티 가입자 수 : ActivityView의 컬럼명으로 꺼내오기 위해 user_no 사용
	@Query(nativeQuery = true, value="select count(user_no) user_no from activityregisteredmember where activity_no=?1")
	public String select_activity_user_cnt(String activity_no);

	// 액티비티 좋아요 수 : ActivityView의 컬럼명으로 꺼내오기 위해 user_no 사용
	@Query(nativeQuery = true, value="select count(activity_like_no) user_no from activity_like where activity_no=?1")
	public String select_activity_like_cnt(String activity_no);

	// 액티비티 가입된 회원 : ActivityView의 컬럼명으로 꺼내오기 위해 user_no 사용
	@Query(nativeQuery = true, value="select user_no from activityregisteredmember where activity_no=?1")
	public List<String> select_activity_registered_member_user_no(String activity_no);

}

package com.mab.activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.activity.model.ActivityVO;
import com.mab.activity.model.ActivityView;
import com.mab.activity.repository.ActivityFeedRepository;
import com.mab.activity.repository.ActivityInsertUpdateRepository;
import com.mab.activity.repository.MeetSelectRepository;
import com.mab.meet.model.MeetVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActiivityService {
	
	@Autowired
	ActivityInsertUpdateRepository a_repository;
	
	@Autowired
	ActivityFeedRepository a_feed_repository;
	
	@Autowired
	MeetSelectRepository m_repository;

	// meet 정보 select
	public MeetVO activity_select_meet_private_state(String meet_no) {
		log.info("activity_select_meet_private_state().....");
		return m_repository.activity_select_meet_private_state(meet_no);
	}
	
	// 액티비티 생성
	public int activity_insert(ActivityVO avo) {
		log.info("activity_insert().....");
		return a_repository.activity_insert(avo);
	}

	// 액티비티 수정을 위한 기존 정보 select
	public ActivityVO select_one_activity_info(String activity_no) {
		log.info("select_one_activity_info().....");
		return a_repository.select_one_activity_info(activity_no);
	}

	// 액티비티 수정
	public int activity_update(ActivityVO avo) {
		log.info("activity_update().....");
		return a_repository.activity_update(avo);
	}

	// 액티비티 피드 정보 select
	// - 액티비티 기본 정보, 개설자 정보
	public ActivityView select_one_activity_feed_info(String activity_no) {
		log.info("select_one_activity_feed_info().....");
		return a_feed_repository.select_one_activity_feed_info(activity_no);
	}

	// - 액티비티 가입자 수
	public String select_activity_user_cnt(String activity_no) {
		log.info("select_activity_user_cnt().....");
		return a_feed_repository.select_activity_user_cnt(activity_no);
	}

	// - 액티비티 좋아요 수
	public String select_activity_like_cnt(String activity_no) {
		log.info("select_activity_like_cnt().....");
		return a_feed_repository.select_activity_like_cnt(activity_no);
	}

	// 액티비티 가입된 회원 user_no
	public List<String> select_activity_registered_member_user_no(String activity_no) {
		log.info("select_activity_registered_member_user_no().....");
		return a_feed_repository.select_activity_registered_member_user_no(activity_no);
	}


}

package com.mab.activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.activity.model.ActivityUserVO;
import com.mab.activity.model.ActivityVO;
import com.mab.activity.model.ActivityView;
import com.mab.activity.model.ActivityVoteListView;
import com.mab.activity.repository.ActivityEventListRepository;
import com.mab.activity.repository.ActivityVoteListRepository;
import com.mab.activity.repository.ActivityFeedRepository;
import com.mab.activity.repository.ActivityInsertUpdateRepository;
import com.mab.activity.repository.ActivityRegisteredRepository;
import com.mab.activity.repository.ActivityUserInfoRepository;
import com.mab.activity.repository.MeetSelectRepository;
import com.mab.event.model.EventVO;
import com.mab.meet.model.MeetVO;
import com.mab.user.model.UserVO;

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
	
	@Autowired
	ActivityVoteListRepository v_list_repository;
	
	@Autowired
	ActivityEventListRepository e_list_repository;
	
	@Autowired
	ActivityRegisteredRepository a_member_repository;
	
	@Autowired
	ActivityUserInfoRepository a_user_repository;
	

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

	// 액티비티 투표 리스트
	public List<ActivityVoteListView> select_activity_vote_list(String activity_no) {
		log.info("select_activity_vote_list().....");
		return v_list_repository.select_activity_vote_list(activity_no);
	}

	// 액티비티 이벤트 리스트
	public List<EventVO> select_activity_event_list(String activity_no) {
		log.info("select_activity_event_list().....");
		return e_list_repository.select_activity_event_list(activity_no);
	}
	
	// 가입 신청을 하는 유저 정보 select
	public ActivityUserVO select_one_activity_user_info(String user_no) {
		log.info("select_one_activity_user_info().....");
		return a_user_repository.select_one_activity_user_info(user_no);
	}

	// 액티비티 가입 신청 처리
	public int activity_application(String activity_no, String user_no) {
		log.info("activity_application().....");
		return a_member_repository.activity_application(activity_no,user_no);
	}

	// 액티비티가 속한 모임에 해당 유저가 가입했는지 
	public String select_one_meet_registered_userinfo(String meet_no, String user_no) {
		log.info("select_one_meet_registered_userinfo().....");
		return m_repository.select_one_meet_registered_userinfo(meet_no, user_no);
	}



}

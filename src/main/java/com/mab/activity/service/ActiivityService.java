package com.mab.activity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.activity.model.ActivityVO;
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


}

package com.mab.meet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.activity.model.ActivityVO;
import com.mab.activity.repository.ActivityInsertUpdateRepository;
import com.mab.activity.repository.MeetSelectRepository;
import com.mab.meet.model.MeetVO;
import com.mab.meet.repository.MeetInsertUpdateRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetInsertUpdateService {
	
	@Autowired
	ActivityInsertUpdateRepository a_repository;
	
	@Autowired
	MeetInsertUpdateRepository m_repository;

	// 모임 생성
	public int meet_insert(MeetVO mvo) {
		return m_repository.meet_insert(mvo);
	}

	// 모임 정보 가져오기
	public MeetVO select_one_meet_info(String meet_no) {
		return m_repository.select_one_meet_info(meet_no);
	}

	// 모임 수정
	public int meet_update(MeetVO mvo) {
		return m_repository.meet_update(mvo);
	}


}

/**
 * @author 최진실
 */
package com.mab.meet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.activity.repository.ActivityInsertUpdateRepository;
import com.mab.meet.model.MeetVO;
import com.mab.meet.repository.MeetInsertUpdateRepository;
import com.mab.meet.repository.MeetRegisteredRepository;
import com.mab.meet.repository.MeetUserGenderRepository;
import com.mab.user.model.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetInfoService {
	
	@Autowired
	ActivityInsertUpdateRepository a_repository;
	
	@Autowired
	MeetInsertUpdateRepository m_repository;
	
	@Autowired
	MeetRegisteredRepository m_regi_repository;
	
	@Autowired
	MeetUserGenderRepository m_gen_repository;
	
	// 개설자 성별
	public UserVO select_one_meet_user_info(String user_no) {
		log.info("select_one_meet_user_info().....");
		return m_gen_repository.select_one_meet_user_info(user_no);
	}


	// 모임 생성
	public int meet_insert(MeetVO mvo) {
		log.info("meet_insert().....");
		return m_repository.meet_insert(mvo);
	}

	// 모임 개설 후 모임 정보 가져오기
	public MeetVO select_one_meet_no(String user_no) {
		log.info("select_one_meet_no().....");
		return m_repository.select_one_meet_no(user_no);
	}
	
	// 모임 개설자 자동 가입
	public int meet_application(String meet_no, String user_no) {
		log.info("meet_application().....");
		return m_regi_repository.meet_application(meet_no, user_no);
	}
	
	// 일반 유저 모임 가입
	public int meet_application_user(String meet_no, String user_no) {
		log.info("meet_application().....");
		return m_regi_repository.meet_application_user(meet_no, user_no);
	}
	
	// 모임 탈퇴
	public int meet_leave_user(String meet_no, String user_no) {
		return m_regi_repository.meet_leave_user(meet_no, user_no);
	}
	
	
	// 모임 정보 가져오기
	public MeetVO select_one_meet_info(String meet_no) {
		log.info("select_one_meet_info().....");
		return m_repository.select_one_meet_info(meet_no);
	}

	// 모임 수정
	public int meet_update(MeetVO mvo) {
		log.info("meet_update().....");
		return m_repository.meet_update(mvo);
	}




}

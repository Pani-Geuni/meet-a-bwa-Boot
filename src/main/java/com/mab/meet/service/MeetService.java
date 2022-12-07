/**
 * @author 전판근
 */

package com.mab.meet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.list.repository.MeetListRepository;
import com.mab.meet.model.LoginUserInfoVO;
import com.mab.meet.model.MeetActivityVO;
import com.mab.meet.model.MeetInfoVO;
import com.mab.meet.model.MeetUserVO;
import com.mab.meet.model.MeetVoteVO;
import com.mab.meet.repository.LoginUserInfoRepository;
import com.mab.meet.repository.MeetActivityRepository;
import com.mab.meet.repository.MeetInfoRepository;
import com.mab.meet.repository.MeetUserRepository;
import com.mab.meet.repository.MeetVoteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetService {

	
	@Autowired
	MeetInfoRepository meetInfoRepository;
	
	@Autowired
	MeetUserRepository meetUserRepository;
	
	@Autowired
	MeetActivityRepository activityRepository;
	
	@Autowired
	MeetVoteRepository voteRepository;
	
	@Autowired
	LoginUserInfoRepository loginUserRepository;
	
	
	public MeetService() {
		log.info("MeetService()...");
	}
	
	
	public MeetInfoVO select_one_meet_info(String meet_no) {
		MeetInfoVO vo = meetInfoRepository.select_one_meet_info(meet_no);
		
		return vo;
	}
	
	public List<MeetUserVO> select_all_meet_registered_member(String meet_no) {
		List<MeetUserVO> vos = meetUserRepository.select_all_meet_registered_member(meet_no);
		
		return vos;
	}
	
	public List<MeetActivityVO> select_all_activity_for_feed(String meet_no) {
		List<MeetActivityVO> vos = activityRepository.select_all_activity_for_feed(meet_no);
		
		return vos;
	}
	
	public List<MeetVoteVO> select_all_vote_meet(String meet_no) {
		List<MeetVoteVO> vos = voteRepository.select_all_vote_meet(meet_no);
		
		return vos;
	}
	
	public LoginUserInfoVO select_one_now_login_user(String user_no) {
		LoginUserInfoVO vo = loginUserRepository.select_one_now_login_user(user_no);
		
		return vo;
	}
	
	
	
}

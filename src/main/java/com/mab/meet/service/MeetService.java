/**
 * @author 전판근
 */

package com.mab.meet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.meet.model.MeetInfoVO;
import com.mab.meet.repository.MeetInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetService {

	
	@Autowired
	MeetInfoRepository meetInfoRepository;
	
	public MeetService() {
		log.info("MeetService()...");
	}
	
	
	public MeetInfoVO select_one_meet_info(String meet_no) {
		MeetInfoVO vo = meetInfoRepository.select_one_meet_info(meet_no);
		
		return vo;
	}
	
}

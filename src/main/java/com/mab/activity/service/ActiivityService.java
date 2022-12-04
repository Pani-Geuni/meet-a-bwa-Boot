package com.mab.activity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.activity.model.ActivityVO;
import com.mab.activity.repository.ActivityCreateUpdateRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActiivityService {
	
	@Autowired
	ActivityCreateUpdateRepository a_repository;

	public int activity_insert(ActivityVO avo) {
		log.info("activity_insert().....");
		return a_repository.activity_insert(avo);
	}

}

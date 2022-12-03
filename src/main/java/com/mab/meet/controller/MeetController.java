/**
 * @author 전판근
 */

package com.mab.meet.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.meet.model.MeetInfoVO;
import com.mab.meet.service.MeetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/meet")
public class MeetController {

	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Autowired
	MeetService service;
	
	@GetMapping(value = "/detail")
	public String meet_info(String meet_no, Model model) {
		
		Map<String, Object> map = new HashMap<>();
		
		MeetInfoVO mvo = service.select_one_meet_info(meet_no);
		
		log.info("mvo : {}", mvo);
		
		return "meet_info";
	}
}

package com.mab.main.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.main.model.MainActivityViewVO;
import com.mab.main.model.MainMeetViewVO;
import com.mab.main.service.MainActivityService;
import com.mab.main.service.MainMeetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Api(tags="HOME 컨트롤러")
@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	HttpSession session;
	
	@Autowired
	MainMeetService meet_service;
	
	@Autowired
	MainActivityService activity_service;
	
	
	//자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	
	@ApiOperation(value="메인 화면 로드", notes="메인 페이지 띄우는 컨트롤러")
	@GetMapping("/")
	public String main(Model model) {
		log.info("mainPage");
		
		List<MainMeetViewVO> meet_list = null;
		
		if(session.getAttribute("user_id") != null) {
			meet_list = meet_service.SQL_SELECT_ALL_COUNTY(null);
		}else {
			meet_list = meet_service.SQL_SELECT_ALL_LIKE();
		}
		
		List<MainActivityViewVO> activity_list = activity_service.SQL_SELECT_ALL_6();
		
		
		model.addAttribute("u_list", meet_list);
		model.addAttribute("a_list", activity_list);
		model.addAttribute("checkCategory", "전체");
		
		model.addAttribute("content", "thymeleaf/html/main/main");
		return "thymeleaf/layouts/main/layout_main";
	}

}

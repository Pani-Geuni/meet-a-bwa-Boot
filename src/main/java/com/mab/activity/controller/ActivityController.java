package com.mab.activity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.activity.model.ActivityVO;
import com.mab.activity.model.ActivityView;
import com.mab.activity.service.ActiivityFileService;
import com.mab.activity.service.ActiivityService;
import com.mab.meet.model.MeetVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Api(tags="액티비티 컨트롤러")
@RequestMapping("/meet-a-bwa")
public class ActivityController {
	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	ActiivityService service;

	@Autowired
	ActiivityFileService fileService;

	@Autowired
	HttpSession session;
	
	/**
	 * 액티비티 생성 폼
	 */
	@ApiOperation(value="액티비티 피드", notes="액티비티 메인 페이지입니다.")
	@GetMapping("/activity_main")
	public String activity_main(Model model, String activity_no) {
		log.info("/activity_main...");
		
		ActivityView avo = service.select_one_activity_feed_info(activity_no);
		String user_cnt = service.select_activity_user_cnt(activity_no);
		String like_cnt = service.select_activity_like_cnt(activity_no);
		List<String> user_no = service.select_activity_registered_member_user_no(activity_no);
		String regi_user_no = "";
		for (String st : user_no) {
			regi_user_no.concat(","+st);
		}
		
		model.addAttribute("user_cnt", user_cnt);
		model.addAttribute("like_cnt", like_cnt);
		model.addAttribute("regi_user_no", regi_user_no);
		model.addAttribute("avo", avo);
		model.addAttribute("content", "thymeleaf/html/activity/feed/main");
		model.addAttribute("title", "액티비티 피드");

		return "thymeleaf/layouts/activity/layout_activity";
	}
	
	
}

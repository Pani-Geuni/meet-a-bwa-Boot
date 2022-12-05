package com.mab.activity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ActivityInfoController {
	
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
	@ApiOperation(value="액티비티 생성", notes="액티비티 생성 페이지입니다.")
	@GetMapping("/activity_insert")
	public String activity_insert(Model model, String meet_no, String user_no) {
		log.info("/activity_insert...");
		
		meet_no="M1002";
		user_no="U1002";

		model.addAttribute("meet_no", meet_no);
		model.addAttribute("user_no", user_no);
		model.addAttribute("content", "thymeleaf/html/activity/insert/insert");
		model.addAttribute("title", "액티비티 생성");

		return "thymeleaf/layouts/activity/layout_info_activity";
	}
	
	/**
	 * 액티비티 생성 처리
	 * @throws ParseException 
	 */
	@ApiOperation(value="액티비티 생성 처리", notes="액티비티 생성 처리입니다.")
	@PostMapping("/activity_insertOK") // String meet_no, String user_no -> vo로 한번에 받아올 수 있음.
	public String activity_insertOK(Model model, String recruitment_stime, String recruitment_etime, String activity_stime, String activity_etime
			,ActivityVO avo, MultipartHttpServletRequest mtfRequest, @RequestParam(value = "multipartFile_activity") MultipartFile multipartFile_activity) throws ParseException {
		log.info("/activity_insertOK...");
		
		recruitment_stime = "2022-12-04";
		recruitment_etime = "2022-12-05";
		activity_stime = "2022-12-27";
		activity_etime = "2022-12-27";
		
		// String -> Date 타입 변환
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		Date r_s = sdf.parse(recruitment_stime);
		avo.setRecruitment_stime(r_s);
		
		Date r_e = sdf.parse(recruitment_etime);
		avo.setRecruitment_etime(r_e);
		
		Date a_s = sdf.parse(activity_stime);
		avo.setActivity_stime(a_s);
		
		Date a_e = sdf.parse(activity_etime);
		avo.setActivity_etime(a_e);
		
		// 공개/비공개 설정
		MeetVO mvo = service.activity_select_meet_private_state(avo.getMeet_no());
		avo.setPrivate_state(mvo.getPrivate_state());
		
		// 이미지 파일
		avo = fileService.activity_image_upload(avo, mtfRequest, multipartFile_activity);
		log.info("filupload activity:{}", avo);
		
		int result = service.activity_insert(avo);
		
		String rt ="";
		if(result==1) {
			rt="redirect:activity_feed?activity_no="+avo.getActivity_no();
		}else {
			rt="redirect:activity_insert"; 
		}
		
		return rt;
	}
	

	/**
	 * 액티비티 수정 폼
	 */
	@ApiOperation(value="액티비티 수정", notes="액티비티 수정 페이지입니다.")
	@GetMapping("/activity_update")
	public String activity_update(Model model, String activity_no) {
		log.info("/activity_update...");

		ActivityVO avo = service.select_one_activity_info(activity_no);
		
		// Date -> String 타입 변환

		log.info("avo:{}",avo);
		
		model.addAttribute("avo", avo);
		model.addAttribute("content", "thymeleaf/html/activity/update/update");
		model.addAttribute("title", "액티비티 수정");

		return "thymeleaf/layouts/activity/layout_info_activity";
	}
	
	/**
	 * 액티비티 수정 처리
	 * @throws ParseException 
	 */
	@ApiOperation(value="액티비티 수정 처리", notes="액티비티 수정 처리입니다.")
	@PostMapping("/activity_updateOK") // String meet_no, String user_no -> vo로 한번에 받아올 수 있음.
	public String activity_updateOK(Model model, String recruitment_stime, String recruitment_etime, String activity_stime, String activity_etime
			,ActivityVO avo, MultipartHttpServletRequest mtfRequest, @RequestParam(value = "multipartFile_activity") MultipartFile multipartFile_activity) throws ParseException {
		log.info("/activity_updateOK...");
		
		recruitment_stime = "2022-12-04";
		recruitment_etime = "2022-12-05";
		activity_stime = "2022-12-27";
		activity_etime = "2022-12-27";
		
		// String -> Date 타입 변환
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		Date r_s = sdf.parse(recruitment_stime);
		avo.setRecruitment_stime(r_s);
		
		Date r_e = sdf.parse(recruitment_etime);
		avo.setRecruitment_etime(r_e);
		
		Date a_s = sdf.parse(activity_stime);
		avo.setActivity_stime(a_s);
		
		Date a_e = sdf.parse(activity_etime);
		avo.setActivity_etime(a_e);
		
		// 공개/비공개 설정
		MeetVO mvo = service.activity_select_meet_private_state(avo.getMeet_no());
		avo.setPrivate_state(mvo.getPrivate_state());
		
		// 이미지 파일
		ActivityVO avo2 = service.select_one_activity_info(avo.getActivity_no());
		log.info("avo2 : {}", avo2.getActivity_image());
		log.info("avo : {}", avo.getActivity_image());
		if (!avo.getActivity_image().equals(avo2.getActivity_image())) {
			avo = fileService.activity_image_upload(avo,mtfRequest, multipartFile_activity);
			log.info("filupload activity:{}", avo);
		}
		
		int result = service.activity_update(avo);
		
		String rt ="";
		if(result==1) {
			rt="redirect:activity_feed?activity_no="+avo.getActivity_no();
		}else {
			rt="redirect:activity_update"; 
		}
		
		return rt;
	}
	

}

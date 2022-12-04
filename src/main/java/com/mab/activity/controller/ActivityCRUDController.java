package com.mab.activity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Api(tags="액티비티 컨트롤러")
@RequestMapping("/activity")
public class ActivityCRUDController {
	
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
	@GetMapping("/insert")
	public String activity_insert(Model model, String meet_no, String private_state) {
		log.info("/activity_insert...");

		model.addAttribute("meet_no", meet_no);
		model.addAttribute("private_state", private_state);
		model.addAttribute("content", "thymeleaf/html/activity/create/create");
		model.addAttribute("title", "액티비티 생성");

		return "thymeleaf/layouts/activity/layout_activity";
	}
	
	/**
	 * 액티비티 생성 처리
	 * @throws ParseException 
	 */
	@ApiOperation(value="액티비티 생성 처리", notes="액티비티 생성 처리입니다.")
	@PostMapping("/insertOK") // String meet_no, String private_state, String user_no 
	public String activity_insertOK(Model model, String recruitment_stime, String recruitment_etime, String activity_stime, String activity_etime
			,ActivityVO avo, MultipartHttpServletRequest mtfRequest, @RequestParam(value = "multipartFile_activity") MultipartFile multipartFile_activity) throws ParseException {
		log.info("/activity_insertOK...");
		
		recruitment_stime = "2022-12-04";
		recruitment_etime = "2022-12-05";
		activity_stime = "2022-12-27";
		activity_etime = "2022-12-27";
		String meet_no="M1002";
		String user_no="U1002";
		avo.setMeet_no(meet_no);
		avo.setUser_no(user_no);
		avo.setPrivate_state("F");
		
		// Date 타입 변환
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
//		MeetVO mvo = service.activity_select_meet_private_state(meet_no);
//		avo.setPrivate_state(mvo.getPrivate_state);
		
		// 이미지 파일
		avo = fileService.activity_image_upload(avo, mtfRequest, multipartFile_activity);
		log.info("filupload activity:{}", avo);
		
		// 관심사, 연령대 List<String> img_list = new ArrayList<String>(); , String a = img_list.stream().collect(Collectors.joining(", "));
		int result = service.activity_insert(avo);
		
		model.addAttribute("content", "thymeleaf/html/activity/create/create");
		model.addAttribute("title", "액티비티 생성");
		
		return "thymeleaf/layouts/activity/layout_activity";
	}

}

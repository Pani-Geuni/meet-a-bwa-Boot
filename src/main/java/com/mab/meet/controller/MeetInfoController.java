/**
 * @author 최진실
 */
package com.mab.meet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
import com.mab.activity.model.ActivityUserVO;
import com.mab.activity.model.ActivityVO;
import com.mab.meet.model.MeetVO;
import com.mab.meet.service.MeetInfoFileService;
import com.mab.meet.service.MeetInfoService;
import com.mab.user.model.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Api(tags = "모임 생성, 수정 컨트롤러")
@RequestMapping("/meet-a-bwa")
public class MeetInfoController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	MeetInfoService service;

	@Autowired
	MeetInfoFileService fileService;

	@Autowired
	HttpSession session;

	/**
	 * 모임 생성 폼
	 */
	@ApiOperation(value = "모임 생성", notes = "모임 생성 페이지입니다.")
	@GetMapping("/meet_insert")
	public String meet_insert(Model model, HttpServletRequest request) {
		log.info("/meet_insert...");

		Cookie[] cookies = request.getCookies();
		String user_no = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user_no")) {
					user_no = cookie.getValue();
				}
			}
		}
		
		UserVO uvo = service.select_one_meet_user_info(user_no);
		log.info("개설자 성별 : {}",uvo.getUser_gender());

		model.addAttribute("user_no", user_no);
		model.addAttribute("user_gender", uvo.getUser_gender());
		model.addAttribute("content", "thymeleaf/html/meet/insert/insert");
		model.addAttribute("title", "모임 생성");

		return "thymeleaf/layouts/meet/layout_info_meet";
	}

	/**
	 * 모임 생성 처리
	 * 
	 * @throws ParseException
	 */
	@ApiOperation(value = "모임 생성 처리", notes = "모임 생성 처리입니다.")
	@PostMapping("/meet_insertOK")
	public String meet_insertOK(Model model, MeetVO mvo, MultipartHttpServletRequest mtfRequest,
			@RequestParam(value = "multipartFile_meet") MultipartFile multipartFile_meet) throws ParseException {
		log.info("/meet_insertOK...");

		// 이미지 파일
		mvo = fileService.meet_image_upload(mvo, mtfRequest, multipartFile_meet);
		log.info("filupload activity:{}", mvo);

		// 엠블럼
		mvo.setMeet_emblem("default_emblem.png");

		// 공개/비공개 여부
		mvo.setPrivate_state("F");

		int result = service.meet_insert(mvo);

		String rt = "";
		if (result == 1) {
			MeetVO mvo2 = service.select_one_meet_no(mvo.getUser_no()); // meet_no
			if (mvo2!=null) {
				int flag = service.meet_application(mvo2.getMeet_no(),mvo.getUser_no()); // 개설자 가입 처리
				if (flag!=0) {
					rt = "redirect:meet-main.do?idx=" + mvo2.getMeet_no();
				}else {
					rt = "redirect:meet_insert";
				}
			}else {
				rt = "redirect:meet_insert";
			}
		} else {
			rt = "redirect:meet_insert";
		}

		return rt;
	}

	/**
	 * 모임 수정 폼
	 */
	@ApiOperation(value = "모임 수정", notes = "모임 수정 페이지입니다.")
	@GetMapping("/meet_update")
	public String meet_update(Model model, String meet_no, HttpServletRequest request) {
		log.info("/meet_update...");
		
		Cookie[] cookies = request.getCookies();
		String user_no = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user_no")) {
					user_no = cookie.getValue();
				}
			}
		}

		MeetVO mvo = service.select_one_meet_info(meet_no);
		log.info("mvo:{}", mvo);

		UserVO uvo = service.select_one_meet_user_info(user_no);
		log.info("개설자 성별 : {}",uvo.getUser_gender());

		model.addAttribute("user_gender", uvo.getUser_gender());

		model.addAttribute("mvo", mvo);
		model.addAttribute("content", "thymeleaf/html/meet/update/update");
		model.addAttribute("title", "모임 수정");

		return "thymeleaf/layouts/meet/layout_info_meet";
	}

	/**
	 * 모임 수정 처리
	 * 
	 * @throws ParseException
	 */
	@ApiOperation(value = "모임 수정 처리", notes = "모임 수정 처리입니다.")
	@PostMapping("/meet_updateOK")
	public String meet_updateOK(Model model, MeetVO mvo, MultipartHttpServletRequest mtfRequest,
			@RequestParam(value = "multipartFile_meet") MultipartFile multipartFile_meet) throws ParseException {
		log.info("/meet_updateOK...");
		log.info("mvo : {}",mvo);

		// 이미지 파일
		MeetVO mvo2 = service.select_one_meet_info(mvo.getMeet_no());
		log.info("mvo2 : {}",mvo2);
		if (!mvo.getMeet_image().equals(mvo2.getMeet_image())) {
			mvo = fileService.meet_image_upload(mvo, mtfRequest, multipartFile_meet);
			log.info("filupload meet:{}", mvo);
		}

		// 엠블럼
		mvo.setMeet_emblem("default_emblem.png");

		// 공개/비공개 여부
		mvo.setPrivate_state("F");

		int result = service.meet_update(mvo);

		String rt = "";
		if (result == 1) {
			rt = "redirect:meet-main.do?idx=" + mvo2.getMeet_no();
		} else {
			rt = "redirect:meet_update";
		}

		return rt;
	}

}

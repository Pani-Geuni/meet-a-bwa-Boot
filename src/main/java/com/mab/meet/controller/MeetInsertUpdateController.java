package com.mab.meet.controller;

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
import com.mab.meet.model.MeetVO;
import com.mab.meet.service.MeetInsertUpdateFileService;
import com.mab.meet.service.MeetInsertUpdateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Api(tags = "모임 생성, 수정 컨트롤러")
@RequestMapping("/meet-a-bwa")
public class MeetInsertUpdateController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	MeetInsertUpdateService service;

	@Autowired
	MeetInsertUpdateFileService fileService;

	@Autowired
	HttpSession session;

	/**
	 * 모임 생성 폼
	 */
	@ApiOperation(value = "모임 생성", notes = "모임 생성 페이지입니다.")
	@GetMapping("/meet_insert")
	public String meet_insert(Model model, String user_no) {
		log.info("/meet_insert...");

		user_no = "U1002";

		model.addAttribute("user_no", user_no);
		model.addAttribute("content", "thymeleaf/html/meet/create/create");
		model.addAttribute("title", "모임 생성");

		return "thymeleaf/layouts/meet/layout_insert_update_meet";
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
			rt = "redirect:meet-main.do?meet_no=" + mvo.getMeet_no();
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
	public String meet_update(Model model, String meet_no) {
		log.info("/meet_update...");

		MeetVO mvo = service.select_one_meet_info(meet_no);

		log.info("mvo:{}", mvo);

		model.addAttribute("mvo", mvo);
		model.addAttribute("content", "thymeleaf/html/meet/update/update");
		model.addAttribute("title", "모임 수정");

		return "thymeleaf/layouts/meet/layout_insert_update_meet";
	}

	/**
	 * 모임 수정 처리
	 * 
	 * @throws ParseException
	 */
	@ApiOperation(value = "모임 수정 처리", notes = "모임 수정 처리입니다.")
	@PostMapping("/meet_updateOK")
	public String meet_updateOK(Model model, MeetVO mvo, MultipartHttpServletRequest mtfRequest,
			@RequestParam(value = "multipartFile_activity") MultipartFile multipartFile_meet) throws ParseException {
		log.info("/meet_updateOK...");

		// 이미지 파일
		MeetVO mvo2 = service.select_one_meet_info(mvo.getMeet_no());
		if (multipartFile_meet.getSize()>0) {
			mvo = fileService.meet_image_upload(mvo, mtfRequest, multipartFile_meet);
			log.info("filupload meet:{}", mvo);
		}else {
			mvo.setMeet_image(mvo2.getMeet_image());
		}

		// 엠블럼
		mvo.setMeet_emblem("default_emblem.png");

		// 공개/비공개 여부
		mvo.setPrivate_state("F");

		int result = service.meet_update(mvo);

		String rt = "";
		if (result == 1) {
			rt = "redirect:meet-main.do?meet_no=" + mvo2.getMeet_no();
		} else {
			rt = "redirect:activity_update";
		}

		return rt;
	}

}
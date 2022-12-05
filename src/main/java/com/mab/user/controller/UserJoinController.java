/**
 * @author 강경석
 * 로그인,로그아웃 
 * 회원 탈퇴
 * 아이디, 비밀번호 찾기
 */
package com.mab.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.user.model.EmailVO;
import com.mab.user.model.UserVO;
import com.mab.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "회원가입 컨트롤러")
@Slf4j
@Controller
@RequestMapping("/meet-a-bwa")
public class UserJoinController {

	@Autowired
	UserService service;

	@Autowired
	HttpSession session;

	@Autowired
	UserSendEmail authSendEmail;

	// 자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	// **********************
	// 회원가입
	// **********************
	@ApiOperation(value = "회원가입", notes = "회원가입 입니다.")
	@GetMapping("/u_insert.do")
	public String u_insert(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("u_insert()...");


//		umvo.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + umvo.getUser_image());

//		model.addAttribute("umvo", umvo);

		model.addAttribute("content", "thymeleaf/html/user/join");
		model.addAttribute("title", "회원가입");

		return "thymeleaf/layouts/user/layout_activity";
	}

}// end class

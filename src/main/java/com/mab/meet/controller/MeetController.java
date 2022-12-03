/**
 * @author 전판근
 */

package com.mab.meet.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.meet.model.MeetInfoVO;
import com.mab.meet.service.MeetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "MEET 컨트롤러")
@Controller
@RequestMapping("/meet-a-bwa")
public class MeetController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	HttpSession session;

	@Autowired
	MeetService service;

	@ApiOperation(value = "모임 메인 화면", notes = "모임 메인 화면 띄우는 컨트롤러")
	@GetMapping(value = "/meet-main.do")
	public String meet_main(@RequestParam("idx") String meet_no, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		log.info("meet_main Page");

		String like_meet = request.getParameter("like_meet");
		String session_user_id = (String) session.getAttribute("user_id");

		String cookie_interest = "";
		String cookie_county = "";
		String cookie_nickName = "";
		String cookie_userNo = "";

		Map<String, Object> map = new HashMap<String, Object>();
		
		// 로그인 O
		if (session_user_id != null) {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user_interest")) {
					cookie_interest = cookie.getValue();
				} else if (cookie.getName().equals("user_county")) {
					cookie_county = cookie.getValue();
				} else if (cookie.getName().equals("nick_name")) {
					cookie_nickName = cookie.getValue();
				} else if (cookie.getName().equals("user_no")) {
					cookie_userNo = cookie.getValue();
				}
			}

			
			map.put("isLogin", true);
			map.put("nick_name", cookie_nickName);
			map.put("interest", cookie_interest);
			map.put("county", cookie_county);
			map.put("user_no", cookie_userNo);

			if (like_meet != null) {
				Cookie cookie = new Cookie("like_meet", like_meet);
				response.addCookie(cookie);
			}

			request.setAttribute("list", map);
		} else {
			map.put("isLogin", false);
			request.setAttribute("list", map);
		}

		MeetInfoVO mvo = service.select_one_meet_info(meet_no);

		log.info("mvo : {}", mvo);

		// 모임 정보 불러오기
		model.addAttribute("mvo", mvo);
		
		model.addAttribute("list", map);

		model.addAttribute("content", "thymeleaf/html/meet/feed/feed");
		return "thymeleaf/layouts/meet/layout_meet";
	}
}

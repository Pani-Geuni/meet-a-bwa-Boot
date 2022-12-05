/**
 * @author 전판근
 */

package com.mab.list.controller;

import java.util.HashMap;
import java.util.List;
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

import com.mab.main.model.MainMeetViewVO;
import com.mab.meet.service.MeetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "LIST 컨트롤러")
@Controller
@RequestMapping("/meet-a-bwa")
public class ListController {
	
	@Autowired
	MeetService meetService;

	@ApiOperation(value = "모임 리스트 화면", notes = "모임 리스트 컨트롤러")
	@GetMapping(value = "/meet-list.do")
	public String meet_list(String type, String typeData, String searchWord, 
			HttpServletRequest request, HttpServletResponse response, Model model) {

		String like_meet = request.getParameter("like_meet");

		HttpSession session = request.getSession();
		String session_user_id = (String) session.getAttribute("user_id");

		String cookie_interest = "";
		String cookie_county = "";
		String cookie_nickName = "";
		
		Map<String, Object> map = new HashMap<String, Object>();

		// 로그인 O
		if (session_user_id != null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) { // NullPointerException 처리
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("user_interest")) {
						cookie_interest = cookie.getValue();
					} else if (cookie.getName().equals("user_county")) {
						cookie_county = cookie.getValue();
					} else if (cookie.getName().equals("nick_name")) {
						cookie_nickName = cookie.getValue();
					}
				}
			}

			map.put("isLogin", true);
			map.put("nick_name", cookie_nickName);
			map.put("interest", cookie_interest);
			map.put("county", cookie_county);

			if (like_meet != null) {
				Cookie cookie = new Cookie("like_meet", like_meet);
				response.addCookie(cookie);
			}

		} else {
			Cookie[] cookies = request.getCookies();

			if (cookies != null) { // NullPointerException 처리
				for (int i = 0; i < cookies.length; i++) {
					// 유효시간을 0초 설정 삭제하는 효과
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}
			
			map.put("isLogin", false);
		}

//		String type = request.getParameter("type");
//		String typeData = request.getParameter("typeData");
//		String searchWord = request.getParameter("searchWord");
//		
		log.info("isLogin :: {}", map);
		List<MainMeetViewVO> meet_list = null;
		
		if (type.equals("like")) {
			meet_list = meetService.select_all_more_like(searchWord);
		} else if (type.equals("interest")) {
			if (typeData.equals("전체")) {
				meet_list = meetService.select_all_more_like(searchWord);
			} else {
				meet_list = meetService.select_all_more_interest(typeData, searchWord);
			}
		} else if (type.equals("county")) {
			meet_list = meetService.select_all_more_county(typeData, searchWord);
		}
		
		log.info("type : {}", type);
		log.info("typeData : {}", typeData);
		log.info("searchWord : {}", searchWord);
		
		log.info("meet list : {}", meet_list);
		
		model.addAttribute("meet_list", meet_list);
		model.addAttribute("meet_list_cnt", meet_list.size());
		model.addAttribute("list", map);
		
		model.addAttribute("content", "thymeleaf/html/main/meet_list");
		return "thymeleaf/layouts/main/layout_list";
	}
}

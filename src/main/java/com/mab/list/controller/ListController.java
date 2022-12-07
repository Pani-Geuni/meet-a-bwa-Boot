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

import com.mab.list.service.ListService;
import com.mab.main.model.MainActivityViewVO;
import com.mab.main.model.MainMeetViewVO;
import com.mab.main.service.MainActivityService;
import com.mab.main.service.MainMeetService;
import com.mab.user.model.UserVO;
import com.mab.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "LIST 컨트롤러")
@Controller
@RequestMapping("/meet-a-bwa")
public class ListController {

	@Autowired
	HttpSession session;

	@Autowired
	ListService listService;

	@Autowired
	MainActivityService mainActivityService;
	
	@Autowired
	MainMeetService mainMeetService;

	@Autowired
	UserService user_service;

	@ApiOperation(value = "모임 리스트 화면", notes = "모임 리스트 컨트롤러")
	@GetMapping(value = "/meet-list.do")
	public String meet_list(String type, String typeData, String searchWord, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String session_user_id = (String) session.getAttribute("user_id");

		Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기
		String user_no = null;

		Map<String, Object> map = new HashMap<String, Object>();

		if (cookies != null) {
			for (Cookie c : cookies) {
				String name = c.getName(); // 쿠키 이름 가져오기
				String value = c.getValue(); // 쿠키 값 가져오기
				if (name.equals("user_no")) {
					user_no = value;
				}
			}
		}

		// 로그인 O
		if (session_user_id != null) {
			UserVO uvo = user_service.select_user_info(user_no);

			map.put("nick_name", uvo.getUser_nickname());
			map.put("interest", uvo.getUser_interest());
			map.put("county", uvo.getUser_county());
			model.addAttribute("list", map);

			String like_meet_txt = "";
			List<String> like_meet_list = mainMeetService.SELECT_ALL_LIKE_USER_NO(user_no);
			for (String meet_no : like_meet_list) {
				like_meet_txt += meet_no + ",";
			}

			model.addAttribute("like_meet_list", like_meet_txt);

		} else {

			if (cookies != null) { // NullPointerException 처리
				for (int i = 0; i < cookies.length; i++) {
					// 유효시간을 0초 설정 삭제하는 효과
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}

			map.put("isLogin", false);
		}

		log.info("isLogin :: {}", map);
		List<MainMeetViewVO> meet_list = null;

		if (type.equals("like")) {
			meet_list = listService.select_all_more_like(searchWord);
		} else if (type.equals("interest")) {
			if (typeData.equals("전체")) {
				meet_list = listService.select_all_more_like(searchWord);
			} else {
				meet_list = listService.select_all_more_interest(typeData, searchWord);
			}
		} else if (type.equals("county")) {
			meet_list = listService.select_all_more_county(typeData, searchWord);
		}

		log.info("type : {}", type);
		log.info("typeData : {}", typeData);
		log.info("searchWord : {}", searchWord);

		log.info("meet list : {}", meet_list);

		model.addAttribute("meet_list", meet_list);
		model.addAttribute("meet_list_cnt", meet_list.size());
		model.addAttribute("list", map);
		model.addAttribute("page", "meet_list");

		model.addAttribute("content", "thymeleaf/html/main/meet_list");
		return "thymeleaf/layouts/main/layout_list";
	}

	@ApiOperation(value = "모임 리스트 화면", notes = "모임 리스트 컨트롤러")
	@GetMapping(value = "/activity-list.do")
	public String activity_list(String category, String searchWord, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String session_user_id = (String) session.getAttribute("user_id");

		Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기
		String user_no = null;

		Map<String, Object> map = new HashMap<String, Object>();

		if (cookies != null) {
			for (Cookie c : cookies) {
				String name = c.getName(); // 쿠키 이름 가져오기
				String value = c.getValue(); // 쿠키 값 가져오기
				if (name.equals("user_no")) {
					user_no = value;
				}
			}
		}

		// 로그인 O
		if (session_user_id != null) {
			UserVO uvo = user_service.select_user_info(user_no);

			map.put("nick_name", uvo.getUser_nickname());
			map.put("interest", uvo.getUser_interest());
			map.put("county", uvo.getUser_county());
			model.addAttribute("list", map);

			String like_activity_txt = "";
			List<String> like_activity_list = mainActivityService.SELECT_ALL_LIKE_USER_NO(user_no);
			for(String activity_no : like_activity_list) {
				like_activity_txt += activity_no+",";
			}

			model.addAttribute("like_activity_list", like_activity_txt);

		} else {

			if (cookies != null) { // NullPointerException 처리
				for (int i = 0; i < cookies.length; i++) {
					// 유효시간을 0초 설정 삭제하는 효과
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}

			map.put("isLogin", false);
		}

		List<MainActivityViewVO> acti_list = null;

		log.info("category : {}", category);
		log.info("searchWord : {}", searchWord);

		if (category.equals("전체")) {
			acti_list = listService.select_all_more_like_acti(searchWord);
		} else {
			acti_list = listService.select_all_more_interest_acti(category, searchWord);
		}

		log.info("acti list : {}", acti_list);

		model.addAttribute("acti_list", acti_list);
		model.addAttribute("acti_list_cnt", acti_list.size());
		model.addAttribute("list", map);
		model.addAttribute("page", "activity_list");

		model.addAttribute("content", "thymeleaf/html/main/acti_list");
		return "thymeleaf/layouts/main/layout_list";
	}
}

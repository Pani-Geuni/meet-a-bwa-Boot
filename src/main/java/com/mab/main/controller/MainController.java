/**
 * @author 김예은
 */
package com.mab.main.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	
	@Autowired
	UserService user_service;
	
	
	
	//자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	
	@ApiOperation(value="메인 화면 로드", notes="메인 페이지 띄우는 컨트롤러")
	@GetMapping("/")
	public String main(Model model, HttpServletRequest req){
		log.info("mainPage");
		
		Cookie[] cookies = req.getCookies(); // 모든 쿠키 가져오기
		String user_no = null;
		
	    if(cookies!=null){
	        for (Cookie c : cookies) {
	            String name = c.getName(); // 쿠키 이름 가져오기
	            String value = c.getValue(); // 쿠키 값 가져오기
	            if (name.equals("user_no")) {
	            	user_no = value;
	            }
	        }
	    }
		
		List<MainMeetViewVO> meet_list = null;
		
		/* 로그인 되어 있을 시*/
		if(session.getAttribute("user_id") != null) {
			UserVO uvo = user_service.select_user_info(user_no);
			
			/* 회원이 설정한 관심사가 있을 때 */
			if(uvo.getUser_interest() != null) {
				/* 회원이 설정한 관심사가 여러개일 때 */
				if(uvo.getUser_interest().contains(",")) {
					String[] interest_arr = null;
					interest_arr = uvo.getUser_interest().split(",");
					meet_list = meet_service.SQL_SELECT_ALL_COUNTY(interest_arr[0]);
				}
				/* 회원이 설정한 관심사가 하나일 때 */
				else 
					meet_list = meet_service.SQL_SELECT_ALL_COUNTY(uvo.getUser_interest());
			}
			
			for(MainMeetViewVO mvo : meet_list) {
				if(mvo.getMeet_age() != null) {
					if(mvo.getMeet_age().contains(",")) {
						mvo.setMeet_age_arr(mvo.getMeet_age().split(","));
					}else {
						String[] tmp = new String[1];
						tmp[0] = mvo.getMeet_age();
						mvo.setMeet_age_arr(tmp);
					}
				}
			}
			
			List<String> like_meet_list = meet_service.SELECT_ALL_LIKE_USER_NO(user_no);
			List<String> like_activity_list = activity_service.SELECT_ALL_LIKE_USER_NO(user_no);
			
			model.addAttribute("like_meet_list", like_meet_list);
			model.addAttribute("like_activity_list", like_activity_list);
		}
		/* 로그인 안되어 있을 시*/
		else {
			meet_list = meet_service.SQL_SELECT_ALL_LIKE();
			for(MainMeetViewVO mvo : meet_list) {
				if(mvo.getMeet_age() != null) {
					if(mvo.getMeet_age().contains(",")) {
						mvo.setMeet_age_arr(mvo.getMeet_age().split(","));
					}else {
						String[] tmp = new String[1];
						tmp[0] = mvo.getMeet_age();
						mvo.setMeet_age_arr(tmp);
					}
				}
			}
		}
		
		List<MainActivityViewVO> activity_list = activity_service.SQL_SELECT_ALL_6();
		for(MainActivityViewVO avo : activity_list) {
			if(avo.getActivity_age() != null) {
				if(avo.getActivity_age().contains(",")) {
					avo.setActivity_age_arr(avo.getActivity_age().split(","));
				}else {
					String[] tmp = new String[1];
					tmp[0] = avo.getActivity_age();
					avo.setActivity_age_arr(tmp);
				}
			}
		}
		
		
		model.addAttribute("u_list", meet_list);
		model.addAttribute("a_list", activity_list);
		model.addAttribute("checkCategory", "전체");
		
		model.addAttribute("content", "thymeleaf/html/main/main");
		return "thymeleaf/layouts/main/layout_main";
	}
	
	@ApiOperation(value="카테고리별 액티비티 리스트 정보 셀렉터", notes="메인에서 카테고리별 액티비티 리스트 정보 가져오는 컨트롤러")
	@GetMapping("/select_activity_category")
	@ResponseBody
	public String select_activity_category(String category){
		List<MainActivityViewVO> list = null;
		log.info("category : {}", category);
		
		if(category.equals("전체")) {
			list = activity_service.SQL_SELECT_ALL_6();
		}else {
			list = activity_service.SQL_SELECT_CATEGORY_6(category);
		}
		
		for(MainActivityViewVO avo : list) {
			if(avo.getActivity_age() != null) {
				if(avo.getActivity_age().contains(",")) {
					avo.setActivity_age_arr(avo.getActivity_age().split(","));
				}else {
					String[] tmp = new String[1];
					tmp[0] = avo.getActivity_age();
					avo.setActivity_age_arr(tmp);
				}
			}
		}
		
		String json = gson.toJson(list);
		return json;
	}

}

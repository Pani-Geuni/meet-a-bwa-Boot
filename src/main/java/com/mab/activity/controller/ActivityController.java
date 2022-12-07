package com.mab.activity.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.activity.model.ActivityUserVO;
import com.mab.activity.model.ActivityView;
import com.mab.activity.model.ActivityVoteListView;
import com.mab.activity.service.ActiivityFileService;
import com.mab.activity.service.ActiivityService;
import com.mab.event.model.EventVO;
import com.mab.meet.model.MeetVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Api(tags = "액티비티 컨트롤러")
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
	 * 액티비티 피드
	 */
	@ApiOperation(value = "액티비티 피드", notes = "액티비티 메인 페이지입니다.")
	@GetMapping("/activity_main")
	public String activity_main(Model model, String activity_no, HttpServletRequest request) {
		log.info("/activity_main...");

		Cookie[] cookies = request.getCookies();
		String user_no = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user_no")) {
					user_no = cookie.getValue();
				}
			}
		}

		ActivityView avo = service.select_one_activity_feed_info(activity_no); // 아래 재사용
		String user_cnt = service.select_activity_user_cnt(activity_no); // 아래 재사용
		String like_cnt = service.select_activity_like_cnt(activity_no);
		List<String> regi_user_no = service.select_activity_registered_member_user_no(activity_no);
		log.info("regi_user_no : {}", regi_user_no);
//		String regi_user_no = "";
//		for (String st : user_no) {
//			regi_user_no.concat(","+st);

//		}

		List<ActivityVoteListView> vvos = service.select_activity_vote_list(activity_no);
		List<EventVO> evos = service.select_activity_event_list(activity_no);

		model.addAttribute("cookie", user_no);
		model.addAttribute("user_cnt", user_cnt);
		model.addAttribute("like_cnt", like_cnt);
		model.addAttribute("regi_user_no", regi_user_no);
		model.addAttribute("avo", avo);
		model.addAttribute("vvos", vvos);
		model.addAttribute("evos", evos);
		model.addAttribute("content", "thymeleaf/html/activity/feed/main");
		model.addAttribute("title", "액티비티 피드");

		return "thymeleaf/layouts/activity/layout_activity";
	}

	/**
	 * 액티비티 신청
	 */
	@ApiOperation(value = "액티비티 신청", notes = "액티비티 신청 처리입니다.")
	@GetMapping("/activity_register")
	@ResponseBody
	public String activity_register(Model model, String activity_no, String user_no) {
		log.info("/activity_register...");
		log.info("activity_no...:{}", activity_no);
		log.info("user_no...:{}", user_no);

		Map<String, String> map = new HashMap<String, String>();

		// user_no 가져온 후 조건 비교
		ActivityUserVO uvo = service.select_one_activity_user_info(user_no); // 해당 유저 정보
		ActivityView avo = service.select_one_activity_feed_info(activity_no); // 액티비티 피드 정보
		String user_cnt = service.select_activity_user_cnt(activity_no); // 멤버 수

		String register_no = service.select_one_meet_registered_userinfo(avo.getMeet_no(), user_no); // 액티비티가 속한 모임에 유저가
																										// 가입했는지 여부

		Boolean condition = true;

		if (register_no != null) { // 모임 가입 유무
			if (avo.getActivity_nop() > Integer.parseInt(user_cnt)) { // 액티비티 인원 수 초과 유무 -> 프론트에서 처리 가능
				if (avo.getActivity_gender() != "무관") { // 성별 조건 검사
					if (!avo.getActivity_gender().equals(uvo.getUser_gender())) {
						log.info("avo.getActivity_gender() : {}", avo.getActivity_gender());
						log.info("uvo.getUser_gender()() : {}", uvo.getUser_gender());

						map.put("result", "0"); // 조건 불충족
						condition = false;
					}
				}
				if (avo.getActivity_age() != null) { // 연령대 조건 검사
					// 현재 년도
					Calendar now = Calendar.getInstance();
					Integer currentYear = now.get(Calendar.YEAR);
					log.info("currentYear : {}", currentYear);

					SimpleDateFormat format = new SimpleDateFormat("yyyy");
					String stringBirthYear = format.format(uvo.getUser_birth());

					// 태어난 년도
					Integer birthYear = Integer.parseInt(stringBirthYear);
					log.info("birthYear : {}", birthYear);

					// 현재 년도 - 태어난 년도 => 나이 (만나이X)
					String age_result = String.valueOf(currentYear - birthYear + 1);
					log.info("age_result : {}", age_result);

					StringBuffer sb = new StringBuffer();
					sb.append(age_result);
					sb.setCharAt(1, '0');
					log.info("age_group : {}", sb);

					if (!avo.getActivity_age().contains(sb)) {
						map.put("result", "0"); // 조건 불충족
						condition = false;
					}
				}
			} else {
				map.put("result", "2"); // 인원수 초과
				condition = false;
			}
		} else {
			map.put("result", "3"); // 모임 미가입
			condition = false;
		}

		if (condition) {
			int result = service.activity_application(activity_no, user_no); // 가입 처리
			if (result == 1) {
				map.put("result", "1");
				log.info("success");
			} else {
				map.put("result", "0");
				log.info("fail");
			}
		}

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 액티비티 탈퇴
	 */
	@ApiOperation(value = "액티비티 탈퇴", notes = "액티비티 탈퇴 처리입니다.")
	@GetMapping("/activity_withdrawal")
	@ResponseBody
	public String activity_withdrawal(Model model, String activity_no, String user_no) {
		log.info("/activity_withdrawal...");
		log.info("activity_no...:{}", activity_no);

		Map<String, String> map = new HashMap<String, String>();

		int result = service.activity_withdrawal(activity_no, user_no); // 탈퇴 처리
		if (result == 1) {
			map.put("result", "1");
			log.info("success");
		} else {
			map.put("result", "0");
			log.info("fail");
		}

		String json = gson.toJson(map);

		return json;
	}

}

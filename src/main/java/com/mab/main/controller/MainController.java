/**
 * @author 김예은
 */

package com.mab.main.controller;

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
import com.mab.activity.model.ActivityLikeVO;
import com.mab.main.model.MainActivityViewVO;
import com.mab.main.model.MainMeetViewVO;
import com.mab.main.service.MainActivityService;
import com.mab.main.service.MainMeetService;
import com.mab.meet.model.MeetLikeVO;
import com.mab.user.model.UserVO;
import com.mab.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "HOME 컨트롤러")
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

	// 자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@ApiOperation(value = "메인 화면 로드", notes = "메인 페이지 띄우는 컨트롤러")
	@GetMapping("/")
	public String main(Model model, HttpServletRequest req) {
		log.info("mainPage");

		Cookie[] cookies = req.getCookies(); // 모든 쿠키 가져오기
		String user_no = null;

		if (cookies != null) {
			for (Cookie c : cookies) {
				String name = c.getName(); // 쿠키 이름 가져오기
				String value = c.getValue(); // 쿠키 값 가져오기
				if (name.equals("user_no")) {
					user_no = value;
				}
			}
		}

		List<MainMeetViewVO> meet_list = null;
		List<MainMeetViewVO> meet_list_interest_recommend = null;

		/* 로그인 되어 있을 시 */
		if (session.getAttribute("user_id") != null) {
			UserVO uvo = user_service.select_user_info(user_no);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nick_name", uvo.getUser_nickname());
			map.put("interest", uvo.getUser_interest());
			map.put("county", uvo.getUser_county());
			model.addAttribute("list", map);

			/* 회원이 설정한 관심사가 있을 때 */
			if (uvo.getUser_interest() != null) {
				Map<String, Object> interest_map = new HashMap<String, Object>();
				
				/* 회원이 설정한 관심사가 여러개일 때 */
				if (uvo.getUser_interest().contains(",")) {
					String[] interest_arr = null;
					interest_arr = uvo.getUser_interest().split(",");
					meet_list_interest_recommend = meet_service.SQL_SELECT_ALL_INTEREST(interest_arr[0]);
					interest_map.put("interests", interest_arr);
				}
				/* 회원이 설정한 관심사가 하나일 때 */
				else {
					meet_list_interest_recommend = meet_service.SQL_SELECT_ALL_INTEREST(uvo.getUser_interest());
					interest_map.put("interests", uvo.getUser_interest());
				}
				
				model.addAttribute("interest_list", interest_map);
				
				/* 회원의 관심사별 모임 추천 */
				for (MainMeetViewVO mvo : meet_list_interest_recommend) {
					if (mvo.getMeet_age() != null) {
						if (mvo.getMeet_age().contains(",")) {
							mvo.setMeet_age_arr(mvo.getMeet_age().split(","));
						} else {
							String[] tmp = new String[1];
							tmp[0] = mvo.getMeet_age();
							mvo.setMeet_age_arr(tmp);
						}
					}
					if (Integer.parseInt(mvo.getLike_cnt()) > 99) {
						mvo.setLike_cnt("99+");
					}
					mvo.setMeet_image("https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/meet/" + mvo.getMeet_image());
				}
				
				model.addAttribute("interest_meet_list", meet_list_interest_recommend);
			}
			
			
			/* 회원 지역 내에 모임 추천 */
			meet_list = meet_service.SQL_SELECT_ALL_COUNTY(uvo.getUser_county());
			
			for (MainMeetViewVO mvo : meet_list) {
				if (mvo.getMeet_age() != null) {
					if (mvo.getMeet_age().contains(",")) {
						mvo.setMeet_age_arr(mvo.getMeet_age().split(","));
					} else {
						String[] tmp = new String[1];
						tmp[0] = mvo.getMeet_age();
						mvo.setMeet_age_arr(tmp);
					}
				}
				if (Integer.parseInt(mvo.getLike_cnt()) > 99) {
					mvo.setLike_cnt("99+");
				}
				mvo.setMeet_image("https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/meet/" + mvo.getMeet_image());
			}

			String like_meet_txt = "";
			List<String> like_meet_list = meet_service.SELECT_ALL_LIKE_USER_NO(user_no);
			for (String meet_no : like_meet_list) {
				like_meet_txt += meet_no + ",";
			}

			String like_activity_txt = "";
			List<String> like_activity_list = activity_service.SELECT_ALL_LIKE_USER_NO(user_no);
			for (String activity_no : like_activity_list) {
				like_activity_txt += activity_no + ",";
			}

			model.addAttribute("like_meet_list", like_meet_txt);
			model.addAttribute("like_activity_list", like_activity_txt);
			model.addAttribute("user_county", uvo.getUser_county());
		}
		
		/* 로그인 안되어 있을 시 */
		else {
			meet_list = meet_service.SQL_SELECT_ALL_LIKE();
			for (MainMeetViewVO mvo : meet_list) {
				if (mvo.getMeet_age() != null) {
					if (mvo.getMeet_age().contains(",")) {
						mvo.setMeet_age_arr(mvo.getMeet_age().split(","));
					} else {
						String[] tmp = new String[1];
						tmp[0] = mvo.getMeet_age();
						mvo.setMeet_age_arr(tmp);
					}
				}
				if (Integer.parseInt(mvo.getLike_cnt()) > 99) {
					mvo.setLike_cnt("99+");
				}
				
				mvo.setMeet_image("https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/meet/" + mvo.getMeet_image());
			}
		}

		List<MainActivityViewVO> activity_list = activity_service.SQL_SELECT_ALL_6();
		for (MainActivityViewVO avo3 : activity_list) {
			if (avo3.getActivity_age() != null) {
				if (avo3.getActivity_age().contains(",")) {
					avo3.setActivity_age_arr(avo3.getActivity_age().split(","));
				}else {
					String[] tmp = new String[1];
					tmp[0] = avo3.getActivity_age();
					avo3.setActivity_age_arr(tmp);
				}
			}
			log.info("avo.getActivity_image() : {}", avo3.getActivity_image());
			avo3.setActivity_image("https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/activity/" + avo3.getActivity_image().trim());
			log.info("avo.getActivity_images() : {}", avo3.getActivity_image());
			
			if (Integer.parseInt(avo3.getLike_cnt()) > 99) {
				avo3.setLike_cnt("99+");
			}
		}
		
		List<MainActivityViewVO> deadline_activity_list = activity_service.SQL_SELECT_IMMINENT_DEADLINE();
		for (MainActivityViewVO avo2 : deadline_activity_list) {
			if (avo2.getActivity_age() != null) {
				if (avo2.getActivity_age().contains(",")) {
					avo2.setActivity_age_arr(avo2.getActivity_age().split(","));
				}else {
					String[] tmp = new String[1];
					tmp[0] = avo2.getActivity_age();
					avo2.setActivity_age_arr(tmp);
				}
			}
			log.info("avo2.getActivity_image() : {}", avo2.getActivity_image());
			avo2.setActivity_image("https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/activity/" + avo2.getActivity_image());
			log.info("avo2.getActivity_image() : {}", avo2.getActivity_image());
			
			if (Integer.parseInt(avo2.getLike_cnt()) > 99) {
				avo2.setLike_cnt("99+");
			}
		}
		
		model.addAttribute("u_list", meet_list);
		model.addAttribute("a_list", activity_list);
		log.info("avo.activity_list() : {}", activity_list);
		model.addAttribute("a_deadline_list", deadline_activity_list);
		model.addAttribute("checkCategory", "전체");

		model.addAttribute("content", "thymeleaf/html/main/main");
		return "thymeleaf/layouts/main/layout_main";
	}
	
	@ApiOperation(value = "괌심사별 모임 리스트 정보 셀렉터", notes = "메인에서 괌심사별 모임 리스트 정보 가져오는 컨트롤러")
	@GetMapping("/select_meet_interest")
	@ResponseBody
	public String select_meet_interest(String interest) {
		log.info("select_meet_interest...");
		log.info("interest : {}", interest);
		
		List<MainMeetViewVO> list = null;
		list = meet_service.SQL_SELECT_ALL_INTEREST(interest);
		
		for (MainMeetViewVO mvo : list) {
			if (mvo.getMeet_age() != null) {
				if (mvo.getMeet_age().contains(",")) {
					mvo.setMeet_age_arr(mvo.getMeet_age().split(","));
				} else {
					String[] tmp = new String[1];
					tmp[0] = mvo.getMeet_age();
					mvo.setMeet_age_arr(tmp);
				}
			}
			
			if (Integer.parseInt(mvo.getLike_cnt()) > 99) {
				mvo.setLike_cnt("99+");
			}
			mvo.setMeet_image("https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/meet/" + mvo.getMeet_image());
		}
		
		String json = gson.toJson(list);
		return json;
	}

	@ApiOperation(value = "카테고리별 액티비티 리스트 정보 셀렉터", notes = "메인에서 카테고리별 액티비티 리스트 정보 가져오는 컨트롤러")
	@GetMapping("/select_activity_category")
	@ResponseBody
	public String select_activity_category(String category) {
		log.info("select_activity_category...");
		log.info("category : {}", category);
		
		List<MainActivityViewVO> list = null;

		if (category.equals("전체")) {
			list = activity_service.SQL_SELECT_ALL_6();
		} else {
			list = activity_service.SQL_SELECT_CATEGORY_6(category);
		}

		for (MainActivityViewVO avo : list) {
			if (avo.getActivity_age() != null) {
				if (avo.getActivity_age().contains(",")) {
					avo.setActivity_age_arr(avo.getActivity_age().split(","));
				} else {
					String[] tmp = new String[1];
					tmp[0] = avo.getActivity_age();
					avo.setActivity_age_arr(tmp);
				}
			}
			avo.setActivity_image("https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/activity/" + avo.getActivity_image());
			
			if(Integer.parseInt(avo.getLike_cnt()) > 99){
				avo.setLike_cnt("99+");
			}
		}

		String json = gson.toJson(list);
		return json;
	}

	@ApiOperation(value = "메인 - 모임 좋아요 추가", notes = "메인에서 좋아요 추가하는 컨트롤러")
	@GetMapping("/main_meet_like_insert")
	@ResponseBody
	public String main_meet_like_insert(MeetLikeVO vo) {
		Map<String, String> map = new HashMap<String, String>();
		int result = meet_service.insert_meet_like(vo);

		if (result == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}

		String json = gson.toJson(map);
		return json;
	}

	@ApiOperation(value = "메인 - 모임 좋아요 삭제", notes = "메인에서 좋아요 삭제하는 컨트롤러")
	@GetMapping("/main_meet_like_delete")
	@ResponseBody
	public String main_meet_like_delete(MeetLikeVO vo) {
		Map<String, String> map = new HashMap<String, String>();
		int result = meet_service.delete_meet_like(vo);

		if (result == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}

		String json = gson.toJson(map);
		return json;
	}

	@ApiOperation(value = "메인 - 액티비티 좋아요 추가", notes = "메인에서 액티비티 좋아요 추가하는 컨트롤러")
	@GetMapping("/main_activity_like_insert")
	@ResponseBody
	public String main_activity_like_insert(ActivityLikeVO vo) {
		Map<String, String> map = new HashMap<String, String>();
		int result = activity_service.insert_activity_like(vo);

		if (result == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}

		String json = gson.toJson(map);
		return json;
	}

	@ApiOperation(value = "메인 - 액티비티 좋아요 삭제", notes = "메인에서 액티비티 좋아요 삭제하는 컨트롤러")
	@GetMapping("/main_activity_like_delete")
	@ResponseBody
	public String main_activity_like_delete(ActivityLikeVO vo) {
		Map<String, String> map = new HashMap<String, String>();
		int result = activity_service.delete_activity_like(vo);

		if (result == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}

		String json = gson.toJson(map);
		return json;
	}

}

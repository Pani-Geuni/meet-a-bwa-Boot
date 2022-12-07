/**
 * @author 전판근
 */

package com.mab.meet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.comment.service.CommentService;
import com.mab.meet.model.MeetActivityVO;
import com.mab.meet.model.MeetInfoVO;
import com.mab.meet.model.MeetUserVO;
import com.mab.meet.model.MeetVoteVO;
import com.mab.meet.service.MeetService;
import com.mab.meetboard.model.MBUserVO;
import com.mab.meetboard.service.MeetBoardService;

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
	
	@Autowired
	MeetBoardService boardService;
	
	@Autowired
	CommentService commentService;

	@ApiOperation(value = "모임 메인 화면", notes = "모임 메인 화면 띄우는 컨트롤러")
	@GetMapping(value = "/meet-main.do")
	public String meet_main(@RequestParam("idx") String meet_no, HttpServletRequest request,
			HttpServletResponse response, Model model) throws ParseException {
		
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

			model.addAttribute("list", map);
		} else {
			map.put("isLogin", false);
			model.addAttribute("list", map);
		}
		log.info("list : {}", map);

		// 모임 정보 불러오기
		MeetInfoVO mvo = service.select_one_meet_info(meet_no);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String meet_date = formatter.format(mvo.getMeet_date());
		
		String[] meet_age_split = mvo.getMeet_age().split(",");
		List<String> meet_age_list = new ArrayList<>();
		for (String age : meet_age_split) {
			meet_age_list.add(age);
		}
		log.info("mvo : {}", mvo);
		
		// 모임 가입자 리스트 불러오기
		List<MeetUserVO> member_list = service.select_all_meet_registered_member(meet_no);
		log.info("member list : {}", member_list);
		
		List<String> user_no_list = new ArrayList<>();
		for (MeetUserVO vo : member_list) {
			user_no_list.add(vo.getUser_no());
		}
		log.info("user no list : {}", user_no_list);
		
		
		// 모임 게시글 피드 불러오기
		List<MBUserVO> feed = boardService.select_all_board_feed(meet_no);
		log.info("feed : {}", feed);
		
		// 피드의 댓글 개수 불러오기
		for (MBUserVO post : feed) {
			Integer comment_cnt = commentService.select_comment_cnt(post.getBoard_no());
			post.setComment_cnt(comment_cnt);
		}
		
		// 액티비티 불러오기
		List<MeetActivityVO> activities = service.select_all_activity_for_feed(meet_no);
		log.info("activities : {}", activities);
		
		// 투표 불러오기
		List<MeetVoteVO> votes = service.select_all_vote_meet(meet_no);
		log.info("votes : {}", votes);
		

		model.addAttribute("mvo", mvo);
		model.addAttribute("meet_age_list", meet_age_list);
		model.addAttribute("meet_date", meet_date);
		model.addAttribute("m_list", member_list);
		model.addAttribute("user_no_list", user_no_list);
		model.addAttribute("vos", feed);
		model.addAttribute("avos", activities);
		model.addAttribute("votes", votes);
		model.addAttribute("page", "feed");
		
		model.addAttribute("content", "thymeleaf/html/meet/feed/feed");
		return "thymeleaf/layouts/meet/layout_meet";
	}
	
	@ApiOperation(value = "모임 멤버 리스트", notes = "모임 멤버 리스트 띄우는 컨트롤러")
	@GetMapping(value = "/meet-member.do")
	public String meet_member_list(@RequestParam("idx") String meet_no, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		
		log.info("meet member list Page");

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

			model.addAttribute("list", map);
		} else {
			map.put("isLogin", false);
			model.addAttribute("list", map);
		}
		log.info("list : {}", map);

		// 모임 정보 불러오기
		MeetInfoVO mvo = service.select_one_meet_info(meet_no);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String meet_date = formatter.format(mvo.getMeet_date());
		
		String[] meet_age_split = mvo.getMeet_age().split(",");
		List<String> meet_age_list = new ArrayList<>();
		for (String age : meet_age_split) {
			meet_age_list.add(age);
		}
		log.info("mvo : {}", mvo);
		
		// 모임 가입자 리스트 불러오기
		List<MeetUserVO> member_list = service.select_all_meet_registered_member(meet_no);
		log.info("member list : {}", member_list);
		
		List<String> user_no_list = new ArrayList<>();
		for (MeetUserVO vo : member_list) {
			user_no_list.add(vo.getUser_no());
		}
		log.info("user no list : {}", user_no_list);
		
		
		
		// 액티비티 불러오기
		List<MeetActivityVO> activities = service.select_all_activity_for_feed(meet_no);
		log.info("activities : {}", activities);
		
		// 투표 불러오기
		List<MeetVoteVO> votes = service.select_all_vote_meet(meet_no);
		log.info("votes : {}", votes);
		

		model.addAttribute("mvo", mvo);
		model.addAttribute("meet_age_list", meet_age_list);
		model.addAttribute("meet_date", meet_date);
		model.addAttribute("m_list", member_list);
		model.addAttribute("user_no_list", user_no_list);
		model.addAttribute("m_list_cnt", member_list.size());
		model.addAttribute("avos", activities);
		model.addAttribute("votes", votes);
		model.addAttribute("page", "feed");
		
		
		model.addAttribute("content", "thymeleaf/html/meet/feed/member_list");
		return "thymeleaf/layouts/meet/layout_meet";
	}
	
	
	@ApiOperation(value = "모임 멤버 리스트", notes = "모임 멤버 리스트 띄우는 컨트롤러")
	@GetMapping(value = "/meet-detail.do")
	public String meet_detail(@RequestParam("idx") String meet_no, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		
		log.info("meet detail Page");

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

			model.addAttribute("list", map);
		} else {
			map.put("isLogin", false);
			model.addAttribute("list", map);
		}
		log.info("list : {}", map);

		// 모임 정보 불러오기
		MeetInfoVO mvo = service.select_one_meet_info(meet_no);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String meet_date = formatter.format(mvo.getMeet_date());
		
		String[] meet_age_split = mvo.getMeet_age().split(",");
		List<String> meet_age_list = new ArrayList<>();
		for (String age : meet_age_split) {
			meet_age_list.add(age);
		}
		log.info("mvo : {}", mvo);
		
		// 모임 가입자 리스트 불러오기
		List<MeetUserVO> member_list = service.select_all_meet_registered_member(meet_no);
		log.info("member list : {}", member_list);
		
		List<String> user_no_list = new ArrayList<>();
		for (MeetUserVO vo : member_list) {
			user_no_list.add(vo.getUser_no());
		}
		log.info("user no list : {}", user_no_list);
		
		// 액티비티 불러오기
		List<MeetActivityVO> activities = service.select_all_activity_for_feed(meet_no);
		log.info("activities : {}", activities);
		
		// 투표 불러오기
		List<MeetVoteVO> votes = service.select_all_vote_meet(meet_no);
		log.info("votes : {}", votes);
		

		model.addAttribute("mvo", mvo);
		model.addAttribute("meet_age_list", meet_age_list);
		model.addAttribute("meet_date", meet_date);
		model.addAttribute("m_list", member_list);
		model.addAttribute("user_no_list", user_no_list);
		model.addAttribute("m_list_cnt", member_list.size());
		model.addAttribute("avos", activities);
		model.addAttribute("votes", votes);
		model.addAttribute("page", "feed");
		
		
		model.addAttribute("content", "thymeleaf/html/meet/feed/meet_detail");
		return "thymeleaf/layouts/meet/layout_meet";
	}
}

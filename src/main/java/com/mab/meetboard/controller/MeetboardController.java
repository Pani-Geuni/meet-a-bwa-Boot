package com.mab.meetboard.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mab.comment.model.CommentUserVO;
import com.mab.comment.service.CommentService;
import com.mab.meet.model.MeetActivityVO;
import com.mab.meet.model.MeetInfoVO;
import com.mab.meet.model.MeetUserVO;
import com.mab.meet.model.MeetVoteVO;
import com.mab.meet.service.MeetService;
import com.mab.meetboard.model.MBUserVO;
import com.mab.meetboard.model.MeetBoardVO;
import com.mab.meetboard.service.MeetBoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "MEETBOARD 컨트롤러")
@Controller
@RequestMapping("/meet-a-bwa")
public class MeetboardController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	HttpSession session;
	
	@Autowired
	MeetService service;
	
	@Autowired
	MeetBoardService boardService;
	
	@Autowired
	CommentService commentService;
	
	private String S3Bucket = "meet-a-bwa/meetboard"; // Bucket 이름

	@Autowired
	AmazonS3Client amazonS3Client;

	@ApiOperation(value = "모임 게시글 디테일 화면", notes = "모임 메인 화면 띄우는 컨트롤러")
	@GetMapping(value = "/post-detail.do")
	public String board_detail(@RequestParam("idx") String meet_no, String board_no, HttpServletRequest request, HttpServletResponse response, Model model) {

		log.info("board detail page");

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
		log.info("mvo : {}", mvo);

		// 모임 가입자 리스트 불러오기
		List<MeetUserVO> member_list = service.select_all_meet_registered_member(meet_no);
		log.info("member list : {}", member_list);

		List<String> user_no_list = new ArrayList<>();
		for (MeetUserVO vo : member_list) {
			user_no_list.add(vo.getUser_no());
		}
		log.info("user no list : {}", user_no_list);
		
		
		// 게시글 상세 불러오기
		MBUserVO post_detail = boardService.select_one_post_detail(board_no);
		log.info("post_detail : {}", post_detail);
		
		// 해당 게시글의 댓글 불러오기
		List<CommentUserVO> comment_list = commentService.select_all_board_comment(board_no);
		log.info("comment list : {}", comment_list);

		// 액티비티 불러오기
		List<MeetActivityVO> activities = service.select_all_activity_for_feed(meet_no);
		log.info("activities : {}", activities);

		// 투표 불러오기
		List<MeetVoteVO> votes = service.select_all_vote_meet(meet_no);
		log.info("votes : {}", votes);


		model.addAttribute("mvo", mvo);
		model.addAttribute("meet_date", meet_date);
		model.addAttribute("m_list", member_list);
		model.addAttribute("user_no_list", user_no_list);
		
		model.addAttribute("post_detail", post_detail);
		model.addAttribute("comment_list", comment_list);
		
		model.addAttribute("avos", activities);
		model.addAttribute("votes", votes);
		model.addAttribute("page", "post_writer");

		model.addAttribute("content", "thymeleaf/html/meet/feed/post_detail");
		return "thymeleaf/layouts/meet/layout_meet";
	}
	
	@ApiOperation(value = "모임 게시글 작성 화면", notes = "모임 게시글 작성 컨트롤러")
	@GetMapping(value = "/post-write.do")
	public String post_write(@RequestParam("idx") String meet_no, String board_no, HttpServletRequest request, HttpServletResponse response, Model model) {

		log.info("post_write page");

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
		log.info("mvo : {}", mvo);

		// 모임 가입자 리스트 불러오기
		List<MeetUserVO> member_list = service.select_all_meet_registered_member(meet_no);
		log.info("member list : {}", member_list);

		List<String> user_no_list = new ArrayList<>();
		for (MeetUserVO vo : member_list) {
			user_no_list.add(vo.getUser_no());
		}
		log.info("user no list : {}", user_no_list);
		
		
		// 게시글 상세 불러오기
		MBUserVO post_detail = boardService.select_one_post_detail(board_no);
		log.info("post_detail : {}", post_detail);
		
		// 해당 게시글의 댓글 불러오기
		List<CommentUserVO> comment_list = commentService.select_all_board_comment(board_no);
		log.info("comment list : {}", comment_list);

		// 액티비티 불러오기
		List<MeetActivityVO> activities = service.select_all_activity_for_feed(meet_no);
		log.info("activities : {}", activities);

		// 투표 불러오기
		List<MeetVoteVO> votes = service.select_all_vote_meet(meet_no);
		log.info("votes : {}", votes);

		

		model.addAttribute("mvo", mvo);
		model.addAttribute("meet_date", meet_date);
		model.addAttribute("m_list", member_list);
		model.addAttribute("user_no_list", user_no_list);
		
		model.addAttribute("post_detail", post_detail);
		model.addAttribute("comment_list", comment_list);
		
		model.addAttribute("avos", activities);
		model.addAttribute("votes", votes);
		model.addAttribute("page", "feed");

		model.addAttribute("content", "thymeleaf/html/meet/feed/post_writer");
		return "thymeleaf/layouts/meet/layout_meet";
	}
	
	@ApiOperation(value = "모임 게시글 화면 화면", notes = "모임 게시글 작성 컨트롤러")
	@PostMapping(value = "/post-writeOK.do")
	@ResponseBody
	public String post_writeOK(MeetBoardVO bvo, Model model) {
		
		Map<String, String> map = new HashMap<>();
		
		String board_title = bvo.getBoard_title();
		String board_content = bvo.getBoard_content();
		String user_no = bvo.getUser_no();
		String meet_no = bvo.getMeet_no();
		
		int result = boardService.insert_post(board_title, board_content, user_no, meet_no);
		
		if (result == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		
		return json;
	}
	
	@ApiOperation(value = "모임 게시글 수정 화면", notes = "모임 게시글 수정 컨트롤러")
	@GetMapping(value = "/post-update.do")
	public String post_update(@RequestParam("idx") String meet_no, String board_no, HttpServletRequest request, HttpServletResponse response, Model model) {
		log.info("post_write page");

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
		log.info("mvo : {}", mvo);

		// 모임 가입자 리스트 불러오기
		List<MeetUserVO> member_list = service.select_all_meet_registered_member(meet_no);
		log.info("member list : {}", member_list);

		List<String> user_no_list = new ArrayList<>();
		for (MeetUserVO vo : member_list) {
			user_no_list.add(vo.getUser_no());
		}
		log.info("user no list : {}", user_no_list);
		
		
		// 게시글 상세 불러오기
		MBUserVO post_detail = boardService.select_one_post_detail(board_no);
		log.info("post_detail : {}", post_detail);
		
		// 해당 게시글의 댓글 불러오기
		List<CommentUserVO> comment_list = commentService.select_all_board_comment(board_no);
		log.info("comment list : {}", comment_list);

		// 액티비티 불러오기
		List<MeetActivityVO> activities = service.select_all_activity_for_feed(meet_no);
		log.info("activities : {}", activities);

		// 투표 불러오기
		List<MeetVoteVO> votes = service.select_all_vote_meet(meet_no);
		log.info("votes : {}", votes);

		

		model.addAttribute("mvo", mvo);
		model.addAttribute("meet_date", meet_date);
		model.addAttribute("m_list", member_list);
		model.addAttribute("user_no_list", user_no_list);
		
		model.addAttribute("post_detail", post_detail);
		model.addAttribute("comment_list", comment_list);
		
		model.addAttribute("avos", activities);
		model.addAttribute("votes", votes);
		model.addAttribute("page", "feed");

		model.addAttribute("content", "thymeleaf/html/meet/feed/post_update");
		return "thymeleaf/layouts/meet/layout_meet";
		
	}
	
	@ApiOperation(value = "모임 게시글 수정 완료 화면", notes = "모임 게시글 수정 완료 컨트롤러")
	@PostMapping(value = "/post-updateOK.do")
	@ResponseBody
	public String post_updateOK(MeetBoardVO bvo, String board_no, Model model) {
		
		Map<String, String> map = new HashMap<>();
		
		String board_title = bvo.getBoard_title();
		String board_content = bvo.getBoard_content();
		String user_no = bvo.getUser_no();
		String meet_no = bvo.getMeet_no();
		
		int result = boardService.update_post(board_title, board_content, user_no, meet_no, board_no);
		
		if (result == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		
		return json;
	}
	
	
	@ApiOperation(value = "Summernote 이미지 처리", notes = "Summernote 이미지 처리 컨트롤러")
	@PostMapping(value = "/summernote_image.do")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
		JsonObject json = new JsonObject();
		
		log.info("{} byte", multipartFile.getSize());
		
		if (multipartFile.getSize() > 0) {
			
			String fileRoot = "https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/activity/";
			String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		    String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자

		    String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		    File targetFile = new File(fileRoot + savedFileName);
		    
		    log.info("fileRoot : {}", fileRoot);
		    log.info("originalFileName : {}", originalFileName);
		    log.info("extension : {}", extension);
		    log.info("savedFileName : {}", savedFileName);
		    log.info("targetFile : {}", targetFile);
			
		    ObjectMetadata objectMetaData = new ObjectMetadata();
		    objectMetaData.setContentType(extension);
		    objectMetaData.setContentLength(multipartFile.getSize());
		    
		    try {
		    	amazonS3Client.putObject(
		    			new PutObjectRequest(S3Bucket, savedFileName, multipartFile.getInputStream(), objectMetaData)
		    			.withCannedAcl(CannedAccessControlList.PublicRead));
		    	
		    	String imagePath = amazonS3Client.getUrl(S3Bucket, savedFileName).toString();
		    	log.info("이미지 링크 : {}", imagePath);
		    	json.addProperty("url", imagePath);
		    	json.addProperty("responseCode", "success");
		    	
		    } catch (IOException e) {
		    	FileUtils.deleteQuietly(targetFile);	
		        json.addProperty("responseCode", "error");
		        e.printStackTrace();
		    }
		} else {
			log.info("multipartFile's size is 0");
		}
		
		String jsonValue = json.toString();
		
		return jsonValue;
	}
	
	@ApiOperation(value = "모임 게시글 삭제", notes = "게시글 삭제 컨트롤러")
	@GetMapping(value = "/post-delete.do")
	@ResponseBody
	public String post_delete(String board_no, Model model) {
		Map<String, String> map = new HashMap<>();
		
		int result = boardService.delete_post(board_no);
		
		if (result == 1) {
			map.put("result", "1");			
		} else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		
		return json;
	}

	
}

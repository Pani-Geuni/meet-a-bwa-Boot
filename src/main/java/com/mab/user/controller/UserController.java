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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Api(tags = "유저 컨트롤러")
@Slf4j
@Controller
@RequestMapping("/meet-a-bwa")
public class UserController {

	@Autowired
	UserService service;

	@Autowired
	HttpSession session;

	@Autowired
	UserSendEmail authSendEmail;

	// 자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	// **********************
	// 로그인 완료
	// **********************
	@ApiOperation(value = "로그인 성공", notes = "로그인 성공 입니다")
	@PostMapping("/loginSuccess")
	@ResponseBody
	public String user_loginOK(@RequestParam String username, HttpServletResponse response) {
		log.info("user_loginOK ()...");
		log.info("username: {}", username);

		// 로그인 성공시 기존의 유저관련쿠키 제거
		Cookie cc = new Cookie("user_no", null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
		cc.setMaxAge(0); // 유효시간을 0으로 설정
		response.addCookie(cc); // 응답 헤더에 추가해서 없어지도록 함
	

		UserVO uvo = service.user_login_info(username);
		log.info("uvo: {}",uvo);
		Map<String, String> map = new HashMap<String, String>();

		session.setAttribute("user_id", uvo.getUser_id());

		Cookie cookie = new Cookie("user_no", uvo.getUser_no()); // 고유번호 쿠키 저장
		cookie.setPath("/");
		response.addCookie(cookie);
		

		log.info("User Login success.....");
		map.put("result", "1"); // 로그인 성공

		String jsonObject = gson.toJson(map);

		return jsonObject;
	}

	// **********************
	// 로그인 실패
	// **********************
	@ApiOperation(value = "로그인 실패", notes = "로그인 실패 입니다")
	@PostMapping("/loginFail")
	@ResponseBody
	public String user_loginFail(UserVO uvo, HttpServletResponse response) {
		log.info("user_loginFail ()...");
		log.info("result: {}", uvo);

		Map<String, String> map = new HashMap<String, String>();

		log.info("User Login failed.....");
		map.put("result", "0"); // 로그인 실패

		String jsonObject = gson.toJson(map);

		return jsonObject;
	}

	// **********************
	// 아이디 찾기
	// **********************
	@ApiOperation(value = "아이디 찾기", notes = "아이디 찾기 입니다")
	@PostMapping("/id_find.do")
	@ResponseBody
	public String user_find_id(UserVO uvo, EmailVO evo) {
		log.info("user_find_id ()...");
		log.info("result: {}", uvo);

		Map<String, String> map = new HashMap<String, String>();

		UserVO uvo2 = service.user_email_select(uvo);
		log.info("uvo2: {}", uvo2);
		if (uvo2 != null) {
			uvo2 = authSendEmail.findId(uvo2, evo); // 유저의 메일로 아이디 전송

			if (uvo2 != null) {
				log.info("user_fine_id successed...");
				map.put("result", "1");

			} else {
				log.info("user_fine_id failed...");
				map.put("result", "0");
			}
		}

		String jsonObject = gson.toJson(map);

		return jsonObject;
	}

	// **********************
	// 비밀번호 찾기
	// **********************
	// 사용자가 비밀번호를 찾으면 초기화된 비밀번호를 이메일로 전송,데이터베이스에는 초기화된 비번 저장
	@ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기 입니다")
	@PostMapping("/pw_find.do")
	@ResponseBody
	public String user_find_pw(UserVO uvo, EmailVO evo) {
		log.info("user_find_pw ()...");
		log.info("result{}", uvo); // 넘어오는 값 출력

		UserVO uvo2 = service.user_id_email_select(uvo); // 아이디 이메일 체크
		Map<String, String> map = new HashMap<String, String>();
		if (uvo2 != null) {
			// uvo2가 null이 아니면(테이블에 데이터가 존재하면) 메일을 통해 수정링크 제공
			uvo2 = authSendEmail.findPw(uvo2, evo);
			log.info("비밀번호 찾기 메일 전송완료");
			int result = service.user_pw_init(uvo2);
			log.info("비밀번호 초기화 업데이트 완료");

			if (result != 0) {
				log.info("user_fine_pw successed...");
				map.put("result", "1");
			} else {
				log.info("user_fine_pw failed...");
				map.put("result", "0");
			}

		}
		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

}// end class

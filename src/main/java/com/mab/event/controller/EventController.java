/**
 * 
 * @author 김예은
 *
 */

package com.mab.event.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.event.model.EventVO;
import com.mab.event.service.EventService;
import com.mab.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Api(tags="EVENT 컨트롤러")
@RequestMapping("/meet-a-bwa")
public class EventController {

	@Autowired
	HttpSession session;
	
	@Autowired
	EventService service;
	
	@Autowired
	UserService user_service;
	
	
	//자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	
	@ApiOperation(value="이벤트 상세 보기", notes="이벤트 상세 정보 띄우는 컨트롤러")
	@GetMapping("/event_view.do")
	@ResponseBody
	public String event_view(EventVO vo2) {
		log.info("/event_view.do");
		
		EventVO vo = service.SQL_EVENT_SELECT_ONE(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		map.put("event_no", vo.getEvent_no());
		map.put("event_title", vo.getEvent_title());
		map.put("event_description", vo.getEvent_content());
		map.put("activity_no", vo.getActivity_no());
		map.put("user_no", vo.getUser_no());
		
		map.put("user_name", user_service.select_user_info(vo2.getUser_no()).getUser_name());
		map.put("event_date", vo.getEvent_date());
		map.put("event_d_day", vo.getEvent_d_day());
		
		String json = gson.toJson(map);
		return json;
	}
	
	
	@ApiOperation(value="이벤트 생성", notes="이벤트 생성하는 컨트롤러")
	@PostMapping("/event_create.do")
	@ResponseBody
	public String event_create(EventVO vo2) {
		log.info("/event_create.do");
		
		DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.from(formatDateTime.parse(vo2.getEvent_d_day()));
        Timestamp ts = Timestamp.valueOf(localDateTime);
        vo2.setEvent_d_day_sql(ts);
		
		int result = service.SQL_INSERT_EVENT(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		
		if(result == 1) {
			map.put("result", "1");
		}else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		return json;
	}
	
	@ApiOperation(value="이벤트 수정", notes="이벤트 수정하는 컨트롤러")
	@PostMapping("/event_update.do")
	@ResponseBody
	public String event_update(EventVO vo2) {
		log.info("/event_update.do");
		
		int result = service.SQL_UPDATE_EVENT(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		
		if(result == 1) {
			map.put("result", "1");
		}else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		return json;
	}
	
	@ApiOperation(value="이벤트 삭제", notes="이벤트 삭제하는 컨트롤러")
	@GetMapping("/event_delete.do")
	@ResponseBody
	public String event_delete(EventVO vo2) {
		log.info("/event_delete.do");
		
		int result = service.SQL_DELETE_EVENT(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		
		if(result == 1) {
			map.put("result", "1");
		}else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		return json;
	}

}

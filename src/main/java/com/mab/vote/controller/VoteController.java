/**
 * 
 * @author 김예은
 *
 */

package com.mab.vote.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.mab.user.service.UserService;
import com.mab.vote.model.VoteContentVO;
import com.mab.vote.model.VoteResultVO;
import com.mab.vote.model.VoteVO;
import com.mab.vote.model.VoteViewVO;
import com.mab.vote.service.VoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags="HOME 컨트롤러")
@Controller
@RequestMapping("/meet-a-bwa")
public class VoteController {

	@Autowired
	HttpSession session;
	
	@Autowired
	VoteService service;
	
	@Autowired
	UserService user_service;
	
	
	//자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	
	@ApiOperation(value="모임 내 투표 상세 보기", notes="모임 내 투표 내용 띄우는 컨트롤러")
	@GetMapping("/vote_view.do")
	@ResponseBody
	public String vote_view(VoteVO vo2) {
		log.info("vote_view");
		
		List<VoteViewVO> list = service.select_all_vote_content(vo2);
		
		String vote_title = "";
		String vote_description = "";
		String vote_eod = "";
		String vote_state = "";
		String writer_no = "";
		String private_state = "";
		
		
		Map<String, Object> map_wrap =  new HashMap<String, Object>();
		ArrayList<Object> arr = new ArrayList<Object>();
		
		for(VoteViewVO vvo2 : list) {
			if(vote_title.equals("")) {
				vote_title = vvo2.getVote_title();
			}
			if(vote_description.equals("")) {
				vote_description = vvo2.getVote_info();
			}
			if(vote_eod.equals("")) {
				vote_eod = vvo2.getVote_eod();
			}
			if(vote_state.equals("")) {
				vote_state = vvo2.getVote_state();
			}
			if(writer_no.equals("")) {
				writer_no = vvo2.getUser_no();
			}
			if(private_state.equals("")) {
				private_state = vvo2.getPrivate_state();
			}
			
			Map<String, Object> map =  new HashMap<String, Object>();
			map.put("content_no", vvo2.getContent_no());
			map.put("content", vvo2.getVote_content());
			arr.add(map);
		}
		
		List<Object> vr_list = service.vr_selectOne(vo2.getVote_no());
		String my_result = service.myVr_selectOne(vo2.getVote_no(), vo2.getUser_no());

		
		map_wrap.put("vote_result", vr_list);
		map_wrap.put("isVote", my_result);
		map_wrap.put("content_arr", arr);
		map_wrap.put("user_no", writer_no);
		map_wrap.put("user_name", user_service.select_user_info(writer_no).getUser_name());
		map_wrap.put("vote_state", vote_state);
		map_wrap.put("vote_eod", vote_eod);
		map_wrap.put("vote_description", vote_description);
		map_wrap.put("vote_title", vote_title);
		map_wrap.put("vote_no", vo2.getVote_no());
		map_wrap.put("private_state", private_state);
		
		String json = gson.toJson(map_wrap);
		return json;
	}
	
	
	@ApiOperation(value="투표 생성", notes="투표 생성하는 컨트롤러")
	@PostMapping("/vote_create.do")
	@ResponseBody
	public String vote_create(VoteVO vo2, ArrayList<String> contents) {
		log.info("/vote_create.do");
		
        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.from(formatDateTime.parse(vo2.getVote_eod()));
        Timestamp ts = Timestamp.valueOf(localDateTime);
		
		vo2.setVote_eod_sql(ts);
		int result = service.insert_vote(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		
		if(result == 1) {
			int result2 = 0;
			for(String content : contents) {
				String vote_no = service.select_last_voteNO();
				
				VoteContentVO cvo = new VoteContentVO();
				cvo.setVote_no(vote_no);
				cvo.setVote_content(content);
				
				result2 = service.insert_voteContent(cvo);
				if(result2 == 0) {
					map.put("result", "0");
					break;
				}
			}
			if(map.size() == 0) {
				map.put("result", "1");
			}
		}else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		return json;
	}
	
	@ApiOperation(value="투표 수정", notes="투표 내용을 수정하는 컨트롤러")
	@PostMapping("/vote_update.do")
	@ResponseBody
	public String vote_update(VoteVO vo2) {
		log.info("/vote_update.do");
		
		DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.from(formatDateTime.parse(vo2.getVote_eod()));
        Timestamp ts = Timestamp.valueOf(localDateTime);
		
		vo2.setVote_eod_sql(ts);
		
		int result = service.update_vote(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		
		if(result == 1) {
			map.put("result", "1");
		}else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		return json;
	}
	
	@ApiOperation(value="투표 삭제", notes="투표를 삭제하는 컨트롤러")
	@GetMapping("/vote_delete.do")
	@ResponseBody
	public String vote_delete(VoteVO vo2) {
		log.info("/vote_delete.do");
		
		int result = service.delete_vote(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		
		if(result == 1) {
			map.put("result", "1");
		}else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		return json;
	}
	
	@ApiOperation(value="투표 참여", notes="투표에 참여했을 때 결과 추가하는 컨트롤러")
	@GetMapping("/voteOK.do")
	@ResponseBody
	public String voteOK(VoteResultVO vo2) {
		log.info("/voteOK.do");
		
		int result = service.insert_vote_result(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		
		if(result == 1) {
			map.put("result", "1");
		}else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		return json;
	}
	
	@ApiOperation(value="투표 재참여", notes="투표에 재참여하는 컨트롤러")
	@GetMapping("/re_voteOK.do")
	@ResponseBody
	public String re_voteOK(VoteResultVO vo2) {
		log.info("/re_voteOK.do");
		
		int result = service.update_vote_result(vo2);
		
		Map<String, String> map =  new HashMap<String, String>();
		
		if(result == 1) {
			map.put("result", "1");
		}else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		return json;
	}
	
	@ApiOperation(value="투표 조기마감", notes="투표 조기마감하는 컨트롤러")
	@GetMapping("/vote_stateUpdate.do")
	@ResponseBody
	public String vote_stateUpdate(VoteVO vo2) {
		log.info("/vote_stateUpdate.do");
		
		int result = service.update_vote_state(vo2);
		
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

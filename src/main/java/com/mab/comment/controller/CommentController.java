package com.mab.comment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mab.comment.model.CommentVO;
import com.mab.comment.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Api(tags = "COMMENT 컨트롤러")
@Controller
@RequestMapping("/meet-a-bwa")
public class CommentController {
	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Autowired
	CommentService commentService;
	
	@ApiOperation(value = "댓글 추가", notes = "게시글에 댓글을 추가하는 컨트롤러")
	@GetMapping(value = "/comment_insert.do")
	@ResponseBody
	public String comment_insert(CommentVO cvo, Model model) {
		
		Map<String, String> map = new HashMap<>();
		
		int result = commentService.insert_comment(cvo.getMother_no(), cvo.getComment_content(), cvo.getBoard_no(), cvo.getUser_no(), cvo.getMeet_no());
		log.info("comment insert result : {}", result);
		
		if (result == 1) {
			map.put("result", "1");			
		} else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		
		return json;
	}
	
	@ApiOperation(value = "댓글 삭제", notes = "게시글에 댓글을 삭제하는 컨트롤러")
	@GetMapping(value = "/comment_delete.do")
	@ResponseBody
	public String comment_delete(CommentVO cvo, Model model) {
		
		Map<String, String> map = new HashMap<>();
		
		int result = commentService.update_comment(cvo.getComment_no());
		log.info("delete result :: {}", result);
		
		if (result == 1) {
			map.put("result", "1");			
		} else {
			map.put("result", "0");
		}
		
		String json = gson.toJson(map);
		
		return json;
	}
	
	
	
}

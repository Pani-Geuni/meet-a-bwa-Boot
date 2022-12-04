/**
 * @author 김예은
 */

package com.mab.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.vote.model.VoteContentVO;
import com.mab.vote.model.VoteResultVO;
import com.mab.vote.model.VoteVO;
import com.mab.vote.model.VoteViewVO;
import com.mab.vote.repository.VoteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VoteService {
	@Autowired
	VoteRepository repo;
	
	
	public VoteService(){
		log.info("VoteService()...");
	}

	// 특정 투표에 대한 투표 내용 반환
	public List<VoteViewVO> select_all_vote_content(VoteVO vo2) {
		List<VoteViewVO> list = repo.select_all_vote_content(vo2);
		
		return list;
	}
	
	// 특정 투표에 대한 투표 결과 반환
	public List<Object> vr_selectOne(String vote_no){
		List<Object> list = repo.select_one_vote_result(vote_no);
		
		return list;
	}
	
	// 특정 투표에 대한 나의 투표 결과 반환
	public String myVr_selectOne(String vote_no, String user_no) {
		String result = repo.select_one_my_vote_result(vote_no, user_no);
		
		return result;
	}
	
	// 가장 최근에 추가된 투표 고유번호 반환 -> 투표 내용 추가 시에 필요
	public String select_last_voteNO() {
		String vote_no = repo.SQL_SELECT_ONE_LAST_NO();
		
		return vote_no;
	}

	// 투표 추가
	public int insert_vote(VoteVO vo2) {
		int result = repo.SQL_INSERT_VOTE(vo2);
		
		return result;
	}
	
	// 투표 내용 추가
	public int insert_voteContent(VoteContentVO vo2) {
		int result = repo.SQL_INSERT_VOTE_CONTENT(vo2);
		
		return result;
	}
	
	// 투표 수정
	public int update_vote(VoteVO vo2) {
		int result = repo.SQL_UPDATE_VOTE(vo2);
		
		return result;
	}
	
	// 투표 조기 마감
	public int update_vote_state(VoteVO vo2) {
		int result = repo.SQL_UPDATE_VOTE_STATE(vo2);
		
		return result;
	}
	
	// 투표 삭제
	public int delete_vote(VoteVO vo2) {
		int result = repo.SQL_DELETE_VOTE(vo2);
		
		return result;
	}
	
	// 투표 참여
	public int insert_vote_result(VoteResultVO vo2) {
		int result = repo.SQL_INSERT_VOTE_RESULT(vo2);
		
		return result;
	}
	
	// 투표 재참여
	public int update_vote_result(VoteResultVO vo2) {
		int result = repo.SQL_UPDATE_VOTE_RESULT(vo2);
		
		return result;
	}
}

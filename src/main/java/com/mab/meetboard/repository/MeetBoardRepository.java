package com.mab.meetboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mab.meetboard.model.MeetBoardVO;

public interface MeetBoardRepository extends JpaRepository<MeetBoardVO, Object> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "delete from meetboard where board_no=?1")
	public int delete_post(String board_no);
}

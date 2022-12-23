package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgBoard;
import com.muglang.muglangspace.entity.MglgUser;


@Transactional
public interface MglgBoardRepository extends JpaRepository<MglgBoard, String> {
	
	@Modifying
	void deleteByBoardId(int boardId);
	
	MglgBoard findByBoardId(MglgBoard board);

}

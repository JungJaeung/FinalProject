package com.muglang.muglangspace.service.mglgboard.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.entity.MglgBoard;
import com.muglang.muglangspace.repository.MglgBoardRepository;
import com.muglang.muglangspace.service.mglgboard.MglgBoardService;

@Service
public class MglgBoardServiceImpl implements MglgBoardService{
	@Autowired
	MglgBoardRepository mglgBoardRepository;

	@Override
	public List<MglgBoard> getFAQList() {

		return mglgBoardRepository.findAll();
	}

	@Override
	public void deleteBoard(int boardId) {
		mglgBoardRepository.deleteByBoardId(boardId); 
	}

	@Override
	public MglgBoard getBoard(MglgBoard board) {
		
		return mglgBoardRepository.findByBoardId(board);
	}
}

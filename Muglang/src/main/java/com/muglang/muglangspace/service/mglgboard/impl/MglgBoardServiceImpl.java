package com.muglang.muglangspace.service.mglgboard.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.repository.MglgBoardRepository;
import com.muglang.muglangspace.service.mglgboard.MglgBoardService;

@Service
public class MglgBoardServiceImpl implements MglgBoardService{
	@Autowired
	MglgBoardRepository mglgBoardRepository;
}

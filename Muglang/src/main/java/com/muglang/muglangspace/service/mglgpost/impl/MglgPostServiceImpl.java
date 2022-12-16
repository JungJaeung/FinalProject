package com.muglang.muglangspace.service.mglgpost.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.common.Function;
import com.muglang.muglangspace.dto.MglgPostDTO;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.repository.MglgPostRepository;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;

@Service
public class MglgPostServiceImpl implements MglgPostService, Function<List<MglgPost>, List<MglgPostDTO>>{
	@Autowired
	private MglgPostRepository mglgPostRepository;
	
	//포스팅 관련 서비스 제공
	@Override
	public void insertPost(MglgPost mglgpost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePost(MglgPost mglgpost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePost(MglgPost mglgpost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void likePost(MglgPost mglgpost) {
		// TODO Auto-generated method stub
		
	}

	//DB에서 페이지 기법으로 형태를 짜서 map의 객체로 만들어서 반환
	@Override
	public Page<MglgPost> getPagePostList(Pageable pageable) {
		
		return mglgPostRepository.findAll(pageable);
	}

	@Override
	public int cntPost(int postId) {
		// TODO Auto-generated method stub
		return 0;
	}


}

package com.muglang.muglangspace.service.mglgpost.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.repository.MglgPostRepository;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;

@Service
public class MglgPostServiceImpl implements MglgPostService	{
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

	@Override
	public List<MglgPost> getPostList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int cntPost(int postId) {
		// TODO Auto-generated method stub
		return 0;
	}

}

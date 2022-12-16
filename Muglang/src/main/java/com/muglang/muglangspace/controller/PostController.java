package com.muglang.muglangspace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;

@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	private MglgPostService mglgPostService;
	//수정작업실행함
	public void insertPost(MglgPost mglgpost) {
		
	}
	
	public void updatePost(MglgPost mglgpost) {
		
	}
	
	public void deletePost(MglgPost mglgpost) {
		
	}
	
	public void likePost(MglgPost mglgpost) {
		
	}
	
	public List<MglgPost> getPostList() {
		return null;
	}
	
	public List<MglgPost> getYourPost() {
		return null;
	}
	
	public int cntPost(int postId) {
		return 0;
	}
}

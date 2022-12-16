package com.muglang.muglangspace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;

@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	private MglgPostService mglgPostService;
	//3843783

	
	public void insertPost(MglgPost mglgpost) {
		
		mglgPostService.insertPost(mglgpost);
	}
	
	public void updatePost(MglgPost mglgpost) {
		
	}
	
	public void deletePost(MglgPost mglgpost) {
		
	}
	
	public void likePost(MglgPost mglgpost) {
		
	}
	
	//로그인후 메인페이지로 이동하여 게시글의 내용을 최종적으로 html화면단에 넘기는 메소드
	public ModelAndView getPostList() {
		List<MglgPost> mglgPostList;
		
		mglgPostList = mglgPostService.getPostList();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/post/mainPost.html");
		mv.addObject("postList", mglgPostList);
		
		return mv;
	}
	
	public List<MglgPost> getYourPost() {
		return null;
	}
	
	public int cntPost(int postId) {
		return 0;
	}
}

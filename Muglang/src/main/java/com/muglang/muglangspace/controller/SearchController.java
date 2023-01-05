package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.dto.MglgPostDTO;
import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired MglgPostService mglgPostService;
	
	// 포스트 내용을 기준으로 검색
	@GetMapping("/searchByPost")
	public ModelAndView searchByPost(@RequestParam("searchKeyword") String searchKeyword,
									 @PageableDefault(page = 0, size = 5) Pageable pageable) {
		
		log.info("searchByPost - SearchKeyword: " + searchKeyword);
		
		Page<CamelHashMap> mglgPosts = mglgPostService.searchByPost(searchKeyword, pageable);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/search/searchByPost.html");
		mv.addObject("mglgPosts", mglgPosts);
		
		
		return mv;
	}
	
	
	// 닉네임을 기준으로 검색
	@GetMapping("/searchByNick")
	public ModelAndView searchByNick(@RequestParam("searchKeyword") String searchKeyword,
									 @PageableDefault(page = 0, size = 5)Pageable pageable) {
		
		log.info("searchByNick - SearchKeyword: " + searchKeyword);
		
		Page<CamelHashMap> mglgPosts = mglgPostService.searchByNick(searchKeyword, pageable);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/search/searchByNick.html");
		mv.addObject("mglgPosts", mglgPosts);
		
		return mv;
		
	}
}

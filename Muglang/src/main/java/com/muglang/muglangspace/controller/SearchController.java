package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired MglgPostService mglgPostService;
	
	// 포스트 닉 해쉬태그를 기준으로 검색
	@GetMapping("/searchByPost")
	public ModelAndView searchByPost(@RequestParam("searchKeyword") String searchKeyword,
									 @PageableDefault(page = 0, size = 20) Pageable pageable) {
		
		log.info("searchByNick - SearchKeyword: " + searchKeyword);
		
		Page<CamelHashMap> mglgPostsContent = mglgPostService.searchByPost(searchKeyword, pageable);
		
		Page<CamelHashMap> mglgPostsNick = mglgPostService.searchByNick(searchKeyword, pageable);
		
		Page<CamelHashMap> mglgPostsHashtag = mglgPostService.searchByHashtag(searchKeyword, pageable);
		
		System.out.println("=======================================" + mglgPostsContent.getContent());
		
		System.out.println("=======================================" + mglgPostsNick.getContent());
		
		System.out.println("=======================================" + mglgPostsHashtag.getContent());
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/search/searchByPost.html");
		mv.addObject("mglgPostsContent", mglgPostsContent);
		mv.addObject("mglgPostsNick", mglgPostsNick);
		mv.addObject("mglgPostsHashtag", mglgPostsHashtag);
		mv.addObject("searchKeyword", searchKeyword);
		
		return mv;
		
	}
}

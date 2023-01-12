package com.muglang.muglangspace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.dto.MglgShowHotKeywordsDTO;
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
									 @PageableDefault(page = 0, size = 5) Pageable pageable) {
		
		log.info("searchByNick - SearchKeyword: " + searchKeyword);
		
		// 검색어를 T_MGLG_HOT_KEYWORDS 테이블에 INSERT
		mglgPostService.insertKeyword(searchKeyword);
		
		Page<CamelHashMap> mglgPostsContent = mglgPostService.searchByPost(searchKeyword, pageable);
		
		Page<CamelHashMap> mglgPostsNick = mglgPostService.searchByNick(searchKeyword, pageable);

		Page<CamelHashMap> mglgPostsHashtag = mglgPostService.searchByHashtag(searchKeyword, pageable);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/search/searchByPost.html");
		mv.addObject("mglgPostsContent", mglgPostsContent);
		mv.addObject("mglgPostsNick", mglgPostsNick);
		mv.addObject("mglgPostsHashtag", mglgPostsHashtag);
		mv.addObject("searchKeyword", searchKeyword);
		
		return mv;
		
	}
	
	// HotKeywords 리스트 보는 화면
	@GetMapping("/hotKeywords")
	public ModelAndView hotKeywords(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		
		Page<CamelHashMap> mglgHotKeywords = mglgPostService.getHotKeywords(pageable);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/search/hotKeywords.html");
		mv.addObject("mglgHotKeywords", mglgHotKeywords);
		
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/insrtSHKs")
	public void insertShowHotKeywords(@RequestBody List<MglgShowHotKeywordsDTO> showHotKeywordsList) {
		showHotKeywordsList.forEach(a -> System.out.println(a));
	}
	
}

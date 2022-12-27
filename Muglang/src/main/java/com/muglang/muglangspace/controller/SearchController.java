package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muglang.muglangspace.dto.MglgPostDTO;
import com.muglang.muglangspace.dto.ResponseDTO;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	MglgPostService mglgPostService;
	
	//포스트 검색
	@GetMapping("/searchPostList")
	public ResponseEntity<?> searchPostList(MglgPost mglgPost, Pageable pageable) {
		ResponseDTO<Page<MglgPostDTO>> responseDTO = new ResponseDTO<>();
		
		try {
			Page<MglgPost> pagePostList = mglgPostService.searchPostList(mglgPost, pageable);
			
			Page<MglgPostDTO> pagePostDTOList = pagePostList.map(pagePost ->
																 MglgPostDTO.builder()
																 			.postId(pagePost.getPostId())
																 			.userId(pagePost.getMglgUser().getUserId())
																 			.postRating(pagePost.getPostRating())
																 			.postContent(pagePost.getPostContent())
																 			.restNm(pagePost.getRestNm())
																 			.restRating(pagePost.getRestRating())
																 			.hashTag1(pagePost.getHashTag1())
																 			.hashTag2(pagePost.getHashTag2())
																 			.hashTag3(pagePost.getHashTag3())
																 			.hashTag4(pagePost.getHashTag4())
																 			.hashTag5(pagePost.getHashTag5())
																 			.postDate(pagePost.getPostDate() == null ? 
																 					null :
																 					pagePost.getPostDate().toString())
																 			.build()
																);
			responseDTO.setItem(pagePostDTOList);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}

package com.muglang.muglangspace.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.dto.MglgReportDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.dto.MglgUserRelationDTO;
import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserRelation;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;
import com.muglang.muglangspace.service.mglgsocial.UserRelationService;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@RestController
@RequestMapping("/social")
public class SocialController {
	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private MglgUserService mglguserService;
	
	@Autowired
	private MglgPostService mglgPostService;
	
	//팔로워 개수세기
	@GetMapping("/cntFollow")
	public ResponseEntity<?> cntFollow(@AuthenticationPrincipal CustomUserDetails customUser) {
		MglgResponseDTO<MglgUserRelationDTO> response = new MglgResponseDTO<>();		
		try {
			MglgUser user = MglgUser.builder()
									.userId(Integer.parseInt(customUser.getUsername()))
									.build();
			
			MglgUserRelation relUser = MglgUserRelation.builder()
														.mglgUser(user)
														.build();
			
		    int postCnt = mglgPostService.postCnt(relUser);
			System.out.println("postCnt ==" + postCnt);

			int followCnt = userRelationService.cntFollow(relUser);
			int followingCnt = userRelationService.cntFollowing(relUser);
			
			
			MglgUserRelationDTO returnResponse = MglgUserRelationDTO.builder()
																	 .followCount(followCnt)
																	 .postCount(postCnt)
																	 .followingCount(followingCnt)
																	 .build();

																  

			response.setItem(returnResponse);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	/// 사이드바에서 정보 불러오기
	@GetMapping("/otherUserDetail")
	public ResponseEntity<?> otherUserDetail(@AuthenticationPrincipal CustomUserDetails customUser) {
		MglgResponseDTO<MglgUser> response = new MglgResponseDTO<>();		
		try {
			int userId =Integer.parseInt(customUser.getUsername());
							
			
			MglgUser user = mglguserService.findUser(userId);													  
			System.out.println("user객체의 정체 ======"+user);
			
			response.setItem(user);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	//맞팔
	@PostMapping("followUser")
	public void followUser(int followerId, int userId, HttpServletResponse response) throws IOException {
		userRelationService.followUser(followerId, userId);
		
		response.sendRedirect("/user/profile");
	}
	
	
	//다른 유저 조회
	@GetMapping("otherUser")
	public ModelAndView otherUserView(@RequestParam("userId") int userId,
										@PageableDefault(page=0, size=5) Pageable pageable,@AuthenticationPrincipal CustomUserDetails customUser) {		
		//조회 대상자의 아이디 = userId
		MglgUser user = mglguserService.findUser(userId);
		//조회대상자의 정보 가져오기
		MglgUserDTO userDTO = MglgUserDTO.builder()
										 .userNick(user.getUserNick())
										 .userName(user.getUserName())
										 .bio(user.getBio())
										 .email(user.getEmail())
										 .userId(user.getUserId())
										 .build();
		
		//조회 대상유저의 포스트 목록 불러오기
		Page<MglgPost> postList = mglgPostService.userPostList(userId, pageable);

		ModelAndView mv = new ModelAndView();

		mv.addObject("postList", postList);
		mv.addObject("user", userDTO);
		mv.setViewName("/user/otherUserProfile.html");
		return mv;
	}
	//다른유저 조회페이지 실행시 에이작스를 통한 팔로워리스트 불러오기
	@GetMapping("otherUserFollow")
	public ResponseEntity<?> otherUserFollow(@RequestParam("userId") int userId ,@PageableDefault(page = 0, size = 5)Pageable pageable) {
		
		MglgResponseDTO<MglgUserDTO> response = new MglgResponseDTO<>();
		try {
			MglgUser user = MglgUser.builder()
									.userId(userId)
									.build();
			Page<MglgUser> pageUserFollow = userRelationService.followList(user, pageable);
			Page<MglgUserDTO> pageUserFollowDTO = pageUserFollow.map(page->
																	 MglgUserDTO.builder()
																	             .userId(page.getUserId())
																				 .email(page.getEmail())
																				 .userName(page.getUserName())
																				 .userNick(page.getUserNick())
																				 .build()
																				 );
			
			response.setPageItems(pageUserFollowDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		

				
			
			
	}
	@GetMapping("otherUserFollowing")
	public ResponseEntity<?> otherUserFollowing(@RequestParam("userId") int userId ,@PageableDefault(page = 0, size = 5)Pageable pageable) {
		
		MglgResponseDTO<MglgUserDTO> response = new MglgResponseDTO<>();
		try {
			MglgUser user = MglgUser.builder()
									.userId(userId)
									.build();
			Page<MglgUser> pageUserFollow = userRelationService.followingList(user, pageable);
			Page<MglgUserDTO> pageUserFollowDTO = pageUserFollow.map(page->
																	 MglgUserDTO.builder()
																	             .userId(page.getUserId())
																				 .email(page.getEmail())
																				 .userName(page.getUserName())
																				 .userNick(page.getUserNick())
																				 .build()
																				 );
			
			response.setPageItems(pageUserFollowDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		

				
			
			
	}
	////팔로우 페이징처리
	@GetMapping("otherUserFollowPaging")
	public ResponseEntity<?> otherUserFollow(@RequestParam("userId") int userId ,@RequestParam("page_num") int page_num ,Pageable pageable) {
		pageable = PageRequest.of(page_num, 5);
		System.out.println("팔로우로 넘어옴"+page_num);
		MglgResponseDTO<MglgUserDTO> response = new MglgResponseDTO<>();
		try {
			MglgUser user = MglgUser.builder()
									.userId(userId)
									.build();
			Page<MglgUser> pageUserFollow = userRelationService.followList(user, pageable);
			Page<MglgUserDTO> pageUserFollowDTO = pageUserFollow.map(page->
																	 MglgUserDTO.builder()
																	             .userId(page.getUserId())
																				 .email(page.getEmail())
																				 .userName(page.getUserName())
																				 .userNick(page.getUserNick())
																				 .build()
																				 );
			
			response.setPageItems(pageUserFollowDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		

				
			
			
	}
	////팔로잉 페이징처리
	@GetMapping("otherUserFollowingPaging")
	public ResponseEntity<?> otherUserFollowing(@RequestParam("userId") int userId ,@RequestParam("page_num") int page_num ,Pageable pageable) {
		pageable = PageRequest.of(page_num, 5);
		System.out.println("팔로우로 넘어옴"+page_num);
		MglgResponseDTO<MglgUserDTO> response = new MglgResponseDTO<>();
		try {
			MglgUser user = MglgUser.builder()
									.userId(userId)
									.build();
			Page<MglgUser> pageUserFollow = userRelationService.followingList(user, pageable);
			Page<MglgUserDTO> pageUserFollowDTO = pageUserFollow.map(page->
																	 MglgUserDTO.builder()
																	             .userId(page.getUserId())
																				 .email(page.getEmail())
																				 .userName(page.getUserName())
																				 .userNick(page.getUserNick())
																				 .build()
																				 );
			
			response.setPageItems(pageUserFollowDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		

				
			
			
	}
	
	
}

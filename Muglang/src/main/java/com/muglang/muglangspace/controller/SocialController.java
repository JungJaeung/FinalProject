package com.muglang.muglangspace.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.dto.MglgUserRelationDTO;
import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserRelation;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;
import com.muglang.muglangspace.service.mglgsocial.UserRelationService;

@RestController
@RequestMapping("/social")
public class SocialController {
	@Autowired
	private UserRelationService userRelationService;
	
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
			
			//질문하기 --- 포스트 cnt는 왜오류나는지
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
	//맞팔
	@PostMapping("followUser")
	public void followUser(int followerId, int userId, HttpServletResponse response) throws IOException {
		userRelationService.followUser(followerId, userId);
		
		response.sendRedirect("/user/profile");
	}
}

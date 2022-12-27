package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.dto.MglgUserRelationDTO;
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
	private MglgPostService mglgPostService;
	
	//팔로잉 // 팔로워 동시워 조회
	@GetMapping("/cntFollow")
	public ResponseEntity<?> cntFollow(@RequestParam("userId_side") int userId_side) {
		MglgResponseDTO<MglgUserRelationDTO> response = new MglgResponseDTO<>();
		
		
		try {
			MglgUser user = MglgUser.builder()
									.userId(userId_side)
									.build();
			
			MglgUserRelation relUser = MglgUserRelation.builder()
														.mglgUser(user)
														.build();
			
			//질문하기 --- 포스트 cnt는 왜오류나는지
			int postCnt = mglgPostService.postCnt(relUser);
//			System.out.println("postCnt ==" + postCnt);
			int followCnt = userRelationService.cntFollow(relUser);
			int followingCnt = userRelationService.cntFollowing(relUser);
			
			
			MglgUserRelationDTO returnResponse = MglgUserRelationDTO.builder()
																	 .followCount(followCnt)
//																	 .postCount(postCnt)
																	 .followingCount(followingCnt)
																	 .build();

																  

			response.setItem(returnResponse);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

}

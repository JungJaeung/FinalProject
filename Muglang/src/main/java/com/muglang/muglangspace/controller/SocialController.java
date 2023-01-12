package com.muglang.muglangspace.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.muglang.muglangspace.dto.MglgPostDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.dto.MglgUserRelationDTO;
import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserProfile;
import com.muglang.muglangspace.entity.MglgUserRelation;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;
import com.muglang.muglangspace.service.mglgsocial.UserRelationService;
import com.muglang.muglangspace.service.mglguser.MglgUserService;
import com.muglang.muglangspace.service.mglguserprofile.MglgUserProfileService;

@RestController
@RequestMapping("/social")
public class SocialController {
	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private MglgUserService mglguserService;

	@Autowired
	private MglgPostService mglgPostService;
	@Autowired
	private MglgUserProfileService mglgUserProfileService;
;

	// 팔로워 개수세기
	@GetMapping("/cntFollow")
	public ResponseEntity<?> cntFollow(@AuthenticationPrincipal CustomUserDetails customUser) {
		MglgResponseDTO<MglgUserRelationDTO> response = new MglgResponseDTO<>();
		try {
			MglgUser user = MglgUser.builder().userId(Integer.parseInt(customUser.getUsername())).build();

			MglgUserRelation relUser = MglgUserRelation.builder().mglgUser(user).build();

			int postCnt = mglgPostService.postCnt(relUser);
			System.out.println("postCnt ==" + postCnt);

			int followCnt = userRelationService.cntFollow(relUser);
			int followingCnt = userRelationService.cntFollowing(relUser);

			MglgUserRelationDTO returnResponse = MglgUserRelationDTO.builder().followCount(followCnt).postCount(postCnt)
					.followingCount(followingCnt).build();

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
			int userId = Integer.parseInt(customUser.getUsername());

			MglgUser user = mglguserService.findUser(userId);
			System.out.println("user객체의 정체 ======" + user);

			response.setItem(user);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	// 맞팔
	@PostMapping("followUser")
	public void followUser(int followerId,@AuthenticationPrincipal CustomUserDetails loginUser, HttpServletResponse response) throws IOException {
		int userId = loginUser.getMglgUser().getUserId();
		userRelationService.followUser(followerId, userId);

		response.sendRedirect("/user/profile");
	}

	// 다른 유저 조회
	@GetMapping("otherUser")
	public ModelAndView otherUserView(@RequestParam("userId") int userId,
			@PageableDefault(page = 0, size = 10) Pageable pageable,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		// 조회 대상자의 아이디 = userId
		MglgUser user = mglguserService.findUser(userId);
		// 조회대상자의 정보 가져오기
		MglgUserDTO userDTO = MglgUserDTO.builder().userNick(user.getUserNick()).userName(user.getUserName())
				.bio(user.getBio()).email(user.getEmail()).userId(user.getUserId()).build();

		// 조회 대상유저의 포스트 목록 불러오기
		Page<MglgPost> postLists = mglgPostService.userPostList(userId, pageable);
		Page<MglgPostDTO> postList = postLists.map(pageMglgPost -> MglgPostDTO.builder()
				.userId(pageMglgPost.getMglgUser().getUserId()).postId(pageMglgPost.getPostId())
				.postContent(pageMglgPost.getPostContent()).postDate(pageMglgPost.getPostDate().toString())
				.restNm(pageMglgPost.getRestNm()).restRating(pageMglgPost.getRestRating())
				.postRating(pageMglgPost.getPostRating()).hashTag1(pageMglgPost.getHashTag1())
				.hashTag2(pageMglgPost.getHashTag2()).hashTag3(pageMglgPost.getHashTag3())
				.hashTag4(pageMglgPost.getHashTag4()).hashTag5(pageMglgPost.getHashTag5())
				.betweenDate(Duration.between(pageMglgPost.getPostDate(), LocalDateTime.now()).getSeconds()).build());

		// System.out.println(postList.getContent().get(0).getBetweenDate());
		ModelAndView mv = new ModelAndView();

		mv.addObject("postList", postList);
		mv.addObject("user", userDTO);
		mv.setViewName("/user/otherUserProfile.html");
		return mv;
	}

	// 다른유저 조회페이지 실행시 에이작스를 통한 팔로워리스트 불러오기
	@GetMapping("otherUserFollow")
	public ResponseEntity<?> otherUserFollow(@RequestParam("userId") int userId,
			@PageableDefault(page = 0, size = 10) Pageable pageable,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		// 팔로우 했는지 아닌지 여부 표출을 위한 로직
		int loginUser = customUser.getMglgUser().getUserId();
		int followYn = userRelationService.followingOrNot(userId, loginUser);

		MglgResponseDTO<CamelHashMap> response = new MglgResponseDTO<>();

		try {
			MglgUser user = MglgUser.builder().userId(userId).build();
			Page<CamelHashMap> pageUserFollow = userRelationService.followList(user, pageable);
			pageUserFollow.getContent().get(0).put("followYn", followYn);
			System.out.println("----------");
			System.out.println(pageUserFollow.getContent().get(0));

			response.setPageItems(pageUserFollow);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

	}

	@GetMapping("otherUserFollowing")
	public ResponseEntity<?> otherUserFollowing(@RequestParam("userId") int userId,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		MglgResponseDTO<CamelHashMap> response = new MglgResponseDTO<>();
		try {
			MglgUser user = MglgUser.builder().userId(userId).build();
			Page<CamelHashMap> pageUserFollow = userRelationService.followingList(user, pageable);

			response.setPageItems(pageUserFollow);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

	}

	@GetMapping("otherUserFollowingPaging")
	public ResponseEntity<?> otherUserFollowing(@RequestParam("userId") int userId,
			@RequestParam("page_num") int page_num, Pageable pageable) {
		pageable = PageRequest.of(page_num, 10);
		System.out.println("팔로우로 넘어옴" + page_num);
		MglgResponseDTO<CamelHashMap> response = new MglgResponseDTO<>();
		try {
			MglgUser user = MglgUser.builder().userId(userId).build();
			Page<CamelHashMap> pageUserFollow = userRelationService.followingList(user, pageable);

			response.setPageItems(pageUserFollow);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

	}

	// 유저를 팔로잉하는 로직
	@GetMapping("/followOtherUser")
	public void followUser(@RequestParam("userId") int followId, HttpServletResponse response,
			@AuthenticationPrincipal CustomUserDetails customUser) throws IOException {
		int userId = customUser.getMglgUser().getUserId();

		userRelationService.followUser(followId, userId);

		response.sendRedirect("/social/otherUser?userId=" + followId);
	}

	@GetMapping("/unFollowOtherUser")
	public void unFollowUser(@RequestParam("userId") int userId, HttpServletResponse response,
			@AuthenticationPrincipal CustomUserDetails customUser) throws IOException {
		int loginUser = customUser.getMglgUser().getUserId();

		userRelationService.unFollowUser(userId, loginUser);

		response.sendRedirect("/social/otherUser?userId=" + userId);

	}


	// 팔로워 유저 에이작스 처리를 위한 전부 수정함
	@GetMapping("/follower")
	public ResponseEntity<?> followerList(@RequestParam("searchKeyword") String searchKeyword 
			,@PageableDefault(page = 0, size = 5) Pageable pageable,@AuthenticationPrincipal CustomUserDetails customUser) {
		
		MglgResponseDTO<CamelHashMap> response = new MglgResponseDTO<>();
		try {

		MglgUser user = MglgUser.builder().searchKeyword(searchKeyword)
										  .userId(customUser.getMglgUser().getUserId())
									      .build();
		int i =0;
		Page<CamelHashMap> pagefollowList = userRelationService.followList(user, pageable);
		for(CamelHashMap a : pagefollowList) {
			System.out.println(i+"번째"+pagefollowList.getContent().get(i));
			
			int eachUserId = (int)pagefollowList.getContent().get(i).get("userId");
			MglgUserProfile followerProfile = mglgUserProfileService.followerProfile(eachUserId);
			pagefollowList.getContent().get(i).put("followerProfile", followerProfile);
			System.out.println(i+"번째"+pagefollowList.getContent().get(i)+"끝");
			if(i==5) {
				i=0;
			}else {
				i++;
			}

		}
		    response.setPageItems(pagefollowList);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			System.out.println("오류발생==== "+e);
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	// 팔로워 유저 서치
	@GetMapping("/followerSearch")
	public ResponseEntity<?> followerSearch(@RequestParam("searchKeyword") String searchKeyword 
			,@PageableDefault(page = 0, size = 10000) Pageable pageable,@AuthenticationPrincipal CustomUserDetails customUser) {
		MglgResponseDTO<CamelHashMap> response = new MglgResponseDTO<>();
		try {

		MglgUser user = MglgUser.builder().searchKeyword(searchKeyword)
										  .userId(customUser.getMglgUser().getUserId())
									      .build();

		Page<CamelHashMap> pagefollowList = userRelationService.followList(user, pageable);

			response.setPageItems(pagefollowList);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {

			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	// 팔로잉 유저 에이작스 처리를 위한 전부 수정함
	@GetMapping("/following")
	public ResponseEntity<?> followeringList(@RequestParam("searchKeyword") String searchKeyword 
			,@PageableDefault(page = 0, size = 5) Pageable pageable,@AuthenticationPrincipal CustomUserDetails customUser) {
		
		MglgResponseDTO<CamelHashMap> response = new MglgResponseDTO<>();
		try {

		MglgUser user = MglgUser.builder().searchKeyword(searchKeyword)
										  .userId(customUser.getMglgUser().getUserId())
									      .build();
		int i =0;
		Page<CamelHashMap> pagefollowingList = userRelationService.followingList(user, pageable);
		for(CamelHashMap a : pagefollowingList) {
			System.out.println(i+"번째"+pagefollowingList.getContent().get(i));
			
			int eachUserId = (int)pagefollowingList.getContent().get(i).get("userId");
			MglgUserProfile followingProfile = mglgUserProfileService.followingProfile(eachUserId);
			pagefollowingList.getContent().get(i).put("followingProfile", followingProfile);
			System.out.println(i+"번째"+pagefollowingList.getContent().get(i)+"끝");
			if(i==5) {
				i=0;
			}else {
				i++;
			}

		}
		    response.setPageItems(pagefollowingList);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			System.out.println("오류발생==== "+e);
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	// 팔로잉 유저 에이작스 처리를 위한 전부 수정함
	@GetMapping("/followingSearch")
	public ResponseEntity<?> followingSearch(@RequestParam("searchKeyword") String searchKeyword 
			,@PageableDefault(page = 0, size = 10000) Pageable pageable,@AuthenticationPrincipal CustomUserDetails customUser) {
		MglgResponseDTO<CamelHashMap> response = new MglgResponseDTO<>();
		System.out.println("searchkeyword===="+searchKeyword);
		try {
		MglgUser user = MglgUser.builder().searchKeyword(searchKeyword)
										  .userId(customUser.getMglgUser().getUserId())
									      .build();

		Page<CamelHashMap> pagefollowList = userRelationService.followingList(user, pageable);
		System.out.println("pagefollowList ====" + pagefollowList.getContent());

			response.setPageItems(pagefollowList);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {

			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
}

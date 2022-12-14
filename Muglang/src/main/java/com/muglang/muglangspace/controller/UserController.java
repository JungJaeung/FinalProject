package com.muglang.muglangspace.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.dto.MglgPostDTO;
import com.muglang.muglangspace.dto.MglgReportDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.dto.ResponseDTO;
import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;
import com.muglang.muglangspace.service.mglgsocial.UserRelationService;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private MglgUserService mglgUserService;
	@Autowired
	private UserRelationService userRelationService;
	
	//?????? ?????? ?????????
	@Autowired
	private MglgPostService mglgPostService;

	@GetMapping("/profile")
	public ModelAndView profileView(@AuthenticationPrincipal CustomUserDetails customUser,@PageableDefault(page = 0, size = 5) Pageable pageable) {
		int userId = customUser.getMglgUser().getUserId();
		Page<MglgUser> requestFollowList = userRelationService.requestFollowList(userId,pageable);
		Page<MglgUserDTO> requestFollowDTOList = requestFollowList.map(followList -> 
																					MglgUserDTO.builder()
																					 .userName(followList.getUserName())
																					 .userId(followList.getUserId())
																					 .build()
		);
		long followCnt = requestFollowDTOList.getTotalElements();
		System.out.println("???????????? ?????? "+ followCnt);
		

		ModelAndView mv = new ModelAndView();
		MglgUserDTO user = MglgUserDTO.builder()
								.userNick(customUser.getMglgUser().getUserNick())
								.userName(customUser.getMglgUser().getUserName())
								.email(customUser.getMglgUser().getEmail())
								.firstName(customUser.getMglgUser().getFirstName())
								.lastName(customUser.getMglgUser().getLastName())
								.address(customUser.getMglgUser().getAddress())
								.phone(customUser.getMglgUser().getPhone())
								.userId(customUser.getMglgUser().getUserId())
								.bio(customUser.getMglgUser().getBio())
								.regDate(customUser.getMglgUser().getRegDate().toString())
								.userSnsId(customUser.getMglgUser().getUserSnsId())
								.build();
		//???????????? ???????????? ????????????
		

		mv.addObject("followCnt", followCnt);
		mv.addObject("requestList", requestFollowDTOList);
		mv.addObject("user", user);
		mv.setViewName("profile.html");
		return mv;
	}
	//??????????????? ??????
	@GetMapping("/profileAjax")
	public ResponseEntity<?> profileAjax(@AuthenticationPrincipal CustomUserDetails customUser,@PageableDefault(page = 0, size = 5)Pageable pageable) {
		int userId = customUser.getMglgUser().getUserId();
		MglgResponseDTO<MglgUserDTO> response = new MglgResponseDTO<>();
		try {
			Page<MglgUser> requestFollowList = userRelationService.requestFollowList(userId,pageable);
			Page<MglgUserDTO> requestFollowDTOList = requestFollowList.map(followList -> 
																				MglgUserDTO.builder()
																				 .userName(followList.getUserName())
																				 .userId(followList.getUserId())
																				 .build()
			);
			response.setPageItems(requestFollowDTOList);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	
	// ??? ??????????????? ??????
	@GetMapping("/myBoard")
	public ModelAndView myBoard() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/myBoard.html");
		return mv;
	}

	// ??????????????? ??????
	// ?????? ?????? ???????????? + ?????????
	@GetMapping("/follower")
	public ModelAndView followList(@AuthenticationPrincipal CustomUserDetails loginUser) {
			int userId = loginUser.getMglgUser().getUserId();
			ModelAndView mv = new ModelAndView();

					
					mv.setViewName("/user/follower.html");
					mv.addObject("userIds", userId);
				
					return mv;
	}

	// ???????????? ??????
	@GetMapping("/following")
	public ModelAndView followingList(@AuthenticationPrincipal CustomUserDetails loginUser) {
		int userId = loginUser.getMglgUser().getUserId();
		ModelAndView mv = new ModelAndView();

				
				mv.setViewName("/user/follower.html");
				mv.addObject("userId", userId);
					
					mv.setViewName("/user/following.html");
					
					
					return mv;
	}



		//?????? ????????? ??????
		// ?????? ?????? ???????????? + ?????????
		@GetMapping("/getAdminUserList")
		public ModelAndView getAdminUserList(MglgUserDTO userDTO, @PageableDefault(page = 0, size = 10) Pageable pageable) {

			MglgUser user = MglgUser.builder()
						   .searchCondition(userDTO.getSearchCondition())
						   .searchKeyword(userDTO.getSearchKeyword())
						   .build();
			
			Page<CamelHashMap> pageUserList = mglgUserService.getAdminUserList(user, pageable);
			Page<MglgUserDTO> pageUserDTOList = pageUserList.map(pageUser -> 
														MglgUserDTO.builder()
																	.userId((int)pageUser.get("userId"))
																	.userName(String.valueOf(pageUser.get("userName")))
																	.password(String.valueOf(pageUser.get("password")))
																	.firstName(String.valueOf(pageUser.get("firstName")))
																	.lastName(String.valueOf(pageUser.get("lastName")))
																	.phone(String.valueOf(pageUser.get("phone")))
																	.email(String.valueOf(pageUser.get("email")))
																	.address(String.valueOf(pageUser.get("address")))
																	.bio(String.valueOf(pageUser.get("bio")))
																	.userBanYn(String.valueOf(pageUser.get("userBanYn")))
																	.regDate(String.valueOf(pageUser.get("regDate")) == null ?
																		   	null :
																		   		String.valueOf(pageUser.get("regDate")))
																	.reportCnt(Integer.valueOf(String.valueOf(pageUser.get("reportCnt"))))
																	.build()
															);

						ModelAndView mv = new ModelAndView();
						
						mv.setViewName("/admin/adminUser.html");
						
						mv.addObject("getUserList", pageUserDTOList);
						
						if(userDTO.getSearchCondition() != null && !userDTO.getSearchCondition().equals("")) {
							mv.addObject("searchCondition", userDTO.getSearchCondition());
						}
						
						if(userDTO.getSearchKeyword() != null && !userDTO.getSearchKeyword().equals("")) {
							mv.addObject("searchKeyword", userDTO.getSearchKeyword());
						}
						
						return mv;
		}//getUserList???
	
	
	//????????? ??? ?????? - ?????????
	@GetMapping("/login")
	public ModelAndView loginView() {
		ModelAndView mv = new ModelAndView();
		System.out.println("?????? ????????? ???????????????");
		mv.setViewName("user/login.html");
		return mv;
	}
	
	//?????? ???????????? ???????????????.
	//?????? ????????? ???????????????, ???????????? ????????? ????????? ???????????? ???????????? ???????????????.
	//??????????????? api?????? ????????? ??? ??? ???????????? ???????????? ????????? ????????? ???????????????.
	@RequestMapping("/socialLoginPage")
	public ModelAndView socialLoginInput(HttpServletResponse response, HttpSession session, SecurityContextHolder security) throws IOException {

		System.out.println(SecurityContextHolder.getContext());
		
		ModelAndView mv = new ModelAndView();
		
		CustomUserDetails userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//???????????? ?????? ?????? ???????????? ????????? ???????????? ??????.
		if(userInfo.getMglgUser().getRegDate() != null) {
			System.out.println("?????? ????????? ??????????????????.");
			System.out.println("????????? ???????????? ?????? : " + userInfo.getMglgUser().getUserId() + ", " + userInfo.getMglgUser().getEmail());
			MglgUser loginUser = mglgUserService.loginUser(userInfo.getMglgUser());
			MglgUserDTO loginUserDTO = MglgUserDTO.builder()
												  .userId(loginUser.getUserId())
												  .userName(loginUser.getUserName())
												  .userSnsId(loginUser.getUserSnsId())
												  .regDate(loginUser.getRegDate().toString())
												  .email(loginUser.getEmail())
												  .userRole(loginUser.getUserRole())
												  .build();
			
			session.setAttribute("loginUser", loginUserDTO);
			response.sendRedirect("/post/mainPost");
			//mv.setViewName("post/post.html");
		} else { //?????? ????????? ?????? ??????
			System.out.println("?????????????????????.");
			mv.setViewName("user/socialLoginPage.html");
		}
		return mv;
	}
	
	//??????????????? ????????? ????????? ???????????? ?????????
	//????????? ????????? ????????? ??????????????? ????????? ???????????? ???????????? ????????? ???????????? ?????? ???????????? ??????.
	//?????? ????????? ???????????? ????????? ????????? ?????? ????????? ???????????? ?????????????????????.
	@PostMapping("/socialNewLogin")
	public void socialLoginView(MglgUserDTO mglgUserDTO, HttpSession session,
			HttpServletResponse response) throws IOException {
		CustomUserDetails userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
//		System.out.println("?????????????????? ?????? ?????? : " + userInfo.getAttributes().keySet());
		
		MglgUser newUser = MglgUser.builder()
						   .userName(userInfo.getMglgUser().getUserName())
						   .userSnsId(userInfo.getMglgUser().getUserSnsId())
						   .userBanYn("N")
						   //.phone(userInfo.getAttributes().) //?????? ???????????? ???????????? ??????
						   .firstName(mglgUserDTO.getFirstName())
						   .lastName(mglgUserDTO.getLastName())
						   .email(userInfo.getMglgUser().getEmail())
						   .userNick(mglgUserDTO.getUserNick())
						   .regDate(LocalDateTime.now())
						   .userRole(userInfo.getMglgUser().getUserRole())
						   .build();
		
		newUser = mglgUserService.socialLoginProcess(newUser);
		System.out.println("??????????????? ??????????????????. ??????????????? ???????????????.");
		//???????????? ????????? ?????? ????????? ???????????? ?????? DTO??? ?????? ???????????? ???????????????.
//		MglgUser loginUser = mglgUserService.socialLoginUser(newUser);
//		MglgUserDTO loginUserDTO = MglgUserDTO.builder()
//											  .userId(loginUser.getUserId())
//											  .userName(loginUser.getUserName())
//											  .userSnsId(loginUser.getUserSnsId())
//											  .regDate(loginUser.getRegDate().toString())
//											  .email(loginUser.getEmail())
//											  .userRole(loginUser.getUserRole())
//											  .build();
//		session.setAttribute("loginUser", loginUserDTO);
		//???????????? ??????????????? ?????? ???(?????? ??? ?????? ???????????? ?????? )
		CustomUserDetails customUserDetails = mglgUserService.loadByUserId(newUser.getUserId());
		
		Authentication authetication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		securityContext.setAuthentication(authetication);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		
		response.sendRedirect("/post/mainPost");

	}
	//?????? ?????? -----?????? ??????????????? ??????
	@PostMapping("reportUser")
	public void reportUser(String msg, String url,HttpServletResponse response,@RequestParam("userId") int postUserId,@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException {
		System.out.println("????????? ?????? ??????");
		url = "/post/mainPost";
		reportUserBase(msg,url,response,postUserId,loginUser);
	}
	//?????? ?????? ----- ???????????? ??????????????? ??????
	@PostMapping("reportUserFollow")
	public void reportUserFollow(String msg, String url,HttpServletResponse response,@RequestParam("userId") int postUserId,@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException {
		url = "/social/otherUser?userId="+postUserId;
		reportUserBase(msg,url,response,postUserId,loginUser);
	}
	//?????? ?????? ????????? ?????????
	public void reportUserBase(String msg, String url,HttpServletResponse response,int postUserId,@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException {
		int userId = loginUser.getMglgUser().getUserId();
		msg = mglgUserService.reportUser(postUserId,userId);
		if(msg.equals("self")) {
			msg="?????? ????????? ????????? ??? ????????????.";
		}else if(msg.equals("success")) {
			msg= postUserId+"??? ????????? ??????????????????.";
		}else {
			msg= "??? ????????? ???????????? ????????? ??? ????????????.";
		}
	    try {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter w = response.getWriter();
	        w.write("<script>alert('"+msg+"');location.href='"+url+"';</script>");
			w.flush();
			w.close();
	    } catch(Exception e) {
			e.printStackTrace();
	    }
	}
	
	//????????? ???????????? ?????? url
//	@PostMapping("/login")
//	public ModelAndView loginProcess(@PageableDefault(page=0, size=5) Pageable pageable, MglgUserDTO userDTO, HttpSession session) {
//		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
//		Map<String, String> returnMap = new HashMap<String, String>();
//		ModelAndView mv = new ModelAndView();
//		
//		System.out.println(userDTO);
//		//????????? ?????? ???????????? ??????
//		try {
//			MglgUser user = MglgUser.builder()
//							.userId(userDTO.getUserId())
//							.build();
//			MglgUser checkedUser = mglgUserService.loginUser(user);
//			System.out.println("????????? ?????? : " + checkedUser);
//			if(checkedUser == null) {
//				System.out.println("???????????? ?????????.");
//				returnMap.put("msg", "idFail");
//			} else {
//				MglgUserDTO loginUser = MglgUserDTO.builder()
//									 	.userId(checkedUser.getUserId())
//									 	.userName(checkedUser.getUserName())
//									 	.password(checkedUser.getPassword())
//									 	.email(checkedUser.getEmail())
//									 	.phone(checkedUser.getPhone())
//									 	.userRole(checkedUser.getUserRole())
//									 	.build();
//					
//				session.setAttribute("loginUser", loginUser);
//				System.out.println("???????????? ?????? ????????? : " + loginUser);
//			}
//			responseDTO.setItem(returnMap);
//			mv.addObject("loginUser", (MglgUserDTO)session.getAttribute("loginUser"));
//			//???????????? ????????? ???????????? ?????????. ???????????? ????????? ???????????? ??? ????????? ?????? ???????????? ??????.
//			Page<MglgPost> postList = mglgPostService.getPagePostList(pageable);
//			Page<MglgPostDTO> postListDTO = postList.map(pageMglgPost -> MglgPostDTO.builder()
//																					.postId(pageMglgPost.getPostId())
//																					.userId(pageMglgPost.getMglgUser().getUserId())
//																					.postContent(pageMglgPost.getPostContent())
//																					.postDate((pageMglgPost.getPostDate()).toString())
//																					.restNm(pageMglgPost.getRestNm())
//																					.restRating(pageMglgPost.getRestRating())
//																					.postRating(pageMglgPost.getPostRating())
//																					.hashTag1(pageMglgPost.getHashTag1())
//																					.hashTag2(pageMglgPost.getHashTag2())
//																					.hashTag3(pageMglgPost.getHashTag3())
//																					.hashTag4(pageMglgPost.getHashTag4())
//																					.hashTag5(pageMglgPost.getHashTag5())
//																					//.betweenDate(Duration.between(LocalDateTime.now(), pageMglgPost.getPostDate()).getSeconds())
//																					.build()
//														);
//			mv.addObject("postList", postListDTO);
//			
//			System.out.println(postListDTO.getContent().size());
//			
//			for(int i = 0; i < postListDTO.getContent().size(); i++) {
//				System.out.println("111111111111111111111111111111111");
//				System.out.println(postListDTO.getContent().get(i).getBetweenDate());
//			}
//			
//			mv.setViewName("post/post.html");
//			
//			return mv;
//		} catch(Exception e) {
//			responseDTO.setErrorMessage(e.getMessage());
//			
//			mv.setViewName("user/login.html");
//			
//			return mv;
//		}
//	}

	//??????????????? ?????? ?????? ?????????(???????????? ????????? securityFilter??? ?????? ?????? ??????.)
	@GetMapping("/logout")
	public void logout() {
		
	}
	//?????? ?????? ???????????? ???
	@PostMapping("/updateUser")
	public void updateUser(MglgUserDTO mglgUserDTO, HttpSession session,
			HttpServletResponse response, @AuthenticationPrincipal CustomUserDetails customUser) throws IOException {
		System.out.println(mglgUserDTO);
		MglgUser user = MglgUser.builder()
						   .userNick(mglgUserDTO.getUserNick())
						   .userName(customUser.getMglgUser().getUserName())
						   .bio(mglgUserDTO.getBio())
						   .address(mglgUserDTO.getAddress())
						   .phone(mglgUserDTO.getPhone())
						   .email(customUser.getMglgUser().getEmail())
						   .firstName(customUser.getMglgUser().getFirstName())
						   .lastName(customUser.getMglgUser().getLastName())
						   .userId(customUser.getMglgUser().getUserId())
						   .userSnsId(customUser.getMglgUser().getUserSnsId())
						   .regDate(customUser.getMglgUser().getRegDate())
						   .userRole(customUser.getMglgUser().getUserRole())
						   .userBanYn(customUser.getMglgUser().getUserBanYn())				
						   .build();

		System.out.println(user);
		MglgUser updateUser = mglgUserService.updateUser(user);

		CustomUserDetails customUserDetails = mglgUserService.loadByUserId(updateUser.getUserId());
		
		Authentication authetication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		securityContext.setAuthentication(authetication);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		
		response.sendRedirect("/user/profile");

	}

}//????????? ???


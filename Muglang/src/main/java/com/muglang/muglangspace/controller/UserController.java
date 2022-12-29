package com.muglang.muglangspace.controller;

import java.io.IOException;
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
	
	//계정 관련 컨트롤
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
		System.out.println("profile use" + user);

		//맞팔로우 요청목록 보여주기
		

		
		mv.addObject("requestList", requestFollowDTOList);
		mv.addObject("user", user);
		mv.setViewName("profile.html");
		return mv;
	}
	//에이작스로 처리
	@GetMapping("/profileAjax")
	public ModelAndView profileAjax(@AuthenticationPrincipal CustomUserDetails customUser,@RequestParam("page_num") int page_num, Pageable pageable) {
		pageable = PageRequest.of(page_num, 5);
		int userId = customUser.getMglgUser().getUserId();
		Page<MglgUser> requestFollowList = userRelationService.requestFollowList(userId,pageable);
		Page<MglgUserDTO> requestFollowDTOList = requestFollowList.map(followList -> 
																					MglgUserDTO.builder()
																					 .userName(followList.getUserName())
																					 .userId(followList.getUserId())
																					 .build()
		);
		
		

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
		System.out.println("profile use" + user);

		//맞팔로우 요청목록 보여주기
		

		
		mv.addObject("requestList", requestFollowDTOList);
		mv.addObject("user", user);
		mv.setViewName("profile.html");
		return mv;
	}
	
	
	// 내 게시물으로 이동
	@GetMapping("/myBoard")
	public ModelAndView myBoard() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/myBoard.html");
		return mv;
	}

	// 팔로잉으로 이동
	// 유저 목록 불러오기 + 페이징
	@GetMapping("/follower")
	public ModelAndView followList(MglgUserDTO userDTO,@PageableDefault(page = 0, size = 5) Pageable pageable,HttpSession session) {
		MglgUserDTO temp = (MglgUserDTO)session.getAttribute("loginUser");
		
		MglgUser user = MglgUser.builder()
				   .searchKeyword(userDTO.getSearchKeyword())
				   .userId(temp.getUserId())
				   .build();
		
		ModelAndView mv = new ModelAndView();
		
		Page<MglgUser> pagefollowList = userRelationService.followList(user, pageable);
		Page<MglgUserDTO> pagefollowDTOList = pagefollowList.map(pageUser -> 
													MglgUserDTO.builder()
																.userId(pageUser.getUserId())
																.userName(pageUser.getUserName())
																.email(pageUser.getEmail())
																.userNick(pageUser.getUserNick())
																.build()
														);

					
					mv.setViewName("/user/follower.html");
					
					mv.addObject("followList", pagefollowDTOList);
					
					
					if(userDTO.getSearchKeyword() != null && !userDTO.getSearchKeyword().equals("")) {
						mv.addObject("searchKeyword", userDTO.getSearchKeyword());
					}
					
					return mv;
	}

	// 팔로워로 이동
	@GetMapping("/following")
	public ModelAndView followingList(MglgUserDTO userDTO,@PageableDefault(page = 0, size = 5) Pageable pageable,HttpSession session) {
		MglgUserDTO temp = (MglgUserDTO)session.getAttribute("loginUser");
		
		MglgUser user = MglgUser.builder()
				   .searchKeyword(userDTO.getSearchKeyword())
				   .userId(temp.getUserId())
				   .build();
		
		ModelAndView mv = new ModelAndView();
		
		Page<MglgUser> pagefollowingList = userRelationService.followingList(user, pageable);
		Page<MglgUserDTO> pagefollowingDTOList = pagefollowingList.map(pageUser -> 
													MglgUserDTO.builder()
																.userId(pageUser.getUserId())
																.userName(pageUser.getUserName())
																.email(pageUser.getEmail())
																.userNick(pageUser.getUserNick())
																.build()
														);

					
					mv.setViewName("/user/following.html");
					
					mv.addObject("followingList", pagefollowingDTOList);
					
					
					if(userDTO.getSearchKeyword() != null && !userDTO.getSearchKeyword().equals("")) {
						mv.addObject("searchKeyword", userDTO.getSearchKeyword());
					}
					
					return mv;
	}

	// 유저 목록 불러오기 + 페이징
	@GetMapping("/getUserList")
	public ModelAndView getUserList(MglgUserDTO userDTO, @PageableDefault(page = 0, size = 10) Pageable pageable) {

		MglgUser user = MglgUser.builder()
					   .searchCondition(userDTO.getSearchCondition())
					   .searchKeyword(userDTO.getSearchKeyword())
					   .build();
		
		Page<MglgUser> pageUserList = mglgUserService.getUserList(user, pageable);
		Page<MglgUserDTO> pageUserDTOList = pageUserList.map(pageUser -> 
													MglgUserDTO.builder()
																.userId(pageUser.getUserId())
																.userName(pageUser.getUserName())
																.password(pageUser.getPassword())
																.firstName(pageUser.getFirstName())
																.lastName(pageUser.getLastName())
																.phone(pageUser.getPhone())
																.email(pageUser.getEmail())
																.address(pageUser.getAddress())
																.bio(pageUser.getBio())
																.userBanYn(pageUser.getUserBanYn())
																.password(pageUser.getPassword())
																.firstName(pageUser.getFirstName())
																.regDate(pageUser.getRegDate() == null ?
																	   	null :
																	   		pageUser.getRegDate().toString())
																.reportCnt(pageUser.getReportCnt())
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
	}//getUserList끝

		//유저 노란색 처리
		// 유저 목록 불러오기 + 페이징
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
		}//getUserList끝
	
	
	//로그인 겟 맵핑 - 김동현
	@GetMapping("/login")
	public ModelAndView loginView() {
		ModelAndView mv = new ModelAndView();
		System.out.println("최초 로그인 시스템작동");
		mv.setViewName("user/login.html");
		return mv;
	}
	
	//소셜 로그인을 진행합니다.
	//소셜 계정이 존재할경우, 존재하지 않았을 경우의 두가지를 제어하는 로직입니다.
	//계정정보를 api에서 가져온 뒤 그 데이터를 사용하여 로그인 정보를 제어합니다.
	@RequestMapping("/socialLoginPage")
	public ModelAndView socialLoginInput(HttpServletResponse response, HttpSession session, SecurityContextHolder security) throws IOException {

		System.out.println(SecurityContextHolder.getContext());
		
		ModelAndView mv = new ModelAndView();
		
		CustomUserDetails userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//로그인을 했던 기존 유저인지 아닌지 확인하는 과정.
		if(userInfo.getMglgUser().getRegDate() != null) {
			System.out.println("기존 회원이 로그인합니다.");
			System.out.println("회원의 아이디와 메일 : " + userInfo.getMglgUser().getUserId() + ", " + userInfo.getMglgUser().getEmail());
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
		} else { //신규 회원일 경우 처리
			System.out.println("신규회원입니다.");
			mv.setViewName("user/socialLoginPage.html");
		}
		return mv;
	}
	
	//신규회원의 로그인 처리를 담당하는 메소드
	//계정의 검증을 끝내고 최종적으로 정보를 추가하여 처리하는 메소드 가입하고 메인 페이지로 이동.
	//유저 정보를 추가하는 자리를 추가할 경우 여기를 수정하여 수정하면됩니다.
	@PostMapping("/socialNewLogin")
	public void socialLoginView(MglgUserDTO mglgUserDTO, HttpSession session,
			HttpServletResponse response) throws IOException {
		CustomUserDetails userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
//		System.out.println("연동되어있는 정보 표시 : " + userInfo.getAttributes().keySet());
		
		MglgUser newUser = MglgUser.builder()
						   .userName(userInfo.getMglgUser().getUserName())
						   .userSnsId(userInfo.getMglgUser().getUserSnsId())
						   .userBanYn("N")
						   //.phone(userInfo.getAttributes().) //추가 입력창에 입력하는 정보
						   .firstName(mglgUserDTO.getFirstName())
						   .lastName(mglgUserDTO.getLastName())
						   .email(userInfo.getMglgUser().getEmail())
						   .userNick(mglgUserDTO.getUserNick())
						   .regDate(LocalDateTime.now())
						   .userRole(userInfo.getMglgUser().getUserRole())
						   .build();
		
		newUser = mglgUserService.socialLoginProcess(newUser);
		System.out.println("회원가입을 축하드립니다. 게시판으로 이동합니다.");
		//로그인한 유저의 세션 정보는 엔티티가 아닌 DTO로 따로 저장하여 사용할것임.
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
		//회원정보 수정시에도 삽입 필(수정 후 바로 반영하기 위함 )
		CustomUserDetails customUserDetails = mglgUserService.loadByUserId(newUser.getUserId());
		
		Authentication authetication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		securityContext.setAuthentication(authetication);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		
		response.sendRedirect("/post/mainPost");

	}

	
	//로그인 시도하는 임시 url
	@PostMapping("/login")
	public ModelAndView loginProcess(@PageableDefault(page=0, size=5) Pageable pageable, MglgUserDTO userDTO, HttpSession session) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView();
		
		System.out.println(userDTO);
		//로그인 과정 수행하는 부분
		try {
			MglgUser user = MglgUser.builder()
							.userId(userDTO.getUserId())
							.build();
			MglgUser checkedUser = mglgUserService.loginUser(user);
			System.out.println("비교군 계정 : " + checkedUser);
			if(checkedUser == null) {
				System.out.println("로그인을 실패함.");
				returnMap.put("msg", "idFail");
			} else {
				MglgUserDTO loginUser = MglgUserDTO.builder()
									 	.userId(checkedUser.getUserId())
									 	.userName(checkedUser.getUserName())
									 	.password(checkedUser.getPassword())
									 	.email(checkedUser.getEmail())
									 	.phone(checkedUser.getPhone())
									 	.userRole(checkedUser.getUserRole())
									 	.build();
					
				session.setAttribute("loginUser", loginUser);
				System.out.println("로그인한 유저 아이디 : " + loginUser);
			}
			responseDTO.setItem(returnMap);
			mv.addObject("loginUser", (MglgUserDTO)session.getAttribute("loginUser"));
			//로그인후 게시글 페이지로 이동함. 게시글의 정보를 조회하고 이 정보를 다음 화면단에 넘김.
			Page<MglgPost> postList = mglgPostService.getPagePostList(pageable);
			Page<MglgPostDTO> postListDTO = postList.map(pageMglgPost -> MglgPostDTO.builder()
																					.postId(pageMglgPost.getPostId())
																					.userId(pageMglgPost.getMglgUser().getUserId())
																					.postContent(pageMglgPost.getPostContent())
																					.postDate((pageMglgPost.getPostDate()).toString())
																					.restNm(pageMglgPost.getRestNm())
																					.restRating(pageMglgPost.getRestRating())
																					.postRating(pageMglgPost.getPostRating())
																					.hashTag1(pageMglgPost.getHashTag1())
																					.hashTag2(pageMglgPost.getHashTag2())
																					.hashTag3(pageMglgPost.getHashTag3())
																					.hashTag4(pageMglgPost.getHashTag4())
																					.hashTag5(pageMglgPost.getHashTag5())
																					//.betweenDate(Duration.between(LocalDateTime.now(), pageMglgPost.getPostDate()).getSeconds())
																					.build()
														);
			mv.addObject("postList", postListDTO);
			
			System.out.println(postListDTO.getContent().size());
			
			for(int i = 0; i < postListDTO.getContent().size(); i++) {
				System.out.println("111111111111111111111111111111111");
				System.out.println(postListDTO.getContent().get(i).getBetweenDate());
			}
			
			mv.setViewName("post/post.html");
			
			return mv;
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			mv.setViewName("user/login.html");
			
			return mv;
		}
	}

	//로그아웃을 하는 매핑 메소드(아무것도 없어도 securityFilter에 정의 되어 있음.)
	@GetMapping("/logout")
	public void logout() {
		
	}
	//유저 정보 업데이트 폼
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

}//페이지 끝


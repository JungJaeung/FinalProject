package com.muglang.muglangspace.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.common.LoginUserLoad;
import com.muglang.muglangspace.dto.MglgPostDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.dto.ResponseDTO;
import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.service.mglgadmin.AdminService;
import com.muglang.muglangspace.service.mglgcomment.MglgCommentService;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	private MglgPostService mglgPostService;
	
	@Autowired
	private MglgUserService mglgUserService;

	@Autowired
	private MglgCommentService mglgCommentService;

	@Autowired
	private AdminService adminService;
	
	//글쓰기 버튼으로 적용되는 글 새로 작성
	@PostMapping("/insertPost")
	public ResponseEntity<?> insertPost(MglgPostDTO mglgPostDTO, 
			HttpServletResponse response
			,@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException {
		System.out.println(mglgPostDTO);
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

		System.out.println("가져온 내용 : " + mglgPostDTO);

		try {
			MglgPost mglgPost = MglgPost.builder()
					.postId(mglgPostDTO.getPostId())
					.mglgUser(loginUser.getMglgUser())
					.postContent(mglgPostDTO.getPostContent())
					.restNm(mglgPostDTO.getRestNm())
					.postDate(LocalDateTime.now())
					.postRating(mglgPostDTO.getPostRating())
					.restRating(mglgPostDTO.getRestRating())
					.hashTag1(mglgPostDTO.getHashTag1() == ""? "0": mglgPostDTO.getHashTag1())
					.hashTag2(mglgPostDTO.getHashTag2() == ""? "0": mglgPostDTO.getHashTag2())
					.hashTag3(mglgPostDTO.getHashTag3() == ""? "0": mglgPostDTO.getHashTag3())
					.hashTag4(mglgPostDTO.getHashTag4() == ""? "0": mglgPostDTO.getHashTag4())
					.hashTag5(mglgPostDTO.getHashTag5() == ""? "0": mglgPostDTO.getHashTag5())
					.build();

			mglgPost = mglgPostService.insertPost(mglgPost);
			//화면단으로 넘길 DTO를 생성
			MglgPostDTO returnDTO = MglgPostDTO.builder()
												 .postId(mglgPost.getPostId())
												 .userId(loginUser.getMglgUser().getUserId())
												 .restNm(mglgPost.getRestNm())
												 .postDate(mglgPost.getPostDate().toString())
												 .postRating(mglgPost.getPostRating())
												 .restRating(mglgPost.getRestRating())
												 .hashTag1(mglgPost.getHashTag1() == ""? "0": mglgPost.getHashTag1())
												 .hashTag2(mglgPost.getHashTag2() == ""? "0": mglgPost.getHashTag2())
												 .hashTag3(mglgPost.getHashTag3() == ""? "0": mglgPost.getHashTag3())
												 .hashTag4(mglgPost.getHashTag4() == ""? "0": mglgPost.getHashTag4())
												 .hashTag5(mglgPost.getHashTag5() == ""? "0": mglgPost.getHashTag5())
												 .postContent(mglgPost.getPostContent())
												 .betweenDate(Duration.between(mglgPost.getPostDate(), LocalDateTime.now()).getSeconds())
												 .build();
												 
												 
			
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("insertPost", returnDTO);
			returnMap.put("loginUser", LoginUserLoad.toHtml(loginUser.getMglgUser()));
			responseDTO.setItem(returnMap);
			System.out.println("새로운 글을 추가합니다.");
			return ResponseEntity.ok().body(responseDTO); 
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@PutMapping("/updatePost")
	public ResponseEntity<?> updatePost(MglgPostDTO mglgPostDTO, HttpSession session) {
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		MglgUser mglgUser = new MglgUser();
		mglgUser.setUserId(mglgPostDTO.getUserId());
		mglgUser = mglgUserService.loginUser(mglgUser);
		System.out.println("수정 작업을 실행합니다." + mglgPostDTO);
		
		try {
			//수정될 게시글의 정보를 모두 담아 갱신하려는 객체 생성.
			MglgPost mglgPost = MglgPost.builder()
										.postId(mglgPostDTO.getPostId())
										.mglgUser(mglgUser)
										.postContent(mglgPostDTO.getPostContent())
										.restNm(mglgPostDTO.getRestNm())
										.postDate(LocalDateTime.parse(mglgPostDTO.getPostDate()))
										.build();
			
			MglgPost updateMglgPost = mglgPostService.updatePost(mglgPost);
			System.out.println(updateMglgPost);
			MglgPostDTO updateMglgPostDTO = MglgPostDTO.builder()
														.postId(mglgPost.getPostId())
														.userId(mglgPost.getMglgUser().getUserId())
														.postContent(mglgPost.getPostContent())
														.restNm(mglgPost.getRestNm())
														.build();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("getPost", updateMglgPostDTO);
			
			responseDTO.setItem(returnMap);
			System.out.println("수정 작업 마무리단계");
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(responseDTO);
		}

	}
	
	//삭제 작업 수행하기
	@PostMapping("/deletePost")
	public void deletePost(MglgPostDTO mglgPostDTO, HttpSession session,
			HttpServletResponse response) throws IOException {
		System.out.println("삭제 작업 실행");
		MglgUser mglgUser = new MglgUser();
		mglgUser.setUserId(mglgPostDTO.getUserId());
		mglgUser = mglgUserService.loginUser(mglgUser);

		MglgPost mglgPost = MglgPost.builder()
									.postId(mglgPostDTO.getPostId())
									.mglgUser(mglgUser)
									.build();
		
		mglgPostService.deletePost(mglgPost);
		
		response.sendRedirect("/post/mainPost");
	}
	
	
	@GetMapping("/mainPost")
	// 로그인후 메인페이지로 이동하여 게시글의 내용을 최종적으로 html화면단에 넘기는 메소드
	public ModelAndView getPostList(@PageableDefault(page = 0, size = 5) Pageable pageable,
			HttpServletResponse response, HttpSession session, @AuthenticationPrincipal CustomUserDetails loginUser)
			throws IOException {
		
		
		int userId = loginUser.getMglgUser().getUserId();


		Page<CamelHashMap> pagePostList = mglgPostService.getPagePostList(pageable, userId);
		
		for (int i = 0; i < pagePostList.getContent().size(); i++) {
			System.out.println(pagePostList.getContent().get(i).toString());
		}

		for (int i = 0; i < pagePostList.getContent().size(); i++) {
			pagePostList.getContent().get(i).put(
				"between_date", Duration.between(
						((Timestamp) pagePostList.getContent().get(i).get("postDate")).toLocalDateTime(),
						LocalDateTime.now()).getSeconds()
			);
			
			pagePostList.getContent().get(i).put(
					"post_date",
					String.valueOf(
							((Timestamp) pagePostList.getContent().get(i).get("postDate")).toLocalDateTime()
					)
			);
		}
		
		for (int i = 0; i < pagePostList.getContent().size(); i++) {
			System.out.println(pagePostList.getContent().get(i).toString());
		}
		
		// 화면단에 뿌려줄 정보를 반환하는 객체 생성. 로그인한 유저의 정보와 게시글의 정보를 담고있다.
		ModelAndView mv = new ModelAndView();
		mv.setViewName("post/post.html");
		mv.addObject("postList", pagePostList);
		// 세션 대신 유저 인증 유저 토큰의 정보 추출하여 화면단으로 표시
		mv.addObject("loginUser", LoginUserLoad.toHtml(loginUser.getMglgUser()));

//		for(int i = 0; i < pagePostListDTO.getContent().size(); i++) {
//			System.out.println(pagePostListDTO.getContent().get(i).getBetweenDate());
//		}

		mv.addObject("loginUser", (MglgUserDTO) session.getAttribute("loginUser"));

		return mv;
	}
	
	@PostMapping("/mainPost")
	//스크롤시 데이터 불러오는 로직
	//재웅이형이 작성한 바로 위 로직이랑 거의 동일
	public ResponseEntity<?> getPostListScroll(Pageable pageable, @RequestParam("page_num") int page_num, 
			@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException  {
		
		int userId = loginUser.getMglgUser().getUserId();
		pageable = PageRequest.of(page_num, 5);
		
		Page<CamelHashMap> pagePostList = mglgPostService.getPagePostList(pageable, userId);
		
		
		return ResponseEntity.ok().body(pagePostList);
	}
	
	public List<MglgPost> getYourPost() {
		return null;
	}
	

	//포스트 단건 조회
	@GetMapping("/post")
	public ResponseEntity<?> getPost(@RequestParam("postId") int postId) {
		MglgResponseDTO<MglgPostDTO> response = new MglgResponseDTO<>();
		
		try {
			MglgPost post = MglgPost.builder()
										.postId(postId)
										.build();
			
			post = mglgPostService.getPost(post);
			MglgPostDTO returnPostDTO = MglgPostDTO.builder()
												   .postId(post.getPostId())
												   .postContent(post.getPostContent())
												   .postDate(post.getPostDate().toString())
												   //아래줄 원래 .postId(post.getMglgUser().getUserId()) 2022/12/21 19:09
												   .userId(post.getMglgUser().getUserId())
												   .restNm(post.getRestNm())
												   .build();

			response.setItem(returnPostDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	//팔로우 하고 있는사람 포스트만 불러오는 로직
	@GetMapping("/getFollowerPost")
	public ResponseEntity<?> getFollowerPost(@PageableDefault(page=0,size=5) Pageable pageable,@AuthenticationPrincipal CustomUserDetails loginUser) {
		MglgResponseDTO<MglgPostDTO> response = new MglgResponseDTO<>();
		int userId = loginUser.getMglgUser().getUserId();
		try {		
			
		Page<MglgPost> pagePostList = mglgPostService.getFollowerPost(userId,pageable);
		Page<MglgPostDTO> pagePostListDTO = pagePostList.map(pageMglgPost->MglgPostDTO.builder()
																						.userId(pageMglgPost.getMglgUser().getUserId())
																						.postId(pageMglgPost.getPostId())
																						.postContent(pageMglgPost.getPostContent())
																						.postDate(pageMglgPost.getPostDate().toString())
																						.restNm(pageMglgPost.getRestNm())
																						.restRating(pageMglgPost.getRestRating())
																							.postRating(pageMglgPost.getPostRating())
																						.hashTag1(pageMglgPost.getHashTag1())
																						.hashTag2(pageMglgPost.getHashTag2())
																						.hashTag3(pageMglgPost.getHashTag3())
																						.hashTag4(pageMglgPost.getHashTag4())
																						.hashTag5(pageMglgPost.getHashTag5())
																						.betweenDate(Duration.between(pageMglgPost.getPostDate(), LocalDateTime.now()).getSeconds())
																						.build()
		);
				response.setPageItems(pagePostListDTO);
				return ResponseEntity.ok().body(response);
			} catch (Exception e) {
				response.setErrorMessage(e.getMessage());
				return ResponseEntity.badRequest().body(response);
			}
		}
	
	// 좋아요 눌렀을때 ajax
		@GetMapping("likeUp")
		public ResponseEntity<?> likeUp(@RequestParam("userId") int userId, @RequestParam("postId") int postId) {
			try {
				int likeCnt = mglgPostService.likeUp(userId, postId);
				return ResponseEntity.ok().body(likeCnt);
				
			} catch (Exception e) {
				// TODO: handle exception
				return ResponseEntity.ok().body(0);
			}

		}

		@GetMapping("likeDown")
		public ResponseEntity<?> likeDown(@RequestParam("userId") int userId, @RequestParam("postId") int postId) throws IOException{
			try {
				int likeCnt = mglgPostService.likeDown(userId, postId);
				return ResponseEntity.ok().body(likeCnt);
				
			} catch (Exception e) {
				// TODO: handle exception
				return ResponseEntity.ok().body(0);
			}
		}
		
		@GetMapping("reportPost")
		public void reportPost(String msg, String url,HttpServletResponse response,@RequestParam("postId") int postId,@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException {
			int userId = loginUser.getMglgUser().getUserId();
			msg = mglgPostService.reportPost(postId,userId);
			url = "/post/mainPost";
			
			if(msg.equals("self")) {
				msg= "자신의 글은 신고할 수 없습니다.";
			}else if(msg.equals("success")) {
				msg= postId+"번 포스트를 신고했습니다.";
			}else {
				msg= "중복해서 신고할 수 없습니다.";
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

}

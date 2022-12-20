package com.muglang.muglangspace.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.dto.MglgPostDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	private MglgPostService mglgPostService;
	
	@Autowired
	private MglgUserService mglgUserService;

	//메인 게시글 사이트 글쓰기 페이지로 이동 (로그인 세션 적용은 추후에 할 예정)
	@GetMapping("/newPost")
	public ModelAndView goInsertView(HttpSession session) {
		MglgUserDTO temp = (MglgUserDTO)session.getAttribute("loginUser");
		ModelAndView mv = new ModelAndView();
		mv.addObject("loginUser", temp);
		mv.setViewName("post/insertPost.html");
		System.out.println("새글을 작성하는 페이지로 이동합니다.");
		return mv; 
		
	}
	
	//글쓰기 버튼으로 적용되는 글 새로 작성
	@PostMapping("/insertPost")
	public ModelAndView insertPost(MglgPostDTO mglgPostDTO, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		MglgUserDTO temp = (MglgUserDTO)session.getAttribute("loginUser");
		System.out.println(temp);
		MglgUser mglgUser = MglgUser.builder()
									.userId(temp.getUserId())
									.build();

		System.out.println("가져온 내용 : " + mglgPostDTO);

		MglgPost mglgPost = MglgPost.builder()
									.postId(mglgPostDTO.getPostId())
									.mglgUser(mglgUser)
									.postContent(mglgPostDTO.getPostContent())
									.restNm(mglgPostDTO.getRestNm())
									.postDate(LocalDateTime.now())
									.postRating(mglgPostDTO.getPostRating())
									.restRating(mglgPostDTO.getRestRating())
									.build();
		
		mglgPostService.insertPost(mglgPost);
		mv.addObject("loginUser", temp);
		mv.setViewName("post/post.html");
		return mv;
	}
	
	@PutMapping("/updatePost")
	public void updatePost(MglgPost mglgPost, HttpSession session,
			HttpServletResponse response) throws IOException {
		MglgPost getMglgPost = mglgPostService.updatePost(mglgPost);
		System.out.println(getMglgPost);
		//갱신한 데이터내용을 수정한 뒤 다시 메인화면으로 이동하여 돌아옴.
//		MglgPostDTO getMglgPostDTO = MglgPostDTO.builder()
//												.postId(getMglgPost.getPostId())
//												.userId(getMglgPost.getMglgUser().getUserId())
//												.postContent(getMglgPost.getPostContent())
//												.restNm(getMglgPost.getRestNm())
//												.restRating(getMglgPost.getRestRating())
//												.postRating(getMglgPost.getPostRating())
//												.postDate(getMglgPost.getPostDate().toString())
//												.build();
//		mv.addObject("getPost", getMglgPostDTO);
//		mv.addObject("loginUser",(MglgUserDTO)session.getAttribute("loginUser"));
		response.sendRedirect("/post/mainPost");
		
//		return mv;
	}
	
	public void deletePost(MglgPost mglgpost) {
		
	}
	
	public void likePost(MglgPost mglgpost) {
		
	}
	
	@GetMapping("/mainPost")
	//로그인후 메인페이지로 이동하여 게시글의 내용을 최종적으로 html화면단에 넘기는 메소드
	public ModelAndView getPostList(@PageableDefault(page=0, size=10) Pageable pageable, HttpSession session) {
		Page<MglgPost> pagePostList = mglgPostService.getPagePostList(pageable);
		
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
																			.build()
															);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("post/post.html");
		mv.addObject("loginUser", (MglgUserDTO)session.getAttribute("loginUser"));
		mv.addObject("postList", pagePostListDTO);
		
		return mv;
	}
	
	public List<MglgPost> getYourPost() {
		return null;
	}
	
	public int cntPost(int postId) {
		return 0;
	}

	//포스트 단건 조회
	@GetMapping("post")
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
												   .postId(post.getMglgUser().getUserId())
												   .restNm(post.getRestNm())
												   .userId(post.getMglgUser().getUserId())
												   .build();

			response.setItem(returnPostDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	

}

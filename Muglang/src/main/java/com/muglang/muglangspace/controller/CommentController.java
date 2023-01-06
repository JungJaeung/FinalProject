package com.muglang.muglangspace.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.muglang.muglangspace.dto.MglgCommentDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.service.mglgadmin.AdminService;
import com.muglang.muglangspace.service.mglgcomment.MglgCommentService;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private MglgCommentService mglgCommentService;
	@Autowired
	private MglgUserService mglgUserService;
	@Autowired
	private AdminService adminService;
	
	
	//커멘트 조회
	@GetMapping("/comment")
	public ResponseEntity<?> getComment(@RequestParam("commentId") int commentId, @RequestParam("postId") int postId) {
		MglgResponseDTO<MglgCommentDTO> response = new MglgResponseDTO<>();
		try {
			MglgPost post = MglgPost.builder()
								    .postId(postId)
								    .build();
			MglgComment comment = MglgComment.builder()
										.commentId(commentId)
										.mglgPost(post)
										.build();
			
			comment = mglgCommentService.getComment(comment);
			MglgCommentDTO returnCommentDTO = MglgCommentDTO.builder()
												   .commentId(comment.getCommentId())
												   .commentContent(comment.getCommentContent())
												   .commentDate(comment.getCommentDate().toString())
												   .postId(comment.getMglgPost().getPostId())
												   .userId(comment.getMglgUser().getUserId())
												   .build();

			response.setItem(returnCommentDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}


	// 댓글 리스트 불러오기
//	@GetMapping("/commentList")
//	public ResponseEntity<?> getCommentList(MglgComment comment, @PageableDefault(page = 0, size = 8) Pageable pageable,
//			@RequestParam("postId") int postId) {
//		Page<MglgComment> commentList = mglgCommentService.getCommentList(comment, pageable, postId);
//		
//		System.out.println("댓글 조회 ㄱㄱ");
//		return ResponseEntity.ok().body(commentList);
//	}
	
	//댓글 리스트 불러오기 - 불러온 댓글들을 다시 화면단으로 전송
	@GetMapping("/commentList")
	public ResponseEntity<?> getCommentList(HttpSession session ,MglgComment comment, @PageableDefault(page = 0, size = 8) Pageable pageable,
			@RequestParam("postId") int postId) {
		//이 객체에는 댓글이 현재 가지고 있는 유저 정보와 포스팅의 정보도 같이 가지고 있다.
		Page<MglgComment> pageCommentList = mglgCommentService.getPageCommentList(pageable, postId);
		//댓글 목록 테스트용
		for(MglgComment comment1 :  pageCommentList) {
			System.out.println(comment1);
		}
		try {
//			returnMap.put("commentList", pageCommentList);
//			returnMap.put("", pageCommentList.getPageable().getPageNumber());
			return ResponseEntity.ok().body(pageCommentList);
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(pageCommentList);
		}

	}
	
	//코멘트 리스트 페이징 무한 스크롤
	@PostMapping("/commentList")
	//스크롤시 데이터 불러오는 로직
	//재웅이형이 작성한 바로 위 로직이랑 거의 동일
	public ResponseEntity<?> getCommentListScroll(Pageable pageable, @RequestParam("page_num") int page_num,
			@RequestParam("postId") int postId) {
		pageable = PageRequest.of(page_num, 8);
		
		Page<MglgComment> pageCommentList = mglgCommentService.getPageCommentList(pageable, postId);
//		Page<MglgCommentDTO> pageCommentListDTO = pageCommentList.map(pageMglgComment->MglgCommentDTO.builder()
//																			.userId(pageMglgComment.getMglgUser().getUserId())
//																			.commentId(pageMglgComment.getCommentId())
//																			.commentContent(pageMglgComment.getCommentContent())
//																			.commentDate(pageMglgComment.getCommentDate().toString())
//																			.build()
//															);
//		
//	
		return ResponseEntity.ok().body(pageCommentList);
	}
	

	// 댓글 작성 쿼리 실행
	@PostMapping("/insertComment")
	public void insertComment(@RequestParam("userId") int userId, @RequestParam("postId") int postId, @RequestParam("commentContent") String commentContent)
			throws IOException {
		System.out.println("댓글 입력 작업");
		mglgCommentService.insertComment(userId, postId, commentContent);
	}

	// 댓글 삭제
	@PostMapping("/deleteComment")
	public void deleteComment(@RequestParam("commentId") int commentId, @RequestParam("postId") int postId,
			HttpServletResponse response, MglgComment comment)
			throws IOException {
		System.out.println("댓글 삭제");
		mglgCommentService.deleteComment(commentId, postId);
		adminService.deleteReport(commentId, postId);

		response.sendRedirect("/post/mainPost");
	}

	// 댓글 업데이트
	@PutMapping("/updateComment")
	public void updateComment(@RequestParam("commentId") int commentId, @RequestParam("postId") int postId,
			@RequestParam("commentContent") String commentContent) throws IOException {
		System.out.println("댓글 수정");
		mglgCommentService.updateComment(commentId, postId, commentContent);
	}
	//댓글 신고하기
	@PostMapping("reportComment")
	public void reportComment(HttpServletResponse response,@RequestParam("postId") int postId,
								@RequestParam("commentId") int commentId,@RequestParam("postUserId") int postUserId,
								@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException {
		int userId = loginUser.getMglgUser().getUserId();
		String msg = mglgCommentService.reportComment(postId,commentId,postUserId,userId);
		String url = "/post/mainPost";
		
		if(msg.equals("self")) {
			msg= "자신의 댓글은 신고할 수 없습니다.";
		}else if(msg.equals("success")) {
			msg= commentId+"번 댓글을 신고했습니다.";
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

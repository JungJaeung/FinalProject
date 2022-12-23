package com.muglang.muglangspace.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muglang.muglangspace.dto.MglgCommentDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.service.mglgadmin.AdminService;
import com.muglang.muglangspace.service.mglgcomment.MglgCommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private MglgCommentService mglgCommentService;
	@Autowired
	private AdminService adminService;
	
	
	//커멘트 조회
	@GetMapping("/comment")
	public ResponseEntity<?> getComment(@RequestParam("commentId") int commentId,@RequestParam("postId") int postId) {
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

	//커멘트 아이디로 삭제

	@Transactional
	@GetMapping("deleteComment")
	public void deleteComment(@RequestParam("commentId") int commentId,@RequestParam("postId") int postId, HttpServletResponse response) throws IOException {
		mglgCommentService.deleteComment(commentId,postId);
		 adminService.deleteReport(commentId,postId);
		 System.out.println("--------딜리트 끝--------");

		 response.sendRedirect("/admin/commentReport");
	}
	
	//댓글 작성 쿼리 실행
	@PostMapping("/insertComment")
	public void insertComment(HttpSession session, MglgCommentDTO commentDTO, HttpServletResponse response) throws IOException {
		
		MglgComment mglgComment = MglgComment.builder()
											 .mglgPost(MglgPost.builder().postId(commentDTO.getPostId()).build())
											 .mglgUser(MglgUser.builder().userId(commentDTO.getUserId()).build())
											 .commentContent(commentDTO.getCommentContent())
											 .commentDate(LocalDateTime.now())
											 .build();
		mglgCommentService.insertComment(mglgComment);
		response.sendRedirect("/post/post");
	}
	
}

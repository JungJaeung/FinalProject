package com.muglang.muglangspace.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.dto.MglgCommentDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.service.comment.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	
	//커멘트 조회
	@GetMapping("comment")
	public ResponseEntity<?> getComment(@RequestParam("commentId") int commentId) {
		MglgResponseDTO<MglgCommentDTO> response = new MglgResponseDTO<>();
		
		try {
			MglgComment comment = MglgComment.builder()
										.commentId(commentId)
										.build();
			
			comment = commentService.getComment(comment);
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
	@GetMapping("deleteComment/{commentId}/{postId}")
	public void deleteComment(@PathVariable("commentId") int commentId,@PathVariable("postId") int postId, HttpServletResponse response) throws IOException {
		 commentService.deleteComment(commentId,postId);
		 
		 response.sendRedirect("/admin/commentReport");
	}
	
}

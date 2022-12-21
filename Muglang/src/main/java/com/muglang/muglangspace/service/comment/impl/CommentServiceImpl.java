package com.muglang.muglangspace.service.comment.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.dto.MglgCommentDTO;
import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgCommentId;
import com.muglang.muglangspace.repository.MglgCommentRepository;
import com.muglang.muglangspace.service.comment.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
		@Autowired
		private MglgCommentRepository mglgCommentRepository;

		@Override
		public MglgComment getComment(MglgComment comment) {
			// TODO Auto-generated method stub
			System.out.println(comment.getCommentId());
			MglgCommentId commentIds = new MglgCommentId();
			commentIds.setCommentId(comment.getCommentId());
			commentIds.setMglgPost(comment.getMglgPost().getPostId());
			return mglgCommentRepository.findById(commentIds).get();
		}

		@Override
		public void deleteComment(int commentId,int postId) {
			mglgCommentRepository.deleteComment(commentId,postId);
		}
		
		

		
}

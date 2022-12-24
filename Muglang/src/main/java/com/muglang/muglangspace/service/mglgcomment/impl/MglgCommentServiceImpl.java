package com.muglang.muglangspace.service.mglgcomment.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.dto.MglgCommentDTO;
import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgCommentId;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.repository.MglgCommentRepository;
import com.muglang.muglangspace.service.mglgcomment.MglgCommentService;

@Service
public class MglgCommentServiceImpl implements MglgCommentService{
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
		public Optional<MglgComment> getCommentList(MglgPost mglgPost) {
			// TODO Auto-generated method stub
			return mglgCommentRepository.findAllById(mglgPost);
		}
		
		@Override
		public void deleteComment(int commentId,int postId) {
			System.out.println("commentId============" + commentId);
			System.out.println("postId============" + postId);
			mglgCommentRepository.deleteComment(commentId,postId);
		}

		@Override
		public void insertComment(MglgComment comment) {
			// TODO Auto-generated method stub
			mglgCommentRepository.save(comment);
		}

		@Override
		public void updateComment(MglgComment comment) {
			// TODO Auto-generated method stub
			mglgCommentRepository.updateComment(comment);
		}


}

package com.muglang.muglangspace.service.mglgcomment.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgCommentId;
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
		public Page<MglgComment> getCommentList(MglgComment comment, Pageable pageable, int postId) {
			// TODO Auto-generated method stub
			return mglgCommentRepository.getCommentList(pageable, postId);
		}
//		@Override
//		public Optional<MglgComment> getCommentList(MglgPost mglgPost) {
//			// TODO Auto-generated method stub
//			return mglgCommentRepository.findAllByCommentId(mglgPost);
//		}
		
		@Override
		public void deleteComment(int commentId,int postId) {
			mglgCommentRepository.deleteComment(commentId,postId);
		}
		
		@Override
		public void updateComment(int commentId, int postId, String commentContent) {
			// TODO Auto-generated method stub
			mglgCommentRepository.updateComment(commentId, postId, commentContent);
		}

		@Override
		public void insertComment(int userId, int postId, String commentContent) {
			mglgCommentRepository.insertComment(userId, postId, commentContent);
			
		}

		@Override
		public Page<MglgComment> getPageCommentList(Pageable pageable) {
			// TODO Auto-generated method stub
			return mglgCommentRepository.findAll(pageable);
		}

//		@Override
//		public void insertComment(MglgComment comment) {
//			// TODO Auto-generated method stub
//			mglgCommentRepository.save(comment);
//		}

//		@Override
//		public void updateComment(MglgComment comment) {
//			// TODO Auto-generated method stub
//			mglgCommentRepository.updateComment(comment);
//		}


}

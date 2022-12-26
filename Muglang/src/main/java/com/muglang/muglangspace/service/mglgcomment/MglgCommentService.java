package com.muglang.muglangspace.service.mglgcomment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muglang.muglangspace.entity.MglgComment;

public interface MglgCommentService {
	MglgComment getComment(MglgComment comment);

//	Optional<MglgComment> getCommentList(MglgPost mglgPost);

	void deleteComment(int commentId, int postId);

//	void insertComment(MglgComment comment);
//	
//	void updateComment(MglgComment comment);

	void updateComment(int commentId, int postId, String commentContent);

	void insertComment(int userId, int postId, String commentContent);

	Page<MglgComment> getCommentList(MglgComment comment, Pageable pageable, int postId);

	Page<MglgComment> getPageCommentList(Pageable pageable);
}

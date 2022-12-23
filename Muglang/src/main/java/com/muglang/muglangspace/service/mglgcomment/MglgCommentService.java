package com.muglang.muglangspace.service.mglgcomment;



import com.muglang.muglangspace.entity.MglgComment;

public interface MglgCommentService {
	MglgComment getComment(MglgComment comment);
	
	void deleteComment(int commentId, int postId);
	
	void insertComment(MglgComment comment);

}

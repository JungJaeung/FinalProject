package com.muglang.muglangspace.service.comment;

import java.util.List;


import com.muglang.muglangspace.entity.MglgComment;

public interface CommentService {
	MglgComment getComment(MglgComment comment);
	
	void deleteComment(int commentId, int postId);

}

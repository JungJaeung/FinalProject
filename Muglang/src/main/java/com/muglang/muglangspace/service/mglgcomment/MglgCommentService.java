package com.muglang.muglangspace.service.mglgcomment;



import java.util.List;
import java.util.Optional;

import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgPost;

public interface MglgCommentService {
	MglgComment getComment(MglgComment comment);
	
	Optional<MglgComment> getCommentList(MglgPost mglgPost);
	
	void deleteComment(int commentId, int postId);
	
	void insertComment(MglgComment comment);
	
	void updateComment(MglgComment comment);

}

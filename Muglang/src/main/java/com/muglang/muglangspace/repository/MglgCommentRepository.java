package com.muglang.muglangspace.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgCommentId;
import com.muglang.muglangspace.entity.MglgPost;
@Transactional
public interface MglgCommentRepository extends JpaRepository<MglgComment, MglgCommentId>{
	

	
	Optional<MglgComment> findById(MglgCommentId commentIds);
	
	Optional<MglgComment> findAllById(MglgPost mglgPost);
	
	@Modifying
	@Query(value="DELETE FROM T_MGLG_COMMENT WHERE COMMENT_ID = :commentId AND POST_ID = :postId",nativeQuery=true)
	void deleteComment(@Param("commentId") int commentId,@Param("postId") int postId);
	

	@Modifying
	@Query(value="UPDATE T_MGLG_COMMENT SET COMMENT_CONTENT = :#{mglgComment.commentContent}"
			+ " WHERE COMMENT_ID = :#{mglgComment.commentId}", nativeQuery=true)
	void updateComment(@Param("mglgComment") MglgComment mglgComment);
}

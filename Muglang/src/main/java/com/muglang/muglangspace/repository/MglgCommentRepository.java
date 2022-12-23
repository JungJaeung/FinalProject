package com.muglang.muglangspace.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgCommentId;
@Transactional
public interface MglgCommentRepository extends JpaRepository<MglgComment, MglgCommentId>{
	

	
	Optional<MglgComment> findById(MglgCommentId commentIds);
	
	
	@Modifying
	@Query(value="DELETE FROM T_MGLG_COMMENT WHERE COMMENT_ID = :commentId AND POST_ID = :postId",nativeQuery=true)
	void deleteComment(@Param("commentId") int commentId,@Param("postId") int postId);
	
	
}

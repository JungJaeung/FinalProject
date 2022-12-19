package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgCommentId;

@Transactional
public interface MglgCommentRepository extends JpaRepository<MglgComment, MglgCommentId>{
	
	MglgComment findByCommentId(@Param("commentId") int commentId);


}

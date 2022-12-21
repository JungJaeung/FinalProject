package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgPost;

@Transactional
public interface MglgPostRepository extends JpaRepository<MglgPost, Integer>{
	
	public MglgPost findByPostId(@Param("postId") int postId);

	@Modifying
	@Query(value="UPDATE T_MGLG_POST SET POST_CONTENT = :#{#mglgPost.postContent} WHERE POST_ID = :#{#mglgPost.postId}", nativeQuery=true)
	public void updateMglgPost(@Param("mglgPost") MglgPost mglgPost);
	
}

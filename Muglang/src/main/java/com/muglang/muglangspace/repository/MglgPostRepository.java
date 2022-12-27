package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	//내용 검색
	Page<MglgPost> findByPostContentContainingOrderByPostDateDesc(String searchKeyword, Pageable pageable);
	
	//식당 검색
	Page<MglgPost> findByRestNmContainingOrderByPostDateDesc(String searchKeyword, Pageable pageable);
	
	//해시태그 검색
	Page<MglgPost> findByHashTag1OrHashTag2OrHashTag3OrHashTag4OrHashTag5ContainingOrderByPostDateDesc(
			String searchKeyword1, 
			String searchKeyword2, 
			String searchKeyword3,
			String searchKeyword4, 
			String searchKeyword5, 
			Pageable pageable
			);
	
 	//포스트 갯수 세기
 	@Query(value="SELECT COUNT(*) AS postCount FROM T_MGLG_POST WHERE USER_ID = :userId", nativeQuery=true)
	int postCnt(@Param("userId") int userId);

	//모두 검색

	Page<MglgPost> findByPostContentOrRestNmOrHashTag1OrHashTag2OrHashTag3OrHashTag4OrHashTag5OrSearchKeywordContainingOrderByPostDateDesc(
			String searchKeyword1,
			String searchKeyword2,
			String searchKeyword3,
			String searchKeyword4,
			String searchKeyword5,
			String searchKeyword6,
			String searchKeyword7,
			String searchKeyword8,
			Pageable pageable
			);
}

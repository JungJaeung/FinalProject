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
	
	//추후 논의 후 검색 추상메소드를 통일하거나 폐기 하겠음
	//일단은 모두 검색을 기반으로 구현 2022/12/24
	//내용 검색
	Page<MglgPost> findByPostContentContaining(String searchKeyword, Pageable pageable);
	
	//식당 검색
	Page<MglgPost> findByRestNmContaining(String searchKeyword, Pageable pageable);
	
	//해시태그 검색
	Page<MglgPost> findByHashTag1Containing(String searchKeyword, Pageable pageable);
	Page<MglgPost> findByHashTag2Containing(String searchKeyword, Pageable pageable);
	Page<MglgPost> findByHashTag3Containing(String searchKeyword, Pageable pageable);
	Page<MglgPost> findByHashTag4Containing(String searchKeyword, Pageable pageable);
	Page<MglgPost> findByHashTag5Containing(String searchKeyword, Pageable pageable);
	
	//모두 검색
	Page<MglgPost> findByEntireSearchKeywordContaining(
			String searchKeyword1,
			String searchKeyword2,
			String searchKeyword3,
			String searchKeyword4,
			String searchKeyword5,
			String searchKeyword6,
			String searchKeyword7,
			Pageable pageable
			);
}

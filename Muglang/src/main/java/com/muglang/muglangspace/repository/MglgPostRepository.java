package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgPost;

@Transactional
public interface MglgPostRepository extends JpaRepository<MglgPost, Integer>{
	
	public MglgPost findByPostId(@Param("postId") int postId);

	@Query(value = "SELECT D.*\r\n"
			+ "	 , IFNULL(E.POST_LIKE, 'N') AS POST_LIKE\r\n"
			+ "	FROM (\r\n"
			+ "			SELECT A.*, IFNULL(C.LIKE_CNT, 0) AS LIKE_CNT \r\n"
			+ "				FROM T_MGLG_POST A \r\n"
			+ "				LEFT OUTER JOIN (\r\n"
			+ "									SELECT COUNT(B.POST_ID) AS LIKE_CNT\r\n"
			+ "										  , B.POST_ID\r\n"
			+ "										FROM T_MGLG_POST_LIKES B\r\n"
			+ "										GROUP BY B.POST_ID\r\n"
			+ "								) C\r\n"
			+ "			  ON A.POST_ID = C.POST_ID\r\n"
			+ "		  ) D\r\n"
			+ "	 LEFT OUTER JOIN (\r\n"
			+ "						SELECT F.POST_ID, 'Y' AS POST_LIKE \r\n"
			+ "							FROM T_MGLG_POST_LIKES F\r\n"
			+ "							WHERE F.USER_ID = :userId \r\n"
			+ "					 ) E\r\n"
			+ "	 ON D.POST_ID = E.POST_ID\r\n"
			+ "     ORDER BY D.POST_ID DESC",
			countQuery = " SELECT COUNT(*) FROM (SELECT * FROM T_MGLG_POST) D", nativeQuery = true)
	Page<CamelHashMap> getPagePostList(Pageable pageable, @Param("userId") int userId);

	
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
	

	//
	///개인 작성글 조회
 	 @Query(value="SELECT * FROM T_MGLG_POST WHERE USER_ID = :userId", nativeQuery=true)
	 Page<MglgPost> findByUserId(@Param("userId") int userId, Pageable pageable);
   
   //게시글 최신순 페이징 처리
	 Page<MglgPost> findAllByOrderByPostIdDesc(Pageable pageable);
	 
	//팔로잉 한 사람 포스팅만 가져옴
	 @Query(
					value = 
					"SELECT A.* FROM T_MGLG_POST A WHERE A.USER_ID IN "
				+ "(SELECT B.FOLLOWER_ID FROM T_MGLG_USER_RELATION B WHERE B.USER_ID = :userId)                 "
				,	countQuery = "SELECT COUNT(*) FROM ("
						+ "SELECT * FROM T_MGLG_POST B WHERE B.USER_ID IN "
						+ "(SELECT C.FOLLOWER_ID FROM T_MGLG_USER_RELATION C WHERE C.USER_ID = :userId) ) A ",
				nativeQuery = true)
	 Page<MglgPost> getFollowerPost(@Param("userId") int userId, Pageable pageable);
	 
	//좋아요 선택
	@Modifying
	@Query(value = "INSERT INTO T_MGLG_POST_LIKES VALUES(:postId, :userId, NOW())", nativeQuery = true)
	void likeUp(@Param("userId") int usreId, @Param("postId") int postId);
	
	//좋아요 해제
	@Modifying
	@Query(value = "DELETE FROM T_MGLG_POST_LIKES WHERE USER_ID = :userId AND POST_ID = :postId", nativeQuery = true)
	void likeDown(@Param("userId") int usreId, @Param("postId") int postId);
	
	@Query(value = "SELECT IFNULL(COUNT(A.POST_ID), 0) AS LIKE_CNT FROM T_MGLG_POST_LIKES A "
			+ "WHERE A.POST_ID = :postId", nativeQuery = true)
	int boardLikeCnt(@Param("postId") int postId);
}

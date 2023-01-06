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

	//좋아요 버튼 조작을 조회하는 쿼리.
	@Query(value = "SELECT D.*\r\n"
			+ "    , IFNULL(E.POST_LIKE, 'N') AS POST_LIKE\r\n"
			+ "   FROM (\r\n"
			+ "         SELECT A.*, IFNULL(C.LIKE_CNT, 0) AS LIKE_CNT \r\n"
			+ "            FROM (\r\n"
			+ "               SELECT I.*, IFNULL(L.RESTAURANT, 'N') AS RESTAURANT\r\n"
			+ "                  FROM (\r\n"
			+ "                       SELECT G.*\r\n"
			+ "                           , J.USER_NAME\r\n"
			+ "                         FROM T_MGLG_POST G\r\n"
			+ "                           , T_MGLG_USER J\r\n"
			+ "                         WHERE G.USER_ID = J.USER_ID\r\n"
			+ "                     ) I\r\n"
			+ "                  LEFT OUTER JOIN (\r\n"
			+ "									SELECT K.POST_ID, 'Y' AS RESTAURANT\r\n"
			+ "                                    FROM T_MGLG_RESTAURANT K\r\n"
			+ "                                    ) L\r\n"
			+ "				  ON I.POST_ID = L.POST_ID\r\n"
			+ "                ) A\r\n"
			+ "            LEFT OUTER JOIN (\r\n"
			+ "                           SELECT COUNT(B.POST_ID) AS LIKE_CNT\r\n"
			+ "                                , B.POST_ID\r\n"
			+ "                              FROM T_MGLG_POST_LIKES B\r\n"
			+ "                              GROUP BY B.POST_ID\r\n"
			+ "                        ) C\r\n"
			+ "           ON A.POST_ID = C.POST_ID\r\n"
			+ "        ) D\r\n"
			+ "    LEFT OUTER JOIN (\r\n"
			+ "                  SELECT F.POST_ID, 'Y' AS POST_LIKE \r\n"
			+ "                     FROM T_MGLG_POST_LIKES F\r\n"
			+ "                     WHERE F.USER_ID = :userId\r\n"
			+ "                ) E\r\n"
			+ "    ON D.POST_ID = E.POST_ID\r\n"
			+ "     ORDER BY D.POST_ID DESC",
			countQuery = " SELECT COUNT(*) FROM (SELECT * FROM T_MGLG_POST) D", nativeQuery = true)
	Page<CamelHashMap> getPagePostList(Pageable pageable, @Param("userId") int userId);

	//포스팅 내용을 수정하는 부분. 파일 정리는 따로 하고 나머지 글의 내용을 고치고, 반영.
	@Modifying
	@Query(value="UPDATE T_MGLG_POST SET POST_CONTENT = :#{#mglgPost.postContent} "
			+ " WHERE POST_ID = :#{#mglgPost.postId}", nativeQuery=true)
	public void updateMglgPost(@Param("mglgPost") MglgPost mglgPost);
	
   // 내용을 기준으로 검색
   @Query(value="SELECT POST_ID, HASH_TAG1, HASH_TAG2, HASH_TAG3, HASH_TAG4, HASH_TAG5,\r\n"
   			+ "POST_CONTENT, POST_DATE, POST_RATING, REST_NM, REST_RATING, P.USER_ID AS PUID, U.USER_ID AS UUID, U.USER_NICK\r\n"
            + "FROM T_MGLG_POST P\r\n"
            + "   INNER JOIN T_MGLG_USER U\r\n"
            + "   ON P.USER_ID = U.USER_ID \r\n"
            + "WHERE P.POST_CONTENT LIKE CONCAT('%', :#{#searchKeyword}, '%')\r\n"
            + "ORDER BY P.POST_DATE DESC",
		   countQuery = " SELECT COUNT(*) FROM T_MGLG_POST P WHERE P.POST_CONTENT LIKE '%:#{#searchKeyword}%'", nativeQuery=true)
	Page<CamelHashMap> searchByPost(@Param("searchKeyword") String searchKeyword, Pageable pageable);
	
   	// 닉네임을 기준으로 검색
    @Query(value="SELECT POST_ID, HASH_TAG1, HASH_TAG2, HASH_TAG3, HASH_TAG4, HASH_TAG5,\r\n"
  		   + "POST_CONTENT, POST_DATE, POST_RATING, REST_NM, REST_RATING, P.USER_ID AS PUID, U.USER_ID AS UUID, U.USER_NICK\r\n"
           + "FROM T_MGLG_POST P\r\n"
           + "   INNER JOIN T_MGLG_USER U\r\n"
           + "   ON P.USER_ID = U.USER_ID \r\n"
           + "WHERE U.USER_NICK LIKE CONCAT('%', :#{#searchKeyword}, '%')\r\n"
           + "ORDER BY P.POST_DATE DESC",
		   countQuery = " SELECT COUNT(*) FROM T_MGLG_POST P WHERE P.POST_CONTENT LIKE '%:#{#searchKeyword}%'", nativeQuery=true)
	Page<CamelHashMap> searchByNick(@Param("searchKeyword") String searchKeyword, Pageable pageable);
	
	// 해시태그를 기준으로 검색
    @Query(value="SELECT POST_ID, HASH_TAG1, HASH_TAG2, HASH_TAG3, HASH_TAG4, HASH_TAG5,\r\n"
 		  + "POST_CONTENT, POST_DATE, POST_RATING, REST_NM, REST_RATING, P.USER_ID AS PUID, U.USER_ID AS UUID, U.USER_NICK\r\n"
          + "FROM T_MGLG_POST P\r\n"
          + "   INNER JOIN T_MGLG_USER U\r\n"
          + "   ON P.USER_ID = U.USER_ID \r\n"
          + "WHERE\r\n"
          + "	P.HASH_TAG1 LIKE CONCAT('%', :#{#searchKeyword}, '%') OR\r\n"
          + "	P.HASH_TAG2 LIKE CONCAT('%', :#{#searchKeyword}, '%') OR\r\n"
          + "	P.HASH_TAG3 LIKE CONCAT('%', :#{#searchKeyword}, '%') OR\r\n"
          + "	P.HASH_TAG4 LIKE CONCAT('%', :#{#searchKeyword}, '%') OR\r\n"
          + "	P.HASH_TAG5 LIKE CONCAT('%', :#{#searchKeyword}, '%')\r\n"
          + "ORDER BY P.POST_DATE DESC",
		   countQuery = " SELECT COUNT(*) FROM T_MGLG_POST P WHERE P.POST_CONTENT LIKE '%:#{#searchKeyword}%'", nativeQuery=true)
	Page<CamelHashMap> searchByHashtag(@Param("searchKeyword") String searchKeyword, Pageable pageable);
	
 	//포스트 갯수 세기
 	@Query(value="SELECT COUNT(*) AS postCount FROM T_MGLG_POST WHERE USER_ID = :userId", nativeQuery=true)
	int postCnt(@Param("userId") int userId);

	//모두 검색

	Page<MglgPost> findByPostContentOrRestNmOrHashTag1OrHashTag2OrHashTag3OrHashTag4OrHashTag5OrSearchKeywordContainingOrderByPostDateDesc(
			@Param("searchKeyword") String searchKeyword1,
			@Param("searchKeyword") String searchKeyword2,
			@Param("searchKeyword") String searchKeyword3,
			@Param("searchKeyword") String searchKeyword4,
			@Param("searchKeyword") String searchKeyword5,
			@Param("searchKeyword") String searchKeyword6,
			@Param("searchKeyword") String searchKeyword7,
			@Param("searchKeyword") String searchKeyword8,
			Pageable pageable
			);
	

	//
	///개인 작성글 조회
 	 @Query(value="SELECT * FROM T_MGLG_POST WHERE USER_ID = :userId", nativeQuery=true)
	 Page<MglgPost> findByUserId(@Param("userId") int userId, Pageable pageable);
   
   //게시글 최신순 페이징 처리
	 Page<MglgPost> findAllByOrderByPostIdDesc(Pageable pageable);
	 
	//팔로우 한 사람 포스팅만 가져옴
	 @Query(
					value = 
					"SELECT A.* FROM T_MGLG_POST A WHERE A.USER_ID IN "
				+ "(SELECT B.FOLLOWER_ID FROM T_MGLG_USER_RELATION B WHERE B.USER_ID = :userId) ORDER BY A.POST_DATE DESC "
				,	countQuery = "SELECT COUNT(*) FROM ("
						+ "SELECT * FROM T_MGLG_POST B WHERE B.USER_ID IN "
						+ "(SELECT C.FOLLOWER_ID FROM T_MGLG_USER_RELATION C WHERE C.USER_ID = :userId) ) A ",
				nativeQuery = true)
	 Page<MglgPost> getFollowerPost(@Param("userId") int userId, Pageable pageable);
	 
	//팔로잉 한 사람 포스팅만 가져옴
		 @Query(
						value = 
						"SELECT A.* FROM T_MGLG_POST A WHERE A.USER_ID IN "
					+ "(SELECT B.USER_ID FROM T_MGLG_USER_RELATION B WHERE B.FOLLOWER_ID = :userId) ORDER BY A.POST_DATE DESC "
					,	countQuery = "SELECT COUNT(*) FROM ("
							+ "SELECT * FROM T_MGLG_POST B WHERE B.USER_ID IN "
							+ "(SELECT C.USER_ID FROM T_MGLG_USER_RELATION C WHERE C.FOLLOWER_ID = :userId) ) A ",
					nativeQuery = true)
		 Page<MglgPost> getFollowingPost(@Param("userId") int userId, Pageable pageable);
	 
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
	
	
	//포스트 신고 로직
	@Modifying
	@Query(value = ""
			+ "INSERT INTO T_MGLG_REPORT VALUES("
			+ "(SELECT IFNULL(MAX(A.REPORT_ID), 0) + 1 FROM T_MGLG_REPORT A),2,0,:postId,NOW(),:userId,0"
			+ ")", nativeQuery = true)
	void reportPost(@Param("postId") int postId,@Param("userId") int userId);		
	
	//포스트 다중 신고 방지 로직
	@Query(value = ""
			+ "SELECT COUNT(*) FROM T_MGLG_REPORT "
			+ "WHERE SOURCE_USER_ID= :userId AND POST_Id = :postId", nativeQuery = true)
	int reportPostCheck(@Param("postId") int postId,@Param("userId") int userId);	
	//자기자신의 포스트 신고 방지 로직 
	@Query(value = ""
			+ "SELECT COUNT(*) FROM T_MGLG_POST WHERE POST_ID =:postId AND USER_ID=:userId"
			+ "", nativeQuery = true)
	int reportPostSelfCheck(@Param("postId") int postId,@Param("userId") int userId);	
	
	
}

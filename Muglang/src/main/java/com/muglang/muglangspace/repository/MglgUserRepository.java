package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgUser;

@Transactional

public interface MglgUserRepository extends JpaRepository<MglgUser, Integer>{

	//이름으로 검색
	Page<MglgUser> findByUserNameContaining(String searchKeyword, Pageable pageable);
	//이메일로검색
	Page<MglgUser> findByEmailContaining(String searchKeyword, Pageable pageable);
	//디폴트 검색
	Page<MglgUser> findByEmailContainingOrUserNameContaining(String searchKeyword1, String searchKeyword2, Pageable pageable);

	MglgUser findByUserNameContaining(MglgUser user);
	
	@Modifying //데이터의 변경이 일어나는 @Query을 사용할 때는 @Modifying을 붙여준다.
	@Query(value="UPDATE T_MGLG_USER SET USER_BAN_YN = :userBanYn WHERE USER_ID = :userId", nativeQuery=true)
	void uptUserBan(@Param("userBanYn") String userBanYn, @Param("userId") int userId);
	
	MglgUser findById(@Param("userId") int userId);

	//Sns계정이 가입 되어있는지 여부를 판단하는 간단한 검색쿼리
	@Query(value="SELECT * FROM T_MGLG_USER WHERE USER_SNS_ID = :userSnsId", nativeQuery=true)
	MglgUser findByUserSnsId(@Param("userSnsId") String userSnsId);
	
//--------------------어드민 관련///	
	@Query(value="SELECT A.*"
			+ "	 , IFNULL(B.REPORT_CNT, 0) AS REPORT_CNT"
			+ "	FROM T_MGLG_USER A"
			+ "    LEFT OUTER JOIN ("
			+ "						SELECT C.TARGET_USER_ID"
			+ "							 , COUNT(C.TARGET_USER_ID) AS REPORT_CNT"
			+ "                            FROM T_MGLG_REPORT C"
			+ "                            GROUP BY C.TARGET_USER_ID"
			+ "					 ) B"
			+ "	ON A.USER_ID = B.TARGET_USER_ID",
			countQuery = "SELECT COUNT(*) FROM ("
					+ " SELECT A.*"
					+ "	 , IFNULL(B.REPORT_CNT, 0) AS REPORT_CNT"
					+ "	FROM T_MGLG_USER A"
							+ "    LEFT OUTER JOIN ("
							+ "						SELECT C.TARGET_USER_ID"
							+ "							 , COUNT(C.TARGET_USER_ID) AS REPORT_CNT"
							+ "                            FROM T_MGLG_REPORT C"
							+ "                            GROUP BY C.TARGET_USER_ID"
							+ "					 ) B"
							+ "	ON A.USER_ID = B.TARGET_USER_ID) D",
			nativeQuery = true)
		Page<CamelHashMap> searchDefault(Pageable pageable);
	
		//이름으로 검색
	@Query(value="SELECT A.*"
			+ "	 , IFNULL(B.REPORT_CNT, 0) AS REPORT_CNT"
			+ "	FROM (T_MGLG_USER) A"
			+ "    LEFT OUTER JOIN ("
			+ "						SELECT C.TARGET_USER_ID"
			+ "							 , COUNT(C.TARGET_USER_ID) AS REPORT_CNT"
			+ "                            FROM (T_MGLG_REPORT) C"
			+ "                            GROUP BY C.TARGET_USER_ID"
			+ "					 ) B"
			+ "	ON A.USER_ID = B.TARGET_USER_ID"
			+ " WHERE USER_NAME LIKE CONCAT('%',:searchKeyword,'%')", nativeQuery=true)
		Page<CamelHashMap> searchEmail(@Param("searchKeyword") String searchKeyword, Pageable pageable);
		//이메일로검색
	 @Query(value="SELECT A.*"
			+ "	 , IFNULL(B.REPORT_CNT, 0) AS REPORT_CNT"
			+ "	FROM (T_MGLG_USER) A"
			+ "    LEFT OUTER JOIN ("
			+ "						SELECT C.TARGET_USER_ID"
			+ "							 , COUNT(C.TARGET_USER_ID) AS REPORT_CNT"
			+ "                            FROM (T_MGLG_REPORT) C"
			+ "                            GROUP BY C.TARGET_USER_ID"
			+ "					 ) B"
			+ "	ON A.USER_ID = B.TARGET_USER_ID"
			+ " WHERE EMAIL LIKE CONCAT('%',:searchKeyword,'%')", nativeQuery=true)
		Page<CamelHashMap> searchName(@Param("searchKeyword") String searchKeyword, Pageable pageable);
		//둘다 검색
	 	@Query(value="SELECT A.*"
				+ "	 , IFNULL(B.REPORT_CNT, 0) AS REPORT_CNT"
				+ "	FROM (T_MGLG_USER) A"
				+ "    LEFT OUTER JOIN ("
				+ "						SELECT C.TARGET_USER_ID"
				+ "							 , COUNT(C.TARGET_USER_ID) AS REPORT_CNT"
				+ "                            (FROM T_MGLG_REPORT) C"
				+ "                            GROUP BY C.TARGET_USER_ID"
				+ "					 ) B"
				+ "	ON A.USER_ID = B.TARGET_USER_ID"
				+ " WHERE USER_NAME LIKE CONCAT('%',:searchKeyword1,'%') OR EMAIL LIKE CONCAT('%',:searchKeyword2,'%')", nativeQuery=true)
		Page<CamelHashMap> searchAll(@Param("searchKeyword1") String searchKeyword1,@Param("searchKeyword2") String searchKeyword2, Pageable pageable);
//--------------------어드민 관련 끝///	
	 	
	 	//팔로워 리스트
		@Query(value="SELECT A.* FROM T_MGLG_USER A WHERE USER_ID IN (SELECT FOLLOWER_ID FROM t_mglg_user_relation WHERE USER_ID= :userId)", nativeQuery=true)
		Page<MglgUser> followList(@Param("userId") int userId, Pageable pageable);

		//질문하기---쿼리문 왜 틀림
		@Query(value="SELECT A.* FROM T_MGLG_USER A WHERE USER_ID IN (SELECT FOLLOWER_ID FROM t_mglg_user_relation WHERE USER_ID= :userId)"
				+ "	   AND USER_NAME = :searchKeyword", nativeQuery=true)
	Page<MglgUser> searchFollowList( @Param("searchKeyword") String searchKeyword, @Param("userId") int userId, Pageable pageable);
	 	
}

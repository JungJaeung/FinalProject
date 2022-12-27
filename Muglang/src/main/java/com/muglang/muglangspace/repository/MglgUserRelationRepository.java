package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgUser;

@Transactional
public interface MglgUserRelationRepository extends JpaRepository<MglgUser, Integer>{

	@Query(value="SELECT COUNT(*) AS followCount FROM T_MGLG_USER_RELATION WHERE USER_ID = :userId", nativeQuery=true)
	public int cntFollow(@Param("userId") int userId);
	
	@Query(value="SELECT COUNT(*) AS followingCount FROM T_MGLG_USER_RELATION WHERE FOLLOWER_ID = :userId", nativeQuery=true)
	public int cntFollowing(@Param("userId") int userId);
	
	
	@Query(value=
			"	SELECT A.*"
			+ "	FROM T_MGLG_USER A"
			+ "	   , T_MGLG_USER_RELATION B"
			+ "    WHERE B.USER_ID = :userId"
			+ "	  AND B.FOLLOWER_ID NOT IN (\r\n"
			+ "									SELECT C.USER_ID\r\n"
			+ "										FROM T_MGLG_USER_RELATION C\r\n"
			+ "                                        WHERE C.FOLLOWER_ID = :userId\r\n"
			+ "							   )\r\n"
			+ "	  AND A.USER_ID = B.FOLLOWER_ID", nativeQuery=true)
	public Page<MglgUser> requestFollowList(@Param("userId") int userId, Pageable pageable) ;

	///맞팔 
	@Modifying
	@Query(value="INSERT INTO T_MGLG_USER_RELATION "
			+ "	  VALUES(now(), :userId, :followId)" , nativeQuery=true)
	public void followUser(@Param("followId") int followId,@Param("userId") int userId);

	
}

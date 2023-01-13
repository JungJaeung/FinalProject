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
public interface MglgUserRelationRepository extends JpaRepository<MglgUser, Integer> {

	@Query(value = "SELECT COUNT(*) AS followCount FROM T_MGLG_USER_RELATION WHERE USER_ID = :userId", nativeQuery = true)
	public int cntFollow(@Param("userId") int userId);

	@Query(value = "SELECT COUNT(*) AS followingCount FROM T_MGLG_USER_RELATION WHERE FOLLOWER_ID = :userId", nativeQuery = true)
	public int cntFollowing(@Param("userId") int userId);

	@Query(
		value = "	                  "
		+ "			SELECT A.*\r\n" 
		+ "               FROM T_MGLG_USER A\r\n"
		+ "               WHERE A.USER_ID \r\n" 
		+ "               NOT IN \r\n"
		+ "               (SELECT B.USER_ID FROM T_MGLG_USER_RELATION B WHERE B.FOLLOWER_ID = :userId)\r\n"
		+ "               AND A.USER_ID IN \r\n"
		+ "				  (SELECT C.FOLLOWER_ID FROM T_MGLG_USER_RELATION C WHERE C.USER_ID = :userId)",
		countQuery = "SELECT COUNT(*) FROM ("
		+ "			SELECT A.*\r\n" 
		+ "               FROM T_MGLG_USER A\r\n"
		+ "               WHERE A.USER_ID \r\n" 
		+ "               NOT IN \r\n"
		+ "               (SELECT B.USER_ID FROM T_MGLG_USER_RELATION B WHERE B.FOLLOWER_ID = :userId)\r\n"
		+ "               AND A.USER_ID IN \r\n"
		+ "				  (SELECT C.FOLLOWER_ID FROM T_MGLG_USER_RELATION C WHERE C.USER_ID = :userId)"
		+ " 		) F",
		nativeQuery = true)
	public Page<CamelHashMap> requestFollowList(@Param("userId") int userId, Pageable pageable);

	/// 맞팔
	@Modifying
	@Query(value = "INSERT INTO T_MGLG_USER_RELATION " + "	  VALUES(now(), :userId, :followId)", nativeQuery = true)
	public void followUser(@Param("followId") int followId, @Param("userId") int userId);
	/// 언팔
	@Modifying
	@Query(value = "DELETE FROM T_MGLG_USER_RELATION WHERE USER_ID= :userId AND FOLLOWER_ID= :loginUser", nativeQuery = true)
	public void unFollowUser(@Param("userId") int userId, @Param("loginUser") int loginUser);

	@Query(value = "SELECT COUNT(*) FROM T_MGLG_USER_RELATION WHERE USER_ID= :userId AND FOLLOWER_ID= :loginUserId", nativeQuery = true)	
	public int followingOrNot(@Param("userId")int userId,@Param("loginUserId") int loginUserId); 
}

package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgUser;

@Transactional
public interface MglgUserRelationRepository extends JpaRepository<MglgUser, Integer>{

	@Query(value="SELECT COUNT(*) AS followCount FROM T_MGLG_USER_RELATION WHERE USER_ID = :userId", nativeQuery=true)
	public int cntFollow(@Param("userId") int userId);
	
	@Query(value="SELECT COUNT(*) AS followingCount FROM T_MGLG_USER_RELATION WHERE FOLLOWER_ID = :userId", nativeQuery=true)
	public int cntFollowing(@Param("userId") int userId);
}

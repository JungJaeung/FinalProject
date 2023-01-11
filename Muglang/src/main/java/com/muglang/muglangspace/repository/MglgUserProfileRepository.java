package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserProfile;

@Transactional
public interface MglgUserProfileRepository extends JpaRepository<MglgUserProfile, Integer>{
	
	@Modifying
	@Query(value = "INSERT INTO t_mglg_user_profile "
			+ "VALUES(:userId,'png','defaultImg.png',"
			+ ":attachPath,'defaultImg.png')",
	nativeQuery = true)
	public int insertDefault(@Param("userId") int userId,@Param("attachPath") String attachPath);

	MglgUserProfile findByUserId(@Param("userId") int userId);

}

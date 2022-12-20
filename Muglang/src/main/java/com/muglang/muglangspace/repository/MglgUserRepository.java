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
	
	MglgUser findByUserId(@Param("userId") int userId);
}

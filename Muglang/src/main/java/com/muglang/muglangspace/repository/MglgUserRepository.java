package com.muglang.muglangspace.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgUser;

public interface MglgUserRepository extends JpaRepository<MglgUser, String>{
	//이름으로 검색
	Page<MglgUser> findByUserNameContaining(String searchKeyword, Pageable pageable);
	//이메일로검색
	Page<MglgUser> findByEmailContaining(String searchKeyword, Pageable pageable);
	//디폴트 검색
	Page<MglgUser> findByEmailContainingOrUserNameContaining(String searchKeyword1, String searchKeyword2, Pageable pageable);

	
}

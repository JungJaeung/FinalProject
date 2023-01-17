package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgHotKeywords;

@Transactional
public interface MglgHotKeywordsRepository extends JpaRepository<MglgHotKeywords, String> {
	
	@Modifying
	@Query(value=""
			+ " INSERT INTO T_MGLG_HOT_KEYWORDS\r\n"
			+ " (HOT_KEYWORD, CONFIRM_YN, NUM_OF_TIME)\r\n"
			+ " VALUES(:searchKeyword, 'N', 1)\r\n"
			+ " ON DUPLICATE KEY UPDATE NUM_OF_TIME = NUM_OF_TIME + 1", nativeQuery=true)
	void insrtOrUpdte(@Param("searchKeyword") String searchKeyword);
	
}

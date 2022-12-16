package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgPost;

@Transactional
public interface MglgPostRepository extends JpaRepository<MglgPost, Integer>{
	
}

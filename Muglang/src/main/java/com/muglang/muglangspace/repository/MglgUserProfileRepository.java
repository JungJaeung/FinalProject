package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgUserProfile;

@Transactional
public interface MglgUserProfileRepository extends JpaRepository<MglgUserProfile, Integer>{

}

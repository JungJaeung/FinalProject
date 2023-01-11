package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserProfile;
import com.muglang.muglangspace.entity.MglgUserProfileId;

@Transactional
public interface MglgUserProfileRepository extends JpaRepository<MglgUserProfile, MglgUserProfileId>{

}

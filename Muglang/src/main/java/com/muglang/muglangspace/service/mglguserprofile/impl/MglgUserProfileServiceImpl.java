package com.muglang.muglangspace.service.mglguserprofile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.entity.MglgUserProfile;
import com.muglang.muglangspace.repository.MglgUserProfileRepository;
import com.muglang.muglangspace.service.mglguserprofile.MglgUserProfileService;

@Service
public class MglgUserProfileServiceImpl implements MglgUserProfileService{
	@Autowired
	MglgUserProfileRepository mglgUserProfileRepository;

	@Override
	public void insertDefault(int userId,String attachPath) {
		mglgUserProfileRepository.insertDefault(userId,attachPath);
	}

	@Override
	public MglgUserProfile getUserImg(int userId) {
		return mglgUserProfileRepository.findByUserId(userId);

	}
	
	
}

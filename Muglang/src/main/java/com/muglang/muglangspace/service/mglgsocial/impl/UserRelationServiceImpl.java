package com.muglang.muglangspace.service.mglgsocial.impl;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserRelation;
import com.muglang.muglangspace.repository.MglgUserRelationRepository;
import com.muglang.muglangspace.repository.MglgUserRepository;
import com.muglang.muglangspace.service.mglgsocial.UserRelationService;

@Service
public class UserRelationServiceImpl implements UserRelationService {
	@Autowired
	private MglgUserRelationRepository mglgUserRelationRepository;
	@Autowired
	private MglgUserRepository mglgUserRepository;
	@Override
	public int cntFollow(MglgUserRelation follow) {
		 MglgUser user = follow.getMglgUser();
		 int userId = user.getUserId();
		return mglgUserRelationRepository.cntFollow(userId);
	}
	@Override
	public int cntFollowing(MglgUserRelation following) {
		 MglgUser user = following.getMglgUser();
		 int userId = user.getUserId();
		 return mglgUserRelationRepository.cntFollowing(userId);
	}
	
	@Override
	public Page<MglgUser> followList(MglgUser user, Pageable pageable) {
		int userId = user.getUserId();
		String searchKeyword = user.getSearchKeyword();
		if(user.getSearchKeyword() != null && !user.getSearchKeyword().equals("")) {
			return mglgUserRepository.searchFollowList(searchKeyword,userId, pageable);
			
		}else {
			return mglgUserRepository.followList(userId,pageable);
		}
		

	}
	@Override
	public Page<MglgUser> followingList(MglgUser user, Pageable pageable) {
		int userId = user.getUserId();
		String searchKeyword = user.getSearchKeyword();
		if(user.getSearchKeyword() != null && !user.getSearchKeyword().equals("")) {
			return mglgUserRepository.searchFollowingList(searchKeyword,userId, pageable);
			
		}else {
			return mglgUserRepository.followingList(userId,pageable);
		}
		

	}
	@Override
	public Page<MglgUser> requestFollowList(int userId, Pageable pageable) {
		return mglgUserRelationRepository.requestFollowList(userId,pageable);
	}
	@Override
	public void followUser(int followId, int userId) {
		System.out.println("팔로우 아이디 =="+followId +"....."+"유저아이디=="+ userId+".....데이트:");
		
		mglgUserRelationRepository.followUser(followId, userId);
	}
	


}

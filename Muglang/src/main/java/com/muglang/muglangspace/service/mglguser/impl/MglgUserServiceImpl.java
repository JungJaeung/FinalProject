package com.muglang.muglangspace.service.mglguser.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.mapper.MglgUserMapper;
import com.muglang.muglangspace.repository.MglgUserRepository;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@Service
public class MglgUserServiceImpl implements MglgUserService{
	@Autowired
	private MglgUserRepository mglgUserRepository;
	
	@Autowired
	private MglgUserMapper mglgUserMapper;

	@Override
	public Page<MglgUser> getUserList(MglgUser user, Pageable pageable) {
			if(user.getSearchKeyword() != null && !user.getSearchKeyword().equals("")) {
				if(user.getSearchCondition().equals("ALL")) {
					return mglgUserRepository.findByEmailContainingOrUserNameContaining
							(user.getSearchKeyword(), 
							 user.getSearchKeyword(), 
							 pageable);
				} else if(user.getSearchCondition().equals("이름")) {
					return mglgUserRepository.findByUserNameContaining(user.getSearchKeyword(), pageable);
				} else if(user.getSearchCondition().equals("이메일")) {
					return mglgUserRepository.findByEmailContaining(user.getSearchKeyword(), pageable);
				} else {
					return mglgUserRepository.findAll(pageable);
				}
			}else {
				return mglgUserRepository.findAll(pageable);
			}
	}
	
	///// 검색 + 유저 카운트
	@Override
	public Page<CamelHashMap> getAdminUserList(MglgUser user, Pageable pageable) {
			if(user.getSearchKeyword() != null && !user.getSearchKeyword().equals("")) {
				if(user.getSearchCondition().equals("ALL")) {
					return mglgUserRepository.searchAll(user.getSearchKeyword(),
							user.getSearchKeyword(), pageable);		
				} else if(user.getSearchCondition().equals("이름")) {
					return mglgUserRepository.searchName(user.getSearchKeyword(), pageable);
				} else{
					return mglgUserRepository.searchEmail(user.getSearchKeyword(), pageable);
				}
			}else {
				return mglgUserRepository.searchDefault(pageable);
			}
	}
	
	@Override
	public MglgUser loginUser(MglgUser mglgUser) {
		return mglgUserRepository.findById(mglgUser.getUserId());
	}
	
	@Override
	public MglgUser socialLoginUser(MglgUser mglgUser) {
		// TODO Auto-generated method stub
		return mglgUserRepository.findByUserSnsId(mglgUser.getUserSnsId());
	}	
	
	//소셜로그인 최종 완료
	@Override
	public void socialLoginProcess(MglgUser mglgUser) {
		mglgUserRepository.save(mglgUser);
	}

	@Override
	public List<MglgUser> getNoUserList() {
		// TODO Auto-generated method stub
		return null;
	}

}

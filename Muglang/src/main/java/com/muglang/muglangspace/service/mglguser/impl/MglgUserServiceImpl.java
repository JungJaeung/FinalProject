package com.muglang.muglangspace.service.mglguser.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.repository.MglgUserRepository;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@Service
public class MglgUserServiceImpl implements MglgUserService{
	@Autowired
	MglgUserRepository mglgUserRepository;

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
	
	
}

package com.muglang.muglangspace.service.mglguser.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.repository.MglgUserRepository;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@Service
public class MglgUserServiceImpl implements MglgUserService{
	@Autowired
	MglgUserRepository mglgUserRepository;
	
	
}

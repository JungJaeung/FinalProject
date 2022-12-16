package com.muglang.muglangspace.service.mglgadmin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.repository.MglgPostRepository;
import com.muglang.muglangspace.repository.MglgReportRepository;
import com.muglang.muglangspace.repository.MglgUserRepository;
import com.muglang.muglangspace.service.mglgadmin.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
		@Autowired
		private MglgReportRepository mglgReportRepository;
		@Autowired
		private MglgUserRepository mglgUserRepository;
		@Autowired
		private MglgPostRepository mglgPostRepository;
		
}

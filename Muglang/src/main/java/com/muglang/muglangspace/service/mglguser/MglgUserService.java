package com.muglang.muglangspace.service.mglguser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muglang.muglangspace.entity.MglgUser;

public interface MglgUserService {
	
	Page<MglgUser> getUserList(MglgUser user,Pageable pageable); 
	
	

}

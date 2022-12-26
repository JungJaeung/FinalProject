package com.muglang.muglangspace.service.mglguser;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgUser;

public interface MglgUserService {
	
	MglgUser findUser(int userId);
	
	Page<MglgUser> getUserList(MglgUser user,Pageable pageable);

	MglgUser loginUser(MglgUser user); 
	
	MglgUser socialLoginUser(MglgUser user);
	
	void socialLoginProcess(MglgUser user);
	
	Page<CamelHashMap> getAdminUserList(MglgUser user,Pageable pageable);

	List<MglgUser> getNoUserList();
	

}

package com.muglang.muglangspace.service.mglgadmin;

import com.muglang.muglangspace.entity.MglgUser;

public interface AdminService {

	MglgUser searchBan(MglgUser user);
	
	MglgUser uptUserBan(MglgUser user);
	
	
}

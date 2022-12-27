package com.muglang.muglangspace.service.mglgsocial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserRelation;

public interface UserRelationService {

	int cntFollow(MglgUserRelation follow);
	
	int cntFollowing(MglgUserRelation following);

	Page<MglgUser> followList(MglgUser user,Pageable pageable);
	Page<MglgUser> followingList(MglgUser user,Pageable pageable);

}

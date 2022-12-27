package com.muglang.muglangspace.service.mglgpost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserRelation;

public interface MglgPostService {
	public void insertPost(MglgPost mglgpost);
	
	public MglgPost updatePost(MglgPost mglgpost);
	
	public void deletePost(MglgPost mglgpost);
	
	public void likePost(MglgPost mglgpost);
	
	public Page<MglgPost> getPagePostList(Pageable pageable);
	
	public MglgPost getPost(MglgPost post);
	
	public int postCnt(MglgUserRelation relUser);

	
	//검색
	public Page<MglgPost> searchPostList(MglgPost mglgPost, Pageable pageable);
}

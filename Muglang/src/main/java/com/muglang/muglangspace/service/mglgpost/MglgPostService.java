package com.muglang.muglangspace.service.mglgpost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUserRelation;

public interface MglgPostService {
	public MglgPost insertPost(MglgPost mglgpost);

	public MglgPost updatePost(MglgPost mglgpost);

	public void deletePost(MglgPost mglgpost);

	public void likePost(MglgPost mglgpost);

	public Page<CamelHashMap> getPagePostList(Pageable pageable, int userId);

	public MglgPost getPost(MglgPost post);

	public int postCnt(MglgUserRelation relUser);

	// 개인 유저에 대한 포스팅 불러오기
	public Page<MglgPost> userPostList(int userId, Pageable pageable);

	// 검색
	public Page<MglgPost> searchPostList(MglgPost mglgPost, Pageable pageable);

	public Page<MglgPost> getFollowerPost(int userId, Pageable pageable);

	public int likeUp(int userId, int postId);

	public int likeDown(int userId, int postId);
	
	public String reportPost(int postId,int userId);
}

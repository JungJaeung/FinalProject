package com.muglang.muglangspace.service.mglgpost;

import java.util.List;

import com.muglang.muglangspace.entity.MglgPost;

public interface MglgPostService {
	public void insertPost(MglgPost mglgpost);
	
	public void updatePost(MglgPost mglgpost);
	
	public void deletePost(MglgPost mglgpost);
	
	public void likePost(MglgPost mglgpost);
	
	public List<MglgPost> getPostList();
	
	public int cntPost(int postId);
}

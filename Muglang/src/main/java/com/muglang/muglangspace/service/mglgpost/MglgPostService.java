package com.muglang.muglangspace.service.mglgpost;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muglang.muglangspace.entity.MglgPost;

public interface MglgPostService {
	public void insertPost(MglgPost mglgpost);
	
	public void updatePost(MglgPost mglgpost);
	
	public void deletePost(MglgPost mglgpost);
	
	public void likePost(MglgPost mglgpost);

	public Page<MglgPost> getPagePostList(Pageable pageable);
	
	public int cntPost(int postId);
	
	public MglgPost getPost(MglgPost post);

	
}

package com.muglang.muglangspace.service.mglgpost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.dto.MglgPostDTO;
import com.muglang.muglangspace.entity.CustomUserDetails;
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

	public Page<CamelHashMap> getFollowerPost(int userId, Pageable pageable);

	public Page<CamelHashMap> getFollowingPost(int userId, Pageable pageable);

	
	public int likeUp(int userId, int postId);

	public int likeDown(int userId, int postId);
	
	public String reportPost(int postId,int userId);
	
	// 포스트 내용을 기준으로 검색
	public Page<CamelHashMap> searchByPost(String searchKeyword, Pageable pageable);
	
	// 닉네임을 기준으로 검색
	public Page<CamelHashMap> searchByNick(String searchKeyword, Pageable pageable);
	
	// 해시태그를 기준으로 검색
	public Page<CamelHashMap> searchByHashtag(String searchKeyword, Pageable pageable);
	
	// 검색어 T_MGLG_HOT_KEYWORDS 테이블에 INSERT
	public void insertKeyword(String searchKeyword);
	
	// 인기 검색어를 SELECT
	public Page<CamelHashMap> getHotKeywords(Pageable pageable);
	
}

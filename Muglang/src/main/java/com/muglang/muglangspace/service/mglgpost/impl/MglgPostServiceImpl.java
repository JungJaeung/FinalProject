package com.muglang.muglangspace.service.mglgpost.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.entity.MglgUserRelation;
import com.muglang.muglangspace.repository.MglgPostFileRepository;
import com.muglang.muglangspace.repository.MglgPostRepository;
import com.muglang.muglangspace.service.mglgpost.MglgPostService;

@Service
public class MglgPostServiceImpl implements MglgPostService {
	@Autowired
	private MglgPostRepository mglgPostRepository;

	@Autowired
	private MglgPostFileRepository mglgPostFileRepository;

	// 포스팅 관련 서비스 제공
	@Override
	public MglgPost insertPost(MglgPost mglgPost) {
		// TODO Auto-generated method stub
		mglgPostRepository.save(mglgPost);

		mglgPostRepository.flush();

		return mglgPost;
	}

	@Override
	public MglgPost updatePost(MglgPost mglgPost) {
		// TODO Auto-generated method stub
		mglgPostRepository.updateMglgPost(mglgPost);

		mglgPostRepository.flush();

		return mglgPost;
	}

	@Override
	public void deletePost(MglgPost mglgpost) {
		// TODO Auto-generated method stub
		mglgPostRepository.delete(mglgpost);

		mglgPostRepository.flush();
	}

	@Override
	public void likePost(MglgPost mglgpost) {
		// TODO Auto-generated method stub

	}

	// DB에서 페이지 기법으로 형태를 짜서 map의 객체로 만들어서 반환
	@Override
	public Page<CamelHashMap> getPagePostList(Pageable pageable, int userId) {

		return mglgPostRepository.getPagePostList(pageable, userId);
	}

	@Override
	public MglgPost getPost(MglgPost post) {

		return mglgPostRepository.findByPostId(post.getPostId());
	}

	// 포스트 갯수 조회
	@Override
	public int postCnt(MglgUserRelation relUser) {
		MglgUser user = relUser.getMglgUser();
		int userId = user.getUserId();
		return mglgPostRepository.postCnt(userId);
	}

	// 검색 - 쿼리 이름 다 안적어서 오류남. keyword가 아니고 searchKeyword임.그리고 매개변수랑 이름 적은 거랑 일치하게
	// 줘야함.
	@Override
	public Page<MglgPost> searchPostList(MglgPost mglgPost, Pageable pageable) {
		if (mglgPost.getSearchKeyword() != null && !mglgPost.getSearchKeyword().equals("")) {
			if (mglgPost.getSearchCondition().equals("ALL")) {
				return mglgPostRepository
						.findByPostContentOrRestNmOrHashTag1OrHashTag2OrHashTag3OrHashTag4OrHashTag5OrSearchKeywordContainingOrderByPostDateDesc(
								mglgPost.getSearchKeyword(), mglgPost.getSearchKeyword(), mglgPost.getSearchKeyword(),
								mglgPost.getSearchKeyword(), mglgPost.getSearchKeyword(), mglgPost.getSearchKeyword(),
								mglgPost.getSearchKeyword(), mglgPost.getSearchKeyword(), pageable);
			} else if (mglgPost.getSearchCondition().equals("POSTCONTENT")) {
				return mglgPostRepository.findByPostContentContainingOrderByPostDateDesc(mglgPost.getSearchKeyword(),
						pageable);
			} else if (mglgPost.getSearchCondition().equals("RESTNM")) {
				return mglgPostRepository.findByRestNmContainingOrderByPostDateDesc(mglgPost.getSearchKeyword(),
						pageable);
			} else if (mglgPost.getSearchCondition().equals("HASHTAG")) {
				return mglgPostRepository
						.findByHashTag1OrHashTag2OrHashTag3OrHashTag4OrHashTag5ContainingOrderByPostDateDesc(
								mglgPost.getSearchKeyword(), mglgPost.getSearchKeyword(), mglgPost.getSearchKeyword(),
								mglgPost.getSearchKeyword(), mglgPost.getSearchKeyword(), pageable);
			} else {
				return mglgPostRepository.findAll(pageable);
			}
		} else {
			return mglgPostRepository.findAll(pageable);
		}

	}

	@Override
	public Page<MglgPost> userPostList(int userId, Pageable pageable) {

		return mglgPostRepository.findByUserId(userId, pageable);
	}

	@Override
	public Page<MglgPost> getFollowerPost(int userId, Pageable pageable) {

		return mglgPostRepository.getFollowerPost(userId, pageable);
	}
	
	@Override
	public int likeUp(int userId, int postId) {
		mglgPostRepository.likeUp(userId, postId);
		
		return (Integer)mglgPostRepository.boardLikeCnt(postId);
	}

	@Override
	public int likeDown(int userId, int postId) {
		mglgPostRepository.likeDown(userId, postId);
		
		mglgPostRepository.flush();
		
		return (Integer)mglgPostRepository.boardLikeCnt(postId);
	}

	@Override
	public String reportPost(int postId,int userId) {
		//자신의 게시물을 신고시 리턴시킴
		if(0 != mglgPostRepository.reportPostSelfCheck(postId,userId)) {
			return "self";
		}
		//동일 신고가 존재하면 리턴시킴
		if(0 != mglgPostRepository.reportPostCheck(postId,userId)) {
			return "fail";
		}
		
		
		//신고하는 로직
		mglgPostRepository.reportPost(postId,userId);
		return "success";
	}
}

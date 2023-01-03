package com.muglang.muglangspace.service.mglgpostfile;

import java.util.List;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgPostFile;


public interface MglgPostFileService {
	//게시글의 파일들을 수정할 수 있게 로드하는 메소드
	public List<MglgPostFile> getPostFileList(int postId);
	
	//게시글의 파일 1개를 불러오는 메소드.
	public MglgPostFile getPostFile(MglgPost mglgPost, MglgPostFile MglgPostFile);
	
	//게시글의 파일1개를 등록하는 메소드.
	public void insertPostFile(MglgPostFile file);
	
	//게시글에 올린 파일 목록을 수정하는 메소드.
	public void updatePostFileList(MglgPostFile fileList);
	
	//게시글의 파일을 삭제하는 것은 수정하는 것으로 대체함. 게시글을 지울시 해당 게시글의 파일들도 DB에서 제외함.
}

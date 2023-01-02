package com.muglang.muglangspace.service.mglgpostfile.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgPostFile;
import com.muglang.muglangspace.entity.MglgPostFileId;
import com.muglang.muglangspace.repository.MglgPostFileRepository;
import com.muglang.muglangspace.service.mglgpostfile.MglgPostFileService;

public class MglgPostFileServiceImpl implements MglgPostFileService{
	@Autowired
	private MglgPostFileRepository mglgPostFileRepository;
	//해당 게시글의 모든 파일들을 불러오는 메소드
	@Override
	public List<MglgPostFile> getPostFileList(MglgPost mglgPost) {
		// TODO Auto-generated method stub
		return mglgPostFileRepository.findAllByMglgPost(mglgPost);
	}
	
	//해당 게시글의 파일 1개를 로드하는 메소드.
	@Override
	public MglgPostFile getPostFile(MglgPost mglgPost, MglgPostFile mglgPostFile) {
		MglgPostFileId targetId = new MglgPostFileId(mglgPost.getPostId(), mglgPostFile.getPostFileId());
		Optional<MglgPostFile> targetFile = mglgPostFileRepository.findById(targetId);
		// TODO Auto-generated method stub
		return targetFile.get();
	}

	//해당 게시글의 파일 1개씩 DB에 저장하여 입력한다.
	@Override
	public void insertPostFileList(MglgPostFile fileList) {
		// TODO Auto-generated method stub
		mglgPostFileRepository.save(fileList);
		
		mglgPostFileRepository.flush();
	}

	//해당 게시글의 파일 정보를 수정한다.
	@Override
	public void updatePostFileList(MglgPostFile fileList) {
		// TODO Auto-generated method stub
		mglgPostFileRepository.save(fileList);
		
		mglgPostFileRepository.flush();
	}

}

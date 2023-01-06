package com.muglang.muglangspace.service.mglgpostfile.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgPostFile;
import com.muglang.muglangspace.entity.MglgPostFileId;
import com.muglang.muglangspace.repository.MglgPostFileRepository;
import com.muglang.muglangspace.service.mglgpostfile.MglgPostFileService;

@Service
public class MglgPostFileServiceImpl implements MglgPostFileService{
	@Autowired
	private MglgPostFileRepository mglgPostFileRepository;
	//해당 게시글의 모든 파일들을 불러오는 메소드
	@Override
	public List<MglgPostFile> getPostFileList(int postId) {
		// TODO Auto-generated method stub
		return mglgPostFileRepository.findAllByMglgPost(postId);
	}
	
	//해당 게시글의 파일 1개를 로드하는 메소드.
	@Override
	public MglgPostFile getPostFile(MglgPost mglgPost, MglgPostFile mglgPostFile) {
		MglgPostFileId targetId = new MglgPostFileId(mglgPost.getPostId(), mglgPostFile.getPostFileId());
		Optional<MglgPostFile> targetFile = mglgPostFileRepository.findById(targetId);
		// TODO Auto-generated method stub
		return targetFile.get();
	}

	//해당 게시글의 파일 1개씩 DB에 저장하여 입력한다. controller 에서 반복문을 통해 여러개의 파일을 입력하게됨.
	@Override
	public void insertPostFile(MglgPostFile fileList) {
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
	//포스트의 아이디에 해당하는 모든 파일리스트를 찾아서 지운다.
	@Override
	public void deletePostFileList(int postId) {
		// TODO Auto-generated method stub
		mglgPostFileRepository.deleteAllByPostId(postId);
		
		mglgPostFileRepository.flush();
	}

}

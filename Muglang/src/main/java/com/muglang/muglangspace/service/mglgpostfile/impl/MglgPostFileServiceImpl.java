package com.muglang.muglangspace.service.mglgpostfile.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgPostFile;
import com.muglang.muglangspace.repository.MglgPostFileRepository;
import com.muglang.muglangspace.service.mglgpostfile.MglgPostFileService;

public class MglgPostFileServiceImpl implements MglgPostFileService{
	@Autowired
	private MglgPostFileRepository mglgPostFileRepository;
	
	@Override
	public List<MglgPostFile> getPostFileList(MglgPost mglgPost) {
		// TODO Auto-generated method stub
		return mglgPostFileRepository.findAllByMglgPost(mglgPost);
	}
	
}

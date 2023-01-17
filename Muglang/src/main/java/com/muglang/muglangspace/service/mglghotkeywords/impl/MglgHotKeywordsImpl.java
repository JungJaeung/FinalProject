package com.muglang.muglangspace.service.mglghotkeywords.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.repository.MglgHotKeywordsRepository;
import com.muglang.muglangspace.service.mglghotkeywords.MglgHotKeywordsService;

@Service
public class MglgHotKeywordsImpl implements MglgHotKeywordsService {
	
	// DB에 데이터를 CRUD할 일꾼
	@Autowired MglgHotKeywordsRepository mglgHotKeywordsRepository;
	
	// 키워드 검색 시 DB에 키워드가 없을 경우에는 INSERT 있을 경우에는 UPDATE 쿼리를 보냄
	@Override
	public void insrtOrUpdte(String searchKeyword) {
		mglgHotKeywordsRepository.insrtOrUpdte(searchKeyword);
	}

}

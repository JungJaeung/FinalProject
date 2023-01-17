package com.muglang.muglangspace.service.mglghotkeywords;

public interface MglgHotKeywordsService {
	
	// 키워드 검색 시 DB에 키워드가 없을 경우에는 INSERT 있을 경우에는 UPDATE 쿼리를 보냄
	public void insrtOrUpdte(String searchKeyword);
}

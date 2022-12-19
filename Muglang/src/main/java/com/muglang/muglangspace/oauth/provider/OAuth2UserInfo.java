package com.muglang.muglangspace.oauth.provider;

//221219 김동현 작업중
//Google, Kakao, Naver 소셜 로그인 기능 구현을 위한 Interface
public interface OAuth2UserInfo {
	int getProviderId(); //소셜 로그인 제공 업체 ID
	String getPrivider(); //소셜 로그인 제공 업체명
	String getEmail(); //업체로부터 받은 사용자 Email (선택)
	String getName(); //업체로부터 받은 닉네임(필수)
}

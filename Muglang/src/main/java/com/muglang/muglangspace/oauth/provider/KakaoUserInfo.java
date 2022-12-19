package com.muglang.muglangspace.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{
	Map<String, Object> attributes;
	Map<String, Object> properties;
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.properties = (Map<String, Object>)attributes.get(("kakao acount");
	}
	
	@Override
	public String getProviderId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPrivider() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

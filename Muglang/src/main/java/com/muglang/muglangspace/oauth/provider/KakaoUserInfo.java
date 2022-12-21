package com.muglang.muglangspace.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{
	Map<String, Object> attributes;
	Map<String, Object> properties;
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.properties = (Map<String, Object>)attributes.get("kakao_account");
	}
	
	@Override
	public String getProviderId() {
		return attributes.get("id") + "";
	}
	
	@Override
	public String getProvider() {
		return "kakao";
	}
	
	@Override
	public String getEmail() {
		return properties.get("email") + "";
	}
	
	@Override
	public String getName() {
		Map profile = (Map)properties.get("profile");
		return profile.get("nickname") + "";
	}
	
	
}

package com.muglang.muglangspace.oauth;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.oauth.provider.KakaoUserInfo;
import com.muglang.muglangspace.oauth.provider.OAuth2UserInfo;
import com.muglang.muglangspace.repository.MglgUserRepository;

@Service //서비스 표시
public class Oauth2UserService extends DefaultOAuth2UserService {
	
	//이미 가입한 회원인지 검사하기위해 MglgUserRepository를 가져옴
	@Autowired
	MglgUserRepository mglgUserRepository;
	
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		Map<String, Object> temp = oAuth2User.getAttributes();
		
		Iterator<String> iter = temp.keySet().iterator(); //이더레이터로 벨류 값만 저장
		
		while(iter.hasNext()) {
			System.out.println(iter.next());
			System.out.println(userRequest.getAccessToken().getTokenValue());
		}
		
		String userName = ""; //닉네임
		String providerId = "";//업체가 제공한 아이디
		
		OAuth2UserInfo oAuth2UserInfo = null;
		
		//소셜 카테고리 검증
		if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
			userName = oAuth2UserInfo.getName();
			providerId = oAuth2UserInfo.getProviderId();		
		} else {
			System.out.println("카카오 계정이 아닙니다.");
		}
		
		String provider = oAuth2UserInfo.getProvider();
		//userId = kakao_4891279
		String userId = provider + "_" + providerId; //id는 식별 목적, 표시는 닉네임으로
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		
		//사용자가 이미 소셜 로그인한 기록이 있는지 검사
		MglgUser mglgUser;
		
		//userId가 존재하면 true 존재하지 않으면 false로 반환 
		if(mglgUserRepository.findById(userId).isPresent()) {
			//userId가 존재할 시 정보를 mglgUser 엔티티에 담아줌
			mglgUser = mglgUserRepository.findById(userId).get();
		} else {
			//존재하지 않으면 null로 리턴하여 회원가입
			mglgUser = null;
		}
		
		if(mglgUser == null) {
			mglgUser = MglgUser.builder()
							   .userId(userId)
							   .userName(userName)
							   .email(email)
							   .userRole(role)
							   .build();
			
			mglgUserRepository.save(mglgUser);
		}
		
		
		//SecurityContext에 인증 정보 저장
		return CustomUserDetails.builder()
								.mglgUser(mglgUser)
								.attributes(oAuth2User.getAttributes())
								.build();
	}
	
}

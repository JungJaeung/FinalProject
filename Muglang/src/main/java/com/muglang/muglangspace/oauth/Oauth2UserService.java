package com.muglang.muglangspace.oauth;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.controller.UserController;
import com.muglang.muglangspace.entity.CustomUserDetails;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.oauth.provider.GoogleUserInfo;
import com.muglang.muglangspace.oauth.provider.KakaoUserInfo;
import com.muglang.muglangspace.oauth.provider.NaverUserInfo;
import com.muglang.muglangspace.oauth.provider.OAuth2UserInfo;
import com.muglang.muglangspace.repository.MglgUserRepository;

@Service //서비스 표시
public class Oauth2UserService extends DefaultOAuth2UserService {
	
	//이미 가입한 회원인지 검사하기위해 MglgUserRepository를 가져옴
	@Autowired
	MglgUserRepository mglgUserRepository;

	public OAuth2User loadUser(OAuth2UserRequest userRequest, HttpSession session) throws OAuth2AuthenticationException {
		System.out.println("111111111111111111111");
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		Map<String, Object> temp = oAuth2User.getAttributes();
		
		Iterator<String> iter = temp.keySet().iterator(); //이더레이터로 키 값만 저장
		
		while(iter.hasNext()) {
			System.out.println(iter.next());
			System.out.println(userRequest.getAccessToken().getTokenValue());
		}
		
		String userName = ""; //닉네임
		String providerId = "";//업체가 제공한 아이디
		String providerName = userRequest.getClientRegistration().getRegistrationId();//업체이름
		
		OAuth2UserInfo oAuth2UserInfo = null;
		
		//소셜 카테고리 검증 카카오, 네이버, 구글
		if(providerName.equals("kakao")) { //업체의 이름이 카카오라면 유저의 정보를 변수에 담아줌
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
			userName = oAuth2UserInfo.getName();
			providerId = oAuth2UserInfo.getProviderId();		
		} else if(providerName.equals("naver"))  { //업체의 이름이 네이버라면 유저의 정보를 변수에 담아줌
			oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
			userName = oAuth2UserInfo.getName();
			providerId = oAuth2UserInfo.getProviderId();
		} else if(providerName.equals("google"))  { //업체의 이름이 서브(구글)라면 유저의 정보를 변수에 담아줌
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
			userName = oAuth2UserInfo.getName();
			providerId = oAuth2UserInfo.getProviderId();
		} else {
			System.out.println("소셜 계정을 다시 확인해주세요");
		}
		
		String provider = oAuth2UserInfo.getProvider();
		//userSnsId = kakao_4891279
		String userSnsId = provider + "_" + providerId; //id는 식별 목적, 표시는 닉네임으로
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		
		//사용자가 이미 소셜 로그인한 기록이 있는지 검사
		MglgUser mglgUser;
		
		//userSnsId가 존재하면 true 존재하지 않으면 false로 반환, user 정보를 찾는 것은 userSnsId로 sns로그인의 기록이 있는지 확인한다.
		if(mglgUserRepository.findByUserSnsId(userSnsId) != null) {
			//userId가 존재할 시 정보를 mglgUser 엔티티에 담아줌
			System.out.println("존재하는 회원입니다. 로그인을 진행합니다.");
			mglgUser = mglgUserRepository.findByUserSnsId(userSnsId);
		} else {
			System.out.println("회원이 존재하지 않습니다. 새로 회원을 등록해야합니다.");
			//존재하지 않으면 null로 리턴하여 회원가입
			mglgUser = null;
		}
		if(mglgUser == null) {
			mglgUser = MglgUser.builder()
							   .userSnsId(userSnsId)
							   .userName(userName)
							   .email(email)
							   .userRole(role)
							   .build();
			System.out.println("새로운 회원을 등록할 임시 정보를 취합니다." + mglgUser);
			//세션에 담아 추가적인 정보를 처리하려고 했지만 실패함.
			session.setAttribute("loginUser", mglgUser);
			System.out.println(mglgUser);
			//추가정보 입력한 뒤, 로그인 처리를 마무리 할 예정
			UserController.socialLoginView();
			
			//mglgUserRepository.save(mglgUser);
		}
		
		System.out.println("새로운 로그인 정보를 저장완료하였습니다.");
		//SecurityContext에 인증 정보 저장
		return CustomUserDetails.builder()
								.mglgUser(mglgUser)
								.attributes(oAuth2User.getAttributes())
								.build();
	}
	
}
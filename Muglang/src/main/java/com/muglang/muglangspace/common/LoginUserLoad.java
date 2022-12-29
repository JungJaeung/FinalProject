package com.muglang.muglangspace.common;

import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.entity.MglgUser;

//해당 메소드는 로그인 유저의 정보를 화면단에 출력하기 위해 HTML로 이동하기위해
//DTO 데이터로 만들 때 사용하는 함수.
//예) MglgUser의 정보를 꺼내고, 해당 객체를 MglgUserDTO로 바꿀 때 사용하면 됩니다.
public class LoginUserLoad {
	public static MglgUserDTO toHtml(MglgUser user) {
		MglgUserDTO info = MglgUserDTO.builder()
									  .userId(user.getUserId())
									  .userName(user.getUserName())
									  .userRole(user.getUserRole())
									  .firstName(user.getFirstName())
									  .lastName(user.getLastName())
									  .bio(user.getBio())
									  .regDate(user.getRegDate().toString())
									  .email(user.getEmail())
									  .address(user.getAddress())
									  .phone(user.getPhone())
									  .build();
		return info;
	}
}

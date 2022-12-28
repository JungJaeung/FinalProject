package com.muglang.muglangspace.common;

import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.entity.MglgUser;

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

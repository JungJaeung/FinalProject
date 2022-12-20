package com.muglang.muglangspace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDetails {
	private MglgUser mglgUser;
	
	//소셜 로그인에서 사용자 정보를 담아줄 맵
}

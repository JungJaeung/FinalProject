package com.muglang.muglangspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MglgUserDTO {
	private int userId;
	private String userName;
	private String passWord;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String address;
	private String bio;
	private String regDate;
	private String userRole;
}

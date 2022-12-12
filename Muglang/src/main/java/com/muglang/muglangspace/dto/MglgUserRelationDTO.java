package com.muglang.muglangspace.dto;


import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MglgUserRelationDTO {
	private int userId;
	private int followerId;
	private String followDate;
}

package com.muglang.muglangspace.dto;


import com.muglang.muglangspace.entity.MglgUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MglgPostDTO {

	private int postId;

	private MglgUser mglgUser;
	private String postContent;
	private String restNm;
	private int restRating;
	private String postDate;
}

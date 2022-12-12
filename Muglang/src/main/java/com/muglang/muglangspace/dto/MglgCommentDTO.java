package com.muglang.muglangspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MglgCommentDTO {
	private int commentId;
	private int postId;
	private int userId;
	private String commentContent;
	private String commentDate;

}

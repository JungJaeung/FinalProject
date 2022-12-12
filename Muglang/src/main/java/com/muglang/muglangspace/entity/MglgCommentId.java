package com.muglang.muglangspace.entity;

import java.io.Serializable;


import lombok.Data;

@Data
public class MglgCommentId implements Serializable{
	private int commentId;
	private int mglgPost;
}

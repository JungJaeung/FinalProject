package com.muglang.muglangspace.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class MglgPostId implements Serializable{
	private int postId;
	private int userId;
}

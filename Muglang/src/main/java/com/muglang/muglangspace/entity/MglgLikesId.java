package com.muglang.muglangspace.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class MglgLikesId implements Serializable{
	private int mglgPost;
	private int userId;
}

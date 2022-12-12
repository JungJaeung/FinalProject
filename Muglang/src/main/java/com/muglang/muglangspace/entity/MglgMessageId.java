package com.muglang.muglangspace.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class MglgMessageId implements Serializable{
	private int messageNo;
	private int mglgChatroom;
}

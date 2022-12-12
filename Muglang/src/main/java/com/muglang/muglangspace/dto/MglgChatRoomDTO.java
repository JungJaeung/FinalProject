package com.muglang.muglangspace.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MglgChatRoomDTO {
	private int chatroomId;
	private int userId;
	private int receiverId;
	private String datetime;
	private String userYn;
}

package com.muglang.muglangspace.service.mglgchat;

import java.util.List;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgChatMembers;
import com.muglang.muglangspace.entity.MglgChatMessage;
import com.muglang.muglangspace.entity.MglgChatrooms;

public interface ChatService {
	
	List<MglgChatrooms> getChatRooms();
	
	void insertMember(MglgChatMembers member);
	
	void deleteMember(MglgChatMembers member);
	
	int getMember(String chatRoomId, int userId);
	
	List<CamelHashMap> getPastMsg(MglgChatMembers member);
	
	void insertMsg(MglgChatMessage message);
	
}

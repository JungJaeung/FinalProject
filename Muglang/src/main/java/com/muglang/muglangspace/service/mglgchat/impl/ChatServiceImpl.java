package com.muglang.muglangspace.service.mglgchat.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgChatMembers;
import com.muglang.muglangspace.entity.MglgChatMessage;
import com.muglang.muglangspace.entity.MglgChatrooms;
import com.muglang.muglangspace.repository.ChatMembersRepository;
import com.muglang.muglangspace.repository.ChatRoomsRepository;
import com.muglang.muglangspace.service.mglgchat.ChatService;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	private ChatRoomsRepository chatRoomsRepository;
	
	@Autowired
	private ChatMembersRepository chatMembersRepository;


	@Override
	public List<MglgChatrooms> getChatRooms() {
		// TODO Auto-generated method stub
		return chatRoomsRepository.findAll();
	}
	
	@Override
	public void insertMember(MglgChatMembers member) {
		String chatRoomId = member.getChatRoomId();
		int userId = member.getUserId();
		
		if(chatMembersRepository.getMember(chatRoomId, userId) == 0) {
			chatMembersRepository.insertMember(chatRoomId, userId);
		}
		
	}
	
	@Override
	public void deleteMember(MglgChatMembers member) {
		String chatRoomId = member.getChatRoomId();
		int userId = member.getUserId();
		
		chatMembersRepository.deleteMember(chatRoomId, userId);
	}
	
	@Override
	public int getMember(String chatRoomId, int userId) {
		
		return chatMembersRepository.getMember(chatRoomId, userId);
	}
	
	@Override
	public List<CamelHashMap> getPastMsg(MglgChatMembers member) {
		return chatMembersRepository.getPastMsg(member.getChatRoomId(), member.getUserId());
	}
	
	@Override
	public void insertMsg(MglgChatMessage message) {
		String chatRoomId = message.getChatRoomId();
		int userId = message.getUserId();
		String chatContent = message.getChatContent();
		chatMembersRepository.insertMsg(chatRoomId, userId, chatContent);
	}
	
	@Override
	public void leaveRoom(MglgChatMembers member) {
		String chatRoomId = member.getChatRoomId();
		int userId = member.getUserId();
		
		chatMembersRepository.leaveRoom(chatRoomId, userId);
	}
	
}

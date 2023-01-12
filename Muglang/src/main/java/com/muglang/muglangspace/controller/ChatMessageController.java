package com.muglang.muglangspace.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.muglang.muglangspace.dto.ChatMessage;
import com.muglang.muglangspace.dto.MessageType;
import com.muglang.muglangspace.entity.MglgChatMembers;
import com.muglang.muglangspace.entity.MglgChatMessage;
import com.muglang.muglangspace.repository.ChatMembersRepository;
import com.muglang.muglangspace.service.mglgchat.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

	@Autowired
	private ChatService chatService;

	// private final SimpMessagingTemplate template;

//    @Autowired
//    public ChatMessageController(SimpMessagingTemplate template) {
//        this.template = template;
//    }
	
	@Autowired
	public ChatMembersRepository chatMembersRespository;

	private final SimpMessageSendingOperations sendingOperations;

//    @MessageMapping(value = "/chat/join")
//    public void join(ChatMessage message) {
//    	System.out.println("join======================" + message.getChatRoomId());
//        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
//        //template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
//        sendingOperations.convertAndSend("/topic/chat/room/"+message.getChatRoomId(),message);
//    }
//    
	@MessageMapping("/chat/message")
	public void message(ChatMessage message) {
		String chatRoomId = message.getChatRoomId();
		
		
		int userId = message.getUserId();
		//3. 해당 사용자 방 참여여부 조회(select)
		int joinYn = chatService.getMember(chatRoomId, userId);
		
		MglgChatMembers members = MglgChatMembers.builder()
												 .chatRoomId(chatRoomId)
												 .userId(userId)
												 .build();
		chatService.insertMember(members);
		System.out.println(joinYn);
		
		if (MessageType.JOIN.equals(message.getType())) {
			//4. 방 참여여부 데이터가 있으면 join 메시지 안띄우게 
			//5. enterdate보다 최근에 올라온 메시지들 조회
			//6. messageList에 담기
			if(joinYn == 0) {
				message.setMessage(message.getWriter() + "님이 입장하였습니다.");
			} else {
				message.setMessageList(chatService.getPastMsg(members));
			}
		} else {
			MglgChatMessage messageParam = MglgChatMessage.builder()
					 								 .chatRoomId(message.getChatRoomId())
					 								 .userId(message.getUserId())
					 								 .chatContent(message.getMessage())
					 								 .build();
			
			LocalTime currentTime = LocalTime.now();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
            String chatTime = currentTime.format(df);
            message.setChatTime(chatTime);
			
			chatService.insertMsg(messageParam);
		}
		sendingOperations.convertAndSend("/topic/chat/room/" + message.getChatRoomId(), message);

		//System.out.println(message.getMessage());
		// template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(),
		// message);
	}
}

package com.muglang.muglangspace.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.muglang.muglangspace.dto.ChatMessage;
import com.muglang.muglangspace.dto.MessageType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

	// private final SimpMessagingTemplate template;

//    @Autowired
//    public ChatMessageController(SimpMessagingTemplate template) {
//        this.template = template;
//    }

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
		if (MessageType.JOIN.equals(message.getType())) {
			message.setMessage(message.getWriter() + "님이 입장하였습니다.");
		}
		sendingOperations.convertAndSend("/topic/chat/room/" + message.getChatRoomId(), message);

		//System.out.println(message.getMessage());
		// template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(),
		// message);
	}
}

package com.muglang.muglangspace.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T_MGLG_MESSAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@IdClass(MglgMessageId.class)
public class MglgMessage {
	@Id
	private int messageNo;
	@Id
	@ManyToOne
	@JoinColumn(name="CHATROOM_ID")
	private MglgChatroom chatroomId;
	private int userId;
	private int receiverId;
	private String messageContent;
	private LocalDateTime sendTime;
	private String messageReadYn;
}

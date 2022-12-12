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
@Table(name="T_MGLG_BOARD")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Data
public class MglgBoard {
	@Id
	private int boardId;
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private MglgUser mglgUser;
	private String boardTitle;
	private String boardContent;
	private int boardCount;
	private LocalDateTime boardDate = LocalDateTime.now();
}

package com.muglang.muglangspace.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="T_MGLG_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Data
@IdClass(MglgCommentId.class)
public class MglgComment {
	
	@Id
	private int commentId;
	@Id
	@ManyToOne
	@JoinColumn(name="POST_ID")
	private MglgPost mglgPost;
	private String commentContent;
	private LocalDateTime commentDate = LocalDateTime.now();
	@Id
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private MglgUser mglgUser;
}

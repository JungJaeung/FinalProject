package com.muglang.muglangspace.entity;



import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T_MGLG_POST")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Data
public class MglgPost {
	@Id
	private int postId;
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private MglgUser mglgUser;
	private String postContent;
	private String restNm;
	private int postRating;
	private int viewCount;
	private LocalDateTime postDate=LocalDateTime.now();
}

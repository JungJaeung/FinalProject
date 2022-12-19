package com.muglang.muglangspace.entity;



import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	private int postRating;
	private String postContent;
	private String restNm;
	private int restRating;
	private String hashTag1;
	private String hashTag2;
	private String hashTag3;
	private String hashTag4;
	private String hashTag5;
//	@Column
//	@ColumnDefault(LocalDateTime.now())
	private LocalDateTime postDate = LocalDateTime.now();
}

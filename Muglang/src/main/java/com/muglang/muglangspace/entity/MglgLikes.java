package com.muglang.muglangspace.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T_MGLG_LIKES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@IdClass(MglgLikesId.class)
public class MglgLikes {
	@Id
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private MglgUser mglgUser;
	@Id
	@ManyToOne
	@JoinColumn(name="POST_ID")
	private MglgPost mglgPost;
	private LocalDateTime likeDate = LocalDateTime.now();
}

package com.muglang.muglangspace.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T_MGLG_REPORT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@IdClass(MglgReportId.class)
public class MglgReport {
	@Id
	private int reportId;
	@Id
	private int reportType;
	private int sourceUserId;
	private int targetUserId;
	private LocalDateTime reportDate = LocalDateTime.now();
	private int userId;
	private int postId;
	private int commentId;
}

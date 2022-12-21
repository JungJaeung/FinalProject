package com.muglang.muglangspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MglgReportDTO {
	private int reportId;
	private int reportType;
	private int sourceUserId;
	private int targetUserId;
	private String reportDate;
	private int postId;
	private int commentId;
	private int count;
}

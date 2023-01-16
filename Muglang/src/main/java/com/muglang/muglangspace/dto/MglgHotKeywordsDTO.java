package com.muglang.muglangspace.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MglgHotKeywordsDTO {
	private String insertedKeyword;
	private LocalDateTime searchedTime;
}

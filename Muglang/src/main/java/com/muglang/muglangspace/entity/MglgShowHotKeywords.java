package com.muglang.muglangspace.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 사용자에게 보여줄 실시간 검색 순위
@Entity
@Table(name="T_MGLG_SHOW_HOT_KEYWORDS")
@SequenceGenerator(
		name="MglgShowHotKeywordsSequenceGenerator",
		sequenceName="T_MGLG_SHOW_HOT_KEYWORDS_SEQ",
		initialValue=1,
		allocationSize=1
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert // null 값 허용 X, null의 값이 오면 지정된 Default 값이 들어간다.
@Data
public class MglgShowHotKeywords {
	@Id
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE,
			generator="MglgShowHotKeywordsSequenceGenerator"
	)
	private int showKeywordId;
	
	private String showHotKeyword;
	@Column
	@ColumnDefault("LocalDateTime.now()")
	private LocalDateTime showedTime;
	private int keywordOrder;
}

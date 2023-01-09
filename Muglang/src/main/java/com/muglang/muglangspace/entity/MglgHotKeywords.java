package com.muglang.muglangspace.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 사용자가 검색시 INSERT 될 테이블 
@Entity
@Table(name="T_MGLG_HOT_KEYWORDS")
@SequenceGenerator(
		name="MglgHotKeywordsSequenceGenerator",
		sequenceName="T_MGLG_HOT_KEYWORDS_SEQ",
		initialValue=1,
		allocationSize=1
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert // null 값 허용 X, null의 값이 오면 지정된 Default 값이 들어간다.
@Data
public class MglgHotKeywords {
	@Id
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE,
			generator="MglgHotKeywordsSequenceGenerator"
	)
	private int keywordId;
	private String insertedKeyword;
	private LocalDateTime searchedTime;
}

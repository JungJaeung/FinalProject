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
@Table(name="T_MGLG_POST_FILE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MglgPostFile {
	@Id
	private int postId;
	private int postFileId;
	private String postFileNm;
	private String postFileOriginNm;
	private String postFilePath;
	private LocalDateTime postFileRegdate = LocalDateTime.now();
	
}

package com.muglang.muglangspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgPostFile;
import com.muglang.muglangspace.entity.MglgPostFileId;

public interface MglgPostFileRepository extends JpaRepository<MglgPostFile, MglgPostFileId> {
	
	//해당 포스트의 파일을 불러오는 JPA쿼리 메소드임.
	public List<MglgPostFile> findAllByMglgPost(MglgPost mglgPost);
}

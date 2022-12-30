package com.muglang.muglangspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgPostFile;
import com.muglang.muglangspace.entity.MglgPostFileId;

public interface MglgPostFileRepository extends JpaRepository<MglgPostFile, MglgPostFileId> {

}

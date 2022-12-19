package com.muglang.muglangspace.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgReport;
import com.muglang.muglangspace.entity.MglgUser;

public interface MglgReportRepository extends JpaRepository<MglgReport, Integer>{

	Page<MglgReport> findByReportType(int a,Pageable pageable);
}

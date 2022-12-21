package com.muglang.muglangspace.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.muglang.muglangspace.common.CamelHashMap;
import com.muglang.muglangspace.entity.MglgReport;

public interface MglgReportRepository extends JpaRepository<MglgReport, Integer>{

	Page<MglgReport> findByReportType(int a,Pageable pageable);
	
	@Query(value="SELECT TARGET_USER_ID, REPORT_DATE, COUNT(*) AS count"
			+ " FROM T_MGLG_REPORT "
			+ " GROUP BY TARGET_USER_ID "
			+ " ORDER BY count desc"
			, nativeQuery =true)
	Page<CamelHashMap> reportedUser(Pageable pageable);
	
	
}

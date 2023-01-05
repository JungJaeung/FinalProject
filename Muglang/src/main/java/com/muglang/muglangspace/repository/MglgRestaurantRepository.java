package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgRestaurant;

@Transactional
public interface MglgRestaurantRepository extends JpaRepository<MglgRestaurant, Integer>{
	
	@Query(value="SELECT * FROM T_MGLG_RESTAURANT WHERE POST_ID = :postId", nativeQuery=true)
	public MglgRestaurant selectRes(@Param("postId") int postId);
}

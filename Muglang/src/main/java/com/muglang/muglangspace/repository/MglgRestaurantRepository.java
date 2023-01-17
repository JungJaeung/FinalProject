package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgRestaurant;
import com.muglang.muglangspace.entity.MglgRestaurantId;

@Transactional
public interface MglgRestaurantRepository extends JpaRepository<MglgRestaurant, MglgRestaurantId>{
	
	@Query(value="SELECT * FROM T_MGLG_RESTAURANT WHERE POST_ID = :postId", nativeQuery=true)
	public MglgRestaurant selectRes(@Param("postId") int postId);
	
	@Query(value="select count(*)\r\n"
			+ "from t_mglg_restaurant \r\n"
			+ "where post_id\r\n"
			+ "		IN \r\n"
			+ "		(SELECT POST_ID FROM t_mglg_post WHERE USER_ID\r\n"
			+ "				IN \r\n"
			+ "				(SELECT USER_ID FROM T_MGLG_USER_RELATION WHERE FOLLOWER_ID = :userId) ) \r\n"
			+ "AND RES_NAME = :resName group by res_address",
			nativeQuery=true)
	public String countRes(@Param("userId") int userId, @Param("resName") String resName);
	
	@Modifying
	@Query(value="DELETE FROM T_MGLG_RESTAURANT WHERE POST_ID = :postId", nativeQuery=true)
	public void deleteRes(@Param("postId") int postId);
}

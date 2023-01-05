package com.muglang.muglangspace.service.mglgrestaurant;

import java.util.Map;

import com.muglang.muglangspace.entity.MglgRestaurant;

public interface MglgRestaurantService {
	public void insertRestaurant(MglgRestaurant mglgRestaurant);
	
	public MglgRestaurant selectRes(int postId);
}

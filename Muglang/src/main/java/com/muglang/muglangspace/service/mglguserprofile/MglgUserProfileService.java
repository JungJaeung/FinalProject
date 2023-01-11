package com.muglang.muglangspace.service.mglguserprofile;

import com.muglang.muglangspace.entity.MglgUserProfile;

public interface MglgUserProfileService {

	public void insertDefault(int userId,String attachPath);

	public MglgUserProfile getUserImg(int userId);
}

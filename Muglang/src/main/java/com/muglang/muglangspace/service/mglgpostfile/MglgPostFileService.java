package com.muglang.muglangspace.service.mglgpostfile;

import java.util.List;

import com.muglang.muglangspace.entity.MglgPost;
import com.muglang.muglangspace.entity.MglgPostFile;

public interface MglgPostFileService {
	public List<MglgPostFile> getPostFileList(MglgPost mglgPost);
	
}

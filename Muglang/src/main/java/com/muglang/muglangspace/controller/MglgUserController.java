package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muglang.muglangspace.service.mglguser.MglgUserService;

@RestController
@RequestMapping("/user")
public class MglgUserController {
	@Autowired
	private MglgUserService mglgUserService;
	//계정 관련 컨트롤
}

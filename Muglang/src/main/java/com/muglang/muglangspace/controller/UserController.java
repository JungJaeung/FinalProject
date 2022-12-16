package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.service.mglguser.MglgUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private MglgUserService mglgUserService;
	//계정 관련 컨트롤
	
	@GetMapping("/profile")
	public ModelAndView profileView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("profile.html");
		return mv;
	}
}

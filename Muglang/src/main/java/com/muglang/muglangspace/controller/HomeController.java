package com.muglang.muglangspace.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
public class HomeController {
	@RequestMapping("/main")
	public ModelAndView mainPage() {
		ModelAndView mv = new ModelAndView();
		System.out.println("로그인을 해주세요.");
		mv.setViewName("user/login.html");
		
		return mv;
	}
}

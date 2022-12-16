package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.service.mglgadmin.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	//어드민페이지로 이동
	@GetMapping("/adminView")
	public ModelAndView adminView() {
		ModelAndView mv = new ModelAndView();
		System.out.println("asd");
		mv.setViewName("/admin/admin.html");
		return mv;
	}


}

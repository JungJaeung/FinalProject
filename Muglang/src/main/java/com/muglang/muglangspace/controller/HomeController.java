package com.muglang.muglangspace.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.entity.CustomUserDetails;

//index.html에서 login과정을 자동으로 진행한뒤 이곳을 매핑하여 계정확인을 다시 수행함.
@RestController
@RequestMapping("/home")
public class HomeController {
	@RequestMapping("/main")
	public ModelAndView mainPage(HttpServletResponse response, HttpSession session) throws IOException {
		System.out.println(SecurityContextHolder.getContext());
		
		ModelAndView mv = new ModelAndView();
		
		CustomUserDetails userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//로그인을 했던 기존 유저인지 아닌지 확인하는 과정.
		if(userInfo.getMglgUser().getRegDate() != null) {
			System.out.println("기존 회원이 로그인합니다.");
			System.out.println("회원의 아이디와 메일 : " + userInfo.getMglgUser().getUserId() + ", " + userInfo.getMglgUser().getEmail());
			session.setAttribute("loginUser", userInfo);
			response.sendRedirect("/post/mainPost");
			//mv.setViewName("post/post.html");
		} else { //신규 회원일 경우 처리
			System.out.println("신규회원입니다.");
			mv.setViewName("user/socialLogin.html");
		}
		return mv;
	}
}

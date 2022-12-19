package com.muglang.muglangspace.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.dto.ResponseDTO;
import com.muglang.muglangspace.entity.MglgUser;
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

	//유저 목록 불러오기 + 페이징
	@GetMapping("/getUserList")
	public ModelAndView getUserList(MglgUserDTO userDTO, @PageableDefault(page = 0, size = 10) Pageable pageable) {
		MglgUser user = MglgUser.builder()
					   .searchCondition(userDTO.getSearchCondition())
					   .searchKeyword(userDTO.getSearchKeyword())
					   .build();
			
		Page<MglgUser> pageUserList = mglgUserService.getUserList(user, pageable);
		Page<MglgUserDTO> pageUserDTOList = pageUserList.map(pageUser -> 
													MglgUserDTO.builder()
																.userId(pageUser.getUserId())
																.userName(pageUser.getUserName())
																.password(pageUser.getPassword())
																.firstName(pageUser.getFirstName())
																.lastName(pageUser.getLastName())
																.phone(pageUser.getPhone())
																.email(pageUser.getEmail())
																.address(pageUser.getAddress())
																.bio(pageUser.getBio())
																.userBanYn(pageUser.getUserBanYn())
																.password(pageUser.getPassword())
																.firstName(pageUser.getFirstName())
																.regDate(pageUser.getRegDate() == null ?
																	   	null :
																	   		pageUser.getRegDate().toString())
																.build()
														);

					ModelAndView mv = new ModelAndView();
					mv.setViewName("/admin/adminUser.html");
					mv.addObject("getUserList", pageUserDTOList);
					
					if(userDTO.getSearchCondition() != null && !userDTO.getSearchCondition().equals("")) {
						mv.addObject("searchCondition", userDTO.getSearchCondition());
					}
					
					if(userDTO.getSearchKeyword() != null && !userDTO.getSearchKeyword().equals("")) {
						mv.addObject("searchKeyword", userDTO.getSearchKeyword());
					}
					
					return mv;
	}//getUserList끝
	
	//로그인을 위한 페이지로 이동하는 임시 mapping
	@GetMapping("/login")
	public ModelAndView loginPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/login.html");
		return mv;
	}
	
	//로그인 시도하는 임시 url
	@PostMapping("/login")
	public ModelAndView loginProcess(MglgUserDTO userDTO, HttpSession session) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView();
		
		System.out.println(userDTO);
		
		try {
			MglgUser user = MglgUser.builder()
							.userId(userDTO.getUserId())
							.build();
			MglgUser checkedUser = mglgUserService.loginUser(user);
			if(checkedUser == null) {
				System.out.println("로그인을 실패함.");
				returnMap.put("msg", "idFail");
			} else {

				MglgUserDTO loginUser = MglgUserDTO.builder()
									 .userId(checkedUser.getUserId())
									 .userName(checkedUser.getUserName())
									 .password(checkedUser.getPassword())
									 .email(checkedUser.getEmail())
									 .phone(checkedUser.getPhone())
									 .regDate(checkedUser.getRegDate().toString())
									 .userRole(checkedUser.getUserRole())
									 .build();
				
				session.setAttribute("loginUser", loginUser);
				System.out.println("로그인한 유저 아이디 : " + loginUser);
			}
			responseDTO.setItem(returnMap);
			mv.addObject("loginUser", session.getAttribute("loginUser"));
			mv.setViewName("post/post.html");
			
			return mv;
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			
			mv.setViewName("user/login.html");
			
			return mv;
		}
	}
	
}//페이지 끝

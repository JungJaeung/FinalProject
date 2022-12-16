package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.service.mglguser.MglgUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private MglgUserService mglgUserService;
	//계정 관련 컨트롤
	
	//유저 목록 불러오기 + 페이징
	@GetMapping("getUserList")
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
	
	
	
	
	
}//페이지 끝

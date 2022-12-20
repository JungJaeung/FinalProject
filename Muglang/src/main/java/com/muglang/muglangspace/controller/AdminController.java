package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.dto.MglgUserDTO;
import com.muglang.muglangspace.entity.MglgUser;
import com.muglang.muglangspace.service.mglgadmin.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	// 어드민페이지로 이동
	@GetMapping("/adminView")
	public ModelAndView adminView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/memberManager.html");
		return mv;
	}

	// 신고멤버로 이동
	@GetMapping("/declarationMember")
	public ModelAndView declarationMember() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/declarationMember.html");
		return mv;
	}

	// 신고글로 이동
	@GetMapping("/declarationBoard")
	public ModelAndView declarationBoard() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/declarationBoard.html");
		return mv;
	}

	// 신고댓글로 이동
	@GetMapping("/declarationComment")
	public ModelAndView declarationComment() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/declarationComment.html");
		return mv;
	}

	// FAQ로 이동
	@GetMapping("/FAQ")
	public ModelAndView FAQ() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/F.A.Q.html");
		return mv;
	}

	// 밴 유저 yn 변경
	@PutMapping("banUser")
	public ResponseEntity<?> banUser(MglgUserDTO userDTO) {
		MglgResponseDTO<MglgUserDTO> response = new MglgResponseDTO<>();

		try {
			MglgUser user = MglgUser.builder().userId(userDTO.getUserId()).userBanYn(userDTO.getUserBanYn()).build();
			user = adminService.uptUserBan(user);
			MglgUserDTO returnUserDTO = MglgUserDTO.builder().userId(user.getUserId()).userBanYn(user.getUserBanYn())
					.build();

			response.setItem(returnUserDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

	}

}

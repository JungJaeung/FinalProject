package com.muglang.muglangspace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/board")
public class FAQController {
	// FAQ로 이동
		@GetMapping("/FAQ")
		public ModelAndView FAQ() {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/board/F.A.Q.html");
			return mv;
		}
}

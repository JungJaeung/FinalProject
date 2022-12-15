package com.muglang.muglangspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class FQAController {
	@Autowired
	private FQAController mglgBoardController;
	//게시글 관련 컨트롤
}

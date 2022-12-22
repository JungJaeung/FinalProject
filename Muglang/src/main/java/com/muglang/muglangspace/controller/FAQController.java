package com.muglang.muglangspace.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muglang.muglangspace.dto.MglgBoardDTO;
import com.muglang.muglangspace.dto.MglgResponseDTO;
import com.muglang.muglangspace.entity.MglgBoard;
import com.muglang.muglangspace.service.mglgboard.MglgBoardService;

@RestController
@RequestMapping("/board")
public class FAQController {
	@Autowired
	private MglgBoardService mglgBoardService;

	// FAQ로 이동
	@GetMapping("/adminFAQ")
	public ModelAndView adminFAQ() {
		List<MglgBoard> board = mglgBoardService.getFAQList();
		ModelAndView mv = new ModelAndView();

		mv.addObject("boardList", board);
		mv.setViewName("/admin/adminFAQ.html");
		return mv;
	}

	// FAQ로 이동
	@GetMapping("/userFAQ")
	public ModelAndView userFAQ() {
		List<MglgBoard> board = mglgBoardService.getFAQList();
		ModelAndView mv = new ModelAndView();

		mv.addObject("boardList", board);
		mv.setViewName("/board/userFAQ.html");
		return mv;
	}

	@GetMapping("getAdminFAQ")
	public ResponseEntity<?> getBoard(@RequestParam("boardId") int boardId) {
		MglgResponseDTO<MglgBoardDTO> response = new MglgResponseDTO<>();
		try {
			MglgBoard board = MglgBoard.builder().boardId(boardId).build();

			board = mglgBoardService.getBoard(board);
			MglgBoardDTO returnBoardDTO = MglgBoardDTO.builder()
					.boardTitle(board.getBoardTitle())
					.boardContent(board.getBoardContent())
					.build();

			response.setItem(returnBoardDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	

	@GetMapping("/deleteBoard")
	public void deleteBoard(@RequestParam("boardId") int boardId, HttpServletResponse response) throws IOException {
		mglgBoardService.deleteBoard(boardId);

		response.sendRedirect("/board/adminFAQ");
	}

}

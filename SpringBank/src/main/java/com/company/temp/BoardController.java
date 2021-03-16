package com.company.temp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.board.service.NewBoardVO;

@Controller
public class BoardController {

	
	//파라미터를 vo에 담고 결과페이지에서 title과 writer 출력하는 핸들러 작성
	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("board") NewBoardVO vo) {
		return "insertBoardResult";
	}
	
	//파라미터를 vo에 담고 vo를 return 
	@RequestMapping("/ajaxInsertBoard")
	@ResponseBody
	public NewBoardVO ajaxInsertBoard(NewBoardVO vo) {
		return vo;
	}
	
	//파라미터를 vo에 담고 
	
}

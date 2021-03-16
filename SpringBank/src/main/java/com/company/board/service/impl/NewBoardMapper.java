package com.company.board.service.impl;

import com.company.board.service.NewBoardVO;

public interface NewBoardMapper {
	
	public int insertNewBoard(NewBoardVO vo);
	public NewBoardVO getBoard(NewBoardVO vo);
	
}

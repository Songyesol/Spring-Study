package com.company.board.service;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NewBoardVO {
	private int seq;
	private String title;
	private String writer;
	private String content;
	private String filename; 
// 	form데이터에서 실제로 넘어오는 upload파일 (단일파일)
//	private MultipartFile uploadFile; 
	
	/*다중파일 업로드*/
	private MultipartFile[] uploadFile; 
}

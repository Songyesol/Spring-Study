package com.company.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.company.board.service.NewBoardVO;
import com.company.board.service.impl.NewBoardMapper;
import com.company.common.FileRenamePolicy;

@Controller
public class NewBoardController {
	@Autowired
	NewBoardMapper mapper;

	@GetMapping("/insertNewBoard")
	public String insertNewBoardForm(NewBoardVO vo) {
		return "board/insertBoard";
	}

	@PostMapping("/insertNewBoard")
	public String insertNewBoard(NewBoardVO vo) throws IllegalStateException, IOException {
		MultipartFile[] uploadFiles = vo.getUploadFile();
		String filenames = "";
		Boolean start = true;
		for(MultipartFile file : uploadFiles) {
			if (file != null && !file.isEmpty() && file.getSize() > 0) {
				
				String filename = file.getOriginalFilename(); // upload된 파일 실제이름 
				
				/*file이름 중복체크*/
				File rename = FileRenamePolicy.rename(new File("C:/upload", filename));
				if(! start) {				
					filenames += ",";
				} else {
					start=false;
				}
				filenames += rename.getName();	
				file.transferTo(rename); 		//임시폴더에서 실제 업로드 폴더로 파일 이동
			}
		}
		/* 등록 서비스 호출 */
		vo.setFilename(filenames); 	//rename된 파일명 vo에 담기
		System.out.println("======== 업로드결과vo : " + vo);
		mapper.insertNewBoard(vo);
		return "redirect:/getBoard?seq=" + vo.getSeq();
	}

	// 단건조회
	@GetMapping("/getBoard")
	public String getBoard(NewBoardVO vo, Model model) {
		model.addAttribute("board", mapper.getBoard(vo));
		return "board/getBoard";
	}

	// file 다운
	@GetMapping("/fileDown")
	public void fileDown(NewBoardVO vo, HttpServletResponse response) throws Exception {
		vo = mapper.getBoard(vo);
		File file = new File("c:/upload", vo.getFilename());
		if (file.exists()) { //파일이 존재하면 다운로드 
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition","attachment; filename=\"" + URLEncoder.encode(vo.getFilename(), "utf-8") + "\"");
			BufferedInputStream in = null;
			BufferedOutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(file));
				out = new BufferedOutputStream(response.getOutputStream());
				FileCopyUtils.copy(in, out);
				out.flush();
				
			} catch (IOException ex) {
			} finally {
				in.close();
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			
		} else { //아니면 error alert 
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().append("<script>")
								.append("alert('파일이 존재하지 않습니다.');")
								.append("history.go(-1);")
								.append("</script>");
		}
	}
	
	// file 다운
	@GetMapping("/fileNameDown")
	public void fileNameDown(NewBoardVO vo, HttpServletResponse response) throws Exception {
		File file = new File("c:/upload", vo.getFilename());
		if (file.exists()) { //파일이 존재하면 다운로드 
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition","attachment; filename=\"" + URLEncoder.encode(vo.getFilename(), "utf-8") + "\"");
			BufferedInputStream in = null;
			BufferedOutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(file));
				out = new BufferedOutputStream(response.getOutputStream());
				FileCopyUtils.copy(in, out);
				out.flush();
				
			} catch (IOException ex) {
			} finally {
				in.close();
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			
		} else { //아니면 error alert 
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().append("<script>")
								.append("alert('파일이 존재하지 않습니다.');")
								.append("history.go(-1);")
								.append("</script>");
		}
	}

}

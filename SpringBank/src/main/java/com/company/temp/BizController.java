package com.company.temp;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BizController {
	
	//페이지 이동
	@GetMapping("/getBiz")
	public String getBizForm() {
		return "biz/getBiz";
	}
	
	//크롤링 결과 조회 
	//넘겨주는 파라미터의 name과 vo 내 이름이나 변수명이 같으면 RequestParam을 쓸때 자동으로 받아줌.
	@PostMapping("/getBiz")
	public String getBiz(@RequestParam String bizno, Model model) {
		/*크롤링*/
		/*수집대상 URL*/
		String url="https://bizno.net/?query=" + bizno;
		
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elements element = doc.select(".title a h4");
		String bizname = element.text();
		System.out.println(bizname);
		
		//회사이름 찾아서 
		model.addAttribute("bizname", bizname);
		return "biz/getBiz";
	}
}

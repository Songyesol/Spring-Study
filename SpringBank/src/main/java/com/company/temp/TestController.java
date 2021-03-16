package com.company.temp;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.temp.service.TestVO;

@Controller
public class TestController {
	
	//GET  / parameter 전달 : command 객체(vo)
	//@GetMapping("/getTest1")
	@PostMapping("/getTest1")
	public String getTest1(TestVO vo) {
		System.out.println(vo);
		return "home";
	}
	
	//파라미터 @RequestParam = > request.getParameter("")와 동일
	@RequestMapping("/getTest2")
	public String getTest2(	  @RequestParam String firstName 
							, @RequestParam int salary) {
		System.out.println(firstName+ " : " + salary);
		return "home";
	}
	
	@PostMapping("/getTest3")
	//보내는 변수name과 받을 변수명이 다르다면 requestparam 뒤에 보내는 변수명을 입력해주면 됨.
	public String getTest3(@RequestParam("hobby") String[] hobbies) {
//		for(String hobbies : hobby) {
//			System.out.println(hobbies);
//		}
//		List<String> hobbies = new ArrayList( Arrays.asList(hobby));
		System.out.println(Arrays.asList(hobbies));
		
		return "home";
	}
	
	@RequestMapping("/getTest4")
	//@RequestBody TestVO vo : 보내는데이터가 json일때 json을 파싱해서 vo에 담거라.
	//{ } -> 객체 (vo 나 Map으로 받을수 있음)
	//[ ] -> 배열 (List나 String[] 등으로 받을 수 있음
	public String getTest4(@RequestBody List<Map> list) {
		System.out.println(list);
		return "home";
	}
	
	@RequestMapping("/getTest5/{firstName}/{salary}")
	//@ModelAttribute -> command객체는 원래 class이름의 첫글자만 소문자로 바꿔서 자동으로 Model로 넘어가는데 modelAttribute를 쓰면 넘어가는  key이름을 지정할 수있다. 
	public String getTest5(	@PathVariable String firstName, 
							@PathVariable int salary, 
							@ModelAttribute("tvo") TestVO vo,
							Model model) {
		//vo에 담고싶다면..이렇게...
		vo.setFirstName(firstName);
		vo.setSalary(Integer.toString(salary));
		
		model.addAttribute("firstName", firstName);
		return "test";
	}
	
	@RequestMapping("/getTest6/{firstName}/{salary}")
	public ModelAndView getTest6(	@PathVariable String firstName, 
									@PathVariable int salary, 
									@ModelAttribute("tvo") TestVO vo) {
		vo.setFirstName(firstName);
		vo.setSalary(Integer.toString(salary));
		
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("firstName", firstName);
//		mv.setViewName("test");
		return new ModelAndView("test","firstName",firstName);
	}
	
	
	/*응답결과  JSON타입으로 받기*/
	@RequestMapping("/getTest7/{firstName}/{salary}")
	 /*@ResponseBody를 서블릿자리에 적든지 VO앞에 적던지 상관없음.*/
	/*VO객체로 결과만 받겠다*/
	public @ResponseBody TestVO getTest7(	@PathVariable String firstName, 
											@PathVariable int salary, 
											TestVO vo) {
		vo.setFirstName(firstName);
		vo.setSalary(Integer.toString(salary));

		return vo;
	}
	
	@RequestMapping("/getTest8")
	@ResponseBody
	/*
	 * 서블릿자리에 적든지 VO앞에 적던지 상관없음.
	 *VO객체로 결과만 받겠다
	 * return 값이 view페이지가 아니고 json타입의 결과를 받아오고자 할 때 사용
	*/
	public List<Map> getTest8() {
		List list = new ArrayList<>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "park");
		map.put("sal", "1000");
		list.add(map);
		
		map.put("name", "song");
		map.put("sal", "2000");
		list.add(map);
		
		return list;
	}
	
}

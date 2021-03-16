package com.company.temp;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.common.BankAPI;
import com.company.temp.service.BankVO;

@Controller
public class BankController {
	
	@Autowired BankAPI bankAPI;
	String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzcwNTMxIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjMxNDExMzEsImp0aSI6IjJhMTRkMTViLWRiOGItNDhiZS05YzI3LTkyM2UzOGI1NTQwOCJ9.sspnz_SxjsgaPi-jVrTRWJ4awpV0-3yIH0DULJTIx0s";

	
	
	
	@RequestMapping("/auth")
	public String auth() {
		String reqURL="https://testapi.openbanking.or.kr/oauth/2.0/authorize_account";
//		String param = "?response_type=code"+
//						"&client_id=5c2bd884-be53-4585-927a-c0bc64a7484f"+
//						"&redirect_uri=http://localhost/callback"+
//						"&scope=login inquiry transfer"+
//						"&state=01234567890123456789012345678932"+
//						"&lang=kor"+
//						"&auth_type=0";
		String response_type = "code";
		String client_id="5c2bd884-be53-4585-927a-c0bc64a7484f";
		String redirect_uri="http://localhost/temp/callback";
		String scope="login inquiry transfer";
		String state = "01234567890123456789012345678932";
		String lang = "kor";
		String auth_type="0";
		
		StringBuilder qstr = new StringBuilder();
		qstr.append("response_type="+response_type)
			.append("&client_id="+client_id)
			.append("&redirect_uri="+redirect_uri)
			.append("&scope="+scope)
			.append("&state="+state)
			.append("&lang"+lang)
			.append("&auth_type="+auth_type);
			
		return "redirect:"+reqURL+"?"+qstr.toString();
			
	}
	
	@RequestMapping("/callback")
	public String callback(@RequestParam Map<String,Object> map,HttpSession session) {
		System.out.println("========== code ======== " +map.get("code"));
		//access_token받기 
		String code=(String) map.get("code");
		
		session.setAttribute("code",code );
		map = bankAPI.getAccessToken(code);
		
		String access_token = (String) map.get("access_token");
		System.out.println("============= access_token ============= "+map.get("access_token"));
		session.setAttribute("access_token", access_token);
		
		return "home";
	}
	
	@RequestMapping("/userInfo")
	public String userInfo(HttpSession session) {
		//String access_token = (String) session.getAttribute("access_token");
		String user_num = "1100770531";
		Map<String,Object> userinfo = bankAPI.getAccountList(access_token, user_num);
		System.out.println(" ================= userinfo ============= " + userinfo );
		return "home";
		
	}
	
	@RequestMapping("/getAccountList")
	public String getAccountList(HttpServletRequest request, Model model) {
		String user_num = "1100770531";
		Map<String, Object> map = bankAPI.getAccountList(access_token, user_num);
		System.out.println("===================== account list ==================== " + map);
		model.addAttribute("list", map);
		return "bank/getAccountList";
	
	}
	
	@RequestMapping("/getBalance")
	public String getBalance(BankVO vo, Model model) {
		vo.setAccess_token(access_token);
		Map<String, Object> map = bankAPI.getBalance(vo);
		System.out.println(" =============잔액조회================= " + map);
		model.addAttribute("balance", map);
		
		return "bank/getBalance";
	
	}
	
	@ResponseBody //return값을 json으로 넘겨주기 위해.
	@RequestMapping("/ajaxGetBalance")
	public Map<String,Object > ajaxGetBalance(BankVO vo) {
		vo.setAccess_token(access_token);
		Map<String, Object> map = bankAPI.getBalance(vo);
		return map;	
	}
	
	@RequestMapping("/getOrgAccessToken")
	public String getOrgAccessToken() {
		Map<String, Object> map = bankAPI.getOrgAccessTokenRestTemplate();
		System.out.println(map);
		System.out.println("access_token : "+ map.get("access_token"));
		return "home";
	}
}

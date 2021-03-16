package com.company.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.company.temp.service.BankVO;
import com.google.gson.Gson;

@Component
public class BankAPI {
	
	
	String host= "https://testapi.openbanking.or.kr";
	String client_id = "5c2bd884-be53-4585-927a-c0bc64a7484f";
	String client_secret = "ccbc83af-9af1-48f5-a461-a3e4b66438c7";
	String redirect_uri="http://localhost/temp/callback";
	String use_org_code = "M202111678";
	String org_access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJNMjAyMTExNjc4Iiwic2NvcGUiOlsib29iIl0sImlzcyI6Imh0dHBzOi8vd3d3Lm9wZW5iYW5raW5nLm9yLmtyIiwiZXhwIjoxNjIzMzA2NDc0LCJqdGkiOiJiMTY0MzMyYi1kMjM3LTQ5ZTQtYTA1NC0zZmI3N2MyZGNjMTEifQ.rxmtg4hhdN-0uxAmq8tE_4WS8eD5DJOyTrAz5O7PMO4";
	
	
	
	public Map<String, Object> getAccessToken (String authorize_code) {
        String reqURL = host + "/oauth/2.0/authorize_token";
        Map<String,Object> map = new HashMap<String, Object>();
        
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            //.getOutputStream() --> 
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(client_id);
            sb.append("&client_secret=").append(client_secret);
            sb.append("&redirect_uri=").append(redirect_uri);
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();
            
            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            Gson gson = new Gson();
            map = gson.fromJson(result, Map.class);
            
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return map;
    }
	
		public Map<String, Object> getAccountList (String access_Token , String user_num) {
	    
	    // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
	    Map<String, Object> map = new HashMap<>();
	    String reqURL = "https://testapi.openbanking.or.kr/v2.0/account/list";
		    StringBuilder qstr = new StringBuilder();
			qstr.append("user_seq_no="+user_num)
				.append("&include_cancel_yn=Y")
				.append("&sort_order=D");
	    try {
	        URL url = new URL(reqURL+"?"+qstr);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        
	        // 요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        

	        
	        // 출력되는 값이 200이면 정상작동
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        
	        //string 값을 map에 담기
	        //map.put("map", result);
	        Gson gson = new Gson();
            map = gson.fromJson(result, HashMap.class);
	        
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    return map;
	}
		
		public String getDate() {
			String str = "";
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
			str = format.format(date);
			return str;
		}
		
		public String getRend9() { //9자리의 난수값 만들기
			long time = System.currentTimeMillis();
			String str = Long.toString(time);
			return str.substring(str.length()-9);
		}
		
		public String getRend32() {
			long time = System.currentTimeMillis();
			String str = Long.toString(time);
			return str.substring(str.length()-9);
		}
		
		
		//잔액조회
		public Map<String, Object> getBalance(BankVO vo){
			 Map<String, Object> map = new HashMap<>();
			 
			// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
			    String reqURL = host + "/v2.0/account/balance/fin_num";
			    String bank_tran_id = use_org_code+"U"+getRend9();
			    
			    StringBuilder qstr = new StringBuilder();
					qstr.append("bank_tran_id="+bank_tran_id)
						.append("&fintech_use_num="+vo.getFintech_use_num())
						.append("&tran_dtime="+getDate());
			    try {
			        URL url = new URL(reqURL+"?"+qstr);
			        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			        conn.setRequestMethod("GET");
			        
			        // 요청에 필요한 Header에 포함될 내용
			        conn.setRequestProperty("Authorization", "Bearer " + vo.getAccess_token());
			        
			        // 출력되는 값이 200이면 정상작동
			        int responseCode = conn.getResponseCode();
			        System.out.println("responseCode : " + responseCode);
			        
			        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			        
			        String line = "";
			        String result = "";
			        
			        while ((line = br.readLine()) != null) {
			            result += line;
			        }
			        System.out.println("response body : " + result);
			        
			        //string 값을 map에 담기
			        //map.put("map", result);
			        Gson gson = new Gson();
		            map = gson.fromJson(result, HashMap.class);
			        
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
			    
			    return map;
		}
		
		//이용기관인증 토큰발급
		public Map<String, Object> getOrgAccessToken () {
	        String reqURL = host + "/oauth/2.0/token";
	        Map<String,Object> map = new HashMap<String, Object>();
	        
	        try {
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	            //헤더에 포함할 값들.
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	            
	            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            //.getOutputStream() --> 
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=client_credentials");
	            sb.append("&client_id=").append(client_id);
	            sb.append("&client_secret=").append(client_secret);
	            sb.append("&scope=").append("oob");
	            bw.write(sb.toString());
	            bw.flush();
	            
	            
	            //    결과 코드가 200이라면 성공
	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode);
	 
	            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";
	            
	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("response body : " + result);
	            
	            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            Gson gson = new Gson();
	            map = gson.fromJson(result, Map.class);
	            
	            br.close();
	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	        
	        return map;
	    }
		
		
		//이용기관인증  토큰발급2 (RestTemplate 활용ver)
			public Map<String, Object> getOrgAccessTokenRestTemplate () {
		        String reqURL = host + "/oauth/2.0/token";
	            
	            MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
	            param.add("client_id", client_id);
	            param.add("client_secret", client_secret);
	            param.add("scope", "oob");
	            param.add("grant_type", "client_credentials");

	            HttpHeaders headers = new HttpHeaders();
	            headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

	            HttpEntity<MultiValueMap<String, String>> request = 
	            		new HttpEntity<MultiValueMap<String, String>>( param, headers);
	            
	            RestTemplate restTemplate = new RestTemplate();
	            Map map = restTemplate.postForObject(
	            		reqURL,
	    		        request,
	    		        Map.class);
	            return map;
			}
		
}

package com.company.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JacksonTest {
	public static void main(String[] args) throws JsonProcessingException {
		
		//jackson : spring json library @ReqeustBody @ResponseBody
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "최준");
		map.put("age", 20);
		
		//자바객체 -> string (json)
		JsonMapper mapper = new JsonMapper();
		String str = mapper.writeValueAsString(map);
		System.out.println(str);
		
		// string(json)을 자바객체로 변환(파싱)
		map= mapper.readValue(str, Map.class);
		System.out.println(map);
	}
}

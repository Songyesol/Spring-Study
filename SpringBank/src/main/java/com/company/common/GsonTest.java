package com.company.common;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class GsonTest {

	public static void main(String[] args) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "홍길동");
		map.put("age", 20);
		
		//자바객체 -> string (json)
		Gson gson = new Gson();
		String str = gson.toJson(map);
		System.out.println(str);

		// string(json)을 자바객체로 변환
		str = "{\"name\":\"홍길동\",\"age\":20 , \"dept\":\"개발\"}";
		map = gson.fromJson(str, Map.class);
		System.out.println(map.get("dept"));
	
	
	
	}

}

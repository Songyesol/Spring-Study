package com.company.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class JsonUtil {
	
	
	/*map값을 넘겼을떄 json값으로 변경해주는 메서드 구현하기
	 *set은 집합개념이라 중복을 허용하지 않고 순서도 없음.
	 *그래서 set을 iterator 해서 나열해야함. 
	**/
	public String toJson(Map<String, Object> map) {
		StringBuilder result= new StringBuilder();
		result.append("{");
			//map개수만큼 for문 돌면서... map의 key값 받아오기
			Iterator<String> keys = map.keySet().iterator();
			Boolean start = true;
			while(keys.hasNext()) {
				String key = keys.next();
				String value= (String )map.get(key);
				
				if(! start) {
					result.append(",");
				} else {
					start = false;
				}
				
				result.append("\"")
						.append(key)
						.append("\"")
						.append(":")
						.append("\"")
						.append(value)
						.append("\"");
				
			}
		result.append("}");
		
		
		return result.toString();
	}
	
	public String objToJson(List<Map<String, Object>> list) {
		StringBuilder result= new StringBuilder();
		result.append("{");
		Boolean start = true;
			for(Map<String, Object> map : list) {
				Iterator<String> keys = map.keySet().iterator();
				while(keys.hasNext()) {
					String key = keys.next();
					String value = (String) map.get(key);

					if(! start) {
						result.append(",");
					} else {
						start = false;
					}
					
					result.append("\"")
					.append(key)
					.append("\"")
					.append(":")
					.append("\"")
					.append(value)
					.append("\"");
				}
			}
		
		result.append("}");
		
		return result.toString();
	}
	
	public String toObjectJson(Object vo) throws Exception, Throwable {
		String result="";
		
		//클래스에 있는 객체명을 다 읽어오는 것  getDeclaredFields()
		Field[] fields =  vo.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			System.out.println(field.getName());
			field.setAccessible(true);
			String key = field.getName();
			String method = "get" + field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
			Method m = vo.getClass().getDeclaredMethod(method, null);
			String value = (String) m.invoke(vo, null);
			System.out.println(key + ":" + value);
			System.out.println(field.getName());
			result = key + ":" + value;
 		}	
		
		return result;
	}
	
	public String toJson(List<Object> vo) {
		String result="";
		
		return result;
	}
	
	
	
	
} //end of class

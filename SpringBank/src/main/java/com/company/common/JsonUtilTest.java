package com.company.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.board.service.NewBoardVO;

public class JsonUtilTest {
	public static void main(String[] args) throws Exception, Throwable {
		JsonUtil jsonUtil = new JsonUtil();
		
		//json에서 봤을떄 VO 와 map은 같은형식. => {"username":"김봉봉","age":30,"dept":"개발"}
		Map<String, Object> map = new HashMap<>();
		map.put("username", "김봉봉");
		map.put("age", "30");
		map.put("dept", "개발");
		
		//map -> json
		String result = jsonUtil.toJson(map);
		System.out.println("map -> json : " + result);
		
		//list -> json
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list.add(map);
		result = jsonUtil.objToJson(list);
		System.out.println("List<Map>  -> json : " + result);
		
		NewBoardVO vo = new NewBoardVO();
		vo.setSeq(6);
		vo.setContent("test");
		vo.setTitle("title test");
		vo.setWriter("송구리");
		result = jsonUtil.toObjectJson(vo);
		System.out.println("VO -> json : " + result);
	}
}

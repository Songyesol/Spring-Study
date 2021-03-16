package com.company.temp.service.impl;

import java.util.List;
import java.util.Map;


public interface EmpMapper {
	
	//넘겨받는 excel view가 map으로 해두었기때문에 map으로 보내는데 한건이 아니라서 list
	public List<Map<String,Object>> getEmpList();
	//일별판매 합계보는 추상메서드
	public List<Map<String, Object>> getDailySaleList();
	//월별판매 합계보는 추상메서드
	public List<Map<String, Object>> getMonthSaleList();
	
}

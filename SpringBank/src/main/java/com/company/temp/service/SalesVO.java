package com.company.temp.service;

import java.util.Date;

import lombok.Data;

@Data
public class SalesVO {
	
	private int sale_seq;
	private Date sale_date;
	private int product_id;
	private int sale_qty;
	private int sale_price;
	private String member_id;
	private String salesdate;
	
}

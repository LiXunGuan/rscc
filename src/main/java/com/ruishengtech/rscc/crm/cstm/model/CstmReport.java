package com.ruishengtech.rscc.crm.cstm.model;


public class CstmReport {

	/**
	 * 该客户池报表统计个数
	 */
	private String count;
	
	/**
	 * 该客户池报表统计时间
	 */
	private String date;
	
	private String name;
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 总计
	 */
	private String allCount;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAllCount() {
		return allCount;
	}

	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}
	
	
}

package com.ruishengtech.rscc.crm.report.condition;

import com.ruishengtech.framework.core.db.condition.Page;


public class ReportBillCondition extends Page{
	
	/**
	 * 计费类型   分机/技能组
	 */
	private String billing_type;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 通话类型  市话/长途
	 */
	private String call_type;
	
	/**
	 * 通话总费用
	 */
	private String call_charge;
	
	/**
	 * 计费时间
	 */
	private String billing_date;
	
	/**
	 * 通话时长
	 */
	private String call_length;
	
	
	/**
	 * 统计开始时间
	 * @return
	 */
	private String Stime;
	
	/**
	 * 统计结束时间
	 * @return
	 */
	private String Etime;
	
	/**
	 * 显示方式
	 */
	private String show_type;
	
	/**
	 * 选择今天数据
	 * @return
	 */
	private String todayData;
	
	

	public String getTodayData() {
		return todayData;
	}

	public void setTodayData(String todayData) {
		this.todayData = todayData;
	}

	public String getBilling_type() {
		return billing_type;
	}

	public void setBilling_type(String billing_type) {
		this.billing_type = billing_type;
	}

	public String getCall_type() {
		return call_type;
	}

	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	public String getCall_charge() {
		return call_charge;
	}

	public void setCall_charge(String call_charge) {
		this.call_charge = call_charge;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBilling_date() {
		return billing_date;
	}

	public void setBilling_date(String billing_date) {
		this.billing_date = billing_date;
	}

	public String getCall_length() {
		return call_length;
	}

	public void setCall_length(String call_length) {
		this.call_length = call_length;
	}

	public String getStime() {
		return Stime;
	}

	public void setStime(String stime) {
		Stime = stime;
	}

	public String getEtime() {
		return Etime;
	}

	public void setEtime(String etime) {
		Etime = etime;
	}

	public String getShow_type() {
		return show_type;
	}

	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	
}

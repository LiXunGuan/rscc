package com.ruishengtech.rscc.crm.report.model;

import java.sql.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name="report_billing")
public class ReportBill {
	
	@Key
	@Column(meta = "SERIAL")
	private Long id;
	
	/**
	 * 计费类型   分机/技能组
	 */
	@Column(meta="VARCHAR(64)",column="billing_type")
	private String billing_type;
	
	/**
	 * 名称
	 */
	@Index
	@Column(meta="VARCHAR(64)",column="name")
	private String name;
	
	/**
	 * 通话类型  市话/长途
	 */
	@Column(meta="VARCHAR(64)",column="call_type")
	private String call_type;
	
	/**
	 * 通话总费用
	 */
	@Index
	@Column(meta="VARCHAR(64)",column="call_charge")
	private String call_charge;
	
	/**
	 * 计费时间
	 */
	@Index
	@Column(meta="VARCHAR(64)",column="billing_date")
	private String billing_date;
	
	
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
	 * 显示当天数据
	 */
	private String todayData;
	
	/**
	 * 显示总费用
	 */
	private String sumcost;
//	/**
//	 * 计费明细id
//	 * @return
//	 */
//	@Column(meta="BIGINT(32)",column="billing_id")
//	private String billing_id;
	
	
	public String getShow_type() {
		return show_type;
	}

	public String getSumcost() {
		return sumcost;
	}

	public void setSumcost(String sumcost) {
		this.sumcost = sumcost;
	}

	public String getTodayData() {
		return todayData;
	}

	public void setTodayData(String todayData) {
		this.todayData = todayData;
	}

	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getBilling_date() {
		return billing_date;
	}

	public void setBilling_date(String billing_date) {
		this.billing_date = billing_date;
	}

//	public String getBilling_id() {
//		return billing_id;
//	}
//
//	public void setBilling_id(String billing_id) {
//		this.billing_id = billing_id;
//	}

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
	
	
}

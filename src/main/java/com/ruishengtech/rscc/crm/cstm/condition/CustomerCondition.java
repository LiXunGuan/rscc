package com.ruishengtech.rscc.crm.cstm.condition;

import java.util.Date;

import com.ruishengtech.framework.core.db.condition.Page;

public class CustomerCondition extends Page{

	/**
	 * 客户名字
	 */
	private String customerName;
	
	/**
	 * 客户编号
	 */
	private String customerId;
	
	/**
	 * 客户归属池编号
	 */
	private String poolId;
	
	/**
	 * 客户归属人编号  无主客户可为空
	 */
	private String ownId;
	
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	
	/**
	 * 客户描述
	 */
	private String customerDes;
	
	/**
	 * 客户进入系统时间
	 */
	private Date startDate;
	
	/**
	 *客户状态 
	 */
	private String status;
	
	/**
	 * 客户标签
	 */
	private String customerTags;
	
	/**
	 * 字号码
	 */
	private String minorNumber;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public String getOwnId() {
		return ownId;
	}

	public void setOwnId(String ownId) {
		this.ownId = ownId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCustomerDes() {
		return customerDes;
	}

	public void setCustomerDes(String customerDes) {
		this.customerDes = customerDes;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerTags() {
		return customerTags;
	}

	public void setCustomerTags(String customerTags) {
		this.customerTags = customerTags;
	}

	public String getMinorNumber() {
		return minorNumber;
	}

	public void setMinorNumber(String minorNumber) {
		this.minorNumber = minorNumber;
	}

}

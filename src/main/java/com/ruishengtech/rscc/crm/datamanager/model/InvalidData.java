package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

public class InvalidData {
	
	//电话号码
	public String phoneNumber;
	
	//批次id
	public String batchUuid;
	
	//号码状态
	public String phoneNumberState;
	
	//操作日期
	public Date maketime;
//	//是否废弃
//	public String isAbandon;
//	
//	//是否黑名单
//	public String isBlacklist;
//	
//	//是否冷冻
//	public String isFrozen;
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Date getMaketime() {
		return maketime;
	}

	public void setMaketime(Date maketime) {
		this.maketime = maketime;
	}

	public String getPhoneNumberState() {
		return phoneNumberState;
	}

	public void setPhoneNumberState(String phoneNumberState) {
		this.phoneNumberState = phoneNumberState;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}

//	public String getIsAbandon() {
//		return isAbandon;
//	}
//
//	public void setIsAbandon(String isAbandon) {
//		this.isAbandon = isAbandon;
//	}
//
//	public String getIsBlacklist() {
//		return isBlacklist;
//	}
//
//	public void setIsBlacklist(String isBlacklist) {
//		this.isBlacklist = isBlacklist;
//	}
//
//	public String getIsFrozen() {
//		return isFrozen;
//	}
//
//	public void setIsFrozen(String isFrozen) {
//		this.isFrozen = isFrozen;
//	}
	
	
}

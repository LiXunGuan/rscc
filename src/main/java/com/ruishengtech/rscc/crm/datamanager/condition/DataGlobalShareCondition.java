package com.ruishengtech.rscc.crm.datamanager.condition;

import java.util.Date;

import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.condition.Page;

public class DataGlobalShareCondition extends Page{

	private String userUuid;
	
	private Integer totalQueryNum;
	
	private Integer queryNum;
	
	//从哪个批次取得数据
    private String batchUuid;

	//号码
	private String phoneNumber;
	
	//数据的其他信息
	private String json;
	
	//当前被哪人占用
	private String ownUser;
	
	//占用时间
	private Date ownUserTimestamp;
	
	//从谁那里移动进来的
	private String transferDepartment;
			
	//从谁那里移动进来的
	@Index
	private String transferUser;
		
	//进池时间
	private Date transferTimestamp;

	private Integer callCount;

	private String lastCallResult;
	
	private Date lastCallTime;
	
	//进池次数
	private Integer enterCount;

	private String intentType;
	
	private Date intentTimestamp;
	
	private String batchName;
	
	private String ownUserName;
	
	private String transferUserName;
	
	private String transferDeptName;

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public Integer getTotalQueryNum() {
		return totalQueryNum;
	}

	public void setTotalQueryNum(Integer totalQueryNum) {
		this.totalQueryNum = totalQueryNum;
	}

	public Integer getQueryNum() {
		return queryNum;
	}

	public void setQueryNum(Integer queryNum) {
		this.queryNum = queryNum;
	}

	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getOwnUser() {
		return ownUser;
	}

	public void setOwnUser(String ownUser) {
		this.ownUser = ownUser;
	}

	public Date getOwnUserTimestamp() {
		return ownUserTimestamp;
	}

	public void setOwnUserTimestamp(Date ownUserTimestamp) {
		this.ownUserTimestamp = ownUserTimestamp;
	}

	public String getTransferDepartment() {
		return transferDepartment;
	}

	public void setTransferDepartment(String transferDepartment) {
		this.transferDepartment = transferDepartment;
	}

	public String getTransferUser() {
		return transferUser;
	}

	public void setTransferUser(String transferUser) {
		this.transferUser = transferUser;
	}

	public Date getTransferTimestamp() {
		return transferTimestamp;
	}

	public void setTransferTimestamp(Date transferTimestamp) {
		this.transferTimestamp = transferTimestamp;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public String getLastCallResult() {
		return lastCallResult;
	}

	public void setLastCallResult(String lastCallResult) {
		this.lastCallResult = lastCallResult;
	}

	public Date getLastCallTime() {
		return lastCallTime;
	}

	public void setLastCallTime(Date lastCallTime) {
		this.lastCallTime = lastCallTime;
	}

	public Integer getEnterCount() {
		return enterCount;
	}

	public void setEnterCount(Integer enterCount) {
		this.enterCount = enterCount;
	}

	public String getIntentType() {
		return intentType;
	}

	public void setIntentType(String intentType) {
		this.intentType = intentType;
	}

	public Date getIntentTimestamp() {
		return intentTimestamp;
	}

	public void setIntentTimestamp(Date intentTimestamp) {
		this.intentTimestamp = intentTimestamp;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getOwnUserName() {
		return ownUserName;
	}

	public void setOwnUserName(String ownUserName) {
		this.ownUserName = ownUserName;
	}

	public String getTransferUserName() {
		return transferUserName;
	}

	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
	}

	public String getTransferDeptName() {
		return transferDeptName;
	}

	public void setTransferDeptName(String transferDeptName) {
		this.transferDeptName = transferDeptName;
	}
	
	
}

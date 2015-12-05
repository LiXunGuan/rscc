package com.ruishengtech.rscc.crm.data.condition;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * @author Wangyao
 *
 */
public class TaskCondition extends Page {

	private String taskTable;
	
	private String itemName;

    private String itemPhone;

    private String itemAddress;
	
	private String itemJson;
	
	private String callTimes;
	
	private Integer callTimesMin;

	private Integer callTimesMax;
	
	private String allocateTime;
	
	private String dataSource;

	public Integer getCallTimesMin() {
		return callTimesMin;
	}

	public void setCallTimesMin(Integer callTimesMin) {
		this.callTimesMin = callTimesMin;
	}

	public Integer getCallTimesMax() {
		return callTimesMax;
	}

	public void setCallTimesMax(Integer callTimesMax) {
		this.callTimesMax = callTimesMax;
	}

	public String getTaskTable() {
		return taskTable;
	}

	public void setTaskTable(String taskTable) {
		this.taskTable = taskTable;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemPhone() {
		return itemPhone;
	}

	public void setItemPhone(String itemPhone) {
		this.itemPhone = itemPhone;
	}

	public String getItemAddress() {
		return itemAddress;
	}

	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	public String getItemJson() {
		return itemJson;
	}

	public void setItemJson(String itemJson) {
		this.itemJson = itemJson;
	}

	public String getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(String callTimes) {
		this.callTimes = callTimes;
	}

	public String getAllocateTime() {
		return allocateTime;
	}

	public void setAllocateTime(String allocateTime) {
		this.allocateTime = allocateTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
}

package com.ruishengtech.rscc.crm.billing.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class BillingCondition extends Page {

	private String type;

	private String caller;
	
	private String exten;

	private String accessNumber;
	
	private String destNumber;
	
	private Integer callMin;
	
	private Integer callMax;
	
	private Float costMin;
	
	private Float costMax;

	private String startTime;
	
	private String endTime;
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getExten() {
		return exten;
	}

	public void setExten(String exten) {
		this.exten = exten;
	}

	public String getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}

	public String getDestNumber() {
		return destNumber;
	}

	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}

	public Integer getCallMin() {
		return callMin;
	}

	public void setCallMin(Integer callMin) {
		this.callMin = callMin;
	}

	public Integer getCallMax() {
		return callMax;
	}

	public void setCallMax(Integer callMax) {
		this.callMax = callMax;
	}

	public Float getCostMin() {
		return costMin;
	}

	public void setCostMin(Float costMin) {
		this.costMin = costMin;
	}

	public Float getCostMax() {
		return costMax;
	}

	public void setCostMax(Float costMax) {
		this.costMax = costMax;
	}
	
}

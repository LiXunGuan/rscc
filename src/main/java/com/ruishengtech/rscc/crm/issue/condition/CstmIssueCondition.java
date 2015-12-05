package com.ruishengtech.rscc.crm.issue.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class CstmIssueCondition extends Page{
	
	private String cstmserviceName;
	
	private String userName;

	private String adminFlag;
	
	private String cstmserviceDescription;
	
	private String cstmserviceAssignee;
	
	private String cstmserviceReporter;
	
	private String cstmserviceId;
	
	private String cstmserviceStatus;
	
	private String levels;
	
	public String getLevels() {
		return this.levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getCstmserviceName() {
		return cstmserviceName;
	}

	public void setCstmserviceName(String cstmserviceName) {
		this.cstmserviceName = cstmserviceName;
	}

	public String getCstmserviceDescription() {
		return cstmserviceDescription;
	}

	public void setCstmserviceDescription(String cstmserviceDescription) {
		this.cstmserviceDescription = cstmserviceDescription;
	}

	public String getCstmserviceAssignee() {
		return cstmserviceAssignee;
	}

	public void setCstmserviceAssignee(String cstmserviceAssignee) {
		this.cstmserviceAssignee = cstmserviceAssignee;
	}

	public String getCstmserviceReporter() {
		return cstmserviceReporter;
	}

	public void setCstmserviceReporter(String cstmserviceReporter) {
		this.cstmserviceReporter = cstmserviceReporter;
	}

	public String getCstmserviceId() {
		return cstmserviceId;
	}

	public void setCstmserviceId(String cstmserviceId) {
		this.cstmserviceId = cstmserviceId;
	}

	public String getCstmserviceStatus() {
		return cstmserviceStatus;
	}

	public void setCstmserviceStatus(String cstmserviceStatus) {
		this.cstmserviceStatus = cstmserviceStatus;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAdminFlag() {
		return this.adminFlag;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}
	
	

}

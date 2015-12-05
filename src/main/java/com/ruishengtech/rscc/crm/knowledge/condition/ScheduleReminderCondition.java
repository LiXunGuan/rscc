package com.ruishengtech.rscc.crm.knowledge.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class ScheduleReminderCondition extends Page{

	private String content;
	
	private String createtimeS;
	
	private String createtimeE;
	
	private String startimeS;
	
	private String startimeE;
	
	private String userUUid;
	
	private String adminflag;
	
	private String level;

	private String pop_event;
	
	private String phone1;
	
	private String phone2;
	

	public String getCreatetimeS() {
		return createtimeS;
	}

	public void setCreatetimeS(String createtimeS) {
		this.createtimeS = createtimeS;
	}

	public String getCreatetimeE() {
		return createtimeE;
	}

	public void setCreatetimeE(String createtimeE) {
		this.createtimeE = createtimeE;
	}

	public String getStartimeS() {
		return startimeS;
	}

	public void setStartimeS(String startimeS) {
		this.startimeS = startimeS;
	}

	public String getStartimeE() {
		return startimeE;
	}

	public void setStartimeE(String startimeE) {
		this.startimeE = startimeE;
	}

	public String getPop_event() {
		return this.pop_event;
	}

	public void setPop_event(String pop_event) {
		this.pop_event = pop_event;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserUUid() {
		return userUUid;
	}

	public void setUserUUid(String userUUid) {
		this.userUUid = userUUid;
	}

	public String getAdminflag() {
		return adminflag;
	}

	public void setAdminflag(String adminflag) {
		this.adminflag = adminflag;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
}

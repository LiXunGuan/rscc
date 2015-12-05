package com.ruishengtech.rscc.crm.knowledge.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class AgentNoticeCondition extends Page{

	/**
	 * 公告标题
	 */
	private String title;
	
	private String userUUid;
	
	/**
	 * 公告发布状态
	 */
	private String publishStatus;
	
	/**
	 * 创建时间段
	 */
	private String sCreateTime;
	
	/**
	 * 创建时间段
	 */
	private String eCreateTime;
	
	/**
	 * 操作对象
	 */
	private String operation;
	
	private String level;
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getsCreateTime() {
		return sCreateTime;
	}

	public void setsCreateTime(String sCreateTime) {
		this.sCreateTime = sCreateTime;
	}

	public String geteCreateTime() {
		return eCreateTime;
	}

	public void seteCreateTime(String eCreateTime) {
		this.eCreateTime = eCreateTime;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserUUid() {
		return userUUid;
	}

	public void setUserUUid(String userUUid) {
		this.userUUid = userUUid;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}

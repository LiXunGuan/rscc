package com.ruishengtech.rscc.crm.knowledge.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "schedule_reminder_user")
public class ScheduleReminderUser extends CommonDbBean{

	/**
	 * 日程的uuid
	 */
	@Column(meta = "VARCHAR(64)", column = "schedule_uuid")
	private String scheduleUUID;
	
	/**
	 * 创建用户的uuid
	 */
	@Column(meta = "VARCHAR(64)", column = "createuser_uuid")
	private String createUserUUID;
	
	/**
	 * 日程创建时间
	 */
	@Column(meta = "DATETIME", column = "createtime")
	private Date createTime;
	
	private String schedulecontent;

	public String getScheduleUUID() {
		return scheduleUUID;
	}

	public void setScheduleUUID(String scheduleUUID) {
		this.scheduleUUID = scheduleUUID;
	}

	public String getCreateUserUUID() {
		return createUserUUID;
	}

	public void setCreateUserUUID(String createUserUUID) {
		this.createUserUUID = createUserUUID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSchedulecontent() {
		return schedulecontent;
	}

	public void setSchedulecontent(String schedulecontent) {
		this.schedulecontent = schedulecontent;
	}
	
}

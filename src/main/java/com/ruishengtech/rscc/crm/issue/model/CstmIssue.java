package com.ruishengtech.rscc.crm.issue.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank 客服
 */
@Table(name = "cstm_cstmservice")
public class CstmIssue extends CommonDbBean {
	
	/**
	 * 打进打出标识
	 */
	public static String CALLIN = "0";
	public static String CALLOUT = "1";
	
	/**
	 * 待解决
	 */
	public static String STATUS0 = "0";
	
	/**
	 * 已经解决
	 */
	public static String STATUS1 = "1";
	
	/**
	 * 
	 */
	public static String STATUS2 = "2";
	
	@SuppressWarnings("serial")
	public static Map<String, String> STATUSMAP = new LinkedHashMap<String, String>(){
		{
			put(STATUS0, "未解决");
			put(STATUS1, "已解决");
		}
	};
	
	/**
	 * cstmservice名字
	 */
	@Column(meta = "TEXT", column = "cstmservice_name")
	private String cstmserviceName;

	/**
	 * 客服编号
	 */
	@Column(meta = "VARCHAR(64)", column = "cstmservice_id")
	private String cstmserviceId;
	
	/**
	 * cstmservice 描述(cstmservice基本信息)
	 */
	@NColumn(meta = "TEXT", column = "cstmservice_description")
	private String cstmserviceDescription;

	/**
	 * cstmservice指派给谁
	 */
	@NColumn(meta = "VARCHAR(64)", column = "cstmservice_assignee")
	private String cstmserviceAssignee;

	/**
	 * cstmservice 标签
	 */
	@NColumn(meta = "text", column = "cstmservice_assignee_note")
	private String cstmserviceAssigneeNote;

	/**
	 * cstmservice发起者
	 */
	@NColumn(meta = "VARCHAR(64)", column = "cstmservice_reporter")
	private String cstmserviceReporter;
	
	/**
	 * cstmservice发起者uuid
	 */
	@NColumn(meta = "VARCHAR(64)", column = "cstmservice_reporter_uuid")
	private String cstmserviceReporterUuid;

	/**
	 * cstmservice语音记录
	 */
	@NColumn(meta = "VARCHAR(64)", column = "cstmservice_voice_record")
	private String cstmserviceVoiceRecord;

	/**
	 * 指派对象
	 */
	@NColumn(meta = "VARCHAR(64)", column = "user_name")
	private String userName;

	
	/**
	 * 电话号码
	 */
	@NColumn(meta = "VARCHAR(64)", column = "phone_number")
	private String phoneNumber;
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * cstmservice 开始时间
	 */
	@Column(meta = "DATETIME", column = "cstmservice_starttime")
	private Date cstmserviceStartTime;

	/**
	 * cstmservice计划结束时间
	 */
	@NColumn(meta = "DATETIME", column = "cstmservice_planendtime")
	private Date cstmservicePlanEndTime;

	/**
	 * cstmservice计划实际结束时间
	 */
	@NColumn(meta = "DATETIME", column = "cstmservice_realendtime")
	private Date cstmserviceRealEndTime;

	/**
	 * cstmservice状态
	 */
	@Column(meta = "VARCHAR(64)", column = "cstmservice_status")
	private String cstmserviceStatus;
	

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

	public String getCstmserviceAssigneeNote() {
		return cstmserviceAssigneeNote;
	}

	public void setCstmserviceAssigneeNote(String cstmserviceAssigneeNote) {
		this.cstmserviceAssigneeNote = cstmserviceAssigneeNote;
	}

	public String getCstmserviceReporter() {
		return cstmserviceReporter;
	}

	public void setCstmserviceReporter(String cstmserviceReporter) {
		this.cstmserviceReporter = cstmserviceReporter;
	}

	public String getCstmserviceVoiceRecord() {
		return cstmserviceVoiceRecord;
	}

	public void setCstmserviceVoiceRecord(String cstmserviceVoiceRecord) {
		this.cstmserviceVoiceRecord = cstmserviceVoiceRecord;
	}

	public Date getCstmserviceStartTime() {
		return cstmserviceStartTime;
	}

	public void setCstmserviceStartTime(Date cstmserviceStartTime) {
		this.cstmserviceStartTime = cstmserviceStartTime;
	}

	public Date getCstmservicePlanEndTime() {
		return cstmservicePlanEndTime;
	}

	public void setCstmservicePlanEndTime(Date cstmservicePlanEndTime) {
		this.cstmservicePlanEndTime = cstmservicePlanEndTime;
	}

	public Date getCstmserviceRealEndTime() {
		return cstmserviceRealEndTime;
	}

	public void setCstmserviceRealEndTime(Date cstmserviceRealEndTime) {
		this.cstmserviceRealEndTime = cstmserviceRealEndTime;
	}

	public String getCstmserviceStatus() {
		return cstmserviceStatus;
	}

	public void setCstmserviceStatus(String cstmserviceStatus) {
		this.cstmserviceStatus = cstmserviceStatus;
	}

	public String getCstmserviceId() {
		return cstmserviceId;
	}

	public void setCstmserviceId(String cstmserviceId) {
		this.cstmserviceId = cstmserviceId;
	}

	public String getCstmserviceReporterUuid() {
		return cstmserviceReporterUuid;
	}

	public void setCstmserviceReporterUuid(String cstmserviceReporterUuid) {
		this.cstmserviceReporterUuid = cstmserviceReporterUuid;
	}

}

/**
 * cstmservice关注者
 */
//	@NColumn(meta = "VARCHAR(64)", column = "cstmservice_watchers")
//	private String cstmserviceWatchers;

//
//	public String getCstmserviceWatchers() {
//		return cstmserviceWatchers;
//	}
//
//	public void setCstmserviceWatchers(String cstmserviceWatchers) {
//		this.cstmserviceWatchers = cstmserviceWatchers;
//	}
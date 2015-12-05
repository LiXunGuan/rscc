package com.ruishengtech.rscc.crm.knowledge.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "schedule_reminder")
public class ScheduleReminder extends CommonDbBean{
	
	/**
	 * 日程提醒内容
	 */
	@Column(meta = "LONGTEXT", column = "schedule_content")
	private String scheduleContent;
	
	/**
	 * 日程开始时间
	 */
	@Column(meta = "DATETIME", column = "schedule_start_time")
	private Date schStartTime;
	
	/**
	 * 日程提醒
	 */
	@Column(meta = "VARCHAR(20)", column = "schedule_remind")
	private String scheduleRemind;
	
	/**
	 * 日程重复
	 */
	@Column(meta = "VARCHAR(20)", column = "schedule_repeat")
	private String scheduleRepeat;

	/**
	 * 日程提醒的创建人
	 */
	@Column(meta = "VARCHAR(64)", column = "schedule_user")
	private String scheduleUser;
	
	/**
	 * 创建时间
	 */
	@Column(meta = "DATETIME", column = "schedule_createtime")
	private Date scheduleCreateTime;
	
	/**
	 * 结束重复
	 */
	@Column(meta = "VARCHAR(64)", column = "schedule_endrepeat")
	private String endRepeat;
	
	/**
	 * 结束重复的时间
	 */
	@NColumn(meta = "DATETIME", column = "schedule_endrepeat_time")
	private Date endRepeatTime;
	
	/**
	 * 结束重复的次数
	 */
	@NColumn(meta = "CHAR(2)", column = "schedule_endrepeat_number")
	private String endRepeatNumber;
	
	/**
	 * 客户姓名
	 */
	private String cstmName;
	
	public String getCstmName() {
		return cstmName;
	}

	public void setCstmName(String cstmName) {
		this.cstmName = cstmName;
	}

	/**
	 * 电话号码
	 */
	@NColumn(meta = "VARCHAR(64)", column = "phone_number")
	private String phoneNumber;

	public static Map<String, String> REMIND = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = -3077378371412617543L;
		{
			put("0", "准时");
			put("-1", "不提醒");
			put("300000", "提前5分钟");
			put("600000", "提前10分钟");
			put("900000", "提前15分钟");
			put("1800000", "提前30分钟");
			put("3600000", "提前1小时");
			put("86400000", "提前1天");
			put("172800000", "提前2天");
			put("604800000", "提前1周");
		}
	};
	
	public static String REPEAT_DISPOSABLE = "disposable";
	
	public static String REPEAT_EVERY_DAY = "everyDay";
	
	public static String REPEAT_WORKING_DAY = "workingDay";
	
	public static String REPEAT_EVERY_WEEK = "everyWeek";
	
	public static String REPEAT_EVERY_MONTH = "everyMonth";
	
	public static String REPEAT_EVERY_YEAR = "everyYear";
	
	public static Map<String, String> REPEAT = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = -8478540627524568771L;
		{
			put(REPEAT_DISPOSABLE, "一次性活动");
			put(REPEAT_EVERY_DAY, "每天");
			put(REPEAT_EVERY_WEEK, "每周");
			put(REPEAT_EVERY_MONTH, "每月");
			put(REPEAT_EVERY_YEAR, "每年");
			put(REPEAT_WORKING_DAY, "每个工作日");
		}
	};
	
	public static String ENDREPEAT_NEVER = "never";
	
	public static String ENDREPEAT_TIME = "time";
	
	public static String ENDREPEAT_NUMBER = "number";
	
	public static Map<String, String> ENDREPEAT = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 4867952116194221613L;

		{
			put(ENDREPEAT_NEVER, "永不");
			put(ENDREPEAT_TIME, "时间");
			put(ENDREPEAT_NUMBER, "次数");
		}
	};
	
	public String getScheduleContent() {
		return scheduleContent;
	}

	public void setScheduleContent(String scheduleContent) {
		this.scheduleContent = scheduleContent;
	}

	public Date getSchStartTime() {
		return schStartTime;
	}

	public void setSchStartTime(Date schStartTime) {
		this.schStartTime = schStartTime;
	}

	public String getScheduleRemind() {
		return scheduleRemind;
	}

	public void setScheduleRemind(String scheduleRemind) {
		this.scheduleRemind = scheduleRemind;
	}

	public String getScheduleRepeat() {
		return scheduleRepeat;
	}

	public void setScheduleRepeat(String scheduleRepeat) {
		this.scheduleRepeat = scheduleRepeat;
	}


	public String getScheduleUser() {
		return scheduleUser;
	}

	public void setScheduleUser(String scheduleUser) {
		this.scheduleUser = scheduleUser;
	}

	public Date getScheduleCreateTime() {
		return scheduleCreateTime;
	}

	public void setScheduleCreateTime(Date scheduleCreateTime) {
		this.scheduleCreateTime = scheduleCreateTime;
	}

	public String getEndRepeat() {
		return endRepeat;
	}

	public void setEndRepeat(String endRepeat) {
		this.endRepeat = endRepeat;
	}

	public Date getEndRepeatTime() {
		return endRepeatTime;
	}

	public void setEndRepeatTime(Date endRepeatTime) {
		this.endRepeatTime = endRepeatTime;
	}

	public String getEndRepeatNumber() {
		return endRepeatNumber;
	}

	public void setEndRepeatNumber(String endRepeatNumber) {
		this.endRepeatNumber = endRepeatNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}

package com.ruishengtech.rscc.crm.knowledge.service;

import java.util.List;

import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminderUser;

public interface ScheduleReminderUserService {

	/**
	 * 保存或修改一个日程用户中间对象
	 * @param scheduleReminder
	 */
	public abstract void saveOrUpdate(ScheduleReminderUser reminderUser);
	
	/**
	 * 根据日程UUID删除日程信息
	 * @param uuid
	 */
	public abstract void deleteByScheduleUUID(String scheduleuuid);
	
	/**
	 * 根据用户UUID删除日程信息
	 * @param uuid
	 */
	public abstract void deleteByUSID(String usreUUID, String scheduleuuid);
	
	/**
	 * 根据日程的uuid得到相关的信息
	 * @param uuid
	 */
	public abstract ScheduleReminderUser getScheduleByUUID(String uuid);
	
	public abstract ScheduleReminderUser getScheduleByScheduleReminderUUID(String uuid);
	
	/**
	 * 根据用户UUID查询信息
	 * @param uuid
	 * @return
	 */
	public abstract List<ScheduleReminderUser> getScheduleReminderUserByUserUUid(String uuid);
}

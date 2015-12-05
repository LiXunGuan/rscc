package com.ruishengtech.rscc.crm.knowledge.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.quartz.SchedulerException;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminder;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminderUser;
import com.ruishengtech.rscc.crm.user.model.User;

public interface ScheduleReminderService {

	/**
	 * 保存或修改一个日程对象
	 * @param scheduleReminder
	 */
	public abstract void saveOrUpdate(ScheduleReminder scheduleReminder);
	
	/**
	 * 根据UUID删除日程信息
	 * @param uuid
	 */
	public abstract void delete(String uuid);
	
	/**
	 * 查询符合条件的日程信息
	 * @param cond
	 * @return
	 */
	public abstract PageResult<ScheduleReminder> queryPage(ICondition cond);
	
	/**
	 * 根据UUID查询日程信息
	 * @param uuid
	 * @return
	 */
	public abstract ScheduleReminder getScheduleRemindersByUUid(String uuid);
	
	/**
	 * 根据useruuid查询日程信息
	 * @param useruuid
	 * @return
	 */
	public abstract List<ScheduleReminder> getScheduleRemindersByUserUUid(String useruuid);
	
	/**
	 * 保存或者是更新
	 * @param request
	 * @param sr
	 * @param uid
	 * @param oldstime
	 * @param user
	 * @param starttime
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	public void saveOrUpdateReminder(HttpServletRequest request,
			ScheduleReminder sr, String uid, String oldstime, User user,
			String starttime) throws ParseException, SchedulerException;
	
	/**
	 * 添加默认的预约提醒
	 * @param phone
	 * @param startTime
	 * @param reminderContent
	 * @return
	 * @throws Exception 
	 */
	public void addDefaultReminder(HttpServletRequest request ,String phone , String oldTime ,  String startTime , String reminderContent) throws Exception;

	/**
	 *  方法调用
	 * @param sr
	 * @param sru
	 * @param user
	 * @param opertaion
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void doTimeTask(ScheduleReminder sr, ScheduleReminderUser sru, User user, String opertaion) throws SchedulerException, ParseException;

	/**
	 * 根据号码查询代办事项信息
	 * @param arg0
	 * @param miniorNumber
	 */
	public abstract ScheduleReminder getReminderByPhone(String arg0, String miniorNumber);
	
}

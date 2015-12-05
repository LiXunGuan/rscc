package com.ruishengtech.rscc.crm.ui.schedulereminder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminder;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminderUser;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderUserService;
import com.ruishengtech.rscc.crm.user.model.User;

public class ScheduleReminderTimerTask implements Job{
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private ScheduleReminderUserService scheduleReminderUserService;
	
	private BrokerService brokerService;
	
	public ScheduleReminderTimerTask(){
		scheduleReminderUserService = ApplicationHelper.getApplicationContext().getBean(ScheduleReminderUserService.class);
		brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//获取参数
		JobDataMap data = context.getJobDetail().getJobDataMap();
		//得到日程信息
		ScheduleReminder sr = (ScheduleReminder)data.get("sr");
		//得到日程用户中间表对象
		ScheduleReminderUser sru = (ScheduleReminderUser)data.get("sru");
		//得到提醒的用户
		User user = (User)data.get("user");
		try {
			sru.setCreateTime(fmt.parse(fmt.format(sru.getCreateTime())));
			scheduleReminderUserService.saveOrUpdate(sru);
//			System.out.println("日程：|"+sr.getScheduleContent()+"| 数据插入成功!!!");
			
			JSONObject  json = new JSONObject();
			json.put("type", "schedule");
			json.put("uuid", sr.getUid());
			json.put("content", sr.getScheduleContent());
			json.put("phoneNumber", sr.getPhoneNumber());
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			brokerService.sendToUser("/user", user.getLoginName(), json.toString());
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

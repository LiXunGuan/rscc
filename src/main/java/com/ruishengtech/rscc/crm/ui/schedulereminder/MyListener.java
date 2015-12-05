package com.ruishengtech.rscc.crm.ui.schedulereminder;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNotice;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminder;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderUserService;

public class MyListener implements SchedulerListener,TriggerListener,JobListener{
	
	private String name;
	
	private ScheduleReminderUserService scheduleReminderUserService;
	
	public MyListener(String name){
		this.name = name;
		scheduleReminderUserService = ApplicationHelper.getApplicationContext().getBean(ScheduleReminderUserService.class);
	}

	@Override
	public void jobAdded(JobDetail arg0) {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void jobDeleted(String arg0, String arg1) {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void jobScheduled(Trigger arg0) {
//		System.out.println("Scheduled执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void jobUnscheduled(String arg0, String arg1) {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void jobsPaused(String arg0, String arg1) {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void jobsResumed(String arg0, String arg1) {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void schedulerError(String arg0, SchedulerException arg1) {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void schedulerInStandbyMode() {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void schedulerShutdown() {
//		1
//		System.out.println("shutdown执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void schedulerShuttingdown() {
//		2
//		System.out.println("shuttingdown执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void schedulerStarted() {
//		3
//		System.out.println("started定时任务执行开始!");
	}

	@Override
	public void triggerFinalized(Trigger tri) {
		StdSchedulerFactory factory = new StdSchedulerFactory();
		JobDataMap data = tri.getJobDataMap();
		String type = (String) data.get("type");
		if("scheduleReminder".equalsIgnoreCase(type)){
			ScheduleReminder sr = (ScheduleReminder)data.get("sr");
			try {
				Scheduler scheduler = factory.getScheduler(sr.getUid());
				if(!scheduler.isShutdown()){
					scheduler.shutdown();
				}
//				scheduleReminderUserService.deleteByUSID(sr.getScheduleUser(), sr.getUid());
//				System.out.println("日程提醒执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}else if("agentNotice".equalsIgnoreCase(type)){
			AgentNotice agentnotice = (AgentNotice)data.get("agentnotice");
			try {
				Scheduler scheduler = factory.getScheduler(agentnotice.getUid());
				if(!scheduler.isShutdown()){
					scheduler.shutdown();
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}else{
			
		}
	}

	@Override
	public void triggersPaused(String arg0, String arg1) {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void triggersResumed(String arg0, String arg1) {
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		// TODO Auto-generated method stub
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		// TODO Auto-generated method stub
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void jobWasExecuted(JobExecutionContext arg0,
			JobExecutionException arg1) {
		// TODO Auto-generated method stub
//		4
//		System.out.println("jobWasExecuted执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void triggerComplete(Trigger arg0, JobExecutionContext arg1, int arg2) {
		// TODO Auto-generated method stub
//		5
//		System.out.println("执行一次的完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
		// TODO Auto-generated method stub
//		System.out.println("triggerFired执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public void triggerMisfired(Trigger arg0) {
		// TODO Auto-generated method stub
//		System.out.println("执行完毕的时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
	}

	@Override
	public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}

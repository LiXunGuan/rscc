package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminder;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminderUser;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderService;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderUserService;
import com.ruishengtech.rscc.crm.knowledge.solution.ScheduleReminderSolution;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.schedulereminder.MyListener;
import com.ruishengtech.rscc.crm.ui.schedulereminder.ScheduleReminderTimerTask;
import com.ruishengtech.rscc.crm.user.model.User;

import freemarker.template.SimpleDate;

@Service
@Transactional
public class ScheduleReminderServiceImp extends BaseService implements ScheduleReminderService{

	@Autowired
	private ScheduleReminderUserService scheduleReminderUserService;
	
	@Override
	public void saveOrUpdate(ScheduleReminder scheduleReminder) {
		if(scheduleReminder == null)
			return;
		if(StringUtils.isBlank(scheduleReminder.getUid())){
			super.save(scheduleReminder);
		}else{
			scheduleReminder.setUuid(UUID.UUIDFromString(scheduleReminder.getUid()));
			super.update(scheduleReminder);
		}
	}

	@Override
	public void delete(String uuid) {
		//查询此日程信息是否有中间表信息
		ScheduleReminderUser sru = scheduleReminderUserService.getScheduleByUUID(uuid);
		if(sru != null){
			scheduleReminderUserService.deleteByScheduleUUID(sru.getScheduleUUID());
		}
		jdbcTemplate.update(" delete from schedule_reminder WHERE uuid  = ? ", uuid);
	}

	@Override
	public PageResult<ScheduleReminder> queryPage(ICondition cond) {
		return super.queryPage(new ScheduleReminderSolution(), cond, ScheduleReminder.class);
	}

	@Override
	public ScheduleReminder getScheduleRemindersByUUid(String uuid) {
		UUID uid = UUID.UUIDFromString(uuid);
		return super.getByUuid(ScheduleReminder.class, uid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScheduleReminder> getScheduleRemindersByUserUUid(final String useruuid) {
		if (StringUtils.isNotBlank(useruuid)) {
			
			List<ScheduleReminder> ru = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM schedule_reminder WHERE schedule_user = ? ");
					params.add(useruuid);
				}
			}, ScheduleReminder.class);
			if (ru.size() > 0) {
				return ru;
			}
		}
		return null;
	}

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
	@Override
	public void saveOrUpdateReminder(HttpServletRequest request,
			ScheduleReminder sr, String uid, String oldstime, User user,
			String starttime) throws ParseException, SchedulerException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(StringUtils.isBlank(uid)){
			// 构建并保存日程信息
			sr.setSchStartTime(fmt.parse(starttime));
			sr.setScheduleCreateTime(fmt.parse(fmt.format((new Date()))));
			sr.setScheduleUser(user.getUid());
			
			if(StringUtils.isBlank(sr.getPhoneNumber())){
				sr.setPhoneNumber(request.getParameter("phone1"));
			}
			
			//保存或修改日程信息
			saveOrUpdate(sr);
			
			// 构建日程用户中间表信息
			ScheduleReminderUser sru = new ScheduleReminderUser();
			sru.setScheduleUUID(sr.getUid());
			sru.setCreateUserUUID(sr.getScheduleUser());
			sru.setCreateTime(fmt.parse(fmt.format(sr.getScheduleCreateTime())));
			
			//判断日程是否需要提醒，0表示不需要提醒，只记录数据
			if(!"-1".equalsIgnoreCase(sr.getScheduleRemind())){
				//调用定时任务
				doTimeTask(sr, sru, user, "");
			}
		}else{
			//修改
			ScheduleReminder sre = getScheduleRemindersByUUid(uid);
			sre.setScheduleContent(sr.getScheduleContent());
			sre.setScheduleRemind(sr.getScheduleRemind());
			sre.setScheduleRepeat(sr.getScheduleRepeat());
			sre.setSchStartTime(fmt.parse(starttime));
			sre.setScheduleUser(user.getUid());
			ScheduleReminderUser sru = null;
			//保存或修改日程信息
			saveOrUpdate(sre);
			if(fmt.parse(starttime).getTime() != fmt.parse(oldstime).getTime()){
				//判断日程是否需要提醒，0表示不需要提醒，只记录数据
				if(!"-1".equalsIgnoreCase(sre.getScheduleRemind())){
					sru = scheduleReminderUserService.getScheduleByScheduleReminderUUID(uid);
					if(sru != null){
						scheduleReminderUserService.deleteByScheduleUUID(uid);
					}
					sru = new ScheduleReminderUser();
					sru.setScheduleUUID(sre.getUid());
					sru.setCreateUserUUID(sre.getScheduleUser());
					sru.setCreateTime(fmt.parse(fmt.format(sre.getScheduleCreateTime())));
					//调用定时任务
					doTimeTask(sre, sru, user, "");
				}
			}
		}
	}
	
	
	/**
	 * 添加默认的预约提醒
	 * @param phone
	 * @param startTime
	 * @param reminderContent
	 * @return
	 * @throws SchedulerException 
	 * @throws ParseException 
	 */
	@Override
	public void addDefaultReminder(HttpServletRequest request , String phone, String oldTime, String startTime, String reminderContent) throws Exception {
		
		ScheduleReminder sr = new ScheduleReminder();
		sr.setEndRepeat("never");
		sr.setScheduleContent(reminderContent);
		sr.setScheduleRemind("0");
		sr.setScheduleRepeat("disposable");
		sr.setPhoneNumber(phone);
		
		String uid = request.getParameter("scheduleId");
		saveOrUpdateReminder(request, sr, uid, oldTime, SessionUtil.getCurrentUser(request), startTime);
	}
	
	@Override
	@SuppressWarnings("static-access")
	public void doTimeTask(ScheduleReminder sr, ScheduleReminderUser sru, User user, String opertaion) throws SchedulerException, ParseException{
		
		// 通过UUID组建job的name和groupName
		String jobName = "jobname" + sr.getUid();
		String jobGroup = "jobgroup" + sr.getUid();
		
		// 创建一个任务(Scheduler)
		StdSchedulerFactory sf = new StdSchedulerFactory(); 
		Properties props = new Properties();// 监听完成时会用到
		props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		props.put("org.quartz.scheduler.instanceName", sr.getUid());
		props.put("org.quartz.threadPool.threadCount", "10");
		sf.initialize(props);
		Scheduler scheduler = sf.getScheduler();
		
		// 添加监听
		MyListener my = new MyListener("listener" + sr.getUid());
		scheduler.addSchedulerListener(my);
 		scheduler.start();
		
		// 删除之前的JobDetail
		scheduler.deleteJob(jobName, jobGroup);
		
		// 重新获取日程的开始和结束时间
		Date stime = sr.getSchStartTime();
		if(!"login".equals(opertaion)){
			// 重新计算并获取定时任务的开始时间
			stime = new Date(stime.getTime() - Long.parseLong(sr.getScheduleRemind()));
		}
		
		int seconds = 0,hours = 0,minutes = 0,month = 0,day = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(stime);
		month = cal.get(cal.MONTH);
		day = cal.get(cal.DATE);
		hours = cal.get(cal.HOUR_OF_DAY);
		minutes = cal.get(cal.MINUTE);
		seconds = cal.get(cal.SECOND);
		
		// 创建一个JobDetail,该Job负责定义需要执行任务,指明任务名，任务组，任务执行类.
		JobDetail job = new JobDetail(jobName, jobGroup, ScheduleReminderTimerTask.class);
		// 存放参数
		job.getJobDataMap().put("sr", sr);
		job.getJobDataMap().put("sru", sru);
		job.getJobDataMap().put("user", user);
		
		// 创建触发器,负责设置调度策略
		Trigger trigger = null;
		
		// 判断重复类型
		if("disposable".equalsIgnoreCase(sr.getScheduleRepeat())){
			// 一次性活动
			trigger = new SimpleTrigger(jobName, jobGroup, stime, null, 0, 0L);
		}else if("everyDay".equalsIgnoreCase(sr.getScheduleRepeat())){
			// 每天
			trigger = new SimpleTrigger(jobName, jobGroup, stime, null, 0, 1*24*60*60*1000);
		}else if("workingDay".equalsIgnoreCase(sr.getScheduleRepeat())){
			// 每个工作日(周一至周五)
			trigger = new CronTrigger(jobName, jobGroup, ""+seconds+" "+minutes+" "+hours+" ? * MON-FRI");
		}else if("everyWeek".equalsIgnoreCase(sr.getScheduleRepeat())){
			// 每周(得到当前时间是周几)
			String week = getWeekOfDate(stime);
			trigger = new CronTrigger(jobName, jobGroup, ""+seconds+" "+minutes+" "+hours+" ? * "+week+"");
		}else if("everyMonth".equalsIgnoreCase(sr.getScheduleRepeat())){
			// 每月
			trigger = new CronTrigger(jobName, jobGroup, ""+seconds+" "+minutes+" "+hours+" "+day+" * ?");
		}else if("everyYear".equalsIgnoreCase(sr.getScheduleRepeat())){
			// 每年
			trigger = new CronTrigger(jobName, jobGroup, ""+seconds+" "+minutes+" "+hours+" "+day+" "+month+" ?"); 
		}else{
			// 自定义
		}
		
		trigger.setStartTime(stime);
		trigger.getJobDataMap().put("type","scheduleReminder");
		trigger.getJobDataMap().put("sr", sr);
		scheduler.scheduleJob(job, trigger);
	}

	private static String getWeekOfDate(Date date) {      
	    String[] weekOfDays = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};        
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null){        
	         calendar.setTime(date);      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w < 0){        
	        w = 0;      
	    }      
	    return weekOfDays[w];    
	}

	/**
	 * 根据号码查询代办事项信息
	 * @param arg0
	 * @param miniorNumber
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ScheduleReminder getReminderByPhone(final String arg0, final String miniorNumber) {

		List<ScheduleReminder> list = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {

				sql.append("SELECT * FROM `schedule_reminder` WHERE phone_number = ? ");
				params.add(arg0);
				if(StringUtils.isNotBlank(miniorNumber)){
					
					sql.append(" OR phone_number  = ? ");
					params.add(miniorNumber);
				}
			}
		}, ScheduleReminder.class);
		
		if(list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}
	

}

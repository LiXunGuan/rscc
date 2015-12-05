package com.ruishengtech.rscc.crm.ui.schedulereminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.knowledge.condition.ScheduleReminderCondition;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminder;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminderUser;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderService;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderUserService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Controller
@RequestMapping("schedulereminder")
public class ScheduleReminderController{
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ScheduleReminderService scheduleReminderService;
	
	@Autowired
	private BrokerService brokerService;
	
	@Autowired
	private ScheduleReminderUserService scheduleReminderUserService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping
	public String index(HttpServletRequest request,Model model){
		
		//弹屏如果有电话号码
		if(StringUtils.isNotBlank(request.getParameter("phone1"))){
			
			model.addAttribute("phone1", request.getParameter("phone1"));
		}
		//弹屏如果有电话号码
		if(StringUtils.isNotBlank(request.getParameter("phone2"))){
			
			model.addAttribute("phone2", request.getParameter("phone2"));
		}
		
		
		if("pop".equals(request.getParameter("level"))){
			model.addAttribute("pageLength", 5);
		}
		if("agent".equals(request.getParameter("level"))){
			model.addAttribute("agent", "我的预约");
			request.getSession().setAttribute("level", "agent");
		}else{
			model.addAttribute("agent", "预约管理");
			request.getSession().removeAttribute("level");
		}
		
		if(!"pop".equals(request.getParameter("level"))){
			
			model.addAttribute("iframecontent","schedulereminder/schedulereminder_index");
			return "iframe";
		}
		
		return "schedulereminder/schedulereminder_index";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request, HttpServletResponse response, ScheduleReminderCondition scheduleReminderCondition) throws SchedulerException{
		//获取当前登录用户
		User user = (User) SessionUtil.getCurrentUser(request);
		
		scheduleReminderCondition.setRequest(request);
		scheduleReminderCondition.setAdminflag(user.getAdminFlag());
		scheduleReminderCondition.setUserUUid(user.getUid());
		if(!"pop".equals(scheduleReminderCondition.getLevel())){
			scheduleReminderCondition.setLevel(request.getSession().getAttribute("level") != null ?request.getSession().getAttribute("level").toString(): "");
		}
		PageResult<ScheduleReminder> pageResult =  scheduleReminderService.queryPage(scheduleReminderCondition);
		
		List<ScheduleReminder> reminders = pageResult.getRet();
		
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String, String> reminds = ScheduleReminder.REMIND;
		Map<String, String> repeats = ScheduleReminder.REPEAT;
		
		for (int i = 0; i < reminders.size(); i++) {
			JSONObject json = new JSONObject(reminders.get(i));
			if(reminders.get(i).getScheduleContent().length() > 20){
				reminders.get(i).setScheduleContent(reminders.get(i).getScheduleContent().substring(0, 15)+"....");
			}
			json.put("scheduleContent", StringUtils.trimToEmpty(reminders.get(i).getScheduleContent()));
			json.put("scheduleTime", StringUtils.trimToEmpty(fmt.format(reminders.get(i).getSchStartTime())));
			/* 提醒  */
			String remind = StringUtils.trimToEmpty(reminders.get(i).getScheduleRemind());
			json.put("scheduleRemind", reminds.containsKey(remind) ? reminds.get(remind) : remind);
			/* 重复  */
			String repeat = StringUtils.trimToEmpty(reminders.get(i).getScheduleRepeat());
			json.put("scheduleRepeat", repeats.containsKey(repeat) ? repeats.get(repeat) : repeat);
			User us = userService.getByUuid(UUID.UUIDFromString(reminders.get(i).getScheduleUser()));
			json.put("scheduleUser", us.getLoginName());
			json.put("scheduleCreateTime", StringUtils.trimToEmpty(fmt.format(reminders.get(i).getScheduleCreateTime())));
			array.put(json);
		}
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	@RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest request, ScheduleReminder sr, BindingResult bindingResult, String uid, String oldstime,String phone1) throws ParseException, SchedulerException {
		// 获取当前登陆用户信息
		User user = (User) SessionUtil.getCurrentUser(request);
		// 获取开始和结束时间
		String starttime = request.getParameter("schStartTime");
		// 保存日程相关信息
		scheduleReminderService.saveOrUpdateReminder(request, sr, uid, oldstime, user, starttime);
        return new JSONObject().put("success", true).toString();
    }
	
	
	
	@RequestMapping("get")
    public String getData(HttpServletRequest request, HttpServletResponse response, String uuid, String operation,String phone1, Model model){
		if(uuid != null){
			ScheduleReminder scheduleReminder = scheduleReminderService.getScheduleRemindersByUUid(uuid);
			model.addAttribute("item", scheduleReminder);
			if("look".equals(operation)){
				model.addAttribute("operation", "look");
				ScheduleReminderUser sru = scheduleReminderUserService.getScheduleByScheduleReminderUUID(uuid);
				if(sru != null){
					scheduleReminderUserService.deleteByScheduleUUID(uuid);
				}
			}
			if("clickContent".equals(operation)){
				model.addAttribute("operation", "clickContent");
			}
		}
		//得到所有提醒信息
		model.addAttribute("reminds", ScheduleReminder.REMIND);
		//得到所有重复信息
		if(StringUtils.isNotBlank(phone1)){
			model.addAttribute("phone1", phone1);
		}
		model.addAttribute("repeats", ScheduleReminder.REPEAT);
		//得到结束重复信息
		model.addAttribute("endrepeats", ScheduleReminder.ENDREPEAT);
		return "schedulereminder/schedulereminder_save";
    }
	
	@RequestMapping("deleteScheduleUserByUUid")
    @ResponseBody
    public void deleteScheduleUserByUUid(HttpServletRequest request,String uuid){
		ScheduleReminderUser sru = scheduleReminderUserService.getScheduleByScheduleReminderUUID(uuid);
		if(sru != null){
			scheduleReminderUserService.deleteByScheduleUUID(uuid);
		}
	}
	
	
	@RequestMapping("remove")
    @ResponseBody
    public String remove(HttpServletRequest request,String uuid) {
		//清除定时任务
		ScheduleReminder sr = scheduleReminderService.getScheduleRemindersByUUid(uuid);
		//通过UUID组建job的name和groupName
		String jobName = "jobname" + sr.getUid();
		String jobGroup = "jobgroup" + sr.getUid();
		StdSchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler scheduler = sf.getScheduler(sr.getUid());
			if(scheduler != null){
				scheduler.deleteJob(jobName, jobGroup);
				if(!scheduler.isShutdown()){
					scheduler.shutdown();
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		//删除数据库内容
		scheduleReminderService.delete(uuid);
        return new JSONObject().put("success", true).toString();
    }
	
	@RequestMapping("getScheduleReminders")
	@ResponseBody
    public String getScheduleReminders(HttpServletRequest request) throws InterruptedException{
		//获取当前登陆用户信息
		User user = (User) SessionUtil.getCurrentUser(request);
		//查询登陆用户的日程提醒
		List<ScheduleReminderUser> srs = scheduleReminderUserService.getScheduleReminderUserByUserUUid(user.getUid());
		if(srs != null){
			return new JSONObject().put("success", true).put("srs", srs).toString();
		}
		return new JSONObject().put("success",false).toString();
		
	}

}

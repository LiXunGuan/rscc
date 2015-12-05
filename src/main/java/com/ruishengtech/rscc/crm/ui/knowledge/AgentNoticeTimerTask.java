package com.ruishengtech.rscc.crm.ui.knowledge;

import java.io.IOException;

import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNotice;
import com.ruishengtech.rscc.crm.knowledge.service.AgentNoticeService;

public class AgentNoticeTimerTask implements Job{

	private BrokerService brokerService;
	
	private AgentNoticeService agentNoticeService;

	public AgentNoticeTimerTask(){
		brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
		agentNoticeService = ApplicationHelper.getApplicationContext().getBean(AgentNoticeService.class);
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//获取参数
		JobDataMap data = context.getJobDetail().getJobDataMap();
		//得到坐席公告信息
		AgentNotice agentNotice  = (AgentNotice)data.get("agentnotice");
		//修改状态
		agentNotice.setPublishStatus("1");
		agentNoticeService.saveOrUpdate(agentNotice);
		//用户信息
		String[] users = (String[]) data.get("users");
		
		JSONObject  json = new JSONObject();
		json.put("type", "notice");
		json.put("content", agentNotice.getNoticeTitle());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			brokerService.sendToUsers("/user", json.toString(), users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package com.ruishengtech.rscc.crm.ui.mw.event.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.ui.mw.event.IEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;

/**
 * Created by yaoliceng on 2015/6/17.
 */
public class MissCallEvent implements IEventHandler {

	private static Map<String,Object> MissCallMap = new HashMap<String,Object>();
	
	public static Map<String, Object> getMissCallMap() {
		return MissCallMap;
	}

	private static final Logger logger = Logger.getLogger(MissCallEvent.class);
	/***
	 * 
	 * {"timestamp":"2015-06-18 11:43:50","event":"miss_call_event","access_number":"60172032","call_session_uuid":"38985c10-156c-11e5-bd26-b937065c2a95","agent_id":"cx","agent_exten":"3001","agent_info":"1111"}
	 * 
	 * 
	 * 
	 * 
	 * {"timestamp":"2015-06-18 17:47:55","queue_name":"999999","event":"miss_call_event",
	 * "access_number":"60172032","call_session_uuid":"153048b8-159f-11e5-be9a-b937065c2a95",
	 * "queue_exten":"999999"}
	 *
	 * {"timestamp":"2015-08-28 17:08:29",
	 * 		"event":"miss_call_event",
	 * 		"access_number":"60172062",
	 * 		"call_session_uuid":"51aaf2b4-4d64-11e5-bdeb-dfd01e3c05f0",
	 * 		"agent_id":"new",
	 * 		"caller_id_number":"13044125730",
	 * 		"agent_exten":"8041",
	 * 		"agent_info":"new"}
	 *
	 */
	@Override
    public void handler(JSONObject jsonObject) {
    	
    	BrokerService brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
    	DatarangeService datarangeService = ApplicationHelper.getApplicationContext().getBean(DatarangeService.class);
    	
    	logger.info("收到漏话消息！");
    	if(jsonObject.has("agent_id")){
    		Integer count = 1;
    		String agentid = jsonObject.getString("agent_id");
    		if(MissCallMap.containsKey(agentid)){
    			count = Integer.parseInt(MissCallMap.get(agentid).toString());
    			count ++;
    		}
    		//存放漏话
    		MissCallMap.put(agentid, count);
    		//向页面推送信息
    		try {
    			JSONObject  json = new JSONObject();
    			json.put("type", "missedcall");
    			json.put("content", count);
				brokerService.sendToUser("/user", agentid, json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else{
    		
    		//所有在线用户
    		Set<String> userSet = brokerService.getOnlineUsers("/user");
    		Map<String, Integer> queueMap =  QueueManager.getQueueExtenMap();
    		if (StringUtils.isBlank(jsonObject.optString("queue_exten"))) {
    			return;
    		}
    		List<User> userList = datarangeService.getUsers(queueMap.get(jsonObject.get("queue_exten")).toString());
    		String[] users = new String[userList.size()];
    		int i = 0 ;
    		for (User user : userList) {
				if(userSet.contains(user.getLoginName())){
					users[i++] = user.getLoginName().toString();
				}
			}
    		if(users != null){
    			//存放漏话
    			for (int j = 0; j < users.length; j++) {
    				Integer count = 1;
    				if(MissCallMap.containsKey(users[j])){
    					count = Integer.parseInt(MissCallMap.get(users[j]).toString());
    					count ++;
    				}
    				MissCallMap.put(users[j], count);
    				//推送漏话信息
    				try {
    					JSONObject  json = new JSONObject();
    					json.put("type", "missedcall");
    					json.put("content", count);
    					brokerService.sendToUser("/user", users[j], json.toString());
    					logger.info("推送给  "+users[j]+" 的漏话消息为： "+json.toString()+"");
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
				}
    		}
    	}
    }

}

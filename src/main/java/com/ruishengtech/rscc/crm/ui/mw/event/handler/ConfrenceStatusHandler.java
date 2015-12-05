package com.ruishengtech.rscc.crm.ui.mw.event.handler;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.ui.mw.event.IEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.model.Confrence;
import com.ruishengtech.rscc.crm.ui.mw.send.AgentManager;
import com.ruishengtech.rscc.crm.ui.mw.send.ConfrenceManager;

/**
 * Created by yaoliceng on 2015/6/16.
 */

//关闭有两种可能：1、会议室没有成员时，自动关闭，此时只有事件
//2、手动点击关闭会议室，踢出所有成员并关闭，即能收到踢人的事件（因为一次性踢完，所以只能收到一次关闭），也能收到关闭的事件
//人员退出有两种可能：1、手动踢出
//2、对方挂断
//两个都可以收到事件，但是在点击中也做了移除处理，注意空指针问题，或者干脆去掉手动点击时的移除操作，统一交给事件来做
public class ConfrenceStatusHandler implements IEventHandler {
    
	private static final Logger logger = Logger.getLogger(ConfrenceStatusHandler.class);
	@Override
    public void handler(JSONObject jsonObject) {
		JSONObject json = new JSONObject();
		json.put("type", "confrence_stat");
		BrokerService brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
		
		if("end".equals(jsonObject.optString("end"))){
			Confrence con = ConfrenceManager.confrenceMap.remove(jsonObject.getString("conference_exten"));
			if(con!=null){
				ConfrenceManager.extenMap.remove(con.getOwner());
				json.put("stat_type", "confrence_change");
				json.put("stat", "close");
				try {
					brokerService.sendToUser("/user",AgentManager.agentMap.get(con.getOwner()),json.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("details");
		if(jsonArray.length() == 0){
			Confrence con = ConfrenceManager.confrenceMap.remove(jsonObject.getString("conference_exten"));
			if(con != null){
				ConfrenceManager.extenMap.remove(con.getOwner());
				json.put("stat_type", "confrence_change");
				json.put("stat", "close");
				try {
					brokerService.sendToUser("/user",AgentManager.agentMap.get(con.getOwner()),json.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		JSONObject obj = null;
		Confrence con = ConfrenceManager.confrenceMap.get(jsonObject.optString("conference_exten"));
		con.clear();
		for (int i = 0;i < jsonArray.length();i++){
			obj = jsonArray.getJSONObject(i);
			if("".equals(obj.optString("extension")))
				continue;
			JSONObject jsonObj = con.get(obj.optString("extension"));
			if (jsonObj != null) {
				obj.put("accessNumber", jsonObj.optString("accessNumber", null));
			}
			con.add(obj.optString("extension"), obj);
		}
		json.put("stat_type", jsonObject.optString("member_change"));
    	try {
    		brokerService.sendToUser("/user",AgentManager.agentMap.get(con.getOwner()),json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}

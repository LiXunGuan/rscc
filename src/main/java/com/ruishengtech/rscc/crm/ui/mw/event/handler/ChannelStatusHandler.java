package com.ruishengtech.rscc.crm.ui.mw.event.handler;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.ui.mw.event.IEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.send.AgentManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

/**
 * Created by yaoliceng on 2015/6/16.
 */
public class ChannelStatusHandler implements IEventHandler {
	
	//日志记录器
	private static final Logger logger = Logger.getLogger(ChannelStatusHandler.class);
	
	@Override
    public void handler(JSONObject jsonObject) {
    	String agentID = StringUtils.isBlank(jsonObject.optString("agent_id"))?AgentManager.agentMap.get(jsonObject.optString("exten")):jsonObject.optString("agent_id");
    	
    	//agent_id为空是对方摘机，也可能是自己点了软电话的忽略，不做处理
    	if(StringUtils.isBlank(agentID))
    		return;
    	SysConfigService configService = ApplicationHelper.getApplicationContext().getBean(SysConfigService.class);
    	BrokerService brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
    	String autoBusy = configService.getSysConfigByKey("autoBusy").getSysVal();
    	
    	try {
    		
    		jsonObject.put("type", "stat_change");
			jsonObject.put("number", PhoneUtil.getNumberPhone(jsonObject.optString("number")));
    		jsonObject.put("agent_id", agentID);
    		jsonObject.put("exten", jsonObject.optString("exten"));
    		jsonObject.put("exten_status", jsonObject.optString("exten_status"));
    		jsonObject.put("pause_status", jsonObject.optString("pause_status"));
    		jsonObject.put("openwindow", !"dataCall-queue".equals(jsonObject.optString("user_field")));
    		jsonObject.put("timestamp", jsonObject.optString("timestamp"));
    		jsonObject.put("call_session_uuid", jsonObject.optString("call_session_uuid"));
    		
    		//存放是否自动置忙
    		jsonObject.put("autoBusy", autoBusy);
    		
    		boolean f = brokerService.sendToUser("/user", agentID, jsonObject.toString());
    		logger.info("给 "+agentID+" 推送webSocket消息为： "+ jsonObject.toString() +" 结果为： "+f);
    		
    		
    		
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}

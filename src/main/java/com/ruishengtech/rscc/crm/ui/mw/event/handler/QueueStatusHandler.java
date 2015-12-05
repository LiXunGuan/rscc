package com.ruishengtech.rscc.crm.ui.mw.event.handler;

import java.io.IOException;

import org.json.JSONObject;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.ui.mw.event.IEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;

/**
 * Created by yaoliceng on 2015/6/16.
 */
public class QueueStatusHandler implements IEventHandler {

	@Override
    public void handler(JSONObject jsonObject) {
		QueueManager.queueMap.put(jsonObject.getString("queue_id"), jsonObject.getInt("count"));
		BrokerService brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
		jsonObject.put("type", jsonObject.getString("event"));
		try {
			brokerService.send("/user", jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(jsonObject.toString());
    }

}

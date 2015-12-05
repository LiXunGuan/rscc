package com.ruishengtech.rscc.crm.ui.mw.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.rscc.crm.ui.mw.event.handler.CdrHandler;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.ChannelStatusHandler;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.ConfrenceStatusHandler;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.MissCallEvent;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.PauseStatusHandler;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.PopEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.QueueStatusHandler;

/**
 * Created by yaoliceng on 2015/6/16.
 */
@Controller
@RequestMapping("event")
public class EventController {

	private static final Logger logger = Logger.getLogger(EventController.class);
	
    Map<String,IEventHandler> eventHandlerMap = new HashMap<String,IEventHandler>(){
		private static final long serialVersionUID = 1L;

		{
            // 通话的channel的状态推送
            put("channel_status_event",new ChannelStatusHandler());
            put("agent_status_event",new ChannelStatusHandler());

            //多方会议
            put("confrence_status_event",new ConfrenceStatusHandler());
            
            //cdr
            put("cdr_event",new CdrHandler());

            //弹屏
            put("pop_event",new PopEventHandler());

            //漏话
            put("miss_call_event",new MissCallEvent());

            put("agent_pause_status_event",new PauseStatusHandler());
            
            put("queue_status_event",new QueueStatusHandler());
            
        }
	};
    
    @RequestMapping
    @ResponseBody
    public String event(HttpServletRequest request) throws IOException {

        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"UTF-8");
        BufferedReader br = new BufferedReader(isr);

        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        JSONObject jsonObject = null;
        try {
        	jsonObject = new JSONObject(sb.toString());
        } catch (JSONException e) {
            return "";
        }
 
        IEventHandler handler = eventHandlerMap.get(jsonObject.getString("event"));
        logger.info("分发信息为："+jsonObject.toString()+"---"+jsonObject.getString("event"));
    	if(handler!=null){
    		handler.handler(jsonObject);
    	}
        return "";
    }
}

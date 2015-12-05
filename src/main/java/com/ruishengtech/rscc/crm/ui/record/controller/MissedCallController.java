package com.ruishengtech.rscc.crm.ui.record.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.websocket.service.BrokerService;

@Controller
@RequestMapping("missedcall")
public class MissedCallController {
	
	@Autowired
	private BrokerService brokerService;
	
	public void doMissedCall(String msg) throws IOException{
		JSONObject  json = new JSONObject();
		json.put("type", "missedcall");
		json.put("content", msg);
		brokerService.sendToUser("/user", "admin", json.toString());
	}
	
	@RequestMapping("miss")
    @ResponseBody
    public String getMissedCalls(HttpServletRequest request) throws IOException{
		String msg = "有漏话信息了！";
		doMissedCall(msg);
		return new JSONObject().put("success", true).toString();
	}
	
}

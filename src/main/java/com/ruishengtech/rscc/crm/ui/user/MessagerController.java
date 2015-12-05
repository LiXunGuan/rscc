package com.ruishengtech.rscc.crm.ui.user;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.websocket.service.BrokerService;

@Controller
@RequestMapping("user/messager")
public class MessagerController {
	@Autowired
	private BrokerService brokerService;

	@RequestMapping("group")
	@ResponseBody
	public String getGroups(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Set<String> s = brokerService.getUrls();
		JSONArray jsonArray = new JSONArray(s);
		return jsonArray.toString();
	}
	
	@RequestMapping("user")
	@ResponseBody
	public String getUser(HttpServletRequest request,
			HttpServletResponse response, String group,
			Model model) {
		Set<String> s = brokerService.getOnlineUsers(group);
		JSONArray jsonArray = new JSONArray(s);
		return jsonArray.toString();
	}
	
}

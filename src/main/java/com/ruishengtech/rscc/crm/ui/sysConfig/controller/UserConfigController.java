package com.ruishengtech.rscc.crm.ui.sysConfig.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

@Controller
@RequestMapping("userconfig")
public class UserConfigController {
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private BrokerService brokerService;
	
	@RequestMapping
	public String index(HttpServletRequest request,HttpServletResponse response,Model model){
		List<DataIntent> dis = dataIntentService.getAll();
		model.addAttribute("dis", dis);
		
		Map<String, SysConfig> data = SysConfigManager.getInstance().getDataMap();
		model.addAttribute("data", data); 
		
		model.addAttribute("iframecontent","sysconfig/otherconfig");
		return "iframe";
		
//		return "sysconfig/otherconfig";
	}
	
	@RequestMapping("saveconfig")
	@ResponseBody
	public String saveconfig(HttpServletRequest request) throws ParseException, JSONException, IOException {
		Enumeration<String> pns = request.getParameterNames();
		String[] c= null;
		while(pns.hasMoreElements()){
			String k  = pns.nextElement().replace("_", ".");
			c = k.substring(1, k.indexOf("}")).split(",");
		}
		SysConfig sys = null;
		for(String config : c){
			String key = config.split(":")[0].substring(1,config.split(":")[0].lastIndexOf('"'));
			String val = config.split(":")[1];
			sys = sysConfigService.getSysConfigByKey(key);
			if(key.equals("sys.billing") || key.equals("hiddenPhoneNumber") || key.equals("sys.data.distinct")){
				sys.setSysVal(val);
			}else{
				sys.setSysVal(val.substring(1,val.lastIndexOf('"')));
			}
			sysConfigService.saveOrUpdate(sys);
		}
		return new JSONObject().put("success", true).toString();
	}
}

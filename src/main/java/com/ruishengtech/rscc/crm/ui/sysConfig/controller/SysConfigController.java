package com.ruishengtech.rscc.crm.ui.sysConfig.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;


@Controller
@RequestMapping("sysconfig")
public class SysConfigController {
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private BrokerService brokerService;
	
	@RequestMapping
	public String index(Model model){
		Map<String, SysConfig> data = SysConfigManager.getInstance().getDataMap();
		model.addAttribute("data", data); 
		
		model.addAttribute("iframecontent","sysconfig/sysconfig_index");
		return "iframe";
		
//		return "sysconfig/sysconfig_index";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request,String key,String value) throws ParseException, JSONException, IOException {
		SysConfig sys = null;
		if(key.equals("companyinfo")){
			sysConfigService.updatSysConfig(!StringUtils.isNotBlank(value)?"":value.substring(value.indexOf("name:")+5,value.indexOf("add:")),"companyname");
			sysConfigService.updatSysConfig(!StringUtils.isNotBlank(value)?"":value.substring(value.indexOf("add:")+4,value.indexOf("phone:")),"companyadd");
			sysConfigService.updatSysConfig(!StringUtils.isNotBlank(value)?"":value.substring(value.indexOf("phone:")+6,value.length()),"companyphone");
		}else{
			sys = sysConfigService.getSysConfigByKey(key);
			sys.setSysVal(value);
			sysConfigService.saveOrUpdate(sys);
		}
		return new JSONObject().put("success", true).toString();
	}
	
}

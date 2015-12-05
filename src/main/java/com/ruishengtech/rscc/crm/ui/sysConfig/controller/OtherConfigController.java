package com.ruishengtech.rscc.crm.ui.sysConfig.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.ui.mw.send.BindExtenManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

@Controller
@RequestMapping("otherconfig")
public class OtherConfigController {
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@Autowired
	private CustomerPoolService customerPoolService;
	
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private BrokerService brokerService;
	
	@RequestMapping
	public String index(HttpServletRequest request,HttpServletResponse response,Model model){
		//获取已经存在的所有意向客户类型
		List<DataIntent> dis = dataIntentService.getAll();
		model.addAttribute("dis", dis);
		
		//获取已经存在的所有的成交客户类型
		List<CustomerPool> cps =  customerPoolService.getAllPool();
		model.addAttribute("cps", cps);
		
		Map<String, SysConfig> data = SysConfigManager.getInstance().getDataMap();
		model.addAttribute("data", data);
		
		com.ruishengtech.rscc.crm.ui.mw.model.SysConfig sysConfig=sysConfigService.getSysConfig("agentbusy");
	 	   
	 	   if (sysConfig!=null) {
				getSysConfig(model, sysConfig);
			}else{
				com.ruishengtech.rscc.crm.ui.mw.model.SysConfig config=new com.ruishengtech.rscc.crm.ui.mw.model.SysConfig();
				JSONObject jsob = new JSONObject();
				jsob.put("name_1","小休！");
				jsob.put("state_1","1");
				jsob.put("name_2","午餐！");
				jsob.put("state_2","1");
				jsob.put("name_3","晚餐！");
				jsob.put("state_3","1");
				jsob.put("name_4","洗手间！");
				jsob.put("state_4","1");
				jsob.put("name_5","其他！");
				jsob.put("state_5","1");
				jsob.put("name_6","自定义！");
				jsob.put("state_6","0");
//				jsob.put("name_7","就是不接电话！");
//				jsob.put("state_7","0");
//				jsob.put("name_8","点错了！");
//				jsob.put("state_8","0");
//				jsob.put("name_9","找老板喝茶！");
//				jsob.put("state_9","0");
//				jsob.put("name_10","还有什么呢！");
//				jsob.put("state_10","0");
				config.setVal(jsob.toString());
				config.setName("agentbusy");
				
				sysConfigService.saveOrUpdate(config);
				getSysConfig(model, config);
			}
		
	 	model.addAttribute("iframecontent","sysconfig/otherconfig");
		return "iframe";
//		return "sysconfig/otherconfig";
	}
	
	@RequestMapping("saveconfig")
	@ResponseBody
	public String saveconfig(HttpServletRequest request,SysConfig sysConfig) throws ParseException, JSONException, IOException {
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
			if(key.equals("sys.billing") || key.equals("hiddenPhoneNumber") || key.equals("autoBusy")){
				//直接保存为 true/false
				sys.setSysVal(val);
			}else{
				sys.setSysVal(val.substring(1,val.lastIndexOf('"')));
				if("sys.call.bindexten".equals(key)){
					BindExtenManager.canCallWithBinder("1".equals(sys.getSysVal()) ? "true" : "false");
				}
			}
			sysConfigService.saveOrUpdate(sys);
		}
		
		
		return new JSONObject().put("success", true).toString();
	}
	
	public static void getSysConfig(Model model, com.ruishengtech.rscc.crm.ui.mw.model.SysConfig sysConfig) {
			JSONObject jsob = new JSONObject(sysConfig.getVal());
			model.addAttribute("id", sysConfig.getId());
	   		model.addAttribute("name", sysConfig.getName());
   		model.addAttribute("name_1", jsob.optString("name_1", ""));
   		model.addAttribute("state_1", jsob.optString("state_1", ""));
   		model.addAttribute("name_2", jsob.optString("name_2", ""));
   		model.addAttribute("state_2", jsob.optString("state_2", ""));
   		model.addAttribute("name_3", jsob.optString("name_3", ""));
   		model.addAttribute("state_3", jsob.optString("state_3", ""));
   		model.addAttribute("name_4", jsob.optString("name_4", ""));
   		model.addAttribute("state_4", jsob.optString("state_4", ""));
   		model.addAttribute("name_5", jsob.optString("name_5", ""));
   		model.addAttribute("state_5", jsob.optString("state_5", ""));
   		model.addAttribute("name_6", jsob.optString("name_6", ""));
   		model.addAttribute("state_6", jsob.optString("state_6", ""));
//   		model.addAttribute("name_7", jsob.optString("name_7", ""));
//   		model.addAttribute("state_7", jsob.optString("state_7", ""));
//   		model.addAttribute("name_8", jsob.optString("name_8", ""));
//   		model.addAttribute("state_8", jsob.optString("state_8", ""));
//   		model.addAttribute("name_9", jsob.optString("name_9", ""));
//   		model.addAttribute("state_9", jsob.optString("state_9", ""));
//   		model.addAttribute("name_10", jsob.optString("name_10", ""));
//   		model.addAttribute("state_10", jsob.optString("state_10", ""));
		}
	
	/**
	 * 保存
	 * @param request
	 * @param fsGateWay
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("saveagentbusy")
	@ResponseBody
	public String save(HttpServletRequest request, com.ruishengtech.rscc.crm.ui.mw.model.SysConfig sysConfig) throws ParseException {
		JSONObject jsob = new JSONObject();
		jsob.put("name_1", StringUtils.trimToEmpty(sysConfig.getName_1()));
		jsob.put("name_2", StringUtils.trimToEmpty(sysConfig.getName_2()));
		jsob.put("name_3", StringUtils.trimToEmpty(sysConfig.getName_3()));
		jsob.put("name_4", StringUtils.trimToEmpty(sysConfig.getName_4()));
		jsob.put("name_5", StringUtils.trimToEmpty(sysConfig.getName_5()));
		jsob.put("name_6", StringUtils.trimToEmpty(sysConfig.getName_6()));
//		jsob.put("name_7", StringUtils.trimToEmpty(sysConfig.getName_7()));
//		jsob.put("name_8", StringUtils.trimToEmpty(sysConfig.getName_8()));
//		jsob.put("name_9", StringUtils.trimToEmpty(sysConfig.getName_9()));
//		jsob.put("name_10", StringUtils.trimToEmpty(sysConfig.getName_10()));
		jsob.put("state_1", sysConfig.getState_1()!=null?sysConfig.getState_1():0);
		jsob.put("state_2", sysConfig.getState_2()!=null?sysConfig.getState_2():0);
		jsob.put("state_3", sysConfig.getState_3()!=null?sysConfig.getState_3():0);
		jsob.put("state_4", sysConfig.getState_4()!=null?sysConfig.getState_4():0);
		jsob.put("state_5", sysConfig.getState_5()!=null?sysConfig.getState_5():0);
		jsob.put("state_6", sysConfig.getState_6()!=null?sysConfig.getState_6():0);
//		jsob.put("state_7", sysConfig.getState_7()!=null?sysConfig.getState_7():0);
//		jsob.put("state_8", sysConfig.getState_8()!=null?sysConfig.getState_8():0);
//		jsob.put("state_9", sysConfig.getState_9()!=null?sysConfig.getState_9():0);
//		jsob.put("state_10", sysConfig.getState_10()!=null?sysConfig.getState_10():0);
		
		sysConfig.setVal(jsob.toString());
		
		sysConfigService.saveOrUpdate(sysConfig);

		return new JSONObject().put("success", true).toString();
	}
}

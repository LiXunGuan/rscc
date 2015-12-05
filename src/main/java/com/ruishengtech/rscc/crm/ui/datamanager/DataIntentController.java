package com.ruishengtech.rscc.crm.ui.datamanager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.model.User;

/**
 * 人员数据管理
 * @author Frank
 */
@Controller
@RequestMapping("dataintent")
public class DataIntentController {
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@Autowired
	private SysConfigService configService;
	
	/**
	 * @return
	 */
	@RequestMapping()
	public String userDataIndex(HttpServletRequest request,Model model){
		
		model.addAttribute("iframecontent","datamanager/new_user_data");
		return "iframe";
		
//		return "datamanager/new_user_data";
	}
	
	@RequestMapping("add")
	public String addIntentType(Model model){
		return "datamanager/add-intent";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String addIntentType(HttpServletRequest request, HttpServletResponse response, String intentName, String intentInfo, Model model){
		
		String uuid = dataIntentService.batSave(intentName, intentInfo);
		return new JSONObject().put("success", true).put("uuid",uuid).toString();
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteIntent(HttpServletRequest request,HttpServletResponse response,String uid,Model model){
		boolean b = dataIntentService.delete(uid);
		return new JSONObject().put("success", b).put("uuid", uid).toString();
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateIntentType(HttpServletRequest request,HttpServletResponse response,Model model,String obj){
		JSONObject jsonObject = new JSONObject(obj);
		
		@SuppressWarnings("unchecked")
		List<String> list = new ArrayList<>(jsonObject.keySet());
		for(String uid : list){
//			if(StringUtils.isEmpty(uid) && StringUtils.isNotBlank(jsonObject.getJSONObject(uid).getString("name")) && StringUtils.isNotBlank(jsonObject.getJSONObject(uid).getString("des"))){
//				this.addIntentType(request, response, jsonObject.getJSONObject(uid).getString("name"), jsonObject.getJSONObject(uid).getString("des"), model);
//			}else{
//				return "false";
//			}
			if(StringUtils.isNotBlank(uid)){
				DataIntent d = dataIntentService.getByUuid(uid);
				d.setIntentName(jsonObject.getJSONObject(uid).getString("name"));
				d.setIntentInfo(jsonObject.getJSONObject(uid).getString("des"));
				dataIntentService.update(d);
			}
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("checkname/intentName")
	@ResponseBody
	public String checkName(HttpServletRequest request, String uid,String intentName) {
		
		List<DataIntent> dis = dataIntentService.getAll();
		List<String> diName = new ArrayList<>();
		for(DataIntent di : dis){
			diName.add(di.getIntentName());
		}
		if(StringUtils.isBlank(uid)){
			if(StringUtils.isNotBlank(intentName) && diName.contains(intentName)){
				return "false";
			}else{
				return "true";
			}
		}else{
			String name = dataIntentService.getByUuid(uid).getIntentName();
			if(name.equals(intentName)){
				return "true";
			}else if(StringUtils.isNotBlank(intentName) && diName.contains(intentName)){
				return "false";
			}
		}
		return "true";
	}	
	
}

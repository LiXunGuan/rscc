package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.model.Confrence;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.mw.send.ConfrenceManager;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

/**
 * @author Wangyao
 *
*/
@Controller
@RequestMapping("confrence")
public class ConfrenceController {
	
	@Autowired
	private SysConfigService sysConfigService;
	
	public ConfrenceController() {
//		System.out.println(11);
	}
	
	@RequestMapping()
    public String indext(HttpServletRequest request,
                       HttpServletResponse response, Model model) {
		String confrenceId = ConfrenceManager.extenMap.get(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN));
		if(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN) == null){
			model.addAttribute("errorMessage", "请先绑定分机号");
		}
		model.addAttribute("confrenceId" ,confrenceId);
		
		model.addAttribute("iframecontent","confrence/confrence");
		return "iframe";
		
//		return "confrence/confrence";
    }
	
	@RequestMapping("create")
	public String createConfrencte(HttpServletRequest request, HttpServletResponse response, String id, Integer timeout, Model model) {
		return "confrence/conferenceroom_create";
	}
	
	@RequestMapping("invite")
	public String inviteConfrencte(HttpServletRequest request, HttpServletResponse response, String confrenceId, Model model) {
		HashMap<String, String> accessNumbers = new HashMap<String, String>();
		JSONArray accessNumberArray = QueueManager.getAllAccessNumber();
		for (int i = 0 ; i < accessNumberArray.length() ; i++ ) {
			accessNumbers.put(accessNumberArray.getJSONObject(i).getString("accessnumber"), String.valueOf(accessNumberArray.getJSONObject(i).getInt("concurrency")));
		}
		model.addAttribute("accessNumberls", accessNumbers.keySet());
		
		SysConfig config = sysConfigService.getSysConfig("defaultaccessnumber");

        if (config!=null) {
            JSONObject jsob = new JSONObject(config.getVal());
            model.addAttribute("id", config.getId());
            model.addAttribute("accessnumber", jsob.optString("accessnumber", ""));
        }
		
		
		return "confrence/conferenceroom_invite";
	}
	
	@RequestMapping("add")
	@ResponseBody
    public String addConfrence(HttpServletRequest request, HttpServletResponse response, String id, Integer timeout, Model model) {
		JSONObject json = new JSONObject();
		if(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN) == null){
			return json.put("success", false).put("message", "没有绑定分机号").toString();
		}
        String confrenceId = ConfrenceManager.addConfrence(null, timeout, null, SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString());
        json.put("confrenceId", confrenceId);
        return json.put("success", true).toString();
    }
	
	@RequestMapping("delete")
	@ResponseBody
    public String deleteConfrence(HttpServletRequest request, HttpServletResponse response, String confrenceId, Model model) {
		confrenceId = StringUtils.isBlank(confrenceId)?ConfrenceManager.extenMap.get(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN)):confrenceId;
        ConfrenceManager.deleteConfrence(confrenceId);
        JSONObject json = new JSONObject();
        json.put("confrenceId", confrenceId);
        return confrenceId;
    }
	
	@RequestMapping("data")
	@ResponseBody
    public String queryData(HttpServletRequest request, HttpServletResponse response, String confrenceId, Model model) {
		confrenceId = StringUtils.isBlank(confrenceId)?ConfrenceManager.extenMap.get(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN)):confrenceId;
		Confrence con = ConfrenceManager.confrenceMap.get(confrenceId);
		JSONArray array = null;
		if(con != null){
			array = new JSONArray(con.getMembers().values());
		}else{
			array = new JSONArray();
		}
        JSONObject json = new JSONObject();
        json.put("data", array);
        json.put("iTotalRecords", con==null?0:con.count());
        json.put("iTotalDisplayRecords", con==null?0:con.count());	
//        json.put("confrenceId", confrenceId);
		return json.toString();
    }
	
	@RequestMapping("invitation")
	@ResponseBody
    public String invitation(HttpServletRequest request, HttpServletResponse response, String confrenceId, String num, String accessnumber, Model model) {
		confrenceId = StringUtils.isBlank(confrenceId)?ConfrenceManager.extenMap.get(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN)):confrenceId;
		if(StringUtils.isBlank(confrenceId))
			return new JSONObject().put("success", false).toString();
		ConfrenceManager.addConfrence(confrenceId, null, accessnumber, num.split("[^0-9]+"));
        return new JSONObject().put("success", true).toString();
    }
	
	@RequestMapping("mute")
	@ResponseBody
    public String mute(HttpServletRequest request, HttpServletResponse response, String confrenceId, String num, Model model) {
		confrenceId = StringUtils.isBlank(confrenceId)?ConfrenceManager.extenMap.get(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN)):confrenceId;
		if(StringUtils.isBlank(confrenceId))
			return new JSONObject().put("success", false).toString();
		ConfrenceManager.muteConfrence(confrenceId, num);
        return new JSONObject().put("success", true).toString();
    }
	
	@RequestMapping("unmute")
	@ResponseBody
    public String unmute(HttpServletRequest request, HttpServletResponse response, String confrenceId, String num, Model model) {
		confrenceId = StringUtils.isBlank(confrenceId)?ConfrenceManager.extenMap.get(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN)):confrenceId;
		if(StringUtils.isBlank(confrenceId))
			return new JSONObject().put("success", false).toString();
		ConfrenceManager.unmuteConfrence(confrenceId, num);
        return new JSONObject().put("success", true).toString();
    }
	
	//踢出成员
	@RequestMapping("deleteNum")
	@ResponseBody
    public String deleteNum(HttpServletRequest request, HttpServletResponse response, String confrenceId, String num, Model model) {
		confrenceId = StringUtils.isBlank(confrenceId)?ConfrenceManager.extenMap.get(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN)):confrenceId;
		if(StringUtils.isBlank(confrenceId))
			return new JSONObject().put("success", false).toString();
		ConfrenceManager.deleteConfrence(confrenceId, num);
        return new JSONObject().put("success", true).toString();
    }
	
}


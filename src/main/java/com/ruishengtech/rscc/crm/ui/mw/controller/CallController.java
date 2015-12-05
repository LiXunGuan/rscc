package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.send.AgentManager;
import com.ruishengtech.rscc.crm.ui.mw.send.CallManager;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.user.model.User;

/**
 * @author Wangyao
 *
*/
@Controller
@RequestMapping("callcenter")
public class CallController {
	
	private static final Logger logger = Logger.getLogger(CallController.class);
	
	@Autowired
	private CallLogService callLogService;
	
	public CallController() {
		//System.out.println(11);
	}
	
	@RequestMapping()
    public String indext(HttpServletRequest request,
                       HttpServletResponse response, Long id, Model model) {
        return "";
    }
	
	@RequestMapping("call")
    public String call(HttpServletRequest request, HttpServletResponse response, String id, Model model) {
        model.addAttribute("callid", id);
        return "call/agent_call";
    }
	
	@RequestMapping("transfer")
    public String transfer(HttpServletRequest request, HttpServletResponse response, String id, Model model) {
        model.addAttribute("callid", id);
        model.addAttribute("extenRoute", CallManager.getAllExtenMap());
        return "call/agent_transfer";
    }
	
	@RequestMapping("agentcall")
	@ResponseBody
    public String makecall(HttpServletRequest request, HttpServletResponse response, String callid, String callNum, Model model) {
		
		JSONObject jsonObject = CallManager.makeCall(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), callNum, SessionUtil.getCurrentUser(request), null);
		logger.info("呼叫后返回的结果为:"+jsonObject);
		if("0".equals(jsonObject.optString("exit_code","1999"))){
			
			User currentUser = SessionUtil.getCurrentUser(request);
			CallLog callLog = new CallLog();
			callLog.setAgent_id(currentUser.getUid());
			callLog.setAgent_name(currentUser.getLoginName());
			callLog.setCall_phone(callNum);
			callLog.setIn_out_flag("呼出");
			callLog.setCall_time(new Date());
			callLog.setCall_session_uuid(jsonObject.getString("call_session_uuid"));
			callLogService.save(callLog);
			
			return new JSONObject().put("success", true).put("phone", callNum).put("call_session_uuid", jsonObject.getString("call_session_uuid")).toString();
		}
			
		return null;
    }
	
	@RequestMapping("transferto")
	@ResponseBody
    public String transferto(HttpServletRequest request, HttpServletResponse response, String id, String extensions, Model model) {
//		if(CallManager.transferCall(1, id, extensions).optInt("exit_code",19999)==0)
		if(CallManager.transferCall(2, AgentManager.agentMap.getKey(id), extensions).optInt("exit_code",19999)==0)
			return new JSONObject().put("success", true).toString();
		return null;
    }
	
	@RequestMapping("callclose")
	@ResponseBody
	public String callclose(HttpServletRequest request, HttpServletResponse response, String id, Model model) {
//		if(CallManager.hangupCall(1, id).optInt("exit_code",19999)==0)
		if(CallManager.hangupCall(2, AgentManager.agentMap.getKey(id)).optInt("exit_code",19999)==0)
			return new JSONObject().put("success", true).toString();
		return null;
	}
	
	@RequestMapping("tomusic")
	@ResponseBody
	public String tomusic(HttpServletRequest request, HttpServletResponse response, String id, Model model) {
//		if(CallManager.holdCall(1, id).optInt("exit_code",19999)==0)
		if(CallManager.holdCall(2, AgentManager.agentMap.getKey(id)).optInt("exit_code",19999)==0)
			return new JSONObject().put("success", true).toString();
		return null;
	}
	
	@RequestMapping("backcall")
	@ResponseBody
	public String backcall(HttpServletRequest request, HttpServletResponse response, String id, Model model) {
//		if(CallManager.unholdCall(1, id).optInt("exit_code",19999)==0)
		if(CallManager.unholdCall(2, AgentManager.agentMap.getKey(id)).optInt("exit_code",19999)==0)
			return new JSONObject().put("success", true).toString();
		return null;
	}
	
}


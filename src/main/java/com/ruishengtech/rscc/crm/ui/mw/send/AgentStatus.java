package com.ruishengtech.rscc.crm.ui.mw.send;

import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;

/**
 * @author Frank
 *
 */
public class AgentStatus {
	
	public static JSONObject getAgentStatus(String agent_id){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "getAgentStatus");
		requestJson.put("agent_id", agent_id);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseJson!=null?responseJson:new JSONObject();
	}

	/**
	 * 置忙
	 * @param jsonObject
	 */
	public static void setUserBusy(JSONObject jsonObject) {
		
		
		
	}
	
	
	

}

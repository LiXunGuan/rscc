package com.ruishengtech.rscc.crm.ui.mw.send;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;
import com.ruishengtech.rscc.crm.ui.mw.send.exception.HttpException;
import com.ruishengtech.rscc.crm.ui.mw.send.structure.AgentMap;
import com.ruishengtech.rscc.crm.user.model.User;

public class AgentManager {
	
	public static AgentMap agentMap = new AgentMap();
	
	public static JSONObject addAgent(User user){
		JSONObject userJson = new JSONObject();
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "agent_save_or_update");
		userJson.put("agent_id", user.getLoginName());
		userJson.put("agent_job_number", user.getJobNumber());
		userJson.put("agent_info", user.getUserDescribe());
		userJson.put("caller_id_number", user.getCallerIdName());
		userJson.put("caller_id_name", user.getCallerIdName());
		requestJson.put("agent_list", new JSONArray().put(userJson));
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.optInt("exit_code",99999) != 0) {
			throw new HttpException(response);
		}
		return response;
	}
	
	public static JSONObject deleteAgent(User user){
		return deleteAgent(user.getLoginName());
	}
	
	public static JSONObject deleteAgent(String id){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "agent_delete");
		requestJson.put("agent_list", new JSONArray().put(id));
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.optInt("exit_code",99999) != 0) {
			throw new HttpException(response);
		}
		return response;
	}
	
	public static JSONObject deleteAgent(String[] idlist){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "agent_delete");
		requestJson.put("agent_list", new JSONArray(idlist));
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.optInt("exit_code",99999) != 0) {
			throw new HttpException(response);
		}
		return response;
	}
	
	public static JSONObject deleteAgent(Collection<String> idlist) {
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "agent_delete");
		requestJson.put("agent_list", new JSONArray(idlist));
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.optInt("exit_code",99999) != 0) {
			throw new HttpException(response);
		}
		return response;
	}

	
	
	
	/*======================================== 登录绑定 ============================================*/
	
	/**
	 * 用户登录绑定
	 * @param user
	 * @param exten
	 * @throws Exception 
	 * @throws JSONException 
	 */
	public static JSONObject extenBind(User user, String exten) {
		
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "bind");
		requestJson.put("agent_id", user.getLoginName());
		requestJson.put("exten", exten);
//		requestJson.put("queue_exten_list", "");
		
		JSONObject response = null;
		
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
		
	}

	/**
	 * 分机解绑
	 * @param user
	 * @return
	 */
	public static JSONObject extenUbind(User user) {
		System.out.println("-----------------------解绑分机-------------------------" + new Date());
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "unbind");
		requestJson.put("agent_id", user.getLoginName());
		
		JSONObject response = null;
		
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	/**
	 * 根据exten获取当前绑定的agent
	 * @param exten
	 * @return
	 */
	public static JSONObject getBindAgent(String exten){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "get_bind_agent");
		requestJson.put("exten", exten);
		
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	/**
	 * 根据agent获取当前绑定的exten
	 * @param exten
	 * @return
	 */
	public static JSONObject getBindExten(String agentid){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "get_bind_exten");
		requestJson.put("agent_id", agentid);
		
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	/**
	 * 分机解绑
	 * @param agentid
	 * @return
	 */
	public static JSONObject kickOffExten(String agentid) {
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "unbind");
		requestJson.put("agent_id", agentid);
		
		JSONObject response = null;
		
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	
}

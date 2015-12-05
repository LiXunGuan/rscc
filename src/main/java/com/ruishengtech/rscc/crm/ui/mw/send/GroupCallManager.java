package com.ruishengtech.rscc.crm.ui.mw.send;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;
import com.ruishengtech.rscc.crm.data.model.GroupCall;
import com.ruishengtech.rscc.crm.datamanager.model.NewGroupCall;


public class GroupCallManager {
	
	public static Map<String, String> strategyMap = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("0", "static");
			put("1", "dynamic");
			put("2", "agent_one_by_one");
		}
	};
	
	public static JSONObject createGroupCall(GroupCall groupCall){
		JSONObject requestJson = new JSONObject(groupCall); 
		JSONObject responseJson = null; 
		requestJson.put("command", "groupcall_save_or_update");
		requestJson.put("dst_exten", groupCall.getDst_exten().split("#")[0]);
		try {
			if("0".equals(groupCall.getStrategy())) {//静态群呼
				requestJson.put("strategy", "static");
				requestJson.remove("ratio");
				if(StringUtils.isBlank(requestJson.optString("concurrency"))) {
					requestJson.put("concurrency", "10");
					groupCall.setConcurrency("10");
				}
			}
			else if("1".equals(groupCall.getStrategy())){//动态群呼
				requestJson.put("strategy", "dynamic");
				requestJson.remove("concurrency");
				if(StringUtils.isBlank(requestJson.optString("ratio"))) {
					requestJson.put("ratio", "1.5");
					groupCall.setRatio("1.5");
				}
			}
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject deleteGroupCall(String... groupCallId){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "groupcall_delete");
		requestJson.put("groupcall_id_list", new JSONArray(groupCallId));
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject startGroupCall(String groupCallId){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "groupcall_start");
		requestJson.put("groupcall_id", groupCallId);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
//	public static JSONObject stopGroupCall(String groupCallId, String notifyUrl){
	public static JSONObject stopGroupCall(String groupCallId) {
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "groupcall_stop");
		requestJson.put("groupcall_id", groupCallId);
		requestJson.put("groupcall_stopped_notify_url", SpringPropertiesUtil.getProperty("sys.rscc") + SpringPropertiesUtil.getProperty("sys.data.call.stopcall"));
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject getGroupCallCount(){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "groupcall_count");
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject queryGroupCall(String page, String size){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "groupcall_count");
		requestJson.put("page_count", page);
		requestJson.put("page_size", size);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject queryConcurrency(){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "groupcall_concurrency");
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optInt("exit_code", 1) == 0)
			return new JSONObject(responseJson.getString("ret"));
		return new JSONObject();
	}
	
	
	
	public static JSONObject createGroupCall(NewGroupCall groupCall){
		JSONObject requestJson = new JSONObject(groupCall); 
		JSONObject responseJson = null; 
		requestJson.put("command", "groupcall_save_or_update");
		requestJson.put("dst_exten", groupCall.getDst_exten().split("#")[0]);
		try {
			if("0".equals(groupCall.getStrategy())) {//静态群呼
				requestJson.put("strategy", "static");
				requestJson.remove("ratio");
				if(StringUtils.isBlank(requestJson.optString("concurrency"))) {
					requestJson.put("concurrency", "10");
					groupCall.setConcurrency("10");
				}
			}
			else if("1".equals(groupCall.getStrategy())){//动态群呼
				requestJson.put("strategy", "dynamic");
				requestJson.remove("concurrency");
				if(StringUtils.isBlank(requestJson.optString("ratio"))) {
					requestJson.put("ratio", "1.5");
					groupCall.setRatio("1.5");
				}
			}
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
}

package com.ruishengtech.rscc.crm.ui.mw.send;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;

public class QueueManager {

	public static ConcurrentHashMap<String, Integer> queueMap = new ConcurrentHashMap<String, Integer>();
	
	public static JSONObject createQueue(String queue_exten, String queue_name, String strategy) throws Exception{
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "queue_save_or_update");
		requestJson.put("queue_exten", queue_exten);
		requestJson.put("queue_name", queue_name);
		requestJson.put("strategy", "longest-idle-agent");
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			throw e;
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject deleteQueue(String queueExten){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "queue_delete");
		requestJson.put("queue_exten", queueExten);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static int getQueueCount(){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "queue_count");
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optInt("exit_code") == 0)
			return responseJson.optInt("queue_count");
		return 0;
	}
	
	public static JSONArray queueQueue(int page, int size){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "queue_query");
		requestJson.put("page_count", String.valueOf(page));
		requestJson.put("page_size", String.valueOf(size));
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optInt("exit_code") == 0)
			return responseJson.getJSONArray("queue_list");
		return new JSONArray();
	}
	
	public static JSONArray getAllQueue(){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "queue_query");
		requestJson.put("page_count", String.valueOf(0));
		requestJson.put("page_size", String.valueOf(getQueueCount()));
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optInt("exit_code") == 0)
			return new JSONArray(responseJson.getString("queue_list"));
		return new JSONArray();
	}
	
	public static JSONArray getQueueTree(){
		
		JSONArray ret = new JSONArray();
		
		JSONObject jsonRoot = new JSONObject();
        jsonRoot.put("id", "queue");
        jsonRoot.put("pId", "root");
        jsonRoot.put("name", "队列");
        jsonRoot.put("open", true);
        ret.put(jsonRoot);
        
        JSONObject jsonAll = new JSONObject();
        jsonAll.put("id", "aee");
        jsonAll.put("pId", "queue");
        jsonAll.put("name", "全部队列");
        jsonAll.put("open", true);
        ret.put(jsonAll);
        
        
		JSONArray jsonArray = getAllQueue();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", jsonArray.getJSONObject(i).get("queue_id"));
        	jsonObject.put("pId", "queue");
        	jsonObject.put("name", jsonArray.getJSONObject(i).get("queue_name"));
        	jsonObject.put("open", true);
            ret.put(jsonObject);
		}
		return ret;
	}
	
	public static JSONArray getAllAccessNumber(){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "getAccessNumber");
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optInt("exit_code") == 0)
			return new JSONArray(responseJson.getString("ret"));
		return new JSONArray();
	}
	
	public static JSONArray getAgentQueue(String agent_id){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "get_agent_queue");
		requestJson.put("agent_id", agent_id);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optInt("exit_code") == 0)
			return new JSONArray(responseJson.getString("ret"));
		return new JSONArray();
	}
	
	public static String[] getAgentQueueArray(String agent_id){
		JSONArray jsonArray = getAgentQueue(agent_id); 
		String[] queues = new String[jsonArray.length()];
		for (int i = 0; i < jsonArray.length(); i++) {
			queues[i] = String.valueOf(jsonArray.getJSONObject(i).getInt("queueId"));
		}
		return queues;
	}
	
	public static JSONArray getQueueRuntime(String... queueIds){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "getQueueRuntime");
		requestJson.put("queue_id", new JSONArray(queueIds));
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optInt("exit_code") == 0)
			return new JSONArray(responseJson.getString("ret"));
		return new JSONArray();
	}
	
	public static Map<String, String> getQueueNameMap(){
		JSONArray jsonArray = getAllQueue();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			map.put(String.valueOf(jsonArray.getJSONObject(i).getInt("queue_id")), jsonArray.getJSONObject(i).getString("queue_name"));
		}
		return map;
	}
	
	public static Map<String, Integer> getQueueExtenMap(){
		JSONArray jsonArray = getAllQueue();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < jsonArray.length(); i++) {
			if(StringUtils.isNotBlank(jsonArray.getJSONObject(i).optString("queue_exten")))
				map.put(jsonArray.getJSONObject(i).get("queue_exten")==null?null:String.valueOf(jsonArray.getJSONObject(i).get("queue_exten")), jsonArray.getJSONObject(i).getInt("queue_id"));
		}
		return map;
	}
	
}

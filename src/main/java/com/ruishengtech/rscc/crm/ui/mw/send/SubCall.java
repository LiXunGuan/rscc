package com.ruishengtech.rscc.crm.ui.mw.send;

import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;
import com.ruishengtech.rscc.crm.user.model.User;


public class SubCall {
	
	public static JSONObject subCdr(String src, String number, User user){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "cdr_sub");
		requestJson.put("url", "http://192.168.1.140:8080/rs/event");
		JSONObject responseJson = null; 
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject subChannel(String src, String number, User user){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "channel_status_sub");
		requestJson.put("url", "http://192.168.1.140:8080/rs/event");
		JSONObject responseJson = null; 
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject subMissCall(){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "channel_status_sub");
		requestJson.put("url", "http://192.168.1.150:8080/rs/event");
		JSONObject responseJson = null; 
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject subQueueStatus(){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "queue_status_sub");
		requestJson.put("url", "http://192.168.1.140:8080/rs/event");
		JSONObject responseJson = null; 
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject subPop(String timing){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "pop_sub");
		requestJson.put("time", timing);
		requestJson.put("url", SpringPropertiesUtil.getProperty("sys.crm") + "event");
		JSONObject responseJson = null; 
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
}

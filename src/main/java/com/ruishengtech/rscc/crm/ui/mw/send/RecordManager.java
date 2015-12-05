package com.ruishengtech.rscc.crm.ui.mw.send;

import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;

public class RecordManager {

	public static JSONObject recordTime(String result){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "sysconfig");
		requestJson.put("key", "cancallwithbinder");
        requestJson.put("value", result);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson != null ? responseJson : new JSONObject();
	}
	
}

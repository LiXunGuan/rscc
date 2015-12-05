package com.ruishengtech.rscc.crm.ui.mw.send;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class MwMangaer {

	public static JSONObject reloadAccessNumber(){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "reloadAccessNumber");
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson != null ? responseJson : new JSONObject();
	}
	
	public static void reloadRoute(){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "reloadRoute");
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void reloadDefaultGateWay() {

        JSONObject requestJson = new JSONObject();
        JSONObject responseJson = null;
        requestJson.put("command", "reloadDefaultGateWay");
        try {
            responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sendAgentHttpCommond(String url, String params, String hostName) {

        JSONObject requestJson = new JSONObject();
        JSONObject responseJson = null;
        requestJson.put("command", "fsHttpCommand");
        requestJson.put("url", url);
        requestJson.put("params", params);
        requestJson.put("hostName", hostName);
        try {
            responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void sendAsynFsCommond(String content, String hostName) {

        JSONObject requestJson = new JSONObject();
        JSONObject responseJson = null;
        requestJson.put("command", "fsCommand");
        requestJson.put("action", content);
        requestJson.put("hostName", hostName);
        try {
            responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Map<String, Boolean> getGateWayStatus() {

        Map<String,Boolean> map =new HashMap<>();

        JSONObject requestJson = new JSONObject();
        JSONObject responseJson = null;
        requestJson.put("command", "getGateWayStatus");

        try {
            responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));

            if(responseJson.optInt("exit_code") != 0) {
                throw new RuntimeException(requestJson.toString());
            }

            String arrays = responseJson.getString("ret");
            JSONArray array = new JSONArray(arrays);

            for(int i=0;i<array.length();i++){
                JSONObject json = array.getJSONObject(i);
                map.put(json.optString("name"),Boolean.TRUE.equals(json.optBoolean("status"))?true:false);
            }
            return map;

        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}

package com.ruishengtech.rscc.crm.ui.mw.send;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;
import com.ruishengtech.rscc.crm.ui.mw.model.Confrence;

public class ConfrenceManager {
	
	public static HashMap<String,Confrence> confrenceMap = new HashMap<String,Confrence>();
	
	public static HashMap<String,String> extenMap = new HashMap<String,String>();
	
	//返回会议室ID，如果为0则创建失败
	public static String addConfrence(String confrenceId, Integer timeout, String accessNumber, String... numberList) {
		if (numberList == null)
			return "0";
		boolean newFlag = false;//是否为新创建的会议室
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "confrence_invite");
		if (confrenceId == null){
			confrenceId = getRandomNumber(100000,800000,5);
			confrenceMap.put(confrenceId, new Confrence(confrenceId, numberList[0]));
			confrenceMap.get(confrenceId).add(numberList[0], new JSONObject().put("invitation_state","1").put("extension", numberList[0]));
			extenMap.put(numberList[0], confrenceId);
			newFlag = true;
		}else{
			requestJson.put("accessNumber", accessNumber);
			for (String number:numberList)//如果不是新会议室，则在会议室中添加号码
				confrenceMap.get(confrenceId).add(number,new JSONObject().put("invitation_state","1").put("extension", number).put("accessNumber", accessNumber));
		}
		requestJson.put("conference_exten", confrenceId);
		requestJson.put("number_list", new JSONArray(numberList));
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.optInt("exit_code",1)!=0){
			if(newFlag) {//创建会议室失败时，如果是新的，则移除掉
				Confrence con = confrenceMap.remove(confrenceId);
				extenMap.remove(con.getOwner());
			}
			else {//邀请失败时，删除这个添加的
				for (String number:numberList)
					confrenceMap.get(confrenceId).remove(number);
			}
		}
		return response.optInt("exit_code",1)==0?confrenceId:"0";
	}
	
	public static JSONObject deleteConfrence(String confrenceId, String... numberList){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "confrence_kick");
		requestJson.put("conference_exten", confrenceId);
		requestJson.put("number_list", new JSONArray(numberList));
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.optInt("exit_code", 1) == 0)
			for (String number : numberList)
				confrenceMap.get(confrenceId).kickout(number);//从会议室删除用户
		return response;
	}
	
	public static JSONObject deleteConfrence(String confrenceId){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "confrence_kick");
		requestJson.put("conference_exten", confrenceId);
		requestJson.put("number_list", new JSONArray(confrenceMap.get(confrenceId).getMembers().keySet()));
		Confrence con = confrenceMap.remove(confrenceId);
		extenMap.remove(con.getOwner());
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static JSONObject muteConfrence(String confrenceId, String... numberList){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "confrence_mute");
		requestJson.put("conference_exten", confrenceId);
		requestJson.put("number_list", new JSONArray(numberList));
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.optInt("exit_code",1)==0)
			for(String number:numberList)
				confrenceMap.get(confrenceId).get(number).put("mute_state", "2");
		return response;
	}
	
	public static JSONObject unmuteConfrence(String confrenceId, String... numberList){
		JSONObject requestJson = new JSONObject(); 
		requestJson.put("command", "confrence_unmute");
		requestJson.put("conference_exten", confrenceId);
		requestJson.put("number_list", new JSONArray(numberList));
		JSONObject response = null;
		try {
			response = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString())) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.optInt("exit_code",1)==0)
			for(String number:numberList)
				confrenceMap.get(confrenceId).get(number).put("mute_state", "1");
		return response;
	}
	
	//注意这个递归，可能会造成死循环
	private static String getRandomNumber(int min, int max, int size){
		int temp = (int) (min + (max - min) * Math.random());
		int count = Math.max(size, String.valueOf(max).length());
		String num = String.format(String.valueOf(temp), "%0" + count + "d");
		if(confrenceMap.keySet().contains(num))
			return getRandomNumber(min, max, size);
		return String.format(String.valueOf(temp), "%0" + count + "d");
	}
}

package com.ruishengtech.rscc.crm.ui.mw.send;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;


public class CallManager {
	
	
	private static final Logger logger = Logger.getLogger(CallManager.class);
	
	private static Map<String, String> extenName = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{ //"SIPUSER":"分机","CALLCENTER":"技能组","IVR":"IVR","DIALPLAN":"拨号计划","CUSTOMER":"自定义","CONFERENCE":"会议室","AGENT":"坐席人员"
			put("SIPUSER", "分机");
			put("CALLCENTER", "技能组");
			put("IVR", "IVR");
			put("DIALPLAN", "拨号计划");
			put("CUSTOMER", "自定义");
			put("CONFERENCE", "会议室");
			put("AGENT", "坐席人员");
		}
	}; 
	
	/**
	 * @param src 分机号
	 * @param number 呼叫电话号码
	 * @param user 当前用户
	 * @param userField 
	 * @return
	 */
	public static JSONObject makeCall(String src, String number, User user, String userField){
		
		//呼叫前检测绑定状态，没有绑定则替他绑定一下
		JSONObject obj = AgentManager.getBindAgent(src);
		System.out.println(obj.toString() + new Date());
//		if(!obj.optString("agent_id").equals(user.getLoginName())) {
//			AgentManager.extenBind(user, src);
//		}
		
		JSONObject requestJson = new JSONObject();
		JSONObject responseJson = null; 
		requestJson.put("command", "originate");
		requestJson.put("src", src);   //分机号
		requestJson.put("dst", number);  //要呼叫的号码
		//makeCall传入的最后一个参数，controller中设置了null，不知道什么用？
		if(StringUtils.isNotBlank(userField)) {   
			requestJson.put("user_field", userField);
//			requestJson.put("userfield", userField);
		}
		//是否隐藏号码
		String hiddenPhoneNumber = SysConfigManager.getInstance().getDataMap().get("hiddenPhoneNumber").getSysVal();
		UserService service = ApplicationHelper.getApplicationContext().getBean(UserService.class);
		//读取permission中的查看号码的配置，即角色权限中的是否勾选了查看号码的权限
		boolean hasPermission = service.hasPermission(user.getUid(), "90");
		if ("true".equals(hiddenPhoneNumber) && !hasPermission) {
			requestJson.put("originate_caller_id_name", PhoneUtil.hideNumber(number));
			requestJson.put("originate_caller_id_number", PhoneUtil.hideNumber(number));
		}
		try {
			//电话呼出去了
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	/**
	 * @param src 分机号
	 * @param number 呼叫电话号码
	 * @param user 当前用户
	 * @param userField 
	 * @return
	 */
	public static JSONObject makeCall(String src, String number, User user, String userField, boolean hiddenPhone){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "originate");
		requestJson.put("src", src);
		requestJson.put("dst", number);
		if(StringUtils.isNotBlank(userField))
			requestJson.put("user_field", userField);
		if (hiddenPhone) {
			requestJson.put("originate_caller_id_name", PhoneUtil.hideNumber(number));
			requestJson.put("originate_caller_id_number", PhoneUtil.hideNumber(number));
		}
//		requestJson.put("caller_id_name", user.getCallerIdName());
//		requestJson.put("caller_id_number", user.getCallerIdNumber());
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	//置忙后，agent将不会在接到由技能组分配的来电
	public static JSONObject pauseCall(String agentid, String reason){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "agent_pause");
		requestJson.put("agent_id", agentid);
		requestJson.put("reason", reason);
		try {
			
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
			logger.info("发送API的位置为："+SpringPropertiesUtil.getProperty("sys.data.call.api"));
			logger.info("置忙请求结果为："+responseJson);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject unpauseCall(String agentid){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "agent_unpause");
		requestJson.put("agent_id", agentid);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
			logger.info("发送API的位置为："+SpringPropertiesUtil.getProperty("sys.data.call.api"));
			logger.info("置闲请求结果为："+responseJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	//呼叫保持，0为channel，1为agent，2为exten
	public static JSONObject holdCall(int type, String id){
		JSONObject requestJson = new JSONObject();
		JSONObject responseJson = null; 
		requestJson.put("command", "hold");
		switch (type) {
		case 0:
			requestJson.put("type","channel").put("channel_uuid", id);
			break;
		case 2:
			requestJson.put("type","exten").put("exten", id);
			break;
		default:
			requestJson.put("type","agent").put("agent_id", id);
		}
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject unholdCall(int type, String id){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "unhold");
		switch (type) {
		case 0:
			requestJson.put("type","channel").put("channel_uuid", id);
			break;
		case 2:
			requestJson.put("type","exten").put("exten", id);
			break;
		default:
			requestJson.put("type","agent").put("agent_id", id);
		}
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONObject transferCall(int type, String id, String dst){
		JSONObject requestJson = new JSONObject();
		JSONObject responseJson = null; 
		requestJson.put("command", "transfer");
		switch (type) {
		case 0:
			requestJson.put("type","channel").put("channel_uuid", id);
			break;
		case 2:
			requestJson.put("type","exten").put("exten", id);
			break;
		default:
			requestJson.put("type","agent").put("agent_id", id);
		}
		requestJson.put("dst", dst);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	//挂断
	public static JSONObject hangupCall(int type, String id){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "hangup");
		switch (type) {
		case 0:
			requestJson.put("type","channel").put("channel_uuid", id);
			break;
		case 2:
			requestJson.put("type","exten").put("exten", id);
			break;
		default:
			requestJson.put("type","agent").put("agent_id", id);
		}
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();
	}
	
	public static JSONArray getAllExten(){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "getAllExten");
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optInt("exit_code") == 0)
			return new JSONArray(responseJson.getString("ret"));
		return new JSONArray();
	}
	
	/**
	 * 获取号码归属地
	 * @param number
	 * @return
	 */
	public static JSONObject getMobileInfo(String number){
		JSONObject requestJson = new JSONObject(); 
		JSONObject responseJson = null; 
		requestJson.put("command", "getMobileInfo");
		requestJson.put("number", number);
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(responseJson.optString("exit_code").equals("0")){
			return responseJson;
		}
		if(responseJson.optString("exit_code").equals("10001")){
			return new JSONObject(responseJson.getString("err_msg"));
		}
		
		if(responseJson.optInt("exit_code") == 0){
			return new JSONObject(responseJson.getString("ret"));
		}
		
		return new JSONObject();
	}
	
	public static Map<String, String> getAllExtenMap(){
		Map<String, String> extenMap = new HashMap<String, String>();
		JSONArray jsonArray = getAllExten();
		for (int i = 0 ; i < jsonArray.length() ; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if (jsonObject.opt("extension") != null) {
				extenMap.put(jsonObject.getString("extension"), "(" + extenName.get(jsonObject.getString("type")) + ")" + jsonObject.getString("name"));
			}
		}
		return extenMap;
	}
	
	/**
	 * 号码归属地
	 * @param optString
	 * @return
	 */
	public static String getPhoneLocation(String optString) {

		if(StringUtils.isNotBlank(optString)){

			JSONObject array = CallManager.getMobileInfo(optString);
			
			try {
				
				JSONObject jsonObject = new JSONObject(array.getString("ret"));
				if(jsonObject.optString("mobileArea","").equals("")){
					return "未知地区";
				}
				return jsonObject.optString("mobileArea") + "(" + jsonObject.optString("mobileType") + ")";
			} catch (Exception e) {
				
				return "未知地区";
			}
		}
		
		return "未知地区";
	}

	/**
	 * 
	 * @param str
	 * @param model
	 * @return
	 */
	public static String getPhoneDetails(String str , Model model){
		
		
		return null;
	}

	
}

package com.ruishengtech.rscc.crm.ui.mw.send;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.HttpRequest;

public class SipManager {

	/**
	 * 批量创建分机
	 * @param beginExten 开始分机号
	 * @param endExten 结束分机号 
	 * @param passwordType 密码类型
	 * @param password 固定密码
	 * @return 
	 */
	public static JSONObject batchCreateSip(Integer beginExten,
			Integer endExten, String passwordType, String password) {

		// 分机总信息
		JSONObject requestSipJson = new JSONObject();

		requestSipJson.put("command", "exten_save_or_update");

		// 分机信息
		JSONArray array = new JSONArray();

		if ("1".equals(passwordType)) {
			
			for (int i = beginExten; i <= endExten; i++) {
				JSONObject extenInfo = new JSONObject();
				extenInfo.put("exten", i);
				extenInfo.put("password", i);
				array.put(extenInfo);
			}
			requestSipJson.put("exten_list", array);
			
		} else {
			
			for (int i = beginExten; i <= endExten; i++) {
				JSONObject extenInfo = new JSONObject();
				extenInfo.put("exten", i);
				extenInfo.put("password", password);
				array.put(extenInfo);
			}
			requestSipJson.put("exten_list", array);
		}

		JSONObject responseJson = null;
		try {
			responseJson = new JSONObject(HttpRequest.sendPost(SpringPropertiesUtil.getProperty("sys.data.call.api"), requestSipJson.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson!=null?responseJson:new JSONObject();

	}

}

package com.ruishengtech.framework.core.licence.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.ruishengtech.framework.core.PathUtil;

/**
 * @author Frank 读取本地license文件
 */
public class LicenseUtils {
	
	//认证服务器地址
//	public static String serverIP = "114.215.175.114";
	public static String serverIP = "192.168.1.120";
	//服务器端口
	public static int serverPORT = 8000;
	//产品名
	public static String productName = "RSCC8000";
	//是否开启远程认证
	public static String remoteVerification = "false";

	
	private static LicenseUtils ourInstance = new LicenseUtils();
	
	private LicenseUtils() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static LicenseUtils getInstance() throws Exception{
		
		return ourInstance;
	}
	
	
	public JSONObject jsonObject = new JSONObject();
	
	/**
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception{
		
		jsonObject = readLicense();
	}
	
	/**
	 * 刷新缓存
	 * @throws Exception
	 */
	public void reload(JSONObject json) throws Exception{
		
		jsonObject = json;
	}
	
	
	/**
	 * 读取本地license
	 * @return
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	public JSONObject readLicense() throws Exception {
		String filePath =  PathUtil.getBasePath() + File.separator + "license.txt";
		byte[] data = FileUtils.getBytes(filePath);
    	if(data != null){
    		RSAPublicKey publickey = (RSAPublicKey)KeyHelper.readKeyPath(PathUtil.getBasePath() + File.separator + "key_public.txt");
			if(publickey != null){
//				String decodedData = KeyUtils.decryptByPublicKey(new String(data.getBytes(),"UTF-8"), publickey);
				byte[] decodedData = RSAUtil.PublicDECRYPT(publickey, data);
				jsonObject = new JSONObject(new String(decodedData, "utf-8"));
				System.out.println("读取到的本地license数据:" + jsonObject.toString());
			}
    		return jsonObject;
    	}
		return null;
	}
	

	/**
	 * 获取product
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	public String getProductName()  {
		
		return jsonObject.getString("productName");
	}
	
	/**
	 * 获取issuance_date
	 * @param jsonObject
	 * @return
	 * @
	 */
	public String getIssuanceDate()  {
		
		return jsonObject.getString("issuance_date");
	}

	/**
	 * 获取expire_date
	 * @param jsonObject
	 * @return
	 * @
	 */
	public String getExpireDate()  {
		
		return jsonObject.getString("expire_date");
	}
	
	/**
	 * 获取license_type
	 * @param jsonObject
	 * @return
	 * @
	 */
	public String getLicenseType()  {
		
		return jsonObject.getString("license_type");
	}
	
	/**
	 * 获取other_info
	 * @param jsonObject
	 * @return
	 * @
	 */
	public String getOtherInfo()  {
		
		return jsonObject.getString("other_info");
	}
	
	/**
	 * 获取status
	 * @param jsonObject
	 * @return
	 * @
	 */
	public String getStatus()  {
		
		return jsonObject.getString("status");
	}
	
	/**
	 * 获取status
	 * @param license_serial
	 * @return
	 * @
	 */
	public String getLicenseSerial()  {
		
		return jsonObject.getString("license_serial");
	}
	
	/**
	 * 获取function
	 * @param jsonObject
	 * @return
	 * @
	 */
	public Map<String, String> getFunction()  {
		
//		JSONObject jsonObject2 =  getLicenseInfo();
		Map<String, String> map = new HashMap<String, String>();
		
//		String str = jsonObject2.getString("function");
//		
//		String[] str1 = str.split(",");
//		for (int i = 0; i < str1.length; i++) {
//			String[] str2 = str1[i].split(":");
//			map.put(str2[0], str2[1]);
//		}
		return map;
	}
	
	
	/**
	 * 获取resource_limit
	 * @param jsonObject
	 * @return
	 * @
	 */
	public Map<String, String> getResourceLimit()  {
		
//		JSONObject jsonObject2 =  getLicenseInfo();
//		
		Map<String, String> map = new HashMap<String, String>();
//		String str = jsonObject2.getString("resource_limit");
//		
//		String[] str1 = str.split(",");
//		if(str1.length > 1){
//			
//			for (int i = 0; i < str1.length; i++) {
//				String[] str2 = str1[i].split(":");
//				map.put(str2[0], str2[1]);
//			}
//		}
		return map;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
}

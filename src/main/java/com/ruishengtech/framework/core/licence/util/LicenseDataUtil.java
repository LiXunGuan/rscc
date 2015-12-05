package com.ruishengtech.framework.core.licence.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPublicKey;

import org.json.JSONObject;

import com.ruishengtech.framework.core.PathUtil;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.licence.ChannelManager;

public class LicenseDataUtil {

	private static ByteBuf licenseMessage;
	
	private static ByteBuf hearBeatMessage;
	
	private static ByteBuf clientFirstMessage;
	
	/**
	 * 对客户端接收到的消息进行转换
	 * @param msg
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertMsg(Object msg) throws UnsupportedEncodingException{
		ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        buf.release();
        return new String(req,"UTF-8");
	}
	
	/**
	 * 请求License
	 */
	public static void requestLicense(){
		JSONObject json = createRequestLicenseData();
    	byte[] req = json.toString().getBytes();
    	licenseMessage = Unpooled.buffer(req.length);
    	licenseMessage.writeBytes(req);
    	Channel chan = ChannelManager.getInstance().getChannel();
    	if(chan != null){
    		chan.writeAndFlush(licenseMessage);
    	}else{
    		System.out.println("未连接到服务器...");
    	}
    }
	
	/**
	 * 处理服务器返回的数据
	 * @param content
	 * @throws Exception
	 */
	public static void handleBackData(String content) throws Exception {
		JSONObject json = new JSONObject(content);
		String command = json.getString("command");
		if("hearbeat".equals(command)){
			System.out.println("客户端接收到了服务器返回的心跳数据...");
		}else if("formallicense".equals(command) || "ls_licenes".equals(command)){
			base64FileAndKey(json, decideFolder()); // base64解码文件和公钥
		}
	}

	/**
	 * 判断文件夹
	 * @return
	 */
	private static String decideFolder() {
//		String folderPath = SpringPropertiesUtil.getProperty("sys.license");
		String folderPath = PathUtil.getBasePath();
		File folder = new File(folderPath);
		if (!folder.exists()) { // 不存在则创建
			folder.mkdirs();
		}
		return folderPath;
	}

	/**
	 * base64解码文件和公钥
	 * @param json
	 * @param folderpath
	 * @throws Exception
	 */
	private static void base64FileAndKey(JSONObject json, String folderpath) throws Exception {
		String base64file = json.getString("licenseData");
		String filepath = folderpath + File.separator + "license.txt";
		FileUtils.decoderBase64File(base64file, filepath);
		
		String base64key = json.getString("publicKeyData");
		String keypath = folderpath + File.separator + json.getString("keyname");
		FileUtils.decoderBase64File(base64key, keypath);
		// 公钥读取文件
		publicKeyReadFile(filepath, keypath);
	}

	/**
	 * 公钥读取文件
	 * @return
	 * @throws Exception 
	 */
	private static void publicKeyReadFile(String filepath, String keypath) throws Exception {
//		String data1 = FileUtils.readFile(filepath);
		byte[] data = FileUtils.getBytes(filepath);
		if(data != null){
			RSAPublicKey publickey = (RSAPublicKey)KeyHelper.readKeyPath(keypath);
			if(publickey != null){
//				String decodedData = KeyUtils.decryptByPublicKey(new String(data.getBytes(),"UTF-8"), publickey);
				byte[] decodedData = RSAUtil.PublicDECRYPT(publickey, data);
				String filedata = new String(decodedData, "utf-8");
				LicenseUtils.getInstance().reload(new JSONObject(filedata));
				System.out.println("客户端:产品中心返回的解密后的正式数据:" + LicenseUtils.getInstance().getJsonObject());
			}
		}else{
			System.out.println("文件没有数据...");
		}
	}

	/**
	 * 构建请求License数据
	 * @return
	 */
	private static JSONObject createRequestLicenseData() {
		JSONObject json = new JSONObject();
    	json.put("command", "license");
    	json.put("productName", LicenseUtils.productName);
    	json.put("fingerprint", ChannelManager.getInstance().getFingerprint());
    	return json;
	}
	
	/**
	 * 构建报道数据请求
	 * @return
	 */
	public static ByteBuf clientFirstMessage(){
    	JSONObject json = new JSONObject();
    	json.put("command", "firstMessage");
    	json.put("fingerprint", ChannelManager.getInstance().getFingerprint());
    	byte[] req = json.toString().getBytes();
    	clientFirstMessage = Unpooled.buffer(req.length);
    	clientFirstMessage.writeBytes(req);
    	return clientFirstMessage;
    }
    
    /**
     * 构建心跳数据请求
     * @return
     */
    public static ByteBuf hearBeatMessage(){
    	JSONObject json = new JSONObject();
    	json.put("command", "hearbeat");
    	json.put("fingerprint", ChannelManager.getInstance().getFingerprint());
    	byte[] req = json.toString().getBytes();
    	hearBeatMessage = Unpooled.buffer(req.length);
    	hearBeatMessage.writeBytes(req);
    	return hearBeatMessage;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	PathUtil.getBasePath();
	}
}

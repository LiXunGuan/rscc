package com.ruishengtech.framework.core.licence.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Decoder;

import com.ruishengtech.framework.core.SpringPropertiesUtil;

public class KeyHelper {

	/**
     * 得到公钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String key) throws Exception {
          byte[] keyBytes;
          keyBytes = (new BASE64Decoder()).decodeBuffer(key);
          X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
          return publicKey;
    }
    
    
    /**
     * 得到私钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String key) throws Exception {
          byte[] keyBytes;
          keyBytes = (new BASE64Decoder()).decodeBuffer(key);
          PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
          return privateKey;
    }
    
    public static Key readKey(String keyName) throws Exception{
    	String keypath = SpringPropertiesUtil.getProperty("sys.keyPath");
    	File file = new File(keypath + "/" + keyName);
    	FileInputStream fiskey = new FileInputStream(file);
        ObjectInputStream oiskey = new ObjectInputStream(fiskey);  
        Key key=(Key)oiskey.readObject();  
        oiskey.close();  
        fiskey.close();  
        return key;
    }  
    
    public static Key readKeyPath(String path) throws Exception{
    	File file = new File(path);
    	FileInputStream fiskey = new FileInputStream(file);
        ObjectInputStream oiskey = new ObjectInputStream(fiskey);  
        Key key=(Key)oiskey.readObject();  
        oiskey.close();  
        fiskey.close();  
        return key;
    }  
	
}

package com.ruishengtech.framework.core.licence.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.apache.commons.lang3.ArrayUtils;

public class RSAUtil {
	
	// 公钥加密  
    public static byte[] PublicEncrypt(KeyPair key,String str)throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());  
        byte[] enBytes = null;
        for (int i = 0; i < str.getBytes().length; i += 64) {  
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(str.getBytes(), i,i + 64));  
            enBytes = ArrayUtils.addAll(enBytes, doFinal);  
        }
        return enBytes;
    }  
      
    // 公钥解密  
    public static byte[] PublicDECRYPT (PublicKey key,byte[]  data)throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
        cipher.init(Cipher.DECRYPT_MODE, key);  
        
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < data.length; i += 128) {
//            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));
//            sb.append(new String(doFinal));
//        }
//        return sb.toString().getBytes();
        
        int chunkSize = 128;
        int idx = 0;
        ByteBuffer buf = ByteBuffer.allocate(data.length);
        while(idx < data.length) {
            int len = Math.min(data.length-idx, chunkSize);
            byte[] chunk = cipher.doFinal(data, idx, len);
            buf.put(chunk);
            idx += len;
        }
        return buf.array();
    }  
      
    //私钥加密  
    public static byte[] PrivateEncrypt (PrivateKey key, String str)throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, key);  
        
//        byte[] enBytes = null;
//        for (int i = 0; i < str.getBytes().length; i += 64) {  
//            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(str.getBytes(), i,i + 64));  
//            enBytes = ArrayUtils.addAll(enBytes, doFinal);  
//        }
//        return enBytes;
        
        int chunkSize = 117; // 1024 / 8 - 11(padding) = 117
        int encSize = (int) (Math.ceil(str.getBytes().length/117.0)*128);
        int idx = 0;
        ByteBuffer buf = ByteBuffer.allocate(encSize);
        while (idx < str.getBytes().length) {
            int len = Math.min(str.getBytes().length-idx, chunkSize);
            byte[] encChunk = cipher.doFinal(str.getBytes(), idx, len);
            buf.put(encChunk);
            idx += len;
        }
        return buf.array();
    }  
      
    //私钥解密  
    public static byte[] PrivateDECRYPT(KeyPair key,byte[]  data)throws Exception  {  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
        cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());  
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));
            sb.append(new String(doFinal));
        }
        return sb.toString().getBytes();
    }  
  
    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    } 
      
    public static void main(String args[]) throws Exception {  
//        byte[] da = getBytes("d:\\license\\license.txt");
//        String str = new String(da,"gbk");
//        
//        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");  
//        keyGen.initialize(1024);  
//        KeyPair key = keyGen.generateKeyPair();  
//        test t = new test();  
//        System.out.println("加密前原文:"+str);  
        
//        byte[] data = t.PublicEncrypt(key,str);   
//        System.out.println("私钥解密后:"+new String(t.PrivateDECRYPT(key,data)));  
//        
//        byte[] data1 = t.PrivateEncrypt(key,str);   
//        System.out.println("公钥解密后:"+new String(t.PublicDECRYPT(key,data1)));  
    }  
	
}

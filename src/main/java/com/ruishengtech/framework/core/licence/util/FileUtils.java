package com.ruishengtech.framework.core.licence.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.ruishengtech.framework.core.SpringPropertiesUtil;

public class FileUtils {
	/**
	 * 
	 * @param file
	 * @param conent
	 */
	public static void writeFile(String file, String conent) {
		if(delFile(file)){
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
				out.write(conent + "\r\n");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 清空文件
	 * @param strPath
	 * @return
	 */
	public static boolean delFile(String strPath) {
		File f = new File(strPath);
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static String readFile(String filePath){
        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                StringBuilder sb = new StringBuilder();
                while((lineTxt = bufferedReader.readLine()) != null){
                	sb.append(lineTxt);
                }
                read.close();
                return sb.toString();
            }else{
            	System.out.println("找不到指定的文件");
            }
        }catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
    	}
        return null;
    }
    
	@SuppressWarnings("resource")
	public static String readFileData(String filePath) throws IOException{
    	File file = new File(filePath);
        FileReader reader = null;
        char[] chars = null;
		try {
			reader = new FileReader(file);
			int fileLen = (int)file.length();
			chars = new char[fileLen];
			reader.read(chars);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return String.valueOf(chars);
    }
	
	public static boolean readKey(HttpServletRequest request) throws Exception{  
		boolean result = true;
		String prolicense = SpringPropertiesUtil.getProperty("sys.proCenterLicense");
		File serverFiles = new File(prolicense);
		if(!serverFiles.exists()) { // 不存在则创建文件夹
			serverFiles.mkdirs();
		}
		InputStream in = null;
		FileOutputStream foskey = null;
		try {
			in = request.getInputStream();
			File file = new File(prolicense + "/" + request.getHeader("KeyName"));
			foskey = new FileOutputStream(file);  
			byte[] keybufferOut = new byte[1024];
            int keybytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((keybytes = in.read(keybufferOut)) != -1) {
            	foskey.write(keybufferOut, 0, keybytes);
            }
		}catch (IOException e) {
			result = false;
			e.printStackTrace();
		}finally{
			try {
				if(in != null){
					in.close();
				}
				if(foskey != null){
					foskey.close();
				}
			} catch (IOException e) {
				result = false;
				e.printStackTrace();
			}
		}
		return result;
    }  
	
	public static boolean readFile(HttpServletRequest request){
		String prolicense = SpringPropertiesUtil.getProperty("sys.proCenterLicense");
		File serverFiles = new File(prolicense);
		if(!serverFiles.exists()) { // 不存在则创建文件夹
			serverFiles.mkdirs();
		}
		InputStream in = null;
		FileOutputStream fos = null;
		boolean result = true;
		try {
			in = request.getInputStream();
			String name = request.getHeader("FileName");
			// 本地生成文件
			File file = new File(prolicense + "/" + name);
			fos = new FileOutputStream(file);
	        byte[] buffer = new byte[1024];
	        int bytes = 0; 
	        while ((bytes = in.read(buffer)) != -1) {
	        	fos.write(buffer, 0, bytes);
	        }
	        fos.flush();
		}catch (IOException e) {
			result = false;
			e.printStackTrace();
		}finally{
			try {
				if(fos != null){
					fos.close();
				}
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				result = false;
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 将文件转成base64字符串
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer);
	}
	
	/**
	 * 将base64字符解码并保存文件
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}
	
	//获得指定文件的byte数组 
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
//            e.printStackTrace();
            return null;
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    } 
    
    //根据byte数组，生成文件 
    public static void getFile(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath + "\\" + fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        }catch (Exception e) {  
            e.printStackTrace();  
        }finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                   e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    } 
	
	public static void main(String[] args) {
		String filePath = "/productCenterLicense";
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if(!file.isDirectory()){
				System.out.println(file.getAbsolutePath()+file.getName());
			}
		}
	}
	
}

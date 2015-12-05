package com.ruishengtech.framework.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

/**
 * @author Frank
 *
 */
public class MethodTimeAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		StopWatch clock = new StopWatch();
		clock.start();
		Object result = invocation.proceed();

		clock.stop();

		@SuppressWarnings("rawtypes")
		Class[] params = invocation.getMethod().getParameterTypes();
		String[] simpleParams = new String[params.length];
		for (int i = 0; i < params.length; i++) {
			simpleParams[i] = params[i].getSimpleName();
		}
		
		System.out.println("方法:"+invocation.getMethod().getName() +"耗时:" + clock.getTime() + " ms ["+ invocation.getThis().getClass().getName() + "."+  "("+ StringUtils.join(simpleParams, ",") + ")] ");

		return result;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		readTxtFile("E://file.txt");
	}
	
	public static void readTxtFile(String filePath){
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        System.out.println(lineTxt);
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     
    }
	
	
}

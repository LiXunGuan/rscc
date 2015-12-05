package com.ruishengtech.framework.core;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class App {
	
	public static final String LEVEL = "level";

	public static String getDateRange(HttpServletRequest request){
		
		return request.getParameter(App.LEVEL);
	}
	
	public static void main(String[] args) {

		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.dir")+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"config"+File.separator);
		
	}
	
}

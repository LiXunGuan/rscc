package com.ruishengtech.framework.core.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;



public class PhoneUtil {

	public static String getNumberPhone(String phone) {
		String temp = "";
		phone = phone.trim();
		Pattern splitRegular = Pattern.compile("[^0-9]+");
		Pattern noZeroRegular = Pattern.compile("^[0]+");
		temp = splitRegular.matcher(phone).replaceAll("");
		temp = noZeroRegular.matcher(temp).replaceAll("");
		return temp;
	}
	
	public static String hideNumber(String phone) {
		if(StringUtils.isNotBlank(phone)){
			
			return phone.replaceAll("(\\d{3})\\d*", "$1********");
		}
		return phone;
	}
	
}

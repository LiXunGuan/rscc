package com.ruishengtech.framework.core.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Beans {

	public static String getProperty(Object o, String key) {
		try {
			return BeanUtils.getProperty(o, key);
		} catch (Exception e) {
			return "";
		}
	}

	public static Object getPropertyValue(Object o, String key) {
		try {
			return PropertyUtils.getProperty(o, key);
		} catch (Exception e) {
			return null;
		}
	}

	public static void setProperty(Object o, String key, Object value) {
		try {
			BeanUtils.setProperty(o, key, value);
		} catch (Exception e) {

		}
	}

	public static void setValue(Object o, String code, Class type, String value)
			throws ParseException {

		if (StringUtils.isBlank(value)) {
			return;
		}
		Object realValue = null;

		if (type.equals(Long.class)) {
			realValue = Long.valueOf(value);
		} else if (type.equals(Integer.class)) {
			realValue = Integer.valueOf(value);
		} else if (type.equals(Date.class)) {
			SimpleDateFormat yyyymmdddateformat = new SimpleDateFormat(
					"yyyy-MM-dd");
			realValue = yyyymmdddateformat.parse(value);
		} else {
			realValue = value;
		}

		try {
			BeanUtils.setProperty(o, code, realValue);
		} catch (Exception e) {
		}

	}

	public static boolean isWrapClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

}

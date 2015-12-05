package com.ruishengtech.rscc.crm.ui;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.rscc.crm.user.model.User;


/**
 * @author Frank
 *
 */
public class SessionUtil {

	
	public static String CURRENTUSER = "CURRENTUSER";
	
	
	/**
	 * 绑定分机
	 */
	public static String LOGINEXTEN = "LOGINEXTEN";
	
	/**
	 * 从session中取值
	 * 
	 * @param request
	 * @param sessionTag
	 * @return
	 */
	public static Object getSession(HttpServletRequest request, String sessionTag) {

		if (StringUtils.isNotBlank(sessionTag)) {
			return request.getSession().getAttribute(sessionTag);
		}
		return null;
	}

	/**
	 * 设置session值
	 * @param request
	 * @param sessionTag
	 * @param sessionValue
	 */
	public static void setSession(HttpServletRequest request, String sessionTag,
			Object sessionValue) {

		if (StringUtils.isNotBlank(sessionTag) && null != sessionValue) {
			request.getSession().setAttribute(sessionTag, sessionValue);
		}
	}
	
	/**
	 * 获取当前登录用户
	 * @param request
	 * @return
	 */
	public static User getCurrentUser(HttpServletRequest request){
		
		User user = (User) getSession(request, SessionUtil.CURRENTUSER);
		
		if(null != user){
			return user;
		}
		return null;
	}

}

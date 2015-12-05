package com.ruishengtech.rscc.crm.ui;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ruishengtech.framework.core.util.OneToOneMap;

public class SysSessionListener implements HttpSessionListener {

	//注意这里invalidate后不会从map中消失，可能会产生bug，我看还是加个session监听器靠谱
	public static OneToOneMap<String, HttpSession> sessionMap = new OneToOneMap<>();
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		sessionMap.removeByValue(se.getSession());
	}

	
	
}

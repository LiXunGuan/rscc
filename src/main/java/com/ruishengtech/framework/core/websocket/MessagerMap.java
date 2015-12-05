package com.ruishengtech.framework.core.websocket;

import java.util.HashMap;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author Wangyao
 *
 */
public class MessagerMap extends HashMap<String,WebSocketSession>{

	private static final long serialVersionUID = -9057522681477545928L;

	private HashMap<WebSocketSession,String> sessionMap = new HashMap<WebSocketSession,String>();
	
	public WebSocketSession put(String key, WebSocketSession value) {
		sessionMap.put(value, key);
		return super.put(key, value);
	}
	
	public WebSocketSession remove(String key) {
		WebSocketSession v = super.remove(key);
		this.sessionMap.remove(v);
		return v;
    }
	
	public String remove(WebSocketSession value) {
		String k = this.sessionMap.remove(value);
		super.remove(k);
		return k;
    }
	
	public String getKey(WebSocketSession value) {
		return sessionMap.get(value);
	}
	
}

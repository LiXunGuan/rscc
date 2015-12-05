package com.ruishengtech.framework.core.websocket;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Wangyao
 *
 */
public class WebSocketContext {
	
	private static WebSocketContext myselfInstance = new WebSocketContext();
	
	public static WebSocketContext getInstance() {
        return myselfInstance;
    }
	
	private List<Class<?>> webSocketClassHolder = new ArrayList<Class<?>>();
	
	public void addWebSocket(Class<?> clazz) {
		webSocketClassHolder.add(clazz);
    }
	
	public List<Class<?>> getWebSocketClassHolder() {
		return this.webSocketClassHolder;
	}
	
	public void init() {
	}
	
}

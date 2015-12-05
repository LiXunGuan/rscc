package com.ruishengtech.framework.core.websocket.service;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.ruishengtech.framework.core.websocket.MessagerMap;

/**
 * @author Wangyao
 *
 */
public class MessageService {
	
	MessagerMap messagerMap;
	
	public void addUser(String user, WebSocketSession session) {
		messagerMap.put(user, session);
	}
	
	public void remove(String user){
		messagerMap.remove(user);
	}
	
	public void remove(WebSocketSession session){
		messagerMap.remove(session);
	}
	
	public void send(String user, String json) throws IOException {
		messagerMap.get(user).sendMessage(new TextMessage(json) );
	}
}

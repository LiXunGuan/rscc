package com.ruishengtech.framework.core.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.websocket.service.MessageService;


/**
 * @author Wangyao
 *
 */
public class WebSocketMessageHandler extends TextWebSocketHandler {

	protected MessageService messageService;
	
	public WebSocketMessageHandler() {
		messageService = ApplicationHelper.getApplicationContext().getBean(MessageService.class);
	}
	
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		System.out.println("connect to the websocket success......");
		session.sendMessage(new TextMessage("Server:connected OK!"));

	}

	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		TextMessage returnMessage = new TextMessage(message.getPayload()
				+ " received at server");
		session.sendMessage(returnMessage);

	}

	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		System.out.println("websocket connection closed......");

	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		System.out.println("websocket connection closed......");

	}

	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}

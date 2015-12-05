package com.ruishengtech.framework.core.websocket.handler;

import java.io.IOException;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.websocket.WebSocketMsg;
import com.ruishengtech.framework.core.websocket.WebSocketUser;
import com.ruishengtech.framework.core.websocket.service.BrokerService;


/**
 * @author Wangyao
 *
 */
public class WebSocketBrokerHandler extends TextWebSocketHandler {
	
	protected BrokerService brokerService;
	
	protected String BrokerURL = "/";
	
	public String getBrokerURL() {
		return BrokerURL;
	}

	public void setBrokerURL(String brokerURL) {
		BrokerURL = brokerURL;
	}

	public WebSocketBrokerHandler() {
		brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
	}
	
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		brokerService.addUser(getBrokerURL(),WebSocketUser.getUserFromSession(session).getUserName(),session);
//		System.out.println("connect to the websocket success......");
	}

	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws IOException {
		try{
			WebSocketMsg d = new WebSocketMsg(WebSocketUser.getUserFromSession(session).getUserName(),message);
			handleDestination(d);
		} catch(Exception e) {
			brokerService.send(message.toString());
		}
//		System.out.println(message.getPayload() + "received success......");
	}

	//用于接收Destination数据，子类可以重写
	protected void handleDestination(WebSocketMsg d) throws IOException {
		brokerService.send(d);
	}

	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		brokerService.remove(session);
//		System.out.println("websocket connection closed......");
	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		brokerService.remove(session);
//		System.out.println("websocket connection closed......");
	}

	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}

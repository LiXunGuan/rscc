package com.ruishengtech.rscc.crm.ui.user;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.ruishengtech.framework.core.websocket.WebSocketMsg;
import com.ruishengtech.framework.core.websocket.WebSocketUser;
import com.ruishengtech.framework.core.websocket.handler.WebSocketBrokerHandler;
import com.ruishengtech.rscc.crm.ui.SysSessionListener;

public class UserBrokerHandler extends WebSocketBrokerHandler{
	
	//连接可用后
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		WebSocketUser o = WebSocketUser.getUserFromSession(session);
		if(o != null) {
			//这里要判断一下这个用户在sessionmap中是否已有，有的话sessionid是否相同，防止http session被挤的时候websocket还在
			//第二种方法是在handshake时拦截，那里可以直接取到sessionid
			//第三种方法：登录时如果session被挤，且被挤的session与挤他的session的id不同，则把原来的session清除
			if (!isKickout(o, session)) {
				brokerService.addUser(getBrokerURL(), o.getUserName(),session);
			}
		}
	}

	//发送消息
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws IOException {
		WebSocketMsg d = new WebSocketMsg(message);
		brokerService.send(d);
	}

	//传输出错时
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		brokerService.remove(session);
	}

	//连接关闭时
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
 		brokerService.remove(session);
	}
	
	//是否被踢了,可以加在这里，但是这样不能清除他的session,只能发给他让他知道被踢了
	private boolean isKickout(WebSocketUser user, WebSocketSession session) {
		HttpSession userSession = SysSessionListener.sessionMap.get(user.getUserName());
		String cookie = session.getHandshakeHeaders().get("cookie").get(0);
		String sessionId = cookie.substring(cookie.indexOf("JSESSIONID=") + "JSESSIONID=".length(), cookie.indexOf(";", cookie.indexOf("JSESSIONID=") + "JSESSIONID=".length()));
		if (userSession != null && !userSession.getId().equals(sessionId)) {
			try {
				session.sendMessage(new TextMessage(new JSONObject().put("type", "kickout").toString()));
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
}

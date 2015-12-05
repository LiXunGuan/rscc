package com.ruishengtech.rscc.crm.ui.user;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;

import com.ruishengtech.framework.core.websocket.interceptor.HandshakeInterceptor;

public class UserBrokerInterceptor extends HandshakeInterceptor{
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		//这种方式虽然可以直接清除他的session，但是还会导致一定几率发生socket错误
//		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//		HttpSession session = servletRequest.getServletRequest().getSession(false);
//		if (SessionUtil.getCurrentUser(servletRequest.getServletRequest()) == null) {
//			return super.beforeHandshake(request, response, wsHandler, attributes);
//		}
//		String userName = SessionUtil.getCurrentUser(servletRequest.getServletRequest()).getLoginName();
//		HttpSession loginedSession = IndexController.sessionMap.get(userName);
//		if (loginedSession != null && !session.getId().equals(loginedSession.getId())) {
//			session.invalidate();
//			return false;
//		}
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}
}

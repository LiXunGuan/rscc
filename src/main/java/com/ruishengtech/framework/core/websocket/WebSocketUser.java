package com.ruishengtech.framework.core.websocket;

import java.util.Date;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author Wangyao
 *
 */
public class WebSocketUser {

	private String userName;

    private Date createTime;

	public WebSocketUser(String userName) {
		this.userName = userName;
		this.createTime = new Date();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static WebSocketUser getUserFromName(String userName) {
		return new WebSocketUser(userName);
	}
	
	public static WebSocketUser getUserFromSession(WebSocketSession session) {
		return (WebSocketUser)session.getAttributes().get("websocketUser");
	}
	
	public static String WEBSOCKETUSER = "websocketUser";
	
}

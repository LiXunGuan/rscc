package com.ruishengtech.framework.core.websocket;

import org.json.JSONObject;
import org.springframework.web.socket.WebSocketMessage;

/**
 * @author Wangyao
 *
 */
public class WebSocketMsg {
	
	public String toURL;
	
	public String toUser;
	
	public String json;

	public String fromUser;
	
	public String getToURL() {
		return toURL;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public void setToURL(String toURL) {
		this.toURL = toURL;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	public WebSocketMsg(WebSocketMessage<?> message) {
		JSONObject object = new JSONObject(message.getPayload().toString());
		this.toURL = object.getString("toURL");
		this.toUser = object.getString("toUser");
		if(object.has("fromUser"))
			this.fromUser = object.getString("fromUser");
		else
			this.fromUser = "System";
		String json = object.getString("json");
		this.json = json.getBytes().length>json.length() ? String.format("%-10s", json):json;
	}
	
	public WebSocketMsg(String fromUser,WebSocketMessage<?> message) {
		this(message);
		this.fromUser = fromUser;
	}
	
}

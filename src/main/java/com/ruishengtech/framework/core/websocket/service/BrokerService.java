package com.ruishengtech.framework.core.websocket.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.ruishengtech.framework.core.websocket.BrokerMap;
import com.ruishengtech.framework.core.websocket.MessagerMap;
import com.ruishengtech.framework.core.websocket.WebSocketMsg;


/**
 * @author Wangyao
 *	待改进：sendToUser可以不写URL，获取User与Session的对应map
 */
@Service
public class BrokerService {
	
	private BrokerMap brokerMap = new BrokerMap();
	
	public void add(String brokerUrl){
		brokerMap.put(brokerUrl, new MessagerMap());
	}
	
	public void addUser(String brokerUrl, String user, WebSocketSession session) throws IOException {
		MessagerMap m = brokerMap.get(brokerUrl);
		if(m==null) {
			m = new MessagerMap();
			brokerMap.put(brokerUrl, m);
		}
		m.put(user, session);
//		this.sendToUsersExclude(brokerUrl, "用户：" + user + " 登录!，您可以与他聊天了！", user);
	}
	
	public void remove(String brokerUrl){
		brokerMap.remove(brokerUrl);
	}
	
	public void remove(String brokerUrl, String user) {
		MessagerMap m = brokerMap.get(brokerUrl);
		if(m!=null)
			m.remove(user);
	}
	
	public void remove(String brokerUrl, WebSocketSession session) {
		MessagerMap m = brokerMap.get(brokerUrl);
		if(m!=null)
			m.remove(session);
	}
	
	public void remove(WebSocketSession session) {
		for(MessagerMap m:brokerMap.values())
			if(m.containsValue(session))
				m.remove(session);
	}
	
	public int send(String brokerUrl, String json) throws IOException {
		int cnt = 0;
		MessagerMap m = brokerMap.get(brokerUrl);
		if(m==null)
			return cnt;
		for(WebSocketSession s:m.values()){
			synchronized (s) {
				s.sendMessage(new TextMessage(json));
				cnt++;
			}
		}
		return cnt;
	}
	
	public int send(String json) throws IOException {
		int cnt = 0;
		Set<String> s = this.getUrls();
		for(String u:s) {
			MessagerMap m = brokerMap.get(u);
			if(m!=null) {
				for(WebSocketSession w:m.values()){
					w.sendMessage(new TextMessage(json));
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	public int sendToUsersExclude(String brokerUrl, String json, String... user) throws IOException {
		int cnt = 0;
		MessagerMap m = brokerMap.get(brokerUrl);
		if(m==null)
			return cnt;
		for(WebSocketSession s:m.values()) {
			if(!Arrays.asList(user).contains(m.getKey(s))) {
				s.sendMessage(new TextMessage(json));
				cnt++;
			}
		}
		return cnt;
	}
	
	public boolean sendToUser(String brokerUrl, String user, String json) throws IOException {
		//根据url取到MessagerMap（是个一一映射）
		MessagerMap m = brokerMap.get(brokerUrl);
		//messageMap为空，或者不包含这个用户
		if(m==null || !m.containsKey(user))
			return false;
		//取到websocket的session
		WebSocketSession s = m.get(user);
		if (s == null)
			return false;
		//同步处理，防止多个线程对一个用户发送消息
		synchronized (s) {
			//给用户发送消息
			s.sendMessage(new TextMessage(json));
		}
		return true;
	}
	
	public int sendToUsers(String brokerUrl, String json, String... users) throws IOException {
		int cnt = 0;
		MessagerMap m = brokerMap.get(brokerUrl);
		if(m==null)
			return cnt;
		for(String u:users) {
			if(m.containsKey(u)) {
				WebSocketSession s = m.get(u);
				synchronized (s) {
					s.sendMessage(new TextMessage(json));
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	public int sendToUsers(String brokerUrl, String json, Collection<String> users) throws IOException {
		int cnt = 0;
		MessagerMap m = brokerMap.get(brokerUrl);
		if(m==null)
			return cnt;
		for(String u:users) {
			if(m.containsKey(u)) {
				WebSocketSession s = m.get(u);
				synchronized (s) {
					s.sendMessage(new TextMessage(json));
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	public Set<String> getOnlineUsers(String brokerUrl) {
		MessagerMap m = brokerMap.get(brokerUrl);
		if(m==null)
			return null;
		return m.keySet();
	}
	
	public Set<String> getUrls() {
		return brokerMap.keySet();
	}

	public void send(WebSocketMsg d) throws IOException {
		if(StringUtils.isBlank(d.toURL))
			this.send(d.json);
		else if(StringUtils.isBlank(d.toUser))
			this.send(d.toURL, d.json);
		else
			this.sendToUser(d.toURL, d.toUser, d.json);
	}
	
}

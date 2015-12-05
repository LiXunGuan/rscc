package com.ruishengtech.framework.core.licence;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;


public class ChannelManager {

	private Map<String, Channel> channelMap = new HashMap<String, Channel>();
	
	private String fingerprint;
	
	private Channel channel;
	
	private final static ChannelManager instance = new ChannelManager();
	
	public static ChannelManager getInstance(){
		return instance;
	}
	
	private ChannelManager(){
		init();
	}
	
	public void init(){
		
	}

	public Map<String, Channel> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, Channel> channelMap) {
		this.channelMap = channelMap;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}

package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

//会议室，记录会议室ID，创建者，所有成员与成员开始通话时间
public class Confrence {
	
	//以号码为key
	private Map<String,JSONObject> members;
	
	private String confrenceId;
	
	private String owner;
	
	public Map<String, JSONObject> getMembers() {
		return members;
	}

	public void setMembers(Map<String, JSONObject> members) {
		this.members = members;
	}

	public Confrence() {
		members = new LinkedHashMap<String,JSONObject>();
	}
	
	public Confrence(String confrenceId, String owner) {
		this();
		this.confrenceId = confrenceId;
		this.owner = owner;
	}
	
	public String getConfrenceId() {
		return confrenceId;
	}

	public void setConfrenceId(String confrenceId) {
		this.confrenceId = confrenceId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public JSONObject add(String phone, JSONObject member){
//		String temp = phone;//错误代码
//		while(this.contain(temp))
//			temp = "o" + temp;
//		if(temp.startsWith("o") && ("3".equals(this.get(phone).optString("invitation_state")) || "4".equals(this.get(phone).optString("invitation_state"))))
//			this.members.put(temp, this.get(phone));
		return this.members.put(phone, member);
	}
	
	public JSONObject remove(String phone){
		return this.members.remove(phone);
	}
	
	public JSONObject kickout(String phone){
		JSONObject json = this.members.get(phone);
		//1为邀请中，2通话中
		json.put("invitation_state", "4");//踢出的
		return this.members.get(phone);
	}
	
	public JSONObject get(String phone){
		return this.members.get(phone);
	}
	
	public boolean contain(String phone){
		return this.members.containsKey(phone);
	}
	
	public int count(){
		return this.members.size();
	}
	
	public void clear(){
//		this.members.clear();
		for (JSONObject j : this.members.values()) {
			if(!"4".equals(j.getString("invitation_state"))) //如果是之前被踢出去的，就不置为主动出去的
				j.put("invitation_state", "3");//主动出去的
		}
	}
	
	//可以存为json，也可以存为内部类，这里使用json对象
//	private class Member{
//		private String extension;
//	}
}

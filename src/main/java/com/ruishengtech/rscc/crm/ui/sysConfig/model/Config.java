package com.ruishengtech.rscc.crm.ui.sysConfig.model;

public class Config {
	
	
	/**
	 * 网络信息
	 */
	private String ipAddress;
	
	private String netMask;
	
	private String gateway;
	
	
	/**
	 * 管理员信息
	 */
	private String username;
	
	private String userpassword;
	
	private String email;
	

	/**
	 * 分机设置
	 */
	private String agentBegin;
	
	private String agentEnd;
	
	private String agentNumber;
	
	//静态密码
	private String agentNumbers;
	
	
	/**
	 * 外线设置
	 */
	private String sipTrunkName;
	
	private String codec;
	
	private String sipTrunkIP;
	
	private String isRegist;
	
	private String agUserName;
	
	private String agPassword;


	/**
	 * 外线和接入号
	 */
	private String accessNumber;
	
	private String concurrency;
	
	private String callIn;
	
	private String callOut;
	
	
	public String getCallIn() {
		return this.callIn;
	}

	public void setCallIn(String callIn) {
		this.callIn = callIn;
	}

	public String getCallOut() {
		return this.callOut;
	}

	public void setCallOut(String callOut) {
		this.callOut = callOut;
	}

	public String getSipTrunkName() {
		return this.sipTrunkName;
	}

	public void setSipTrunkName(String sipTrunkName) {
		this.sipTrunkName = sipTrunkName;
	}

	public String getAccessNumber() {
		return this.accessNumber;
	}

	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}

	public String getConcurrency() {
		return this.concurrency;
	}

	public void setConcurrency(String concurrency) {
		this.concurrency = concurrency;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getNetMask() {
		return this.netMask;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public String getGateway() {
		return this.gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAgentBegin() {
		return this.agentBegin;
	}

	public void setAgentBegin(String agentBegin) {
		this.agentBegin = agentBegin;
	}

	public String getAgentEnd() {
		return this.agentEnd;
	}

	public void setAgentEnd(String agentEnd) {
		this.agentEnd = agentEnd;
	}

	public String getAgentNumber() {
		return this.agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}

	public String getCodec() {
		return this.codec;
	}

	public void setCodec(String codec) {
		this.codec = codec;
	}

	public String getSipTrunkIP() {
		return this.sipTrunkIP;
	}

	public void setSipTrunkIP(String sipTrunkIP) {
		this.sipTrunkIP = sipTrunkIP;
	}

	public String getIsRegist() {
		return this.isRegist;
	}

	public void setIsRegist(String isRegist) {
		this.isRegist = isRegist;
	}

	public String getAgUserName() {
		return this.agUserName;
	}

	public void setAgUserName(String agUserName) {
		this.agUserName = agUserName;
	}

	public String getAgPassword() {
		return this.agPassword;
	}

	public void setAgPassword(String agPassword) {
		this.agPassword = agPassword;
	}

	public String getAgentNumbers() {
		return this.agentNumbers;
	}

	public void setAgentNumbers(String agentNumbers) {
		this.agentNumbers = agentNumbers;
	}

	public String getUserpassword() {
		return this.userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	


}

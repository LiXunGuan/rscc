package com.ruishengtech.rscc.crm.ui.report;

public class Infos {
	/**
	 * 当前的session用户
	 */
	public static final String USER = "THE_CURRENT_SESSION_USER";
	
	/**
	 * 接通
	 */
	public static final int CALL_RESULT_ANSWER=1;
	/**
	 * 不接通
	 */
	public static final int CALL_RESULT_NOANSWER=0;
	
	/**
	 * ip地址
	 * {@value}
	 */
	public static final String LINUX_IPADDR="ipAddr";
	/**
	 * mac地址
	 * {@value}
	 */
	public static final String LINUX_MACADDR="macAddr";
	/**
	 * 子网掩码
	 * {@value}
	 */
	public static final String LINUX_NETMASK="netMask";
	/**
	 * nginx地址
	 * {@value}
	 */
	public static final String LINUX_NGINX="nginx";
	
	/**
	 * 默认网关
	 * {@value}
	 */
	public static final String LINUX_GATEWAY="gateWay";

	/**
	 * sipPort
	 * {@value}
	 */
	public static final String FREESWITCH_SIP_PORT="sipPort";
	
}

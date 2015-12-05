package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author chengxin
 *
 */
public class Domains {
	
	/**
	 * 群呼任务呼叫策略
	 */
	public static final String GROUPCALL_STRATEGY_STATIC = "static";
	public static final String GROUPCALL_STRATEGY_DYNAMIC = "dynamic";
	public static final String GROUPCALL_STRATEGY_AGENT = "agent_one_by_one";

	/**
	 * 群呼任务状态
	 */
	public static final String GROUPCALL_STATIC_NEW = "New";
	public static final String GROUPCALL_STATIC_RUNNING = "Running";
	public static final String GROUPCALL_STATIC_STOPPING = "Stopping";
	public static final String GROUPCALL_STATIC_STOPPED = "Stopped";
	
	/**
	 * 外线编码
	 */
	public static final String CODEC_G729 = "G729";
	public static final String CODEC_PCMU = "PCMU";
	public static final String CODEC_PCMA = "PCMA";
	public static final String CODEC_GSM = "GSM";
	public static final String TAB = "TAB";
	public static final String TABSELECTED = "TABSELECTED";


	public static Map<String, String> CODECSTRING = new LinkedHashMap<String, String>() {
		{
			put(CODEC_G729, "G729");
			put(CODEC_PCMU, "PCMU");
			put(CODEC_PCMA, "PCMA");
			put(CODEC_GSM, "GSM");

		}

	};
	
	
	/**
	 * 坐席状态
	 */
	public static final String STATUS_LOGGEDOUT="Logged Out";
	public static final String STATUS_AVAILABLE="Available";
	public static final String STATUS_OFF="offline";
	
	public static Map<String,String> AGENTSTATUS =new LinkedHashMap<String,String>(){
		{
			put(STATUS_LOGGEDOUT,"置忙");
			put(STATUS_AVAILABLE,"置闲");
			put(STATUS_OFF,"下线");
		}
	};
	public static Map<String,String> AGENTSTATE =new LinkedHashMap<String,String>(){
		{
			put("Idle","空闲");
			put("Waiting","等待呼叫");
			put("Receiving","振铃");
			put("In a queue call","通话中");
		}
	};

	/**
	 * 通话状态
	 */
	public static final String EXTEN_STATE_DOWN ="down";
	public static final String EXTEN_STATE_RINGING ="ringing";
	public static final String EXTEN_STATE_UP ="up";
	public static final String EXTEN_STATE_DAILING ="dailing";
	public static final String EXTEN_STATE_NOTREG ="notregistered";

	public static Map<String,String> EXTENSTASTUS =new LinkedHashMap<String,String>(){
		{
			put(EXTEN_STATE_DOWN,"未通话");
			put(EXTEN_STATE_RINGING,"振铃");
			put(EXTEN_STATE_UP,"通话中 ");
			put(EXTEN_STATE_DAILING,"呼叫中 ");
			put(EXTEN_STATE_NOTREG,"未注册 ");
		}
	};
	
	
	
	/**
	 * 呼叫方向
	 */
	public static final String DIRECTIONS_OUT="o";
	public static final String DIRECTIONS_IN="i";
	
	public static Map<String,String> DIRECTIONS =new LinkedHashMap<String,String>(){
		{
			put(DIRECTIONS_OUT,"被叫");
			put(DIRECTIONS_IN,"主叫");
		}
	};
	public static Map<String,String> DIRECTIONSMAP =new LinkedHashMap<String,String>(){
		{
			put("master","主叫");
			put("slave","被叫");
		}
	};

	/**
	 * 是否注册
	 */
	public static final String REGISTRATIONS_OFF="未注册";
	public static final String REGISTRATIONS_ON="注册";
	
	
	/**
	 * 报表类型
	 */
	public static final String SELECTION_YEAR="1";
	public static final String SELECTION_MONTH="2";
	public static final String SELECTION_WEEK="3";
	public static final String SELECTION_DAY="4";
	public static final String SELECTION_PERIOD="5";
	
	public static final Map<String,String> SELECTION_DATES =new LinkedHashMap<String,String>(){
		{
			put(SELECTION_YEAR,"年报");
			put(SELECTION_MONTH,"月报");
//			put(SELECTION_WEEK,"周报");
			put(SELECTION_DAY,"日报");
//			put(SELECTION_PERIOD,"时报");
		}
	};
	
	public static final String SELECTION2_YEAR="1";
	public static final String SELECTION2_MONTH="2";
	public static final String SELECTION2_WEEK="3";
	public static final String SELECTION2_DAY="4";
	public static final String SELECTION2_PERIOD="5";
	
	public static final Map<String,String> SELECTION2_DATES =new LinkedHashMap<String,String>(){
		{
			put(SELECTION_YEAR,"年报");
			put(SELECTION_MONTH,"月报");
//			put(SELECTION_WEEK,"周报");
			put(SELECTION_DAY,"日报");
		}
	};
	
	/**
	 * 时段
	 */
	public static final String  PARAGRAPH_1 = "1";
	public static final String  PARAGRAPH_2 = "2";
	public static final String  PARAGRAPH_3 = "3";
	public static final String  PARAGRAPH_4 = "4";
	public static final String  PARAGRAPH_5 = "5";
	public static final String  PARAGRAPH_6 = "6";
	public static final String  PARAGRAPH_7 = "7";
	public static final String  PARAGRAPH_8 = "8";
	public static final String  PARAGRAPH_9 = "9";
	
	public static final String  PARAGRAPH_10 = "10";
	public static final String  PARAGRAPH_11 = "11";
	public static final String  PARAGRAPH_12 = "12";
	public static final String  PARAGRAPH_13 = "13";
	public static final String  PARAGRAPH_14 = "14";
	public static final String  PARAGRAPH_15 = "15";
	public static final String  PARAGRAPH_16 = "16";
	public static final String  PARAGRAPH_17 = "17";
	public static final String  PARAGRAPH_18 = "18";
	public static final String  PARAGRAPH_19 = "19";
	
	public static final String  PARAGRAPH_20 = "20";
	public static final String  PARAGRAPH_21 = "21";
	public static final String  PARAGRAPH_22 = "22";
	public static final String  PARAGRAPH_23 = "23";
	public static final String  PARAGRAPH_24 = "24";
	public static final String  PARAGRAPH_25 = "25";
	public static final String  PARAGRAPH_26 = "26";
	public static final String  PARAGRAPH_27 = "27";
	public static final String  PARAGRAPH_28 = "28";
	public static final String  PARAGRAPH_29 = "29";
	
	public static final String  PARAGRAPH_30 = "30";
	public static final String  PARAGRAPH_31 = "31";
	public static final String  PARAGRAPH_32 = "32";
	public static final String  PARAGRAPH_33 = "33";
	public static final String  PARAGRAPH_34 = "34";
	public static final String  PARAGRAPH_35 = "35";
	public static final String  PARAGRAPH_36 = "36";
	public static final String  PARAGRAPH_37 = "37";
	public static final String  PARAGRAPH_38 = "38";
	public static final String  PARAGRAPH_39 = "39";
	
	public static final String  PARAGRAPH_40 = "40";
	public static final String  PARAGRAPH_41 = "41";
	public static final String  PARAGRAPH_42 = "42";
	public static final String  PARAGRAPH_43 = "43";
	public static final String  PARAGRAPH_44 = "44";
	public static final String  PARAGRAPH_45 = "45";
	public static final String  PARAGRAPH_46 = "46";
	public static final String  PARAGRAPH_47 = "47";
	public static final String  PARAGRAPH_48 = "48";

	public static Map<String, String> PARAGRAPHS = new LinkedHashMap<String, String>() {
		{
			put( PARAGRAPH_1, "00:00:00-00:29:59");
			put( PARAGRAPH_2, "00:30:00-00:59:59");
			put( PARAGRAPH_3, "01:00:00-01:29:59");
			put( PARAGRAPH_4, "01:30:00-01:59:59");
			put( PARAGRAPH_5, "02:00:00-02:29:59");
			put( PARAGRAPH_6, "02:30:00-02:59:59");
			put( PARAGRAPH_7, "03:00:00-03:29:59");
			put( PARAGRAPH_8, "03:30:00-03:59:59");
			put( PARAGRAPH_9, "04:00:00-04:29:59");
			put( PARAGRAPH_10, "04:30:00-04:59:59");

			put( PARAGRAPH_11, "05:00:00-05:29:59");
			put( PARAGRAPH_12, "05:30:00-05:59:59");
			put( PARAGRAPH_13, "06:00:00-06:29:59");
			put( PARAGRAPH_14, "06:30:00-06:59:59");
			put( PARAGRAPH_15, "07:00:00-07:29:59");
			put( PARAGRAPH_16, "07:30:00-07:59:59");
			put( PARAGRAPH_17, "08:00:00-08:29:59");
			put( PARAGRAPH_18, "08:30:00-08:59:59");
			put( PARAGRAPH_19, "09:00:00-09:29:59");
			put( PARAGRAPH_20, "09:30:00-09:59:59");

			put( PARAGRAPH_21, "10:00:00-10:29:59");
			put( PARAGRAPH_22, "10:30:00-10:59:59");
			put( PARAGRAPH_23, "11:00:00-11:29:59");
			put( PARAGRAPH_24, "11:30:00-11:59:59");
			put( PARAGRAPH_25, "12:00:00-12:29:59");
			put( PARAGRAPH_26, "12:30:00-12:59:59");
			put( PARAGRAPH_27, "13:00:00-13:29:59");
			put( PARAGRAPH_28, "13:30:00-13:59:59");
			put( PARAGRAPH_29, "14:00:00-14:29:59");
			put( PARAGRAPH_30, "14:30:00-14:59:59");
			
			put( PARAGRAPH_31, "15:00:00-15:29:59");
			put( PARAGRAPH_32, "15:30:00-15:59:59");
			put( PARAGRAPH_33, "16:00:00-16:29:59");
			put( PARAGRAPH_34, "16:30:00-16:59:59");
			put( PARAGRAPH_35, "17:00:00-17:29:59");
			put( PARAGRAPH_36, "17:30:00-17:59:59");
			put( PARAGRAPH_37, "18:00:00-18:29:59");
			put( PARAGRAPH_38, "18:30:00-18:59:59");
			put( PARAGRAPH_39, "19:00:00-19:29:59");
			put( PARAGRAPH_40, "19:30:00-19:59:59");

			put( PARAGRAPH_41, "20:00:00-20:29:59");
			put( PARAGRAPH_42, "20:30:00-20:59:59");
			put( PARAGRAPH_43, "21:00:00-21:29:59");
			put( PARAGRAPH_44, "21:30:00-21:59:59");
			put( PARAGRAPH_45, "22:00:00-22:29:59");
			put( PARAGRAPH_46, "22:30:00-22:59:59");
			put( PARAGRAPH_47, "23:00:00-23:29:59");
			put( PARAGRAPH_48, "23:30:00-23:59:59");
		}

	};

}

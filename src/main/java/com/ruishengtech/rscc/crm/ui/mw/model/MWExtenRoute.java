package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

@Table(name = "mw.mw_exten_route")
public class MWExtenRoute extends CommonDbBean {

	@Column(meta = "VARCHAR(30)")
	private String name;

	@Column(meta = "VARCHAR(30)")
	private String extension;

	@Column(meta = "VARCHAR(10)")
	private String type;

	@Column(meta = "INT")
	private Long destId;

	@NColumn(meta = "VARCHAR(300)")
	private String destString;

	@NColumn(meta = "INT")
	private Integer can_del;

	/**
	 * dialpan: dial IVR : ivr callcenter: cc out:out
	 */
	public static String ROUTER_TYPE_DIALPLAN = "DIALPLAN";

	public static String ROUTER_TYPE_IVR = "IVR";

	public static String ROUTER_TYPE_IVR_EXT = "IVR_EXT";

	public static String ROUTER_TYPE_CALLCENTER = "CALLCENTER";

	public static String ROUTER_TYPE_CONFERENCE = "CONFERENCE";

	public static String ROUTER_TYPE_SIPUSER = "SIPUSER";

	public static String ROUTER_TYPE_AGENT = "AGENT";

	public static String ROUTER_TYPE_OUTBOUNDNUMBER = "OUTBOUNDNUMBER";
	
	public static String ROUTER_TYPE_CUSTOMER = "CUSTOMER";

	public static Map<String, String> ROUTER = new LinkedHashMap<String, String>() {
		{
			put(ROUTER_TYPE_SIPUSER, "分机");
			put(ROUTER_TYPE_CALLCENTER, "技能组");
			put(ROUTER_TYPE_IVR, "IVR");
			put(ROUTER_TYPE_DIALPLAN, "拨号计划");
//			put(ROUTER_TYPE_CUSTOMER, "自定义");
			//put(ROUTER_TYPE_CONFERENCE, "会议室");
			put(ROUTER_TYPE_AGENT, "坐席人员");
		}
	};

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDestString() {
		return destString;
	}

	public void setDestString(String destString) {
		this.destString = destString;
	}

	public Long getDestId() {
		return destId;
	}

	public void setDestId(Long destId) {
		this.destId = destId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCan_del() {
		return can_del;
	}

	public void setCan_del(Integer can_del) {
		this.can_del = can_del;
	}

}
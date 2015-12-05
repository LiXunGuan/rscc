package com.ruishengtech.rscc.crm.record.model;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank
 *
 */
@Table(name = "cstm_record")
public class Record extends CommonDbBean{ 

    @Column(meta = "VARCHAR(64)", column = "call_session_uuid")
    private String call_session_uuid;

    @Column(meta = "VARCHAR(20)", column = "fshost_name")
    private String fshost_name;

    @Column(meta = "DATETIME", column = "start_stamp")
    private Date start_stamp;

    @NColumn(meta = "DATETIME", column = "answer_stamp")
    private Date answer_stamp;

    @NColumn(meta = "DATETIME", column = "bridge_stamp")
    private Date bridge_stamp;

    @Column(meta = "DATETIME", column = "end_stamp")
    private Date end_stamp;

    @NColumn(meta = "INT", column = "bridgesec")
    private long bridgesec;

    @Column(meta = "INT")
    private Integer duration;

    @Column(meta = "INT")
    private Integer billsec;

    //===========================================================================================================

    @Column(meta = "VARCHAR(50)", column = "hangup_cause")
    private String hangup_cause;

    @Column(meta = "VARCHAR(64)", column = "sip_hangup_disposition")
    private String sip_hangup_disposition;

    @Column(meta = "VARCHAR(64)")
    private String aleguuid;

    @NColumn(meta = "VARCHAR(64)", column = "bleg_uuid")
    private String bleguuid;

    @Column(meta = "VARCHAR(100)", column = "context")
    private String context;

    //============================================================================================================

    @NColumn(meta = "VARCHAR(30)", column = "caller_id_number")
    private String caller_id_number;

    @NColumn(meta = "VARCHAR(20)", column = "caller_type")
    private String caller_type;

    @NColumn(meta = "VARCHAR(50)", column = "caller_name")
    private String caller_name;

    @NColumn(meta = "VARCHAR(30)", column = "caller_agent_interface_type")
    private String caller_agent_interface_type;

    @NColumn(meta = "VARCHAR(30)", column = "caller_agent_interface_name")
    private String caller_agent_interface_name;

    @NColumn(meta = "VARCHAR(30)", column = "caller_agent_interface_exten")
    private String caller_agent_interface_exten;

    @NColumn(meta = "VARCHAR(100)", column = "caller_agent_id")
    private String caller_agent_id;

    @NColumn(meta = "VARCHAR(200)", column = "caller_agent_info")
    private String caller_agent_info;

    //===================================================================================

    @NColumn(meta = "VARCHAR(30)", column = "dest_number")
    private String dest_number;

    @NColumn(meta = "VARCHAR(20)", column = "dest_type")
    private String dest_type;

    @NColumn(meta = "VARCHAR(20)", column = "dest_name")
    private String dest_name;

    @NColumn(meta = "VARCHAR(20)", column = "dest_agent_interface_type")
    private String dest_agent_interface_type;

    @NColumn(meta = "VARCHAR(20)", column = "dest_agent_interface_name")
    private String dest_agent_interface_name;

    @NColumn(meta = "VARCHAR(20)", column = "dest_agent_interface_exten")
    private String dest_agent_interface_exten;

    @NColumn(meta = "VARCHAR(20)", column = "dest_agent_id")
    private String dest_agent_id;

    @NColumn(meta = "VARCHAR(20)", column = "dest_agent_info")
    private String dest_agent_info;

    //===============================================================================================

    @NColumn(meta = "VARCHAR(64)", column = "gateway_name")
    private String gateway_name;

    @NColumn(meta = "VARCHAR(10)", column = "call_direction")
    private String call_direction;

    @NColumn(meta = "VARCHAR(64)", column = "access_number")
    private String access_number;

    @NColumn(meta = "VARCHAR(200)", column = "record_file")
    private String record_file;

    @NColumn(meta = "CHAR(1)", column = "is_upload")
    private String is_upload;

    @Column(meta = "VARCHAR(10)")
    private String type;

    @NColumn(meta = "VARCHAR(1000)",column = "user_field")
    private String user_field;
    
    /*质检评分*/
    private String score;
    
    public static Map<String,String> TYPEMAP =new HashMap<String,String>(){{
        put("m", "master");
        put("s", "slave");
    }};

    public static Map<String,String> CALLDIRECTIONMAP =new HashMap<String,String>(){{
        put("oi", "out_in");
        put("io", "in_out");
        put("ii", "in_in");
        put("oo", "out_out");
        put("i", "in");
    }};


    public static Map<String,String> CALL_DIRECTIONS =new LinkedHashMap<String,String>(){
        {
            put("in_out","呼出");
            put("out_in","呼入");
            put("in_in","分机互拨");
            put("out_out","外转外");
            put("in","单呼");
        }
    };
    
    /**
	 * 是否接通
	 */
	public static final String PUT_THROUGH_Y="y";
	public static final String PUT_THROUGH_N="n";
	
	public static Map<String,String> PUT_THROUGH =new LinkedHashMap<String,String>(){
		{
			put(PUT_THROUGH_Y,"接通");
			put(PUT_THROUGH_N,"未接通");
		}
	};
    

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCall_session_uuid() {
		return call_session_uuid;
	}

	public void setCall_session_uuid(String call_session_uuid) {
		this.call_session_uuid = call_session_uuid;
	}

	public String getFshost_name() {
		return fshost_name;
	}

	public void setFshost_name(String fshost_name) {
		this.fshost_name = fshost_name;
	}

	public Date getStart_stamp() {
		return start_stamp;
	}

	public void setStart_stamp(Date start_stamp) {
		this.start_stamp = start_stamp;
	}

	public Date getAnswer_stamp() {
		return answer_stamp;
	}

	public void setAnswer_stamp(Date answer_stamp) {
		this.answer_stamp = answer_stamp;
	}

	public Date getBridge_stamp() {
		return bridge_stamp;
	}

	public void setBridge_stamp(Date bridge_stamp) {
		this.bridge_stamp = bridge_stamp;
	}

	public Date getEnd_stamp() {
		return end_stamp;
	}

	public void setEnd_stamp(Date end_stamp) {
		this.end_stamp = end_stamp;
	}

	public long getBridgesec() {
		return bridgesec;
	}

	public void setBridgesec(long bridgesec) {
		this.bridgesec = bridgesec;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getBillsec() {
		return billsec;
	}

	public void setBillsec(Integer billsec) {
		this.billsec = billsec;
	}

	public String getHangup_cause() {
		return hangup_cause;
	}

	public void setHangup_cause(String hangup_cause) {
		this.hangup_cause = hangup_cause;
	}

	public String getSip_hangup_disposition() {
		return sip_hangup_disposition;
	}

	public void setSip_hangup_disposition(String sip_hangup_disposition) {
		this.sip_hangup_disposition = sip_hangup_disposition;
	}

	public String getAleguuid() {
		return aleguuid;
	}

	public void setAleguuid(String aleguuid) {
		this.aleguuid = aleguuid;
	}

	public String getBleguuid() {
		return bleguuid;
	}

	public void setBleguuid(String bleguuid) {
		this.bleguuid = bleguuid;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCaller_id_number() {
		return caller_id_number;
	}

	public void setCaller_id_number(String caller_id_number) {
		this.caller_id_number = caller_id_number;
	}

	public String getCaller_type() {
		return caller_type;
	}

	public void setCaller_type(String caller_type) {
		this.caller_type = caller_type;
	}

	public String getCaller_name() {
		return caller_name;
	}

	public void setCaller_name(String caller_name) {
		this.caller_name = caller_name;
	}

	public String getCaller_agent_interface_type() {
		return caller_agent_interface_type;
	}

	public void setCaller_agent_interface_type(String caller_agent_interface_type) {
		this.caller_agent_interface_type = caller_agent_interface_type;
	}

	public String getCaller_agent_interface_name() {
		return caller_agent_interface_name;
	}

	public void setCaller_agent_interface_name(String caller_agent_interface_name) {
		this.caller_agent_interface_name = caller_agent_interface_name;
	}

	public String getCaller_agent_interface_exten() {
		return caller_agent_interface_exten;
	}

	public void setCaller_agent_interface_exten(String caller_agent_interface_exten) {
		this.caller_agent_interface_exten = caller_agent_interface_exten;
	}

	public String getCaller_agent_id() {
		return caller_agent_id;
	}

	public void setCaller_agent_id(String caller_agent_id) {
		this.caller_agent_id = caller_agent_id;
	}

	public String getCaller_agent_info() {
		return caller_agent_info;
	}

	public void setCaller_agent_info(String caller_agent_info) {
		this.caller_agent_info = caller_agent_info;
	}

	public String getDest_number() {
		return dest_number;
	}

	public void setDest_number(String dest_number) {
		this.dest_number = dest_number;
	}

	public String getDest_type() {
		return dest_type;
	}

	public void setDest_type(String dest_type) {
		this.dest_type = dest_type;
	}

	public String getDest_name() {
		return dest_name;
	}

	public void setDest_name(String dest_name) {
		this.dest_name = dest_name;
	}

	public String getDest_agent_interface_type() {
		return dest_agent_interface_type;
	}

	public void setDest_agent_interface_type(String dest_agent_interface_type) {
		this.dest_agent_interface_type = dest_agent_interface_type;
	}

	public String getDest_agent_interface_name() {
		return dest_agent_interface_name;
	}

	public void setDest_agent_interface_name(String dest_agent_interface_name) {
		this.dest_agent_interface_name = dest_agent_interface_name;
	}

	public String getDest_agent_interface_exten() {
		return dest_agent_interface_exten;
	}

	public void setDest_agent_interface_exten(String dest_agent_interface_exten) {
		this.dest_agent_interface_exten = dest_agent_interface_exten;
	}

	public String getDest_agent_id() {
		return dest_agent_id;
	}

	public void setDest_agent_id(String dest_agent_id) {
		this.dest_agent_id = dest_agent_id;
	}

	public String getDest_agent_info() {
		return dest_agent_info;
	}

	public void setDest_agent_info(String dest_agent_info) {
		this.dest_agent_info = dest_agent_info;
	}

	public String getGateway_name() {
		return gateway_name;
	}

	public void setGateway_name(String gateway_name) {
		this.gateway_name = gateway_name;
	}

	public String getCall_direction() {
		return call_direction;
	}

	public void setCall_direction(String call_direction) {
		this.call_direction = call_direction;
	}

	public String getAccess_number() {
		return access_number;
	}

	public void setAccess_number(String access_number) {
		this.access_number = access_number;
	}

	public String getRecord_file() {
		return record_file;
	}

	public void setRecord_file(String record_file) {
		this.record_file = record_file;
	}

	public String getIs_upload() {
		return is_upload;
	}

	public void setIs_upload(String is_upload) {
		this.is_upload = is_upload;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser_field() {
		return user_field;
	}

	public void setUser_field(String user_field) {
		this.user_field = user_field;
	}
	
	public String getPlayUrl(String file) {
        return "http://"+file+""+getRecord_file();
    }

}

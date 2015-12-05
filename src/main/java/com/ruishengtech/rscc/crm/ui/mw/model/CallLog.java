package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Wangyao
 *
 */
@Table(name = "sys_call_log")
public class CallLog extends CommonDbBean { 

	//呼叫发起者
	@Index
    @NColumn(meta = "VARCHAR(64)", column = "agent_id")
    private String agent_id;
    
    //冗余一个用户名
	@Index
    @NColumn(meta = "VARCHAR(64)", column = "agent_name")
    private String agent_name;

    //呼叫目的地
	@Index
    @NColumn(meta = "VARCHAR(64)", column = "call_phone")
    private String call_phone;

    //呼叫时间
    @Index
    @NColumn(meta = "TIMESTAMP default NOW()", column = "call_time")
    private Date call_time;
    
    //文本记录
    @NColumn(meta = "VARCHAR(1024)", column = "text_log")
    private String text_log;

    //是呼入还是呼出，枚举，呼入，呼出
    @NColumn(meta = "VARCHAR(100)", column = "in_out_flag")
    private String in_out_flag;
    
    //数据来源
    @Index
    @NColumn(meta = "VARCHAR(64)", column = "data_source")
    private String data_source;
    
    //数据ID
    @Index
    @NColumn(meta = "VARCHAR(64)", column = "data_id")
    private String data_id;
    
    //call_session_ID，呼叫产生的ID，用来查询cdr
    @Index(type = IndexDefinition.TYPE_UNIQUE, method = IndexDefinition.METHOD_HASH)
    @NColumn(meta = "VARCHAR(64)", column = "call_session_uuid")
    private String call_session_uuid;
    
    //录音文件地址
    @NColumn(meta = "VARCHAR(255)", column = "record_path")
    private String record_path;

    //该条呼叫的状态，接通，未接通，等等，可以用呼叫时长来标记，这里记录的是已经接通后的有效通话时长
    @NColumn(meta = "INT", column = "talk_time")
    private Integer talk_time;

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getCall_phone() {
		return call_phone;
	}

	public void setCall_phone(String call_phone) {
		this.call_phone = call_phone;
	}

	public Date getCall_time() {
		return call_time;
	}

	public void setCall_time(Date call_time) {
		this.call_time = call_time;
	}

	public String getText_log() {
		return text_log;
	}

	public void setText_log(String text_log) {
		this.text_log = text_log;
	}

	public String getIn_out_flag() {
		return in_out_flag;
	}

	public void setIn_out_flag(String in_out_flag) {
		this.in_out_flag = in_out_flag;
	}

	public String getCall_session_uuid() {
		return call_session_uuid;
	}

	public void setCall_session_uuid(String call_session_uuid) {
		this.call_session_uuid = call_session_uuid;
	}

	public String getRecord_path() {
		return record_path;
	}

	public void setRecord_path(String record_path) {
		this.record_path = record_path;
	}

	public Integer getTalk_time() {
		return talk_time;
	}

	public void setTalk_time(Integer talk_time) {
		this.talk_time = talk_time;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	public String getData_id() {
		return data_id;
	}

	public void setData_id(String data_id) {
		this.data_id = data_id;
	}

	public String getPlayUrl(String file) {
        return "http://"+file+""+getRecord_path();
    }
    
}
package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Wangyao
 *
 */
@Table(name = "agent_call")
public class Call extends CommonDbBean { 

	//呼叫发起者
    @Column(meta = "VARCHAR(64)", column = "agent_id")
    private String agent_id;

    //呼叫目的地
    @NColumn(meta = "VARCHAR(64)", column = "call_phone")
    private String call_phone;

    //呼叫使用分机，用户绑定的分机号码
    @NColumn(meta = "VARCHAR(64)", column = "exten")
    private String exten;
    
    //呼叫产生的ID，用来查询cdr
    @NColumn(meta = "VARCHAR(64)", column = "call_session_uuid")
    private String call_session_uuid;

    //该条呼叫的状态，通常为0，只有正在呼叫的为1
    @NColumn(meta = "VARCHAR(64)", column = "call_state")
    private String call_state;
    
	public String getCall_state() {
		return call_state;
	}

	public void setCall_state(String call_state) {
		this.call_state = call_state;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getCall_phone() {
		return call_phone;
	}

	public void setCall_phone(String call_phone) {
		this.call_phone = call_phone;
	}

	public String getExten() {
		return exten;
	}

	public void setExten(String exten) {
		this.exten = exten;
	}

	public String getCall_session_uuid() {
		return call_session_uuid;
	}

	public void setCall_session_uuid(String call_session_uuid) {
		this.call_session_uuid = call_session_uuid;
	}

}
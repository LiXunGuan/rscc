package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "runtime_rtagentstatus")
public class RtAgentStatus extends CommonDbBean{

    @NColumn(meta = "VARCHAR(64)")
    private String buuid;

    @NColumn(meta = "VARCHAR(64)")
    private String call_session_uuid;

    //**基本信息****
    @Column(meta = "VARCHAR(100)")
    private String agent_uid;

    @Column(meta = "VARCHAR(200)")
    private String agent_info;


    @NColumn(meta = "VARCHAR(100)")
    private String extern;

    @Column(meta = "VARCHAR(100)")
    private String queue;


    //**统计数据***
    @Column(meta = "INT")
    private Long out_count=0l;
    @Column(meta = "INT")
    private Long in_count=0l;

    @Column(meta = "INT")
    private Long miss_count=0l;

    @Column(meta = "INT")
    private Long in_time=0l;

    @Column(meta = "INT")
    private Long out_time=0l;

    @Column(meta = "INT")
    private Long ringing_time=0l;

    @Column(meta = "INT")
    private Long binder_time=0l;

    @NColumn(meta = "DATETIME")
    private Date last_binder_date;

    @Column(meta = "INT")
    private Long busy_time=0l;

    @NColumn(meta = "DATETIME")
    private Date last_busy_date;

    @NColumn(meta = "VARCHAR(100)")
    private String busy_reason;

    //实时数据
    @NColumn(meta = "VARCHAR(100)")
    private String number;

    @NColumn(meta = "DATETIME")
    private Date up_time;

    @NColumn(meta = "VARCHAR(10)")
    private String type;

    @Column(meta = "VARCHAR(10)")
    private String status;

    @Column(meta = "VARCHAR(20)")
    private String state;

    //=========================================================

    public String getAgent_uid() {
        return agent_uid;
    }

    public void setAgent_uid(String agent_uid) {
        this.agent_uid = agent_uid;
    }

    public String getAgent_info() {
        return agent_info;
    }

    public void setAgent_info(String agent_info) {
        this.agent_info = agent_info;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Long getOut_count() {
        return out_count;
    }

    public void setOut_count(Long out_count) {
        this.out_count = out_count;
    }

    public Long getMiss_count() {
        return miss_count;
    }

    public void setMiss_count(Long miss_count) {
        this.miss_count = miss_count;
    }

    public Long getIn_time() {
        return in_time;
    }

    public void setIn_time(Long in_time) {
        this.in_time = in_time;
    }

    public Long getOut_time() {
        return out_time;
    }

    public void setOut_time(Long out_time) {
        this.out_time = out_time;
    }

    public Long getRinging_time() {
        return ringing_time;
    }

    public void setRinging_time(Long ringing_time) {
        this.ringing_time = ringing_time;
    }

    public Long getBinder_time() {
        return binder_time;
    }

    public void setBinder_time(Long binder_time) {
        this.binder_time = binder_time;
    }

    public Date getLast_binder_date() {
        return last_binder_date;
    }

    public void setLast_binder_date(Date last_binder_date) {
        this.last_binder_date = last_binder_date;
    }

    public Long getBusy_time() {
        return busy_time;
    }

    public void setBusy_time(Long busy_time) {
        this.busy_time = busy_time;
    }

    public Date getLast_busy_date() {
        return last_busy_date;
    }

    public void setLast_busy_date(Date last_busy_date) {
        this.last_busy_date = last_busy_date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getUp_time() {
        return up_time;
    }

    public void setUp_time(Date up_time) {
        this.up_time = up_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public String getExtern() {
		return extern;
	}

	public void setExtern(String extern) {
		this.extern = extern;
	}

	public Long getIn_count() {
		return in_count;
	}

	public void setIn_count(Long in_count) {
		this.in_count = in_count;
	}

    public String getBusy_reason() {
        return busy_reason;
    }

    public void setBusy_reason(String busy_reason) {
        this.busy_reason = busy_reason;
    }

    public String getBuuid() {
        return buuid;
    }

    public void setBuuid(String buuid) {
        this.buuid = buuid;
    }

    public String getCall_session_uuid() {
        return call_session_uuid;
    }

    public void setCall_session_uuid(String call_session_uuid) {
        this.call_session_uuid = call_session_uuid;
    }
}

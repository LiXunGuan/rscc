package com.ruishengtech.rscc.crm.ui.mw.model.runtime;

import java.util.Date;

import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;


/**
 * Created by yaoliceng on 2014/11/14.
 */
public class AgentRunTime extends CommonDbBean{

    

    private Long id;

    /**
     * 坐席号
     */
    private String uid;
    
    /**
     * 坐席描述
     */
    private String info;
    
    /**
     * 分机号
     */
    private String sipusernumber;
    
    /**
     * 注册状态
     */
    private String regstatus;

    /**
     *坐席致忙状态 
     */
    private String blindingstatus;

    /**
     * 通话状态
     */
    private String callstate; 
    
    /**
     * 对方号码
     */
    private String othernumber;

    /**
     * 通话时长
     */
    private Date answertime;

    /**
     * 通话时长
     */
    private Date answertime2;

    /**
     * 呼叫方向
     */
    private String dailway;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}

	public Date getAnswertime() {
		return answertime;
	}


	public void setAnswertime(Date answertime) {
		this.answertime = answertime;
	}


	public String getDailway() {
		return dailway;
	}


	public void setDailway(String dailway) {
		this.dailway = dailway;
	}

	public String getRegstatus() {
		return regstatus;
	}


	public void setRegstatus(String regstatus) {
		this.regstatus = regstatus;
	}


	public String getSipusernumber() {
		return sipusernumber;
	}


	public void setSipusernumber(String sipusernumber) {
		this.sipusernumber = sipusernumber;
	}


	public String getBlindingstatus() {
		return blindingstatus;
	}


	public void setBlindingstatus(String blindingstatus) {
		this.blindingstatus = blindingstatus;
	}


	public String getCallstate() {
		return callstate;
	}


	public void setCallstate(String callstate) {
		this.callstate = callstate;
	}


	public String getOthernumber() {
		return othernumber;
	}


	public void setOthernumber(String othernumber) {
		this.othernumber = othernumber;
	}

    public Date getAnswertime2() {
        return answertime2;
    }

    public void setAnswertime2(Date answertime2) {
        this.answertime2 = answertime2;
    }

}

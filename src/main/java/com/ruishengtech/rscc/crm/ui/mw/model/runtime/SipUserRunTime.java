package com.ruishengtech.rscc.crm.ui.mw.model.runtime;

import java.util.Date;

import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;


/**
 * Created by yaoliceng on 2014/11/14.
 */
public class SipUserRunTime extends CommonDbBean{

	/**
	 * sipuser id
	 */
    private Long id;

    /**
	 * 分机号
	 */
    private String sipid;
  
    /**
	 * 注册状态
	 */
    private String regstatus;
    
    /**
	 * 坐席号
	 */
    private String agentuid;
    
    /**
	 * 坐席描述
	 */
    private String agentinfo;
    
    /**
	 * 坐席致忙状态
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
     *通话时长
	 */ 
    private Date answertime;

    /**
     *通话时长
	 */
    private Date answertime1;

    /**
	 * 呼叫方向
	 */
    private String direction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSipid() {
		return sipid;
	}

	public void setSipid(String sipid) {
		this.sipid = sipid;
	}


	public String getAgentuid() {
		return agentuid;
	}

	public void setAgentuid(String agentuid) {
		this.agentuid = agentuid;
	}

	public String getAgentinfo() {
		return agentinfo;
	}

	public void setAgentinfo(String agentinfo) {
		this.agentinfo = agentinfo;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getAnswertime() {
		return answertime;
	}

	public void setAnswertime(Date answertime) {
		this.answertime = answertime;
	}

	public String getRegstatus() {
		return regstatus;
	}

	public void setRegstatus(String regstatus) {
		this.regstatus = regstatus;
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

    public Date getAnswertime1() {
        return answertime1;
    }

    public void setAnswertime1(Date answertime1) {
        this.answertime1 = answertime1;
    }
}

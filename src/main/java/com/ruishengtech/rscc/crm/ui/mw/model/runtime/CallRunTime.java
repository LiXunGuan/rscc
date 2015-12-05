package com.ruishengtech.rscc.crm.ui.mw.model.runtime;


import java.util.Date;

import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/11/14.
 */
public class CallRunTime extends CommonDbBean{

    private Long id;

    private String calling;

    private String callingagent;

    private String callingagentinfo;

    private String routetype;


    private String routenumber;

    private String routestring;

    private String gateway1;

    private String gateway2;

    private String called;

    private String calledagent;

    private String calledagentinfo;

    private Date answertime;
    private Date answertime1;

    public Date getAnswertime1() {
        return answertime1;
    }

    public void setAnswertime1(Date answertime1) {
        this.answertime1 = answertime1;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalling() {
		return calling;
	}

	public void setCalling(String calling) {
		this.calling = calling;
	}

	public String getCallingagent() {
		return callingagent;
	}

	public void setCallingagent(String callingagent) {
		this.callingagent = callingagent;
	}

    public String getGateway1() {
        return gateway1;
    }

    public void setGateway1(String gateway1) {
        this.gateway1 = gateway1;
    }

    public String getGateway2() {
        return gateway2;
    }

    public void setGateway2(String gateway2) {
        this.gateway2 = gateway2;
    }

    public String getCalled() {
		return called;
	}

	public void setCalled(String called) {
		this.called = called;
	}

	public String getCalledagent() {
		return calledagent;
	}

	public void setCalledagent(String calledagent) {
		this.calledagent = calledagent;
	}

    public Date getAnswertime() {
        return answertime;
    }

    public void setAnswertime(Date answertime) {
        this.answertime = answertime;
    }

    public String getRoutetype() {
        return routetype;
    }

    public void setRoutetype(String routetype) {
        this.routetype = routetype;
    }


    public String getRoutenumber() {
        return routenumber;
    }

    public void setRoutenumber(String routenumber) {
        this.routenumber = routenumber;
    }

    public String getRoutestring() {
        return routestring;
    }

    public void setRoutestring(String routestring) {
        this.routestring = routestring;
    }

    public String getCallingagentinfo() {
        return callingagentinfo;
    }

    public void setCallingagentinfo(String callingagentinfo) {
        this.callingagentinfo = callingagentinfo;
    }

    public String getCalledagentinfo() {
        return calledagentinfo;
    }

    public void setCalledagentinfo(String calledagentinfo) {
        this.calledagentinfo = calledagentinfo;
    }
}

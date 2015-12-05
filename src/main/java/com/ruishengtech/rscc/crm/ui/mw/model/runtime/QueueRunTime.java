package com.ruishengtech.rscc.crm.ui.mw.model.runtime;

import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;


/**
 * Created by yaoliceng on 2014/11/14.
 */
public class QueueRunTime extends CommonDbBean{

    private Long id;

    private String name;

    private String watting;

    private String answer;

    private String buycount;

    private String idlecount;
    
    
    private String uid;
    private String info;
    private String status;
    private String state;

    private String offlinecount;

    private String cc;
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWatting() {
        return watting;
    }

    public void setWatting(String watting) {
        this.watting = watting;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getBuycount() {
        return buycount;
    }

    public void setBuycount(String buycount) {
        this.buycount = buycount;
    }

    public String getIdlecount() {
        return idlecount;
    }

    public void setIdlecount(String idlecount) {
        this.idlecount = idlecount;
    }


	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

    public String getOfflinecount() {
        return offlinecount;
    }

    public void setOfflinecount(String offlinecount) {
        this.offlinecount = offlinecount;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}

package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/22.
 */
@Table(name = "mw.mw_agent")
public class MWAgent extends CommonDbBean{

    @Column(meta = "VARCHAR(100)")
    private String uid;

    @Column(meta = "VARCHAR(1000)")
    private String info;

    @NColumn(meta = "VARCHAR(100)",column = "agroup")
    private String group;

    @NColumn(meta = "VARCHAR(1000)")
    private String password;

    @Column(meta = "CHAR(1)")
    private String group_leader;
    
    @NColumn(meta = "VARCHAR(1000)")
    private String manages;

    @NColumn(meta = "VARCHAR(100)")
    private String job_number;

    @NColumn(meta = "VARCHAR(100)")
    private String caller_id_number;

    @NColumn(meta = "VARCHAR(100)")
    private String caller_id_name;

    @NColumn(meta = "VARCHAR(30)")
    private String static_exten;

    @NColumn(meta = "VARCHAR(100)")
    private String phone;

    @NColumn(meta = "VARCHAR(10)")
    private String static_strategy;
    
    public static String STATIC_STRATEGY_S = "s";
	
	public static String STATIC_STRATEGY_C = "c";
	
	public static Map<String, String> STATIC_STRATEGY_MAP = new LinkedHashMap<String, String>() {
		{
			put(STATIC_STRATEGY_S, "共振");
			put(STATIC_STRATEGY_C, "连续");
		}
	};

    public String getStatic_exten() {
        return static_exten;
    }

    public void setStatic_exten(String static_exten) {
        this.static_exten = static_exten;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatic_strategy() {
        return static_strategy;
    }

    public void setStatic_strategy(String static_strategy) {
        this.static_strategy = static_strategy;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public String getCaller_id_name() {
        return caller_id_name;
    }

    public void setCaller_id_name(String caller_id_name) {
        this.caller_id_name = caller_id_name;
    }

    public String getCaller_id_number() {
        return caller_id_number;
    }

    public void setCaller_id_number(String caller_id_number) {
        this.caller_id_number = caller_id_number;
    }

    private String conferencestate;

    private String extension;

    private String status;
    private String state;

    private String belongqueue;
    
    private String power;
    
    
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getBelongqueue() {
		return belongqueue;
	}

	public void setBelongqueue(String belongqueue) {
		this.belongqueue = belongqueue;
	}

	public String getGroup_leader() {
		return group_leader;
	}

	public void setGroup_leader(String group_leader) {
		this.group_leader = group_leader;
	}

	public String getManages() {
		return manages;
	}

	public void setManages(String manages) {
		this.manages = manages;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getConferencestate() {
		return conferencestate;
	}

	public void setConferencestate(String conferencestate) {
		this.conferencestate = conferencestate;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}

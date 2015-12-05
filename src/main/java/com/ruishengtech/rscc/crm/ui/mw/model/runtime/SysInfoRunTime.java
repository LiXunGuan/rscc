package com.ruishengtech.rscc.crm.ui.mw.model.runtime;

import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;


/**
 * Created by yaoliceng on 2014/11/14.
 */
public class SysInfoRunTime extends CommonDbBean{

    private Long id;

    private String name;

    private String type;
	
    private String ip;
    
    private Integer serverid;
    
    /**
     * 集群状态
     */
    private String agstate;
    
    private String eslstate;
    
    /**
     * 并发id
     */
    private Long mid;
    
    
    
    /**
     * 实时并发数
     */
    private Integer destmark;
    
    /**
     * 并发总数
     */
    private Integer destmarksum;
    
   
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getServerid() {
		return serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}


	public Integer getDestmark() {
		return destmark;
	}

	public void setDestmark(Integer destmark) {
		this.destmark = destmark;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}



	public String getAgstate() {
		return agstate;
	}

	public void setAgstate(String agstate) {
		this.agstate = agstate;
	}

	public String getEslstate() {
		return eslstate;
	}

	public void setEslstate(String eslstate) {
		this.eslstate = eslstate;
	}

	public Integer getDestmarksum() {
		return destmarksum;
	}

	public void setDestmarksum(Integer destmarksum) {
		this.destmarksum = destmarksum;
	}


    


}

package com.ruishengtech.rscc.crm.record.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "accessnumber")
public class AccessNumber extends CommonDbBean {

    @Column(meta = "VARCHAR(64)")
    private String accessnumber;
    
    @Column(meta = "CHAR(1)")
    private String canout;
    
    @Column(meta = "CHAR(1)")
    private String canin;
    
    @Column(meta = "INT")
    private Integer concurrency;


	public String getAccessnumber() {
		return accessnumber;
	}

	public void setAccessnumber(String accessnumber) {
		this.accessnumber = accessnumber;
	}

	public String getCanout() {
		return canout;
	}

	public void setCanout(String canout) {
		this.canout = canout;
	}

	public String getCanin() {
		return canin;
	}

	public void setCanin(String canin) {
		this.canin = canin;
	}

	public Integer getConcurrency() {
		return concurrency;
	}

	public void setConcurrency(Integer concurrency) {
		this.concurrency = concurrency;
	}




}

package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/11/22.
 */
@Table(name = "mw.log_agent_binding")
public class LogAgentBinding extends CommonDbBean{


    @NColumn(meta = "DATETIME")
    private Date beginDate;

    @NColumn(meta = "DATETIME")
    private Date endDate;

   
    /**
     * 坐席id
     */
    @NColumn(meta = "VARCHAR(200)")
    private String agentuid;
    
    /**
     * 致忙原因
     */
    @NColumn(meta = "VARCHAR(200)")
    private String extension;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }



	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getAgentuid() {
		return agentuid;
	}

	public void setAgentuid(String agentuid) {
		this.agentuid = agentuid;
	}
}

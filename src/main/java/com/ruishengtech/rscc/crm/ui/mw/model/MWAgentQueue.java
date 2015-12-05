package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/22.
 */
@Table(name = "mw.mw_agent_queue")
public class MWAgentQueue extends CommonDbBean {

    @Column(meta = "VARCHAR(100)")
    private String agentUid;

    @Column(meta = "INT")
    private Long queueId;

    @Column(meta = "VARCHAR(10)")
    private String level;

    @Column(meta = "VARCHAR(10)")
    private String position;


    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

	public String getAgentUid() {
		return agentUid;
	}

	public void setAgentUid(String agentUid) {
		this.agentUid = agentUid;
	}
}

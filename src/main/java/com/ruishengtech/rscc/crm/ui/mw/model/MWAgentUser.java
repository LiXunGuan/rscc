package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/22.
 */
@Table(name = "mw.mw_agent_user")
public class MWAgentUser extends CommonDbBean{

    @Column(meta = "VARCHAR(100)")
    private String agentUId;

    @Column(meta = "VARCHAR(20)")
    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAgentUId() {
        return agentUId;
    }

    public void setAgentUId(String agentUId) {
        this.agentUId = agentUId;
    }
}

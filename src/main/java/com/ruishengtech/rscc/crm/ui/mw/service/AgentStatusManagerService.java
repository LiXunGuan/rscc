package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAgent;
import com.ruishengtech.rscc.crm.ui.mw.model.RtAgentStatus;

/**
 * Created by yaoliceng on 2014/12/24.
 */
@Service
@Transactional
public class AgentStatusManagerService extends BaseService {

	private String database;
	
	public AgentStatusManagerService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    public void bind(MWAgent mwAgent) {

        List<RtAgentStatus> list = getMwAgentStatuses(mwAgent);
        if (list.size() > 0) {
            RtAgentStatus status = list.get(0);
            status.setLast_binder_date(new Date());
            status.setState(mwAgent.getState());
            status.setStatus(mwAgent.getStatus());
            status.setExtern(mwAgent.getExtension());
            update(status);
        } else {
            RtAgentStatus status = new RtAgentStatus();
            status.setAgent_uid(mwAgent.getUid());
            status.setAgent_info(mwAgent.getInfo());
            status.setExtern(mwAgent.getExtension());
            status.setStatus(mwAgent.getStatus());
            status.setState(mwAgent.getState());
            status.setLast_binder_date(new Date());
            status.setBinder_time(0l);
            status.setQueue(StringUtils.trimToEmpty(mwAgent.getPower()));
            save(status);
        }

//        agentSessionManager.registerAgent(mwAgent);
    }

	@SuppressWarnings("unchecked")
	private List<RtAgentStatus> getMwAgentStatuses(final MWAgent mwAgent) {
		return queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"rt_agent_status where agent_uid=?");
                params.add(mwAgent.getUid());
            }
        }, RtAgentStatus.class);
	}

}

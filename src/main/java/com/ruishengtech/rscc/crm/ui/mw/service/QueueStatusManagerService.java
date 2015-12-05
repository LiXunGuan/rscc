package com.ruishengtech.rscc.crm.ui.mw.service;

import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.condition.QueueRunTimeCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by yaoliceng on 2014/12/24.
 */
@Service
@Transactional
public class QueueStatusManagerService extends BaseService {

    public void updateQueueStatus(QueueRunTimeCondition queueRunTimeCondition) {

        List<Map<String, Object>> list = jdbcTemplate.queryForList(" select maq.queueid,\n" +
                " sum(case when ras.id is not null and ras.state='down' and ras.status='Logged Out' then 1 end) busy_ready_count,\n" +
                " sum(case when ras.id is not null and ras.state='down' and ras.status='Available' then 1 end) idle_ready_count,\n" +
                " sum(case when ras.id is not null and ras.state!='down' then 1 end) not_ready_count,\n" +
                " sum(case when (ras.id is null or ras.status='offline') then 1 end) offline_count\n" +
                " from mw.mw_agent_queue maq \n" +
                " left join mw.rt_agent_status ras on (maq.agentuid = ras.agent_uid) \n" +
                " where maq.queueid=?",queueRunTimeCondition.getNumbers());

        for (Map<String, Object> stringObjectMap : list) {

            jdbcTemplate.update("UPDATE mw.rt_queue_status set busy_ready_count=?,idle_ready_count=?,not_ready_count=?,offline_count=?" +
                            " WHERE queue_id=? ",stringObjectMap.get("busy_ready_count")==null?0:stringObjectMap.get("busy_ready_count"),
                    stringObjectMap.get("idle_ready_count")==null?0:stringObjectMap.get("idle_ready_count"),
                    stringObjectMap.get("not_ready_count")==null?0:stringObjectMap.get("not_ready_count"),
                    stringObjectMap.get("offline_count")==null?0:stringObjectMap.get("offline_count"),
                    queueRunTimeCondition.getNumbers());
        }
    }
}


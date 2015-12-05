package com.ruishengtech.rscc.crm.ui.mw.service.runtime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.ui.mw.condition.runtime.AgentRunTimeCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.runtime.AgentRunTime;
import com.ruishengtech.rscc.crm.ui.mw.service.BaseConfigService;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class AgentRunTimeService extends BaseConfigService<AgentRunTime> {
	
	private String database;
	
	public AgentRunTimeService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private FsServerManager fsServerManager;


    @Override
    protected Class getClazz() {
        return AgentRunTime.class;
    }

    @Override
    public void deploy() {
    }


   

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	AgentRunTimeCondition cond = (AgentRunTimeCondition) condition;
                //left join registrations reg on c.agentuid=reg.reg_user
                whereSql.append(" from "+database+"mw_agent a "
                               +" left join "+database+"rt_agent_status ras on (a.uid= ras.agent_uid  and ras.status!='offline') where 1=1 ");
//                        " left join mw_agent_user mau on a.uid= mau.agentuid  " +
////                        " left join mw_exten_route  mer on  mau.extension = mer.extension  " +
//                        " left join rt_channal c on a.uid= c.agent_uid  " +
//                        " left join agents agt on a.uid=agt.name  " +
//                        " left join rt_channal c2 on(c.session_uuid=c2.session_uuid and c.id!=c2.id and c.type!=c2.type) ");
                QueryUtil.like(whereSql, params, cond.getUid(), " and a.uid like ? ");
                QueryUtil.like(whereSql, params, cond.getExtension(), " and ras.extern like ? ");
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
                pageSql.append("select ras.uuid as uuid,ras.extern as sipusernumber, a.id as id,a.uid as uid,a.info as info," +
                        "ras.up_time as answertime, ras.type as dailway, " +
                        "ras.status as blindingstatus,ras.number as othernumber,ras.state as callstate").append(whereSql);
                pageSql.append(" order by ras.status desc, id desc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				// TODO Auto-generated method stub
				return null;
			}
        };
    }

}

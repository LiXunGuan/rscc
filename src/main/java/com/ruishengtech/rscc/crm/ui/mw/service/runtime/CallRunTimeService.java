package com.ruishengtech.rscc.crm.ui.mw.service.runtime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.ui.mw.condition.runtime.CallRunTimeCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.runtime.CallRunTime;
import com.ruishengtech.rscc.crm.ui.mw.service.BaseConfigService;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class CallRunTimeService extends BaseConfigService<CallRunTime> {
	
	private String database;
	
	public CallRunTimeService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private FsServerManager fsServerManager;


    @Override
    protected Class getClazz() {
        return CallRunTime.class;
    }

    @Override
    public void deploy() {
    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	CallRunTimeCondition cond = (CallRunTimeCondition) condition;
                whereSql.append(" from "+database+"rt_channal r left join "+database+"rt_channal r1 on (r.session_uuid=r1.session_uuid and r.id!=r1.id and r1.answertime is not null) where r.type='i' ");
                QueryUtil.like(whereSql, params, cond.getCalling(), " and r.caller_id_number  like ? ");
                QueryUtil.like(whereSql, params, cond.getCalled(), " and r1.caller_id_number  like ? ");
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
                pageSql.append("select r.id,r.caller_id_number as calling ,r.agent_uid as callingagent,r.agent_info as callingagentinfo," +
                        "r.route_type as routetype,r.route_number as routenumber,r.route_string as routestring" +
                        ",r.access_number as gateway1,r1.access_number as gateway2," +
                        " r1.caller_id_number  as called,r1.agent_uid as calledagent ,r1.agent_info as calledagentinfo,r.answertime as answertime,r1.answertime as answertime1 ").append(whereSql);
                pageSql.append(" order by id asc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}
        };
    }

}

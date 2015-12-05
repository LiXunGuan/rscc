package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.condition.FSXmlCondition;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAgent;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/27.
 */
@Service
@Transactional
public class MWAgentService extends BaseConfigService<MWAgent> {


    @Autowired
    private FsServerManager fsServerManager;
    
    @Autowired
	private GetQueueRunTimeService getQueueRunTimeService;

    @Autowired
    private CallRouteManager callRouteManager;
    
    @Autowired
	private MWExtenRouteService extenRouteService;
    
    @Autowired
	private MWAccessnumberRputeService mwAccessnumberRputeService;
    
    private String database;
	
	public MWAgentService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}

    @Override
    protected Class getClazz() {
        return MWAgent.class;
    }

    @Override
    public void deploy() {
        // TODO Auto-generated method stub

    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {

            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql,
                                 List<Object> params) {
                FSXmlCondition cond = (FSXmlCondition) condition;
                whereSql.append(" from "+database+"mw_agent a left join "+database+"rt_agent_status rta on (a.uid=rta.agent_uid and rta.status!='offline') where 1=1  ");
                QueryUtil.like(whereSql, params, cond.getUidname(), " and a.uid like ? ");
            }

            @Override
            public void getCountSql(StringBuilder countSql,
                                    StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);

            }

            @Override
            public void getPageSql(StringBuilder pageSql,
                                   StringBuilder whereSql, ICondition condition,
                                   List<Object> params) {
                pageSql.append(" select  rta.status,rta.state ,a.* ").append(whereSql);
                pageSql.append(" order by id desc ");

            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}
        };
    }
    
    @SuppressWarnings("unchecked")
	public MWAgent queryForAgent(final String agent_id) {
        List<MWAgent> list = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"mw_agent where uid=?");
                params.add(agent_id);
            }
        }, MWAgent.class);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
}

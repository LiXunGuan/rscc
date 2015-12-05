package com.ruishengtech.rscc.crm.ui.mw.service.runtime;

import java.util.List;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.ui.mw.condition.runtime.SipUserRunTimeCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.runtime.SipUserRunTime;
import com.ruishengtech.rscc.crm.ui.mw.service.BaseConfigService;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class SipUserRunTimeService extends BaseConfigService<SipUserRunTime> {
	
	private String database;
	
	public SipUserRunTimeService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private FsServerManager fsServerManager;


    @Override
    protected Class getClazz() {
        return SipUserRunTime.class;
    }

    @Override
    public void deploy() {
    }




    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	SipUserRunTimeCondition cond = (SipUserRunTimeCondition) condition;
                whereSql.append(" " +
                        " from "+database+"mw_exten_route mer " +
                        " left join "+database+"mw_agent_user mau on ( mer.extension = mau.extension ) " +
                        " left join "+database+"mw_agent mw  on mau.agentuid = mw.uid  " +
                        " left join "+database+"rt_agent_status agt  on mw.uid = agt.agent_uid  "
                        + "left join "+database+"registrations reg on mer.extension=reg.reg_user"
                        +
                        " left join "+database+"rt_channal c on c.fs_user_id=mer.extension " +
                        " left join "+database+"rt_channal c1 on (c.session_uuid=c1.session_uuid and c.id!=c1.id and  c.type!=c1.type) where mer.type=? ");

                params.add(MWExtenRoute.ROUTER_TYPE_SIPUSER);
                QueryUtil.like(whereSql, params, cond.getExtension(), " and mer.extension like ? ");
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
                pageSql.append("select reg.reg_user as regstatus, agt.state as callstate, agt.status as blindingstatus,mer.id as id,mer.extension as sipid,mw.uid as agentuid," +
                        "mw.info as agentinfo,c.answertime as answertime, c1.answertime as answertime1," +
                        "c.type as direction,c1.caller_id_number as othernumber ").append(whereSql);
                pageSql.append(" order by reg.reg_user desc, id desc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				// TODO Auto-generated method stub
				return null;
			}
        };
    }


}

package com.ruishengtech.rscc.crm.report.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.report.condition.ReportAgentCallCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.cstm.controller.CustomerController;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

public class ReportAgentCallSolution implements ISolution{
	
	private ReportAgentCallCondition cond;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		cond  =  (ReportAgentCallCondition) condition;
		if("callout".equals(cond.getCalltype())){
			whereSql.append(" FROM ( SELECT\n" +
						"	end_stamp AS calltime,\n" +
						"	caller_agent_id AS agentid,\n" +
						"	caller_agent_info AS agentname,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec > \'0\'\n" +
						"		AND bridgesec < \'"+cond.getOutcallcount_p1().split("-")[1]+"\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS outcallcount_p1,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec > \'0\'\n" +
						"		AND bridgesec < \'"+cond.getOutcallcount_p1().split("-")[1]+"\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS outcallduration_p1,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec >= \'"+cond.getOutcallcount_p2().split("-")[0]+"\'\n" +
						"		AND bridgesec < \'"+cond.getOutcallcount_p2().split("-")[1]+"\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS outcallcount_p2,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec >= \'"+cond.getOutcallcount_p2().split("-")[0]+"\'\n" +
						"		AND bridgesec < \'"+cond.getOutcallcount_p2().split("-")[1]+"\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS outcallduration_p2,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec >= \'"+cond.getOutcallcount_p3().split("-")[0]+"\'\n" +
						"		AND bridgesec < \'"+cond.getOutcallcount_p3().split("-")[1]+"\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS outcallcount_p3,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec >= \'"+cond.getOutcallcount_p3().split("-")[0]+"\'\n" +
						"		AND bridgesec < \'"+cond.getOutcallcount_p3().split("-")[1]+"\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS outcallduration_p3,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec >= \'"+cond.getOutcallcount_p4()+"\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS outcallcount_p4,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec >= \'"+cond.getOutcallcount_p4()+"\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS outcallduration_p4,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec >= \'0\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS outcallcount,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'in_out\'\n" +
						"		AND bridgesec >= \'0\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS outcallduration\n");
		}else if("callin".equals(cond.getCalltype())){
			whereSql.append(" FROM ( SELECT\n" +
						"	end_stamp AS calltime,\n" +
						"	caller_agent_id AS agentid,\n" +
						"	caller_agent_info AS agentname,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec > \'0\'\n" +
						"		AND bridgesec < \'"+cond.getIncallcount_p1().split("-")[1]+"\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS incallcount_p1,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec > \'0\'\n" +
						"		AND bridgesec < \'"+cond.getIncallcount_p1().split("-")[1]+"\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS incallduration_p1,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec >= \'"+cond.getIncallcount_p2().split("-")[0]+"\'\n" +
						"		AND bridgesec < \'"+cond.getIncallcount_p2().split("-")[1]+"\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS incallcount_p2,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec >= \'"+cond.getIncallcount_p2().split("-")[0]+"\'\n" +
						"		AND bridgesec < \'"+cond.getIncallcount_p2().split("-")[1]+"\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS incallduration_p2,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec >= \'"+cond.getIncallcount_p3().split("-")[0]+"\'\n" +
						"		AND bridgesec < \'"+cond.getIncallcount_p3().split("-")[1]+"\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS incallcount_p3,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec >= \'"+cond.getIncallcount_p3().split("-")[0]+"\'\n" +
						"		AND bridgesec < \'"+cond.getIncallcount_p3().split("-")[1]+"\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS incallduration_p3,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec >= \'"+cond.getIncallcount_p4()+"\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS incallcount_p4,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec >= \'"+cond.getIncallcount_p4()+"\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS incallduration_p4,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec >= \'0\' THEN\n" +
						"			1\n" +
						"		END\n" +
						"	) AS incallcount,\n" +
						"	SUM(\n" +
						"		CASE\n" +
						"		WHEN call_direction = \'out_in\'\n" +
						"		AND bridgesec >= \'0\' THEN\n" +
						"			bridgesec\n" +
						"		END\n" +
						"	) AS incallduration\n");
		}	
		whereSql.append(" FROM "+SpringPropertiesUtil.getProperty("sys.record.database")+"cdr WHERE caller_agent_id IS NOT NULL ");
		QueryUtils.dateTime(whereSql, params, cond.getStarttime(), " and end_stamp >= ? ");
		QueryUtils.dateTime(whereSql, params, cond.getEndtime(), " and end_stamp < ? ");
		
		//获取当前登录用户，进行权限控制
		String loginUser = SessionUtil.getCurrentUser(cond.getRequest()).getUid();
		if(!"0".equals(loginUser)){
			UserService service = ApplicationHelper.getApplicationContext().getBean(UserService.class);
			//获取当前用户管理下的所有未被删除的坐席id
			Collection<String> loginnames = service.getManagerUsernames(SessionUtil.getCurrentUser(cond.getRequest()).getUid());
			QueryUtils.in(whereSql, params, loginnames, " and caller_agent_id ");
		}
		
		whereSql.append(" GROUP BY caller_agent_id ) AS a");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" select count(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY a.");
		pageSql.append("SELECT * ").append(whereSql);

	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				cond.getRequest().getParameter("columns["+cond.getRequest().getParameter("order[0][column]")+"][data]"), 
				cond.getRequest().getParameter("order[0][dir]")});
		return list;
	}

}

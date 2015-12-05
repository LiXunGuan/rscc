package com.ruishengtech.rscc.crm.ui.mw.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.ui.mw.condition.QueueRunTimeCondition;

public class RtAgentStatusSolution implements ISolution{

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
		  QueueRunTimeCondition cond = (QueueRunTimeCondition) condition;
	      whereSql.append(" from "+SpringPropertiesUtil.getProperty("sys.record.database")+"tiers t left join "+SpringPropertiesUtil.getProperty("sys.record.database")+"rt_agent_status  rta on (t.agent=rta.agent_uid ) ");
	      if (StringUtils.isNotBlank(cond.getNumbers())) {
	    	  QueryUtils.queryData(whereSql, params, cond.getNumbers(), " where t.queue = ? ");
	      }
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" select count(*)  ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
		 pageSql.append("select rta.uuid, rta.agent_uid as agent_uid,rta.agent_info as agent_info, rta.extern as extern, rta.status as status,"
         		+ "rta.state as state,rta.number as number,rta.up_time as up_time,rta.in_count as in_count,rta.miss_count as miss_count ").append(whereSql);
         pageSql.append(" order by rta.agent_uid desc, ");
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		QueueRunTimeCondition cond = (QueueRunTimeCondition) condition;
		final String[] str = new String[]{cond.getRequest().getParameter("columns["+cond.getRequest().getParameter("order[0][column]")+"][data]") , cond.getRequest().getParameter("order[0][dir]")};
		return new ArrayList<String[]>(){{add(str);}};
	}

}

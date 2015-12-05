package com.ruishengtech.rscc.crm.cstm.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.cstm.condition.CstmPopLogCondition;

/**
 * @author Frank
 *
 */
public class CstmPopLogSolution implements ISolution{
	
	private CstmPopLogCondition cstmPopLogCondition;

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
	
		cstmPopLogCondition = (CstmPopLogCondition)condition;
		
		whereSql.append(" FROM cstm_pop_log WHERE 1 = 1 AND agent_id = ? ");
		params.add(cstmPopLogCondition.getAgentId());
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT *").append(whereSql);
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		cstmPopLogCondition = (CstmPopLogCondition)condition;
		
		final String[] str = new String[]{cstmPopLogCondition.getRequest().getParameter("columns["+cstmPopLogCondition.getRequest().getParameter("order[0][column]")+"][data]") , cstmPopLogCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}
	
}

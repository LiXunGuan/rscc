package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.DataPoolCondition;

public class DataPoolSolution implements ISolution{

	private DataPoolCondition dataPoolCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		dataPoolCondition = (DataPoolCondition) condition;
		whereSql.append("From data_data_pool t").append(" WHERE 1=1 ");
		
		QueryUtils.like(whereSql, params, dataPoolCondition.getPoolName(), " AND t.pool_name like ? ");	
		QueryUtils.like(whereSql, params, dataPoolCondition.getPoolDescribe(), " AND t.pool_describe like ? ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT * ").append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				dataPoolCondition.getRequest().getParameter("columns["+dataPoolCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				dataPoolCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

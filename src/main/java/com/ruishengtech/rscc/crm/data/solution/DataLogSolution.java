package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.DataLogCondition;

public class DataLogSolution implements ISolution{

	private DataLogCondition dataLogCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		dataLogCondition = (DataLogCondition) condition;
		whereSql.append("From data_log t").append(" WHERE 1=1 ");
		
		QueryUtils.like(whereSql, params, dataLogCondition.getDataName(), " AND t.data_name like ? ");	
		QueryUtils.like(whereSql, params, dataLogCondition.getSourceName(), " AND t.source_name like ? ");	
		QueryUtils.like(whereSql, params, dataLogCondition.getDataTable(), " AND t.data_table like ? ");
		QueryUtils.like(whereSql, params, dataLogCondition.getDistinctFlag(), " AND t.distinct_flag like ? ");
		QueryUtils.like(whereSql, params, dataLogCondition.getImportFlag(), " AND t.import_flag like ? ");
		QueryUtils.number(whereSql, params, dataLogCondition.getDataCountMin(),dataLogCondition.getDataCountMax(), 
				" AND t.data_count ");
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
				dataLogCondition.getRequest().getParameter("columns["+dataLogCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				dataLogCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

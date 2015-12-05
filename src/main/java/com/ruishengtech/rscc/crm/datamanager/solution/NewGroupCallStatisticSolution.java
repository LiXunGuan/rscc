package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.NewGroupCallDataCondition;

public class NewGroupCallStatisticSolution implements ISolution{

	private NewGroupCallDataCondition groupCallDataCondition ;
	
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		groupCallDataCondition = (NewGroupCallDataCondition) condition;
		whereSql.append(" From new_data_group_call_result t left join new_data_batch p on t.data_batch=p.uuid left join user_datarange q "
				+ "on t.data_dept=q.uuid left join new_data_group_call_link r on "
				+ "(r.groupcall_id=t.groupcall_id and r.data_batch=t.data_batch and r.data_dept=t.data_dept) ").append(" WHERE 1=1 ");
		
		QueryUtils.queryData(whereSql, params, groupCallDataCondition.getGroupcall_id(), " AND t.groupcall_id = ? ");
		QueryUtils.like(whereSql, params, groupCallDataCondition.getBatch_name(), " AND p.batch_name like ? ");
		QueryUtils.like(whereSql, params, groupCallDataCondition.getDept_name(), " AND q.datarange_name like ? ");
		whereSql.append(" group by t.data_batch,t.data_dept ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		
		pageSql.append("SELECT t.groupcall_id,t.data_batch,t.data_dept,r.data_count dataCount,"
				+ "sum( CASE t.call_result WHEN 2 THEN 1 ELSE 0 END ) noAnswer, "
				+ "sum( CASE t.call_result WHEN 3 THEN 1 ELSE 0 END ) singleAnswer, "
				+ "sum( CASE t.call_result WHEN 4 THEN 1 ELSE 0 END ) answered,p.batch_name batchName,q.datarange_name deptName ").append(whereSql);
		
		
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				groupCallDataCondition.getRequest().getParameter("columns["+groupCallDataCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				groupCallDataCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
	
	


}

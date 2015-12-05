package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.NewGroupCallDataCondition;

public class NewGroupCallDataSolution implements ISolution{

	private NewGroupCallDataCondition groupCallDataCondition ;
	
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		groupCallDataCondition = (NewGroupCallDataCondition) condition;
		whereSql.append("From new_data_group_call_result t left join new_data_batch p on t.data_batch=p.uuid left join user_datarange q "
				+ "on t.data_dept=q.uuid").append(" WHERE 1=1 ");
		
		QueryUtils.queryData(whereSql, params, groupCallDataCondition.getGroupcall_id(), " AND t.groupcall_id = ? ");
		QueryUtils.like(whereSql, params, groupCallDataCondition.getData_phone(), " AND t.data_phone like ? ");
		QueryUtils.like(whereSql, params, groupCallDataCondition.getBatch_name(), " AND p.batch_name like ? ");
		QueryUtils.like(whereSql, params, groupCallDataCondition.getDept_name(), " AND q.datarange_name like ? ");
		QueryUtils.queryData(whereSql, params, groupCallDataCondition.getCall_result(), " AND t.call_result = ? ");
//		QueryUtils.like(whereSql, params, groupCallDataCondition.getData_phone(), " AND t.data_phone like ? ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		
//		pageSql.append("SELECT * ").append(whereSql);
		pageSql.append("SELECT t.*,p.batch_name batchName,q.datarange_name deptName ").append(whereSql);
		
		
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

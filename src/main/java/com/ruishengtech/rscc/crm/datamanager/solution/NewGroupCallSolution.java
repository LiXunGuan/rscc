package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.GroupCallCondition;
import com.ruishengtech.rscc.crm.datamanager.condition.NewGroupCallCondition;

public class NewGroupCallSolution implements ISolution{

	private NewGroupCallCondition groupCallCondition ;
	
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		groupCallCondition = (NewGroupCallCondition) condition;
		whereSql.append("From new_data_group_call t ").append(" WHERE 1=1 ");
		
		QueryUtils.like(whereSql, params, groupCallCondition.getGroupcall_id(), " AND t.groupcall_id like ? ");
		QueryUtils.like(whereSql, params, groupCallCondition.getDescription(), " AND t.description like ? ");
		QueryUtils.like(whereSql, params, groupCallCondition.getStrategy(), " AND t.strategy like ? ");
		QueryUtils.like(whereSql, params, groupCallCondition.getConcurrency(), " AND t.concurrency like ? ");
		QueryUtils.like(whereSql, params, groupCallCondition.getGateway(), " AND t.gateway like ? ");
		QueryUtils.like(whereSql, params, groupCallCondition.getCaller_id_num(), " AND t.caller_id_num like ? ");
		QueryUtils.like(whereSql, params, groupCallCondition.getDst_exten(), " AND t.dst_exten like ? ");
		QueryUtils.like(whereSql, params, groupCallCondition.getData_src_url(), " AND t.data_src_url like ? ");
		QueryUtils.like(whereSql, params, groupCallCondition.getData_dst_url(), " AND t.data_dst_url like ? ");
		
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
		pageSql.append("SELECT t.* ").append(whereSql);
		
		
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				groupCallCondition.getRequest().getParameter("columns["+groupCallCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				groupCallCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
	
	


}

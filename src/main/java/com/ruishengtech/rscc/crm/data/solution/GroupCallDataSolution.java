package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.GroupCallDataCondition;

public class GroupCallDataSolution implements ISolution{

	private GroupCallDataCondition groupCallDataCondition ;
	
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		groupCallDataCondition = (GroupCallDataCondition) condition;
		whereSql.append("From data_group_call_link t left join data_container p on t.data_source = p.data_table ").append(" WHERE 1=1 ");
		
		QueryUtils.queryData(whereSql, params, groupCallDataCondition.getGroupcall_id(), " AND t.groupcall_id = ? ");
//		QueryUtils.like(whereSql, params, groupCallDataCondition.getGroupcall_id(), " AND t.groupcall_id like ? ");
		QueryUtils.like(whereSql, params, groupCallDataCondition.getData_phone(), " AND t.data_phone like ? ");
		QueryUtils.like(whereSql, params, groupCallDataCondition.getContainer_name(), " AND p.container_name like ? ");
		if(StringUtils.isNotBlank(groupCallDataCondition.getCall_flag())) {
			whereSql.append(" AND t.call_flag=? ");
			params.add(groupCallDataCondition.getCall_flag());
		}
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
		pageSql.append("SELECT t.*,p.container_name container ").append(whereSql);
		
		
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

package com.ruishengtech.rscc.crm.user.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.condition.GroupCondition;

public class GroupSolution implements ISolution {

	private GroupCondition groupCondition ;

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		groupCondition = (GroupCondition) condition;
		whereSql.append("From user_group t").append(" WHERE 1=1 ");
		
		QueryUtils.like(whereSql, params, groupCondition.getGroupName(), " AND t.group_name like ? ");	
		QueryUtils.like(whereSql, params, groupCondition.getGroupDescribe(), " AND t.group_describe like ? ");	
		
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
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		String orderColumn = groupCondition.getRequest().getParameter("columns["+groupCondition.getRequest().getParameter("order[0][column]")+"][data]");
		orderColumn = orderColumn.startsWith("parent")?"parentUuid":orderColumn;
		list.add(new String[]{orderColumn,groupCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}

}

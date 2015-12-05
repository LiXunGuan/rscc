package com.ruishengtech.rscc.crm.user.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.condition.PermissionCondition;

public class PermissionSolution implements ISolution{

	/**
	 * 条件
	 */
	private PermissionCondition permissionCondition ;

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		permissionCondition = (PermissionCondition) condition;
		whereSql.append("From user_permission t join user_action p on t.permission = p.uuid ").append(" WHERE 1=1 ");
		
		QueryUtils.like(whereSql, params, permissionCondition.getPermissionName(), " AND t.permission_name like ? ");	
		QueryUtils.like(whereSql, params, permissionCondition.getPermissionDescribe(), " AND t.permission_describe like ? ");	
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT t.*,p.action_type ").append(whereSql);
		
	}
	
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		String orderColumn = permissionCondition.getRequest().getParameter("columns["+permissionCondition.getRequest().getParameter("order[0][column]")+"][data]");
		orderColumn = orderColumn.startsWith("parent")?"parentUuid":orderColumn;
		list.add(new String[]{orderColumn,permissionCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}

}

package com.ruishengtech.rscc.crm.user.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.condition.PermissionRoleCondition;

public class PermissionRoleSolution implements ISolution{

	private PermissionRoleCondition permissionRoleCondition ;

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		permissionRoleCondition = (PermissionRoleCondition) condition;
		whereSql.append("From user_permission_role t").append(" WHERE 1=1 ");
		
		QueryUtils.like(whereSql, params, permissionRoleCondition.getPermissionRoleName(), " AND t.role_name like ? ");	
		QueryUtils.like(whereSql, params, permissionRoleCondition.getPermissionRoleDescribe(), " AND t.permissionrole_describe like ? ");	
		
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
		String orderColumn = permissionRoleCondition.getRequest().getParameter("columns["+permissionRoleCondition.getRequest().getParameter("order[0][column]")+"][data]");
		orderColumn = orderColumn.startsWith("parent")?"parentUuid":orderColumn;
		list.add(new String[]{orderColumn,permissionRoleCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}

}

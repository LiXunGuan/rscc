package com.ruishengtech.rscc.crm.user.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.condition.DatarangeRoleCondition;

public class DatarangeRoleSolution implements ISolution{


	private DatarangeRoleCondition datarangeRoleCondition ;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		datarangeRoleCondition = (DatarangeRoleCondition) condition;
		whereSql.append("From user_datarange_role t").append(" WHERE 1=1 ");
		
		QueryUtils.like(whereSql, params, datarangeRoleCondition.getDatarangeRoleName(), " AND t.role_name like ? ");	
		QueryUtils.like(whereSql, params, datarangeRoleCondition.getDatarangeRoleDescribe(), " AND t.datarangerole_describe like ? ");
		
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
		String orderColumn = datarangeRoleCondition.getRequest().getParameter("columns["+datarangeRoleCondition.getRequest().getParameter("order[0][column]")+"][data]");
		orderColumn = orderColumn.startsWith("parent")?"parentUuid":orderColumn;
		list.add(new String[]{orderColumn,datarangeRoleCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}

}

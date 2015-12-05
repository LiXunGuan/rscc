package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DepartmentDataCondition;

public class CustomerDataSolution implements ISolution{

	private DepartmentDataCondition cond;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		cond = (DepartmentDataCondition) condition;
		whereSql.append(" From new_data_batch_"+cond.getBatchUuid()+" dd "
				+ "left join new_data_batch db on db.uuid = dd.batch_uuid "
				+ "left join new_data_department de on de.uuid = dd.own_department "
				+ "left join user_user uu on dd.own_user = uu.uuid where 1=1 and dd.customer_uuid is not null ");
		QueryUtils.queryData(whereSql, params, cond.getBatchUuid(), " and dd.batch_uuid = ? ");
		QueryUtils.queryData(whereSql, params, cond.getDeptUuid(), " and dd.own_department = ? ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append(" SELECT dd.phone_number,"
				+ "dd.own_department_timestamp,"
				+ "dd.own_user_timestamp,"
				+ "db.batch_name as dataBatchName,"
				+ "de.department_name as deptName,"
				+ "uu.loginname as userName ")
			   .append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				cond.getRequest().getParameter("columns["+cond.getRequest().getParameter("order[0][column]")+"][data]"), 
				cond.getRequest().getParameter("order[0][dir]")});
		return list;
	}

}

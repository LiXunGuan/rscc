package com.ruishengtech.rscc.crm.billing.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.billing.condition.BillRateCondition;

public class BillRateSolution implements ISolution{
	
	private BillRateCondition brCondition;
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		brCondition = (BillRateCondition)condition;
		whereSql.append("FROM billing_rate b").append(" WHERE 1=1");
		QueryUtils.queryData(whereSql, params, brCondition.getBillRateType(), " and b.billratetype = ? ");
		QueryUtils.like(whereSql, params, brCondition.getBillRateName(), " AND b.billratename like ? ");	
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append("SELECT COUNT(*) ").append(whereSql);
		
	}
	
	public void getResultSql(StringBuilder resultSql, StringBuilder whereSql){
		resultSql.append("SELECT * ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{
			brCondition.getRequest().getParameter("columns["+brCondition.getRequest().getParameter("order[0][column]")+"][data]"),
			brCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}

}

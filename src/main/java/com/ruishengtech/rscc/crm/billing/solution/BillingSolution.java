package com.ruishengtech.rscc.crm.billing.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.billing.condition.BillingCondition;

public class BillingSolution implements ISolution{

	private BillingCondition billingCondition ;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		billingCondition = (BillingCondition) condition;
		whereSql.append("From billing_bill t").append(" WHERE 1=1 ");
		QueryUtils.queryData(whereSql, params, billingCondition.getType(), " and t.type = ? ");
		QueryUtils.like(whereSql, params, billingCondition.getCaller(), " AND t.caller like ? ");	
		QueryUtils.like(whereSql, params, billingCondition.getExten(), " AND t.exten like ? ");
		QueryUtils.like(whereSql, params, billingCondition.getAccessNumber(), " AND t.access_number like ? ");
		QueryUtils.like(whereSql, params, billingCondition.getDestNumber(), " AND t.dest_number like ? ");
		QueryUtils.number(whereSql, params, billingCondition.getCallMin()==null?null:billingCondition.getCallMin(), billingCondition.getCallMax()==null?null:billingCondition.getCallMax(), " AND t.duration ");
		QueryUtils.number(whereSql, params, billingCondition.getCostMin()==null?null:billingCondition.getCostMin(), billingCondition.getCostMax()==null?null:billingCondition.getCostMax(), " AND t.cost ");
		
		if (StringUtils.isNotBlank(billingCondition.getStartTime())) {
			QueryUtils.queryData(whereSql, params, billingCondition.getStartTime(), " and t.end_stamp >= ? ");
		}
		if (StringUtils.isNotBlank(billingCondition.getEndTime())) {
			QueryUtils.queryData(whereSql, params, billingCondition.getEndTime(), " and t.end_stamp <= ? ");
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
		
		
		pageSql.append("SELECT * ").append(whereSql);
		
		
	}
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		String orderColumn = billingCondition.getRequest().getParameter("columns["+billingCondition.getRequest().getParameter("order[0][column]")+"][data]");
		orderColumn = orderColumn.startsWith("parent")?"parentUuid":orderColumn;
		list.add(new String[]{orderColumn,billingCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
	
	public void getStatisticsSql(ICondition condition, StringBuilder sql, List<Object> params) {
		sql.append(" select SUM(t.duration) sumduration,AVG(t.duration) avgduration, SUM(t.cost) sumcost, AVG(t.cost) avgcost ");
		getWhere(condition, sql, params);
	}

}

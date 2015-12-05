package com.ruishengtech.rscc.crm.report.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.report.condition.ReportBillCondition;

public class ReportBillSolution implements ISolution{
	
	private ReportBillCondition reportBillCondition;
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		reportBillCondition = (ReportBillCondition) condition;
		
		whereSql.append(" FROM report_billing rb WHERE 1=1 ");
		if(StringUtils.isNotBlank(reportBillCondition.getBilling_date())){
			QueryUtils.queryData(whereSql, params, reportBillCondition.getBilling_date(), " and rb.billing_date = ? ");
		}else{
			if(StringUtils.isNotBlank(reportBillCondition.getStime())){
				QueryUtils.queryData(whereSql, params, reportBillCondition.getStime().substring(0,10), " and rb.billing_date >= ? ");
			} 
			if(StringUtils.isNotBlank(reportBillCondition.getEtime())) {
				QueryUtils.queryData(whereSql, params, reportBillCondition.getEtime().substring(0,10), " and rb.billing_date < ? ");
			}
		}
		if(StringUtils.isNotBlank(reportBillCondition.getBilling_type())){
			QueryUtils.like(whereSql, params, reportBillCondition.getBilling_type(), " and rb.billing_type like ? ");
		}
		if(StringUtils.isNotBlank(reportBillCondition.getName())){
			QueryUtils.like(whereSql, params, reportBillCondition.getName(), " and rb.name like ?");
		}
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT * ").append(whereSql);
		
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		reportBillCondition = (ReportBillCondition) condition;
		final String[] str = new String[]{reportBillCondition.getRequest().getParameter("columns["+reportBillCondition.getRequest().getParameter("order[0][column]")+"][data]") , reportBillCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}
	
	public void getStatisticsSql(ICondition condition, StringBuilder sql, List<Object> params) {
		sql.append(" select SUM(rb.call_charge) sumcost ");
		getWhere(condition, sql, params);
	}
}

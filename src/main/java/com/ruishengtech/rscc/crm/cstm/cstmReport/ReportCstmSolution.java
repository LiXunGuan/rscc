package com.ruishengtech.rscc.crm.cstm.cstmReport;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;

/**
 * @author Frank
 */
public class ReportCstmSolution implements ISolution {

	private ReportCstmCondition reportCstmCondition ; 
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {

		reportCstmCondition = (ReportCstmCondition)condition;
		whereSql.append(" FROM report_cstm l WHERE 1 = 1 ");
		if(StringUtils.isNotBlank(reportCstmCondition.getOpt_date())){
			String dateString = reportCstmCondition.getOpt_date();
			String[] str = dateString.split(" - ");
			
			QueryUtil.date(whereSql, params, str[0], " AND opt_date >= ? ");
			QueryUtil.date(whereSql, params, str[1], " AND opt_date <= ? ");
		}
		
		QueryUtil.like(whereSql, params, reportCstmCondition.getOpt_obj(), " AND opt_obj like ? ");
		QueryUtil.queryData(whereSql, params, reportCstmCondition.getPool_name(), " AND pool_name = ? ");
		QueryUtil.like(whereSql, params, reportCstmCondition.getOpt_source(), " AND opt_source like ? ");
		QueryUtil.queryData(whereSql, params, reportCstmCondition.getOpt_count(), " AND opt_count = ? ");
		
		
		whereSql.append(" GROUP BY substr(l.opt_date,1,10),l.pool_name ");

	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		
		countSql.append("SELECT COUNT(*) FROM ( SELECT COUNT(*) ").append(whereSql).append(" ) a ");
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append(" SELECT l.opt_date ,l.opt_obj ,l.opt_source ,l.pool_name ,SUM(l.opt_count) as opt_count ").append(whereSql);
	}

	@Override 
	public List<String[]> getOrderBy(ICondition condition) {

		reportCstmCondition = (ReportCstmCondition)condition; 
		final String[] str = new String[]{reportCstmCondition.getRequest().getParameter("columns["+reportCstmCondition.getRequest().getParameter("order[0][column]")+"][data]") , reportCstmCondition.getRequest().getParameter("order[0][dir]")};
		return new ArrayList<String[]>(){{add(str);}};
		
	}

}

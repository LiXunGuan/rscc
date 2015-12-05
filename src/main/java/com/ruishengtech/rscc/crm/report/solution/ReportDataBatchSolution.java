package com.ruishengtech.rscc.crm.report.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.report.condition.ReportDataBatchCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;

public class ReportDataBatchSolution implements ISolution{
	
	private ReportDataBatchCondition reportDataBatchCondition;
	
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		reportDataBatchCondition = (ReportDataBatchCondition) condition;
		
		whereSql.append(" FROM report_databatch rb WHERE 1=1 ");
		if(reportDataBatchCondition.getSelectionReport().equals("day")){
				if(reportDataBatchCondition.getStartTime().substring(0, 10).equals(reportDataBatchCondition.getEndTime().substring(0, 10))){
					QueryUtils.dateTimeString(whereSql, params, reportDataBatchCondition.getStartTime().substring(0,10), " AND rb.op_time = ?");
				}
				if(StringUtils.isNotBlank(reportDataBatchCondition.getStartTime())){
					QueryUtils.dateTimeString(whereSql, params, reportDataBatchCondition.getStartTime().substring(0,10), " AND rb.op_time >= ? ");
				}
				if(StringUtils.isNotBlank(reportDataBatchCondition.getEndTime())){
					QueryUtils.dateTimeString(whereSql, params, reportDataBatchCondition.getEndTime().substring(0,10), " AND rb.op_time < ? ");
				}
		}else if(reportDataBatchCondition.getSelectionReport().equals("month")){
				if(reportDataBatchCondition.getStartTime().substring(0, 7).equals(reportDataBatchCondition.getEndTime().substring(0, 7))){
					QueryUtils.dateTimeString(whereSql, params, reportDataBatchCondition.getStartTime().substring(0,7), " AND rb.op_time = ?");
				}
				if(StringUtils.isNotBlank(reportDataBatchCondition.getStartTime())){
					QueryUtils.dateTimeString(whereSql, params, reportDataBatchCondition.getStartTime().substring(0,7).trim(), " AND LENGTH(op_time) = 7 AND rb.op_time >= ? ");
				}
				if(StringUtils.isNotBlank(reportDataBatchCondition.getEndTime())){
					QueryUtils.dateTimeString(whereSql, params, reportDataBatchCondition.getEndTime().substring(0,7).trim(), " AND LENGTH(op_time) = 7 AND rb.op_time < ? ");
				}
		}
		if(reportDataBatchCondition.getAgents().size()!=0){
			QueryUtils.in(whereSql, params, reportDataBatchCondition.getAgents(), " AND rb.agent ");
		}
		//判断当前登录用户是否为admin 并且 该用户下面有管辖的坐席
		if(reportDataBatchCondition.getShowagents().size()>0 && !"0".equals(SessionUtil.getCurrentUser(reportDataBatchCondition.getRequest()).getUid())){
			QueryUtils.in(whereSql, params, reportDataBatchCondition.getShowagents(), " AND rb.agent ");
		}
	}
	
	public void getStatisticsSql(ICondition condition, StringBuilder sql, List<Object> params) {
		sql.append(" select SUM(rb.cstmadd_count) sumcstmadd,SUM(rb.cstmdel_count) sumcstmdel, SUM(rb.cstm_count) sumcstm,SUM(rb.intentadd_count) sumintentadd,SUM(rb.intentdel_count) sumintentdel, SUM(rb.intent_count) sumintent");
		getWhere(condition, sql, params);
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
		reportDataBatchCondition = (ReportDataBatchCondition) condition;
		final String[] str = new String[]{reportDataBatchCondition.getRequest().getParameter("columns["+reportDataBatchCondition.getRequest().getParameter("order[0][column]")+"][data]") , reportDataBatchCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}
	
	
}

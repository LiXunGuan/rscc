package com.ruishengtech.rscc.crm.knowledge.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.condition.ScheduleReminderCondition;

public class ScheduleReminderSolution implements ISolution{

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
		
		ScheduleReminderCondition cond = (ScheduleReminderCondition)condition;
		
		whereSql.append(" FROM schedule_reminder sr LEFT JOIN cstm_customer c ON c.phone_number=sr.phone_number where 1=1 ");
		
		if("agent".equals(cond.getLevel()) || "pop".equals(cond.getLevel())){
			QueryUtil.queryData(whereSql, params, cond.getUserUUid(), " and sr.schedule_user = ? ");
		}
		
//		if("0".equals(cond.getAdminflag()) || null == cond.getAdminflag()){}
		
		QueryUtil.like(whereSql, params, cond.getContent(), " and sr.schedule_content like ? ");
		
		QueryUtil.queryData(whereSql, params, cond.getCreatetimeS(), " and sr.schedule_createtime >= ? ");
		QueryUtil.queryData(whereSql, params, cond.getCreatetimeE(), " and sr.schedule_createtime < ? ");
		
		QueryUtil.queryData(whereSql, params, cond.getStartimeS(), " and sr.schedule_start_time >= ? ");
		QueryUtil.queryData(whereSql, params, cond.getStartimeE(), " and sr.schedule_start_time < ? ");
		
		if(StringUtils.isNotBlank(cond.getPhone1())){
			
			whereSql.append(" AND ( sr.phone_number = ? ");
			params.add(cond.getPhone1());
			
			if(StringUtils.isNotBlank(cond.getPhone2())){
				
				whereSql.append(" OR sr.phone_number = ? ) ");
				params.add(cond.getPhone2());
				
			}else{
				whereSql.append(" ) ");
			}
			
		}
		
	}
	
	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" select count(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
//		pageSql.append("select sr.* ").append(whereSql).append(" ORDER BY sr.schedule_createtime desc, ");
		pageSql.append("select sr.*,c.cstm_name as cstmName ").append(whereSql).append(" ORDER BY sr.schedule_createtime desc, ");
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		ScheduleReminderCondition cond = (ScheduleReminderCondition)condition;
		
		final String[] str = new String[]{cond.getRequest().getParameter("columns["+cond.getRequest().getParameter("order[0][column]")+"][data]") , cond.getRequest().getParameter("order[0][dir]")};
		
		return new ArrayList<String[]>(){{add(str);}};
	}
	
}

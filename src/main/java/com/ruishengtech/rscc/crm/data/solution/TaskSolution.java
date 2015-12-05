package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.TaskCondition;

public class TaskSolution implements ISolution{

	private TaskCondition taskCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		taskCondition = (TaskCondition) condition;
		whereSql.append("From ").append("data_task_" + taskCondition.getTaskTable()).append(" t left join data_container p on t.data_source"
				+ "=p.data_table left join sys_call_log q on t.item_phone = q.call_phone and t.item_owner = q.agent_id ").append(" WHERE 1=1 ");

		QueryUtils.like(whereSql, params, taskCondition.getItemName(), " AND t.item_name like ? ");	
		QueryUtils.like(whereSql, params, taskCondition.getItemPhone(), " AND t.item_phone like ? ");
		QueryUtils.like(whereSql, params, taskCondition.getItemAddress(), " AND t.item_address like ? ");
		QueryUtils.like(whereSql, params, taskCondition.getItemJson(), " AND t.item_json like ? ");
		QueryUtils.like(whereSql, params, taskCondition.getDataSource(), " AND p.container_name like ? ");
		QueryUtils.like(whereSql, params, taskCondition.getCallTimes(), " AND t.call_times like ? ");
		QueryUtils.like(whereSql, params, taskCondition.getCallTimes(), " AND t.call_times like ? ");
		QueryUtils.number(whereSql, params, taskCondition.getCallTimesMin(), taskCondition.getCallTimesMax(), " and t.call_times ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(distinct t.item_phone) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append("Group by t.item_phone ORDER BY ");
//		pageSql.append("SELECT t.uuid,t.item_name,t.item_phone,t.item_address,t.item_json,t.call_times,t.allocate_time,p.container_name data_source ").append(whereSql);
		pageSql.append("SELECT t.uuid,t.item_name,t.item_phone,t.item_address,t.item_json,t.call_times,t.allocate_time,max(q.call_time) lastcalltime,p.container_name data_source ").append(whereSql);
		System.out.println(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				taskCondition.getRequest().getParameter("columns["+taskCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				taskCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DataGlobalShareRecordCondition;

public class DataGlobalShareRecordSolution implements ISolution{

	private DataGlobalShareRecordCondition cond;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		cond = (DataGlobalShareRecordCondition) condition;
		whereSql.append(" From new_data_global_share_record dg "
				+ "	left join new_data_department_user_"+cond.getDeptuuid()+" du on dg.globalshare_uuid = du.uuid "
				+ " left join new_data_batch db on db.uuid = du.batch_uuid "
				+ " left join new_data_department de on de.uuid = du.own_department "
				+ " left join user_user uu on uu.uuid = dg.own_user "
				+ " where 1=1 and du.own_department = 'global_share' and dg.mark_save = '0' ");
		QueryUtils.queryData(whereSql, params, cond.getUseruuid(), " and dg.own_user = ? ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append(" SELECT db.batch_name as batchName,"
								+ "de.department_name as deptName,"
								+ "du.json as json,"
								+ "du.own_department_timestamp as ownDepartmentTimestamp,"
								+ "du.call_count as callCount,"
								+ "du.last_call_result as lastCallResult,"
								+ "du.last_call_time as lastCallTime,"
								+ "du.intent_type as intentType,"
								+ "du.intent_timestamp as intentTimestamp,dg.*  ").append(whereSql);
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

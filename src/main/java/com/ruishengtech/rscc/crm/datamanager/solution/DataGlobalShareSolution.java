package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DataGlobalShareCondition;

public class DataGlobalShareSolution implements ISolution{

	private DataGlobalShareCondition cond;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		cond = (DataGlobalShareCondition) condition;
		whereSql.append(" From new_data_global_share gs "
				+ "	left join new_data_batch db on db.uuid = gs.batch_uuid "
				+ " left join user_user uu on gs.own_user = uu.uuid "
				+ " left join user_user us on gs.transfer_user = us.uuid  "
				+ " left join new_data_department dd on gs.transfer_department = dd.uuid "
				+ " where 1=1 ");
		
		if(StringUtils.isNotBlank(cond.getBatchName())){
			QueryUtils.queryData(whereSql, params, cond.getBatchName(), " AND batch_uuid = ? ");
		}
		if(StringUtils.isNotBlank(cond.getPhoneNumber())){
			QueryUtils.queryData(whereSql, params, cond.getPhoneNumber(), " AND phone_number = ? ");
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
		pageSql.append(" SELECT gs.phone_number as phoneNumber,"
				+ "gs.json,gs.uuid,gs.batch_uuid,"
				+ "gs.own_user_timestamp,gs.own_user,"
				+ "gs.transfer_timestamp,gs.transfer_user,"
				+ "gs.call_count,"
				+ "gs.last_call_result,"
				+ "gs.last_call_time AS lastCallTime,"
				+ "gs.enter_count,"
				+ "gs.intent_type,"
				+ "gs.intent_timestamp,"
				+ "db.batch_name as batchName,"
				+ "uu.loginname as ownUserName,"
				+ "dd.department_name as transferDeptName,"
				+ "us.loginname as transferUserName ").append(whereSql);
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

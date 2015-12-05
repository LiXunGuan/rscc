package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DepartmentDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;

public class IntentDataAdminSolution implements ISolution{
	
	private DepartmentDataCondition cond;
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		cond = (DepartmentDataCondition) condition;
		if(cond.getAllDept() != null && cond.getAllDept().size() > 0){
			String deptss = "";
			for(int i = 0;i < cond.getAllDept().size() ; i++){
				if(i == 0){
					deptss = deptss + "(SELECT * FROM new_data_department_user_"+cond.getAllDept().get(i).getUid()+" WHERE intent_type IS NOT NULL UNION ";
				}else if(i == (cond.getAllDept().size()-1)){
					deptss = deptss + " SELECT * FROM new_data_department_user_"+cond.getAllDept().get(i).getUid()+" WHERE intent_type IS NOT NULL) ";
				}else{
					deptss = deptss + " SELECT * FROM new_data_department_user_"+cond.getAllDept().get(i).getUid()+" WHERE intent_type IS NOT NULL UNION";
				}
			}
		whereSql.append(" From " + deptss + " ndd "
				+ " left join  user_user uu on ndd.own_user = uu.uuid "
				+ " left join  new_data_intent ndt on ndd.intent_type = ndt.uuid "
				+ " left join  new_data_batch ndb on ndd.batch_uuid = ndb.uuid where 1=1 and ndd.intent_type is not null ");
		}else{
		whereSql.append(" From new_data_department_user_" + cond.getDeptUuid()+ " ndd "
				+ " left join  user_user uu on ndd.own_user = uu.uuid "
				+ " left join  new_data_intent ndt on ndd.intent_type = ndt.uuid "
				+ " left join  new_data_batch ndb on ndd.batch_uuid = ndb.uuid where 1=1 and ndd.intent_type is not null ");
		}
		
		if("default".equals(cond.getBatchUuid())){
			whereSql.append(" and ndd.batch_uuid = '' ");
		}else if(StringUtils.isNotBlank(cond.getBatchUuid())){
			QueryUtils.queryData(whereSql, params, cond.getBatchUuid(), "and ndd.batch_uuid = ? ");
		}
		
		
		
		if(StringUtils.isNotBlank(cond.getUserID())){
			QueryUtils.queryData(whereSql, params, cond.getUserID(), "and ndd.own_user = ? ");
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
		pageSql.append(" SELECT ndd.uuid,"
				+ "ndd.batch_uuid,"
				+ "ndd.phone_number,"
				+ "ndd.own_user,"
				+ "ndd.own_department,"
				+ "ndd.own_user_timestamp,"
				+ "ndd.call_count,"
				+ "ndd.last_call_result,"
				+ "ndd.last_call_time,"
				+ "ndd.intent_type,"
				+ "ndd.intent_timestamp,"
				+ "ndd.be_audit,"
				+ "ndb.batch_name as batchname,"
				+ "uu.user_describe as username,"
				+ "ndt.intent_name as intentTypeName"
				+ " ").append(whereSql);
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

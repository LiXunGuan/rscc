package com.ruishengtech.rscc.crm.datamanager.solution;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DataBatchDataCondition;


public class DataBatchDataSolution implements ISolution{

	private DataBatchDataCondition dataBatchDataCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		dataBatchDataCondition = (DataBatchDataCondition) condition;
		//left join data_project p on t.item_owner"+ "=p.uuid "
		whereSql.append(" From ").append("new_data_batch_" + dataBatchDataCondition.getBatchUuid()).append(" t ").append(" WHERE 1=1 ");

//		if(StringUtils.isNotBlank(dataBatchDataCondition.getDataFrom())){
//			QueryUtils.queryData(whereSql, params, itemCondition.getDataFrom(), "AND t.data_from = ?");
//			whereSql.append(" AND t.item_owner is null ");
//		}

		QueryUtils.like(whereSql, params, dataBatchDataCondition.getPhoneNumber(), " AND t.phone_number like ? ");
		
		if(StringUtils.isNotBlank(dataBatchDataCondition.getOwnDepartment()) && dataBatchDataCondition.getOwnDepartment().equals("nodept")){
			whereSql.append(" AND t.own_department is NULL");
		}else if (StringUtils.isNotBlank(dataBatchDataCondition.getOwnDepartment()) && dataBatchDataCondition.getOwnDepartment().equals("notnull")){
			whereSql.append(" AND t.own_department is not NULL");
		} else {
			QueryUtils.like(whereSql, params, dataBatchDataCondition.getOwnDepartment(), " AND t.own_department like ? ");
		}
		
		
		if(StringUtils.isNotBlank(dataBatchDataCondition.getOwnUser()) && dataBatchDataCondition.getOwnUser().equals("noagent")){
			whereSql.append(" AND t.own_user is NULL");
		}else{
			QueryUtils.like(whereSql, params, dataBatchDataCondition.getOwnUser(), " AND t.own_user like ? ");
		}
		
		if("globalShare".equals(dataBatchDataCondition.getOperation())){
			whereSql.append(" AND (t.own_user is NULL or t.own_user = '') ");
		}
		
		if(StringUtils.isNotBlank(dataBatchDataCondition.getIntentType()) && dataBatchDataCondition.getIntentType().equals("1")){
			whereSql.append(" AND t.intent_type is NOT NULL ");
		}else{
			QueryUtils.like(whereSql, params, dataBatchDataCondition.getIntentType(), " AND t.intent_type like ? ");
		}
		if(StringUtils.isNotBlank(dataBatchDataCondition.getCustomerUuid()) && dataBatchDataCondition.getCustomerUuid().equals("1")){
			whereSql.append(" AND t.customer_uuid is NOT NULL ");
		}
		if(StringUtils.isNotBlank(dataBatchDataCondition.getIsFrozen()) && "1".equals(dataBatchDataCondition.getIsFrozen())){
			QueryUtils.like(whereSql, params, dataBatchDataCondition.getIsFrozen(), " AND t.is_frozen like ? ");
		}
		if(StringUtils.isNotBlank(dataBatchDataCondition.getIsAbandon()) && "1".equals(dataBatchDataCondition.getIsAbandon())){
			QueryUtils.like(whereSql, params, dataBatchDataCondition.getIsAbandon(), " AND t.is_abandon like ? ");
		}
		if(StringUtils.isNotBlank(dataBatchDataCondition.getIsBlacklist()) && "1".equals(dataBatchDataCondition.getIsBlacklist())){
			QueryUtils.like(whereSql, params, dataBatchDataCondition.getIsBlacklist(), " AND t.is_blacklist like ? ");
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
		pageSql.append("SELECT t.uuid,"
				+ "t.batch_uuid,"
				+ "t.phone_number,"
				+ "t.json,"
				+ "t.own_department,"
				+ "t.own_user,"
				+ "t.call_count,"
				+ "t.intent_type,"
				+ "t.is_abandon,"
				+ "t.is_blacklist,"
				+ "t.is_frozen,"
				+ "t.customer_uuid ").append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				dataBatchDataCondition.getRequest().getParameter("columns["+dataBatchDataCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				dataBatchDataCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

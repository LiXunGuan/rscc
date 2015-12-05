package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.NewUserDataCondition;

/**
 * @author Frank
 */
public class NewUserDataSolution implements ISolution{

	private NewUserDataCondition newUserDataCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
		
		newUserDataCondition = (NewUserDataCondition) condition;
//		whereSql.append(" FROM `new_data_department_user_"+newUserDataCondition.getDeptName()+"` d, `new_data_batch` b, `new_data_department` p,user_user u ,`new_data_intent` i "
//				+ "WHERE d.batch_uuid = b.uuid AND d.own_department = p.uuid AND u.uuid = d.own_user ");
		
		
		whereSql.append(" FROM\n" +
			"	`new_data_department_user_"+newUserDataCondition.getDeptName()+"` d \n" +
			"	LEFT JOIN `new_data_batch` b ON d.batch_uuid = b.uuid \n" +
			"	LEFT JOIN `new_data_department` p ON d.batch_uuid = p.uuid\n" +
			"	LEFT JOIN user_user u ON u.uuid = d.own_user \n" +
			"	LEFT JOIN `new_data_intent` i ON d.intent_type = i.uuid WHERE 1 = 1 ");
		
		QueryUtil.queryData(whereSql, params, newUserDataCondition.getOwnUser(), " AND d.own_user = ? ");
		QueryUtil.queryData(whereSql, params, newUserDataCondition.getBatchUuid(), " AND d.batch_uuid = ? ");
		
		//查询批次名字
		QueryUtil.like(whereSql, params, newUserDataCondition.getBatchName(), " AND b.batch_name like ? ");
		QueryUtil.like(whereSql, params, newUserDataCondition.getPhoneNumber(), " AND d.phone_number like ? ");
		
		
		//呼叫次数判断
		if(StringUtils.isNotBlank(newUserDataCondition.getCallStatus())){
			
			if(("0").equals(newUserDataCondition.getCallStatus())){
				whereSql.append(" AND d.call_count = 0 ");
			}else{
				whereSql.append(" AND d.call_count != 0 ");
			}
		}
		

		if(StringUtils.isNotBlank(newUserDataCondition.getIntentType())){
			
			if("nonull".equals(newUserDataCondition.getIntentType())){
				whereSql.append(" AND i.uuid = d.intent_type ");
				whereSql.append(" AND d.intent_type IS NOT NULL and d.intent_timestamp > d.own_user_timestamp ");
			}else{
				whereSql.append(" AND i.uuid = d.intent_type ");
				whereSql.append(" AND d.intent_type = '"+ newUserDataCondition.getIntentType() +"' AND d.intent_timestamp > d.own_user_timestamp ");
			}
			
		}else{
			whereSql.append(" AND intent_type IS NULL ");
			whereSql.append(" AND d.own_department!='global_share' ");
		}

//		//筛选从共享池中获得的数据，第一个是意向客户，可能有从共享池中来的数据，因为共享池中的数据是呼通后保存为意向时更新的数据
//		if("nonull".equals(newUserDataCondition.getIntentType())){
//			whereSql.append(" AND i.uuid = d.intent_type ");
//			whereSql.append(" AND d.intent_type IS NOT NULL and d.intent_timestamp > d.own_user_timestamp ");
//		} else {	//这里不会有共享池中的数据，因为共享池中的数据只有呼通后才能转到自己的名下，而呼通后只能保存为共享
//			
//		}
		
//		if(!"1".equals(newUserDataCondition.getDetail()) && !"nonull".equals(newUserDataCondition.getIntentType())){
//			whereSql.append(" GROUP BY d.batch_uuid ");
//		}
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		
//		if(!"1".equals(newUserDataCondition.getDetail()) && "nonull".equals(newUserDataCondition.getIntentType())){
//			
//			countSql.append("SELECT COUNT(*) FROM ( SELECT COUNT(*) ").append(whereSql).append(" ) a ");
//		}else{
			
			countSql.append("SELECT COUNT(*) ").append(whereSql);
//		}
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
//		pageSql.append(" SELECT b.batch_name AS batchName, p.department_name as deptName, u.loginname as loginName, d.* "
		pageSql.append("SELECT" +
				"	b.batch_name AS batchname," +
				"	p.department_name AS deptname," +
				"	u.loginname AS loginName," +
				"	d.uuid," +
				"	d.batch_uuid," +
				"	d.phone_number," +
				"	d.json,\n" +
				"	d.own_department," +
				"	d.own_department_timestamp," +
				"	d.own_user," +
				"	d.own_user_timestamp," +
				"	d.call_count," +
				"	d.last_call_result," +
				"	d.last_call_time," +
				"	d.be_audit," +
				"	d.intent_timestamp," +
				"	i.intent_name AS intent_type").append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		newUserDataCondition = (NewUserDataCondition) condition;
		
		return newUserDataCondition.getOrderColumn(newUserDataCondition);
	}

}

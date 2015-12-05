package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.InvalidDataCondition;

public class InvalidDataSolution implements ISolution{
	
	private InvalidDataCondition cond;
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		cond = (InvalidDataCondition)condition;
		whereSql.append(" FROM (SELECT * From new_data_phone_resource WHERE is_abandon <> '0' OR is_blacklist <> '0' OR is_frozen <> '0') AS p WHERE 1=1 ");
		QueryUtils.queryData(whereSql, params, cond.getBatchUuid(), "AND p.batch_uuid = ? ");
		QueryUtils.queryData(whereSql, params, cond.getPhoneNumber(), "AND p.phone_number = ? ");
		if(StringUtils.isNotBlank(cond.getPhoneNumberState())){
			if(cond.getPhoneNumberState().equals("isAbandon")){
				cond.setPhoneNumberState("is_abandon");
			}else if(cond.getPhoneNumberState().equals("isBlacklist")){
				cond.setPhoneNumberState("is_blacklist");
			}else if(cond.getPhoneNumberState().equals("isFrozen")){
				cond.setPhoneNumberState("is_frozen");
			}
			whereSql.append(" AND p." + cond.getPhoneNumberState() + " = '1' ");
		}
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*)").append(whereSql);
		
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT * ").append(whereSql);

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

package com.ruishengtech.rscc.crm.cstm.solution;

import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;

public class CaseSolution implements ISolution{

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.ruishengtech.rscc.crm.qc.solution;

import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;

public class QualityCheckSolution implements ISolution{

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		return null;
	}

}

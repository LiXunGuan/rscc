package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.ResultDesignCondition;

public class ResultDesignSolution implements ISolution{
	
	
	private ResultDesignCondition resultDesignCondition;

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
	
		resultDesignCondition = (ResultDesignCondition)condition;
		
		whereSql.append(" FROM design_column WHERE 1 = 1 AND tableName='result'");
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT *").append(whereSql);
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		resultDesignCondition = (ResultDesignCondition)condition;
		
		final String[] str = new String[]{resultDesignCondition.getRequest().getParameter("columns["+resultDesignCondition.getRequest().getParameter("order[0][column]")+"][data]") , resultDesignCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

}

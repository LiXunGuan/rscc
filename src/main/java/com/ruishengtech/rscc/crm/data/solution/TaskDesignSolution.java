package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.TaskDesignCondition;

public class TaskDesignSolution implements ISolution{
	
	
	private TaskDesignCondition taskDesignCondition;

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
	
		taskDesignCondition = (TaskDesignCondition)condition;
		
		whereSql.append(" FROM design_column WHERE 1 = 1 AND tableName='task'");
		
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
		
		taskDesignCondition = (TaskDesignCondition)condition;
		
		final String[] str = new String[]{taskDesignCondition.getRequest().getParameter("columns["+taskDesignCondition.getRequest().getParameter("order[0][column]")+"][data]") , taskDesignCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

}

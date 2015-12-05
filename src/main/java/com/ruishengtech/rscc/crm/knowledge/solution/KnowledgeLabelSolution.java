package com.ruishengtech.rscc.crm.knowledge.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.condition.KnowledgeLabelCondition;

public class KnowledgeLabelSolution implements ISolution{

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
		
		condition = (KnowledgeLabelCondition)condition;
		
		whereSql.append(" from  knowledge_label ");
	}
	
	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" select count(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		
		pageSql.append("select * ").append(whereSql);
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		KnowledgeLabelCondition knowledgeLabelCondition = (KnowledgeLabelCondition)condition;
		
		final String[] str = new String[]{knowledgeLabelCondition.getRequest().getParameter("columns["+knowledgeLabelCondition.getRequest().getParameter("order[0][column]")+"][data]") , knowledgeLabelCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

}

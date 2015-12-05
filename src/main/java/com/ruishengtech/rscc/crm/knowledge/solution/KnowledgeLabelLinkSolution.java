package com.ruishengtech.rscc.crm.knowledge.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.condition.KnowledgeLabelLinkCondition;

public class KnowledgeLabelLinkSolution implements ISolution{

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" select count(*) ").append(whereSql);
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		KnowledgeLabelLinkCondition knowledgeLabelLinkCondition = (KnowledgeLabelLinkCondition)condition;
		
		final String[] str = new String[]{knowledgeLabelLinkCondition.getRequest().getParameter("columns["+knowledgeLabelLinkCondition.getRequest().getParameter("order[0][column]")+"][data]") , knowledgeLabelLinkCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		
		pageSql.append("select * ").append(whereSql);
	}

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
		
		condition = (KnowledgeLabelLinkCondition)condition;
		
		whereSql.append(" from  knowledge_label_link ");
	}

}

package com.ruishengtech.rscc.crm.knowledge.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.condition.KnowledgeDirectoryCondition;

public class KnowledgeDirectorySolution implements ISolution{

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		KnowledgeDirectoryCondition	cond = (KnowledgeDirectoryCondition)condition;
		
		whereSql.append(" from  knowledge_directory where 1=1 ");
		
		QueryUtil.like(whereSql, params, cond.getDname(), " and  directory_name like ? ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" select count(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		pageSql.append("select * ").append(whereSql).append(" ORDER BY ");
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		KnowledgeDirectoryCondition knowledgeDirectoryCondition = (KnowledgeDirectoryCondition)condition;
		
		final String[] str = new String[]{knowledgeDirectoryCondition.getRequest().getParameter("columns["+knowledgeDirectoryCondition.getRequest().getParameter("order[0][column]")+"][data]") , knowledgeDirectoryCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

}

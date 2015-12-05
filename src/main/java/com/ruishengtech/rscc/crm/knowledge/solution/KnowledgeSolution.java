package com.ruishengtech.rscc.crm.knowledge.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.condition.KnowledgeCondition;

public class KnowledgeSolution implements ISolution{

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		KnowledgeCondition cond = (KnowledgeCondition)condition;
		
		whereSql.append(" FROM knowledge_info k "
				+ "LEFT JOIN knowledge_directory kd ON kd.uuid = k.directory_uuid "
				+ "LEFT JOIN knowledge_label_link li ON k.uuid = li.knowledge_uuid "
				+ "LEFT JOIN knowledge_label la ON la.uuid = li.knowledge_label_uuid "
				+ "WHERE 1=1 ");
		
		QueryUtil.like(whereSql, params, cond.getTitle(), " and k.knowledge_title like ? ");
		QueryUtil.like(whereSql, params, cond.getContent(), " and k.knowledge_content like ? ");
		QueryUtil.queryData(whereSql, params, cond.getDirectory(), " and kd.directory_name = ? ");
		QueryUtil.like(whereSql, params, cond.getTag(), " and la.label_name like ? ");
		
		whereSql.append(" GROUP BY k.uuid ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) FROM ( select count(*) ").append(whereSql).append(" ) a");
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		pageSql.append("SELECT k.*,la.label_name,group_concat(label_name) AS knowledgeTags,kd.directory_name AS direName ").append(whereSql).append(" ORDER BY ");
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		KnowledgeCondition knowledgeCondition = (KnowledgeCondition)condition;
		
		final String[] str = new String[]{knowledgeCondition.getRequest().getParameter("columns["+knowledgeCondition.getRequest().getParameter("order[0][column]")+"][data]") , knowledgeCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	
	}

}

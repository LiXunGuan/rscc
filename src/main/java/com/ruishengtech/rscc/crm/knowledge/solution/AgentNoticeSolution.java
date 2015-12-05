package com.ruishengtech.rscc.crm.knowledge.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.condition.AgentNoticeCondition;

public class AgentNoticeSolution implements ISolution{
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
		
		AgentNoticeCondition cond = (AgentNoticeCondition)condition;
		
		
//		if("agent".equals(cond.getOperation())){
//			QueryUtil.queryData(whereSql, params, cond.getUserUUid(), " and an.create_user_uuid = ? ");
//		}
		
		if("agent".equals(cond.getLevel())){
			whereSql.append(" from agent_notice an LEFT JOIN agent_notice_user_link anu on an.uuid = anu.agent_notice_uuid where 1=1 ");
			QueryUtil.queryData(whereSql, params, cond.getUserUUid(), " and anu.user_uuid = ? ");
		}else{
			whereSql.append(" from agent_notice an where 1=1 ");
		}
		
		QueryUtil.like(whereSql, params, cond.getTitle(), " and ( an.notice_title like ? ");
		QueryUtil.like(whereSql, params, cond.getTitle(), " or an.notice_content like ? ) ");
		QueryUtil.queryData(whereSql, params, cond.getPublishStatus(), " and an.publish_status = ? ");
		QueryUtil.date(whereSql, params, cond.getsCreateTime(), " and an.create_time >= ? ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" select count(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		pageSql.append("select an.* ").append(whereSql).append(" ORDER BY an.create_time desc, ");
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		AgentNoticeCondition cond = (AgentNoticeCondition)condition;
		
		final String[] str = new String[]{cond.getRequest().getParameter("columns["+cond.getRequest().getParameter("order[0][column]")+"][data]") , cond.getRequest().getParameter("order[0][dir]")};
		
		return new ArrayList<String[]>(){{add(str);}};
	}

}

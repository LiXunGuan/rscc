package com.ruishengtech.rscc.crm.ui.mw.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.ui.mw.condition.QueueRunTimeCondition;

public class RtQueueStatusSolution implements ISolution{

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
		QueueRunTimeCondition cond = (QueueRunTimeCondition) condition;
        whereSql.append(" from "+SpringPropertiesUtil.getProperty("sys.record.database")+"rt_queue_status where 1=1 " );
        if(!StringUtils.isBlank(cond.getNumbers())) {
        	QueryUtils.queryData(whereSql, params,cond.getNumbers(), " and queue_id = ? ");
		}
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" select count(*)  ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
		 pageSql.append("select * ").append(whereSql);
         pageSql.append(" order by id asc ");
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
//		QueueRunTimeCondition cond = (QueueRunTimeCondition) condition;
//		final String[] str = new String[]{cond.getRequest().getParameter("columns["+cond.getRequest().getParameter("order[0][column]")+"][data]") , cond.getRequest().getParameter("order[0][dir]")};
//		return new ArrayList<String[]>(){{add();}};
		
		
		return new ArrayList<String[]>();
	}

}

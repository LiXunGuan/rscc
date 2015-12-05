package com.ruishengtech.rscc.crm.ui.mw.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.ui.mw.condition.QueueRunTimeCondition;

public class MembersSolution implements ISolution {

	private QueueRunTimeCondition cond;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
		cond = (QueueRunTimeCondition) condition;
        whereSql.append(" from "+SpringPropertiesUtil.getProperty("sys.record.database")+"members_view where 1=1 ");
        QueryUtils.queryData(whereSql, params, cond.getNumbers(), " and queue = ? ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		pageSql.append("select name,cid_number,joined_epoch ").append(whereSql);
        pageSql.append(" order by joined_epoch asc, ");
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		cond = (QueueRunTimeCondition) condition;
		final String[] str = new String[]{cond.getRequest().getParameter("columns["+cond.getRequest().getParameter("order[0][column]")+"][data]") , cond.getRequest().getParameter("order[0][dir]")};
		return new ArrayList<String[]>(){{add(str);}};
	}


}

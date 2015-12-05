package com.ruishengtech.rscc.crm.issue.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.issue.condition.CstmIssueCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;

public class CstmIssueSolution implements ISolution{

	private CstmIssueCondition cstmserviceCondition;
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {

		cstmserviceCondition = (CstmIssueCondition) condition;
		
		whereSql.append(" FROM cstm_cstmservice WHERE 1 = 1 ");
		
		QueryUtil.like(whereSql, params, cstmserviceCondition.getCstmserviceId(), "  AND cstmservice_id like ?  ");
		QueryUtil.like(whereSql, params, cstmserviceCondition.getCstmserviceAssignee(), "  AND user_name like ?  "); //其实是查询的user_name字段
		
		QueryUtil.like(whereSql, params, cstmserviceCondition.getCstmserviceReporter(), "  AND  cstmservice_reporter like ?  ");
		QueryUtil.like(whereSql, params, cstmserviceCondition.getCstmserviceName(), "  AND cstmservice_name like ?  ");
		QueryUtil.like(whereSql, params, cstmserviceCondition.getCstmserviceDescription(), " AND cstmservice_description like ? ");
		QueryUtil.queryData(whereSql, params, cstmserviceCondition.getCstmserviceStatus(), " AND cstmservice_status = ? ");
		
		/**
		 * 我的工单 只能看我和我自己相关联的工单  
		 * 
		 * 工单管理 管理员身份可以看到所有工单
		 *
		 * 工单管理 班长身份可以查看他管辖范围的人的所有工单
		 */

		//不是我的工单菜单
		if(StringUtils.isNotBlank(cstmserviceCondition.getLevels())){
			
			whereSql.append(" AND ( user_name = ?  OR cstmservice_reporter = ? )");
			params.add(cstmserviceCondition.getUserName());
			params.add(cstmserviceCondition.getUserName());
		}else{
			
			//如果是管理员查看所有  
			//如果是班长
			if(!"1".equals(SessionUtil.getCurrentUser(cstmserviceCondition.getRequest()).getAdminFlag())){
				Collection<String> col = (Collection<String>) cstmserviceCondition.getRequest().getAttribute("manageList");
				
				if(null != col && col.size() > 0){
					whereSql.append(" AND (");
					QueryUtils.in(whereSql, params, col, " cstmservice_reporter_uuid ");
					whereSql.append(" OR ");
					QueryUtils.in(whereSql, params, col, " cstmservice_assignee ");
					whereSql.append(" ) ");
				}
			}
		}
		
		
		//如果是管理员，查看所有
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {

		countSql.append(" SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {

		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT * ").append(whereSql);
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {

		cstmserviceCondition = (CstmIssueCondition) condition;
		final String[] str = new String[]{cstmserviceCondition.getRequest().getParameter("columns["+cstmserviceCondition.getRequest().getParameter("order[0][column]")+"][data]") , cstmserviceCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

}

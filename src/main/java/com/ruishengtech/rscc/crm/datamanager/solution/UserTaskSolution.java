package com.ruishengtech.rscc.crm.datamanager.solution;



import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.UserTaskCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
public class UserTaskSolution implements ISolution{
	
	private UserTaskCondition userTaskCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		userTaskCondition = (UserTaskCondition) condition;
		whereSql.append("From new_data_department_user t join user_user p on t.uuid = p.uuid  "
				+ " join user_datarange q on t.department_uuid=q.uuid WHERE 1=1 ");
		
//		QueryUtils.queryData(whereSql, params, userTaskCondition.getUsername(), " and p.loginname = ? ");
		QueryUtils.like(whereSql, params, userTaskCondition.getLoginname(), " and p.loginname like ? ");
		QueryUtils.like(whereSql, params, userTaskCondition.getUsername(), " and p.user_describe like ? ");
		QueryUtils.queryData(whereSql, params, userTaskCondition.getDeptname(), " and q.datarange_name = ? ");
		
		if(!"1".equals(SessionUtil.getCurrentUser(userTaskCondition.getRequest()).getAdminFlag()) && userTaskCondition.getIns() != null) {
			if(userTaskCondition.getIns().size() > 0) {
				QueryUtils.in(whereSql, params, userTaskCondition.getIns(), " AND( t.department_uuid ");
			} else {
				whereSql.append(" and (1=2 ");
			}
			whereSql.append(")");
		}
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT t.*, p.loginname loginName, p.user_describe userName, q.datarange_name deptName ").append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				userTaskCondition.getRequest().getParameter("columns["+userTaskCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				userTaskCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

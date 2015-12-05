package com.ruishengtech.rscc.crm.user.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.condition.UserCondition;

public class UserSolution implements ISolution{

	private UserCondition userCondition ;
	
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		/*	使用concat实现
		 * SELECT
				t.*, p.datarange_name departmentName, q.role
			FROM
				user_user t
			LEFT JOIN user_datarange p ON t.department = p.uuid
			LEFT JOIN (
				SELECT
					l.user_uuid,
					GROUP_CONCAT(r.role_name) role
				FROM
					user_user_permissionrole_link l
				LEFT JOIN user_permission_role r ON l.permissionrole_uuid = r.uuid
				GROUP BY
					l.user_uuid
			) q ON t.uuid = q.user_uuid
			WHERE
				1 = 1
			AND delete_flag = '0'
			ORDER BY
				loginname DESC
		 */
			
		userCondition = (UserCondition) condition;
		whereSql.append("From user_user t left join user_datarange p on t.department=p.uuid ").append(" WHERE 1=1 AND delete_flag='0' ");
		
		QueryUtils.like(whereSql, params, userCondition.getLoginName(), " AND t.loginname like ? ");	
		QueryUtils.like(whereSql, params, userCondition.getPhone(), " AND t.phone like ? ");
		QueryUtils.like(whereSql, params, userCondition.getUserDescribe(), " AND t.user_describe like ? ");
		QueryUtils.like(whereSql, params, userCondition.getDepartmentName(), " AND p.datarange_name like ? ");
		if(!"1".equals(SessionUtil.getCurrentUser(userCondition.getRequest()).getAdminFlag()) && userCondition.getIns() != null) {
//			whereSql.append("(");
			if(userCondition.getIns().size() > 0) {
				QueryUtils.in(whereSql, params, userCondition.getIns(), " AND( t.department ");
			} else {
				whereSql.append(" and (1=2 ");
			}
			whereSql.append(")");
		}
		if(userCondition.getUuids()!=null && userCondition.getUuids().size()> 0){
			QueryUtils.in(whereSql, params, userCondition.getUuids(), " AND t.uuid ");
		}
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(*) from (SELECT t.*,p.datarange_name departmentName ").append(whereSql).append(") as q");
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		
		pageSql.append("SELECT t.*,p.datarange_name departmentName ").append(whereSql);
		
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				userCondition.getRequest().getParameter("columns["+userCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				userCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
	
	


}

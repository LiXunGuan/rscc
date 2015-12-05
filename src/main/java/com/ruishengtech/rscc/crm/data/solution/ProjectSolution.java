package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.ProjectCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;

public class ProjectSolution implements ISolution{

	private ProjectCondition projectCondition ;
	
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		projectCondition = (ProjectCondition) condition;
		whereSql.append("From data_project t join user_user p on t.uuid = p.uuid join user_datarange q on p.department = q.uuid ").append(" WHERE 1=1 ");
		
		QueryUtils.like(whereSql, params, projectCondition.getProjectName(), " AND t.project_name like ? ");
		QueryUtils.like(whereSql, params, projectCondition.getDepartment(), " AND q.datarange_name like ? ");
		QueryUtils.number(whereSql, params, projectCondition.getDataMin(), projectCondition.getDataMax(), " AND t.data_count ");
		QueryUtils.number(whereSql, params, projectCondition.getCompleteMin(), projectCondition.getCompleteMin(), " AND t.complete_count ");
		QueryUtils.like(whereSql, params, projectCondition.getProjectInfo(), " AND t.project_info like ? ");
		QueryUtils.like(whereSql, params, projectCondition.getProjectStat(), " AND t.project_stat like ? ");
		QueryUtils.like(whereSql, params, projectCondition.getProjectDescribe(), " AND t.project_describe like ? ");
		if(!"1".equals(SessionUtil.getCurrentUser(projectCondition.getRequest()).getAdminFlag()) && projectCondition.getIns() != null){
			if(projectCondition.getIns().size() > 0) {
				QueryUtils.in(whereSql, params, projectCondition.getIns(), " AND( q.uuid ");
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
		
//		pageSql.append("SELECT * ").append(whereSql);
		pageSql.append("SELECT t.*,q.datarange_name department ").append(whereSql);
		
		
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				projectCondition.getRequest().getParameter("columns["+projectCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				projectCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
	
	


}

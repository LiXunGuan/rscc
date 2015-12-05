package com.ruishengtech.rscc.crm.datamanager.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DeptDataCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;

public class DeptDataSolution implements ISolution{

	private DeptDataCondition cond;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		cond = (DeptDataCondition) condition;
		whereSql.append(" FROM new_data_batch_department_link dl "
				+ "left join new_data_department dd on dl.department_uuid = dd.uuid "
				+ "left join new_data_batch db on db.uuid = dl.data_batch_uuid where 1=1 ");
		QueryUtils.like(whereSql, params, cond.getBatchname(), " and db.batch_name like ? ");
		QueryUtils.like(whereSql, params, cond.getDeptname(), " and dd.department_name like ? ");
		
		if(!"1".equals(SessionUtil.getCurrentUser(cond.getRequest()).getAdminFlag()) && cond.getIns() != null) {
			if(cond.getIns().size() > 0) {
				QueryUtils.in(whereSql, params, cond.getIns(), " AND( dl.department_uuid ");
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
		pageSql.append("SELECT dl.single_limit,"
				+ "dl.day_limit,"
				+ "dl.department_uuid,"
				+ "dl.data_batch_uuid,"
				+ "dl.open_flag,"
				+ "dl.data_count,"
				+ "dl.own_count,"
				+ "dl.share_count,"
				+ "dl.intent_count,"
				+ "dl.customer_count,"
				+ "dl.abandon_count,"
				+ "dl.blacklist_count,"
				+ "dl.is_lock,"
				+ "dl.is_auto,"
				+ "dl.return_times,"
				+ "dd.department_name as deptname,"
				+ "db.batch_name as batchname,"
				+ "dd.total_limit as totalLimit,"
				+ "db.data_count as batchDataCount,"
				+ "db.own_count as batchOwnCount ")
			   .append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				cond.getRequest().getParameter("columns["+cond.getRequest().getParameter("order[0][column]")+"][data]"), 
				cond.getRequest().getParameter("order[0][dir]")});
		list.add(new String[]{"batchname", "desc"});
		return list;
	}
	
}

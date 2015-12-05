package com.ruishengtech.rscc.crm.cstm.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerPoolCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;

/**
 * @author Frank
 *
 */
public class CustomerPoolSolution implements ISolution {

	private CustomerPoolCondition customerPoolCondition;

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {

		customerPoolCondition = (CustomerPoolCondition) condition;

		whereSql.append(" FROM cstm_customer_pool p,user_user u WHERE p.creater = u.uuid ");

		//如果不是管理员  //只能看到自己创建的 自己所属角色包含的部门所关联的客户池
		if(!("1").equals(SessionUtil.getCurrentUser(customerPoolCondition.getRequest()).getAdminFlag())){
			
			if( customerPoolCondition.getIns().size() >0 ){
				QueryUtils.in(whereSql, params, customerPoolCondition.getIns(), " AND ( p.uuid ");
			} else {
				whereSql.append(" and (1=2 ");
			}
			
			whereSql.append(" OR creater = ? ");
			params.add(customerPoolCondition.getCreater());
			whereSql.append(")");
		}
		
		QueryUtils.queryData(whereSql, params, customerPoolCondition.getPoolName(), " AND pool_name = ? ");
		
		QueryUtils.like(whereSql, params, customerPoolCondition.getPoolCreater(), " AND u.loginname like ? ");
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);

	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT p.*,u.loginname as poolCreater").append(whereSql);

	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		customerPoolCondition = (CustomerPoolCondition) condition;
		final String[] str = new String[]{customerPoolCondition.getRequest().getParameter("columns["+customerPoolCondition.getRequest().getParameter("order[0][column]")+"][data]") , customerPoolCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

}

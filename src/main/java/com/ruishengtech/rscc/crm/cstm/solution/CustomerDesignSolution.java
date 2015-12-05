package com.ruishengtech.rscc.crm.cstm.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerDesignCondition;

public class CustomerDesignSolution implements ISolution{
	
	
	private CustomerDesignCondition customerDesignCondition;

	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
	
		customerDesignCondition = (CustomerDesignCondition)condition;
		
		whereSql.append(" FROM design_column WHERE 1 = 1 ");
		QueryUtil.queryData(whereSql, params, customerDesignCondition.getTableName(), "  AND  tableName = ? ");
		
		
		if(StringUtils.isNotBlank(customerDesignCondition.getShowAll())){
			
			if(customerDesignCondition.getShowAll().equals("false")){
				
				whereSql.append(" AND is_default != 1 ");
			}
		}
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT *").append(whereSql);
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		
		customerDesignCondition = (CustomerDesignCondition)condition;
		
		final String[] str = new String[]{customerDesignCondition.getRequest().getParameter("columns["+customerDesignCondition.getRequest().getParameter("order[0][column]")+"][data]") , customerDesignCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

}

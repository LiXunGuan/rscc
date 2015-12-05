package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.ItemCondition;

public class ItemSolution implements ISolution{

	private ItemCondition itemCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		itemCondition = (ItemCondition) condition;
		whereSql.append("From ").append("data_item_" + itemCondition.getItemTable()).append(" t left join data_project p on t.item_owner"
				+ "=p.uuid ").append(" WHERE 1=1 ");

		if(StringUtils.isNotBlank(itemCondition.getDataFrom())){
			QueryUtils.queryData(whereSql, params, itemCondition.getDataFrom(), "AND t.data_from = ?");
			whereSql.append(" AND t.item_owner is null ");
		}
		QueryUtils.like(whereSql, params, itemCondition.getItemName(), " AND t.item_name like ? ");	
		QueryUtils.like(whereSql, params, itemCondition.getItemPhone(), " AND t.item_phone like ? ");
		QueryUtils.like(whereSql, params, itemCondition.getItemAddress(), " AND t.item_address like ? ");
		QueryUtils.like(whereSql, params, itemCondition.getItemJson(), " AND t.item_json like ? ");
		QueryUtils.like(whereSql, params, itemCondition.getItemOwner(), " AND p.project_name like ? ");
		QueryUtils.like(whereSql, params, itemCondition.getCallTimes(), " AND t.call_times like ? ");
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT t.uuid,t.item_name,t.item_phone,t.item_address,t.item_json,t.call_times,t.allocate_time,p.project_name item_owner ").append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				itemCondition.getRequest().getParameter("columns["+itemCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				itemCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

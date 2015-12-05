package com.ruishengtech.rscc.crm.data.solution;

import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.DataContainerCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;

public class DataContainerSolution implements ISolution{

	private DataContainerCondition dataContainerCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		dataContainerCondition = (DataContainerCondition) condition;
		whereSql.append("From data_container t left join user_user p on t.upload_user = p.uuid ").append(" WHERE 1=1 AND container_type = ? ");
		params.add(dataContainerCondition.getContainerType());
		QueryUtils.like(whereSql, params, dataContainerCondition.getContainerName(), " AND t.container_name like ? ");	
		QueryUtils.like(whereSql, params, dataContainerCondition.getDataInfo(), " AND t.data_info like ? ");	
		QueryUtils.like(whereSql, params, dataContainerCondition.getDataTable(), " AND t.data_table like ? ");
		QueryUtils.like(whereSql, params, dataContainerCondition.getDistinctFlag(), " AND t.distinct_flag like ? ");
		QueryUtils.number(whereSql, params, dataContainerCondition.getDataCountMin(), dataContainerCondition.getDataCountMax(), 
				" AND t.data_count ");
		QueryUtils.number(whereSql, params, dataContainerCondition.getAllocateMin(), dataContainerCondition.getAllocateMax(), 
				" AND t.allocate_count ");
		if(!"1".equals(SessionUtil.getCurrentUser(dataContainerCondition.getRequest()).getAdminFlag()) && dataContainerCondition.getIns() != null) {
//			whereSql.append("(");
			if(dataContainerCondition.getIns().size() > 0) {
				QueryUtils.in(whereSql, params, dataContainerCondition.getIns(), " AND( t.uuid ");
			} else {
				whereSql.append(" and (1=2 ");
			}
			whereSql.append(" or t.upload_user=? ");
			params.add(SessionUtil.getCurrentUser(dataContainerCondition.getRequest()).getUid());
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
		pageSql.append("SELECT t.uuid,t.container_name,t.container_type,t.data_count,t.allocate_count,t.data_info,t.data_table,t.distinct_flag,t.data_createtime,t.file_path,p.loginname upload_user ").append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				dataContainerCondition.getRequest().getParameter("columns["+dataContainerCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				dataContainerCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

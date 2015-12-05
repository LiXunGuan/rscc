package com.ruishengtech.rscc.crm.datamanager.solution;



import java.util.ArrayList;
import java.util.List;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DataBatchCondition;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
public class DataBatchSolution implements ISolution{
	
	private DataBatchCondition dataBatchCondition;
	
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		dataBatchCondition = (DataBatchCondition) condition;
		whereSql.append("From new_data_batch t left join user_user p on t.upload_user = p.uuid  WHERE 1=1 ");
		QueryUtils.like(whereSql, params, dataBatchCondition.getBatchName(), " AND t.batch_name like ? ");	
		QueryUtils.like(whereSql, params, dataBatchCondition.getFileName(), " AND t.file_name like ? ");	
		QueryUtils.like(whereSql, params, dataBatchCondition.getDataTable(), " AND t.data_table like ? ");
		
		
		if(!"1".equals(SessionUtil.getCurrentUser(dataBatchCondition.getRequest()).getAdminFlag()) && dataBatchCondition.getIns() != null) {
//			whereSql.append("(");
			if(dataBatchCondition.getIns().size() > 0) {
				QueryUtils.in(whereSql, params, dataBatchCondition.getIns(), " AND( t.uuid ");
			} else {
				whereSql.append(" and (1=2 ");
			}
			whereSql.append(" or t.upload_user=? ");
			params.add(SessionUtil.getCurrentUser(dataBatchCondition.getRequest()).getUid());
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
		pageSql.append("SELECT t.*,p.loginname ").append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				dataBatchCondition.getRequest().getParameter("columns["+dataBatchCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				dataBatchCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

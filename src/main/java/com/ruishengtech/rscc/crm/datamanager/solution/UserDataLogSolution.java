package com.ruishengtech.rscc.crm.datamanager.solution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.UserDataLogCondition;

public class UserDataLogSolution implements ISolution{
	
	private UserDataLogCondition userDataLogCondition;
	
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		userDataLogCondition = (UserDataLogCondition) condition;
		whereSql.append("From new_data_department_user_log t left join user_user p on t.op_user = p.uuid  WHERE 1=1 ");
		
		if(StringUtils.isNotBlank(userDataLogCondition.getStartTime()) && StringUtils.isNotBlank(userDataLogCondition.getEndTime())){
			userDataLogCondition.setStartTime(userDataLogCondition.getStartTime()+" 00:00:00");
			userDataLogCondition.setEndTime(userDataLogCondition.getEndTime()+" 23:59:59");
		}else{
			userDataLogCondition.setStartTime(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date()));
			userDataLogCondition.setEndTime(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date()));
		}
		if(StringUtils.isNotBlank(userDataLogCondition.getBatch_uuid())){
			QueryUtils.like(whereSql, params, userDataLogCondition.getBatch_uuid(), " AND t.batch_uuid like ? ");
		}
		if(userDataLogCondition.getOp_users() != null && userDataLogCondition.getOp_users().size() > 0){
			QueryUtils.in(whereSql, params, userDataLogCondition.getOp_users(), " AND t.op_user ");
		}
		
		QueryUtils.date(whereSql, params, userDataLogCondition.getStartTime().toString(), " AND t.op_time > ? ");	
		QueryUtils.date(whereSql, params, userDataLogCondition.getEndTime().toString(), " AND t.op_time <= ? ");	
		QueryUtils.like(whereSql, params, userDataLogCondition.getPhone_number(), " AND t.phone_number like ? ");
		QueryUtils.like(whereSql, params, userDataLogCondition.getOp_type(), " AND t.op_type like ? ");
		
		
//		if(!"1".equals(SessionUtil.getCurrentUser(userDataLogCondition.getRequest()).getAdminFlag()) && userDataLogCondition.getIns() != null) {
//			if(userDataLogCondition.getIns().size() > 0) {
//				QueryUtils.in(whereSql, params, userDataLogCondition.getIns(), " AND( t.uuid ");
//			} else {
//				whereSql.append(" and (1=2 ");
//			}
//			whereSql.append(" or t.upload_user=? ");
//			params.add(SessionUtil.getCurrentUser(userDataLogCondition.getRequest()).getUid());
//			whereSql.append(")");
//		}
		
	}

	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		 countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append("SELECT t.*,p.user_describe ").append(whereSql);
	}

	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{
				userDataLogCondition.getRequest().getParameter("columns["+userDataLogCondition.getRequest().getParameter("order[0][column]")+"][data]"), 
				userDataLogCondition.getRequest().getParameter("order[0][dir]")});
		return list;
	}
}

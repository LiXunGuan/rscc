package com.ruishengtech.rscc.crm.ui.mw.solution;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.ISolution;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.User;

public class CallLogSolution implements ISolution {
	

	public void getWhere(Map<String, ColumnDesign> str, StringBuilder whereSql,
			HttpServletRequest request ,List<Object> params) {
		
		Map<String, String[]> map = request.getParameterMap();
		whereSql.append(" FROM sys_call_log ").append(" t WHERE 1=1 ");
		
//		QueryUtil.queryData(whereSql, params, (String) request.getAttribute("phone"), " AND call_phone = ? ");
//		QueryUtil.queryData(whereSql, params, (String) request.getAttribute("phone1"), " AND call_phone = ? ");
		//和号码关联
		QueryUtil.getOrData(whereSql,params, (String) request.getAttribute("phone"), (String) request.getAttribute("phone1"), "call_phone");
		
		User temp = SessionUtil.getCurrentUser(request);
		//如果点击的菜单是我的客服记录
		if(null != request.getAttribute("level") && request.getAttribute("level").equals("agent")){
				//只要显示自己的客服记录
				whereSql.append(" AND agent_name = ? ");
				params.add(temp.getLoginName());
		//否则点击的菜单是部门客服记录
		}else{
			//如果不是admin登录，显示自己管理的坐席的客服记录   
//			if(!"0".equals(temp.getUid())){
//				whereSql.append(QueryUtils.inString(" and agent_id", params, (Collection<String>)request.getAttribute("users")));
//			}
		}
		if (null != request.getAttribute("record")){
			if((boolean)request.getAttribute("record"))
				whereSql.append(" AND record_path is not null ");
		}
		
		//遍历所有参数 包含在内的，可能作为查询条件
		for (String s : str.keySet()) {
			//包含此条件并且值不为空 添加筛选条件
			if(map.keySet().contains(s) && StringUtils.isNotBlank(map.get(s)[0])){
				
				if(str.get(s).getColumnType().equals(ColumnDesign.DATETYPE)){
					
					if(map.keySet().contains(s) && StringUtils.isNotBlank(map.get(s)[0])){
						
						String[] date = map.get(str.get(s).getColumnNameDB())[0].split(" - ");
						
						/* 日期区段查询 */
						QueryUtils.date(whereSql, params, date[0], " AND "+ str.get(s).getColumnNameDB() +" >= ? ");
						QueryUtils.date(whereSql, params, date[1], " AND "+ str.get(s).getColumnNameDB() +" <= ? ");
					}
				}else if(str.get(s).getColumnType().equals(ColumnDesign.ENUMTYPE)){
					if(map.get(s)[0].equals("未呼通")){
						whereSql.append(" AND in_out_flag = '' ");
					}else{
						QueryUtil.queryData(whereSql, params, map.get(s)[0], " AND " + s + " = ? ");
					}
				}else{
					QueryUtil.like(whereSql, params, map.get(s)[0], " AND " + s + " like ? ");
				}
			}
		}
	}
	
	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append("SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			Map<String, ColumnDesign> str, HttpServletRequest request) {
		
		pageSql.append("SELECT * ").append(whereSql);
	}
}

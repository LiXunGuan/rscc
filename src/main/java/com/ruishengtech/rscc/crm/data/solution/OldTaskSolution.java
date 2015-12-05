package com.ruishengtech.rscc.crm.data.solution;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.ISolution;

public class OldTaskSolution implements ISolution {
	
	public void getWhere(Map<String, ColumnDesign> str, StringBuilder whereSql,
			HttpServletRequest request ,List<Object> params) {
		
		Map<String, String[]> map = request.getParameterMap();
		whereSql.append(" FROM data_task_").append(map.get("taskTable")[0]).append(" t WHERE 1=1 ");
		//遍历所有参数 包含在内的，可能作为查询条件
		for (String s : str.keySet()) {
			//包含此条件并且值不为空 添加筛选条件
			if(map.keySet().contains(s) && StringUtils.isNotBlank(map.get(s)[0])){
				QueryUtil.like(whereSql, params, map.get(s)[0], " AND " + s + " like ? ");
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

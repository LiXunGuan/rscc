package com.ruishengtech.rscc.crm.product.solution;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.ISolution;


public class ProductSolution implements ISolution{

	@Override
	public void getWhere(Map<String, ColumnDesign> str, StringBuilder whereSql,
			HttpServletRequest request ,List<Object> params) {
		
		Map<String, String[]> map = request.getParameterMap();
		
		whereSql.append("FROM  product WHERE 1=1 ");
		
		QueryUtil.like(whereSql, params, map.get("product_id")[0].toString(), " AND product_id like ? ");
		QueryUtil.like(whereSql, params, map.get("product_name")[0].toString(), " AND product_name like ? ");
	}
	
	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			Map<String, ColumnDesign> str, HttpServletRequest request) {
		pageSql.append(" SELECT * ").append(whereSql);
	}

}

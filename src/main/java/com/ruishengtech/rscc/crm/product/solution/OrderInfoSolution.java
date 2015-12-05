package com.ruishengtech.rscc.crm.product.solution;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.ISolution;

public class OrderInfoSolution implements ISolution{

	@SuppressWarnings("unchecked")
	@Override
	public void getWhere(Map<String, ColumnDesign> str, StringBuilder whereSql,
			HttpServletRequest request ,List<Object> params) {
		
		Map<String, String[]> map = request.getParameterMap();
		whereSql.append("FROM  order_info WHERE 1=1 ");
		
		//判断我的订单和订单管理
		if("agent".equals(request.getSession().getAttribute("level"))){
			QueryUtil.queryData(whereSql, params, (String)map.get("useruuid")[0], " AND order_user_uuid = ? ");
		}else if("pop".equals(request.getSession().getAttribute("level"))){
			if(request.getSession().getAttribute("cstmId") == null || StringUtils.isBlank(String.valueOf(request.getSession().getAttribute("cstmId")))){
				whereSql.append(" AND 1 != 1 ");
			}else{
				QueryUtil.queryData(whereSql, params, (String) request.getSession().getAttribute("cstmId"), " AND cstm_id = ? ");
			}
		}else{
			if(!"1".equals(map.get("adminflag")[0])){
				List<String> allusers = (List<String>) request.getAttribute("allusers");
				if(allusers != null){
					QueryUtils.in(whereSql, params, allusers, " AND order_user_uuid ");
				}else{
					whereSql.append(" AND 1 != 1 ");
				}
			}
		}
		
		QueryUtil.like(whereSql, params, map.get("order_id")[0].toString(), " AND order_id like ? ");
		QueryUtil.queryData(whereSql, params, map.get("order_status")[0].toString(), " AND order_status = ? ");
//		for (Object obj :params) {
//			System.out.println(obj);
//		}
	}
	
	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql).append(" ORDER BY order_create_time desc ");
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			Map<String, ColumnDesign> str, HttpServletRequest request) {
		pageSql.append(" SELECT * ").append(whereSql);
//		System.out.println(pageSql);
	}

}

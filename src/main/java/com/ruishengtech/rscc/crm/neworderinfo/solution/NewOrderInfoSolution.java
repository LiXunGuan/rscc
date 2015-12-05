package com.ruishengtech.rscc.crm.neworderinfo.solution;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.ISolution;

public class NewOrderInfoSolution implements ISolution{

	@SuppressWarnings("unchecked")
	@Override
	public void getWhere(Map<String, ColumnDesign> str, StringBuilder whereSql,
			HttpServletRequest request ,List<Object> params) {
		
		Map<String, String[]> map = request.getParameterMap();
		whereSql.append(" FROM new_order_info o "
				+ " LEFT JOIN "+SpringPropertiesUtil.getProperty("sys.record.database")+"cdr c on o.call_session_uuid = c.call_session_uuid and c.type = 'master' "
				+ " LEFT JOIN qualitycheck q ON c.call_session_uuid = q.uuid_obj "
				+ " WHERE 1=1  ");
		
		QueryUtil.datetime(whereSql, params, (String)map.get("starttime")[0], " and o.order_create_time >= ? ");
		QueryUtil.datetime(whereSql, params, (String)map.get("endtime")[0], " and o.order_create_time <= ? ");
		
		//判断我的订单和订单管理
		if("agent".equals(request.getSession().getAttribute("level"))){
			QueryUtil.queryData(whereSql, params, (String)map.get("useruuid")[0], " AND o.order_user_uuid = ? ");
		}else if("pop".equals(request.getSession().getAttribute("level"))){//cstmId其实是对应该客户的电话号码
			if(request.getSession().getAttribute("cstmId") == null || StringUtils.isBlank(String.valueOf(request.getSession().getAttribute("cstmId")))){
				whereSql.append(" AND 1 != 1 ");
			}else{
				
				//对第二个号码下单，也可以看得到
				String[] phoneStr = String.valueOf(request.getSession().getAttribute("cstmId")).split(",");
				if(str.size() ==2 ){
					if(StringUtils.isNotBlank(phoneStr[1])){
						whereSql.append("  AND ( receive_user_mobile = ? OR receive_user_mobile = ? ) ");
						params.add(phoneStr[0]);
						params.add(phoneStr[1]);
					}
				}else{
					whereSql.append("  AND receive_user_mobile = ?  ");
					params.add(phoneStr[0]);
				}
				
//				QueryUtil.queryData(whereSql, params, (String) request.getSession().getAttribute("cstmId"), " AND receive_user_mobile = ? ");
			}
		}else{
			if(!"1".equals(map.get("adminflag")[0])){
				List<String> allusers = (List<String>) request.getAttribute("allusers");
				if(allusers != null){
					QueryUtils.in(whereSql, params, allusers, " AND o.order_user_uuid ");
				}else{
					whereSql.append(" AND 1 != 1 ");
				}
			}
		}
		
		QueryUtil.like(whereSql, params, map.get("order_id")[0].toString(), " AND o.order_id like ? ");
		QueryUtil.queryData(whereSql, params, map.get("order_status")[0].toString(), " AND o.order_status = ? ");
		
		for (String s : str.keySet()) {
			
			//如果是整数和浮点数类型
			if(str.get(s).getColumnType().equals(ColumnDesign.INTTYPE) || str.get(s).getColumnType().equals(ColumnDesign.FLOATTYPE)){
				
				if(map.keySet().contains(s) && StringUtils.isNotBlank(map.get(s)[0].replace(",", ""))){
					
					QueryUtils.compare(whereSql, params, map.get(str.get(s).getColumnNameDB())[0], " AND "+ str.get(s).getColumnNameDB() +" ");
				}
				
			}else if(str.get(s).getColumnType().equals(ColumnDesign.DATETYPE)){
				
				if(map.keySet().contains(s) && StringUtils.isNotBlank(map.get(s)[0])){
					
					String[] date = map.get(str.get(s).getColumnNameDB())[0].split(" - ");
					
					QueryUtils.date(whereSql, params, date[0], " AND "+ str.get(s).getColumnNameDB() +" >= ? ");
					QueryUtils.date(whereSql, params, date[1], " AND "+ str.get(s).getColumnNameDB() +" <= ? ");
					
				}
			}else if(str.get(s).getColumnType().equals(ColumnDesign.ENUMTYPE)){
				
				if(null != map.get(s) ){
					
					QueryUtil.queryData(whereSql, params, map.get(s)[0], " AND " + s + " = ? ");
				
				}
				
			}else if(map.keySet().contains(s) && StringUtils.isNotBlank(map.get(s)[0])){
				
				QueryUtil.like(whereSql, params, map.get(s)[0], " AND " + s + " like ? ");
			
			}
		}
	}
	
	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql).append(" ORDER BY o.order_create_time desc ");
	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			Map<String, ColumnDesign> str, HttpServletRequest request) {
		pageSql.append(" SELECT o.*," + "q.score as score," + "c.bridgesec as bridgesec ").append(whereSql);
	}

}

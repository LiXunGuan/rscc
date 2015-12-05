package com.ruishengtech.rscc.crm.record.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.record.condition.RecordCondition;

/**
 * @author Frank
 *
 */
public class RecordSolution implements ISolution{

	private RecordCondition recordCondition;
	@Override
	public void getWhere(ICondition condition, StringBuilder whereSql,
			List<Object> params) {
		
		recordCondition = (RecordCondition) condition;
		
		//whereSql.append(" FROM cstm_record c LEFT JOIN qualitycheck q ON c.uuid = q.uuid_obj  WHERE 1 = 1 ");
		
		whereSql.append(" FROM ( select "
				+ "c.call_session_uuid,"
				+ "c.start_stamp,"
				+ "c.caller_id_number,"
				+ "c.caller_agent_id,"
				+ "c.caller_agent_info,"
				+ "c.dest_agent_interface_exten,"
				+ "c.dest_agent_id,"
				+ "c.dest_agent_info,"
				+ "c.call_direction,"
				+ "c.duration,"
				+ "c.bridgesec,"
				+ "c.billsec,"
				+ "c.access_number,"
				+ "c.dest_number,c.type,"
				+ "q.score as score "
				+ " from  "+ recordCondition.getDatabase() +"cdr c LEFT JOIN qualitycheck q ON c.call_session_uuid = q.uuid_obj ");
		
		whereSql.append(" WHERE 1 = 1 ");
		
		//删选条件
//		if("0".equals(recordCondition.getAdminflag()) || null == recordCondition.getAdminflag()){
//			QueryUtil.queryData(whereSql, params, recordCondition.getUsername(), " and (c.caller_agent_id = ? ");
//			QueryUtil.queryData(whereSql, params, recordCondition.getUsername(), " or c.dest_agent_id = ? ) ");
//		}
		
		if("agent".equals(recordCondition.getLevel())){
			QueryUtil.queryData(whereSql, params, recordCondition.getUsername(), " and (c.caller_agent_id = ? ");
			QueryUtil.queryData(whereSql, params, recordCondition.getUsername(), " or c.dest_agent_id = ? ) ");
		}else{
			if(!"1".equals(recordCondition.getAdminflag())){
				Collection<String> queuesList = recordCondition.getQueuesList();
				if(queuesList.size() != 0) {
					QueryUtils.in(whereSql, params, queuesList, " and (c.caller_agent_id ");
					QueryUtils.in(whereSql, params, queuesList, " or c.dest_agent_id ");
					whereSql.append(")");
				}
			}
		}
		
		QueryUtils.number(whereSql, params, recordCondition.getScore1(), recordCondition.getScore2(), " and q.score ");
		QueryUtil.datetime(whereSql, params, recordCondition.getStime(), " and c.start_stamp >= ? ");
		QueryUtil.datetime(whereSql, params, recordCondition.getEtime(), " and c.start_stamp <= ? ");
//		QueryUtil.datetime(whereSql, params, recordCondition.getStime(), " and c.end_stamp >= ? ");
//      QueryUtil.datetime(whereSql, params, recordCondition.getEtime(), " and c.end_stamp <= ? ");
        QueryUtil.like(whereSql, params, recordCondition.getAccess_number(), " and c.access_number like ? ");
        QueryUtil.like(whereSql, params, recordCondition.getCaller_id_number(), " and c.caller_id_number like ? ");
        QueryUtil.like(whereSql, params, recordCondition.getDest_agent_interface_exten(), " and c.dest_number like ? ");
        
        QueryUtil.like(whereSql, params, recordCondition.getAgentinfo(), " and ( c.caller_agent_id like ? ");
        QueryUtil.like(whereSql, params, recordCondition.getAgentinfo(), " or c.dest_agent_id like ? ) ");
        
        //通话时长/秒
//      QueryUtil.queryData(whereSql, params, recordCondition.getSduration(), " and c.duration >= ? ");
//      QueryUtil.queryData(whereSql, params, recordCondition.getEduration(), " and c.duration <= ? ");

        //通话时长/秒
        QueryUtil.queryData(whereSql, params, recordCondition.getSduration(), " and c.billsec >= ? ");
        QueryUtil.queryData(whereSql, params, recordCondition.getEduration(), " and c.billsec <= ? ");
        
        //接通时长
//      QueryUtil.queryData(whereSql, params, recordCondition.getSbillsec(), " and c.billsec >= ? ");
//      QueryUtil.queryData(whereSql, params, recordCondition.getEbillsec(), " and c.billsec <= ? ");

        //接通时长
        QueryUtil.queryData(whereSql, params, recordCondition.getSbillsec(), " and c.bridgesec >= ? ");
        QueryUtil.queryData(whereSql, params, recordCondition.getEbillsec(), " and c.bridgesec <= ? ");
        
        QueryUtil.queryData(whereSql, params, recordCondition.getCalldirection(), " and c.call_direction = ? ");
        if(StringUtils.isNotBlank(recordCondition.getBridgesec())){
        	if("y".equals(recordCondition.getBridgesec())){
        		whereSql.append(" and c.bridgesec > 0 ");
        	}else if("n".equals(recordCondition.getBridgesec())){
        		whereSql.append(" and c.bridgesec = 0 ");
        	}
        }
        
        
        whereSql.append(" ) as a where a.type = 'master' ");
	}


	@Override
	public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
		countSql.append(" SELECT COUNT(*) ").append(whereSql);

	}

	@Override
	public void getPageSql(StringBuilder pageSql, StringBuilder whereSql,
			ICondition condition, List<Object> params) {
		whereSql.append(" ORDER BY ");
		pageSql.append(" SELECT * ").append(whereSql);
		
//		System.out.println(pageSql);
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<String[]> getOrderBy(ICondition condition) {
		recordCondition = (RecordCondition) condition;
		final String[] str = new String[]{recordCondition.getRequest().getParameter("columns["+recordCondition.getRequest().getParameter("order[0][column]")+"][data]") , recordCondition.getRequest().getParameter("order[0][dir]")};

		return new ArrayList<String[]>(){{add(str);}};
	}

}

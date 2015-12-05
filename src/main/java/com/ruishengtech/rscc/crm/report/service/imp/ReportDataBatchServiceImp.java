package com.ruishengtech.rscc.crm.report.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.model.UserDataLog;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.UserDataLogServiceImp;
import com.ruishengtech.rscc.crm.report.condition.ReportDataBatchCondition;
import com.ruishengtech.rscc.crm.report.model.ReportDataBatch;
import com.ruishengtech.rscc.crm.report.solution.ReportDataBatchSolution;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class ReportDataBatchServiceImp extends BaseService{
	
	@Autowired
	private UserDataLogServiceImp userLogService;
	
	@Autowired
	private DataBatchService dbService;
	
	@Autowired
	private UserService userService;
	
	public PageResult<ReportDataBatch> querypage(ReportDataBatchCondition batchCondition){
		List<String> der = new ArrayList<>();
		if(StringUtils.isNotBlank(batchCondition.getAgent())){
			List<User> list = userService.getUserByDescribe(batchCondition.getAgent());
			for(User u : list){
				der.add(u.getUid());
			}
		}
		batchCondition.setAgents(der);
		
		return super.queryPage(new ReportDataBatchSolution(), batchCondition, ReportDataBatch.class);
	}
	
	public Map<String,Object> getAllReportDataBatch(ICondition condition){
		
		ReportDataBatchSolution solution = new ReportDataBatchSolution();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<>();
		solution.getStatisticsSql(condition, sb, params);
		Map<String,Object> map = jdbcTemplate.queryForMap(sb.toString(),params.toArray());
		return map;
	}
	
	public void deleteAgoData(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = sdf.format(cal.getTime());
		jdbcTemplate.update("DELETE FROM report_databatch WHERE op_time >= ? AND op_time < ? ",yesterday,sdf.format(new Date()));
		
	}
	public void insertReportData(final String stime,final String etime,final String type){
		List<ReportDataBatch> rdb = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT\n" +
					"	op_user AS agent,\n" +
					"	SUM(op_type = 'customer') AS cstmaddcount,\n" +
					"	SUM(op_type = 'global_share') AS globalsharecount,\n" +
					"	SUM(op_type = 'abandon') AS abandoncount,\n" +
					"	SUM(op_type = 'intent') AS intentaddcount,\n" +
					"	SUM(op_type = 'noanswer') AS noanswercount,\n" +
					"	SUM(op_type = 'blacklist') AS blacklistcount\n" +
					"FROM\n" +
					"	new_data_department_user_log\n" +
					"WHERE\n" +
					"	op_time >= ?\n" +
					"AND op_time < ?\n" +
					"GROUP BY\n" +
					"	op_user" );
				params.add(stime);
				params.add(etime);
			}
		}, ReportDataBatch.class);
		Map<String,ReportDataBatch> map = new HashMap<String, ReportDataBatch>();
		for(ReportDataBatch reportDataBatch : rdb){
			map.put(reportDataBatch.getAgent(), reportDataBatch);
		}
		
		/* ********************意向减少量****************/
		
		List<ReportDataBatch> intentdel = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT\n" +
					"		op_user AS agent,\n" +
					"		old_stat,\n" +
					"		COUNT(*) AS intentdelcount\n" +
					"	FROM\n" +
					"		new_data_department_user_log\n" +
					"WHERE\n" +
					"	op_time >= ?\n" +
					"AND op_time < ?\n" +
					"AND old_stat='intent' \n" +
					"GROUP BY op_user,old_stat");
				params.add(stime);
				params.add(etime);
			}
		}, ReportDataBatch.class);
		
		Map<String,ReportDataBatch> intentdelmap = new HashMap<String, ReportDataBatch>();
		for(ReportDataBatch reportDataBatch : intentdel){
			intentdelmap.put(reportDataBatch.getAgent(), reportDataBatch);
		}
		
		/* ********************客户减少量****************/
		
		List<ReportDataBatch> cstmdel = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT\n" +
						"		op_user AS agent,\n" +
						"		old_stat,\n" +
						"		COUNT(*) AS cstmdelcount\n" +
						"	FROM\n" +
						"		new_data_department_user_log\n" +
						"WHERE\n" +
						"	op_time >= ?\n" +
						"AND op_time < ?\n" +
						"-- AND old_stat='intent' \n" +
						"AND old_stat='customer'\n" +
						"GROUP BY op_user,old_stat");
				
				params.add(stime);
				params.add(etime);
				
			}
		}, ReportDataBatch.class);
		
		Map<String,ReportDataBatch> cstmdelmap = new HashMap<String, ReportDataBatch>();
		for(ReportDataBatch reportDataBatch : cstmdel){
			cstmdelmap.put(reportDataBatch.getAgent(), reportDataBatch);
		}
		
		/* ********************通话总数量****************/
		
		List<ReportDataBatch> callcount = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT\n" +
						"	op_user AS agent,\n" +
						"	COUNT(*) AS callcount\n" +
						"FROM\n" +
						"	new_data_department_user_log\n" +
						"WHERE\n" +
						"	op_time >= ?\n" +
						"AND op_time < ?\n" +
						"GROUP BY\n" +
						"	op_user");
				
				params.add(stime);
				params.add(etime);
				
			}
		}, ReportDataBatch.class);
		
		Map<String,ReportDataBatch> callcountmap = new HashMap<String, ReportDataBatch>();
		for(ReportDataBatch reportDataBatch : callcount){
			callcountmap.put(reportDataBatch.getAgent(), reportDataBatch);
		}
		
		
		for(String agentid : map.keySet()){
			ReportDataBatch reportDataBatch = new ReportDataBatch();
			try {
				ReportDataBatch  r = map.get(agentid);
				if(r != null){
					try {
						BeanUtils.copyProperties(reportDataBatch, r);
						reportDataBatch.setCstmaddcount(r.getCstmaddcount());
						reportDataBatch.setIntentaddcount(r.getIntentaddcount());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				ReportDataBatch reportforcstm = cstmdelmap.get(agentid);
				if(reportforcstm != null){
					reportDataBatch.setCstmdelcount(0-reportforcstm.getCstmdelcount());
				}
				ReportDataBatch reportforintent = intentdelmap.get(agentid);
				if(reportforintent != null){
					reportDataBatch.setIntentdelcount(0-reportforintent.getIntentdelcount());
				}
				ReportDataBatch reportforcall = callcountmap.get(agentid);
				if(reportforcall != null){
					reportDataBatch.setCallcount(reportforcall.getCallcount());
				}
				reportDataBatch.setCstmcount(reportDataBatch.getCstmaddcount()+reportDataBatch.getCstmdelcount());
				reportDataBatch.setIntentcount(reportDataBatch.getIntentaddcount()+reportDataBatch.getIntentdelcount());
				if(type.equals("day")){
					reportDataBatch.setOptime(stime.substring(0,10));
				}else if(type.equals("month")){
					reportDataBatch.setOptime(stime.substring(0,7));
				}
				save(reportDataBatch);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
//	public void insertReportData(){
//		
//		List<UserDataLog> userlogs = userLogService.getYesterdayData();
//		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if(userlogs.size()!=0){
//			for(UserDataLog userlog : userlogs){
//				ReportDataBatch rdb = new ReportDataBatch();
//				rdb.setOptime(sdf.format(new Date()));
//				rdb.setAgent(userlog.getOp_user());
//				
//				rdb.setCstmaddcount(userLogService.getPhoneTypeCount("customer"));
//				rdb.setCstmdelcount(0-userLogService.getDelCount("customer"));
//				rdb.setCstmcount(rdb.getCstmaddcount()+rdb.getCstmdelcount());
//				
//				rdb.setIntentaddcount(userLogService.getPhoneTypeCount("intent"));
//				rdb.setIntentdelcount(0-userLogService.getDelCount("intent"));
//				rdb.setIntentcount(rdb.getIntentaddcount()+rdb.getIntentdelcount());
//				
//				rdb.setGlobalsharecount(userLogService.getPhoneTypeCount("global_share"));
//				rdb.setNoanswercount(userLogService.getPhoneTypeCount("noanswer"));
//				rdb.setBlacklistcount(userLogService.getPhoneTypeCount("blacklist"));
//				rdb.setAbandoncount(userLogService.getPhoneTypeCount("abandon"));
//				rdb.setCallcount(rdb.getCstmcount()+rdb.getIntentcount()+rdb.getGlobalsharecount()+rdb.getNoanswercount()+rdb.getBlacklistcount()+rdb.getAbandoncount());
//				
//				if(userlogs.size()%100==0){
//					super.save(rdb);
//				}else if(userlogs.size()-100<0){
//					super.save(rdb);
//				}
//			}
//		}
//	}
	
}

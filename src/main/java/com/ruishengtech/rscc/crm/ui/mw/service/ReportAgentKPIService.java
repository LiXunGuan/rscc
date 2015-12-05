package com.ruishengtech.rscc.crm.ui.mw.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.ui.mw.condition.ReportCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.ReportAgentkpi;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class ReportAgentKPIService extends BaseConfigService<ReportAgentkpi> {

    @Autowired
    private FsServerManager fsServerManager;
    
	@Autowired
	private SYSConfigService sysConfigService;
	
    @Override
    protected Class getClazz() {
        return ReportAgentkpi.class;
    }

    @Override
    public void deploy() {
    }

    private String database;
	
	public ReportAgentKPIService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
	/**
	 * 创建Excel
	 */
	public void getExcelCreated(final HttpServletRequest request,final HttpServletResponse response, ReportCondition reportCondition) throws Exception {
		
		response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( "坐席-KPI报表".getBytes("utf-8"), "ISO8859-1" )+".xls");
       
		final StringBuilder whereSql=new StringBuilder();
		List<Object> params = new ArrayList<>();
		String startTime=reportCondition.getStime();	//获取开始时间
		String endTime=reportCondition.getEtime();	//获取结束时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String now=sdf.format(new Date());	//获取当前系统时间
		
		if (startTime==null||startTime=="") {	//如果未获取到开始时间或结束时间  那么赋值当前时间
			startTime=now;
		}
		if (endTime==null||endTime=="") {
			endTime=now;
		}
		
		//根据报表类型拼出完整时间
		if (reportCondition.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
			 reportCondition.setStime(startTime.substring(0,4)+"-01-01 00:00:00");
			 reportCondition.setEtime(endTime.substring(0,4)+"-12-30 23:59:59");
 		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
 			 reportCondition.setStime(startTime.substring(0,7)+"-01 00:00:00");
 			 reportCondition.setEtime(endTime.substring(0,7)+"-30 23:59:59");
 		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
 			
 		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
 			 reportCondition.setStime(startTime+" 00:00:00");
 			 reportCondition.setEtime(endTime+" 23:59:59");
 		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
 			 reportCondition.setStime(startTime+" 00:00:00");
 			 reportCondition.setEtime(endTime+" 23:59:59");
 		}
		
        whereSql.append("from (select "
        		+ " ak.`year`, "
        		+ " ak.`month`, "
        		+ "	ak.`week`, "
        		+ "	ak.`day`, "
        		+ "	ak.`timestamp`, "
        		+ "	ak.`name`, "
        		+ "	ak.`info`,"
        		+ "	ak.online_time, "
        		+ "	ak.score_count, "
        		+ "	ak.score_sum, "
        		+ "	ak.pause_reason_1_count, "
        		+ "	ak.pause_reason_2_count, "
        		+ "	ak.pause_reason_3_count, "
        		+ "	ak.pause_reason_4_count, "
        		+ "	ak.pause_reason_5_count, "
        		+ "	ak.pause_reason_6_count, "
        		+ "	ak.pause_reason_7_count, "
        		+ "	ak.pause_reason_8_count, "
        		+ "	ak.pause_reason_9_count, "
        		+ "	ak.pause_reason_10_count "
        		+ " from "+database+"report_agentkpi ak where 1=1 ");
        QueryUtil.like(whereSql, params, reportCondition.getName(), " and ak.name like ? ");
        QueryUtil.datetime(whereSql, params, reportCondition.getStime(), " and ak.timestamp >= ? ");
		QueryUtil.datetime(whereSql, params, reportCondition.getEtime(), " and ak.timestamp <= ? ");
		if(!"0".equals(reportCondition.getNameUid())){
			if (reportCondition.getAgentsList() != null && reportCondition.getAgentsList().size() > 0) {
				QueryUtils.in(whereSql, params, reportCondition.getAgentsList(), " and ak.name ");
			}else{
				whereSql.append(" and 1 != 1 ");
			}
		}
        whereSql.append(" order by id ) as a ");
		
        final StringBuilder finalSql = new StringBuilder();
		 if (reportCondition.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
			 finalSql.append("select year,name,info,sum(online_time) as online_time,sum(score_count) as score_count,sum(score_sum) as score_sum,"
 					+ "sum(pause_reason_1_count) as pause_reason_1_count,sum(pause_reason_2_count) as pause_reason_2_count,"
 					+ "sum(pause_reason_3_count) as pause_reason_3_count,sum(pause_reason_4_count) as pause_reason_4_count,"
 					+ "sum(pause_reason_5_count) as pause_reason_5_count,sum(pause_reason_6_count) as pause_reason_6_count,"
 					+ "sum(pause_reason_7_count) as pause_reason_7_count,sum(pause_reason_8_count) as pause_reason_8_count,"
 					+ "sum(pause_reason_9_count) as pause_reason_9_count,sum(pause_reason_10_count) as pause_reason_10_count ").append(whereSql).append(" group by year,name,info ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
			finalSql.append("select year,month,name,info,sum(online_time) as online_time,sum(score_count) as score_count,sum(score_sum) as score_sum,"
					+ "sum(pause_reason_1_count) as pause_reason_1_count,sum(pause_reason_2_count) as pause_reason_2_count,"
					+ "sum(pause_reason_3_count) as pause_reason_3_count,sum(pause_reason_4_count) as pause_reason_4_count,"
					+ "sum(pause_reason_5_count) as pause_reason_5_count,sum(pause_reason_6_count) as pause_reason_6_count,"
					+ "sum(pause_reason_7_count) as pause_reason_7_count,sum(pause_reason_8_count) as pause_reason_8_count,"
					+ "sum(pause_reason_9_count) as pause_reason_9_count,sum(pause_reason_10_count) as pause_reason_10_count ").append(whereSql).append(" group by year,month,name,info");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
			finalSql.append("select year,month,day,name,info,sum(online_time) as online_time,sum(score_count) as score_count,sum(score_sum) as score_sum,"
					+ "sum(pause_reason_1_count) as pause_reason_1_count,sum(pause_reason_2_count) as pause_reason_2_count,"
					+ "sum(pause_reason_3_count) as pause_reason_3_count,sum(pause_reason_4_count) as pause_reason_4_count,"
					+ "sum(pause_reason_5_count) as pause_reason_5_count,sum(pause_reason_6_count) as pause_reason_6_count,"
					+ "sum(pause_reason_7_count) as pause_reason_7_count,sum(pause_reason_8_count) as pause_reason_8_count,"
					+ "sum(pause_reason_9_count) as pause_reason_9_count,sum(pause_reason_10_count) as pause_reason_10_count ").append(whereSql).append(" group by year,month,day,name,info ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
			finalSql.append("select * ").append(whereSql);
		}
		 
		jdbcTemplate.query(finalSql.toString(), params.toArray(),new ResultSetExtractor<T>() {
			
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<String> titles = new ArrayList<>();
				titles.add("时间");
				titles.add("坐席号");
				titles.add("在线时长（时:分:秒）");
				titles.add("总评分");
				titles.add("平均评分");
				
				SysConfig config=sysConfigService.getSysConfig("agentbusy");
				if(config!=null){
					JSONObject jsob = new JSONObject(config.getVal());
					if(jsob.get("state_1").toString().equals("1"))
					titles.add("置忙时长1（"+jsob.getString("name_1")+"！）");
					if(jsob.get("state_2").toString().equals("1"))
						titles.add("置忙时长2（"+jsob.getString("name_2")+"！）");
					if(jsob.get("state_3").toString().equals("1"))
						titles.add("置忙时长3（"+jsob.getString("name_3")+"！）");
					if(jsob.get("state_4").toString().equals("1"))
						titles.add("置忙时长4（"+jsob.getString("name_4")+"！）");
					if(jsob.get("state_5").toString().equals("1"))
						titles.add("置忙时长5（"+jsob.getString("name_5")+"！）");
					if(jsob.get("state_6").toString().equals("1"))
						titles.add("置忙时长6（"+jsob.getString("name_6")+"！）");
					if(jsob.get("state_7").toString().equals("1"))
						titles.add("置忙时长7（"+jsob.getString("name_7")+"！）");
					if(jsob.get("state_8").toString().equals("1"))
						titles.add("置忙时长8（"+jsob.getString("name_8")+"！）");
					if(jsob.get("state_9").toString().equals("1"))
						titles.add("置忙时长9（"+jsob.getString("name_9")+"！）");
					if(jsob.get("state_10").toString().equals("1"))
						titles.add("置忙时长10（"+jsob.getString("name_10")+"！）");
				}
				
				try {
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet sheet = workbook.createSheet();
					sheet.setDefaultColumnWidth(20);
					HSSFRow row = sheet.createRow(0);
					HSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

					//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (int i = 0; i < titles.size(); i++) {
						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						HSSFCell cell = row.createCell(i);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(titles.get(i));
					}
					int rowNum = 1;
					while (rs.next()) {

						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						createExcel(retRecord(rs), sheet, rowNum,titles.size());
						rowNum++;
					}
					workbook.write(response.getOutputStream());
					response.getOutputStream().flush();
					response.flushBuffer();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						response.getOutputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return null;
			}

		});
	}
	
	/**
	 * 给Excel列赋值
	 */
	public void createExcel(ReportAgentkpi agent, HSSFSheet sheet, int maxRow,int titleNumber) {

		/* 创建的行数 */
		HSSFRow ro = sheet.createRow(maxRow);
		for (int i = 0; i < titleNumber; i++) {

			HSSFCell cell = ro.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			
			String scoreavg = StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(agent.getScore_sum())/Double.valueOf(agent.getScore_count()))));
			
			switch (i) {
				case 0:
					cell.setCellValue(agent.getExportdate());
					break;
				case 1:
					cell.setCellValue(agent.getName());
					break;
				case 2:
					cell.setCellValue(DateUtils.getSToTime(agent.getOnline_time()));
					break;
				case 3:
					cell.setCellValue(agent.getScore_sum());
					break;
				case 4:
					cell.setCellValue(Integer.parseInt(scoreavg));
					break;
				default:
					break;
				}
			SysConfig config=sysConfigService.getSysConfig("agentbusy");
			if(config!=null){
				JSONObject jsob = new JSONObject(config.getVal());
				if(jsob.get("state_1").toString().equals("1") && i==4){
					cell.setCellValue(agent.getPause_reason_1_count());
					break;
				}
				if(jsob.get("state_2").toString().equals("1") && i==5){
					cell.setCellValue(agent.getPause_reason_2_count());
					break;
				}
				if(jsob.get("state_3").toString().equals("1") && i==6){
					cell.setCellValue(agent.getPause_reason_3_count());
					break;
				}
				if(jsob.get("state_4").toString().equals("1") && i==7){
					cell.setCellValue(agent.getPause_reason_4_count());
					break;
				}
				if(jsob.get("state_5").toString().equals("1") && i==8){
					cell.setCellValue(agent.getPause_reason_5_count());
					break;
				}
				if(jsob.get("state_6").toString().equals("1") && i==9){
					cell.setCellValue(agent.getPause_reason_6_count());
					break;
				}
				if(jsob.get("state_7").toString().equals("1") && i==10){
					cell.setCellValue(agent.getPause_reason_7_count());
					break;
				}
				if(jsob.get("state_8").toString().equals("1") && i==11){
					cell.setCellValue(agent.getPause_reason_8_count());
					break;
				}
				if(jsob.get("state_9").toString().equals("1") && i==12){
					cell.setCellValue(agent.getPause_reason_9_count());
					break;
				}
				if(jsob.get("state_10").toString().equals("1") && i==13){
					cell.setCellValue(agent.getPause_reason_10_count());
					break;
				}
			}
				
				
		}

	}
	
	/**
	 * 将查询到的数据封装到对象
	 * 
	 * @param rs
	 * @param sdf
	 * @return
	 * @throws Exception
	 */
	private ReportAgentkpi retRecord(ResultSet rs)
			throws Exception {

		// 创建对象
		ReportAgentkpi agent = new ReportAgentkpi();
		
		List<String> columns = new ArrayList<>();
		columns.add(rs.getMetaData().getColumnName(1));
		columns.add(rs.getMetaData().getColumnName(2));
		columns.add(rs.getMetaData().getColumnName(3));
		columns.add(rs.getMetaData().getColumnName(4));
		columns.add(rs.getMetaData().getColumnName(5));
		columns.add(rs.getMetaData().getColumnName(6));
		columns.add(rs.getMetaData().getColumnName(7));
		
		//日报
		if (columns.contains("year") && columns.contains("month") && columns.contains("day") 
				&& StringUtils.isNotBlank(rs.getString("year")) && StringUtils.isNotBlank(rs.getString("month")) && StringUtils.isNotBlank(rs.getString("day"))) {
			
			agent.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")) + "-" + (rs.getString("day").length()==1?"0"+rs.getString("day"):rs.getString("day")));
		//月报
		} else if (columns.contains("year") && columns.contains("month") 
				&& StringUtils.isNotBlank(rs.getString("year")) && StringUtils.isNotBlank(rs.getString("month"))) {
			
			agent.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")));
		//年报
		} else if (columns.contains("year") && StringUtils.isNotBlank(rs.getString("year"))) {
			
			agent.setExportdate(rs.getString("year"));
		}
		
		agent.setName(rs.getString("name"));
		agent.setOnline_time(Integer.parseInt(rs.getString("online_time")));
		agent.setScore_sum(Integer.parseInt(rs.getString("score_sum")));
		agent.setPause_reason_1_count(Integer.parseInt(rs.getString("pause_reason_1_count")));
		agent.setPause_reason_2_count(Integer.parseInt(rs.getString("pause_reason_2_count")));
		agent.setPause_reason_3_count(Integer.parseInt(rs.getString("pause_reason_3_count")));
		agent.setPause_reason_4_count(Integer.parseInt(rs.getString("pause_reason_4_count")));
		agent.setPause_reason_5_count(Integer.parseInt(rs.getString("pause_reason_5_count")));
		agent.setPause_reason_6_count(Integer.parseInt(rs.getString("pause_reason_6_count")));
		agent.setPause_reason_7_count(Integer.parseInt(rs.getString("pause_reason_7_count")));
		agent.setPause_reason_8_count(Integer.parseInt(rs.getString("pause_reason_8_count")));
		agent.setPause_reason_9_count(Integer.parseInt(rs.getString("pause_reason_9_count")));
		agent.setPause_reason_10_count(Integer.parseInt(rs.getString("pause_reason_10_count")));

		return agent;
	}
	

    @Override
    protected ISolution getSolution() {
    	
        return new ISolution() {
        	ReportCondition cond=null;
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
             	cond = (ReportCondition) condition;
                whereSql.append(" from (select "
                		+ " ak.`year`, "
                		+ " ak.`month`, "
                		+ "	ak.`week`, "
                		+ "	ak.`day`, "
                		+ "	ak.`timestamp`, "
                		+ "	ak.`name`, "
                		+ "	ak.`info`,"
                		+ "	ak.online_time, "
                		+ "	ak.score_count, "
                		+ "	ak.score_sum, "
                		+ "	ak.pause_reason_1_count, "
                		+ "	ak.pause_reason_2_count, "
                		+ "	ak.pause_reason_3_count, "
                		+ "	ak.pause_reason_4_count, "
                		+ "	ak.pause_reason_5_count, "
                		+ "	ak.pause_reason_6_count, "
                		+ "	ak.pause_reason_7_count, "
                		+ "	ak.pause_reason_8_count, "
                		+ "	ak.pause_reason_9_count, "
                		+ "	ak.pause_reason_10_count "
                		+ " from "+database+"report_agentkpi ak where 1=1 ");
                QueryUtil.like(whereSql, params, cond.getName(), " and ak.name like ? ");
                QueryUtil.datetime(whereSql, params, cond.getStime(), " and ak.timestamp >= ? ");
    			QueryUtil.datetime(whereSql, params, cond.getEtime(), " and ak.timestamp <= ? ");
    			if(!"0".equals(cond.getNameUid())){
    				if (cond.getAgentsList() != null && cond.getAgentsList().size() > 0) {
    					QueryUtils.in(whereSql, params, cond.getAgentsList(), " and ak.name ");
    				}else{
    					whereSql.append(" and 1 != 1 ");
    				}
    			}
                whereSql.append(" order by id ) as a ");

       		 if (cond.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
       			 	whereSql.append(" group by year,name,info ");
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
        			whereSql.append(" group by year,month,name,info ");
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
        			whereSql.append(" group by year,month,day,name,info ");
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
        		}
  

            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
            	if (cond.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	
            		countSql.append(" select count(*)  ").append(whereSql);
            	}else{
            		countSql.append(" select count(*) from (select count(*) ").append(whereSql).append(" ) b");
            	}
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
            	if (cond.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
            		pageSql.append("select year,name,info,sum(online_time) as online_time,sum(score_count) as score_count,sum(score_sum) as score_sum,"
        					+ "sum(pause_reason_1_count) as pause_reason_1_count,sum(pause_reason_2_count) as pause_reason_2_count,"
        					+ "sum(pause_reason_3_count) as pause_reason_3_count,sum(pause_reason_4_count) as pause_reason_4_count,"
        					+ "sum(pause_reason_5_count) as pause_reason_5_count,sum(pause_reason_6_count) as pause_reason_6_count,"
        					+ "sum(pause_reason_7_count) as pause_reason_7_count,sum(pause_reason_8_count) as pause_reason_8_count,"
        					+ "sum(pause_reason_9_count) as pause_reason_9_count,sum(pause_reason_10_count) as pause_reason_10_count ").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
        			pageSql.append("select year,month,name,info,sum(online_time) as online_time,sum(score_count) as score_count,sum(score_sum) as score_sum,"
        					+ "sum(pause_reason_1_count) as pause_reason_1_count,sum(pause_reason_2_count) as pause_reason_2_count,"
        					+ "sum(pause_reason_3_count) as pause_reason_3_count,sum(pause_reason_4_count) as pause_reason_4_count,"
        					+ "sum(pause_reason_5_count) as pause_reason_5_count,sum(pause_reason_6_count) as pause_reason_6_count,"
        					+ "sum(pause_reason_7_count) as pause_reason_7_count,sum(pause_reason_8_count) as pause_reason_8_count,"
        					+ "sum(pause_reason_9_count) as pause_reason_9_count,sum(pause_reason_10_count) as pause_reason_10_count ").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
        			pageSql.append("select year,month,day,name,info,sum(online_time) as online_time,sum(score_count) as score_count,sum(score_sum) as score_sum,"
        					+ "sum(pause_reason_1_count) as pause_reason_1_count,sum(pause_reason_2_count) as pause_reason_2_count,"
        					+ "sum(pause_reason_3_count) as pause_reason_3_count,sum(pause_reason_4_count) as pause_reason_4_count,"
        					+ "sum(pause_reason_5_count) as pause_reason_5_count,sum(pause_reason_6_count) as pause_reason_6_count,"
        					+ "sum(pause_reason_7_count) as pause_reason_7_count,sum(pause_reason_8_count) as pause_reason_8_count,"
        					+ "sum(pause_reason_9_count) as pause_reason_9_count,sum(pause_reason_10_count) as pause_reason_10_count ").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
        			pageSql.append("select * ").append(whereSql);
        		}
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}
        };
    }

}

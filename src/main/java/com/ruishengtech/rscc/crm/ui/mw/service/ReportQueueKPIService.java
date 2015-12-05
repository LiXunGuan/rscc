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
import com.ruishengtech.framework.core.util.RandomUtil;
import com.ruishengtech.rscc.crm.ui.mw.condition.ReportCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.ReportQueue;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class ReportQueueKPIService extends BaseConfigService<ReportQueue> {

    @Autowired
    private FsServerManager fsServerManager;


    @Override
    protected Class getClazz() {
        return ReportQueue.class;
    }

    @Override
    public void deploy() {
    }

    private String database;
	
	public ReportQueueKPIService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
   
	/**
	 * 创建Excel
	 */
	public void getExcelCreated(final HttpServletRequest request,final HttpServletResponse response, ReportCondition reportCondition) throws Exception {
		
		response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( "技能组-KPI报表".getBytes("utf-8"), "ISO8859-1" )+".xls");
       
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
        		+ " qu.`year`, "
        		+ " qu.`month`, "
        		+ "	qu.`week`, "
        		+ "	qu.`day`, "
        		+ "	qu.`hour`, "
        		+ "	qu.`timestamp`, "
        		+ "	qu.`name`, "
        		+ "	qu.in_f_callcount, "
        		+ "	qu.in_t_callcount, "
        		+ "	qu.in_f_wait, "
        		+ "	qu.in_t_wait, "
        		+ "	qu.in_t_duration, "
        		+ "	qu.max_queued, "
        		+ "	qu.min_queued, "
        		+ "	qu.answercount_0_5, "
        		+ "	qu.answercount_5_10, "
        		+ "	qu.answercount_10_15, "
        		+ "	qu.answercount_15_20, "
        		+ "	qu.answercount_20_25, "
        		+ "	qu.answercount_25_30, "
        		+ "	qu.answercount_30_40, "
        		+ "	qu.answercount_40_50, "
        		+ "	qu.answercount_50_60, "
        		+ "	qu.answercount_60_max, "
        		+ " qu.info	from "+database+"report_queue qu where 1=1 ");
        QueryUtil.like(whereSql, params, reportCondition.getName(), " and qu.name like ? ");
        QueryUtil.datetime(whereSql, params, reportCondition.getStime(), " and qu.timestamp >= ? ");
		QueryUtil.datetime(whereSql, params, reportCondition.getEtime(), " and qu.timestamp <= ? ");
		if(!"0".equals(reportCondition.getNameUid())){
			if (reportCondition.getQueuesList() != null && reportCondition.getQueuesList().size() > 0) {
				QueryUtils.in(whereSql, params, reportCondition.getQueuesList(), " and qu.name ");
			}else{
				whereSql.append(" and 1 != 1 ");
			}
		}
        whereSql.append(" ) as a ");
        final StringBuilder finalSql = new StringBuilder();
		 if (reportCondition.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
			 finalSql.append("select year,name,info,sum(in_f_callcount) as in_f_callcount,sum(in_t_callcount) as in_t_callcount,"
 					+ "sum(in_f_wait) as in_f_wait,sum(in_t_wait) as in_t_wait,sum(in_t_duration) as in_t_duration,max(max_queued) as max_queued,"
 					+ "min(min_queued) as min_queued,sum(answercount_0_5) as answercount_0_5,sum(answercount_5_10) as answercount_5_10,"
 					+ "sum(answercount_10_15) as answercount_10_15,sum(answercount_15_20) as answercount_15_20,"
 					+ "sum(answercount_20_25) as answercount_20_25,sum(answercount_25_30) as answercount_25_30,"
 					+ "sum(answercount_30_40) as answercount_30_40,sum(answercount_40_50) as answercount_40_50,"
 					+ "sum(answercount_50_60) as answercount_50_60,sum(answercount_60_max) as answercount_60_max ").append(whereSql).append(" group by year,name ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
			finalSql.append("select year,month,name,info,sum(in_f_callcount) as in_f_callcount,sum(in_t_callcount) as in_t_callcount,"
					+ "sum(in_f_wait) as in_f_wait,sum(in_t_wait) as in_t_wait,sum(in_t_duration) as in_t_duration,max(max_queued) as max_queued,"
					+ "min(min_queued) as min_queued,sum(answercount_0_5) as answercount_0_5,sum(answercount_5_10) as answercount_5_10,"
					+ "sum(answercount_10_15) as answercount_10_15,sum(answercount_15_20) as answercount_15_20,"
					+ "sum(answercount_20_25) as answercount_20_25,sum(answercount_25_30) as answercount_25_30,"
					+ "sum(answercount_30_40) as answercount_30_40,sum(answercount_40_50) as answercount_40_50,"
					+ "sum(answercount_50_60) as answercount_50_60,sum(answercount_60_max) as answercount_60_max ").append(whereSql).append(" group by year,month,name ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
			finalSql.append("select year,month,day,name,info,sum(in_f_callcount) as in_f_callcount,sum(in_t_callcount) as in_t_callcount,"
					+ "sum(in_f_wait) as in_f_wait,sum(in_t_wait) as in_t_wait,sum(in_t_duration) as in_t_duration,max(max_queued) as max_queued,"
					+ "min(min_queued) as min_queued,sum(answercount_0_5) as answercount_0_5,sum(answercount_5_10) as answercount_5_10,"
					+ "sum(answercount_10_15) as answercount_10_15,sum(answercount_15_20) as answercount_15_20,"
					+ "sum(answercount_20_25) as answercount_20_25,sum(answercount_25_30) as answercount_25_30,"
					+ "sum(answercount_30_40) as answercount_30_40,sum(answercount_40_50) as answercount_40_50,"
					+ "sum(answercount_50_60) as answercount_50_60,sum(answercount_60_max) as answercount_60_max ").append(whereSql).append(" group by year,month,day,name ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
			finalSql.append("select * ").append(whereSql);
		}

		jdbcTemplate.query(finalSql.toString(), params.toArray(),new ResultSetExtractor<T>() {
			
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<String> titles = new ArrayList<>();
				titles.add("时间");
				titles.add("时段");
				titles.add("技能组");
				titles.add("呼入次数（未接通）");
				titles.add("呼入次数（接通）");
				titles.add("平均等待时长（接通）");
				titles.add("平均等待时长（未接通）");
				titles.add("平均呼入通话时长（时:分:秒）");
				titles.add("最大排队数（人）");
				titles.add("最小排队数（人）");
				titles.add("5秒内接起电话");
				titles.add("5-10秒内接起电话");
				titles.add("10-15秒内接起电话");
				titles.add("15-20秒内接起电话");
				titles.add("20-25秒内接起电话");
				titles.add("25-30秒内接起电话");
				titles.add("30-40秒内接起电话");
				titles.add("40-50秒内接起电话");
				titles.add("50-60秒内接起电话");
				titles.add("60秒以后接起电话");
				
				
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
	public void createExcel(ReportQueue queue, HSSFSheet sheet, int maxRow,int titleNumber) {

		/* 创建的行数 */
		HSSFRow ro = sheet.createRow(maxRow);
		for (int i = 0; i < titleNumber; i++) {

			HSSFCell cell = ro.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			
			//计算呼入接通/未接通平均等待时长
       	 	String in_t_wait_avg = StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(queue.getIn_t_wait())/Double.valueOf(queue.getIn_t_callcount()))));
       	 	String in_f_wait_avg = StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(queue.getIn_f_wait())/Double.valueOf(queue.getIn_f_callcount()))));
       	 
       	 	//计算呼入平均通话时长
        	String in_t_call_avg = StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(queue.getIn_t_duration())/Double.valueOf(queue.getIn_t_callcount()))));
        	
			switch (i) {
				case 0:
					cell.setCellValue(queue.getExportdate());
					break;
				case 1:
					cell.setCellValue(StringUtils.isNotBlank(Domains.PARAGRAPHS.get(String.valueOf(queue.getHour())))?Domains.PARAGRAPHS.get(String.valueOf(queue.getHour())):"");
					break;
				case 2:
					cell.setCellValue(queue.getName());
					break;
				case 3:
					cell.setCellValue(queue.getIn_f_callcount());
					break;
				case 4:
					cell.setCellValue(queue.getIn_t_callcount());
					break;
				case 5:
					cell.setCellValue(DateUtils.getSToTime(Integer.parseInt(in_t_wait_avg)));
					break;
				case 6:
					cell.setCellValue(DateUtils.getSToTime(Integer.parseInt(in_f_wait_avg)));
					break;
				case 7:
					cell.setCellValue(DateUtils.getSToTime(Integer.parseInt(in_t_call_avg)));
					break;
				case 8:
					cell.setCellValue(queue.getMax_queued());
					break;
				case 9:
					cell.setCellValue(queue.getMin_queued());
					break;
				case 10:
					cell.setCellValue(queue.getAnswercount_0_5());
					break;
				case 11:
					cell.setCellValue(queue.getAnswercount_5_10());
					break;
				case 12:
					cell.setCellValue(queue.getAnswercount_10_15());
					break;
				case 13:
					cell.setCellValue(queue.getAnswercount_15_20());
					break;
				case 14:
					cell.setCellValue(queue.getAnswercount_20_25());
					break;
				case 15:
					cell.setCellValue(queue.getAnswercount_25_30());
					break;
				case 16:
					cell.setCellValue(queue.getAnswercount_30_40());
					break;
				case 17:
					cell.setCellValue(queue.getAnswercount_40_50());
					break;
				case 18:
					cell.setCellValue(queue.getAnswercount_50_60());
					break;
				case 19:
					cell.setCellValue(queue.getAnswercount_60_max());
					break;
				default:
					break;
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
	private ReportQueue retRecord(ResultSet rs)
			throws Exception {

		// 创建对象
		ReportQueue queue = new ReportQueue();
		
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
			
			queue.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")) + "-" + (rs.getString("day").length()==1?"0"+rs.getString("day"):rs.getString("day")));
		//月报
		} else if (columns.contains("year") && columns.contains("month") 
				&& StringUtils.isNotBlank(rs.getString("year")) && StringUtils.isNotBlank(rs.getString("month"))) {
			
			queue.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")));
		//年报
		} else if (columns.contains("year") && StringUtils.isNotBlank(rs.getString("year"))) {
			
			queue.setExportdate(rs.getString("year"));
		}
		
		queue.setName(rs.getString("name"));
		queue.setIn_f_callcount(Integer.parseInt(rs.getString("in_f_callcount")));
		queue.setIn_t_callcount(Integer.parseInt(rs.getString("in_t_callcount")));
		queue.setIn_f_wait(Integer.parseInt(rs.getString("in_f_wait")));
		queue.setIn_t_wait(Integer.parseInt(rs.getString("in_t_wait")));
		queue.setIn_t_duration(Integer.parseInt(rs.getString("in_t_duration")));
		queue.setMax_queued(Integer.parseInt(rs.getString("max_queued")));
		queue.setMin_queued(Integer.parseInt(rs.getString("min_queued")));
		queue.setAnswercount_0_5(Integer.parseInt(rs.getString("answercount_0_5")));
		queue.setAnswercount_5_10(Integer.parseInt(rs.getString("answercount_5_10")));
		queue.setAnswercount_10_15(Integer.parseInt(rs.getString("answercount_10_15")));
		queue.setAnswercount_15_20(Integer.parseInt(rs.getString("answercount_15_20")));
		queue.setAnswercount_20_25(Integer.parseInt(rs.getString("answercount_20_25")));
		queue.setAnswercount_25_30(Integer.parseInt(rs.getString("answercount_25_30")));
		queue.setAnswercount_30_40(Integer.parseInt(rs.getString("answercount_30_40")));
		queue.setAnswercount_40_50(Integer.parseInt(rs.getString("answercount_40_50")));
		queue.setAnswercount_50_60(Integer.parseInt(rs.getString("answercount_50_60")));
		queue.setAnswercount_60_max(Integer.parseInt(rs.getString("answercount_60_max")));

		return queue;
	}
	

    @Override
    protected ISolution getSolution() {
    	
        return new ISolution() {
        	ReportCondition cond=null;
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
             	cond = (ReportCondition) condition;
                whereSql.append(" from (select "
                		+ " qu.`year`, "
                		+ " qu.`month`, "
                		+ "	qu.`week`, "
                		+ "	qu.`day`, "
                		+ "	qu.`hour`, "
                		+ "	qu.`timestamp`, "
                		+ "	qu.`name`, "
                		+ "	qu.in_f_callcount, "
                		+ "	qu.in_t_callcount, "
                		+ "	qu.in_f_wait, "
                		+ "	qu.in_t_wait, "
                		+ "	qu.in_t_duration, "
                		+ "	qu.max_queued, "
                		+ "	qu.min_queued, "
                		+ "	qu.answercount_0_5, "
                		+ "	qu.answercount_5_10, "
                		+ "	qu.answercount_10_15, "
                		+ "	qu.answercount_15_20, "
                		+ "	qu.answercount_20_25, "
                		+ "	qu.answercount_25_30, "
                		+ "	qu.answercount_30_40, "
                		+ "	qu.answercount_40_50, "
                		+ "	qu.answercount_50_60, "
                		+ "	qu.answercount_60_max, "
                		+ " qu.info from "+database+"report_queue qu where 1=1 ");
                QueryUtil.like(whereSql, params, cond.getName(), " and qu.name like ? ");
                QueryUtil.datetime(whereSql, params, cond.getStime(), " and qu.timestamp >= ? ");
    			QueryUtil.datetime(whereSql, params, cond.getEtime(), " and qu.timestamp <= ? ");
    			if(!"0".equals(cond.getNameUid())){
    				if (cond.getQueuesList() != null && cond.getQueuesList().size() > 0) {
    					QueryUtils.in(whereSql, params, cond.getQueuesList(), " and qu.name ");
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
            		countSql.append(" select count(*) from (select count(*) ").append(whereSql).append(" ) a");
            	}
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
            	if (cond.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
            		pageSql.append("select year,name,info,sum(in_f_callcount) as in_f_callcount,sum(in_t_callcount) as in_t_callcount,"
        					+ "sum(in_f_wait) as in_f_wait,sum(in_t_wait) as in_t_wait,sum(in_t_duration) as in_t_duration,max(max_queued) as max_queued,"
        					+ "min(min_queued) as min_queued,sum(answercount_0_5) as answercount_0_5,sum(answercount_5_10) as answercount_5_10,"
        					+ "sum(answercount_10_15) as answercount_10_15,sum(answercount_15_20) as answercount_15_20,"
        					+ "sum(answercount_20_25) as answercount_20_25,sum(answercount_25_30) as answercount_25_30,"
        					+ "sum(answercount_30_40) as answercount_30_40,sum(answercount_40_50) as answercount_40_50,"
        					+ "sum(answercount_50_60) as answercount_50_60,sum(answercount_60_max) as answercount_60_max ").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
        			pageSql.append("select year,month,name,info,sum(in_f_callcount) as in_f_callcount,sum(in_t_callcount) as in_t_callcount,"
        					+ "sum(in_f_wait) as in_f_wait,sum(in_t_wait) as in_t_wait,sum(in_t_duration) as in_t_duration,max(max_queued) as max_queued,"
        					+ "min(min_queued) as min_queued,sum(answercount_0_5) as answercount_0_5,sum(answercount_5_10) as answercount_5_10,"
        					+ "sum(answercount_10_15) as answercount_10_15,sum(answercount_15_20) as answercount_15_20,"
        					+ "sum(answercount_20_25) as answercount_20_25,sum(answercount_25_30) as answercount_25_30,"
        					+ "sum(answercount_30_40) as answercount_30_40,sum(answercount_40_50) as answercount_40_50,"
        					+ "sum(answercount_50_60) as answercount_50_60,sum(answercount_60_max) as answercount_60_max ").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
        			pageSql.append("select year,month,day,name,info,sum(in_f_callcount) as in_f_callcount,sum(in_t_callcount) as in_t_callcount,"
        					+ "sum(in_f_wait) as in_f_wait,sum(in_t_wait) as in_t_wait,sum(in_t_duration) as in_t_duration,max(max_queued) as max_queued,"
        					+ "min(min_queued) as min_queued,sum(answercount_0_5) as answercount_0_5,sum(answercount_5_10) as answercount_5_10,"
        					+ "sum(answercount_10_15) as answercount_10_15,sum(answercount_15_20) as answercount_15_20,"
        					+ "sum(answercount_20_25) as answercount_20_25,sum(answercount_25_30) as answercount_25_30,"
        					+ "sum(answercount_30_40) as answercount_30_40,sum(answercount_40_50) as answercount_40_50,"
        					+ "sum(answercount_50_60) as answercount_50_60,sum(answercount_60_max) as answercount_60_max ").append(whereSql);
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

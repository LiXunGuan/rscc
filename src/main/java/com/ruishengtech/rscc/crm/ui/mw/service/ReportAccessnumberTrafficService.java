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

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.ui.mw.condition.ReportCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.ReportAccessnumber;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class ReportAccessnumberTrafficService extends BaseConfigService<ReportAccessnumber> {

    @Autowired
    private FsServerManager fsServerManager;


    @Override
    protected Class getClazz() {
        return ReportAccessnumber.class;
    }

    @Override
    public void deploy() {
    }

    private String database;
	
	public ReportAccessnumberTrafficService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	

	/**
	 * 创建Excel
	 */
	public void getExcelCreated(final HttpServletRequest request,final HttpServletResponse response, ReportCondition reportCondition) throws Exception {
		
		response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( "接入号-话务报表".getBytes("utf-8"), "ISO8859-1" )+".xls");
       
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
        		+ " a.`year`, "
        		+ " a.`month`, "
        		+ "	a.`week`, "
        		+ "	a.`day`, "
        		+ "	a.`hour`, "
        		+ "	a.`timestamp`, "
        		+ "	a.`name`, "
        		+ "	a.out_f_callcount, "
        		+ "	a.in_f_callcount, "
        		+ "	a.out_t_callcount, "
        		+ "	a.in_t_callcount, "
        		+ "	a.out_t_duration, "
        		+ "	a.in_t_duration, "
        		+ "	a.max_concurrent, "
        		+ "	a.min_concurrent,"
        		+ "	a.info "
        		+ " from "+database+"report_accessnumber a where 1=1 ");
        QueryUtil.like(whereSql, params, reportCondition.getName(), " and a.name like ? ");
        QueryUtil.datetime(whereSql, params, reportCondition.getStime(), " and a.timestamp >= ? ");
		QueryUtil.datetime(whereSql, params, reportCondition.getEtime(), " and a.timestamp <= ? ");
        whereSql.append(" ) as a ");
        
        final StringBuilder finalSql = new StringBuilder();
		 if (reportCondition.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
			 finalSql.append("select year,timestamp,name,info,sum(out_f_callcount) as out_f_callcount,"
	    				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
	    				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,"
	    				+ "sum(in_t_duration) as in_t_duration ,max(max_concurrent) as max_concurrent,min(min_concurrent) as min_concurrent ").append(whereSql).append(" group by year,name,info ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
			finalSql.append("select year,month,name,info,sum(out_f_callcount) as out_f_callcount,"
    				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
    				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,"
    				+ "sum(in_t_duration) as in_t_duration ,max(max_concurrent) as max_concurrent,min(min_concurrent) as min_concurrent ").append(whereSql).append(" group by year,month,name,info ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
			finalSql.append("select year,month,day,name,info,sum(out_f_callcount) as out_f_callcount,"
    				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
    				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,"
    				+ "sum(in_t_duration) as in_t_duration ,max(max_concurrent) as max_concurrent,min(min_concurrent) as min_concurrent ").append(whereSql).append(" group by year,month,day,name,info ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
			finalSql.append("select * ").append(whereSql);
		}
		
		jdbcTemplate.query(finalSql.toString(), params.toArray(),new ResultSetExtractor<T>() {
			
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<String> titles = new ArrayList<>();
				titles.add("时间");
				titles.add("时段");
				titles.add("接入号");
				titles.add("呼出次数（未接通）");
				titles.add("呼入次数（未接通）");
				titles.add("呼出次数（接通）");
				titles.add("呼入次数（接通）");
				titles.add("呼出通话总时长（时:分:秒）");
				titles.add("呼入通话总时长（时:分:秒）");
				titles.add("最大并发数（个）");
				titles.add("最小并发数（个）");
				titles.add("呼出平均时长");
				titles.add("呼入平均时长");
				
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
	public void createExcel(ReportAccessnumber ra, HSSFSheet sheet, int maxRow,int titleNumber) {
		
		/* 创建的行数 */
		HSSFRow ro = sheet.createRow(maxRow);
		
		ra.setOut_t_duration(ra.getOut_t_duration()%1000!=0?ra.getOut_t_duration()/1000+1:ra.getOut_t_duration()/1000);
		ra.setIn_t_duration(ra.getIn_t_duration()%1000!=0?ra.getIn_t_duration()/1000+1:ra.getIn_t_duration()/1000);
		
		Integer outavg = Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(ra.getOut_t_duration())/Double.valueOf(ra.getOut_t_callcount())))));
		Integer inavg =Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(ra.getIn_t_duration())/Double.valueOf(ra.getIn_t_callcount()+ra.getIn_f_callcount())))));
		
		//处理呼入呼出总时长
		Integer out_t_dur = ra.getOut_t_duration();
		Integer in_t_dur = ra.getIn_t_duration();
		for (int i = 0; i < titleNumber; i++) {

			HSSFCell cell = ro.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			
			switch (i) {
				case 0:
					cell.setCellValue(ra.getExportdate());
					break;
				case 1:
					cell.setCellValue(StringUtils.isNotBlank(Domains.PARAGRAPHS.get(String.valueOf(ra.getHour())))?Domains.PARAGRAPHS.get(String.valueOf(ra.getHour())):"");
					break;
				case 2:
					cell.setCellValue(ra.getName());
					break;
				case 3:
					cell.setCellValue(ra.getOut_f_callcount().toString());
					break;
				case 4:
					cell.setCellValue(ra.getIn_f_callcount().toString());
					break;
				case 5:
					cell.setCellValue(ra.getOut_t_callcount().toString());
					break;
				case 6:
					cell.setCellValue(ra.getIn_t_callcount().toString());
					break;
				case 7:
					cell.setCellValue(DateUtils.getSToTime(out_t_dur));
					break;
				case 8:
					cell.setCellValue(DateUtils.getSToTime(in_t_dur));
					break;
				case 9:
					cell.setCellValue(ra.getMax_concurrent().toString());
					break;
				case 10:
					cell.setCellValue(ra.getMin_concurrent().toString());
					break;
				case 11:
					cell.setCellValue(DateUtils.getSToTime(outavg));
					break;
				case 12:
					cell.setCellValue(DateUtils.getSToTime(inavg));
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
	private ReportAccessnumber retRecord(ResultSet rs)
			throws Exception {

		// 创建对象
		ReportAccessnumber ra = new ReportAccessnumber();
		
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
			
			ra.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")) + "-" + (rs.getString("day").length()==1?"0"+rs.getString("day"):rs.getString("day")));
		//月报
		} else if (columns.contains("year") && columns.contains("month") 
				&& StringUtils.isNotBlank(rs.getString("year")) && StringUtils.isNotBlank(rs.getString("month"))) {
			
			ra.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")));
		//年报
		} else if (columns.contains("year") && StringUtils.isNotBlank(rs.getString("year"))) {
			
			ra.setExportdate(rs.getString("year"));
		}
		//时报
		if (columns.contains("hour") && StringUtils.isNotBlank(rs.getString("hour"))) {
			ra.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")) + "-" + (rs.getString("day").length()==1?"0"+rs.getString("day"):rs.getString("day")));
			ra.setHour(rs.getInt("hour"));
		}
		
		ra.setName(rs.getString("name"));
		ra.setOut_f_callcount(Integer.parseInt(rs.getString("out_f_callcount")));
		ra.setIn_f_callcount(Integer.parseInt(rs.getString("in_f_callcount")));
		ra.setOut_t_callcount(Integer.parseInt(rs.getString("out_t_callcount")));
		ra.setIn_t_callcount(Integer.parseInt(rs.getString("in_t_callcount")));
		ra.setOut_t_duration(Integer.parseInt(rs.getString("out_t_duration")));
		ra.setIn_t_duration(Integer.parseInt(rs.getString("in_t_duration")));
		ra.setMax_concurrent(Integer.parseInt(rs.getString("max_concurrent")));
		ra.setMin_concurrent(Integer.parseInt(rs.getString("min_concurrent")));

		return ra;
	}

    
    @Override
    protected ISolution getSolution() {
    	
        return new ISolution() {
        	ReportCondition cond=null;
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
             	cond = (ReportCondition) condition;
                whereSql.append(" from (select "
                		+ " a.`year`, "
                		+ " a.`month`, "
                		+ "	a.`week`, "
                		+ "	a.`day`, "
                		+ "	a.`hour`, "
                		+ "	a.`timestamp`, "
                		+ "	a.`name`, "
                		+ "	a.out_f_callcount, "
                		+ "	a.in_f_callcount, "
                		+ "	a.out_t_callcount, "
                		+ "	a.in_t_callcount, "
                		+ "	a.out_t_duration, "
                		+ "	a.in_t_duration, "
                		+ "	a.max_concurrent, "
                		+ "	a.min_concurrent,"
                		+ "	a.info "
                		+ "from "+database+"report_accessnumber a where 1=1 ");
                QueryUtil.like(whereSql, params, cond.getName(), " and a.name like ? ");
                QueryUtil.datetime(whereSql, params, cond.getStime(), " and a.timestamp >= ? ");
    			QueryUtil.datetime(whereSql, params, cond.getEtime(), " and a.timestamp <= ? ");
                whereSql.append(" ) as a ");

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
            		pageSql.append("select year,name,info,sum(out_f_callcount) as out_f_callcount,"
            				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
            				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,"
            				+ "sum(in_t_duration) as in_t_duration ,max(max_concurrent) as max_concurrent,min(min_concurrent) as min_concurrent ").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
        			pageSql.append("select year,month,name,info,sum(out_f_callcount) as out_f_callcount,"
            				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
            				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,"
            				+ "sum(in_t_duration) as in_t_duration ,max(max_concurrent) as max_concurrent,min(min_concurrent) as min_concurrent").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
        			pageSql.append("select year,month,day,name,info,sum(out_f_callcount) as out_f_callcount,"
            				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
            				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,"
            				+ "sum(in_t_duration) as in_t_duration ,max(max_concurrent) as max_concurrent,min(min_concurrent) as min_concurrent").append(whereSql);
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

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
import com.ruishengtech.rscc.crm.ui.mw.model.ReportExten;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class ReportExtenTrafficService extends BaseConfigService<ReportExten> {

    @Autowired
    private FsServerManager fsServerManager;


    @Override
    protected Class getClazz() {
        return ReportExten.class;
    }

    @Override
    public void deploy() {
    }


    private String database;
	
	public ReportExtenTrafficService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
   
	/**
	 * 创建Excel
	 */
	public void getExcelCreated(final HttpServletRequest request,final HttpServletResponse response, ReportCondition reportCondition) throws Exception {
		
		response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( "分机-话务报表".getBytes("utf-8"), "ISO8859-1" )+".xls");
       
		final StringBuilder whereSql=new StringBuilder();
		final StringBuilder pageSql=new StringBuilder();
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
        		+ " e.`year`, "
        		+ " e.`month`, "
        		+ "	e.`week`, "
        		+ "	e.`day`, "
        		+ "	e.`hour`, "
        		+ "	e.`timestamp`, "
        		+ "	e.`name`, "
        		+ "	e.out_f_callcount, "
        		+ "	e.in_f_callcount, "
        		+ "	e.in_f_ringduration, "
        		+ "	e.in_t_ringduration, "
        		+ "	e.out_t_callcount, "
        		+ "	e.in_t_callcount, "
        		+ "	e.out_t_duration, "
        		+ "	e.in_t_duration, "
        		+ " e.info "
        		+ "from "+database+"report_exten e where 1=1 ");
        QueryUtil.like(whereSql, params, reportCondition.getName(), " and e.name like ? ");
        QueryUtil.datetime(whereSql, params, reportCondition.getStime(), " and e.timestamp >= ? ");
		QueryUtil.datetime(whereSql, params, reportCondition.getEtime(), " and e.timestamp <= ? ");
        whereSql.append(" ) as a ");
        
        final StringBuilder finalSql = new StringBuilder();
		 if (reportCondition.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
			 finalSql.append("select year,name,info,sum(out_f_callcount) as out_f_callcount,"
	    				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
	    				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,sum(in_t_duration) as in_t_duration,"
	    				+ "sum(in_f_ringduration) as in_f_ringduration,sum(in_t_ringduration) as in_t_ringduration ").append(whereSql).append(" group by year,name,info ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
			finalSql.append("select year,month,name,info,sum(out_f_callcount) as out_f_callcount,"
    				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
    				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,sum(in_t_duration) as in_t_duration,"
    				+ "sum(in_f_ringduration) as in_f_ringduration,sum(in_t_ringduration) as in_t_ringduration ").append(whereSql).append(" group by year,month,name,info ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
			finalSql.append("select year,month,day,name,info,sum(out_f_callcount) as out_f_callcount,"
    				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
    				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,sum(in_t_duration) as in_t_duration,"
    				+ "sum(in_f_ringduration) as in_f_ringduration,sum(in_t_ringduration) as in_t_ringduration ").append(whereSql).append(" group by year,month,day,name,info ");
		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
			finalSql.append("select * ").append(whereSql);
		}

		jdbcTemplate.query(finalSql.toString(), params.toArray(),new ResultSetExtractor<T>() {
			
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<String> titles = new ArrayList<>();
				titles.add("时间");
				titles.add("时段");
				titles.add("分机");
				titles.add("呼出次数（未接通）");
				titles.add("呼入次数（未接通）");
				titles.add("呼出次数（接通）");
				titles.add("呼入次数（接通）");
				titles.add("呼出通话总时长（时:分:秒）");
				titles.add("呼入通话总时长（时:分:秒）");
				titles.add("呼入平均振铃（未接通）");
				titles.add("呼入平均振铃（接通）");
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
	public void createExcel(ReportExten rx, HSSFSheet sheet, int maxRow,int titleNumber) {

		/* 创建的行数 */
		HSSFRow ro = sheet.createRow(maxRow);
		
		rx.setOut_t_duration(rx.getOut_t_duration()%1000!=0?rx.getOut_t_duration()/1000+1:rx.getOut_t_duration()/1000);
        rx.setIn_t_duration(rx.getIn_t_duration() % 1000 != 0 ? rx.getIn_t_duration() / 1000 + 1 : rx.getIn_t_duration() / 1000);
        rx.setIn_t_ringduration(rx.getIn_t_ringduration() % 1000 != 0 ? rx.getIn_t_ringduration() / 1000 + 1 : rx.getIn_t_ringduration() / 1000);
        rx.setIn_f_ringduration(rx.getIn_f_ringduration()%1000!=0?rx.getIn_f_ringduration()/1000+1:rx.getIn_f_ringduration()/1000);
        
        Integer out_t_duration = rx.getOut_t_duration();
        Integer in_t_duration = rx.getIn_t_duration();
        
    	String intringduration = StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(rx.getIn_t_ringduration())/Double.valueOf(rx.getIn_t_callcount()))));
   	 	String infringduration = StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(rx.getIn_f_ringduration())/Double.valueOf(rx.getIn_f_callcount()))));
   	 
   	 	//计算呼入/呼出出平均时长
   	 	String outavg = StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(rx.getOut_t_duration())/Double.valueOf(rx.getOut_t_callcount()))));
    	String inavg = StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(rx.getIn_t_duration())/Double.valueOf(rx.getIn_t_callcount()))));
       
        
		for (int i = 0; i < titleNumber; i++) {

			HSSFCell cell = ro.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			
			switch (i) {
				case 0:
					cell.setCellValue(rx.getExportdate());
					break;
				case 1:
					cell.setCellValue(StringUtils.isNotBlank(Domains.PARAGRAPHS.get(String.valueOf(rx.getHour())))?Domains.PARAGRAPHS.get(String.valueOf(rx.getHour())):"");
					break;
				case 2:
					cell.setCellValue(rx.getName());
					break;
				case 3:
					cell.setCellValue(rx.getOut_f_callcount());
					break;
				case 4:
					cell.setCellValue(rx.getIn_f_callcount());
					break;
				case 5:
					cell.setCellValue(rx.getOut_t_callcount());
					break;
				case 6:
					cell.setCellValue(rx.getIn_t_callcount());
					break;
				case 7:
					cell.setCellValue(DateUtils.getSToTime(out_t_duration));
					break;
				case 8:
					cell.setCellValue(DateUtils.getSToTime(in_t_duration));
					break;
				case 9:
					cell.setCellValue(DateUtils.getSToTime(Integer.parseInt(infringduration)));
					break;
				case 10:
					cell.setCellValue(DateUtils.getSToTime(Integer.parseInt(intringduration)));
					break;
				case 11:
					cell.setCellValue(DateUtils.getSToTime(Integer.parseInt(outavg)));
					break;
				case 12:
					cell.setCellValue(DateUtils.getSToTime(Integer.parseInt(inavg)));
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
	private ReportExten retRecord(ResultSet rs)
			throws Exception {

		// 创建对象
		ReportExten rx = new ReportExten();
		
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
			
			rx.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")) + "-" + (rs.getString("day").length()==1?"0"+rs.getString("day"):rs.getString("day")));
		//月报
		} else if (columns.contains("year") && columns.contains("month") 
				&& StringUtils.isNotBlank(rs.getString("year")) && StringUtils.isNotBlank(rs.getString("month"))) {
			
			rx.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")));
		//年报
		} else if (columns.contains("year") && StringUtils.isNotBlank(rs.getString("year"))) {
			
			rx.setExportdate(rs.getString("year"));
		}
		//时报
		if (columns.contains("hour") && StringUtils.isNotBlank(rs.getString("hour"))) {
			rx.setExportdate(rs.getString("year") + "-" + (rs.getString("month").length()==1?"0"+rs.getString("month"):rs.getString("month")) + "-" + (rs.getString("day").length()==1?"0"+rs.getString("day"):rs.getString("day")));
			rx.setHour(rs.getInt("hour"));
		}
		
		rx.setName(rs.getString("name"));
		rx.setOut_f_callcount(Integer.parseInt(rs.getString("out_f_callcount")));
		rx.setIn_f_callcount(Integer.parseInt(rs.getString("in_f_callcount")));
		rx.setOut_t_callcount(Integer.parseInt(rs.getString("out_t_callcount")));
		rx.setIn_t_callcount(Integer.parseInt(rs.getString("in_t_callcount")));
		rx.setOut_t_duration(Integer.parseInt(rs.getString("out_t_duration")));
		rx.setIn_t_duration(Integer.parseInt(rs.getString("in_t_duration")));
		rx.setIn_f_ringduration(Integer.parseInt(rs.getString("in_f_ringduration")));
		rx.setIn_t_ringduration(Integer.parseInt(rs.getString("in_t_ringduration")));

		return rx;
	}
	
	
    @Override
    protected ISolution getSolution() {
    	
        return new ISolution() {
        	ReportCondition cond=null;
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	cond = (ReportCondition) condition;
                whereSql.append(" from (select "
                		+ " e.`year`, "
                		+ " e.`month`, "
                		+ "	e.`week`, "
                		+ "	e.`day`, "
                		+ "	e.`hour`, "
                		+ "	e.`timestamp`, "
                		+ "	e.`name`, "
                		+ "	e.out_f_callcount, "
                		+ "	e.in_f_callcount, "
                		+ "	e.in_f_ringduration, "
                		+ "	e.in_t_ringduration, "
                		+ "	e.out_t_callcount, "
                		+ "	e.in_t_callcount, "
                		+ "	e.out_t_duration, "
                		+ "	e.in_t_duration, "
                		+ " e.info "
                		+ " from "+database+"report_exten e where 1=1 ");
                QueryUtil.like(whereSql, params, cond.getName(), " and e.name like ? ");
                QueryUtil.datetime(whereSql, params, cond.getStime(), " and e.timestamp >= ? ");
    			QueryUtil.datetime(whereSql, params, cond.getEtime(), " and e.timestamp <= ? ");
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
            				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,sum(in_t_duration) as in_t_duration,"
            				+ "sum(in_f_ringduration) as in_f_ringduration,sum(in_t_ringduration) as in_t_ringduration ").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
        			pageSql.append("select year,month,name,info,sum(out_f_callcount) as out_f_callcount,"
            				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
            				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,sum(in_t_duration) as in_t_duration,"
            				+ "sum(in_f_ringduration) as in_f_ringduration,sum(in_t_ringduration) as in_t_ringduration ").append(whereSql);
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
        		}else if (cond.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
        			pageSql.append("select year,month,day,name,info,sum(out_f_callcount) as out_f_callcount,"
            				+ "sum(in_f_callcount) as in_f_callcount,sum(out_t_callcount) as out_t_callcount,"
            				+ "sum(in_t_callcount) as in_t_callcount,sum(out_t_duration) as out_t_duration,sum(in_t_duration) as in_t_duration,"
            				+ "sum(in_f_ringduration) as in_f_ringduration,sum(in_t_ringduration) as in_t_ringduration ").append(whereSql);
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

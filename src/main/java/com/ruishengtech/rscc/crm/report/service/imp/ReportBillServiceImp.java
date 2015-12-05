package com.ruishengtech.rscc.crm.report.service.imp;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.billing.model.Billing;
import com.ruishengtech.rscc.crm.billing.solution.BillingSolution;
import com.ruishengtech.rscc.crm.report.condition.ReportBillCondition;
import com.ruishengtech.rscc.crm.report.model.ReportBill;
import com.ruishengtech.rscc.crm.report.service.ReportBillService;
import com.ruishengtech.rscc.crm.report.solution.ReportBillSolution;

@Service
@Transactional
public class ReportBillServiceImp extends BaseService implements ReportBillService{

	@Override
	public PageResult<ReportBill> queryPage(ReportBillCondition billCondition) {
		
		Calendar cal_1=Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(billCondition.getBilling_date())){
			if(billCondition.getBilling_date().equals("todaydata")){
				billCondition.setBilling_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
		}
		if(StringUtils.isEmpty(billCondition.getStime()) && StringUtils.isEmpty(billCondition.getEtime())){
			if(billCondition.getShow_type().equals("day")){
				billCondition.setStime(sdf.format(new Date().getTime()-86400000));
				billCondition.setEtime(sdf.format(new Date()));
			}else if(billCondition.getShow_type().equals("month")){
				cal_1.setTime(new Date());
				cal_1.set(Calendar.DAY_OF_MONTH, 1);
				billCondition.setStime(sdf.format(cal_1.getTime()));
				billCondition.setEtime(sdf.format(new Date()));
			}
		}else if(billCondition.getStime().equals(billCondition.getEtime())){
			if(billCondition.getShow_type().equals("day")){
				try {
					cal_1.setTime(sdf.parse(billCondition.getEtime()));
					billCondition.setEtime(sdf.format(cal_1.getTimeInMillis()+86400000l));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if(billCondition.getShow_type().equals("month")){
				cal_1.setTime(new Date());
				cal_1.set(Calendar.DAY_OF_MONTH, 1);
				billCondition.setStime(sdf.format(cal_1.getTime()));
				billCondition.setEtime(sdf.format(new Date()));
			}
		}
		
		
		return super.queryPage(new ReportBillSolution(), billCondition, ReportBill.class);
	}
	
	public boolean insertReportBill(ReportBill rb){
		try {
//			super.save(rb);
			jdbcTemplate.update("insert into report_billing(\n" +
								"	`billing_type`,\n" +
								"	`name`,\n" +
								"	`call_type`,\n" +
								"	`call_charge`,\n" +
								"	`billing_date`) values(?,?,?,?,?)",rb.getBilling_type(),rb.getName(),rb.getCall_type(),rb.getCall_charge(),rb.getBilling_date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Map<String, Object> queryResultForHistory(ICondition condition) {
		ReportBillSolution b = new ReportBillSolution();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<>();
		b.getStatisticsSql(condition, sb, params);
		Map<String, Object> map = jdbcTemplate.queryForMap(sb.toString(), params.toArray());
		return map;
	}
	
	public void deleteReportBillByDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = sdf.format(cal.getTime());
		jdbcTemplate.update("DELETE\n" +
				"FROM\n" +
				"	report_billing\n" +
				"WHERE\n" +
				"	billing_date >= ?\n" +
				"AND billing_date < ?", yesterday,sdf.format(new Date()));
		
	}
	//获取当天的实时数据
	@Override
	public List<ReportBill> getTodayData(ReportBillCondition rbCondition) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String nowTime = sdf.format(now);
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DAY_OF_MONTH, 1);
		now = calendar.getTime();
		String nextStart = sdf.format(now);
		StringBuilder finalSql = new StringBuilder();
		//统计某个分机/队列指定时间段内的总时长和总费用
		finalSql.append("SELECT type,exten,end_stamp,sum(cost) sumC FROM billing_bill WHERE end_stamp >= ? and end_stamp < ? ");
		if(rbCondition.getBilling_type().equals("0")){
			finalSql.append(" and type='0' ");
		}else if(rbCondition.getBilling_type().equals("1")){
			finalSql.append(" and type='1' ");
		}
		List<Object> name = new ArrayList<>();
		name.add(rbCondition.getName());
		QueryUtils.like(finalSql, name, rbCondition.getName(), " and exten like ? ");
		Map<String,Object> sumcost = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(rbCondition.getName())){
			sumcost = jdbcTemplate.queryForMap(finalSql.toString(),nowTime,nextStart,rbCondition.getName());
		}else{
			sumcost = jdbcTemplate.queryForMap(finalSql.toString(),nowTime,nextStart);
		}
		finalSql.append(" GROUP BY type,exten ORDER BY type desc");
		List<Billing> bills = new ArrayList<>();
		if(StringUtils.isNotBlank(rbCondition.getName())){
			bills = super.getBeanListWithSql(Billing.class, finalSql.toString(),nowTime,nextStart,rbCondition.getName());
		}else{
			bills = super.getBeanListWithSql(Billing.class, finalSql.toString(),nowTime,nextStart);
		}
		
		List<ReportBill> rbs = new ArrayList<ReportBill>();
		for(Billing bl : bills){
			ReportBill rb = new ReportBill();
			rb.setName(bl.getExten());
			//0==分机
			rb.setBilling_type(bl.getType().toString());
			//1==市话，2==长途
			rb.setCall_type("1");
			rb.setCall_charge(bl.getSumC().toString());
			rb.setSumcost(sumcost.get("sumC").toString());
			rbs.add(rb);
		}
		return rbs;
	}
	
	public void insertRBillByBill(){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0); 
		calendar.set(Calendar.SECOND,0); 
		calendar.set(Calendar.MILLISECOND, 0);
		now = calendar.getTime();
		long before = now.getTime()-86400000;
		String befores = sdf.format(before);
		String nows = sdf.format(now);
		
		List<Billing> list = getBill(befores,nows);
		
		List<ReportBill> rblist =getAllReportBill();
		List<Long> id = new ArrayList<>();
		if(rblist.size() != 0){
			for(int i=0;i<rblist.size();i++){
				id.add(rblist.get(i).getId());
			}
		}
		ReportBill rb = new ReportBill();
		for(Billing bl : list){
			
			rb.setName(bl.getExten());
			//0==分机
			rb.setBilling_type(bl.getType().toString());
			//1==市话，2==长途
			rb.setCall_type("1");
			rb.setCall_charge(bl.getSumC().toString());

			//设置计费时间(以结束时间为准如果在当天区间内则统计)
			rb.setBilling_date(befores.substring(0, 10));
			
			insertReportBill(rb);
		}
	}
	
	public List<Billing> getBill(String stime,String etime){
		List<Billing> bills = super.getBeanListWithSql(Billing.class, "SELECT type,exten,end_stamp,sum(cost) sumC FROM billing_bill WHERE end_stamp >= ? and end_stamp < ? GROUP BY type,exten",stime,etime);
		return bills;
	}
	
	public void removeReportBill(){
		jdbcTemplate.update("DELETE FROM report_billing");
	}
	
	public List<ReportBill> getAllReportBill(){
		return super.getAll(ReportBill.class);
	}
	
	/**
	 * 创建Excel
	 */
	public void getExcelCreated(final HttpServletRequest request,final HttpServletResponse response, ReportBillCondition billCondition) throws Exception {
		
		response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( "计费报表".getBytes("utf-8"), "ISO8859-1" )+".xls");
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT * FROM report_billing WHERE 1=1 ");
        
		
		
		final StringBuilder esql=new StringBuilder();
		esql.append(getSql(billCondition,sql,request));
		List<Object> params = new ArrayList<Object>();
		jdbcTemplate.query(sql.toString(), params.toArray(),new ResultSetExtractor<T>() {
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<String> titles = new ArrayList<>();
				titles.add("计费类型");
				titles.add("名称");
				titles.add("总花费");
				
				try {
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet sheet = workbook.createSheet();
					sheet.setDefaultColumnWidth(20);
					HSSFRow row = sheet.createRow(0);
					HSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (int i = 0; i < titles.size(); i++) {
						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						HSSFCell cell = row.createCell(i);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(titles.get(i));
					}
					int rowNum = 1;
					while (rs.next()) {

						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						createExcel(retRecord(rs, sdf), sheet, rowNum,titles.size());
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
	 * 添加查询条件
	 * @param billCondition
	 * @param sql
	 * @param request
	 * @return
	 */
	public StringBuilder getSql(ReportBillCondition billCondition,
			StringBuilder sql, HttpServletRequest request) throws Exception{
		//如果没有指定导出的开始和结束时间，则默认导出当前日期前一天的数据
		Calendar cal_1=Calendar.getInstance();
		if(StringUtils.isEmpty(billCondition.getStime()) && StringUtils.isEmpty(billCondition.getEtime())){
			if(billCondition.getShow_type().equals("day")){
				billCondition.setStime(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()-86400000));
				billCondition.setEtime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}else if(billCondition.getShow_type().equals("month")){
				cal_1.setTime(new Date());
				cal_1.set(Calendar.DAY_OF_MONTH, 1);
				billCondition.setStime(new SimpleDateFormat("yyyy-MM-dd").format(cal_1.getTime()));
				billCondition.setEtime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
		}
		
		if(StringUtils.isNotBlank(billCondition.getBilling_type())){
			sql.append(" AND  billing_type = "+billCondition.getBilling_type()+" ");
		}
		if(StringUtils.isNotBlank(billCondition.getStime())){
			sql.append(" AND billing_date >= '"+billCondition.getStime().substring(0,10).replaceAll(" ", "")+"' ");
		}
		if(StringUtils.isNotBlank(billCondition.getEtime())){
			
			sql.append(" AND billing_date <= '"+billCondition.getEtime().substring(0,10).replaceAll(" ", "")+"' ");
		}
		return sql.append(" ORDER BY billing_type DESC ");
	}
	
	/**
	 * 给Excel列赋值
	 */
	public void createExcel(ReportBill rb, HSSFSheet sheet, int maxRow,int titleNumber) {

		/* 创建的行数 */
		HSSFRow ro = sheet.createRow(maxRow);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Double charge = Double.parseDouble(rb.getCall_charge());
		BigDecimal b = new BigDecimal(charge);
		
		rb.setCall_charge(String.valueOf(b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue()));
		
		for (int i = 0; i < titleNumber; i++) {

			HSSFCell cell = ro.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			if(rb.getBilling_type().equals("0")){
				rb.setBilling_type("分机");
			}else{
				rb.setBilling_type("技能组");
			}
			switch (i) {
				case 0:
					cell.setCellValue(rb.getBilling_type());
					break;
				case 1:
					cell.setCellValue(rb.getName());
					break;
				case 2:
					cell.setCellValue(rb.getCall_charge());
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
	private ReportBill retRecord(ResultSet rs, SimpleDateFormat sdf)
			throws Exception {

		// 创建对象
		ReportBill report = new ReportBill();
		
		report.setBilling_type(rs.getString("billing_type"));
		report.setName(rs.getString("name"));
		report.setCall_charge(rs.getString("call_charge"));

		return report;
	}

}

package com.ruishengtech.rscc.crm.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.neworderinfo.service.NewOrderInfoService;


public class ExportReportUtil{
	
	private static CustomerService customerService;
	
	private static NewOrderInfoService orderInfoService = ApplicationHelper.getApplicationContext().getBean(NewOrderInfoService.class);
	
	/**  *****************************************************************************************
	 *    
	* @param filename 保存到客户端的文件名  
	* @param title 标题行 例：String[]{"名称","地址"}  
	* @param key   从查询结果List取得的MAP的KEY顺序，需要和title顺序匹配，例：String[]{"name","address"}  
	* @param values 结果集  
	* @param httpServletResponse  
	* @throws IOException  
	*/
	public static void createExcel(String filename, List<String> title, List<String> key , List<Map<String, Object>> values,HttpServletResponse httpServletResponse)throws IOException {  
		
		String filename2=new String(filename.getBytes(),"iso-8859-1");
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
	    HSSFWorkbook workbook = new HSSFWorkbook();
	    httpServletResponse.setHeader("Content-disposition","attachment; filename=" + filename2);  
	    httpServletResponse.setContentType("application/x-download");
	    HSSFSheet sheet = workbook.createSheet();  
	    HSSFRow row = null;  
	    HSSFCell cell = null;  
	    row = sheet.createRow((short) 0);  
	    for (int i = 0; title != null && i < title.size(); i++) {  
	        cell = row.createCell((int) i);  
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
	        cell.setCellValue(new HSSFRichTextString(title.get(i)));  
	    }  
	    Map map = null;  
	    for (int i = 0; values != null && i < values.size(); i++) {
	        row = sheet.createRow((short) (i + 1));  
	        map = values.get(i);
	        for (int i2 = 0; i2 < key.size(); i2++) {
	            cell = row.createCell((int) (i2));  
	            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
	            if(map.get(key.get(i2))==null){      
	                cell.setCellValue(new HSSFRichTextString(""));  
	            }else if("products".equals(key.get(i2))){
	            	if(StringUtils.isNotBlank(map.get(key.get(i2)).toString())){
	            		
	            		HSSFCellStyle cellStyle = workbook.createCellStyle();
	            		cellStyle.setWrapText(true);//先设置为自动换行     
	            		cell.setCellStyle(cellStyle);                    
	            		
	            		cell.setCellValue(new HSSFRichTextString(map.get(key.get(i2)).toString().replace(";", ";  ")));
	            	}else{
	            		cell.setCellValue(new HSSFRichTextString(""));  
	            	}
	            }else{
	            		cell.setCellValue(new HSSFRichTextString( map.get(key.get(i2)).toString()));  
	            }  
	        }  
	    }  
	    workbook.write(servletOutputStream);
	    servletOutputStream.flush();  
    }
	
	public static List<Map<String, Object>> getResult(ICondition condition, ISolution solution) {
		StringBuilder querySql = new StringBuilder("select * ");
		List<Object> params = new ArrayList<Object>();
		solution.getWhere(condition, querySql, params);
		JdbcTemplate jdbcTemplate = ApplicationHelper.getApplicationContext().getBean(JdbcTemplate.class);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray());
	}
	
	public static List<Map<String, Object>> getDiyResult(Map<String,ColumnDesign> map, HttpServletRequest request,com.ruishengtech.framework.core.db.diy.ISolution solution) {
		StringBuilder querySql = new StringBuilder(" SELECT c.*,group_concat(tag_name) AS customerTags ");
		List<Object> params = new ArrayList<Object>();
		solution.getWhere(map, querySql, request,params);
		JdbcTemplate jdbcTemplate = ApplicationHelper.getApplicationContext().getBean(JdbcTemplate.class);
		
		List<Map<String, Object>> a = jdbcTemplate.queryForList(querySql.toString(), params.toArray());
		
		List<Map<String, Object>> b = new ArrayList<Map<String,Object>>();
		
		customerService = customerService==null?ApplicationHelper.getApplicationContext().getBean(CustomerService.class):customerService;
		
		for (int i = 0; i < a.size(); i++) {
			b.add(customerService.getMapObject(a.get(i)));
		}
			
		return b;
	
	}
	
	public static List<Map<String, Object>> getNewOrderInfoResult(Map<String,ColumnDesign> map, HttpServletRequest request,com.ruishengtech.framework.core.db.diy.ISolution solution) {
		
		JdbcTemplate jdbcTemplate = ApplicationHelper.getApplicationContext().getBean(JdbcTemplate.class);
		
		StringBuilder querySql = new StringBuilder(" SELECT o.*, q.score as score, c.bridgesec as bridgesec ");
		List<Object> params = new ArrayList<Object>();
		solution.getWhere(map, querySql, request,params);
		
		List<Map<String, Object>> a = jdbcTemplate.queryForList(querySql.toString(), params.toArray());
		List<Map<String, Object>> b = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < a.size(); i++) {
			b.add(orderInfoService.getMapObject(a.get(i)));
		}
		
		return b;
	}
	
}

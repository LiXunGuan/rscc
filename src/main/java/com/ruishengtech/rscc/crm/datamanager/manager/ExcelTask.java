package com.ruishengtech.rscc.crm.datamanager.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.data.util.CollectionUtil;

public class ExcelTask implements Runnable {

	private int total = 0;
	
	private int totalProcess = 0;
	
	private int now = 0;
	
	private String uuid;
	
	private String tableName;
	
	private String filePath;
	
	private String uploader;
	
	private Collection<String> relatedUsers;
	
	private JdbcTemplate jdbcTemplate;
	
	private DataSourceTransactionManager dataSourceTransactionManager;

	private BrokerService brokerService;

	public Collection<String> getRelatedUsers() {
		return relatedUsers;
	}

	public void setRelatedUsers(Collection<String> relatedUsers) {
		this.relatedUsers = relatedUsers;
	}

	public String getUploader() {
		return uploader;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNow() {
		return now;
	}

	public void setNow(int now) {
		this.now = now;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ExcelTask(){
		this.jdbcTemplate = ApplicationHelper.getApplicationContext().getBean(JdbcTemplate.class);
		this.dataSourceTransactionManager = ApplicationHelper.getApplicationContext().getBean(DataSourceTransactionManager.class);
		this.brokerService = ApplicationHelper.getApplicationContext().getBean(BrokerService.class);
	}
	
	public ExcelTask(String uuid, String filePath, String uploader, String tableName, Collection<String> relatedUsers){
		this();
		this.uuid = uuid;
		this.filePath = filePath;
		this.uploader = uploader;
		this.tableName = tableName;
		this.relatedUsers = relatedUsers;
	}
	
	//格式化到百分之九十，之后百分之十用于去重
	public synchronized String getProgress() {
		if(totalProcess == 0 || now == 0){
    		return "0%";
    	}
    	double denominator = totalProcess * 1.0;
        double numerator = now * 1.0;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        return nf.format(numerator / denominator);
	}
	
	@Override
	public void run() {
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
		TransactionStatus status = dataSourceTransactionManager.getTransaction(def); // 获得事务状态
		try (Workbook workbook = filePath.endsWith("xlsx")?new XSSFWorkbook(new FileInputStream(filePath)):new HSSFWorkbook(new FileInputStream(filePath))){
			int importNum = 0;
			Sheet sheet = workbook.getSheetAt(0);
			this.total = sheet.getPhysicalNumberOfRows() - 1;
			if (this.total > 200000) {
				throw new Exception();
			}
			//上传上限是百分之九十
			this.totalProcess = this.total * 10 / 9;
			Iterator<Row> i = sheet.rowIterator();
			List<Row> l = new ArrayList<Row>(150);
			Row title = i.next();
			int j = 0;
			while(i.hasNext()){
				Row r = i.next();
				if(r.getCell(0) == null)
					continue;
				int cellType = r.getCell(0).getCellType();//0为数字，1为字符串，2为公式，3为空白
				String phone = cellType==3?"":cellType==1?PhoneUtil.getNumberPhone(r.getCell(0).getStringCellValue()):String.valueOf((long)r.getCell(0).getNumericCellValue());
				if(StringUtils.isNotBlank(phone) && !("0".equals(phone)) && phone.length() < 20) {
					l.add(r);
					if(++j == 150) {
						importNum += CollectionUtil.sum(batchUpdate(this.uuid, "new_data_batch_" + tableName, l, title));
						j=0;
						l.clear();
						brokerService.sendToUser("/user", this.uploader, new JSONObject().put("type", "data-upload-progress").put("uuid", this.uuid).put("progress", this.getProgress()).toString());
						brokerService.sendToUsers("/user", new JSONObject().put("type", "data-upload-progress").put("uuid", this.uuid).put("progress", this.getProgress()).toString(), this.relatedUsers);
					}
				}
				now++;
			}
			importNum += CollectionUtil.sum(batchUpdate(this.uuid, "new_data_batch_" + tableName, l, title));
			int deleteCount = distinctAndInsert(tableName);
			this.jdbcTemplate.update("update new_data_batch set data_count=? where data_table = ?", importNum - deleteCount, tableName);

			dataSourceTransactionManager.commit(status);
			
			brokerService.sendToUser("/user", this.uploader, new JSONObject().put("type", "data-upload").put("total", this.total).put("count", String.valueOf(importNum - deleteCount)).toString());
			brokerService.sendToUsers("/user", new JSONObject().put("type", "data-upload-progress").put("uuid", this.uuid).put("progress", "100%").toString(), this.relatedUsers);
			
		} catch (Exception e) {	//异常时删除
			
//			String uuid = jdbcTemplate.queryForObject("select uuid from new_data_batch where data_table = ?", String.class, tableName);
			dataSourceTransactionManager.rollback(status);
			jdbcTemplate.update("drop table if exists new_data_batch_" + tableName);
			jdbcTemplate.update("delete from new_data_batch where data_table = ?", tableName);
			jdbcTemplate.update("DELETE from new_data_batch_department_link where data_batch_uuid=?", tableName);
			try {
				brokerService.sendToUser("/user", this.uploader, new JSONObject().put("type", "data-upload").put("error", "文件格式错误").toString());
			} catch (JSONException | IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	//批量更新方法
	public int[] batchUpdate(final String uuid, String table, final List<Row> list, final Row title) {
		StringBuilder batchSql = new StringBuilder();
		batchSql.append("INSERT INTO ").append(table).append("(uuid,batch_uuid,phone_number,json) VALUES (?,?,?,?)");
		
		int[] ret = jdbcTemplate.batchUpdate(batchSql.toString(), new BatchPreparedStatementSetter() {
			
			public int getBatchSize() {
                return list.size();
            }
			
            public void setValues(PreparedStatement ps, int i)throws SQLException {

            	Row l = list.get(i);
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, uuid);
                ps.setString(3, l.getCell(0)==null?"":l.getCell(0).getCellType()!=0?PhoneUtil.getNumberPhone(l.getCell(0).getStringCellValue()):String.valueOf((long)l.getCell(0).getNumericCellValue()));
                
                JSONObject json = new JSONObject();
                for(int j=1;j<l.getLastCellNum();j++)
                	json.put(title.getCell(j).getStringCellValue(), l.getCell(j)==null?"":l.getCell(j).getCellType()!=0?l.getCell(j).getStringCellValue():String.valueOf((long)l.getCell(j).getNumericCellValue()));
                ps.setString(4, json.toString());

            }
            
		});
		return ret; 
	}
	
	//去重方法
	public int distinctAndInsert(String tableName) {
		int count = 0;
		try {
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					if (ExcelTask.this.now + 2 * ExcelTask.this.totalProcess / 100 < ExcelTask.this.totalProcess) {
						ExcelTask.this.now += ExcelTask.this.totalProcess / 100;
						try {
							ExcelTask.this.brokerService.sendToUser("/user", ExcelTask.this.uploader, 
									new JSONObject().put("type", "data-upload-progress").put("uuid", ExcelTask.this.uuid).
									put("progress", ExcelTask.this.getProgress()).toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}, 0l, 3000l);
			
			StringBuilder distinctSql = new StringBuilder();
			distinctSql.append("delete b from ").append("new_data_batch_" + tableName).
				append(" b ,(select *,min(uuid) as id from ").append("new_data_batch_" + tableName).
				append(" group by phone_number having count(phone_number) > 1) as d where b.uuid>d.id and b.phone_number = d.phone_number ");
			count = jdbcTemplate.update(distinctSql.toString());
			String lastSql = "DELETE b FROM new_data_batch_" + tableName + " b WHERE EXISTS (SELECT phone_number FROM new_data_phone_resource where phone_number=b.phone_number)";
			count += jdbcTemplate.update(lastSql);
			
			String insertSql = "insert into new_data_phone_resource(phone_number,batch_uuid) (select phone_number,batch_uuid from new_data_batch_" + tableName + ")";
			this.jdbcTemplate.update(insertSql);
			timer.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}

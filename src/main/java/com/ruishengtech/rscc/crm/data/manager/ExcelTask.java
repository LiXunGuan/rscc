package com.ruishengtech.rscc.crm.data.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.util.CollectionUtil;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;

public class ExcelTask implements Runnable {

	private int total = 0;
	
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
	
	public String getProgress() {
		if(total == 0 || now == 0){
    		return "0%";
    	}
    	double denominator = total * 1.0;
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
		
		try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath))){
			int importNum = 0;
			Sheet sheet = workbook.getSheetAt(0);
			this.total = sheet.getPhysicalNumberOfRows() - 1;
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
				if(StringUtils.isNotBlank(phone) && !("0".equals(phone))) {
					l.add(r);
					if(++j == 150) {
						importNum += CollectionUtil.sum(batchUpdate("data_item_" + tableName, l, title));
						j=0;
						l.clear();
						brokerService.sendToUser("/user", this.uploader, new JSONObject().put("type", "data-upload-progress").put("uuid", this.uuid).put("progress", this.getProgress()).toString());
						brokerService.sendToUsers("/user", new JSONObject().put("type", "data-upload-progress").put("uuid", this.uuid).put("progress", this.getProgress()).toString(), this.relatedUsers);
					}
				}
				now++;
			}
			importNum += CollectionUtil.sum(batchUpdate("data_item_" + tableName, l, title));
			int deleteCount = distinct(tableName);
			this.jdbcTemplate.update("update data_container set data_count=? where data_table = ?", importNum - deleteCount, tableName);
			brokerService.sendToUser("/user", this.uploader, new JSONObject().put("type", "data-upload").put("total", this.total).put("count", String.valueOf(importNum - deleteCount)).toString());
			brokerService.sendToUsers("/user", new JSONObject().put("type", "data-upload-progress").put("uuid", this.uuid).put("progress", "100%").toString(), this.relatedUsers);
			
			dataSourceTransactionManager.commit(status);
			
		} catch (Exception e) {	//异常时删除
			
			String uuid = jdbcTemplate.queryForObject("select uuid from data_container where data_table = ?", String.class, tableName);
			jdbcTemplate.update("drop table if exists data_item_" + tableName);
			jdbcTemplate.update("delete from data_container where data_table = ?", tableName);
			jdbcTemplate.update("DELETE from data_container_department_link where datacontainer_uuid=?", uuid);
			dataSourceTransactionManager.rollback(status);
			try {
				brokerService.sendToUser("/user", this.uploader, new JSONObject().put("type", "data-upload").put("error", "文件格式错误,请将文件内容放入Microsoft Office Excel，保存后再次尝试！").toString());
			} catch (JSONException | IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	//批量更新方法
	public int[] batchUpdate(String table, final List<Row> list, final Row title) {
		StringBuilder batchSql = new StringBuilder();
		batchSql.append("INSERT INTO ").append(table).append("(uuid,item_name,item_phone,item_address,item_json) VALUES (?,?,?,?,?)");
		int[] ret = jdbcTemplate.batchUpdate(batchSql.toString(), new BatchPreparedStatementSetter() {
			public int getBatchSize() {
                return list.size();
            }
            public void setValues(PreparedStatement ps, int i)throws SQLException {
            	Row l = list.get(i);
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, l.getCell(1)==null?"":l.getCell(1).getCellType()!=0?l.getCell(1).getStringCellValue():String.valueOf((long)l.getCell(1).getNumericCellValue()));
                ps.setString(3, l.getCell(0)==null?"":l.getCell(0).getCellType()!=0?PhoneUtil.getNumberPhone(l.getCell(0).getStringCellValue()):String.valueOf((long)l.getCell(0).getNumericCellValue()));
                ps.setString(4, l.getCell(2)==null?"":l.getCell(2).getCellType()!=0?l.getCell(2).getStringCellValue():String.valueOf((long)l.getCell(2).getNumericCellValue()));
                JSONObject json = new JSONObject();
                for(int j=3;j<l.getLastCellNum();j++) {
                	if(title.getCell(j) == null)
                		continue;
                	json.put(title.getCell(j).getStringCellValue(), l.getCell(j)==null?"":l.getCell(j).getCellType()!=0?l.getCell(j).getStringCellValue():String.valueOf((long)l.getCell(j).getNumericCellValue()));
                }
                ps.setString(5, json.toString());
            }
		});
		return ret; 
	}
	
	//去重方法
	public int distinct(String tableName) {
		int count = 0;
		try {
			StringBuilder distinctSql = new StringBuilder();
			distinctSql.append("delete b from ").append("data_item_" + tableName).
				append(" b ,(select *,min(uuid) as id from ").append("data_item_" + tableName).
				append(" group by item_phone having count(item_phone) > 1) as d where b.uuid>d.id and b.item_phone = d.item_phone and b.item_owner is null");
			count = jdbcTemplate.update(distinctSql.toString());
			if ("true".equals(SysConfigManager.getInstance().getDataMap().get("sys.data.distinct").getSysVal())) {	//批次间去重
				DataContainerService dataContainerService = ApplicationHelper.getApplicationContext().getBean(DataContainerService.class);
				List<DataContainer> list = dataContainerService.getDatas();
				for (DataContainer dataContainer : list) {
					if(!tableName.equals(dataContainer.getDataTable())){
						String lastSql = "DELETE b FROM data_item_" + tableName + " b WHERE EXISTS (SELECT item_phone FROM data_item_" + dataContainer.getDataTable() + " where item_phone=b.item_phone)";
						count += jdbcTemplate.update(lastSql);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}

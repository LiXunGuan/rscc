package com.ruishengtech.rscc.crm.data.service.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.rscc.crm.data.manager.ExcelImporter;
import com.ruishengtech.rscc.crm.data.manager.ExcelTask;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.SynchronizeService;
import com.ruishengtech.rscc.crm.data.solution.ItemSolution;
import com.ruishengtech.rscc.crm.data.util.CollectionUtil;

@Service
@Transactional
public class DataItemServiceImp extends BaseService implements DataItemService {
	
	@Autowired
	private SynchronizeService synchronizedService;
	
	public List<DataItem> getItems(String tableName) {
		List<DataItem> list = getBeanListWithTable("data_item_" + tableName, DataItem.class, "");
		return list;
	}

	public DataItem getByUuid(String tableName, UUID uuid) {
		
		List<DataItem> list = getBeanListWithTable("data_item_" + tableName, DataItem.class, "and uuid = ?", uuid.toString());
		
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public DataItem getByName(String tableName, String itemName) {
		
		List<DataItem> list = getBeanListWithTable("data_item_" + tableName, DataItem.class, "and item_name = ?", itemName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(String tableName, DataItem d) {
		try {
			super.save("data_item_" + tableName, d);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void save(String tableName, DataItem d, String[] excludeFieldName) {
		super.save("data_item_" + tableName, d, excludeFieldName);
	}
		
	public boolean deleteById(String tableName, UUID uuid) {
		try {
			DataItem temp = getByUuid(tableName, uuid);
			if(StringUtils.isNotBlank(temp.getItemOwner()))
				super.deleteById("data_task_" + temp.getItemOwner(), DataTask.class, uuid);
			super.deleteById("data_item_" + tableName, DataItem.class, uuid);
			synchronizedService.updateContainer(tableName);
			synchronizedService.updateProject(temp.getItemOwner());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public int getCount(String tableName) {
		return getTemplate().queryForObject("select count(*) from data_item_" + tableName, Integer.class);
	}
	
	public int getUnallocateCount(String tableName) {
		return getTemplate().queryForObject("select count(*) from data_item_" + tableName + " where item_owner is null ", Integer.class);
	}
	
	public PageResult<DataItem> queryPage(ICondition condition) {
		return super.queryPage(new ItemSolution(), condition, DataItem.class);
	}
	
	/*public void importExcel(File excel) throws FileNotFoundException, IOException{
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excel));
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> i = sheet.rowIterator();
		while(i.hasNext()){
			super.save("data_item_" + "tableName", rowToBean(i.next()));
		}
	}*/

	/*private DataItem rowToBean(Row row) {
		DataItem d = new DataItem();
		d.setItemName(row.getCell(0).getStringCellValue());
		d.setItemPhone(row.getCell(1).getStringCellValue());
		d.setItemDescribe(row.getCell(2).getStringCellValue());
		d.setItemAddress(row.getCell(3).getStringCellValue());
		return d;
	}
*/
	@Override
	public int importExcelToTable(File excel, String tableName)
			throws Exception {
		XSSFWorkbook workbook = null ;
		int count = 0;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(excel));
			Sheet sheet = workbook.getSheetAt(0);
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
						count += CollectionUtil.sum(batchUpdate("data_item_" + tableName, l, title));
						j=0;
						l.clear();
					}
				}
			}
			count += CollectionUtil.sum(batchUpdate("data_item_" + tableName, l, title));
		} catch (Exception e) {
			throw new Exception("文件格式错误");
		} finally {
			workbook.close();
		}
		return count;
	}
	
	public int[] batchUpdate(String tableName, final List<Row> list, final Row title) throws IOException {
		StringBuilder batchSql = new StringBuilder();
		batchSql.append("INSERT INTO ").append(tableName).append("(uuid,item_name,item_phone,item_address,item_json) VALUES (?,?,?,?,?)");
		try {
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
	                for(int j=3;j<l.getLastCellNum();j++)
	                	json.put(title.getCell(j).getStringCellValue(), l.getCell(j)==null?"":l.getCell(j).getCellType()!=0?l.getCell(j).getStringCellValue():String.valueOf((long)l.getCell(j).getNumericCellValue()));
	                ps.setString(5, json.toString());
	            }
			});
			return ret; 
		} catch (Exception e) {
			throw new IOException();
		}
	}

	//逻辑可以修改为批量的
	public void collection(String dataTable, Integer collection, String[] users) {
		List<DataItem> list = new ArrayList<DataItem>();
		StringBuilder whereSql = new StringBuilder(" and item_owner = ? ");
		switch (collection){
		case 0 :{
			whereSql.append(" and call_times = 0 ");
			break;
		}
		case 1 :{
			whereSql.append(" and call_times > 0 ");
			break;
		}
		case 2:{
		}
		}
		for(String u:users)
			list.addAll(getBeanListWithTable("data_item_" + dataTable, DataItem.class, whereSql.toString(), u));
		for(DataItem d:list){
			jdbcTemplate.update("delete from data_task_" + d.getItemOwner() + " where uuid=?", d.getUid());
			d.setItemOwner(null);
			d.setCallTimes(0);
			d.setAllocateTime(null);
			update("data_item_" + dataTable,d);
		}
		synchronizedService.updateContainer(dataTable);
		for (String u : users)
			synchronizedService.updateProject(u);
	}
	
	public void collection(String dataTable, Integer collection) {
		List<String> list = jdbcTemplate.queryForList("select item_owner from data_item_" + dataTable + " where item_owner is not null group by item_owner", String.class);
		StringBuilder whereSql = new StringBuilder(" where data_source = ? ");
		switch (collection){
		case 0 :{
			whereSql.append(" and call_times = 0 ");
			break;
		}
		case 1 :{
			whereSql.append(" and call_times > 0 ");
			break;
		}
		case 2:{
		}
		}
		for (String u : list) {
			jdbcTemplate.update("delete from data_task_" + u + whereSql.toString(), dataTable);
			synchronizedService.updateProject(u);
		}
	}

	@Override
	public boolean batDelete(String dataTable, String[] uuids) {
		for (String u:uuids)
			this.deleteById(dataTable, UUID.UUIDFromString(u));
		return true;
	}

	@Override
	public List<String> getItems(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		ItemSolution solution = new ItemSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	@Override
	public void importExcelToTable(String uuid, String filePath, String uploader, String tableName, Collection<String> relatedUsers) {
		ExcelTask task = new ExcelTask(uuid, filePath, uploader, tableName, relatedUsers);
		ExcelImporter.getInstance().exe(task);
	}
	
	/*@Override
	public int changeImportFlag(String tableName, String[] items) {
		for(String i:items){
			String sql = "UPDATE data_item_" + tableName + " SET item_stat='1' WHERE uuid=?";
			jdbcTemplate.update(sql, i);
		}
		int count = jdbcTemplate.queryForObject("select count(*) from data_item_" + tableName + " where item_stat='1'",Integer.class);
		return count;
	}*/
}

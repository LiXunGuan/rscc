package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.rscc.crm.data.condition.TaskDesignCondition;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.service.TaskDesignService;
import com.ruishengtech.rscc.crm.data.solution.TaskDesignSolution;

@Service
@Transactional
//public class TaskDesignServiceImp implements TaskDesignService{
public class TaskDesignServiceImp extends DiyTableService implements TaskDesignService{
	
	@Autowired
	private TableService tableService;
	
	public TaskDesignServiceImp() {
	}
	
	public void save(ColumnDesign cd) throws Exception {
		cd.setTableName("task");
		super.addColumnDesin(cd);
		alterAdd(cd);
	}
	
	public void save(String tableName, Map<String, String[]> str) {
		super.save("task", "data_task_" + tableName, str);
	}
	
	public void update(String tableName, Map<String, String[]> str) {
		super.update("task", "data_task_" + tableName, str);
	}
	
	public void deleteByUuid(UUID uuid) {
		super.deleteById(ColumnDesign.class, uuid);
	}
	
	public void createTable(String tableName) {
		Map<String,ColumnDesign> m = super.getDiyColumns("task");
		tableService.createTable(DataItem.class, "data_task_" + tableName, m!=null?m.values():null);
	}
	
	public void deleteTable(String tableName) {
		tableService.deleteTable("data_task_" + tableName);
	}
	
	public PageResult<ColumnDesign> queryPage(TaskDesignCondition condition){
		return super.queryPage(new TaskDesignSolution(), condition, ColumnDesign.class);
	}
	
//	public boolean alterAdd(ColumnDesign customerDesign) {
//		List<String> l = jdbcTemplate.queryForList("select task_table from project_task_result_link", String.class);
//		try {
//			for(String s:l)
//				super.alterAdd(customerDesign, "data_task_" + s);
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
	public void alterAdd(ColumnDesign customerDesign) throws Exception {
		List<String> l = jdbcTemplate.queryForList("select task_table from project_task_result_link", String.class);
		for(String s:l)
			super.alterAdd(customerDesign, "data_task_" + s);
	}
	
	public String getColumnName(){
		return super.getColumnName();
	}
	
	
	public void deleteColumn(String id, String tableName) {
		ColumnDesign cd = super.delete(id, tableName);
		List<String> l = jdbcTemplate.queryForList("select task_table from project_task_result_link", String.class);
		for(String s:l)
			super.dropColumn(cd, "data_task_" + s);
	}

	@Override
	public Map<String,ColumnDesign> getTableDef(String tableName) {
		return super.getDiyColumns(tableName);
	}
	
	public Map<String,String> getColumns() {
		
		HashMap<String,String> map = new HashMap<String,String>();
		Map<String,ColumnDesign> ret = super.getDiyColumns("task");
		for (ColumnDesign columnDesign : ret.values()) {
			if(columnDesign.getAllowShow().equals(ColumnDesign.ALLOWSHOW)){ //查询允许显示的
				map.put(columnDesign.getColumnNameDB(),columnDesign.getColumnName());
			}
		}
		return map;
	}

	@Override
	public Map<String, String> getSelects() {
		
		HashMap<String,String> map = new HashMap<String,String>();
		Map<String,ColumnDesign> ret = super.getDiyColumns("task");
		for (ColumnDesign columnDesign : ret.values()) {
			if(columnDesign.getAllowShow().equals(ColumnDesign.ALLOWSELECT)){ //查询允许显示的
				map.put(columnDesign.getColumnNameDB(),columnDesign.getColumnName());
			}
		}
		return map;
	}
	
}

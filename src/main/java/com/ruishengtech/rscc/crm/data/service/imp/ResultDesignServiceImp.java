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
import com.ruishengtech.rscc.crm.data.condition.ResultDesignCondition;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.service.ResultDesignService;
import com.ruishengtech.rscc.crm.data.solution.ResultDesignSolution;

@Service
@Transactional
public class ResultDesignServiceImp extends DiyTableService implements ResultDesignService{
	
	@Autowired
	private TableService tableService;
	
	public ResultDesignServiceImp() {
		//不能有操作利用bean操作数据库的语句，因为这里操作tableDefine还没有放入数据库。
		//super.addTableInfo("result");
	}
	
	public void save(ColumnDesign cd) throws Exception {
		cd.setTableName("result");
		super.addColumnDesin(cd);
		alterAdd(cd);
	}
	
	public void deleteByUuid(UUID uuid) {
		super.deleteById(ColumnDesign.class, uuid);
	}
	
	public void createTable(String tableName) {
		Map<String,ColumnDesign> m = super.getDiyColumns("result");
		tableService.createTable(DataItem.class, "data_result_" + tableName, m!=null?m.values():null);
	}
	
	public void deleteTable(String tableName) {
		tableService.deleteTable("data_result_" + tableName);
	}
	
	public void alterAdd(ColumnDesign customerDesign) throws Exception{
		List<String> l = jdbcTemplate.queryForList("select result_table from project_task_result_link", String.class);
		for(String s:l)
			super.alterAdd(customerDesign, "data_result_" + s);
	}

	@Override
	public void save(String tableName, Map<String, String[]> str) {
		super.save("result", "data_result_" + tableName, str);
	}

	@Override
	public void update(String tableName, Map<String, String[]> str) {
		super.update("result", "data_result_" + tableName, str);
	}

	@Override
	public PageResult<ColumnDesign> queryPage(ResultDesignCondition condition) {
		return super.queryPage(new ResultDesignSolution(), condition, ColumnDesign.class);
	}

	@Override
	public void deleteColumn(String id, String tableName) {
		ColumnDesign cd = super.delete(id, tableName);
		List<String> l = jdbcTemplate.queryForList("select result_table from project_task_result_link", String.class);
		for(String s:l)
			super.dropColumn(cd, "data_result_" + s);
	}

	@Override
	public Map<String, ColumnDesign> getTableDef(String tableName) {
		return super.getDiyColumns(tableName);
	}

	@Override
	public Map<String, String> getColumns() {
		HashMap<String,String> map = new HashMap<String,String>();
		Map<String,ColumnDesign> ret = super.getDiyColumns("result");
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
		Map<String,ColumnDesign> ret = super.getDiyColumns("result");
		for (ColumnDesign columnDesign : ret.values()) {
			if(columnDesign.getAllowShow().equals(ColumnDesign.ALLOWSELECT)){ //查询允许显示的
				map.put(columnDesign.getColumnNameDB(),columnDesign.getColumnName());
			}
		}
		return map;
	}
	
	public String getColumnName(){
		return super.getColumnName();
	}
}

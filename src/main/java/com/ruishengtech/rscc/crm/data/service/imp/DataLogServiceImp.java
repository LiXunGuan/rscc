package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.model.DataLog;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.DataLogService;
import com.ruishengtech.rscc.crm.data.solution.DataLogSolution;

@Service
@Transactional
public class DataLogServiceImp extends BaseService implements DataLogService {
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private DataItemService dataItemService;
	
	public List<DataLog> getDatas() {
		List<DataLog> list = getBeanList(DataLog.class,"");
		return list;
	}

	public DataLog getByUuid(UUID uuid) {
		return super.getByUuid(DataLog.class, uuid);
	}
	
	public DataLog getDataLogByName(String dataName) {
		
		List<DataLog> list = getBeanList(DataLog.class, "and data_name = ?", dataName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public DataLog getDataLogByTable(String dataTable) {
		
		List<DataLog> list = getBeanList(DataLog.class, "and data_table = ?", dataTable);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(DataLog d) {
		try {
			d.setDataCreateTime(new Date());
			super.save(d);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean save(DataLog d, String[] excludeFieldName) {
		try {
			d.setDataCreateTime(new Date());
			super.save(d, excludeFieldName);
			tableService.createTable(DataItem.class, d.getDataTable());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean update(DataLog d) {
		try {
	        super.update(d, new String[]{"dataName","dataCreateTime"});
			tableService.createTable(DataItem.class, "data_item_" + d.getDataTable());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void update(DataLog p, String[] excludeFieldName) {
		 super.update(p, excludeFieldName);
	}
	
	public void update(String tableName) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_log SET data_count=? WHERE data_table=?");
		params.add(dataItemService.getCount(tableName));
		params.add(tableName);
		getTemplate().update(updateSql.toString(), params.toArray());
	}
	
	/*public void update(String tableName, String distinctFlag) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_log SET data_count=? , distinct_flag=? WHERE data_table=?");
		params.add(dataItemService.getCount(tableName));
		params.add(distinctFlag);
		params.add(tableName);
	}
	
	public void update(String tableName, String importFlag) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_log SET data_count=? , import_flag=? WHERE data_table=?");
		params.add(dataItemService.getCount(tableName));
		params.add(importFlag);
		params.add(tableName);
	}*/
	
	/*public void updateImportCount(String dataName, int count) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_log SET import_count=? WHERE data_table=?");
		params.add(count);
		params.add(dataName);
		getTemplate().update(updateSql.toString(), params.toArray());
	}*/
	
	public boolean deleteById(UUID uuid) {
		try {
			DataLog temp = getByUuid(uuid);
			super.deleteById(DataLog.class, uuid);
			tableService.deleteTable("data_item_" + temp.getDataTable());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean deleteByTable(String dataTable) {
		try {
			StringBuilder deleteSql = new StringBuilder();
			List<Object> params = new ArrayList<Object>();
			deleteSql.append(" DELETE FROM data_log");
			deleteSql.append(" where data_table=? ");
			params.add(dataTable);
			getTemplate().update(deleteSql.toString(), params.toArray());
			tableService.deleteTable(dataTable);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//批次内去重
	public boolean distinct(DataLog d) {
		try {
			StringBuilder distinctSql = new StringBuilder();
			distinctSql.append("delete b from ").append("data_item_" + d.getDataTable()).
				append(" b ,(select *,min(uuid) as id from ").append("data_item_" + d.getDataTable()).
				append(" group by item_phone having count(item_phone) > 1) as d where b.uuid>d.id and b.item_phone = d.item_phone");
			int count = getTemplate().update(distinctSql.toString());
			d.setDistinctFlag("1");
			d.setDataCount(d.getDataCount() - count);
			this.update(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public PageResult<DataLog> queryPage(ICondition condition) {
		return super.queryPage(new DataLogSolution(), condition, DataLog.class);
	}

	@Override
	public void updateImportFlag(String uuid) {
		getTemplate().update("update data_log set import_flag = '是' where uuid=?", uuid);
	}

	@Override
	public boolean batDelete(String[] uuids) {
		for(String u:uuids)
			this.deleteById(UUID.UUIDFromString(u));
		return true;
	}
	
}

package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.data.service.SynchronizeService;

@Service
@Transactional
public class SynchronizeServiceImp extends BaseService implements SynchronizeService {

	public void updateProject(String uuid) {
		if (StringUtils.isBlank(uuid))
			return;
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_project SET data_count=?,complete_count=? WHERE uuid=?");
		params.add(jdbcTemplate.queryForObject("select count(*) from data_task_" + uuid, Integer.class));
		params.add(jdbcTemplate.queryForObject("select count(*) from data_task_" + uuid + " where call_times > 0 ", Integer.class));
		params.add(uuid);
		getTemplate().update(updateSql.toString(), params.toArray());
	}
	
	public void updateContainer(String tableName) {
		if (StringUtils.isBlank(tableName))
			return;
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_container SET data_count=?,allocate_count=? WHERE data_table=?");
		params.add(getTemplate().queryForObject("select count(*) from data_item_" + tableName, Integer.class));
		params.add(getTemplate().queryForObject("select count(*) from data_item_" + tableName + " where item_owner is not null ", Integer.class));
		params.add(tableName);
		getTemplate().update(updateSql.toString(), params.toArray());
	}
	
}

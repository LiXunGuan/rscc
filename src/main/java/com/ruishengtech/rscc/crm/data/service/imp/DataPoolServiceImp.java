package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.CollectionUtil;
import com.ruishengtech.rscc.crm.data.model.DataPool;
import com.ruishengtech.rscc.crm.data.service.DataPoolService;
import com.ruishengtech.rscc.crm.data.solution.DataPoolSolution;

@Service
@Transactional
public class DataPoolServiceImp extends BaseService implements DataPoolService {
	public List<DataPool> getAllTaskPool() {
		return getBeanList(DataPool.class,"");
	}
	
	public boolean save(DataPool d) {
		try {
			super.save(d);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void save(DataPool d, String[] excludeFieldName) {
		super.save(d, excludeFieldName);
	}
	
	public boolean update(DataPool d) {
		try {
	        super.update(d);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void update(DataPool d, String[] excludeFieldName) {
		 super.update(d, excludeFieldName);
	}
	
	public boolean deleteById(UUID uuid) {
		try {
			super.deleteById(DataPool.class, uuid);
			jdbcTemplate.update("DELETE FROM data_data_pool WHERE uuid=?", uuid.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public DataPool getByUuid(UUID uuid) {
		return super.getByUuid(DataPool.class, uuid);
	}
	
	public PageResult<DataPool> queryPage(ICondition condition) {
		return super.queryPage(new DataPoolSolution(), condition, DataPool.class);
	}
	
	public DataPool getTaskPoolByName(String datarangeName) {
		
		List<DataPool> list = getBeanList(DataPool.class, "and pool_name = ?", datarangeName);
		
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}

	public List<DataPool> getByList(List<UUID> l) {
		ArrayList<DataPool> list = new ArrayList<DataPool>();
		for(UUID u:l)
			list.add(getByUuid(u));
		return list;
	}
	
	public boolean batDelete(String[] uuids) {
		for(String u:uuids)
			this.deleteById(UUID.UUIDFromString(u));
		return true;
	}
	
	
	@Override
	public int batSave(String[] poolName, String[] poolDescribe){
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for(int i=0;i<poolName.length;i++){
			if(StringUtils.isNotBlank(poolName[i]))
				list.add(new String[]{UUID.randomUUID().toString(),poolName[i], poolDescribe[i]});
		}
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO data_data_pool (uuid, pool_name, pool_describe, pool_type) VALUES (?, ?, ?, '1')", list);
		return CollectionUtil.sum(nums);
	}
}

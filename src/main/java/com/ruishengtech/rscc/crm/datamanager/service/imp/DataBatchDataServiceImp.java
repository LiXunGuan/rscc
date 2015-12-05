package com.ruishengtech.rscc.crm.datamanager.service.imp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.data.manager.ExcelImporter;
import com.ruishengtech.rscc.crm.data.manager.ExcelTask;
import com.ruishengtech.rscc.crm.data.solution.ItemSolution;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchDataService;
import com.ruishengtech.rscc.crm.datamanager.solution.DataBatchDataSolution;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.User;

@Service
@Transactional
public class DataBatchDataServiceImp extends BaseService implements DataBatchDataService{

	public List<DataBatchData> getItems(String tableName) {
		List<DataBatchData> list = getBeanListWithTable("new_data_batch_" + tableName, DataBatchData.class, "");
		return list;
	}

	public DataBatchData getByUuid(String tableName, String uuid) {
		
		List<DataBatchData> list = getBeanListWithTable("new_data_batch_" + tableName, DataBatchData.class, "and uuid = ?", uuid);
		
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public DataBatchData getByPhone(String tableName, String phone) {
		
		List<DataBatchData> list = getBeanListWithTable("new_data_batch_" + tableName, DataBatchData.class, "and phone_number = ?", phone);
		
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public DataBatchData getByName(String tableName, String itemName) {
		
		List<DataBatchData> list = getBeanListWithTable("new_data_batch_" + tableName, DataBatchData.class, "and item_name = ?", itemName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(String tableName, DataBatchData d) {
		super.save("new_data_batch_" + tableName, d);
		return true;
	}
	
	public void save(String tableName, DataBatchData d, String[] excludeFieldName) {
		super.save("new_data_batch_" + tableName, d, excludeFieldName);
	}
		
	public boolean deleteById(String tableName, UUID uuid) {
//			DataBatchData temp = getByUuid(tableName, uuid);
//			if(StringUtils.isNotBlank(temp.getItemOwner()))
//				super.deleteById("data_task_" + temp.getItemOwner(), DataTask.class, uuid);
//			super.deleteById("new_data_batch_" + tableName, DataItem.class, uuid);
//			synchronizedService.updateContainer(tableName);
//			synchronizedService.updateProject(temp.getItemOwner());
		return true;
	}
	
	public int getCount(String tableName) {
		return getTemplate().queryForObject("select count(*) from new_data_batch_" + tableName, Integer.class);
	}
	
	public int getUnallocateCount(String tableName) {
		return getTemplate().queryForObject("select count(*) from new_data_batch_" + tableName + " where item_owner is null ", Integer.class);
	}
	
	public PageResult<DataBatchData> queryPage(ICondition condition) {
		return super.queryPage(new DataBatchDataSolution(), condition, DataBatchData.class);
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

	@Override
	public void deleteDataByUuid(String uuid, String source) {
		jdbcTemplate.update(" delete from new_data_batch_"+ source +" where uuid = ? ", uuid);
	}

	@Override
	public void deleteDataByPhoneNumber(String phonenumber, String batchuuid) {
		jdbcTemplate.update(" delete from new_data_phone_resource where phone_number = ? and batch_uuid = ? ", phonenumber, batchuuid);
	}

	@Override
	public void updateDataCount(Integer num, String source) {
		jdbcTemplate.update(" update new_data_batch set data_count = ? where uuid = ?  ", num, source);
	}
	
	public DataBatchData getPhoneData(String phone) {
		String batchUuid;
		try {
			batchUuid = jdbcTemplate.queryForObject("select batch_uuid from new_data_phone_resource where phone_number=?", String.class, phone);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return getByPhone(batchUuid, phone);
	}
	
	/**
	 * 添加的时候 更新该资源的拥有者
	 * @param phone
	 * @return
	 */
	@Override
	public Integer updateDataBatchData(HttpServletRequest request, String phone,String cstmUUid) {

		//号码不存在 直接返回
		if(!StringUtils.isNotBlank(phone)){
			return -1;
		}
		
		User user = SessionUtil.getCurrentUser(request);

		//查询是否存在资源库中
		String batchUuid = null;
		try {
			
			batchUuid = jdbcTemplate.queryForObject("SELECT batch_uuid FROM new_data_phone_resource WHERE phone_number= ? ", String.class, phone);
			
		} catch (EmptyResultDataAccessException e) {
			
			batchUuid = "";
		}

		//如果存在批次ID 资源表中有   更新主资源库 更新批次库数据
		if(StringUtils.isNotBlank(batchUuid)){
			
			DataBatchData batchData = getByPhone(batchUuid, phone);
			
			jdbcTemplate.update(" UPDATE `new_data_phone_resource` SET "
					+ " `is_abandon`='0', `abandon_timestamp`=NULL, `is_blacklist`='0', `blacklist_timestamp`=NULL, `is_frozen`='0', "
					+ " `frozen_timestamp`=NULL, `customer_uuid`=? WHERE (`phone_number`=?)",cstmUUid,phone);
			
			//批次表中存在数据 更新批次表数据
			if (null != batchData) {
				
				//如果分配给了部门 坐席
				if(StringUtils.isNoneBlank(batchData.getOwnDepartment(),batchData.getOwnUser())){

					//删除部门分配到人的数据
					jdbcTemplate.update("DELETE FROM `new_data_department_user_"+batchData.getOwnDepartment()+"` WHERE phone_number = ? ",phone);
				}
				
				//分配给了部门						
				if(StringUtils.isNotBlank(batchData.getOwnDepartment()) && null==batchData.getOwnUser()){
					
					//删除分配到部门的数据
					jdbcTemplate.update(" DELETE FROM `new_data_department_"+batchData.getOwnDepartment()+"` WHERE phone_number = ? ",phone);
				}
				
				//更新拥有者
				return jdbcTemplate.update("UPDATE `new_data_batch_"+ batchUuid +"` SET `own_user` = ?, `customer_uuid`=? "
						+ "WHERE `uuid` = ? ",user.getUid(),cstmUUid, batchData.getUid());
			}
			
			
			
			
		}else{
			
			try {
				
				batchUuid = jdbcTemplate.queryForObject("SELECT phone_number FROM new_data_phone_resource WHERE phone_number= ? ", String.class, phone);
				
			} catch (Exception e) {
				batchUuid = "";
			}
			
			if(StringUtils.isNotBlank(batchUuid)){
				
				return jdbcTemplate.update("UPDATE `new_data_phone_resource` SET "
						+ " `is_abandon`='0', `abandon_timestamp`=NULL, `is_blacklist`='0', `blacklist_timestamp`=NULL, `is_frozen`='0', "
						+ " `frozen_timestamp`=NULL, `customer_uuid`=? WHERE (`phone_number`=?)",cstmUUid,phone);
			}else{
				
				String uuid = UUID.randomUUID().toString();
				//默认批次中插入数据
				jdbcTemplate.update("INSERT INTO new_data_batch_ (uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp," +
						"own_user,own_user_timestamp,call_count,last_call_department,last_call_user,last_call_result,last_call_time,customer_uuid) VALUES "
						+ "(?,?,?,?,?,now(),?,now(),?,?,?,?,now(),?)", 
						uuid, "", phone, "{}", user.getDepartment(), user.getUid(), 1, user.getDepartment(), user.getUid(), "1",cstmUUid);
				
				
				//插入新数据
				return jdbcTemplate.update("INSERT INTO `new_data_phone_resource` (`phone_number`,"
						+ " `batch_uuid`, `is_abandon`, `abandon_timestamp`, `is_blacklist`,"
						+ " `blacklist_timestamp`, `is_frozen`, `frozen_timestamp`, "
						+ "`customer_uuid`) VALUES"
						+ " (?, '', '0', NULL, '0', NULL, '0', NULL, ?)",phone,cstmUUid);
			}
		}
		
		return -1;
		
	}
	
	

}

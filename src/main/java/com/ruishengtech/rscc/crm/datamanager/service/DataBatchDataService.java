package com.ruishengtech.rscc.crm.datamanager.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;

public interface DataBatchDataService {

	public List<DataBatchData> getItems(String tableName);
	
	public List<String> getItems(ICondition condition);

	public DataBatchData getByUuid(String tableName, String uuid);
	
	public DataBatchData getByPhone(String tableName, String uuid);

	public DataBatchData getByName(String tableName, String itemName);

	public boolean save(String tableName, DataBatchData d);

	public void save(String tableName, DataBatchData d, String[] excludeFieldName);

	public boolean deleteById(String tableName, UUID uuid);

	public int getCount(String tableName);
	
	public int getUnallocateCount(String tableName);

	public PageResult<DataBatchData> queryPage(ICondition condition);

	public void importExcelToTable(String uuid, String filePath, String uploader, String tableName, Collection<String> relatedUsers);

	public boolean batDelete(String dataTable, String[] uuids);
	
	public void deleteDataByUuid(String uuid, String source);
	
	public void deleteDataByPhoneNumber(String phonenumber, String batchuuid);
	
	public void updateDataCount(Integer num, String source);
	
	public DataBatchData getPhoneData(String phone);
	
	
	/**
	 * 根据号码查找到对应的批次 如果存在 然后更新该批次的拥有者
	 * @param request
	 * @param phone
	 * @param cstmUuid 
	 * @return
	 */
	public Integer updateDataBatchData(HttpServletRequest request, String phone, String cstmUuid) ;

}
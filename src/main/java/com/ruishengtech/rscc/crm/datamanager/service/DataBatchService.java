package com.ruishengtech.rscc.crm.datamanager.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.user.model.User;

public interface DataBatchService {
	
	public List<String> getDatas(ICondition Condition);
	
	public List<DataBatch> getDatas();
	
	public List<String> getUuids();
	
	public DataBatch getByUuid(UUID uuid);
	
	public void createDefaultBatch();
	
	public DataBatchData getDataBatchDataByUuid(String uuid, String source);

	public boolean save(DataBatch db);

	public boolean save(DataBatch db, String[] excludeFieldName);

	public boolean update(DataBatch db);

	public void update(DataBatch d, String[] excludeFieldName);

	public PageResult<DataBatch> queryPage(ICondition condition);
	
	public Set<String> getDepartmentsDataBatchs(Collection<String> uuids);
	
	//获得一个容器和哪些部门有关联
	public List<String> getDataBatchDepartments(String data_batch_uuid);
	
	public List<String> getDataBatchDepartmentsIgnoreLink(String data_batch_uuid);
	
	//获取一个部门关联的所有容器
	public List<String> getDepartmentDataBatchs(String uuid);

	//第三个参数代表是否是新建的批次，如果是新建批次，则不用把旧的数据量算出来
	public int bindDepartment(String uuid, String[] departments, boolean isNew);
	
	public int unbindDepartment(String batchid,String[] undepartments);
	
	public void importExcelToTable(String uuid, String filePath, String uploader, String tableName, Collection<String> relatedUsers);
	
	//根据批次名获取数据对象
	public DataBatch getDataBatchByTable(String dataTable);
	
	//查询新的部门表对象
	public DepartmentTable getDepartmentById(String deptid);
	
	//获取全部部门 new_data_department
	public List<DepartmentTable> getAllDepartment();
	
	//根据批次绑定的部门id获取部门下面的坐席对象
	public List<User> getUserBydeptId(String deptid);
	
	//根据uuid(userid)获取到坐席-部门link表对象
	public UserTask getUserTaskByUUID(String userid);
	
	//根据输入的批次名查询批次表，判断名字是否重复
	public DataBatch getDataBatchByBatchName(String batchName);
	
	public void updateName(String uuid, String batchName);
	
	//查询部门id对应的上限
	public String getLimitByDeptId(String batchid,String deptid);
	
	//选择预览数据页面的全部
	public List<String> getBatchData(ICondition condition);
	
	//回收数据
	public void batRecoveryData(String batchUuid,String[] dataUuids);
	
	//查看这个号码在哪个批次
	public String getPhoneBatch(String phone);
	
	public DataBatchData getDataByPhone(String tableName, String phone);
	
	// 删除批次
	public void deleteBatch(String uuid);
	
	// 批量删除
	public boolean batchDeleteData(HttpServletRequest request, String batchUuid);

	public boolean forceDeleteBatch(String uuid);
	
	public Map<String, Object> getBatchStat(String batchUuid);
	
	public List<DataBatchDepartmentLink> getBatchDepInfo(String batchUuid);
	
	public void collectData(String batchUuid,String[] depts);
	
	//创建自动连接
	public int autoBindDepartment(String batchUuid, String department);

}
package com.ruishengtech.rscc.crm.datamanager.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DepartmentDataCondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DeptAllotDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentData;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;

public interface DeptDataService {

	public PageResult<DataBatchDepartmentLink> queryPage(ICondition condition);
	
	public PageResult<DepartmentData> queryDepartmentDataPage(ICondition condition);
	
	public PageResult<UserData> queryUserDataPage(ICondition condition);
	
	public PageResult<DataBatchData> queryAbandonDataPage(ICondition condition);
	
	public PageResult<DataBatchData> queryBlackListDataPage(ICondition condition);
	
	public PageResult<UserData> queryIntentDataPage(ICondition condition);
	
	public List<DepartmentData> getAllDepartmentData(String batchUuid, String deptUuid);
	
	public int getCountDepartmentDatas(String batchUuid, String deptUuid);
	
	/**
	 * 获取部门今日数据量
	 * @param source
	 * @return
	 */
	public int getDepartmentDataTodayData(String source);
	
	public int getDataBatchDataTodayData(String source, String deptUuid);
	
	public List<UserData> getAllUserData(String batchUuid, String deptUuid);
	
	public List<String> getUserDatas(DepartmentDataCondition cond);
	
	public DataBatchDepartmentLink getByLink(String dataBatchUuid, String departmentUuid);
	
	public void save(DataBatchDepartmentLink d);
	
	public void update(DataBatchDepartmentLink d);
	
	/**
	 * 部门到人分配数据
	 * @param cond
	 * @return
	 */
	public int allotData(DeptAllotDataCondition cond);
	
	/**
	 * 部门到部门分配数据
	 * @param cond
	 * @return
	 */
	public int allotDeptData(DeptAllotDataCondition cond);
	
	public void returnData(String dataBatchUuid, String deptUuid, Integer returnDataNumber);
	
	public void achieveData(String dataBatchUuid, String deptUuid, Integer achieveDataNumber);
	
	public void allotBatchData(String dataBatchUuid, String deptUuid, String[] allotData, String deptuser);

	public void returnBatchData(String batchUuid, String deptUuid, String[] dataUuids);
	
	public List<DataBatchDepartmentLink> getLinks(Collection<String> departments);
	
	/**
	 * 回收任务中所有地 数据
	 * @param batchUuid
	 * @param deptUuid
	 * @return
	 */
	public List<UserTask> getUserTaskInfo(String batchUuid, String deptUuid);
	
	/**
	 * 回收任务中未拨打的数据
	 * @param batchUuid
	 * @param deptUuid
	 * @return
	 */
	public List<UserTask> getUserTaskInfoUnCall(String batchUuid, String deptUuid);
	
	/**
	 * 回收任务中已打已通的数据
	 * @param batchUuid
	 * @param deptUuid
	 * @return
	 */
	public List<UserTask> getUserTaskInfoCallUn(String batchUuid, String deptUuid);
	
	/**
	 * 回收任务中已呼通的数据
	 * @param batchUuid
	 * @param deptUuid
	 * @return
	 */
	public List<UserTask> getUserTaskInfoCall(String batchUuid, String deptUuid);
	
	public void collectData(String dataBatchUuid, String deptUuid, String[] users, Integer[] collect);
	
	public Map<String, Object> getDataDeptStat(String batchUuid, String deptUuid);
	
	public PageResult<DataBatchData> queryCustomerDataPage(ICondition condition);
	
	public int getDepartmentDataCount(String deptUuid);

	/**
	 * 回收管辖部门下当前批次人员的数据
	 * @param dataBatchUuid
	 * @param departmentUuid
	 * @param users
	 * @param collect
	 * @return
	 */
	public int newCollectUserData(String dataBatchUuid, String departmentUuid, String[] users, Integer[] collect);

	/**
	 * 回收部门中的数据(部门到部门)
	 * @param batchUuid
	 * @param deptUuid
	 * @param dts
	 * @return
	 */
	public int collectDeptData(String batchUuid, String deptUuid, String[] dts);
	
	public boolean recycleByUser(String userId);
	
	/**
	 * 返回当前部门已有的数据
	 * @param deptUuid
	 * @return
	 */
	public int getTotalDataByDept(String deptUuid);
	
}

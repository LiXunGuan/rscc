package com.ruishengtech.rscc.crm.datamanager.service;

import java.util.List;

import org.json.JSONObject;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.datamanager.condition.NewUserDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.user.model.User;

/**
 * @author Frank
 *
 */
public interface NewUserDataService {

	/**
	 * 数据查询
	 * @param condition
	 * @return
	 */
	public abstract PageResult<UserData> queryPage(NewUserDataCondition condition);

	/**
	 * 根据坐席编号获取详细数据
	 * @param ownUser
	 */
	public abstract void getDetails(String ownUser);

	public void update(UserData userData);
	
	
	/**
	 * h获取数据
	 * @param ownId
	 * @param batchId
	 * @param deptId
	 * @param countNumber
	 * @return 
	 */
	public abstract int getData(String ownId, String batchId, String deptId, int getCount);
	
	public UserTask getByUuid(UUID uuid);
	
	public void save(String uuid, String departmentUuid, int singleLimit, int dayLimit, int totalLimit, int intentLimit);

	public boolean deleteById(UUID uuid);
	
	/**
	 * 查询当前用户能看到的所有批次 批次名字和还可以获取到的最多数据
	 * @param currentUser
	 */
	public abstract List<DataBatchDepartmentLink> getBatchInfo(User currentUser);

	public abstract UserData getUserDataByPhone(String depUuid, String phone);

	public abstract UserData getNextData(String depUuid, String userUuid);
	
	public int updateData(UserData userData, String action, String callSessionUuid, String cstmUuid);

	/**
	 * 根据编号查询意向类型
	 * @param uuid
	 * @return
	 */
	public abstract String getIntendByUUid(String uuid);

	/**
	 * 获取所有意向类型
	 */
	public abstract List<DataIntent> getAllIntendType();
	
	public String getPhoneJson(String phone);
	
	public abstract void createData(String depUuid, String userUuid, String phone);
	
	/**
	 * 获取个人数据
	 * @param user
	 * @param data_batch_uuid
	 * @return
	 */
	public abstract Integer getPersionData(User user,String data_batch_uuid);

	int getLimitByUser(String uuid);
	
	public int getTotalData(String source, String target);

	public abstract List<String> getAllUser(NewUserDataCondition condition);

	public abstract void commitAudit(String department, String userUuid, String phoneNumber, Integer stat);
	
	//单个转移
	public abstract void changeUser(String batchUuid, String deptUuid,
			String ownerUuid, String phoneNumber, String newOwnUser);
	
	//批量转移
	public abstract void changeUsers(String deptUuid, String ownerUuid, String newOwnUser, String[] datas);
	/**
	 * 还可以获取的意向客户数
	 * @param uid
	 * @return
	 */
	abstract JSONObject getIntendNumber(User user);

	/**
	 * 获取当前用户还能获取的数据
	 * @param currentUser
	 * @return
	 */
	abstract JSONObject getDataNumber(User currentUser);
	
	public List<String> getDataByOwnUser(String userid,String dept);
	
	public UserData getUserDataByDataId(String deptid,String dataid);
	
	UserTask getSingleUserDateInfo(String useruuid);
	
	void saveLog(Integer integer, String ownId);
	
}

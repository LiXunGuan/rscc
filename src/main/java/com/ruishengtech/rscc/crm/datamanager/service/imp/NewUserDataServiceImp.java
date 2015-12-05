package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.MathUtil;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.datamanager.condition.NewUserDataCondition;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.AbandonNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BlacklistNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.CustomerNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.DepNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.IntentNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.ShareNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.CrossUserToUserData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.DepToUserData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.IntentToCustomerData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.IntentToShareData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.IntentToUserData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToAbandonData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToBlacklistData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToCustomerData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToIntentData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToShareData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToUserData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.model.DeptToUserTransgerLog;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.model.UserDataLog;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.datamanager.service.DataGlobalShareService;
import com.ruishengtech.rscc.crm.datamanager.service.DeptToUserTransgerLogService;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.solution.NewUserDataSolution;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.model.User;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class NewUserDataServiceImp extends BaseService implements NewUserDataService {

	@Autowired
	private DataManagers dataManager;
	
	@Autowired
	private DataGlobalShareService dataGlobalShareService;
	
	@Autowired
	private DataBatchDepartmentLinkServiceImp dataBatchDepartmentLinkService;

	@Autowired
	private UserDataLogServiceImp userDataLogService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SysConfigService configService;
	
	@Autowired
	private DeptToUserTransgerLogService deptToUserTransgerLogService;
	
	private static final Logger logger = Logger.getLogger(NewUserDataServiceImp.class);
	
	/**
	 * 数据查询
	 * 
	 * @param condition
	 * @return
	 */
	@Override
	public PageResult<UserData> queryPage(NewUserDataCondition condition) {

		return super.queryPage(new NewUserDataSolution(), condition,
				UserData.class);
	}

	@Override
	public UserData getUserDataByPhone(String depUuid, String phone) {
		
		List<UserData> list = getBeanListWithTable(UserData.tableName + depUuid, UserData.class, " and phone_number = ? ", phone);
		for (UserData userData : list) {
			userData.setDeptUuid(depUuid);
		}
		return list.size()==0?null:list.get(0);

	}
	
	@Override
	public UserData getUserDataByDataId(String deptid,String dataid) {
		List<UserData> list = getBeanListWithTable("new_data_department_user_" + deptid, UserData.class, " and uuid = ? ",dataid);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	public void update(UserData userData) {
		if (userData == null) {
			return;
		}
		super.update(UserData.tableName + userData.getDeptUuid(), userData);
	}
	
	@Override
	public void getDetails(String ownUser) {

	}
	
	public UserTask getByUuid(UUID uuid) {
		return super.getByUuid(UserTask.class, uuid);
	}

	public void save(String uuid, String departmentUuid, int singleLimit,
			int dayLimit, int totalLimit, int intentLimit) {
		UserTask d = new UserTask();
		d.setDepartmentUuid(departmentUuid);
		d.setSingleLimit(singleLimit);
		d.setDayLimit(dayLimit);
		d.setTotalLimit(totalLimit);
		d.setIntentLimit(intentLimit);
		super.save(d, new String[] { "dataCount", "intentCount",
				"customerCount", "abandonCount", "blacklistCount", "shareCount"});
		jdbcTemplate.update("update " + UserTask.tableName
				+ " set uuid=? where uuid=?", uuid, d.getUid());
	}
	
	public boolean deleteById(UUID uuid) {
		//这里有可能有问题，因为默认批次的问题
		UserTask userTask = getByUuid(UserTask.class, uuid);
		if (userTask.getDataCount() > 0) {
			return false;
		}
		super.deleteById(UserTask.class, uuid);
		return true;
	}

	/**
	 * 返回个人获取数据单日上限
	 * @return
	 */
	@Override
	public Integer getPersionData(User user,String data_batch_uuid) {
		
		UserTask userTask = getByUuid(UserTask.class, user.getUuid());
		
		int totalCount = getTotalData(user.getDepartment(), user.getUid());
		int todayCount = getTodayData(user.getDepartment(), user.getUid());
		
//		int limit = Math.min(userTask.getTotalLimit() - totalCount, Math.min(userTask.getDayLimit() - todayCount, Math.min(userTask.getSingleLimit(), countNumber)));
		//总上限
		//当日上限
		//单次上限
		int limit = MathUtil.min(userTask.getTotalLimit() - totalCount, 
								userTask.getDayLimit() - todayCount,  userTask.getSingleLimit(), 
								getPersionDataCount(user.getDepartment(),  data_batch_uuid));

		return limit;
		
	}
	
	
	@Override
	public int getLimitByUser(String uuid){
		// 查询部门和人员的关系表new_data_department_user
		UserTask userTask = getByUuid(UserTask.class, UUID.UUIDFromString(uuid));
		// 查询部门人员表(new_data_department_user_部门UUID)下的当前人能获取的总数据
		int totalCount = getTotalData(userTask.getDepartmentUuid(), uuid);
		// 查询部门人员表(new_data_department_user_部门UUID)下的当前人的今天的数据
		int todayCount = getTodayData(userTask.getDepartmentUuid(), uuid);
//		int limit = Math.min(userTask.getTotalLimit() - totalCount, Math.min(userTask.getDayLimit() - todayCount, Math.min(userTask.getSingleLimit(), countNumber)));
		// 比较
		//	  1.当前人能获取的的总数据-已有的总数据 
		//    2.当前人的单日上限-当前人今日数据;
		//    3.当前人的单次上限
		int limit = MathUtil.min(userTask.getTotalLimit() - totalCount, userTask.getDayLimit() - todayCount, userTask.getSingleLimit());
		return limit;
	}
	
	@Override
	public int getData(String ownId, String batchId, String deptId, int getCount) {

		DepNode dn = new DepNode();
		UserNode un = new UserNode();
		DepToUserData data = new DepToUserData();
		data.setBatchUuid(batchId);
		data.setTransferNum(getCount);
		dn.setTableName(deptId);
		un.setTableName(ownId);

		TransferResult tr = dataManager.transfer(dn, un, data);
		// 保存日志
		saveLog(tr.getTransferCount(), ownId);
		return tr.getTransferCount();

	}

	/**
	 * 保存日志记录
	 * @param integer
	 * @param ownId
	 */
	public void saveLog(Integer integer, String ownId) {

		DeptToUserTransgerLog toUserTransgerLog = new DeptToUserTransgerLog();
		//查询当前人任务量
		UserTask data = getSingleUserDateInfo(ownId);
		toUserTransgerLog.setUuid(UUID.randomUUID());
		toUserTransgerLog.setGotDate(new Date());
		toUserTransgerLog.setUserRealCount(integer+"");
		toUserTransgerLog.setUserUuid(ownId);
		toUserTransgerLog.setUserDayCount(data.getDayLimit()+"");
		toUserTransgerLog.setUserTotalCount(data.getTotalLimit()+"");
		toUserTransgerLog.setUserSingleCount(data.getSingleLimit()+"");
		deptToUserTransgerLogService.save(toUserTransgerLog);		
	}
	
	
	private int getTodayData(String source, String target) {
		int total = 0;
		String selectSql = "select count(*) from " + UserData.tableName + source + " where "
				+ "own_user=? and "
				+ "year(own_user_timestamp)=year(now()) and month(own_user_timestamp)=month(now()) and day(own_user_timestamp)=day(now())";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class, target);
		return total;
	}
	
	public int getTotalData(String source, String target) {
		int total = 0;
		String selectSql = "select count(*) from " + UserData.tableName + source + " where "
				+ "own_user=? ";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class, target);
		return total;
	}
	
	private int getPersionDataCount(String department_uuid, String data_batch_uuid) {
		int total = 0;
		String selectSql = "SELECT data_count-own_count FROM `new_data_batch_department_link` WHERE department_uuid = ? AND data_batch_uuid = ? ";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class, department_uuid,data_batch_uuid);
		return total;
	}
	
	/**
	 * 查询当前用户能看到的所有批次
	 * 
	 * @param currentUser
	 */
	@Override
	public List<DataBatchDepartmentLink> getBatchInfo(final User currentUser) {

		List<DataBatchDepartmentLink> datas = dataBatchDepartmentLinkService.getByDepartmentWithGet(currentUser.getDepartment());
		
		if (null != datas && datas.size() > 0 && null != datas.get(0).getBatchname()) {

			return datas;
		}

		return null;
	}

	@Override
	public int updateData(UserData userData, String action, String callSessionUuid, String cstmUuid) {
		//这些名称最好使用常量标记

		//从共享池中保存之后，可能还会进这里，但是没什么影响，想搞定这个问题，还是考虑添加字段最靠谱
		if ( userData != null && ShareNode.name.equals(userData.getOwnDepartment())) {
				dataGlobalShareService.changeGlobalShareStat(userData.getUid(), true);
		} 
		String intentType = null;
		boolean isSaveLog = true;
		//记录操作
		
		TransferResult tr;
		UserNode fromNode = new UserNode();
		if(StringUtils.isBlank(userData.getIntentType())) {
			if ("nointent".equals(action)) {//无意向,等同用户到用户,ok
				UserNode toNode = new UserNode();
				fromNode.setTableName(userData.getDeptUuid());
				UserToUserData data = new UserToUserData();
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
			} else if ("noanswer".equals(action)) {//无人接听,ok
				UserNode toNode = new UserNode();
				fromNode.setTableName(userData.getDeptUuid());
				UserToUserData data = new UserToUserData();
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
			} else if("abandon".equals(action)) { //号码无效,ok
				AbandonNode toNode = new AbandonNode(userData.getBatchUuid());
				fromNode.setTableName(userData.getDeptUuid());
				UserToAbandonData data = new UserToAbandonData();
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
			} else if("blacklist".equals(action)) { //黑名单,ok
				BlacklistNode toNode = new BlacklistNode(userData.getBatchUuid());
				fromNode.setTableName(userData.getDeptUuid());
				UserToBlacklistData data = new UserToBlacklistData();
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
			} else if ("global_share".equals(action)) {//放入共享池
				ShareNode toNode = new ShareNode();
				fromNode.setTableName(userData.getDeptUuid());
				UserToShareData data = new UserToShareData();
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
			} else if ("customer".equals(action)) {//放入共享池
				CustomerNode toNode = new CustomerNode(cstmUuid);
				fromNode.setTableName(userData.getDeptUuid());
				UserToCustomerData data = new UserToCustomerData();
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
			} else { //转某个意向,ok
				if (isLimited(userData.getDeptUuid(), userData.getOwnUser())) {
					//超过上限
					return -1;
				}
				IntentNode toNode = new IntentNode(action);
				fromNode.setTableName(userData.getDeptUuid());
				UserToIntentData data = new UserToIntentData();
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
				intentType = action;
				action = "intent";
			}
		} else { //原来有意向时
			if ("nointent".equals(action)) {//无意向
				UserNode toNode = new UserNode(userData.getDeptUuid());
				IntentNode newFromNode = new IntentNode(userData.getDeptUuid());
				IntentToUserData data = new IntentToUserData();
				data.setUserData(userData);
				tr = dataManager.transfer(newFromNode, toNode, data);
			} else if ("noanswer".equals(action)) {//无人接听的情况下，只修改呼叫时间
				UserNode toNode = new UserNode();
				UserToUserData data = new UserToUserData();
				fromNode.setTableName(userData.getDeptUuid());
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
			} else if("abandon".equals(action)) { //号码无效的情况不可能出现
				tr = new TransferResult();
				tr.setTransferCount(0);
				isSaveLog = false;
			} else if("blacklist".equals(action)) { //黑名单-应该也不会吧
				tr = new TransferResult();
				tr.setTransferCount(0);
				isSaveLog = false;
			} else if ("global_share".equals(action)) {//放入共享池，这个理论上会有，ok
				ShareNode toNode = new ShareNode();
				IntentNode newFromNode = new IntentNode(userData.getDeptUuid());
				IntentToShareData data = new IntentToShareData();
				data.setUserData(userData);
				tr = dataManager.transfer(newFromNode, toNode, data);
				customerService.emptyOwnerByPhone(userData.getPhoneNumber());
			} else if ("customer".equals(action)) {//转成交客户
				CustomerNode toNode = new CustomerNode(cstmUuid);
				IntentNode newFromNode = new IntentNode(userData.getDeptUuid());
				fromNode.setTableName(userData.getDeptUuid());
				IntentToCustomerData data = new IntentToCustomerData();
				data.setUserData(userData);
				tr = dataManager.transfer(newFromNode, toNode, data);
			} else { //转某个意向,ok
				IntentNode toNode = new IntentNode(action);
				fromNode.setTableName(userData.getDeptUuid());
				UserToIntentData data = new UserToIntentData();
				data.setUserData(userData);
				tr = dataManager.transfer(fromNode, toNode, data);
				intentType = action;
				action = "intent";
			}
		}
		
		if(isSaveLog) {
			UserDataLog userDataLog = new UserDataLog();
			userDataLog.setCall_result(userData.getLastCallResult());
			userDataLog.setCall_session_uuid(callSessionUuid);
			userDataLog.setOp_time(new Date());
			
			String oldStat;
			if (StringUtils.isBlank(userData.getIntentType())) {
				oldStat = "task";
			} else {
				oldStat = "intent";
			}
			
			userDataLog.setOld_stat(oldStat);
//			userDataLog.setOp_time(new Date());
			userDataLog.setOp_user(userData.getOwnUser());
			//记录原有类型
			userDataLog.setType_des(intentType);
			userDataLog.setBatch_uuid(userData.getBatchUuid());
			userDataLog.setPhone_number(userData.getPhoneNumber());
			userDataLog.setOp_type(action);
			userDataLogService.save(userDataLog);
		}
		
		return tr.getTransferCount();
	
	}

	/**
	 * 获取某个人的意向客户量是否达到了他的上限
	 * @param uuid
	 * @return
	 */
	public boolean isLimited(String department, String uuid) {

		//查询意向客户量
		int intendCount = jdbcTemplate.queryForObject("SELECT count(*) FROM new_data_department_user_" + department + " WHERE own_user=? and intent_type is not null", Integer.class,uuid);
		int max = 0;
		UserTask userTask = getByUuid(UUID.UUIDFromString(uuid));
		
		//如果为空或者是0 取到页面配置的最大值 
		if (userTask.getIntentLimit() == null || userTask.getIntentLimit() == 0) {
			
			max = Integer.valueOf(SysConfigManager.getInstance().getDataMap().get("sys.data.getIntentTotalLimit").getSysVal());
		} else {
			
			max = userTask.getIntentLimit();
		}
		return intendCount >= max;
	}
	
	/**
	 * 根据编号查询意向类型
	 * @param uuid
	 * @return
	 */
	@Override
	public String getIntendByUUid(String uuid) {

		String intendType = jdbcTemplate.queryForObject("SELECT * FROM `new_data_intent` WHERE uuid = ? ;", String.class,uuid);
		
		return intendType;
	}

	/**
	 * 获取所有意向类型
	 */
	@Override
	public List<DataIntent> getAllIntendType() {

		List<DataIntent> dataIntents = getBeanList(DataIntent.class, ""); 
		if(dataIntents != null && dataIntents.size() > 0){
			
			return dataIntents;
		}
		return null;
	}
	
	@Override
	public String getPhoneJson(String phone) {
		String batch = jdbcTemplate.queryForObject("select batch_uuid from new_data_phone_resource where phone_number=?", String.class, phone);
		String json = jdbcTemplate.queryForObject("select json from new_data_batch_" + batch + " where phone_number=?", String.class, phone);
		return json;
	}

	//这个号码之前不存在，则在资源表中插入一条，并获取到自己名下，并在空批次中加一条数据
	//暂时未处理数量，以后再说
	@Override
	public void createData(String depUuid, String userUuid, String phone) {
		if (StringUtils.isBlank(phone)) {
			return;
		}
		String uuid = UUID.randomUUID().toString();
		int updateCount = jdbcTemplate.update("INSERT INTO new_data_phone_resource (phone_number,batch_uuid) VALUES(?,?)", phone, "");
		jdbcTemplate.update("INSERT INTO new_data_batch_ (uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp," +
				"own_user,own_user_timestamp,call_count,last_call_department,last_call_user,last_call_result,last_call_time) VALUES "
				+ "(?,?,?,?,?,now(),?,now(),?,?,?,?,now())", uuid, "", phone, "{}", depUuid, userUuid, 1, depUuid, userUuid, "1");
		jdbcTemplate.update("INSERT INTO new_data_department_user_" + depUuid + " (uuid, batch_uuid, phone_number, json, own_department, "
				+ "own_department_timestamp,own_user,own_user_timestamp,call_count,last_call_result,last_call_time) VALUES "
				+ "(?,?,?,?,?,now(),?,now(),?,?,now())", uuid, "", phone, "{}", depUuid, userUuid, 1, "1");
		jdbcTemplate.update("update new_data_batch set data_count=data_count+?,own_count=own_count+? where uuid=?", updateCount, updateCount, "");
	}

	@Override
	public UserData getNextData(String depUuid, String userUuid) {
		List<UserData> list = getBeanListWithTable("new_data_department_user_" + depUuid, UserData.class, " and own_department=? and own_user=? and intent_type is null "
				+ "order by last_call_time,own_user_timestamp,phone_number limit 1", depUuid, userUuid);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<String> getAllUser(NewUserDataCondition condition) {
		StringBuilder querySql = new StringBuilder("select d.phone_number ");
		List<Object> params = new ArrayList<Object>();
		NewUserDataSolution solution = new NewUserDataSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	//是本人的数据才能提交审批
	@Override
	public void commitAudit(String department, String userUuid, String phoneNumber, Integer stat) {
		String updateSql = "update new_data_department_user_" + department + " set be_audit = ? where own_user=? and phone_number=?";
		jdbcTemplate.update(updateSql, stat, userUuid, phoneNumber);
	}

	@Override
	public void changeUser(String batchUuid, String deptUuid, String ownerUuid, String dataUuid, String newOwnUser) {
		UserNode fromNode = new UserNode(ownerUuid);
		UserNode toNode = new UserNode(newOwnUser);
		CrossUserToUserData transferData = new CrossUserToUserData();
		transferData.setBatchUuid(batchUuid);
		transferData.setUserDept(deptUuid);
		transferData.setDatas(new String[]{dataUuid});
		dataManager.transfer(fromNode, toNode, transferData);
	}
	
	@Override
	public void changeUsers(String deptUuid, String ownerUuid,String newOwnUser, String[] datas) {
		UserNode fromNode = new UserNode(ownerUuid);
		UserNode toNode = new UserNode(newOwnUser);
		CrossUserToUserData transferData = new CrossUserToUserData();
		for(int i = 0; i < datas.length; i++){
			transferData.setBatchUuid(getUserDataByDataId(deptUuid,datas[i]).getBatchUuid());
			transferData.setUserDept(deptUuid);
			transferData.setDatas(datas);
			dataManager.transfer(fromNode, toNode, transferData);
		}
	}

	/**
	 * 还可以获取的意向客户数
	 * 
	 * 1、获取当前的用户的部门，查询部门数据表。查询存在并且意向不为空的个数；
	 * 2、获取当前用户最大意向客户数；
	 * 3、返回相减的值；
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public JSONObject getIntendNumber(User user) {

		String msg = "";
		Integer intendCount = 0;
		try {
			
			//当日意向类型数目
			intendCount = jdbcTemplate.queryForObject("SELECT COUNT(intent_type) FROM new_data_department_user_"
							+user.getDepartment() +" WHERE intent_type is NOT NULL AND own_user = ? ", Integer.class,user.getUid());
			
		} catch (Exception e) {

			intendCount = -1;
			msg = "无意向客户数！";
			return new JSONObject().put("msg", msg).put("intendCount", intendCount);
		}
		int max = 0;
		UserTask userTask = getByUuid(UUID.UUIDFromString(user.getUid()));
		
		//如果为空或者是0 取到页面配置的最大值 
		if (userTask.getIntentLimit() == null || userTask.getIntentLimit() == 0) {
			
			max = Integer.valueOf(SysConfigManager.getInstance().getDataMap().get("sys.data.getIntentTotalLimit").getSysVal());
		} else {
			
			max = userTask.getIntentLimit();
		}
		
		return new JSONObject().put("msg", msg).put("intendCount", max - intendCount).put("intentNumber", intendCount);
		
	}

	/**
	 * 获取当前用户还能获取的数据
	 * @param currentUser
	 * @return
	 */
	@Override
	public JSONObject getDataNumber(User currentUser) {

		String msg = "";
		Integer allCount = 0;
		Integer dataCount = 0;
		
		//已经获取的数据条数
		Integer gotDataCount = 0;
		
		try {
			
//			intendCount = jdbcTemplate.queryForObject("SELECT COUNT(intent_type) FROM new_data_department_user_"
//							+currentUser.getDepartment() +" WHERE intent_type is NOT NULL ", Integer.class);
			
			//当日获取的数据（包含了意向数据在内）
			dataCount = jdbcTemplate.queryForObject(" SELECT COUNT(*) FROM new_data_department_user_"+currentUser.getDepartment()+ 
							" WHERE own_user = ? AND  SUBSTR(own_user_timestamp,1,10) = SUBSTR(NOW(),1,10) ;", Integer.class,currentUser.getUid());
			
			//查询当日已经获取的数据条数
			gotDataCount = deptToUserTransgerLogService.getLogCount(new Date(), currentUser.getUid());
			allCount += dataCount;
			
			logger.info("当日已经获取的条数为"+gotDataCount);
			
			
		} catch (Exception e) {

			dataCount = -1;
			msg = "已经获取完成";
			return new JSONObject().put("msg", msg).put("dataCount", dataCount);
		}

		
		int max = 0;
		UserTask userTask = getByUuid(UUID.UUIDFromString(currentUser.getUid()));
		
		//如果为空或者是0 取到页面配置的最大值 
		if (userTask.getDayLimit() == null || userTask.getDayLimit() == 0) {
			
			max = Integer.valueOf(configService.getSysConfigByKey("sys.data.getIntentTotalLimit").getSysVal());
		} else {
			
			max = userTask.getDayLimit();
		}
		
		return new JSONObject().put("msg", msg).put("intendCount", max - allCount).put("DataNumber", allCount);

	}

	@Override
	public List<String> getDataByOwnUser(String userid,String dept) {
		
		List<String> dataids = jdbcTemplate.queryForList("SELECT uuid FROM new_data_department_user_"+dept+" WHERE own_user = ? AND intent_type IS NOT NULL",String.class,userid);
		return dataids;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserTask getSingleUserDateInfo(final String useruuid) {

		List<UserTask> datas = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT * FROM `new_data_department_user` WHERE uuid = ?");
				params.add(useruuid);
			}
		}, UserTask.class);
		
		if(datas.size() > 0){
			return datas.get(0);
		}
		return null;
	}

}

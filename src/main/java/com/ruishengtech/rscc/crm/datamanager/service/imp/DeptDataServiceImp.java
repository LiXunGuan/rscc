package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.MathUtil;
import com.ruishengtech.rscc.crm.datamanager.condition.DepartmentDataCondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DeptAllotDataCondition;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.DepNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.BatchToDepData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.CrossDepToUserData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.CrossUserToDepData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.DepToBatchData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.DepToDepData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.DepToUserData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.TransferData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToDepData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentData;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.datamanager.service.DeptDataService;
import com.ruishengtech.rscc.crm.datamanager.service.DeptToUserTransgerLogService;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.solution.AbandonDataSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.BlackListDataSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.CustomerDataSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.DepartmentDataSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.DeptDataSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.IntentDataSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.UserDataSolution;
import com.ruishengtech.rscc.crm.ui.cstm.controller.CustomerController;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class DeptDataServiceImp extends BaseService implements DeptDataService {

	@Autowired
	private DataManagers dataManagers;
	
	@Autowired
	private NewUserDataService newUserDataService;
	
	@Autowired
	private DepartmentTableServiceImp departmentTableService;
	
	@Autowired
	private DataBatchDepartmentLinkServiceImp dataBatchDepartmentLinkService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DeptToUserTransgerLogService deptToUserTransgerLogService;
	
	private static final Logger logger = Logger.getLogger(CustomerController.class);

	@Override
	public PageResult<DataBatchDepartmentLink> queryPage(ICondition condition) {
		return super.queryPage(new DeptDataSolution(), condition, DataBatchDepartmentLink.class);
	}
	
	@Override
	public PageResult<DepartmentData> queryDepartmentDataPage(ICondition condition) {
		return super.queryPage(new DepartmentDataSolution(), condition, DepartmentData.class);
	}
	
	@Override
	public PageResult<UserData> queryUserDataPage(ICondition condition) {
		return super.queryPage(new UserDataSolution(), condition, UserData.class);
	}
	
	@Override
	public PageResult<DataBatchData> queryAbandonDataPage(ICondition condition) {
		return super.queryPage(new AbandonDataSolution(), condition, DataBatchData.class);
	}
	
	@Override
	public PageResult<DataBatchData> queryBlackListDataPage(ICondition condition) {
		return super.queryPage(new BlackListDataSolution(), condition, DataBatchData.class);
	}
	
	@Override
	public PageResult<DataBatchData> queryCustomerDataPage(ICondition condition) {
		return super.queryPage(new CustomerDataSolution(), condition, DataBatchData.class);
	}
	
	@Override
	public PageResult<UserData> queryIntentDataPage(ICondition condition) {
		return super.queryPage(new IntentDataSolution(), condition, UserData.class);
	}
	
	public int allotData(DeptAllotDataCondition cond) {
		DepNode fromNode = new DepNode(cond.getDeptUuid());
		UserNode toNode = new UserNode();
		TransferData transferData;		
		String[] users = cond.getDeptusers();
		String currentDept = "";
		String currentUser = "";
		int num = 0;
		int allotNum = 0;
		for (String u : users) {
			String[] depuser = u.split("-");
			if (depuser.length == 1) {
				currentDept = cond.getDeptUuid();
				currentUser = depuser[0];
			} else if (depuser.length == 2) {
				currentDept = depuser[0];
				currentUser = depuser[1];
			}
			toNode.setTableName(currentUser);
			allotNum = cond.getAllocate() == 1?Math.min(newUserDataService.getLimitByUser(currentUser), cond.getAllocateMax()):
				Math.min(newUserDataService.getLimitByUser(currentUser), cond.getAllocateMax() - newUserDataService.getTotalData(currentDept, currentUser));
			if (currentDept.equals(cond.getDeptUuid())) {// 如果是当前部门下的人
				transferData = new DepToUserData();
				((DepToUserData)transferData).setBatchUuid(cond.getDataBatchUuid());
				((DepToUserData)transferData).setTransferNum(allotNum);
			} else {
				transferData = new CrossDepToUserData();
				((CrossDepToUserData)transferData).setBatchUuid(cond.getDataBatchUuid());
				((CrossDepToUserData)transferData).setUserDept(currentDept);
				((CrossDepToUserData)transferData).setTransferNum(allotNum);
			}
			
			Integer num1 = dataManagers.transfer(fromNode, toNode, transferData).getTransferCount();
			
			num += num1;
			
			logger.info("部门【"+cond.getDeptUuid()+"】成功分配给部门【"+currentDept+"】下的人员【"+currentUser+"】下分配数据【"+num1+"】条。");
			
			//记录分配出去的日志 每一个人都有一个日志记录
			if(num1 > 0){
				newUserDataService.saveLog(num1, currentUser);
			}
		}
		
		return num;
	}
	
//	public int allotData(DeptAllotDataCondition cond) {
//		
//		DepNode fromNode = new DepNode(cond.getDeptUuid());
//		UserNode toNode = new UserNode();
//		
//		DepToUserData transferData  = new DepToUserData();		
////		transferData.setTransferNum(allotDataNumber);
//		transferData.setBatchUuid(cond.getDataBatchUuid());
//		
////		for (String uid : cond.getDeptusers()) {
////			toNode.setTableName(uid);
////			transferData.setTransferNum(Math.min(newUserDataService.getLimitByUser(uid), allotDataNumber));
////			dataManagers.transfer(fromNode, toNode, transferData);
////		}
//		
//		
//		String[] users = cond.getDeptusers();
//		int num = 0;
//		switch (cond.getAllocate()) {
//		case 1: { //每人分配多少条
//			for (String u : users) {
//				toNode.setTableName(u);
//				transferData.setTransferNum(Math.min(newUserDataService.getLimitByUser(u), cond.getAllocateMax()));
//				num += dataManagers.transfer(fromNode, toNode, transferData).getTransferCount();
//			}
//			break;
//		}
//		case 2: { // 每人分配到上限
//			for (String u : users) {
//				toNode.setTableName(u);
//				transferData.setTransferNum(Math.min(newUserDataService.getLimitByUser(u), 
//						cond.getAllocateMax() - newUserDataService.getTotalData(cond.getDeptUuid(), u)));
//				num += dataManagers.transfer(fromNode, toNode, transferData).getTransferCount();
//			}
//		}
//		}
//		
//		return num;
//	}
	
	@Override
	public int allotDeptData(DeptAllotDataCondition cond) {
		DepNode fromNode = new DepNode(cond.getDeptUuid());
		DepNode toNode = new DepNode();
		DepToDepData transferData  = new DepToDepData();
		transferData.setBatchUuid(cond.getDataBatchUuid());
		String[] depts = cond.getDepts();
		int num = 0;
		switch (cond.getAllocate()) {
			case 1: { //每个部门分配多少条
				for (String u : depts) {
					toNode.setTableName(u);
					transferData.setTransferNum(Math.min(getLimitByDept(cond.getDataBatchUuid(), u), cond.getAllocateMax()));
					
					int num1 = dataManagers.transfer(fromNode, toNode, transferData).getTransferCount();
					
					num += num1;
					
					logger.info("部门【"+cond.getDeptUuid()+"】成功分配给部门【"+u+"】分配数据【"+num1+"】条。");
				}
				break;
			}
			case 2: { // 每个部门分配到上限
				for (String u : depts) {
					toNode.setTableName(u);
//					transferData.setTransferNum(Math.min(getLimitByDept(cond.getDataBatchUuid(), u), cond.getAllocateMax() - getTotalData(u)));
					transferData.setTransferNum(Math.min(getLimitByDept(cond.getDataBatchUuid(), u), cond.getAllocateMax() - getTodayData(u)));
					
					int num1 = dataManagers.transfer(fromNode, toNode, transferData).getTransferCount();
					
					num += num1;
					
					logger.info("部门【"+cond.getDeptUuid()+"】成功分配给部门【"+u+"】分配数据【"+num1+"】条。");
				}
			}
		}
		return num;
	}
	
	@Override
	public int collectDeptData(String batchUuid, String deptUuid, String[] dts) {
		DepNode fromNode = new DepNode();
		DepNode toNode = new DepNode(deptUuid);
		DepToDepData transferData  = new DepToDepData();
		transferData.setBatchUuid(batchUuid);
		int num = 0;
		for (String u : dts) {
			int limit = getTotalLimitByDept(deptUuid);
			if(limit > 0){
				fromNode.setTableName(u);
				transferData.setTransferNum(limit);
				Integer num1 = dataManagers.transfer(fromNode, toNode, transferData).getTransferCount();
				num += num1;
				logger.info("部门【"+deptUuid+"】成功从子部门【"+u+"】下回收数据【"+num1+"】条。");
			}
		}
		return num;
	}
	
	private Integer getTotalLimitByDept(String deptUuid) {
		// 获取相关部门信息
		DepartmentTable dt = departmentTableService.getByUuid(UUID.UUIDFromString(deptUuid));
		// 当前部门能获取的总的数据
		int totallimit = dt.getTotalLimit();
		// 当前部门已有的总数据
		int totaldata = getTotalData(deptUuid);
		return totallimit - totaldata;
	}

	private int getLimitByDept(String dataBatchUuid, String deptuuid) {
		// 获取相关部门信息
		DepartmentTable dt = departmentTableService.getByUuid(UUID.UUIDFromString(deptuuid));
		// 当前部门能获取的总的数据
		int totallimit = dt.getTotalLimit();
		// 当前部门已有的总数据
		int totaldata = getTotalData(deptuuid);
		// 当前部门今日获取的数据
		int todaydata = getTodayData(deptuuid);
		// 当前部门的单日和单次上限
		DataBatchDepartmentLink dbd = dataBatchDepartmentLinkService.getByLink(dataBatchUuid, deptuuid);
		int limit = MathUtil.min(totallimit - totaldata, dbd.getDayLimit() - todaydata, dbd.getSingleLimit());
		return limit;
	}
	
	private int getTodayData(String source) {
		int total = 0;
		String selectSql = "select count(*) from " + DepartmentData.tableName + source + " where "
				+ "year(own_department_timestamp)=year(now()) and month(own_department_timestamp)=month(now()) and day(own_department_timestamp)=day(now())";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class);
		return total;
	}
	
	private int getTotalData(String source) {
		int total = 0;
		String selectSql = "select count(*) from " + DepartmentData.tableName + source + "";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class);
		return total;
	}
	
	public void allotBatchData(String dataBatchUuid, String deptUuid, String[] allotData, String deptuser) {
		
		DepNode fromNode = new DepNode(deptUuid);
		UserNode toNode = new UserNode();
		
		DepToUserData transferData  = new DepToUserData();		
		transferData.setDatas(allotData);
		transferData.setBatchUuid(dataBatchUuid);
		toNode.setTableName(deptuser);
		
		dataManagers.transfer(fromNode, toNode, transferData);
	}
	
	public void returnData(String dataBatchUuid, String deptUuid, Integer returnDataNumber) {
		jdbcTemplate.update("update new_data_batch_department_link set return_times = return_times+1 where data_batch_uuid=? and department_uuid=?", dataBatchUuid, deptUuid);
		DepNode fromNode = new DepNode(deptUuid);
		BatchNode toNode = new BatchNode(dataBatchUuid);
		
		DepToBatchData transferData  = new DepToBatchData();		
		transferData.setTransferNum(returnDataNumber);
		int num = dataManagers.transfer(fromNode, toNode, transferData).getTransferCount();
		
		logger.info("部门【"+deptUuid+"】成功为批次【"+dataBatchUuid+"】归还数据【"+num+"】条。");
	}
	
	public void collectData(String dataBatchUuid, String deptUuid, String[] users, Integer[] collect) {
		UserNode fromNode = new UserNode();
		DepNode toNode = new DepNode(deptUuid);
		UserToDepData transferData  = new UserToDepData();	
		transferData.setTransferMod(collect);
		for (String u : users) {
			fromNode.setTableName(u);
			transferData.setBatchUuid(dataBatchUuid);
			dataManagers.transfer(fromNode, toNode, transferData);
		}
	}
	
	@Override
	public int newCollectUserData(String dataBatchUuid, String departmentUuid, String[] users, Integer[] collect) {
		UserNode fromNode = new UserNode();
		DepNode toNode = new DepNode(departmentUuid);
		TransferData transferData;		
		String currentDept = "";
		String currentUser = "";
		int num = 0;
		for (String u : users) {
			String[] depuser = u.split("-");
			currentDept = depuser[0];
			currentUser = depuser[1];
			fromNode.setTableName(currentUser);
			if (currentDept.equals(departmentUuid)) {// 如果是当前部门下的人
				transferData = new UserToDepData();
				((UserToDepData)transferData).setTransferMod(collect);
				((UserToDepData)transferData).setBatchUuid(dataBatchUuid);
			} else {
				transferData = new CrossUserToDepData();
				((CrossUserToDepData)transferData).setTransferMod(collect);
				((CrossUserToDepData)transferData).setBatchUuid(dataBatchUuid);
				((CrossUserToDepData)transferData).setUserDept(currentDept);
			}
			Integer num1 = dataManagers.transfer(fromNode, toNode, transferData).getTransferCount();
			num += num1;
			logger.info("部门【"+departmentUuid+"】成功从部门【"+currentDept+"】下的人员【"+currentUser+"】回收数据【"+num1+"】条。");
			if(num1 > 0){
				newUserDataService.saveLog(-num1, currentUser);
			}
			
		}
		return num;
	}
	
	@Override
	public void returnBatchData(String batchUuid, String deptUuid, String[] dataUuids) {
		UserNode fromNode = new UserNode(deptUuid);
		DepNode toNode = new DepNode(deptUuid);
		
		UserToDepData transferData  = new UserToDepData();
		transferData.setDatas(dataUuids);
		transferData.setBatchUuid(batchUuid);
		
		dataManagers.transfer(fromNode, toNode, transferData);
	}
	
	@Override
	public void achieveData(String dataBatchUuid, String deptUuid, Integer achieveDataNumber) {
		BatchNode fromNode = new BatchNode(dataBatchUuid);
		DepNode toNode = new DepNode(deptUuid);
		
		BatchToDepData transferData = new BatchToDepData();
		transferData.setTransferNum(achieveDataNumber);
		TransferResult ret = dataManagers.transfer(fromNode, toNode, transferData);
		
		logger.info("部门【"+deptUuid+"】成功从批次【"+dataBatchUuid+"】获取数据【"+ret.getTransferCount()+"】条。");
		
		//更新群呼数据量
		String updateGroupCallDataSql = "update new_data_group_call_link set data_count=data_count+? where data_batch=? and data_dept=?";
		jdbcTemplate.update(updateGroupCallDataSql, ret.getTransferCount(), dataBatchUuid, deptUuid);
		
	}

	@Override
	public DataBatchDepartmentLink getByLink(String dataBatchUuid,
			String departmentUuid) {
		 List<DataBatchDepartmentLink> list = getBeanListWithSql(DataBatchDepartmentLink.class, "select t.*,p.batch_name batchname,q.department_name deptname from new_data_batch_department_link t "
				+ "left join new_data_batch p on t.data_batch_uuid=p.uuid left join "
				+ "new_data_department q on t.department_uuid=q.uuid where t.data_batch_uuid=? and t.department_uuid=?", dataBatchUuid, departmentUuid);
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}

	@Override
	public void save(DataBatchDepartmentLink d) {
		String insertSql = "insert into " + DataBatchDepartmentLink.tableName + 
				" (data_batch_uuid,department_uuid,day_limit,single_limit) values(?,?,?,?)";
		jdbcTemplate.update(insertSql, d.getDataBatchUuid(), d.getDepartmentUuid(), d.getDayLimit(), d.getSingleLimit());
	}
	
	@Override
	public void update(DataBatchDepartmentLink d) {
		String updateSql = "update " + DataBatchDepartmentLink.tableName + " set open_flag=? where data_batch_uuid=? and department_uuid=?";
		jdbcTemplate.update(updateSql, d.getOpenFlag(), d.getDataBatchUuid(), d.getDepartmentUuid());
	}

	@Override
	public List<DepartmentData> getAllDepartmentData(String batchUuid, String deptUuid) {
		
		List<DepartmentData> list = getBeanListWithTable("new_data_department_" + deptUuid, DepartmentData.class, " and batch_uuid = ? and own_department = ? ", batchUuid, deptUuid);
		
		if (list.size() > 0) {
            return list;
        }
		return null;
	}

	@Override
	public List<UserData> getAllUserData(String batchUuid, String deptUuid) {
		List<UserData> list = getBeanListWithTable("new_data_department_user_" + deptUuid, UserData.class, " and batch_uuid = ? and own_department = ? ", batchUuid, deptUuid);
		
		if (list.size() > 0) {
            return list;
        }
        return null;
	}

	@Override
	public int getDepartmentDataTodayData(String source) {
		int total = 0;
		String selectSql = "select count(*) from " + DepartmentData.tableName + source + " where "
				+ "year(own_department_timestamp)=year(now()) and month(own_department_timestamp)=month(now()) and day(own_department_timestamp)=day(now())";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class);
		return total;
	}

	@Override
	public int getCountDepartmentDatas(String batchUuid, String deptUuid) {
		int total = 0;
		String selectSql = "select count(*) from " + DepartmentData.tableName + deptUuid + " where "
				+ " batch_uuid = ? and own_department = ?";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class, batchUuid, deptUuid);
		return total;
	}
	
	@Override
	public int getDepartmentDataCount(String deptUuid) {
		int total = 0;
		String selectSql = "select sum(data_count) from " + DataBatchDepartmentLink.tableName + " where "
				+ " department_uuid = ? group by department_uuid";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class, deptUuid);
		return total;
	}

	@Override
	public int getDataBatchDataTodayData(String source, String deptUuid) {
		int total = 0;
		String selectSql = "select count(*) from " + DataBatchData.tableName + source + " where "
				+ "own_department = ? and "
				+ "year(own_department_timestamp)=year(now()) and month(own_department_timestamp)=month(now()) and day(own_department_timestamp)=day(now())";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class, deptUuid);
		return total;
	}

	@Override
	public List<String> getUserDatas(DepartmentDataCondition cond) {
		StringBuilder querySql = new StringBuilder("select dd.uuid ");
		List<Object> params = new ArrayList<Object>();
		UserDataSolution solution = new UserDataSolution();
		solution.getWhere(cond, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	//这里的sql语句可以优化，但是数据量不多
	@Override
	public List<DataBatchDepartmentLink> getLinks(Collection<String> departments) {
		List<Object> params = new ArrayList<Object>();
		String ins = QueryUtils.inString(" and department_uuid ", params, departments);
		return getBeanListWithSql(DataBatchDepartmentLink.class, "select t.*,p.batch_name batchname,q.department_name deptname "
				+ "from new_data_batch_department_link t left join new_data_batch p on t.data_batch_uuid=p.uuid left join "
				+ "new_data_department q on t.department_uuid=q.uuid "
				+ "where 1=1 " + ins + " order by p.batch_name,q.department_name", params.toArray());
	}

	@Override
	public List<UserTask> getUserTaskInfo(String batchUuid, String deptUuid) {
		
		return getBeanListWithSql(UserTask.class, "SELECT count(*) dataCount,t.own_user uuid,p.loginname userName FROM new_data_department_user_" + deptUuid + 
				" t left join user_user p on t.own_user=p.uuid "
				+ "WHERE t.batch_uuid = ? AND t.own_department = ? GROUP BY t.own_user", batchUuid, deptUuid);
	}
	
	/*
	 * dataCount数据总量
	 * ownCount二次领用数量
	 * intentCount意向数量
	 * customerCount成交客户量
	 * abandonCount废号量
	 * blacklistCount黑名单数量
	 * @see com.ruishengtech.rscc.crm.datamanager.service.DeptDataService#getDataDeptStat(java.lang.String, java.lang.String)
	 */
	
	public Map<String, Object> getDataDeptStat(String batchUuid, String deptUuid) {
		String statisticSql = "SELECT sum( own_user IS NULL AND is_abandon = '0' AND is_blacklist = '0' AND is_frozen = '0' AND customer_uuid IS NULL ) dataCount, "
				+ "sum( own_user IS NOT NULL AND is_abandon = '0' AND is_blacklist = '0' AND is_frozen = '0' AND customer_uuid IS NULL ) ownCount, "
				+ "sum( own_user IS NOT NULL AND intent_type IS NOT NULL ) intentCount, "
				+ "sum(customer_uuid IS NOT NULL) customerCount, "
				+ "sum(is_abandon) abandonCount, sum(is_blacklist) blacklistCount "
				+ "FROM new_data_batch_" + batchUuid + " t WHERE own_department = ?";
		Map<String, Object> map =  jdbcTemplate.queryForMap(statisticSql, deptUuid);
		for(String key:map.keySet()) {
			Object value = map.get(key);
			if (value == null) {
				map.put(key, 0);
			} else if (value instanceof Number) {
				map.put(key, ((Number) value).intValue());
			}
		}
		return map;
	}

	@Override
	public List<UserTask> getUserTaskInfoUnCall(String batchUuid,
			String deptUuid) {
		return getBeanListWithSql(UserTask.class, "SELECT count(*) dataCount,t.own_user uuid,p.loginname userName FROM new_data_department_user_" + deptUuid + 
				" t left join user_user p on t.own_user=p.uuid "
				+ "WHERE t.call_count = 0 AND t.batch_uuid = ? AND t.own_department = ? AND t.intent_type is null GROUP BY t.own_user", batchUuid, deptUuid);
	}

	@Override
	public List<UserTask> getUserTaskInfoCallUn(String batchUuid,
			String deptUuid) {
		return getBeanListWithSql(UserTask.class, "SELECT count(*) dataCount,t.own_user uuid,p.loginname userName FROM new_data_department_user_" + deptUuid + 
				" t left join user_user p on t.own_user=p.uuid "
				+ "WHERE t.call_count > 0 and ( t.last_call_result is null OR t.last_call_result = '' ) AND t.batch_uuid = ? AND t.own_department = ? AND t.intent_type is null GROUP BY t.own_user", batchUuid, deptUuid);
	}

	@Override
	public List<UserTask> getUserTaskInfoCall(String batchUuid, String deptUuid) {
		return getBeanListWithSql(UserTask.class, "SELECT count(*) dataCount,t.own_user uuid,p.loginname userName FROM new_data_department_user_" + deptUuid + 
				" t left join user_user p on t.own_user=p.uuid "
				+ "WHERE t.last_call_result = '1' AND t.batch_uuid = ? AND t.own_department = ?  GROUP BY t.own_user", batchUuid, deptUuid);
	}
	
	
	@Override
	public boolean recycleByUser(String userId) {
		//取到用户的归属部门
		String deptUuid = userService.getByUuid(UUID.UUIDFromString(userId)).getDepartment();
		String[] users = {userId};
		//取到用户的所有任务的所有批次id
		List<String> batchIds = jdbcTemplate.queryForList("SELECT DISTINCT(batch_uuid) FROM new_data_department_user_"+deptUuid+" WHERE own_user = ? AND intent_type is NULL",String.class,userId);
		//回收的类型 0：未拨打  1：已打未通  2：已打已通
		Integer[] collects = {0,1,2};
		if(batchIds != null && batchIds.size()>0){
			for(String batchid : batchIds){
				if(StringUtils.isNotBlank(batchid)){
					collectData(batchid, deptUuid, users, collects);
				}
			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int getTotalDataByDept(String deptUuid) {
		return getTotalData(deptUuid);
	}

}

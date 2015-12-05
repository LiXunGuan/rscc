package com.ruishengtech.rscc.crm.ui.linkservice;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DepartmentTableServiceImp;
import com.ruishengtech.rscc.crm.ui.SysSessionListener;
import com.ruishengtech.rscc.crm.ui.mw.send.AgentManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Service
@Transactional
public class UserDataLinkService extends BaseService {
	
	@Autowired
	private DatarangeService datarangeService;

	@Autowired
	private DepartmentTableServiceImp departmentTableService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NewUserDataService userDataService;
	
	@Autowired
	private CustomerPoolService poolService;
	
	@Autowired
	private DataBatchService dataBatchService;
	
	public boolean saveUser(User user, String[] permissions, String[] dataranges) {
		user.setDate(new Date());
		userService.save(user, permissions, dataranges);
		Map<String, SysConfig> map = SysConfigManager.getInstance().getDataMap();
		userDataService.save(user.getUid(), user.getDepartment(), 
				Integer.parseInt(map.get("sys.data.getDataSingleLimit")==null?"500":map.get("sys.data.getDataSingleLimit").getSysVal()),
				Integer.parseInt(map.get("sys.data.getDataDayLimit")==null?"1000":map.get("sys.data.getDataDayLimit").getSysVal()), 
				Integer.parseInt(map.get("sys.data.getDataTotalLimit")==null?"1000":map.get("sys.data.getDataTotalLimit").getSysVal()),
				Integer.parseInt(map.get("sys.data.getIntentTotalLimit")==null?"500":map.get("sys.data.getIntentTotalLimit").getSysVal()));
		AgentManager.addAgent(user);
		//创建两个和部门关联的表，用于放数据
		return true;
	}
	
	public String deleteUser(UUID uuid) {
		JSONObject jsonObject = new JSONObject();
		//如果用户id是0（超级管理员admin）的话，则直接提示成功，不进行删除操作
		if ("0".equals(uuid.toString())) {
			return jsonObject.put("success", true).toString();
		}
		User temp = userService.getByUuid(uuid);
		if (!userDataService.deleteById(uuid)) {
			return jsonObject.put("success", false).put("message", "不能删除有未完成任务的用户").toString();
		}
		userService.deleteById(uuid);
		AgentManager.extenUbind(temp);
		AgentManager.agentMap.removeValue(temp.getLoginName());
		AgentManager.deleteAgent(temp.getLoginName());
		return jsonObject.put("success", true).toString();
	}
	
	public String updateUser(User user, String permissionRoles, String roledataranges) {
		JSONObject jsonObject = new JSONObject();
		UserTask userTask = getByUuid(UserTask.class, user.getUuid());
		if(!userTask.getDepartmentUuid().equals(user.getDepartment())) {
			if (userTask.getDataCount() > 0) {
				return jsonObject.put("success", false).put("message", "不能修改有未完成任务的用户的部门").toString();
			} else {//修改部门uuid
				jdbcTemplate.update("update new_data_department_user set department_uuid=? where uuid=?", user.getDepartment(), user.getUid());
			}
		}
		userService.update(user, permissionRoles.split(","), roledataranges.split(";"));
		AgentManager.addAgent(user);
		//修改角色信息
		HttpSession session = SysSessionListener.sessionMap.get(user.getLoginName());
		if (session != null) {
			session.setAttribute(user.getLoginName(), user);
		}
		return jsonObject.put("success", true).toString();
	}
	
	public String batchDeleteUser(String[] uuids) {
		for (String uuid : uuids) {
			this.deleteUser(UUID.UUIDFromString(uuid));
		}
		return new JSONObject().put("success", true).put("message", "还有一些未能删除的用户，请移除他们的任务再尝试删除").toString();
	}
	
	
	
	public boolean saveDatarange(Datarange datarange) {
		datarange.setDate(new Date());
		
		//创建两个和部门关联的表，用于放数据
		datarangeService.save(datarange);
//		departmentTableService.save(datarange.getUid(), datarange.getDatarangeName(), 10000);
		// chengxin 2015.11.20 部门上限为100000
		departmentTableService.save(datarange.getUid(), datarange.getDatarangeName(), 100000);
		
//		// ------------------Create by ChengXin Start-------------
//		
//		// 查询该部门的父部门是否有关联批次
//		List<String> batchuuids = dataBatchService.getDepartmentDataBatchs(datarange.getParentUuid());
//		
//		if(batchuuids.size() > 0){
//			for (String batchuuid : batchuuids) {
//				jdbcTemplate.update("insert into new_data_batch_department_link(data_batch_uuid,department_uuid,single_limit,day_limit) values(?,?,?,?)", batchuuid, datarange.getUid(), 5000, 5000);
//			}
//		}
//		
//		// ------------------Create by ChengXin End-------------
		
		
		//关联默认的客户池
		CustomerPool customerPool = poolService.getDefaultPool();
		
		setDefaultPool(customerPool, datarange.getUid());
		
		return true;
	}
	
	/**
	 * 给添加的部门设置默认的客户池
	 * @param customerPool
	 * @param deptUuid
	 */
	public void setDefaultPool(CustomerPool customerPool , String deptUuid) {
		
		jdbcTemplate.update("INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES (?, ?);",customerPool.getUid(),deptUuid);
	}
	
	public String updateDatarange(Datarange datarange) {
		if(datarange.getParentUuid()==null)
			datarange.setParentUuid("");
		JSONObject jsonObject2 = new JSONObject();
		datarangeService.update(datarange);
		jdbcTemplate.update("update new_data_department set department_name=? where uuid=?", datarange.getDatarangeName(), datarange.getUid());
		jsonObject2.put("success", datarangeService.update(datarange));
		return jsonObject2.toString();
	}
	
	public String deleteDatarange(UUID uuid) {
		
		JSONObject jsonObject2 = new JSONObject();
		
		if (!departmentTableService.deleteById(uuid)) {
			return jsonObject2.put("success", false).put("message", "不能删除包含数据的部门").toString();
		}
		jdbcTemplate.update("update new_data_department_user set department_uuid=? where department_uuid=?", "01", uuid.toString());
		datarangeService.deleteById(uuid);
		
		return jsonObject2.put("success", true).toString();
		
	}
	
	public String batchDeleteDatarange(String[] uuids) {
		for (String uuid : uuids) {
			this.deleteDatarange(UUID.UUIDFromString(uuid));
		}
		return new JSONObject().put("success", true).put("message", "还有一些未能删除的部门，请移除他们的数据再尝试删除").toString();
	}
	
}

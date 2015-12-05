package com.ruishengtech.rscc.crm.ui.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.AESTools;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.rscc.crm.data.condition.TaskCondition;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallHistoryManager;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.send.CallManager;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("userdata")
public class UserDataController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private DataContainerService dataContainerService;

	@Autowired
	private DataItemService dataItemService;
	
	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private CallLogService callLogService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@RequestMapping()
	public String index(HttpServletRequest request, Model model) {
		Project myProject = projectService.getByUuid(SessionUtil.getCurrentUser(request).getUuid());
		if(myProject == null || !("1".equals(myProject.getProjectStat())))
			return "data/nodata";
		model.addAttribute("model", "userdata");
		model.addAttribute("uuid", SessionUtil.getCurrentUser(request).getUid());
		List<String> datacontainers = dataContainerService.getDepartmentDataContainers(SessionUtil.getCurrentUser(request).getDepartment());
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		Set<String> datacontainerSet = dataContainerService.getDepartmentsDataContainers(departments);
		datacontainerSet.addAll(datacontainers);
		model.addAttribute("pools", dataContainerService.getDatas("1", datacontainerSet));
		model.addAttribute("sources", dataTaskService.getDataSources(SessionUtil.getCurrentUser(request).getUid()));
		model.addAttribute("dataNumber", sysConfigService.getSysConfigByKey("sys.data.getDataNumber").getSysVal());
		
		
		model.addAttribute("iframecontent","data/user-data");
		return "iframe";
		
//		return "data/user-data";
//		return "data/user-pool-data";
//		return "screenpop/callout";
	}
	
	@RequestMapping("taskAllocate")
	public String getTaskAllocate(HttpServletRequest request, String uuid, Model model){
		model.addAttribute("uuid", uuid);
		model.addAttribute("totalTask", dataTaskService.getTaskCount(uuid));
		model.addAttribute("unallocateTask", dataTaskService.getUnallocateTaskCount(uuid));
		model.addAttribute("totalUser", userService.getUserCount());
		model.addAttribute("projectUser", projectService.getUserCount(uuid));
		model.addAttribute("completeTask", dataTaskService.getCompleteTaskCount(uuid));
		return "data/task-allocate";
	}
	
	@RequestMapping("taskCollection")
	public String getTaskCollection(HttpServletRequest request, String uuid, Model model){
		model.addAttribute("uuid", uuid);
		model.addAttribute("selectedUsers", projectService.getUserTasks());
		return "data/task-collection";
	}
	
	@RequestMapping("allocate")
	@ResponseBody
	public String allocate(HttpServletRequest request, String uuid, String allocate, String allocateMax, Model model) {
		projectService.allocate(uuid, Integer.valueOf(allocate), StringUtils.isNotBlank(allocateMax)?Integer.parseInt(allocateMax):0);
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("collection")
	@ResponseBody
	public String collection(HttpServletRequest request, String uuid, String[] users, String collection, String collectionMax, String containAll, Model model) {
		if(users==null)
			return new JSONObject().put("success", true).toString();
		projectService.collection(uuid, users, Integer.valueOf(collection), "on".equals(containAll), StringUtils.isNotBlank(collectionMax)?Integer.parseInt(collectionMax):0);
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("checkAll")
	@ResponseBody
	public String checkAll(HttpServletRequest request,
			HttpServletResponse response, String currentDataTable,
			Model model) {

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<Project> list = projectService.getProjects();
		for (Project i:list) {
			jsonArray.put(i.getUid());
		}

		jsonObject2.put("projects", jsonArray);
		return jsonObject2.toString();
	}
	
	@RequestMapping("batDelete")
	@ResponseBody
	public String deleteProjects(HttpServletRequest request, Model model){
		JSONObject jsonObject2 = new JSONObject();
		String[] uuids = request.getParameterValues("uuids[]");
		jsonObject2.put("success", projectService.batDelete(uuids));
		return jsonObject2.toString();
	}
	
	@RequestMapping("changeStat")
	@ResponseBody
	public String changeStat(HttpServletRequest request, String uuid, Boolean stat) {
		projectService.changeProjectStat(uuid, stat?"1":"0");
		return "";
	}
	
	@RequestMapping("getData")
	public String getData(HttpServletRequest request, String uuid, Model model){
		model.addAttribute("entry", projectService.getByUuid(UUID.UUIDFromString(uuid)));
		model.addAttribute("datas", dataContainerService.getDatas());
		return "data/get-from-data";
	}
	
	@RequestMapping("getdata")
	@ResponseBody
	public String getdata(HttpServletRequest request, String[] sources, String uuid, Integer getData, Integer allocateMax, String[] datas, Model model){
		JSONObject json = new JSONObject();
//		List<DataContainer> list = dataContainerService.getDatas();
//		datas = new String[list.size()];
//		for(int i = 0 ; i < list.size() ; i++)
//			datas[i] = list.get(i).getDataTable();
//		List<String> list = projectService.getDataSources(uuid);
		List<String> list = new ArrayList<>();
		List<DataContainer> containerList = dataContainerService.getDatas();
		List<String> sourceList = Arrays.asList(sources);
		for (DataContainer dataContainer : containerList) {
			if(sourceList.contains(dataContainer.getContainerName()))
				list.add(dataContainer.getDataTable());
		}
		//上面的方法貌似和dataTaskService.getDataSources的重复了
		datas = new String[list.size()];
		list.toArray(datas);
		int num = projectService.getData(uuid, getData, allocateMax, datas);
		return json.put("success", true).put("num", num).toString();
	}
	
	@RequestMapping("revertData")
	public String revertData(HttpServletRequest request, String uuid, Model model){
		model.addAttribute("entry", projectService.getByUuid(UUID.UUIDFromString(uuid)));
		model.addAttribute("datas", projectService.getDataAllocate(uuid));
		return "data/revert-to-data";
	}
	
	@RequestMapping("revertdata")
	@ResponseBody
	public String revertdata(HttpServletRequest request, String uuid, Integer revertData, String[] datas, Model model){
		JSONObject json = new JSONObject();
		if(datas == null)
			return json.put("success", true).toString();
		projectService.revertData(uuid, revertData, datas);
		return json.put("success", true).toString();
	}
	
	@RequestMapping("review")
	@ResponseBody
	public String importData(HttpServletRequest request,
			HttpServletResponse response, TaskCondition taskCondition,
			Model model) {
		taskCondition.setRequest(request);
		taskCondition.setTaskTable(SessionUtil.getCurrentUser(request).getUid());
		PageResult<DataTask> pageResult = dataTaskService.queryPage(taskCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String defVal = sysConfigService.getSysConfigByKey("addZero").getSysVal();
		List<DataTask> list = pageResult.getRet();
		for (DataTask i:list) {
			JSONObject jsonObject = new JSONObject(i);
			if (i.getAllocateTime()!=null)
				jsonObject.put("allocateTime", DateUtils.getDateString(i.getAllocateTime()));
			if (i.getLastcalltime()!=null)
				jsonObject.put("lastcalltime", DateUtils.getDateString(i.getLastcalltime()));
			jsonObject.put("defVal",defVal);
//			if (i.getItemOwner() != null)
//				jsonObject.put("itemOwner", projectService.getByUuid(UUID.UUIDFromString(i.getItemOwner())).getProjectName());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("completeCount", projectService.getByUuid(SessionUtil.getCurrentUser(request).getUuid()).getUserCount());
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("revertOneData")
	@ResponseBody
	public String revertOneData(HttpServletRequest request, String dataUuid, String projectUuid, Model model){
		JSONObject json = new JSONObject();
		dataTaskService.deleteById(projectUuid, UUID.UUIDFromString(dataUuid));
		return json.put("success", true).toString();
	}
	
	@RequestMapping("checkDataAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<DataTask> list = dataTaskService.getTasks(SessionUtil.getCurrentUser(request).getUid());
		for (DataTask i:list) {
//			JSONObject jsonObject = new JSONObject(i);
			jsonArray.put(i.getUid());
		}
		jsonObject2.put("items", jsonArray);
		return jsonObject2.toString();
	}
	
	@RequestMapping("batDataDelete")
	@ResponseBody
	public String deleteSelectDatas(HttpServletRequest request, String taskTable, Model model){
		JSONObject jsonObject2 = new JSONObject();
		String[] uuids = request.getParameterValues("uuids[]");
		jsonObject2.put("success", dataTaskService.batDelete(taskTable, uuids));
		return jsonObject2.toString();
	}
	
	@RequestMapping("call")
	@ResponseBody
	public String callPhone(HttpServletRequest request, String taskUuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		User currentUser = SessionUtil.getCurrentUser(request);
		DataTask data = dataTaskService.getByUuid(currentUser.getUid(), UUID.UUIDFromString(taskUuid));
		JSONObject json = CallManager.makeCall(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), data.getItemPhone(), SessionUtil.getCurrentUser(request), "dataCall");
		if(json.optInt("exit_code",1)!=0)
			return jsonObject2.put("success", false).toString();
		
		CallLog callLog = new CallLog();
		callLog.setAgent_id(currentUser.getUid());
		callLog.setAgent_name(currentUser.getLoginName());
		callLog.setCall_phone(data.getItemPhone());
		callLog.setIn_out_flag("呼出");
		callLog.setCall_time(new Date());
		callLog.setData_id(data.getUid());
		callLog.setData_source(data.getDataSource());
		callLog.setCall_session_uuid(json.getString("call_session_uuid"));
		callLogService.save(callLog);
//		callResult.setCstmserviceName(data.getItemName());
//		callResult.setCstmserviceId(data.getUid());
//		callResult.setInOutTag(CstmIssue.CALLOUT);
//		callResult.setCstmserviceStatus(CstmIssue.STATUS0);
//		callResult.setCstmserviceStartTime(new Date());
//		callResult.setCstmserviceDescription(data.getItemAddress());
//		callResult.setUserName(currentUser.getLoginName());
//		callResult.setProjectUUID(currentUser.getUid());
//		callResult.setPhoneNumber(data.getItemPhone());
//		callResult.setContainerUUID(data.getDataSource());
//		callResult.setCallSessionUuid(json.getString("call_session_uuid"));
		
		jsonObject2.put("success", true).put("call_session_uuid", json.getString("call_session_uuid")).put("entry", new JSONObject(callLog).put("call_time",DateUtils.getDateString(callLog.getCall_time())));
		return jsonObject2.toString();
	}
	
	@RequestMapping("callpop")
	@ResponseBody
	public String callPhonePop(HttpServletRequest request, String taskUuid, Integer zero, Model model){
		JSONObject jsonObject2 = new JSONObject();
		User currentUser = SessionUtil.getCurrentUser(request);
		DataTask data = dataTaskService.getByUuid(currentUser.getUid(), UUID.UUIDFromString(taskUuid));
		JSONObject json = CallManager.makeCall(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), 0==zero?"0" + data.getItemPhone():data.getItemPhone(), SessionUtil.getCurrentUser(request), "dataCall");
		if(json.optInt("exit_code",1)!=0)
			return jsonObject2.put("success", false).toString();
		
		CallLog callLog = new CallLog();
		callLog.setAgent_id(currentUser.getUid());
		callLog.setAgent_name(currentUser.getLoginName());
		callLog.setCall_phone(data.getItemPhone());
		callLog.setIn_out_flag("呼出");
		callLog.setCall_time(new Date());
		callLog.setData_id(data.getUid());
		callLog.setData_source(data.getDataSource());
		callLog.setCall_session_uuid(json.getString("call_session_uuid"));
		callLogService.save(callLog);
		
		jsonObject2.put("success", true).put("call_session_uuid", json.getString("call_session_uuid")).put("entry", new JSONObject(callLog).put("call_time",DateUtils.getDateString(callLog.getCall_time())));
		return jsonObject2.toString();
	}
	
	@RequestMapping("addCallTimes")
	@ResponseBody
	public String addCallTimes(HttpServletRequest request, String taskUuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		dataTaskService.addCallTimes(SessionUtil.getCurrentUser(request).getUid(), taskUuid);
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
	@RequestMapping("toPool")
	@ResponseBody
	public String moveTo(HttpServletRequest request, String dataUuid, String poolUuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		if(StringUtils.isBlank(poolUuid))
			return jsonObject2.put("success", false).toString();
		String projectUuid = SessionUtil.getCurrentUser(request).getUid();
		dataTaskService.moveTo(projectUuid, dataUuid, poolUuid);
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
	@RequestMapping("getMoreInfo")
	public String getMoreInfo(HttpServletRequest request, String uuid, Model model){
		DataTask entry = dataTaskService.getByUuid(SessionUtil.getCurrentUser(request).getUid(), UUID.UUIDFromString(uuid));
		entry.setDataSource(dataContainerService.getDataContainerByTable(entry.getDataSource()).getContainerName());
		
		boolean b = userService.hasPermission(SessionUtil.getCurrentUser(request).getUid(), "90");
		if("true".equals(SysConfigManager.getInstance().getDataMap().get("hiddenPhoneNumber").getSysVal()) && !b)
			entry.setItemPhone(PhoneUtil.hideNumber(entry.getItemPhone()));
		
		model.addAttribute("entry", entry);
		model.addAttribute("maps", callLogService.getTableDef());
		return "data/get-more-info";
	}
	
	@RequestMapping("getDataInfo")
	@ResponseBody
	public String getDataInfo(HttpServletRequest request, String uuid, Model model){
		DataTask entry = CallHistoryManager.get(SessionUtil.getCurrentUser(request).getLoginName(), uuid);
		if (entry == null){
			entry = dataTaskService.getByUuid(SessionUtil.getCurrentUser(request).getUid(), UUID.UUIDFromString(uuid));
			entry.setDataSource(dataContainerService.getDataContainerByTable(entry.getDataSource()).getContainerName());
			entry.setItemAddress(CallManager.getPhoneLocation(entry.getItemPhone()));
		}
//		entry.setItemPhone(PhoneUtil.hideNumber(entry.getItemPhone()));
		model.addAttribute("entry", entry);
		JSONObject innerJson = new JSONObject(entry.getItemJson());
		JSONObject jsonObject = new JSONObject(entry);
		jsonObject.put("itemJson", innerJson);
		return jsonObject.toString();
	}
	
	@RequestMapping("moveToCustomer")
	@ResponseBody
	public String moveToCustomer(HttpServletRequest request, String dataUuid, Model model) {
		DataTask dataTask = dataTaskService.getByUuid(SessionUtil.getCurrentUser(request).getUid(), UUID.UUIDFromString(dataUuid));
		dataItemService.deleteById(dataTask.getDataSource(), UUID.UUIDFromString(dataUuid));
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("deleteByPhone")
	@ResponseBody
	public String deleteByPhone(HttpServletRequest request, String customerPhone, Model model) throws UnsupportedEncodingException {
		Project myProject = projectService.getByUuid(SessionUtil.getCurrentUser(request).getUuid());
		if (myProject == null) {
			return new JSONObject().toString();
		}
		byte[] decryptFrom = AESTools.parseHexStr2Byte(customerPhone);
		byte[] decryptResult = AESTools.decrypt(decryptFrom, "shrsrjjsyxgs");
		String realPhone = new String(decryptResult,"UTF-8");
		List<DataTask> list = dataTaskService.getByPhone(SessionUtil.getCurrentUser(request).getUid(), realPhone);
		for (DataTask d : list) {
			dataItemService.deleteById(d.getDataSource(), d.getUuid());
		}
		return new JSONObject().put("success", true).toString();
	}
	
}

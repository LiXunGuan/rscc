package com.ruishengtech.rscc.crm.ui.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.util.HttpRequest;
import com.ruishengtech.rscc.crm.billing.service.imp.BillServiceImp;
import com.ruishengtech.rscc.crm.data.condition.GroupCallCondition;
import com.ruishengtech.rscc.crm.data.condition.GroupCallDataCondition;
import com.ruishengtech.rscc.crm.data.condition.ProjectCondition;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.model.GroupCall;
import com.ruishengtech.rscc.crm.data.model.GroupCallDataLink;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.data.service.imp.GroupCallServiceImp;
import com.ruishengtech.rscc.crm.ui.mw.send.GroupCallManager;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("data/groupCall")
public class GroupCallController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private GroupCallServiceImp groupCallService;
	
	@Autowired
	private DataContainerService dataContainerService;
	
	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BillServiceImp billService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "groupCall");
		model.addAttribute("titles", new String[]{"人员名","部门","数据量","完成量","开始日期"});
		model.addAttribute("columns", new String[]{"projectName","department","dataCount","userCount","createDate"});
		
		model.addAttribute("iframecontent","data/data-group-call");
		return "iframe";
		
//		return "data/data-group-call";
	}
	
	@RequestMapping("add")
	public String addTask(Model model){
		model.addAttribute("model", "groupCall");
		model.addAttribute("title", "添加任务");
		HashMap<String, String> queues = new HashMap<String, String>();
		JSONArray queueArray = QueueManager.getAllQueue();
		for (int i = 0 ; i < queueArray.length() ; i++ ) {
			if(queueArray.getJSONObject(i).opt("queue_exten") == null)
				continue;
			queues.put(queueArray.getJSONObject(i).getString("queue_exten") + "#" + queueArray.getJSONObject(i).getInt("queue_id"), queueArray.getJSONObject(i).getString("queue_name"));
		}
		HashMap<String, String> accessNumbers = new HashMap<String, String>();
		JSONArray accessNumberArray = QueueManager.getAllAccessNumber();
		for (int i = 0 ; i < accessNumberArray.length() ; i++ ) {
			accessNumbers.put(accessNumberArray.getJSONObject(i).getString("accessnumber"), String.valueOf(accessNumberArray.getJSONObject(i).getInt("concurrency")));
		}
		
		model.addAttribute("queues", queues);
		model.addAttribute("accessNumbers", accessNumbers);
//		model.addAttribute("users", userService.getAllUser());
//		model.addAttribute("addedUsers", projectService.getUserTasks());
		model.addAttribute("datas", dataContainerService.getDatas("0"));
		model.addAttribute("addedbyothers", groupCallService.getAdded());
		return "data/add-task-wizard";
	}
	
	//更新数据量的操作在去重操作中，若以后不再需要去重，则更新需要写在下面
	@RequestMapping("wizard")
	@ResponseBody
	public String wizard(HttpServletRequest request, GroupCall groupCall, String[] users, String[] datas, Model model){
		JSONObject jsonObject = new JSONObject();
		groupCall.setData_src_url(SpringPropertiesUtil.getProperty("sys.rscc") + SpringPropertiesUtil.getProperty("sys.data.call.getdata"));
		groupCall.setData_dst_url(SpringPropertiesUtil.getProperty("sys.rscc") + SpringPropertiesUtil.getProperty("sys.data.call.returncall"));
		JSONObject responseJson = GroupCallManager.createGroupCall(groupCall);
		if(responseJson.getInt("exit_code") != 0)
			return jsonObject.put("success", false).toString();
		groupCall.setGroupcall_id(responseJson.getString("groupcall_id"));
//		groupCallService.updateUsers(groupCall.getGroupcall_id(), users);
		jsonObject.put("success", groupCallService.save(groupCall));
		if(datas!=null) {
			for(String d:datas)
				groupCallService.importToTask(d, groupCall.getGroupcall_id());
		}
		return jsonObject.toString();
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, GroupCallCondition groupCallCondition,
			Model model) {
		groupCallCondition.setRequest(request);
		PageResult<GroupCall> pageResult = groupCallService.queryPage(groupCallCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject ret = GroupCallManager.queryConcurrency();
		List<GroupCall> list = pageResult.getRet();
		for (GroupCall i:list) {
			JSONObject jsonObject = new JSONObject(i);
			
			//取队列状态
			JSONObject statJson = new JSONObject();
			String[] array = i.getDst_exten().split("#");
			String queueId = null;
			if(array.length == 2) {
				queueId = array[1];
			} else {
				queueId = "1";
			}
			if(StringUtils.isNotBlank(queueId)) {
				JSONArray json = QueueManager.getQueueRuntime(queueId);
				if(json.length() > 0)
					statJson = json.getJSONObject(0);
			}
			
			jsonObject.put("dst_exten", statJson.optString("queue_name"));
			if("1".equals(i.getStat())) {
				jsonObject.put("queueCount", statJson.optString("member_count"));
				jsonObject.put("queueStat", statJson.optInt("not_ready_count") + "/" + (statJson.optInt("not_ready_count") + statJson.optInt("idle_ready_count") + statJson.optInt("busy_ready_count")));
			
				if("1".equals(i.getStrategy())) {
					jsonObject.put("concurrency", (ret.optJSONArray(i.getGroupcall_id())==null?0:ret.optJSONArray(i.getGroupcall_id()).length()));
				} else {
					jsonObject.put("concurrency", (ret.optJSONArray(i.getGroupcall_id())==null?0:ret.optJSONArray(i.getGroupcall_id()).length()) + "/" + i.getConcurrency());
				}
			}
			jsonObject.put("strategy", GroupCallManager.strategyMap.get(i.getStrategy()));
			jsonObject.put("dataStat", new JSONObject(groupCallService.getCalledDataStat(i.getGroupcall_id())));
			
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	//从这里请求数据
	@RequestMapping("getGroupCallData")
	@ResponseBody
	public String getCallData(HttpServletRequest request, HttpServletResponse response, Model model) {

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject requestJson = HttpRequest.getRequestJson(request);
		List<GroupCallDataLink> list = groupCallService.getData(requestJson.getString("groupcall_id"),requestJson.getInt("qty"));
		for (GroupCallDataLink i:list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data_id", i.getDataId());
			jsonObject.put("phonenumber", i.getDataPhone());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("groupcall_id", requestJson.getString("groupcall_id"));
		jsonObject2.put("qty", requestJson.getString("qty"));
		jsonObject2.put("data_list", jsonArray);
		return jsonObject2.toString();
	}
	
	//呼叫接通时调用这个方法
	@RequestMapping("returnGroupCall")
	@ResponseBody
	public String returnCallData(HttpServletRequest request, HttpServletResponse response, Model model) {
		JSONObject jsonObject = new JSONObject();
		JSONObject requestJson = HttpRequest.getRequestJson(request);
		if("groupcall_stopped_notify".equals(requestJson.optString("command"))) {
			GroupCall g = groupCallService.getGroupCallByCallId((requestJson.optString("groupcall_id", "0")));
			if (g == null)
				return new JSONObject().put("exit_code", 0).toString();
			groupCallService.stopTask(g);//关闭状态
		} else {
//			JSONObject obj = requestJson.optJSONObject("data");
//			if(obj != null)
			groupCallService.changeDataStat(requestJson);
		}
		return jsonObject.put("exit_code",0).toString();
	}
	
	@RequestMapping("changeStat")
	@ResponseBody
	public String changeStat(HttpServletRequest request, String uuid, Boolean stat) {
		GroupCall g = groupCallService.getByUuid(UUID.UUIDFromString(uuid));
		JSONObject responseJson;
		if (stat){
			responseJson = GroupCallManager.startGroupCall(g.getGroupcall_id());
			if(responseJson.optInt("exit_code",1) == 0)
				groupCallService.changeStat(uuid, "1");
		}
		else {
			responseJson = GroupCallManager.stopGroupCall(g.getGroupcall_id());
			if(responseJson.optInt("exit_code",1) == 0)
				groupCallService.changeStat(uuid, "2");//等待关闭状态
		}
		if(responseJson.optString("err_msg").contains("已经停止"))
			groupCallService.stopTask(g);
		else if(responseJson.optString("err_msg").contains("非关闭状态"))
			groupCallService.changeStat(uuid, "1");//开启状态
		return new JSONObject().put("success", responseJson.optInt("exit_code",1) == 0).put("stat", stat).toString();
	}
	
	@RequestMapping("stopGroupCall")
	@ResponseBody
	public String stopGroupCall(HttpServletRequest request) {
		JSONObject requestJson = HttpRequest.getRequestJson(request);
		GroupCall g = groupCallService.getGroupCallByCallId((requestJson.optString("groupcall_id", "0")));
		if (g == null)
			return new JSONObject().put("exit_code", 0).toString();
		groupCallService.stopTask(g);//关闭状态
		return new JSONObject().put("exit_code", 0).toString();
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteUser(UUID uuid, Model model){
		JSONObject jsonObject = new JSONObject();
		GroupCall g = groupCallService.getByUuid(uuid);
		JSONObject responseJson = GroupCallManager.deleteGroupCall(g.getGroupcall_id());
		if(responseJson.optInt("exit_code",1) != 0)
			return jsonObject.put("success", false).toString();
		jsonObject.put("success", groupCallService.deleteById(uuid));
		return jsonObject.toString();
	}
	
	@RequestMapping("getData")
	public String getData(HttpServletRequest request, String uuid, Model model){
//		model.addAttribute("entry", projectService.getByUuid(UUID.UUIDFromString(uuid)));
		model.addAttribute("groupId", uuid);
		model.addAttribute("dataCount", groupCallService.getDataCount(uuid));
		model.addAttribute("uncompleteCount", groupCallService.getUncompleteDataCount(uuid));
		Map<String, DataContainer> map = groupCallService.getDataStats(uuid);
		List<String> list = new ArrayList<>();
		for (DataContainer dataContainer : map.values()) {
			list.add(dataContainer.getUid());
		}
		model.addAttribute("selecteddatas", map);
		model.addAttribute("selectedlist", list);
		model.addAttribute("addedbyothers", groupCallService.getAdded());
		model.addAttribute("datas", dataContainerService.getDatas());
		return "data/group-call-get-data";
	}
	
	@RequestMapping("getfromdata")
	@ResponseBody
	public String getdata(HttpServletRequest request, String uuid, String[] datas, String[] unselected, Model model){
		JSONObject json = new JSONObject();
		if(datas!=null) {
			for(String d:datas)
				groupCallService.importToTask(d, uuid);
		}
		groupCallService.getDataStats(uuid);
		removedata(request, uuid, 2, unselected, model);
		return json.put("success", true).toString();
	}
	
	@RequestMapping("removeData")
	public String removeData(HttpServletRequest request, String uuid, Model model){
//		model.addAttribute("entry", projectService.getByUuid(UUID.UUIDFromString(uuid)));
		model.addAttribute("groupId", uuid);
		model.addAttribute("dataCount", groupCallService.getDataCount(uuid));
		model.addAttribute("uncompleteCount", groupCallService.getUncompleteDataCount(uuid));
		model.addAttribute("selecteddatas", groupCallService.getDataStats(uuid));
		return "data/group-call-remove-data";
	}
	
	@RequestMapping("removedata")
	@ResponseBody
	public String removedata(HttpServletRequest request, String uuid, Integer removeData, String[] datas, Model model){
		JSONObject json = new JSONObject();
		if(datas == null)
			return json.put("success", true).toString();
		groupCallService.removeDatas(uuid, removeData, datas);
		return json.put("success", true).toString();
	}
	
	@RequestMapping("reviewModal")
	public String showReviewData(HttpServletRequest request,
			HttpServletResponse response, String uuid,
			Model model) {
		model.addAttribute("groupCallId", uuid);
		model.addAttribute("dataCount", groupCallService.getDataCount(uuid));
		model.addAttribute("uncompleteCount", groupCallService.getUncompleteDataCount(uuid));
		model.addAttribute("ready", groupCallService.getReadyToCallDataCount(uuid));
		model.addAttribute("called", groupCallService.getCalledDataCount(uuid));
		model.addAttribute("projects", projectService.getProjects());
		return "data/call-data-review";
	}
	
	@RequestMapping("review")
	@ResponseBody
	public String importData(HttpServletRequest request,
			HttpServletResponse response, GroupCallDataCondition groupCallDataCondition,
			Model model) {
		groupCallDataCondition.setRequest(request);
		PageResult<GroupCallDataLink> pageResult = groupCallService.queryDataPage(groupCallDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<GroupCallDataLink> list = pageResult.getRet();
		for (GroupCallDataLink i:list) {
			JSONObject jsonObject = new JSONObject(i);
			jsonObject.put("data_phone", i.getDataPhone());
			jsonObject.put("container", i.getContainer());
			jsonObject.put("call_flag", i.getCallFlag());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("deleteOneData")
	@ResponseBody
	public String deleteOneData(HttpServletRequest request, String groupCallId, String dataPhone, Model model){
		JSONObject json = new JSONObject();
		groupCallService.removeOneData(groupCallId, dataPhone);
		return json.put("success", true).toString();
	}
	
	@RequestMapping("collectionPhone")
	@ResponseBody
	public String collection(HttpServletRequest request, String groupCallId, Model model){
		JSONObject json = new JSONObject();
		groupCallService.collection(groupCallId);
		return json.put("success", true).toString();
	}
	
	@RequestMapping("batchDeleteData")
	@ResponseBody
	public String batchDeleteData(HttpServletRequest request, String groupCallId, Model model){
		JSONObject json = new JSONObject();
		String[] phones = request.getParameterValues("phones[]");
		groupCallService.batchRemoveData(groupCallId, phones);
		return json.put("success", true).toString();
	}
	
	@RequestMapping("change")
	public String changeData(HttpServletRequest request,
			HttpServletResponse response, String uuid,
			Model model) {
		model.addAttribute("model", "groupCall");
		model.addAttribute("title", "任务");
		HashMap<String, String> queues = new HashMap<String, String>();
		JSONArray queueArray = QueueManager.getAllQueue();
		for (int i = 0 ; i < queueArray.length() ; i++ ) {
			if(queueArray.getJSONObject(i).opt("queue_exten") == null)
				continue;
			queues.put(queueArray.getJSONObject(i).getString("queue_exten") + "#" + queueArray.getJSONObject(i).getInt("queue_id"), queueArray.getJSONObject(i).getString("queue_name"));
		}
		HashMap<String, String> accessNumbers = new HashMap<String, String>();
		JSONArray accessNumberArray = QueueManager.getAllAccessNumber();
		for (int i = 0 ; i < accessNumberArray.length() ; i++ ) {
			accessNumbers.put(accessNumberArray.getJSONObject(i).getString("accessnumber"), String.valueOf(accessNumberArray.getJSONObject(i).getInt("concurrency")));
		}
		
		model.addAttribute("accessNumbers", accessNumbers);
		model.addAttribute("queues", queues);
		model.addAttribute("entry", groupCallService.getByUuid(UUID.UUIDFromString(uuid)));
		return "data/update-group-data";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateUser(HttpServletRequest request,
			HttpServletResponse response, GroupCall groupCall, Model model) {
		JSONObject jsonObject2 = new JSONObject();
		groupCall.setData_src_url(SpringPropertiesUtil.getProperty("sys.rscc") + SpringPropertiesUtil.getProperty("sys.data.call.getdata"));
		groupCall.setData_dst_url(SpringPropertiesUtil.getProperty("sys.rscc") + SpringPropertiesUtil.getProperty("sys.data.call.returncall"));
		JSONObject responseJson = GroupCallManager.createGroupCall(groupCall);
		if(responseJson.getInt("exit_code") != 0)
			return jsonObject2.put("success", false).toString();
		groupCall.setGroupcall_id(responseJson.getString("groupcall_id"));
		jsonObject2.put("success", groupCallService.update(groupCall));
		return jsonObject2.toString();
	}
	
	@RequestMapping("batDelete")
	@ResponseBody
	public String deleteProjects(HttpServletRequest request, String uuids, Model model){
		JSONObject jsonObject2 = new JSONObject();
		String[] ids = request.getParameterValues("uuids[]");
		if(!groupCallService.batDelete(ids))
			return jsonObject2.put("success", false).toString();
		GroupCallManager.deleteGroupCall(ids);
		return jsonObject2.put("success", true).toString();
	}
	
//	@RequestMapping("users")
//	@ResponseBody
//	public String getAllUser(Model model){
//		return new JSONArray(userService.getAllUser()).toString();
//	}
//	
//	@RequestMapping("addUsers")
//	@ResponseBody
//	public String addUsers(HttpServletRequest request, String uid, Model model){
//		String[] users = request.getParameterValues("users[]");
//		projectService.updateUsers(uid, users);
//		return new JSONArray(userService.getAllUser()).toString();
//	}
	
	@RequestMapping("addDatas")
	@ResponseBody
	public String addDatas(HttpServletRequest request, String uid, Model model){
		String[] datas = request.getParameterValues("datas[]");
		if(datas!=null) {
			for(String d:datas)
				projectService.importToProject(d, uid);
			projectService.distinct(uid);
		}
		JSONObject json = new JSONObject();
		json.put("totalTask", dataTaskService.getTaskCount(uid));
		json.put("unallocateTask", dataTaskService.getUnallocateTaskCount(uid));
		json.put("totalUser", userService.getUserCount());
		json.put("projectUser", projectService.getUserCount(uid));
		return json.toString();
	}

	//更新数据量的操作在去重操作中，若以后不再需要去重，则更新需要写在下面
	@RequestMapping("updateWizard")
	@ResponseBody
	public String updateWizard(HttpServletRequest request, String[] users, Model model){
		JSONObject jsonObject = new JSONObject();
		List<String> list = projectService.getAddedUsers();
		for(String u:users){
			if(!list.contains(u)) {
				Project p = new Project();
				p.setProjectName(userService.getByUuid(UUID.UUIDFromString(u)).getLoginName());
				p.setProjectInfo(u);
				p.setProjectStat("0");
				p.setUserCount(0);
				p.setDataCount(0);
				p.setCreateDate(new Date());
				projectService.save(p);
			}
		}
		jsonObject.put("success", true);
		return jsonObject.toString();
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
	
	//没有使用
	@RequestMapping("stats")
	@ResponseBody
	public String getStats(HttpServletRequest request, String uid, Model model){
		JSONObject json = new JSONObject();
		json.put("totalTask", dataTaskService.getTaskCount(uid));
		json.put("unallocateTask", dataTaskService.getTaskCount(uid));
		json.put("totalUser", userService.getUserCount());
		json.put("projectUser", projectService.getUserCount(uid));
		return json.toString();
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
	
	@RequestMapping("checkname/{type}")
	@ResponseBody
	public String checkName(HttpServletRequest request, String description,String uid) {
		if(uid == null && groupCallService.getGroupCallBydescription(description)==null){
			return "true";
		}else if(uid != null && description.equals(groupCallService.getByUuid(UUID.UUIDFromString(uid)).getDescription())){
			return "true";
		}else if(groupCallService.getGroupCallBydescription(description)!=null){
			return "false";
		}
		return "true";
	}
	
	@RequestMapping("checkAll")
	@ResponseBody
	public String checkAll(HttpServletRequest request,
			HttpServletResponse response, ProjectCondition projectCondition,
			Model model) {
		projectCondition.setRequest(request);
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray(projectService.getProjects(projectCondition));
		jsonObject2.put("projects", jsonArray);
		return jsonObject2.toString();
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String saveProject(HttpServletRequest request,
			HttpServletResponse response, Project project, Model model) {
		project.setCreateDate(new Date());
		project.setProjectStat("0");
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isBlank(project.getUid()))
			jsonObject.put("success", projectService.save(project)).put("uid", project.getUid());
		else
			jsonObject.put("success", projectService.update(project)).put("uid", project.getUid());
		return jsonObject.toString();
	}
	
	@RequestMapping("changeProject")
	public String changeUser(UUID uuid, Model model){
		model.addAttribute("model", "project");
		model.addAttribute("title", "修改项目");
		model.addAttribute("titles", new String[]{"项目名","项目信息","项目状态","项目描述"});
		model.addAttribute("names", new String[]{"projectName","projectInfo","projectStat","projectDescribe"});
		Project p = projectService.getByUuid(uuid);
		model.addAttribute("entry", p);
		return "data/update-modal";
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
			HttpServletResponse response, GroupCallDataCondition condition,
			Model model) {
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray(groupCallService.getAllItem(condition));
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
	
	@RequestMapping("batRevert")
	@ResponseBody
	public String batRevert(HttpServletRequest request, String taskTable, Model model){
		JSONObject jsonObject2 = new JSONObject();
		String[] uuids = request.getParameterValues("uuids[]");
		projectService.batRevert(uuids);
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
	@RequestMapping("addCallTimes")
	@ResponseBody
	public String addCallTimes(HttpServletRequest request, String taskTable, String taskUuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		dataTaskService.addCallTimes(taskTable, taskUuid);
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
	@RequestMapping("resetData")
	@ResponseBody
	public String resetData(HttpServletRequest request, String groupcallId, String type) {
		//type为0重置单方接通，1重置双方接通
		groupCallService.resetData(groupcallId, type);
		return new JSONObject().put("success", true).toString();
	}
	
}

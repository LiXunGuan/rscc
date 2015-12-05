package com.ruishengtech.rscc.crm.ui.datamanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.ruishengtech.rscc.crm.data.condition.ProjectCondition;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.datamanager.condition.NewGroupCallCondition;
import com.ruishengtech.rscc.crm.datamanager.condition.NewGroupCallDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.GroupCallData;
import com.ruishengtech.rscc.crm.datamanager.model.NewGroupCall;
import com.ruishengtech.rscc.crm.datamanager.model.NewGroupCallDataLink;
import com.ruishengtech.rscc.crm.datamanager.model.NewGroupCallResultLink;
import com.ruishengtech.rscc.crm.datamanager.service.DeptDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.NewGroupCallServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.send.GroupCallManager;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("newdata/groupCall")
public class NewGroupCallController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private NewGroupCallServiceImp groupCallService;
	
	@Autowired
	private DataContainerService dataContainerService;
	
	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DeptDataService deptDataService;
	
	//ok
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "groupCall");
		model.addAttribute("titles", new String[]{"人员名","部门","数据量","完成量","开始日期"});
		model.addAttribute("columns", new String[]{"projectName","department","dataCount","userCount","createDate"});
		
		model.addAttribute("iframecontent","datamanager/groupcall/data-group-call");
		return "iframe";
		
//		return "datamanager/groupcall/data-group-call";
	}
	
	//ok
	@RequestMapping("add")
	public String addTask(HttpServletRequest request, Model model){
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
		
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
		List<DataBatchDepartmentLink> list = deptDataService.getLinks(departments);
		List<NewGroupCallDataLink> addedList = groupCallService.getAddedLinks();
		
		for (NewGroupCallDataLink added : addedList) {
			for (int i = 0; i < list.size(); i++) {
				DataBatchDepartmentLink link = list.get(i);
				if (link.getDataBatchUuid().equals(added.getDataBatch()) && link.getDepartmentUuid().equals(added.getDataDept())) {
					list.remove(i);
					break;
				}
			}
		}
		
		
		model.addAttribute("queues", queues);
		model.addAttribute("accessNumbers", accessNumbers);
		model.addAttribute("datas", list);
		return "datamanager/groupcall/add-task-wizard";
	}
	
	//ok
	//更新数据量的操作在去重操作中，若以后不再需要去重，则更新需要写在下面
	@RequestMapping("wizard")
	@ResponseBody
	public String wizard(HttpServletRequest request, NewGroupCall groupCall, String[] users, String[] datas, Model model){
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
			groupCallService.createDataLinks(groupCall.getGroupcall_id(), datas);
		}
		return jsonObject.toString();
	}
	
	//ok
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, NewGroupCallCondition groupCallCondition,
			Model model) {
		groupCallCondition.setRequest(request);
		PageResult<NewGroupCall> pageResult = groupCallService.queryPage(groupCallCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject ret = GroupCallManager.queryConcurrency();
		System.out.println("================");
		List<NewGroupCall> list = pageResult.getRet();
		for (NewGroupCall i:list) {
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
//			jsonObject.put("dataStat", new JSONObject(groupCallService.getCalledDataStat(i.getGroupcall_id())));
			
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	//从这里请求数据
	//ok
	@RequestMapping("getGroupCallData")
	@ResponseBody
	public String getCallData(HttpServletRequest request, HttpServletResponse response, Model model) {

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject requestJson = HttpRequest.getRequestJson(request);
		List<GroupCallData> list = groupCallService.getData(requestJson.getString("groupcall_id"), requestJson.getInt("qty"));
		for (GroupCallData i:list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data_id", i.getBatchUuid() + "-" + i.getOwnDepartment() + "-" + i.getUid());
			jsonObject.put("phonenumber", i.getPhoneNumber());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("groupcall_id", requestJson.getString("groupcall_id"));
		jsonObject2.put("qty", requestJson.getString("qty"));
		jsonObject2.put("data_list", jsonArray);
		return jsonObject2.toString();
//		return "";
	}
	
	//呼叫接通时调用这个方法
	//ok
	@RequestMapping("returnGroupCall")
	@ResponseBody
	public String returnCallData(HttpServletRequest request, HttpServletResponse response, Model model) {
		JSONObject jsonObject = new JSONObject();
		JSONObject requestJson = HttpRequest.getRequestJson(request);
		if("groupcall_stopped_notify".equals(requestJson.optString("command"))) {
			NewGroupCall g = groupCallService.getGroupCallByCallId((requestJson.optString("groupcall_id", "0")));
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
	
	//ok
	@RequestMapping("changeStat")
	@ResponseBody
	public String changeStat(HttpServletRequest request, String uuid, Boolean stat) {
		NewGroupCall g = groupCallService.getByUuid(UUID.UUIDFromString(uuid));
		JSONObject responseJson;
		if (stat){
			//通知中间件开始呼叫
			responseJson = GroupCallManager.startGroupCall(g.getGroupcall_id());
			if(responseJson.optInt("exit_code",1) == 0)
				groupCallService.changeStat(uuid, "1");
		}
		else {
			//通知中间件停止呼叫
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
	
	//ok
	/**
	 * 关闭群呼任务
	 * @param request
	 * @return
	 */
	@RequestMapping("stopGroupCall")
	@ResponseBody
	public String stopGroupCall(HttpServletRequest request) {
		JSONObject requestJson = HttpRequest.getRequestJson(request);
		NewGroupCall g = groupCallService.getGroupCallByCallId((requestJson.optString("groupcall_id", "0")));
		if (g == null)
			return new JSONObject().put("exit_code", 0).toString();
		groupCallService.stopTask(g);//关闭状态
		return new JSONObject().put("exit_code", 0).toString();
	}
	
	//ok
	@RequestMapping("delete")
	@ResponseBody
	public String deleteUser(UUID uuid, Model model){
		JSONObject jsonObject = new JSONObject();
		NewGroupCall g = groupCallService.getByUuid(uuid);
		JSONObject responseJson = GroupCallManager.deleteGroupCall(g.getGroupcall_id());
		if(responseJson.optInt("exit_code",1) != 0)
			return jsonObject.put("success", false).toString();
		jsonObject.put("success", groupCallService.deleteById(uuid));
		return jsonObject.toString();
	}
	
	//修改数据，已经好了
	@RequestMapping("getData")
	public String getData(HttpServletRequest request, String uuid, Model model) {
//		model.addAttribute("entry", projectService.getByUuid(UUID.UUIDFromString(uuid)));
		NewGroupCall groupCall = groupCallService.getGroupCallByCallId(uuid);
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
		List<DataBatchDepartmentLink> list = deptDataService.getLinks(departments);
		List<NewGroupCallDataLink> addedList = groupCallService.getAddedLinks();
		List<DataBatchDepartmentLink> addedBySelfList = new ArrayList<>();
		
		for (NewGroupCallDataLink added : addedList) {
			for (int i = 0; i < list.size(); i++) {
				DataBatchDepartmentLink link = list.get(i);
				if (link.getDataBatchUuid().equals(added.getDataBatch()) && link.getDepartmentUuid().equals(added.getDataDept())) {
					if(uuid.equals(added.getGroupcallId())) {
						addedBySelfList.add(link);
					}
					list.remove(i);
					break;
				}
			}
		}
		
		model.addAttribute("leftlist", list);
		model.addAttribute("rightlist", addedBySelfList);
		model.addAttribute("groupId", uuid);
		model.addAttribute("dataCount", groupCall.getDataCount());
		return "datamanager/groupcall/group-call-get-data";
	}
	
	//ok,已修改
	@RequestMapping("getfromdata")
	@ResponseBody
	public String updatedata(HttpServletRequest request, String uuid, String[] datas, String[] unselected, Model model){
		JSONObject json = new JSONObject();
		//先remove再add可以保证数量一致，因为remove中只能通过减来更新数据量，而add中则可以遍历出所有要添加的以及他们的数量，且这个顺序可以保证没有create的时候数量也正常
		if (unselected!=null) {
			groupCallService.removeDataLinks(uuid, unselected);
		}
		if (datas!=null) {
			groupCallService.createDataLinks(uuid, datas);
		} 
		return json.put("success", true).toString();
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
		return "datamanager/groupcall/update-group-data";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateUser(HttpServletRequest request,
			HttpServletResponse response, NewGroupCall groupCall, Model model) {
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
	
	
	
	//待修改
	@RequestMapping("reviewModal")
	public String showReviewData(HttpServletRequest request,
			HttpServletResponse response, String uuid,
			Model model) {
		model.addAttribute("groupCallId", uuid);
		NewGroupCall groupCall = groupCallService.getGroupCallByCallId(uuid);
		model.addAttribute("entry", groupCall);
		model.addAttribute("dataCount", groupCallService.getCalledDataCount(uuid));
		return "datamanager/groupcall/call-data-review";
	}
	
	@RequestMapping("statistic")
	@ResponseBody
	public String statistic(HttpServletRequest request,
			HttpServletResponse response, NewGroupCallDataCondition groupCallDataCondition,
			Model model) {
		groupCallDataCondition.setRequest(request);
		PageResult<NewGroupCallDataLink> pageResult = groupCallService.queryStatistic(groupCallDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<NewGroupCallDataLink> list = pageResult.getRet();
		for (NewGroupCallDataLink i:list) {
			JSONObject jsonObject = new JSONObject(i);
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("recall")
	@ResponseBody
	public String recall(HttpServletRequest request,
			HttpServletResponse response, Integer type, String groupCallId,
			Model model) {
		boolean success = groupCallService.recallData(groupCallId, type);
		if (success) {
			return new JSONObject().put("success", true).toString();
		} else {
			return new JSONObject().put("success", false).toString();
		}
	}
	
	
	//待修改
	@RequestMapping("review")
	@ResponseBody
	public String importData(HttpServletRequest request,
			HttpServletResponse response, NewGroupCallDataCondition groupCallDataCondition,
			Model model) {
		groupCallDataCondition.setRequest(request);
		PageResult<NewGroupCallResultLink> pageResult = groupCallService.queryResultPage(groupCallDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<NewGroupCallResultLink> list = pageResult.getRet();
		for (NewGroupCallResultLink i:list) {
			JSONObject jsonObject = new JSONObject(i);
//			jsonObject.put("data_phone", i.getDataPhone());
//			jsonObject.put("container", i.getContainer());
//			jsonObject.put("call_flag", i.getCallResult());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	
	
	
	//后面这些可能用不到了
	
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
//		groupCallService.collection(groupCallId);
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
	
	@RequestMapping("checkDataAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, NewGroupCallDataCondition condition,
			Model model) {
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray(groupCallService.getAllItem(condition));
		jsonObject2.put("items", jsonArray);
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

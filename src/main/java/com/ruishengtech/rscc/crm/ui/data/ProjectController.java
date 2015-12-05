package com.ruishengtech.rscc.crm.ui.data;

import java.util.Date;
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

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.data.condition.ProjectCondition;
import com.ruishengtech.rscc.crm.data.condition.TaskCondition;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("data/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private DataContainerService dataContainerService;
	
	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "project");
		model.addAttribute("items", new String[]{"人员名"});
		model.addAttribute("names", new String[]{"projectName"});
		model.addAttribute("titles", new String[]{"人员名","部门","数据量","完成量","开始日期"});
		model.addAttribute("columns", new String[]{"projectName","department","dataCount","userCount","createDate"});
		
		model.addAttribute("iframecontent","data/data-project");
		return "iframe";
		
//		return "data/data-project";
	}
	
	@RequestMapping("users")
	@ResponseBody
	public String getAllUser(Model model){
		return new JSONArray(userService.getAllUser()).toString();
	}
	

	@RequestMapping("add")
	public String addProject(Model model){
		model.addAttribute("model", "project");
		model.addAttribute("title", "添加项目");
//		model.addAttribute("titles", new String[]{"项目名","项目信息","项目状态","项目描述"});
//		model.addAttribute("names", new String[]{"projectName","projectInfo","projectStat","projectDescribe"});
		model.addAttribute("users", userService.getAllUser());
		model.addAttribute("datas", dataContainerService.getDatas());
		return "data/add-project-wizard";
	}
	
	@RequestMapping("addUsers")
	@ResponseBody
	public String addUsers(HttpServletRequest request, String uid, Model model){
		String[] users = request.getParameterValues("users[]");
		projectService.updateUsers(uid, users);
		return new JSONArray(userService.getAllUser()).toString();
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
	@RequestMapping("wizard")
	@ResponseBody
	public String wizard(HttpServletRequest request, Project project, String[] users, String[] datas, Model model){
		JSONObject jsonObject = new JSONObject();
		project.setCreateDate(new Date());
		project.setProjectStat("0");
		project.setUserCount(users==null?0:users.length);
		jsonObject.put("success", projectService.save(project)).put("uid", project.getUid());
		projectService.updateUsers(project.getUid(), users);
		if(datas!=null) {
			for(String d:datas)
				projectService.importToProject(d, project.getUid());
			projectService.distinct(project.getUid());
		}
		return jsonObject.toString();
	}
	
	@RequestMapping("change")
	public String updateWizard(Model model){
		model.addAttribute("users", userService.getAllUser());
		model.addAttribute("selectedUsers", projectService.getAddedUsers());
//		model.addAttribute("selectedDatas", projectService.getDatas(uuid.toString()));
		return "data/add-user";
	}

//	@RequestMapping("change")
//	public String updateWizard(UUID uuid, Model model){
//		model.addAttribute("projectUuid", uuid.toString());
//		Project p = projectService.getByUuid(uuid);
//		model.addAttribute("entry", p);
//		model.addAttribute("users", userService.getAllUser());
//		model.addAttribute("datas", dataContainerService.getDatas());
//		model.addAttribute("selectedUsers", projectService.getUserTasks(uuid.toString()));
////		model.addAttribute("selectedDatas", projectService.getDatas(uuid.toString()));
//		return "data/update-project-wizard";
//	}
//	
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
	
	//更新数据量的操作在去重操作中，若以后不再需要去重，则更新需要写在下面
//	@RequestMapping("updateWizard")
//	@ResponseBody
//	public String updateWizard(HttpServletRequest request, Project project, String[] users, String[] datas, Model model){
//		JSONObject jsonObject = new JSONObject();
//		project.setUserCount(users==null?0:users.length);
//		jsonObject.put("success", projectService.update(project)).put("uid", project.getUid());
//		projectService.updateUsers(project.getUid(), users);
////		List<String> selectedDatas = projectService.getDatas(project.getUuid().toString());
//		if(datas!=null) {
//			for(String d:datas)
//				projectService.importToProject(d, project.getUid());
//			projectService.distinct(project.getUid());
//		}
//		return jsonObject.toString();
//	}
	
	@RequestMapping("taskAllocate")
//	@ResponseBody
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
//	@ResponseBody
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
	
	@RequestMapping("checkAll")
	@ResponseBody
	public String checkAll(HttpServletRequest request,
			HttpServletResponse response, ProjectCondition projectCondition,
			Model model) {
		JSONObject jsonObject2 = new JSONObject();
		projectCondition.setRequest(request);
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		projectCondition.setIns(departments);
		JSONArray jsonArray = new JSONArray(projectService.getProjects(projectCondition));
//		List<Project> list = projectService.getProjects();
//		for (Project i:list) {
//			jsonArray.put(i.getUid());
//		}
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
	
	@RequestMapping("update")
	@ResponseBody
	public String updateUser(HttpServletRequest request,
			HttpServletResponse response, Project project, Model model) {
		project.setCreateDate(new Date());
		project.setUuid(UUID.UUIDFromString(project.getUid()));
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", projectService.update(project));
		return jsonObject2.toString();
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteUser(UUID uuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", projectService.deleteById(uuid));
		return jsonObject2.toString();
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, ProjectCondition projectCondition,
			Model model) {
		projectCondition.setRequest(request);
//		Set<String> departments = userService.getRoleDataranges(SessionUtil.getCurrentUser(request).getUid(), "datarange");
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		projectCondition.setIns(departments);
		PageResult<Project> pageResult = projectService.queryPage(projectCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<Project> list = pageResult.getRet();
		for (Project i:list) {
			JSONObject jsonObject = new JSONObject(i);
//			User user = userService.getByUuid(i.getUuid());
//			if(!"1".equals(SessionUtil.getCurrentUser(request).getAdminFlag()) && !departments.contains(user.getDepartment())){
//				continue;
//			}
//			jsonObject.put("completeCount", dataTaskService.getCompleteTaskCount(i.getUid()));
//			jsonObject.put("loginName", i.getLoginName());
//			jsonObject.put("password", i.getPassword());
//			jsonObject.put("phone", i.getPhone());
//			jsonObject.put("userDescribe", i.getUserDescribe());
//			jsonObject.put("date", i.getDate());
			jsonObject.put("createDate", DateUtils.getDateString(i.getCreateDate()));
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("getData")
	public String getData(HttpServletRequest request, String uuid, Model model){
		model.addAttribute("entry", projectService.getByUuid(UUID.UUIDFromString(uuid)));
		User user = userService.getByUuid(UUID.UUIDFromString(uuid));
		List<String> list = dataContainerService.getDepartmentDataContainers(user.getDepartment()); 
		model.addAttribute("datas", dataContainerService.getDatas(list));
		return "data/get-from-data";
	}
	
	@RequestMapping("getdata")
	@ResponseBody
	public String getdata(HttpServletRequest request, String uuid, Integer getData, Integer allocateMax, String[] datas, Model model){
		JSONObject json = new JSONObject();
		if(datas == null)
			return json.put("success", true).toString();
		projectService.getData(uuid, getData, allocateMax, datas);
		return json.put("success", true).toString();
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
	
	@RequestMapping("reviewModal")
	public String showReviewData(HttpServletRequest request,
			HttpServletResponse response, String uuid,
			Model model) {
		model.addAttribute("currentUuid", uuid);
		Project entry = projectService.getByUuid(UUID.UUIDFromString(uuid));
		model.addAttribute("entry", entry);
		model.addAttribute("projects", projectService.getProjects());
		return "data/data-review";
	}
	
	@RequestMapping("review")
	@ResponseBody
	public String importData(HttpServletRequest request,
			HttpServletResponse response, TaskCondition taskCondition,
			Model model) {
		taskCondition.setRequest(request);
		PageResult<DataTask> pageResult = dataTaskService.queryPage(taskCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataTask> list = pageResult.getRet();
		for (DataTask i:list) {
			JSONObject jsonObject = new JSONObject(i);
			if (i.getAllocateTime()!=null)
				jsonObject.put("allocateTime", DateUtils.getDateString(i.getAllocateTime()));
//			if (i.getItemOwner() != null)
//				jsonObject.put("itemOwner", projectService.getByUuid(UUID.UUIDFromString(i.getItemOwner())).getProjectName());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
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
			HttpServletResponse response, TaskCondition condition,
			Model model) {

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray(dataTaskService.getTasks(condition));
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
	
}

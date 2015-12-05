package com.ruishengtech.rscc.crm.ui.data;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.springframework.web.multipart.MultipartFile;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.data.condition.AllocateCondition;
import com.ruishengtech.rscc.crm.data.condition.DataContainerCondition;
import com.ruishengtech.rscc.crm.data.condition.ItemCondition;
import com.ruishengtech.rscc.crm.data.condition.ProjectTaskItemsCondition;
import com.ruishengtech.rscc.crm.data.manager.ExcelImporter;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.model.ProjectTaskResultLink;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.data.service.ResultService;
import com.ruishengtech.rscc.crm.data.service.TaskService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
*/
@Controller
@RequestMapping("data/data")
public class DataController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private DataContainerService dataContainerService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ResultService resultService;
	
	@Autowired
	private DataItemService dataItemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BrokerService brokerService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "data");
		model.addAttribute("items", new String[]{"批次名","文件名","数据下限","数据上限","去重状态"});
		model.addAttribute("names", new String[]{"containerName","dataInfo","dataCountMin","dataCountMax","distinctFlag"});
		model.addAttribute("titles", new String[]{"批次名","文件名","数据总量","已分配数量","创建人","导入时间"});
		model.addAttribute("columns", new String[]{"containerName","dataInfo","dataCount","allocateCount","uploadUser","dataCreateTime"});
		model.addAttribute("projects", projectService.getProjects());
		
		model.addAttribute("iframecontent","data/data-data");
		return "iframe";
		
//		return "data/data-data";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteUser(HttpServletRequest request, UUID uuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", dataContainerService.deleteById(uuid,SessionUtil.getCurrentUser(request).getUid()));
		return jsonObject2.toString();
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String saveDataLog(HttpServletRequest request,
			HttpServletResponse response, DataContainer dataContainer, String[] departments, 
			Model model) {
		JSONObject jsonObject = new JSONObject();
		dataContainer.setContainerType("0");
		dataContainer.setUploadUser(SessionUtil.getCurrentUser(request).getUid());
		jsonObject.put("success", dataContainerService.save(dataContainer));
		jsonObject.put("uuid", dataContainer.getUid());
		dataContainerService.bindDepartment(dataContainer.getUid(), departments);
		return jsonObject.toString();
	}
	
	@RequestMapping("add")
	public String addData(HttpServletRequest request,
			HttpServletResponse response, String currentDataTable,
			Model model) {
		model.addAttribute("model", "data");
		List<Datarange> l = null;
		//注意这里的admin判定
		if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
			l = datarangeService.getAllDatarange();
		} else {
//			Set<String> list = userService.getRoleDataranges(SessionUtil.getCurrentUser(request).getUid(), "datarange");
			List<String> list = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
			l = datarangeService.getDataranges(list);
		}
		model.addAttribute("departments", l);
		return "data/data-upload";
	}
	
	@RequestMapping("change")
	public String changeData(HttpServletRequest request,
			HttpServletResponse response, String uuid, 
			Model model) {
		model.addAttribute("title", "批次");
		model.addAttribute("model", "data");
		model.addAttribute("name", "批次名");
		model.addAttribute("entry", dataContainerService.getByUuid(UUID.UUIDFromString(uuid)));
//		List<Datarange> l = null;
//		if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
//			l = datarangeService.getAllDatarange();
//		} else {
//			Set<String> list = userService.getRoleDataranges(SessionUtil.getCurrentUser(request).getUid(), "datarange");
//			l = datarangeService.getDataranges(list);
//		}
		List<String> list = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		List<Datarange> l = datarangeService.getDataranges(list);
		model.addAttribute("departments", l);
		model.addAttribute("hasDepartments", dataContainerService.getDataContainerDepartments(uuid));
		return "data/update-data";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateData(HttpServletRequest request,
			HttpServletResponse response, String uuid, String containerName, String dataInfo, String[] departments, String[] undepartments,
			Model model) {
		dataContainerService.updateName(uuid, containerName, dataInfo, SessionUtil.getCurrentUser(request).getUid());
		if (departments != null)
			dataContainerService.bindDepartment(uuid, departments);
		if (undepartments != null)
			dataContainerService.unbindDepartment(uuid, undepartments);
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("toProject")
	public String addToProject(HttpServletRequest request,
			HttpServletResponse response, String dataUuid, String projectUuid,
			Model model){
		projectService.importToProject(dataUuid, projectUuid);
		projectService.distinct(projectUuid);
		return null;
	}
	
	@RequestMapping("uploader")
	@ResponseBody
    public String importExcel(HttpServletRequest request, MultipartFile multipartFile, String uuid, Model model) {
    	
    	if(multipartFile != null){
			// 上传Excel
    		String filePath;
			try {
				filePath = QueryUtils.uploadFile(multipartFile, ApplicationHelper.getContextPath() + "/upload");
				if(!filePath.endsWith(".xlsx"))
					throw new IOException();
	    		File f = new File(filePath);
	    		DataContainer dataContainer = dataContainerService.getByUuid(UUID.UUIDFromString(uuid));
	    		dataContainer.setDataInfo(multipartFile.getOriginalFilename());
	    		dataContainer.setDataTable(f.getName().split("\\.")[0]);
	    		dataContainer.setFilePath(f.getAbsolutePath());
	    		dataContainer.setDataCount(0);
	    		dataContainer.setAllocateCount(0);
	    		dataContainer.setUploadUser(SessionUtil.getCurrentUser(request).getUid());
	    		dataContainerService.update(dataContainer);
	    		dataContainerService.createLog(dataContainer);
	    		
//	    		dataContainer.setDataCount(dataItemService.importExcelToTable(f, dataContainer.getDataTable()));
//	    		dataContainerService.distinct(dataContainer);
	    		
	    		List<String> departments = dataContainerService.getDataContainerDepartments(uuid);
	    		Collection<String> users = userService.getDatarangesUsers(departments);
	    		Collection<String> relatedUsers = userService.getUsernamesByUuids(users);
	    		relatedUsers.add("admin");
	    		
	    		dataItemService.importExcelToTable(uuid, filePath, SessionUtil.getCurrentUser(request).getLoginName(), dataContainer.getDataTable(), relatedUsers);
	    		model.addAttribute("currentDataTable", dataContainer.getDataTable());
	    		model.addAttribute("projects", projectService.getProjects());
	    		//通知用户上传成功
//	    		brokerService.sendToUser("/user", SessionUtil.getCurrentUser(request).getLoginName(), new JSONObject().put("type", "data-upload").put("count", dataContainer.getDataCount()).toString());
	    		return new JSONObject().put("success", true).put("dataTable", dataContainer.getDataTable()).put("containerName", dataContainer.getContainerName()).toString();
			} catch (Exception e) {
				dataContainerService.deleteByIdOnlyWithTable(UUID.UUIDFromString(uuid));
				return new JSONObject().put("success", false).put("error_message", "上传文件格式错误").toString();
//				e.printStackTrace();
			}
    	}
    	return new JSONObject().put("success", false).toString();
    }
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, DataContainerCondition dataContainerCondition,
			Model model) {
		dataContainerCondition.setRequest(request);
		dataContainerCondition.setContainerType("0");
//		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
		Set<String> datacontainers = dataContainerService.getDepartmentsDataContainers(departments);
		dataContainerCondition.setIns(datacontainers);
		PageResult<DataContainer> pageResult = dataContainerService.queryPage(dataContainerCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataContainer> list = pageResult.getRet();
		for (DataContainer i:list) {
			JSONObject jsonObject = new JSONObject(i);
			if (i.getDataTable() == null) {
				jsonObject.put("progress", "上传中"); 
			} else if (i.getDataCount() > 0){
				jsonObject.put("progress", "导入完成");
			} else {
				jsonObject.put("progress", ExcelImporter.getInstance().getStatus(i.getDataTable()));
			}
			jsonObject.put("distinctFlag", "1".equals(i.getDistinctFlag())?"是":"否");
			jsonObject.put("dataCreateTime", DateUtils.getDateString(i.getDataCreateTime()));
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	//查看数据，可与容器类共用，因为参数只需要一个表名即可
	@RequestMapping("import")
	@ResponseBody
	public String importData(HttpServletRequest request,
			HttpServletResponse response, ItemCondition itemCondition,
			Model model) {
		itemCondition.setRequest(request);
		PageResult<DataItem> pageResult = dataItemService.queryPage(itemCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataItem> list = pageResult.getRet();
		for (DataItem i:list) {
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
	
	@RequestMapping("importModal")
	public String showImportData(HttpServletRequest request,
			HttpServletResponse response, String currentDataTable,
			Model model) {
		DataContainer d = dataContainerService.getDataContainerByName(currentDataTable);
		model.addAttribute("entry", d);
		model.addAttribute("projects", projectService.getProjects());
		return "data/data-select";
	}
	
	@RequestMapping("allocate")
	public String allocateData(HttpServletRequest request,
			HttpServletResponse response, String containerId,
			Model model) {
		DataContainer entry = dataContainerService.getByUuid(UUID.UUIDFromString(containerId));
		model.addAttribute("entry", entry);
		List<String> d1 = dataContainerService.getDataContainerDepartments(containerId);
		List<String> d2 = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
//		if ("0".equals(SessionUtil.getCurrentUser(request).getUid())) {
//			d2 = new HashSet<String>(datarangeService.getAllDatarangeUuid());
//		} else {
//			d2 = userService.getRoleDataranges(SessionUtil.getCurrentUser(request).getUid(), "datarange");
//		}
		
		d2.retainAll(d1);//取交集
		
		model.addAttribute("users", userService.getAllUser(d2));
		model.addAttribute("addedUsers", projectService.getUserTasks());
		return "data/data-allocate";
	}
	
	@RequestMapping("collection")
	public String collectionData(HttpServletRequest request,
			HttpServletResponse response, String containerId,
			Model model) {
		DataContainer entry = dataContainerService.getByUuid(UUID.UUIDFromString(containerId));
		model.addAttribute("entry", entry);
//		model.addAttribute("users", userService.getAllUser());
		Map<String, Project> map = dataContainerService.getUserAllocate(entry.getDataTable());
		Map<String, Project> finalMap = new LinkedHashMap<String, Project>();
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		for (Entry<String, Project> e:map.entrySet()) {	//用迭代器可节省一部分内存，迭代器自带remove方法
			if(departments.contains(e.getValue().getDepartment())) {
				e.getValue().setDepartment(datarangeService.getByUuid(UUID.UUIDFromString(e.getValue().getDepartment())).getDatarangeName());
				finalMap.put(e.getKey(), e.getValue());
			}
		}
		model.addAttribute("allocateUsers", finalMap);
		return "data/data-collection";
	}
	
//	@RequestMapping("allocateData")
//	@ResponseBody
//	public String allocate(HttpServletRequest request, String dataTable, String dataType, String dataMax, String allocate, String allocateMax, String[] users, String containAll, Model model) {
//		projectService.allocate(dataTable, users, Integer.valueOf(dataType), Integer.valueOf(dataMax), Integer.valueOf(allocate), StringUtils.isNotBlank(allocateMax)?Integer.parseInt(allocateMax):0, "on".equals(containAll));
//		return new JSONObject().put("success", true).toString();
//	}
	@RequestMapping("allocateData")
	@ResponseBody
	public String allocate(HttpServletRequest request, AllocateCondition condition, Model model) {
//		projectService.allocate(dataTable, users, Integer.valueOf(dataType), Integer.valueOf(dataMax), Integer.valueOf(allocate), StringUtils.isNotBlank(allocateMax)?Integer.parseInt(allocateMax):0, "on".equals(containAll));
		List<String> list = projectService.getAddedUsers();
		if(condition.getUsers() == null)
			return new JSONObject().put("success", true).toString();
		for(String u:condition.getUsers()){
			if(!list.contains(u)) {
				Project p = new Project();
				User user = userService.getByUuid(UUID.UUIDFromString(u));
				p.setProjectName(user.getLoginName());
				p.setProjectInfo(u);
				p.setProjectStat("1");
				p.setUserCount(0);
				p.setDataCount(0);
				p.setCreateDate(new Date());
				projectService.save(p);
			}
		}
		projectService.allocate(condition);
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("collectionData")
	@ResponseBody
	public String collection(HttpServletRequest request,
			HttpServletResponse response, String dataTable, Integer collection, String[] users, 
			Model model) {
		JSONObject json = new JSONObject();
		if(users == null)
			return json.put("success", true).toString();
		dataItemService.collection(dataTable, collection, users);
		return json.put("success", true).toString();
	}
	
	//获取所有的任务，现在没有用
	@RequestMapping("tasks")
	@ResponseBody
	public String getTasks(HttpServletRequest request,
			HttpServletResponse response, String projectUuid, Model model) {
		List<ProjectTaskResultLink> list = projectService.getAllByUuid(projectUuid);
		JSONArray jsonArray = new JSONArray();
		for(ProjectTaskResultLink p:list)
			jsonArray.put(new JSONObject(p));
		return jsonArray.toString();
	}
	
	//获取所有的条目，现在没用
	@RequestMapping("items")
	@ResponseBody
	public String getItems(HttpServletRequest request,
			HttpServletResponse response, String projectUuid, String taskName, Model model) {
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<String> list = taskService.getAllTask(taskName);
		for (String i:list) {
//			JSONObject jsonObject = new JSONObject(i);
			jsonArray.put(i);
		}

		jsonObject2.put("items", jsonArray);
		return jsonObject2.toString();
	}
	
	//预览里面的全选所有
	@RequestMapping("importAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, ItemCondition condition,
			Model model) {

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray(dataItemService.getItems(condition));

		jsonObject2.put("items", jsonArray);
		return jsonObject2.toString();
	}
	
	//选择一部分导入，现在没用，只能全部导入
	@RequestMapping("importToProject")
	@ResponseBody
	public String importToProject(HttpServletRequest request,
			HttpServletResponse response, ProjectTaskItemsCondition condition,
			Model model) {
		if(StringUtils.isBlank(condition.getTaskName()))
			return new JSONObject().put("success", false).toString();
//		String items[] = request.getParameterValues("items[]");
		JSONObject jsonObject = new JSONObject();
//		boolean success = taskService.importToTask(condition.getDataTable(), condition.getTaskName(), items) & resultService.importToResult(condition.getDataTable(), condition.getTaskName(), items);
		//int importNum = dataItemService.changeImportFlag(condition.getDataTable(), items);
//		jsonObject.put("success", success);
		return jsonObject.toString();
	}
	
	//删除一条数据，可以共用
	@RequestMapping("deleteItem")
	@ResponseBody
	public String deleteItem(UUID uuid, String tableName, Model model){
		JSONObject jsonObject2 = new JSONObject();
		boolean success = dataItemService.deleteById(tableName, uuid);
//		dataContainerService.update(tableName);
		jsonObject2.put("success", success);
		return jsonObject2.toString();
	}
	
	@RequestMapping("checkname/{type}")
	@ResponseBody
	public String checkName(HttpServletRequest request, String uid,String containerName) {
		String container_type = "0";
		if(uid == null && dataContainerService.getDataContainerByContainerName(containerName,container_type)==null){
			return "true";
		}else if(uid != null && containerName.equals(dataContainerService.getByUuid(UUID.UUIDFromString(uid)).getContainerName())){
			return "true";
		}else if(dataContainerService.getDataContainerByContainerName(containerName,container_type)!=null){
			return "false";
		}
		return "true";
	}	
	@RequestMapping("checkAll")
	@ResponseBody
	public String checkAll(HttpServletRequest request,
			HttpServletResponse response, DataContainerCondition condition,
			Model model) {

		JSONObject jsonObject2 = new JSONObject();
		condition.setRequest(request);
		condition.setContainerType("0");
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
		Set<String> datacontainers = dataContainerService.getDepartmentsDataContainers(departments);
		condition.setIns(datacontainers);
		JSONArray jsonArray = new JSONArray(dataContainerService.getDatas(condition));
		jsonObject2.put("datas", jsonArray);
		return jsonObject2.toString();
	}
	
	@RequestMapping("batDelete")
	@ResponseBody
	public String deleteDatas(HttpServletRequest request, Model model){
		JSONObject jsonObject2 = new JSONObject();
		String[] uuids = request.getParameterValues("uuids[]");
		jsonObject2.put("success", dataContainerService.batDelete(uuids, SessionUtil.getCurrentUser(request).getUid()));
		return jsonObject2.toString();
	}
	
	@RequestMapping("batDataDelete")
	@ResponseBody
	public String deleteSelectDatas(HttpServletRequest request, String dataTable, Model model){
		JSONObject jsonObject2 = new JSONObject();
		String[] uuids = request.getParameterValues("uuids[]");
		jsonObject2.put("success", dataItemService.batDelete(dataTable, uuids));
		return jsonObject2.toString();
	}
	
	@RequestMapping("distinct")
	@ResponseBody
	public String distinctDatas(HttpServletRequest request, String uuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		List<String> list = customerService.getAllPhones();
		DataContainer d = dataContainerService.getByUuid(UUID.UUIDFromString(uuid));
		jsonObject2.put("success", true).put("count", dataContainerService.distinct(d) + dataContainerService.distinct(d, list));
//		dataContainerService.distinct(dataContainerService.getByUuid(UUID.UUIDFromString(uuid)), list);
		return jsonObject2.toString();
	}
	
}


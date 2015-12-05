package com.ruishengtech.rscc.crm.ui.data;

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

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.rscc.crm.data.condition.ItemCondition;
import com.ruishengtech.rscc.crm.data.model.ProjectTaskResultLink;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.DataLogService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.data.service.TaskDesignService;
import com.ruishengtech.rscc.crm.data.service.TaskService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("data/task")
public class TaskController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private DataLogService dataLogService;
	
	@Autowired
	private DataItemService dataItemService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskDesignService taskDesignService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "task");
		
		model.addAttribute("columns", taskDesignService.getColumns());
		model.addAttribute("selects", taskDesignService.getSelects());
		model.addAttribute("projects", projectService.getProjects());
		
		model.addAttribute("iframecontent","data/data-task");
		return "iframe";
		
//		return "data/data-task";
	}

	@RequestMapping("add")
	public String addProject(Model model){
		model.addAttribute("model", "task");
		model.addAttribute("title", "添加任务");
		model.addAttribute("projects", projectService.getProjects());
		return "data/add-task-modal";
	}
	
//	@RequestMapping("save")
//	@ResponseBody
//	public String saveProject(HttpServletRequest request,
//			HttpServletResponse response, ProjectTaskResultLink projectTaskResultLink, Model model) {
//		projectTaskResultLink.setCreateTime(new Date());
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", taskService.addTaskAndResult(projectTaskResultLink));
//		return jsonObject.toString();
//	}
	
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
	
	@RequestMapping("change")
	public String changeTask(String tableName, UUID uuid, Model model){
		Map<String, ColumnDesign> map =  taskDesignService.getTableDef("task");
		model.addAttribute("map", map);
		model.addAttribute("model", "task");
		model.addAttribute("table", tableName);
		if(StringUtils.isNotBlank(uuid.getUuid())){
			
			Map<String, Object> task = taskService.getTaskById(tableName, uuid);
			//存放客户信息
			model.addAttribute("entry", task);
		}
		return "data/task-edit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateUser(HttpServletRequest request,
			HttpServletResponse response, String tableName, Model model) {
		Map<String, String[]> map = request.getParameterMap();
		taskService.update(tableName, map);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteTask(HttpServletRequest request,
			HttpServletResponse response, String uuid, String tableName, Model model){
		JSONObject jsonObject2 = new JSONObject();
		taskService.deleteById(tableName, uuid);
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
//	@RequestMapping("deleteTaskResult")
//	@ResponseBody
//	public String deleteTaskResult(HttpServletRequest request,
//			HttpServletResponse response, String projectUuid, String tableName, Model model){
//		JSONObject jsonObject2 = new JSONObject();
//		taskService.deleteTaskAndResult(projectUuid, tableName);
//		jsonObject2.put("success", true);
//		return jsonObject2.toString();
//	}
//	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, String projectUuid, String taskTable, ItemCondition itemCondition,
			Model model) {
		if(StringUtils.isBlank(taskTable))
			return new JSONObject().toString();
		itemCondition.setRequest(request);
		Map<String, ColumnDesign> map  = taskDesignService.getTableDef("task");
		PageResult<Map<String, Object>> pageResult = taskService.queryPage(map, request);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<Map<String, Object>> tasks = pageResult.getRet();
		for (int i = 0; i < tasks.size(); i++) {
			jsonArray.put(taskService.getJsonObject(tasks.get(i)));
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
}

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
import com.ruishengtech.rscc.crm.data.condition.TaskCondition;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.model.ProjectTaskResultLink;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.DataLogService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.data.service.ResultDesignService;
import com.ruishengtech.rscc.crm.data.service.ResultService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("data/result")
public class ResultController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private DataLogService dataLogService;
	
	@Autowired
	private DataItemService dataItemService;
	
	@Autowired
	private ResultService resultService;
	
	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private ResultDesignService resultDesignService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "result");
		
		model.addAttribute("columns", new String[]{"条目名称","条目号码","条目地址","条目其他信息","数据来源","呼叫次数","分配时间"});
		model.addAttribute("selects", new String[]{"itemName","itemPhone","itemAddress","itemJson","dataSource","callTimes","allocateTime"});
		model.addAttribute("projects", projectService.getProjects());
		
		model.addAttribute("iframecontent","data/data-result");
		return "iframe";
		
//		return "data/data-result";
	}
	
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
		Map<String, ColumnDesign> map =  resultDesignService.getTableDef("result");
		model.addAttribute("map", map);
		model.addAttribute("model", "result");
		model.addAttribute("table", tableName);
		if(StringUtils.isNotBlank(uuid.getUuid())){
			
			Map<String, Object> task = resultService.getResultById(tableName, uuid);
			//存放客户信息
			model.addAttribute("entry", task);
		}
		return "data/result-edit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateUser(HttpServletRequest request,
			HttpServletResponse response, String tableName, Model model) {
		Map<String, String[]> map = request.getParameterMap();
		resultService.update(tableName, map);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
	@RequestMapping("addResult")
	@ResponseBody
	public String addResult(HttpServletRequest request,
			HttpServletResponse response, String tableName, Model model) {
		Map<String, String[]> map = request.getParameterMap();
		resultService.save(tableName, map);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteResult(HttpServletRequest request,
			HttpServletResponse response, String uuid, String tableName, Model model){
		JSONObject jsonObject2 = new JSONObject();
		resultService.deleteById(tableName, uuid);
		jsonObject2.put("success", true);
		return jsonObject2.toString();
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, TaskCondition condition,
			Model model) {
		if(StringUtils.isBlank(condition.getTaskTable()))
			return new JSONObject().toString();
		condition.setRequest(request);
		PageResult<DataTask> pageResult = dataTaskService.queryPage(condition);
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataTask> tasks = pageResult.getRet();
		for (DataTask d : tasks) {
			jsonArray.put(new JSONObject(d));
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
//		if(StringUtils.isBlank(taskTable))
//			return new JSONObject().toString();
//		itemCondition.setRequest(request);
//		Map<String, ColumnDesign> map  = resultDesignService.getTableDef("result");
//		PageResult<Map<String, Object>> pageResult = resultService.queryPage(map, request);
//		
//		JSONObject jsonObject2 = new JSONObject();
//		JSONArray jsonArray = new JSONArray();
//		
//		List<Map<String, Object>> tasks = pageResult.getRet();
//		for (int i = 0; i < tasks.size(); i++) {
//			jsonArray.put(resultService.getJsonObject(tasks.get(i)));
//		}
//		
//		jsonObject2.put("data", jsonArray);
//		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
//		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
//		return jsonObject2.toString();
	}
	
}

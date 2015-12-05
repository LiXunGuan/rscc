package com.ruishengtech.rscc.crm.ui.data;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ruishengtech.rscc.crm.data.condition.DataContainerCondition;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;
/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("data/dataPool")
public class DataPoolController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DataContainerService dataContainerService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private DatarangeService datarangeService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("title", "数据池");
		model.addAttribute("model", "dataPool");
		model.addAttribute("items", new String[]{"池名称","池描述","数据下限","数据上限","去重状态"});
		model.addAttribute("names", new String[]{"containerName","dataInfo","dataCountMin","dataCountMax","distinctFlag"});
		model.addAttribute("titles", new String[]{"池名称","池描述","数据总量","已分配","创建人","添加日期"});
		model.addAttribute("columns", new String[]{"containerName","dataInfo","dataCount","allocateCount","uploadUser","dataCreateTime"});
		model.addAttribute("projects", projectService.getProjects());
		
		model.addAttribute("iframecontent","data/pool-data");
		return "iframe";
		
//		return "data/pool-data";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, DataContainerCondition dataContainerCondition,
			Model model) {
		dataContainerCondition.setRequest(request);
		dataContainerCondition.setContainerType("1");
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
		Set<String> datacontainers = dataContainerService.getDepartmentsDataContainers(departments);
		dataContainerCondition.setIns(datacontainers);
		
		PageResult<DataContainer> pageResult = dataContainerService.queryPage(dataContainerCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataContainer> list = pageResult.getRet();
		for (DataContainer i:list) {
//			if(!"1".equals(SessionUtil.getCurrentUser(request).getAdminFlag()) && !userService.hasDatarange(SessionUtil.getCurrentUser(request).getUid(), "datapool", i.getUid()))
//				continue;
			JSONObject jsonObject = new JSONObject(i);
			jsonObject.put("distinctFlag", "1".equals(i.getDistinctFlag())?"是":"否");
			jsonObject.put("dataCreateTime", DateUtils.getDateString(i.getDataCreateTime()));
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteUser(HttpServletRequest request, UUID uuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", dataContainerService.deleteById(uuid, SessionUtil.getCurrentUser(request).getUid()));
		return jsonObject2.toString();
	}
	
	@RequestMapping("add")
	public String addData(HttpServletRequest request,
			HttpServletResponse response, String currentDataTable,
			Model model) {
		List<Datarange> l = null;
		if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
			l = datarangeService.getAllDatarange();
		} else {
//			Set<String> list = userService.getRoleDataranges(SessionUtil.getCurrentUser(request).getUid(), "datarange");
			List<String> list = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
			l = datarangeService.getDataranges(list);
		}
		model.addAttribute("departments", l);
		return "data/add-pool";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String savePool(HttpServletRequest request,
			HttpServletResponse response, String poolName, String poolInfo, String[] departments, 
			Model model) {
		int num = dataContainerService.batSave(poolName, poolInfo, SessionUtil.getCurrentUser(request).getUid(), departments);
		return new JSONObject().put("success", true).put("addNum", num).toString();
	}
	
	@RequestMapping("change")
	public String changeData(HttpServletRequest request,
			HttpServletResponse response, String uuid,
			Model model) {
		model.addAttribute("title", "数据池");
		model.addAttribute("name", "数据池名");
		model.addAttribute("model", "dataPool");
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
	
	@RequestMapping("importModal")
	public String showImportData(HttpServletRequest request,
			HttpServletResponse response, String dataTable,
			Model model) {
		DataContainer d = dataContainerService.getDataContainerByName(dataTable);
		model.addAttribute("entry", d);
		model.addAttribute("projects", projectService.getProjects());
		return "data/data-select";
	}

	@RequestMapping("toProject")
	public String addToProject(HttpServletRequest request,
			HttpServletResponse response, String dataUuid, String projectUuid,
			Model model){
		projectService.importToProject(dataUuid, projectUuid);
		projectService.distinct(projectUuid);
		return null;
	}
	
	@RequestMapping("checkname/{type}")
	@ResponseBody
	public String checkName(HttpServletRequest request, String poolName,String uid,String containerName) {
		if(poolName == null){
			poolName = containerName;
		}
		if(uid == null && dataContainerService.getDataContainerByContainerName(poolName,1+"")==null){
			return "true";
		}else if(uid != null && poolName.equals(dataContainerService.getByUuid(UUID.UUIDFromString(uid)).getContainerName())){
			return "true";
		}else if(dataContainerService.getDataContainerByContainerName(poolName,1+"")!=null){
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
		condition.setContainerType("1");
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
	
	@RequestMapping("move")
	public String moveDatas(HttpServletRequest request, String containerId, Model model){
		model.addAttribute("entry", dataContainerService.getByUuid(UUID.UUIDFromString(containerId)));
		List<DataContainer> list = dataContainerService.getDatas("1");
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		Set<String> datacontainers = dataContainerService.getDepartmentsDataContainers(departments);
		for (int i = 0; i < list.size(); i++) {
			if(!SessionUtil.getCurrentUser(request).getUid().equals(list.get(i).getUploadUser()) && !"1".equals(SessionUtil.getCurrentUser(request).getAdminFlag()) && !datacontainers.contains(list.get(i).getUid()))
				list.remove(i--);
		}
		model.addAttribute("datas", list);
		return "data/move-data";
	}
	
	@RequestMapping("movedata")
	@ResponseBody
	public String moveDatas(HttpServletRequest request, String uuid, Integer moveData, String targetPool, Model model){
		JSONObject jsonObject2 = new JSONObject();
		dataContainerService.move(uuid, moveData, targetPool);
		return jsonObject2.put("success", true).toString();
	}
	
}

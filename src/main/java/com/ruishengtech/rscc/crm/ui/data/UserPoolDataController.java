package com.ruishengtech.rscc.crm.ui.data;

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
import com.ruishengtech.rscc.crm.data.condition.ItemCondition;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("userpooldata")
public class UserPoolDataController {
	
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
		model.addAttribute("pools", dataContainerService.getDatas("1"));
//		model.addAttribute("sources", dataTaskService.getDataSourceName(SessionUtil.getCurrentUser(request).getUid()));
		model.addAttribute("dataNumber", sysConfigService.getSysConfigByKey("sys.data.getDataNumber").getSysVal());

		model.addAttribute("iframecontent","data/user-pool-data");
		return "iframe";
		
//		return "data/user-pool-data";
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
	
	@RequestMapping("getMoreInfo")
	public String getMoreInfo(HttpServletRequest request, String uuid, Model model){
		DataTask entry = dataTaskService.getByUuid(SessionUtil.getCurrentUser(request).getUid(), UUID.UUIDFromString(uuid));
		entry.setDataSource(dataContainerService.getDataContainerByTable(entry.getDataSource()).getContainerName());
		model.addAttribute("entry", entry);
		model.addAttribute("maps", callLogService.getTableDef());
		return "data/get-more-info";
	}
	
	@RequestMapping("pooldata")
	@ResponseBody
	public String importData(HttpServletRequest request,
			HttpServletResponse response, ItemCondition itemCondition,
			Model model) {
		if (StringUtils.isBlank(itemCondition.getItemTable())) {
			return new JSONObject().put("data", new JSONArray()).put("iTotalRecords",0).put("iTotalDisplayRecords", 0).toString();
		}
		itemCondition.setRequest(request);
		itemCondition.setDataFrom(SessionUtil.getCurrentUser(request).getUid());
		PageResult<DataItem> pageResult = dataItemService.queryPage(itemCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataItem> list = pageResult.getRet();
		for (DataItem i:list) {
			JSONObject jsonObject = new JSONObject(i);
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("getback")
	@ResponseBody
	public String getback(HttpServletRequest request,
			HttpServletResponse response, String dataTable, String dataId,
			Model model) {
		User user = SessionUtil.getCurrentUser(request);
		dataContainerService.getBack(dataTable, dataId, user.getUid());
		return new JSONObject().put("success", true).toString();
	}
	
}

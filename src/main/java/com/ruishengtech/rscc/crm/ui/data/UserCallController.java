package com.ruishengtech.rscc.crm.ui.data;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

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
import com.ruishengtech.framework.core.db.condition.Page;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.data.condition.TaskCondition;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.ui.IndexService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallHistoryManager;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.send.CallManager;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("usercall")
public class UserCallController {
	
	@Autowired
	private CallLogService callLogService;
	
	@Autowired
	private DiyTableService diyTableService;
	
	@Autowired
	private DataContainerService dataContainerService;
	
	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private DataItemService dataItemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping()
	public String index(HttpServletRequest request, Model model) {
		List<String> datacontainers = dataContainerService.getDepartmentDataContainers(SessionUtil.getCurrentUser(request).getDepartment());
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		Set<String> datacontainerSet = dataContainerService.getDepartmentsDataContainers(departments);
		datacontainerSet.addAll(datacontainers);
		model.addAttribute("pools", dataContainerService.getDatas("1", datacontainerSet));
		model.addAttribute("maps", callLogService.getTableDef());
		
		model.addAttribute("iframecontent","screenpop/callout");
		return "iframe";
		
//		return "screenpop/callout";
	}
	
	@RequestMapping("call")
	@ResponseBody
	public String callPhone(HttpServletRequest request, String taskUuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		User currentUser = SessionUtil.getCurrentUser(request);
		DataTask data = dataTaskService.getByUuid(currentUser.getUid(), UUID.UUIDFromString(taskUuid));
		JSONObject json = CallManager.makeCall(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), data.getItemPhone(), SessionUtil.getCurrentUser(request), "dataCall-queue");
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
	
	@RequestMapping("callHistory")
	@ResponseBody
	public String callHistoryPhone(HttpServletRequest request, String taskUuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		User currentUser = SessionUtil.getCurrentUser(request);
//		DataTask data = dataTaskService.getByUuid(currentUser.getUid(), UUID.UUIDFromString(taskUuid));
		DataTask data = CallHistoryManager.get(SessionUtil.getCurrentUser(request).getLoginName(), taskUuid);
		JSONObject json = CallManager.makeCall(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), data.getItemPhone(), SessionUtil.getCurrentUser(request), "dataCall-queue");
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
	
	@RequestMapping("dataInfo")
	@ResponseBody
	public String dataInfo(HttpServletRequest request, String taskUuid, Page page, Model model){
		if(StringUtils.isBlank(taskUuid)) {
			JSONObject jsonObject = new JSONObject(); 
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < page.getLength(); i++) {
				jsonArray.put(new JSONObject().put("key", ""));
			}
			jsonObject.put("data", jsonArray);
			jsonObject.put("iTotalRecords", 0);
			jsonObject.put("iTotalDisplayRecords", 0);
			return jsonObject.toString();
		}
		JSONObject jsonObject2 = new JSONObject();
		User currentUser = SessionUtil.getCurrentUser(request);
		DataTask data = dataTaskService.getByUuid(currentUser.getUid(), UUID.UUIDFromString(taskUuid));
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject(data.getItemJson());
		for (Object key:json.keySet()) {
			JSONObject jsonObject = new JSONObject().put("key", key.toString()).put("value", json.optString(key.toString(), ""));
			jsonArray.put(jsonObject);
		}
		//先假设最多有五条其他信息
		for (int i = 0; i < page.getLength() - json.keySet().size(); i++) {
			jsonArray.put(new JSONObject().put("key", ""));
		}
		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", json.keySet().size());
		jsonObject2.put("iTotalDisplayRecords", json.keySet().size());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("getCallList")
	@ResponseBody
	public String getCallList(HttpServletRequest request, TaskCondition taskCondition, Model model){
		taskCondition.setRequest(request);
		taskCondition.setTaskTable(SessionUtil.getCurrentUser(request).getUid());
		taskCondition.setCallTimes("0");
		PageResult<DataTask> pageResult = dataTaskService.queryPage(taskCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		int count = 0;//总条目数
		//加历史
		ConcurrentLinkedQueue<DataTask> historyList = CallHistoryManager.getCallHistoryList(SessionUtil.getCurrentUser(request).getLoginName());
		if (historyList != null) {
			for (DataTask i:historyList) {
				JSONObject jsonObject = new JSONObject(i);
				jsonObject.put("history", "history");
				jsonArray.put(jsonObject);
			}
		}
		//加数据
		List<DataTask> list = pageResult.getRet();
		for (DataTask i:list) {
			JSONObject jsonObject = new JSONObject(i);
			jsonArray.put(jsonObject);
			if(jsonArray.length() == taskCondition.getLength())
				break;
		}
		count = jsonArray.length();
		//加空白
		for (int i = 0; i < taskCondition.getLength() - count; i++) {
			DataTask temp = new DataTask();
			temp.setItemName("");
			jsonArray.put(new JSONObject(temp));
		}
		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("getCallOverList")
	@ResponseBody
	public String getCallOverList(HttpServletRequest request, TaskCondition taskCondition, Model model){
		taskCondition.setRequest(request);
		taskCondition.setTaskTable(SessionUtil.getCurrentUser(request).getUid());
		taskCondition.setCallTimesMin(1);
		PageResult<DataTask> pageResult = dataTaskService.queryPage(taskCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataTask> list = pageResult.getRet();
		for (DataTask i:list) {
			JSONObject jsonObject = new JSONObject(i);
			jsonArray.put(jsonObject);
		}
		for (int i = 0; i < taskCondition.getLength() - list.size(); i++) {
			DataTask temp = new DataTask();
			temp.setItemName("");
			jsonArray.put(temp);
		}
		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("call_queue")
	public String addBlankTab(HttpServletRequest request, String accessNumber, Model model){
		List<String> datacontainers = dataContainerService.getDepartmentDataContainers(SessionUtil.getCurrentUser(request).getDepartment());
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		Set<String> datacontainerSet = dataContainerService.getDepartmentsDataContainers(departments);
		datacontainerSet.addAll(datacontainers);
		model.addAttribute("pools", dataContainerService.getDatas("1", datacontainerSet));
		model.addAttribute("maps", callLogService.getTableDef());
		ConcurrentLinkedQueue<DataTask> list = CallHistoryManager.getCallHistoryList(SessionUtil.getCurrentUser(request).getLoginName());
		int i = list==null?0:list.size();
		model.addAttribute("lastcall", i);
		
		//判断是否显示号码
		indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());
		
		return "screenpop/maincallpop";
	}
	
//	@RequestMapping("toPool")
//	@ResponseBody
//	public String moveTo(HttpServletRequest request, String dataUuid, String poolUuid, Model model){
//		JSONObject jsonObject2 = new JSONObject();
//		if(StringUtils.isBlank(poolUuid))
//			return jsonObject2.put("success", false).toString();
//		String projectUuid = SessionUtil.getCurrentUser(request).getUid();
//		DataTask dataTask = CallHistoryManager.get(SessionUtil.getCurrentUser(request).getLoginName(), dataUuid);
//		dataTask.setMoveTo(poolUuid);
//		//保存为客户
//		if("customer".equals(poolUuid)) {
//			dataTaskService.deleteById(projectUuid, UUID.UUIDFromString(dataUuid));
//		} else {
//			dataTaskService.moveTo(projectUuid, dataUuid, poolUuid);
//		}
//		
//		jsonObject2.put("success", true);
//		return jsonObject2.toString();
//	}
	
	@RequestMapping("isOver")
	@ResponseBody
	public String isOver(HttpServletRequest request, String dataUuid, Model model){
		DataTask dataTask = CallHistoryManager.get(SessionUtil.getCurrentUser(request).getLoginName(), dataUuid);
		if(dataTask == null)
			return "false";
		else {
			return "true";
		}
	}
	
	@RequestMapping("toPool")
	@ResponseBody
	public String moveTo(HttpServletRequest request, String dataUuid, String poolUuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		String projectUuid = SessionUtil.getCurrentUser(request).getUid();
		DataTask dataTask = CallHistoryManager.get(SessionUtil.getCurrentUser(request).getLoginName(), dataUuid);
		if(dataTask == null)
			return new JSONObject().put("success", false).put("err_code", "1").put("message", "请结束呼叫后再点击呼叫下一条").toString();
		 //已经移动到客户，不允许再做操作
		if("customer".equals(dataTask.getMoveTo()))
			return jsonObject2.put("success", false).put("message", "已保存为客户，请在客户管理里操作。").toString();
		//未对数据做操作时，执行以下操作
		if(StringUtils.isBlank(dataTask.getMoveTo())){ 
			//数据池为空，不做任何操作
			if(StringUtils.isBlank(poolUuid)){
				return jsonObject2.toString();
			}
			if("customer".equals(poolUuid)) {
//				dataTaskService.deleteById(projectUuid, UUID.UUIDFromString(dataUuid));
				dataItemService.deleteById(dataTask.getDataSource(), UUID.UUIDFromString(dataUuid));
			} else {
				dataTaskService.moveTo(projectUuid, dataUuid, poolUuid);
			}
			dataTask.setMoveTo(poolUuid);
			return jsonObject2.put("success", true).toString();
		}
		//剩下的就是已经移动到池的内容，先取回数据，用于后面操作
		dataContainerService.getBack(dataTask.getMoveTo(), dataTask.getUid(), projectUuid);
		//数据池为空，把呼叫数+1
		if(StringUtils.isBlank(poolUuid)){
			dataTaskService.addCallTimes(projectUuid, dataTask.getUid());
		}
		else if("customer".equals(poolUuid)) { //移动到客户
//			dataTaskService.deleteById(projectUuid, UUID.UUIDFromString(dataUuid));
			dataItemService.deleteById(dataTask.getDataSource(), UUID.UUIDFromString(dataUuid));
		} else { //移动到其他池
			dataTaskService.moveTo(projectUuid, dataUuid, poolUuid);
		}
		dataTask.setMoveTo(poolUuid);
		return jsonObject2.put("success", true).toString();
	}
	
}

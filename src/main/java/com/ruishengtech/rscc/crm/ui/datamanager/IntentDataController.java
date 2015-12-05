package com.ruishengtech.rscc.crm.ui.datamanager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.ruishengtech.rscc.crm.datamanager.condition.DepartmentDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.IntentDataService;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DepartmentTableServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Controller
@RequestMapping("indent/data")
public class IntentDataController {
	
	@Autowired
	private IntentDataService intentDataService;
	
	
	@Autowired
	private NewUserDataService newUserDataService;
	
	@Autowired
	private DataBatchService dataBatchService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DataIntentServiceImp dataIntentServiceImp;
	
	@Autowired
	private DepartmentTableServiceImp departmentTableServiceImp;
	
	
	@RequestMapping
	public String index(HttpServletRequest request,Model model){
		//获取当前登录用户的管理部门
		List<String> depts = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		if(depts.size()!=0){
			model.addAttribute("depts", depts);
		}
		List<DepartmentTable> dts = departmentTableServiceImp.getAll();
		model.addAttribute("alldept", dts);
		
		model.addAttribute("iframecontent","datamanager/intentData");
		return "iframe";
		
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,HttpServletResponse response,Model model,DepartmentDataCondition departmentDataCondition){
		departmentDataCondition.setRequest(request);
		PageResult<UserData> pageResult = intentDataService.queryPage(departmentDataCondition);
			
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		List<UserData> list = pageResult.getRet();
		for (UserData userData : list) {
			JSONObject json = new JSONObject(userData);
			json.put("batchName", userData.getBatchname());
			json.put("ownDept", departmentTableServiceImp.getByUuid(UUID.UUIDFromString(userData.getOwnDepartment())).getDepartmentName());
			json.put("ownUseruuid", userData.getOwnUser());
			if(StringUtils.isNotBlank(userData.getOwnUser())){
				json.put("ownUser", userData.getUsername());
			}
			if(userData.getLastCallTime() != null){
				json.put("lastCallTime", sdf.format(userData.getLastCallTime()));
			}
			if(StringUtils.isNotBlank(userData.getIntentType())){
				json.put("intentTypeName",userData.getIntentTypeName());
			}
			if(userData.getOwnUserTimestamp() != null){
				json.put("ownUserTimestamp", sdf.format(userData.getOwnUserTimestamp()));
			}
			if(userData.getIntentTimestamp() != null){
				json.put("intentTimestamp", sdf.format(userData.getIntentTimestamp()));
			}
			jsonArray.put(json);
		}
		intentDataService.getAllBatchAndUser(list, departmentDataCondition, jsonObject2);
		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}

	/**
	 * 提交审批
	 * @param response
	 * @param userUuid
	 * @param deptUuid
	 * @param phoneNumber
	 * @param stat
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("commitAudit")
	@ResponseBody
	public String commitAudit(HttpServletRequest request, HttpServletResponse response, String userUuid, String deptUuid, String phoneNumber, Integer stat) throws IOException {
		newUserDataService.commitAudit(deptUuid, userUuid, phoneNumber, stat);
		return new JSONObject().put("success", true).toString();
	}
	
}

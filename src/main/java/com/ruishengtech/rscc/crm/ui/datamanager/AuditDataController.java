package com.ruishengtech.rscc.crm.ui.datamanager;

import java.text.SimpleDateFormat;
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
import com.ruishengtech.rscc.crm.datamanager.service.AuditDataService;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DepartmentTableServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.linkservice.DataCustomerLinkService;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Controller
@RequestMapping("audit")
public class AuditDataController {
	
	@Autowired
	private AuditDataService auditDataService;
	
	@Autowired
	private DataBatchService dataBatchService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DataIntentServiceImp dataIntentServiceImp;
	
	@Autowired
	private DepartmentTableServiceImp departmentTableServiceImp;
	
	@Autowired
	private DataCustomerLinkService dataCustomerLinkService;
	
	@RequestMapping
	public String index(HttpServletRequest request,Model model){
		//获取当前登录用户的管理部门
		List<String> depts = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		if(depts.size()!=0){
			model.addAttribute("depts", depts);
		}
		List<DepartmentTable> dts = departmentTableServiceImp.getAll();
		model.addAttribute("alldept", dts);
		
		model.addAttribute("iframecontent","datamanager/auditData");
		return "iframe";
		
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,HttpServletResponse response,Model model,DepartmentDataCondition departmentDataCondition){
		departmentDataCondition.setRequest(request);
		PageResult<UserData> pageResult = auditDataService.queryPage(departmentDataCondition);
				
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		List<UserData> list = pageResult.getRet();
		for (UserData userData : list) {
			JSONObject json = new JSONObject(userData);
			json.put("batchName", dataBatchService.getByUuid(UUID.UUIDFromString(userData.getBatchUuid())).getBatchName());
			if(StringUtils.isNotBlank(userData.getOwnUser())){
				json.put("NownUser", userService.getByUuid(UUID.UUIDFromString(userData.getOwnUser())).getUserDescribe());
			}
			if(userData.getLastCallTime() != null){
				json.put("lastCallTime", sdf.format(userData.getLastCallTime()));
			}
			if(StringUtils.isNotBlank(userData.getIntentType())){
				json.put("intentTypeName",dataIntentServiceImp.getByUuid(userData.getIntentType()).getIntentName());
			}
			if(userData.getOwnUserTimestamp() != null){
				json.put("ownUserTimestamp", sdf.format(userData.getOwnUserTimestamp()));
			}
			if(userData.getIntentTimestamp() != null){
				json.put("intentTimestamp", sdf.format(userData.getIntentTimestamp()));
			}
			jsonArray.put(json);
		}
		
		jsonObject2.put("data", jsonArray);
		jsonObject2.put("batch", departmentTableServiceImp.getBatchByDept(departmentDataCondition.getDeptUuid()));
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	/**
	 * 驳回审批
	 * @param batchUuid
	 * @param department
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping("reject")
	@ResponseBody
	public String reject(String batchUuid, String department, String phoneNumber){
		auditDataService.reject(batchUuid, department, phoneNumber);
		return new JSONObject().put("success", true).toString();
	}
	/**
	 * 通过审批
	 * @param request
	 * @param batchUuid
	 * @param department
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping("accept")
	@ResponseBody
	public String accept(HttpServletRequest request, String batchUuid, String department, String phoneNumber){
		dataCustomerLinkService.acceptAudit(request, batchUuid, department, phoneNumber);
		return new JSONObject().put("success", true).toString();
	}
}

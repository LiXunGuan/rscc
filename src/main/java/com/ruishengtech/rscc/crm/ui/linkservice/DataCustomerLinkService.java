package com.ruishengtech.rscc.crm.ui.linkservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.datamanager.condition.DeptAllotDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.DeptDataService;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataBatchDepartmentLinkServiceImp;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.linkservice.model.SaveBean;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;

/**
 * @author Wangyao
 *
 */
@Service
@Transactional
public class DataCustomerLinkService extends BaseService {
	
	@Autowired
	private NewUserDataService newUserDataService;
	
	@Autowired
	private CallLogService callLogService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DataBatchService dataBatchService;
	
	@Autowired
	private ScheduleReminderService scheduleReminderService;
	
	@Autowired
	private DataBatchDepartmentLinkServiceImp dataBatchDepartmentLinkService;
	
	@Autowired
	private DeptDataService deptDataService;
	
	//弹屏页面 点击保存并关闭按钮
	public String saveResult(HttpServletRequest request, SaveBean values) {
		JSONObject json;
		switch (values.getPhoneStat()) {
		case SaveBean.EntryNotIntent: 
		case SaveBean.EntryIntent: {
			//用32位判断是uuid
			if ("customer".equals(values.getResult()) || values.getResult().length() == 32) {
				//第一步保存详细信息，并把详细信息的uuid传过去
				json = saveCustomer(request, values);
				values.setUid(json.getString("uuid"));
			}
			//保存呼叫记录
			saveCallLog(request, values);
			//添加预约
			addSchedule(request, values);
			//保存呼叫结果
			json = saveDataResult(request, values);
			break;
		}
		case SaveBean.NotEntryNotAcstm: 
		case SaveBean.NotEntryAcstmNotMe: {
			json = saveCallLog(request, values);
			break;
		}
		case SaveBean.NotEntryAcstmMe: {
			json = saveCustomer(request, values);
			saveCallLog(request, values);
			addSchedule(request, values);
			break;
		}
		default: {
			json = new JSONObject();
			break;
		}
		}
		return json.toString();
	}
	
	//保存详细信息
	private JSONObject saveCustomer(HttpServletRequest request, SaveBean values) {
		return customerService.saveCustomerInfo(values.getCstm_name(), values.getOwn_id(), values.getPhone_number(), 
				values.getPhone_number_a(), "customer".equals(values.getResult())?"1":"0", values.getResult());
	}
	
	//保存数据结果
	private JSONObject saveDataResult(HttpServletRequest request, SaveBean values) {
		if(StringUtils.isNoneBlank(values.getPhone_number(), values.getResult())){
			UserData userData = newUserDataService.getUserDataByPhone(SessionUtil.getCurrentUser(request).getDepartment(), values.getPhone_number());
			if(StringUtils.isNotBlank(values.getCall_time())) {
				userData.setLastCallResult(values.getCallStat());
				userData.setLastCallTime(DateUtils.stringToDate(values.getCall_time()));
	//				userData.setCallCount(userData.getCallCount() + 1);
			} else {
				
			}
			newUserDataService.update(userData);
			int result = newUserDataService.updateData(userData, values.getResult(), values.getCall_session_uuid(), values.getUid());
			if (result == -1) {
				return new JSONObject().put("success", false).put("message", "超过意向客户上限");
			}
			return new JSONObject().put("success", true);
		}
		return new JSONObject().put("success", false);
	}
	
	//保存呼叫记录
	private JSONObject saveCallLog(HttpServletRequest request, SaveBean values) {
		
		JSONObject jsonObject = new JSONObject();
		String callSessionUuid = values.getCall_session_uuid();
		if (StringUtils.isNotBlank(callSessionUuid)) {
			callLogService.updateByCallSessionUuid(callSessionUuid, values.getText_log());
			return jsonObject.put("success", true);
		}else{
			
			CallLog callLog = new CallLog();
			callLog.setAgent_id(SessionUtil.getCurrentUser(request).getUid());
			callLog.setAgent_name(SessionUtil.getCurrentUser(request).getLoginName());
			callLog.setCall_phone(values.getPhone_number());
			callLog.setText_log(values.getText_log());
			callLog.setCall_time(new Date());
			callLog.setRecord_path("");
			callLog.setIn_out_flag("");
			callLogService.save(callLog);
			return jsonObject.put("success", true);
		}
	}
	
	//添加预约
	private JSONObject addSchedule(HttpServletRequest request, SaveBean values) {
		try {
			if (StringUtils.isNotBlank(values.getReminderTime())) {
				scheduleReminderService.addDefaultReminder(request, values.getPhone_number(), values.getOldstime(),values.getReminderTime(), values.getReminderTimeContent());
			}
			return new JSONObject().put("success", true);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	//通过成交审批
	public JSONObject acceptAudit(HttpServletRequest request, String batchUuid, String deptUuid, String phoneNumber) {
		if(StringUtils.isNoneBlank(phoneNumber)){
			UserData userData = newUserDataService.getUserDataByPhone(deptUuid, phoneNumber);
			//转意向为成交客户
			JSONObject json = customerService.updateIntendToCustomer(phoneNumber, userData.getOwnUser());
			newUserDataService.updateData(userData, "customer", null, json.getString("uuid"));
			jdbcTemplate.update(" DELETE FROM `new_data_batch_` WHERE phone_number = ? AND own_department = ? ",phoneNumber,deptUuid);
			return new JSONObject().put("success", true);
		}
		return new JSONObject().put("success", false);
	}
	
	public int collectDeptDataSave(String batchUuid, String deptUuid, String depts) {
		String[] ds = depts.split(",");
		List<String> list = new ArrayList<String>();
		for (String department : ds) {
			if(!department.equals(deptUuid)){  
				list.add(department);
		    }  
		}
		int num = 0;
		if(list.size() > 0 ){
			String[] dts = list.toArray(new String[list.size()]);
			num = deptDataService.collectDeptData(batchUuid, deptUuid, dts);
		}
		return num;
	}
	
	//从部门分配到子部门，暂时写这里
	public int allotDeptDataSave(DeptAllotDataCondition cond, String depts) {
		String[] ds = depts.split(",");
		List<String> list = new ArrayList<String>();
		for (String department : ds) {
			DataBatchDepartmentLink dbd = dataBatchDepartmentLinkService.getByLink(cond.getDataBatchUuid(), department);
			if(!department.equals(cond.getDeptUuid())){  
				if(dbd == null){
					dataBatchService.autoBindDepartment(cond.getDataBatchUuid(), department);
				}
				list.add(department);
		    }  
		}
		int num = 0;
		if(list.size() > 0 ){
			String[] dts = list.toArray(new String[list.size()]);
			cond.setDepts(dts);
			num = deptDataService.allotDeptData(cond);
		}
		return num;
	}
	
	//详情时的保存，这里只可能有三种操作：1、修改客户分类，2、修改拥有人，3、移动至共享池。只能根据选择来判断操作即可，不用再判断号码状态。保存详细信息是肯定有的
	public String detailSaveResult(HttpServletRequest request, SaveBean values) {
		JSONObject json;
		//第一步保存详细信息，并把详细信息的uuid传过去
		json = saveCustomer(request, values);
		values.setUid(json.getString("uuid"));
		//第二步，保存结果，不管是共享池还是修改意向类型
		json = detailSaveDataResult(request, values);
		//第三部，判断是否修改了拥有者，修改了则修改用户
		if (!values.getOwnerUuid().equals(values.getNewOwnUser())) {
			newUserDataService.changeUser(values.getBatchUuid(), values.getDeptUuid(), values.getOwnerUuid(), values.getDataUuid(), values.getNewOwnUser());
			//转移原因
			String changeReason = values.getChangeReason();
		}
		return json.toString();
	}
	
	private JSONObject detailSaveDataResult(HttpServletRequest request, SaveBean values) {
		if(!StringUtils.isNoneBlank(values.getPhone_number(), values.getResult())){
			return new JSONObject().put("success", false);
		}
		UserData userData = newUserDataService.getUserDataByPhone(values.getDeptUuid(), values.getPhone_number());
		int result = newUserDataService.updateData(userData, values.getResult(), values.getCall_session_uuid(), values.getUid());
		if (result == -1) {
			return new JSONObject().put("success", false).put("message", "超过意向客户上限");
		}
		return new JSONObject().put("success", true);
	}
}

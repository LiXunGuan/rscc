package com.ruishengtech.rscc.crm.ui.datamanager;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.service.CstmPhoneService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerDesignService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.datamanager.condition.NewUserDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminder;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.cstm.controller.CustomerController;
import com.ruishengtech.rscc.crm.ui.linkservice.DataCustomerLinkService;
import com.ruishengtech.rscc.crm.ui.linkservice.model.SaveBean;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.PopEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.send.CallManager;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.UserConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.UserConfigService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * 人员数据管理
 * @author Frank
 */
/**
 * 
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("newuserdata")
public class NewUserDataController {
	
	@Autowired
	private NewUserDataService newUserDataService;

	@Autowired
	private CallLogService callLogService;
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CstmPhoneService cstmPhoneService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserConfigService userConfigService;
	
	@Autowired
	private DataBatchService dataBatchService;
	
	@Autowired
	@Qualifier(value="diyTableService")
	private DiyTableService divTableService;
	
	@Autowired
	private CustomerDesignService customerDesignService;
	
	@Autowired
	private BrokerService brokerService;
	
	@Autowired
	private DataCustomerLinkService dataCustomerLinkService;
	
	@Autowired
	private SysConfigService configService;
	
	@Autowired
	private ScheduleReminderService scheduleReminderService;
	
//	public static Map<String, JSONObject> linkMap = new HashMap<String, JSONObject>();
//	private Thread thread ;
	
	
	
	/**
	 * @return
	 */
	@RequestMapping()
	public String userDataIndex(HttpServletRequest request, Model model){
		User user = SessionUtil.getCurrentUser(request);
		model.addAttribute("user", user);
		
		UserConfig userConfig = userConfigService.getByUuid(user.getUid());
		model.addAttribute("config", userConfig == null?null:userConfig.getConfig());
		
		String singleLimit = configService.getSysConfigByKey("sys.data.getDataSingleLimit").getSysVal();
		model.addAttribute("singleLimit", singleLimit);
		
		//取到个人的还可以获取的条数
		model.addAttribute("limitNumber", newUserDataService.getLimitByUser(user.getUid()));
		
		
		model.addAttribute("iframecontent","datamanager/new_user_data");
		return "iframe";
		
	}
	
	/**
	 * 获取数据
	 * @param httpServletRequest
	 * @param response
	 * @param condition
	 * @return
	 * @throws ParseException 
	 * @throws JSONException 
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request , HttpServletResponse response,NewUserDataCondition condition) throws JSONException, ParseException{
		
		condition.setRequest(request);
		//是否是查看详细信息 如果为1则为查看详细信息
		if("1".equals(condition.getDetail())){
			condition.setDetail("1");
		}
		if(condition.getOwnUser()==null){
			condition.setOwnUser(SessionUtil.getCurrentUser(request).getUid());
		}
		condition.setDeptName(userService.getByUuid(UUID.UUIDFromString(condition.getOwnUser())).getDepartment());
		
		PageResult<UserData> pageResult = newUserDataService.queryPage(condition);
		List<UserData> cstmservices = pageResult.getRet();
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < cstmservices.size(); i++) {
			JSONObject jsonObject2 = new JSONObject(cstmservices.get(i));
			jsonObject2.put("ownUserTimestamp", DateUtils.getDateString(cstmservices.get(i).getOwnUserTimestamp()));
			jsonObject2.put("last_call_time", cstmservices.get(i).getLastCallTime()==null?null:DateUtils.getDateString(cstmservices.get(i).getLastCallTime()));
			jsonObject2.put("last_call_result", StringUtils.trimToEmpty(cstmservices.get(i).getLastCallResult()));
			jsonObject2.put("intent_type", StringUtils.trimToEmpty(cstmservices.get(i).getIntentType()));
			array.put(jsonObject2);
		}
		
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();		
	}
	
	
	@RequestMapping("showIntendDetails")
	@ResponseBody
	public String showDetails(HttpServletRequest request){
		
		//获取个人还能存放的意向客户
		JSONObject jsob = newUserDataService.getIntendNumber(SessionUtil.getCurrentUser(request));
		JSONObject jsonObject2 = new JSONObject();

		if(-1 == jsob.optInt("intendCount")){
			
			jsonObject2.put("msg", jsob.optString("msg"));
		}else{
			
			jsonObject2.put("msg", "还能添加 "+jsob.optString("intendCount")+" 个意向客户").put("intentNumber", jsob.optString("intentNumber"));
		}
		
		return jsonObject2.toString();
	}
	
	@RequestMapping("showDataDetails")
	@ResponseBody
	public String showDataDetails(HttpServletRequest request){
		
		//获取个人还能获取的数据
		JSONObject jsonObject2 = new JSONObject();
		JSONObject jsob = newUserDataService.getDataNumber(SessionUtil.getCurrentUser(request));
		
		if(-1 == jsob.optInt("dataCount")){
			
			jsonObject2.put("msg", jsob.optString("msg"));
		}else{
			
			jsonObject2.put("msg", "还能获取 "+jsob.optString("intendCount")+" 条数据").put("DataNumber", jsob.optString("DataNumber"));
		}
		
		return jsonObject2.toString();
	}
	
	
	/**
	 * 获取数据界面
	 * @return
	 */
	@RequestMapping("toDetailsPage")
	@ResponseBody
	public String toDetailsPage(HttpServletRequest request,Model model,String batchId,String target){
		
//		if(StringUtils.isNotBlank(batchId)){
//			model.addAttribute("batchId", batchId);
//		}
//		List<DataBatchDepartmentLink> datas = newUserDataService.getBatchInfo(SessionUtil.getCurrentUser(request));
//		model.addAttribute("datas", datas);
//		model.addAttribute("target", target);
//		model.addAttribute("cUser", SessionUtil.getCurrentUser(request));
		
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 获取数据界面
	 * @return
	 */
	@RequestMapping("toDataPage")
	public String toDataPage(HttpServletRequest request,Model model,String batchId,String target){
		
		if(StringUtils.isNotBlank(batchId)){
			
			model.addAttribute("batchId", batchId);
		}
		
		List<DataBatchDepartmentLink> datas = newUserDataService.getBatchInfo(SessionUtil.getCurrentUser(request));
		
		model.addAttribute("datas", datas);
		model.addAttribute("target", target);
		model.addAttribute("cUser", SessionUtil.getCurrentUser(request));
		
		return "datamanager/getDataInfo";
	}

	
	/**
	 * 校验
	 * @param batchuuid
	 * @throws IOException 
	 */
	@RequestMapping("getValidate")
	@ResponseBody
	public void getValidata(HttpServletRequest request , HttpServletResponse response , String batchuuid , String countNumber) throws IOException {
		
		if(StringUtils.isNoneBlank(batchuuid,countNumber)){
			
			if(Integer.valueOf(countNumber) > newUserDataService.getPersionData(SessionUtil.getCurrentUser(request), batchuuid)){
				
				response.getWriter().print(false);			
			}else{
				
				response.getWriter().print(true);			
			}
		}
	}
	
	/**
	 * 提交审批
	 * @param phoneNumber	要提交审批的号码
	 * @throws IOException 
	 */
	@RequestMapping("commitAudit")
	@ResponseBody
	public String commitAudit(HttpServletRequest request, HttpServletResponse response,String department,String userid, String phoneNumber, Integer stat) throws IOException {
		if(StringUtils.isBlank(department) && StringUtils.isBlank(userid)){
			newUserDataService.commitAudit(SessionUtil.getCurrentUser(request).getDepartment(), SessionUtil.getCurrentUser(request).getUid(), phoneNumber, stat);
		}else{
			newUserDataService.commitAudit(department, userid, phoneNumber, stat);
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("updateCount")
	@ResponseBody
	public String updateCount(HttpServletRequest request , String batchUUid){
		
		if(StringUtils.isNotBlank(batchUUid)){
			
			Integer integer = newUserDataService.getPersionData(SessionUtil.getCurrentUser(request), batchUUid);
			
			return new JSONObject().put("success", true).put("data", integer).toString();
		}
		return new JSONObject().put("success", false).put("data", 0).toString();
	}
	
	
	
	
	/**
	 * 保存获取 归还数据
	 * @param ownId
	 * @param batchId
	 * @param deptId
	 * @param countNumber
	 * @param target
	 * @return
	 */
	@RequestMapping("getDataByBatchId")
	@ResponseBody
	public String getDataByBatchId(String ownId,String batchId,String deptId,Integer countNumber,String target){
		
		if("get".equals(target)){ //获取数据
			if(null == countNumber){
				
				return new JSONObject().put("success", false).put("msg", "没有获取到任何数据!").toString();
			}
			Integer integer = newUserDataService.getData(ownId, batchId, deptId, countNumber);
			
		}else{ //归还数据
			
		}
		
		return new JSONObject().put("success", true).toString();
	}
	/**
	 * 获取数据
	 * @param request
	 * @param target
	 * @return
	 */
	@RequestMapping("getDataAuto")
	@ResponseBody
	public String getDataAuto(HttpServletRequest request, String target){
		
		int getCount = 0;
		if("get".equals(target)){ //获取数据
			
			User user = userService.getByUuid(SessionUtil.getCurrentUser(request).getUuid());
			
			int maxCount = newUserDataService.getLimitByUser(user.getUid());
			if (maxCount == 0) {
				return new JSONObject().put("success", false).put("getCount", getCount).put("message", "获取已达上限").toString();
			}
			List<DataBatchDepartmentLink> datas = newUserDataService.getBatchInfo(user);
			if (datas == null) {
				return new JSONObject().put("success", false).put("getCount", getCount).put("message", "没有关联数据").toString();
			}
			for (DataBatchDepartmentLink dataBatchDepartmentLink : datas) {
				
				int count = newUserDataService.getData(user.getUid(), dataBatchDepartmentLink.getDataBatchUuid(), 
						dataBatchDepartmentLink.getDepartmentUuid(), maxCount);
				
				getCount += count;
				
				maxCount -= count;
			}
			
		}else { //归还数据
			
		}
		if (getCount == 0) {
			return new JSONObject().put("success", false).put("getCount", getCount).put("message", "部门下无数据").toString();
		}
		return new JSONObject().put("success", true).put("getCount", getCount).toString();
	}
	
	/**
	 * 意向客户界面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("data_intend_index")
	public String ToIntendIndex(HttpServletRequest request ,HttpServletResponse response,Model model){
		User user = SessionUtil.getCurrentUser(request);
		model.addAttribute("user", user);
		
		UserConfig userConfig = userConfigService.getByUuid(user.getUid());
		model.addAttribute("config", userConfig == null?null:userConfig.getConfig());
		
		//获取所有意向类型
		List<DataIntent> dataintent = dataIntentService.getAll();
		model.addAttribute("intents", dataintent);
		
		model.addAttribute("iframecontent","datamanager/data_intend_index");
		return "iframe";
		
//		return "datamanager/data_intend_index";
	}
	
	@RequestMapping("userconfig")
	public String userConfig(HttpServletRequest request ,HttpServletResponse response,Model model){
		
		User user = SessionUtil.getCurrentUser(request);
		model.addAttribute("user", user);
		UserConfig userConfig = userConfigService.getByUuid(user.getUid());
		String  defUserCfg = "{\"time\":\"5\",\"timing\":\"1\",\"nocall\":\"noanswer\",\"intent\":\"global_share\"}";
		model.addAttribute("config", userConfig == null?defUserCfg:userConfig.getConfig());
		//获取所有意向类型
		model.addAttribute("intents", dataIntentService.getAll());
		if(userConfig != null){
			//默认显示的值
			JSONObject uc = new JSONObject(userConfig.getConfig());
			if(StringUtils.isNotBlank(uc.optString("intent"))){
				model.addAttribute("defaultUserCfg", uc.get("intent").toString());
			}
			if(StringUtils.isNotBlank(uc.optString("nocall"))){
				model.addAttribute("defaultUserNocall", uc.get("nocall").toString());
			}
		}else{
			JSONObject uc = new JSONObject(defUserCfg);
			if(StringUtils.isNotBlank(uc.optString("intent"))){
				model.addAttribute("defaultUserCfg", uc.get("intent").toString());
			}
			if(StringUtils.isNotBlank(uc.optString("nocall"))){
				model.addAttribute("defaultUserNocall", uc.get("nocall").toString());
			}
		}
		
		
		return "datamanager/update_own_config";
	}
	
	@RequestMapping("userconfigSave")
	@ResponseBody
	public String userConfigSave(HttpServletRequest request, HttpServletResponse response, UserConfig userConfig, Model model){
		userConfigService.save(userConfig);
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 意向客户数据
	 * @param request
	 * @param response
	 * @param condition
	 * @return
	 */
	@RequestMapping("intenddata")
	@ResponseBody
	public String getIntendData(HttpServletRequest request , HttpServletResponse response,NewUserDataCondition condition){
		
		
		
		return null;
	}
	
	@RequestMapping("makecall")
	@ResponseBody
	public String makeCall(HttpServletRequest request, String phone,Model model) {
		if(null == SessionUtil.getSession(request, SessionUtil.LOGINEXTEN)){
			return new JSONObject().put("success", false).put("message", "请先绑定分机！").toString();
		}
		
		User usr = SessionUtil.getCurrentUser(request);
		JSONObject jsonObject = CallManager.makeCall(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), phone, usr, null);
		
//		linkMap.put(jsonObject.getString("call_session_uuid"),jsonObject);

		if("0".equals(jsonObject.optString("exit_code","19999"))){
			
			User user = SessionUtil.getCurrentUser(request);
			UserData userData = newUserDataService.getUserDataByPhone(user.getDepartment(), phone);
			
			Date date = new Date();
			
			CallLog callLog = new CallLog();
			callLog.setAgent_id(user.getUid());
			callLog.setAgent_name(user.getLoginName());
			
			//更新下时间，不知道还有没有更好的方案
			if(userData != null){
				userData.setLastCallTime(date);
				userData.setCallCount(userData.getCallCount() + 1);
				newUserDataService.update(userData);
				callLog.setData_id(userData.getUid());
				callLog.setData_source(userData.getBatchUuid());
			}
			
			callLog.setCall_phone(phone);
			callLog.setIn_out_flag("呼出");
			callLog.setCall_time(date);
			callLog.setCall_session_uuid(jsonObject.getString("call_session_uuid"));
			callLogService.save(callLog);
			
//			try {
//				sendMessageByAutoCallOut(user, SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), callLog.getCall_phone(), callLog.getCall_session_uuid(), DateUtils.getDateString(date));
//			} catch (IOException e) {
//				System.out.println("============首次弹屏失败==============" + date);
//				e.printStackTrace();
//			}
			
			return new JSONObject().put("success", true).put("phone", phone).
					put("call_session_uuid", jsonObject.getString("call_session_uuid")).put("dataLog", new JSONObject(callLog)).toString();
		}
		return new JSONObject().put("success", false).toString();
	}
	
	
//	@RequestMapping("startThread")
//	public void startThread(){
//		
//		System.out.println("开启");
////		thread = new Thread(new DestoryTimer(""));
////		thread.start();
//	}
//	@RequestMapping("stopThread")
//	public void stopThread(){
//		
//		System.out.println("关闭");
////		thread = new Thread(new DestoryTimer(""));
////		thread.stop();
//	}
	
	/**
	 * 开始自动呼叫--电话开始呼叫
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("callNextPhone")
	@ResponseBody
	public String callNextPhone(HttpServletRequest request, HttpServletResponse response) {
		
		User user = SessionUtil.getCurrentUser(request);
		UserData userData = newUserDataService.getNextData(user.getDepartment(), user.getUid());
		if (userData == null) {
			return new JSONObject().put("success", false).put("message", "您已没有需要完成的任务").toString();
		}
		userData.setDeptUuid(user.getDepartment());
		
		JSONObject jsonObject = CallManager.makeCall(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), userData.getPhoneNumber(), user, null);
		
		if("0".equals(jsonObject.optString("exit_code","19999"))){
			
			Date date = new Date();
			
			//更新下时间，不知道还有没有更好的方案，放到接通时
			userData.setLastCallTime(date);
			userData.setCallCount(userData.getCallCount() + 1);
			newUserDataService.update(userData);
			
			CallLog callLog = new CallLog();
			callLog.setAgent_id(user.getUid());
			callLog.setAgent_name(user.getLoginName());
			callLog.setData_id(userData.getUid());
			callLog.setData_source(userData.getBatchUuid());
			callLog.setCall_phone(userData.getPhoneNumber());
			callLog.setIn_out_flag("呼出");
			callLog.setCall_time(date);
			callLog.setCall_session_uuid(jsonObject.getString("call_session_uuid"));
			callLogService.save(callLog);
			
//			try {
//				sendMessageByAutoCallOut(user, SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), callLog.getCall_phone(), callLog.getCall_session_uuid(), DateUtils.getDateString(date));
//			} catch (IOException e) {
//				System.out.println("============首次弹屏失败==============" + date);
//				e.printStackTrace();
//			}
			
			return new JSONObject().put("success", true).put("phone", userData.getPhoneNumber()).
					put("call_session_uuid", jsonObject.getString("call_session_uuid")).put("dataLog", new JSONObject(callLog)).toString();
		}
		return new JSONObject().put("success", false).put("message", "呼叫失败，会话过期，请重新登录再点击开始呼叫").toString();
		
	}
	
	@RequestMapping("updateAnswerTime")
	@ResponseBody
	public String callNextPhone(HttpServletRequest request, String phone, String date, HttpServletResponse response) {
		User user = SessionUtil.getCurrentUser(request);
		UserData userData = newUserDataService.getUserDataByPhone(user.getDepartment(), phone);
		if(userData == null) {
			return new JSONObject().toString();
		}
		userData.setLastCallTime(DateUtils.stringToDate(date));
		userData.setCallCount(userData.getCallCount() + 1);
		newUserDataService.update(userData);
		return new JSONObject().toString();
	}
	
	/**
	 * 放入共享池
	 * @param request
	 * @param batchuuid
	 * @param phoneNumber
	 * @param tag
	 * @return
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public String changeStatus(HttpServletRequest request, String phoneNumber, String tag, String department, String callSessionUuid) {
		
		if(StringUtils.isNoneBlank(phoneNumber, tag)){
			if (StringUtils.isBlank(department)) {
				department = SessionUtil.getCurrentUser(request).getDepartment();
			}else{
				
			}
			UserData userData = newUserDataService.getUserDataByPhone(department, phoneNumber);
			
			newUserDataService.updateData(userData, tag, null, null);
			
			return new JSONObject().put("success", true).toString();
		}
		
		return new JSONObject().put("success", false).toString();
	}
	
	/**
	 * 点击弹屏页面的保存按钮之后执行
	 * @param request
	 * @param phoneNumber
	 * @param action
	 * @param callSessionUuid
	 * @param callTime
	 * @param callStat
	 * @param cstmUuid
	 * @return
	 */
	@RequestMapping("saveData")
	@ResponseBody
	public String saveData(HttpServletRequest request, String phoneNumber, String action, String callSessionUuid, String callTime, String callStat, String cstmUuid){
		
		if(StringUtils.isNoneBlank(phoneNumber, action)){
			
			UserData userData = newUserDataService.getUserDataByPhone(SessionUtil.getCurrentUser(request).getDepartment(), phoneNumber);
			
			if(StringUtils.isNotBlank(callStat)) {
				userData.setLastCallResult(callStat);
				userData.setLastCallTime(DateUtils.stringToDate(callTime));
//				userData.setCallCount(userData.getCallCount() + 1);
			} else {
				
			}
			newUserDataService.update(userData);
			
			int result = newUserDataService.updateData(userData, action, callSessionUuid, cstmUuid);
			
			if (result == -1) {
				return new JSONObject().put("success", false).put("message", "超过意向客户上限").toString();
			}
			
			return new JSONObject().put("success", true).toString();
		}
		
		return new JSONObject().put("success", false).toString();
	}
	
	/**
	 * 转为意向 去除意向客户标记
	 * 
	 * @param request
	 * @param batchuuid
	 * @param phoneNumber
	 * @param tag
	 * @return
	 */
	@RequestMapping("toChangeStatuPage")
	public String toChangeStatuPage(HttpServletRequest request,
			String batchuuid, String phoneNumber, String intentType, String tag, Model model) {

		if ("intend".equals(tag)) { // 转为意向客户

			model.addAttribute("target", "1");
			model.addAttribute("datas", newUserDataService.getAllIntendType());
			model.addAttribute("intentType", intentType);
			model.addAttribute("phoneNumber", phoneNumber);
		} 

		return "datamanager/data_intend_dialog";
	}
	
	
	@RequestMapping("changeIntend")
	@ResponseBody
	public String changeIntend(HttpServletRequest request , String intend,String phoneNumber) {
		
		UserData userData = newUserDataService.getUserDataByPhone(SessionUtil.getCurrentUser(request).getDepartment(), phoneNumber);
		
		Integer integer = newUserDataService.updateData(userData, intend, null, null);
		
		if(integer > 0){

			return new JSONObject().put("success", true).toString();
		}

		return new JSONObject().put("success", false).toString();
	}
	/**
	 * 弹屏页面 点击保存并关闭按钮
	 * @param request
	 * @param values
	 * @return
	 */
	@RequestMapping("saveAll")
	@ResponseBody
	public String saveAll(HttpServletRequest request, SaveBean values){
		
		return dataCustomerLinkService.saveResult(request, values);
		
	}
	
	@RequestMapping("detailSaveAll")
	@ResponseBody
	public String detailSaveAll(HttpServletRequest request, SaveBean values){
		
		return dataCustomerLinkService.detailSaveResult(request, values);
		
	}
	
	/**
	 * 修改意向客户拥有者
	 * @param request
	 * @param batchUuid
	 * @param dataUuid
	 * @param ownerUuid
	 * @param department
	 * @param model
	 * @return
	 */
	@RequestMapping("changeOwner")
	public String changeOwner(HttpServletRequest request, String batchUuid, String dataUuid, String ownerUuid, String department, Model model){
		String[] dataUuids = request.getParameterValues("intentchecklist[]");
		List<String> changeids = new ArrayList<>(); 
		//如果不为空，并且数组中存有东西的话，就表示是在用户管理中的批量修改拥有者
		if(dataUuids != null && dataUuids.length >0){
			for(int i = 0;i<dataUuids.length;i++){
				changeids.add(dataUuids[i]);
			}
			model.addAttribute("dataUuids", changeids);
			
		}else{
			model.addAttribute("dataUuid", dataUuid);
			model.addAttribute("batchUuid", batchUuid);
		}
		department = userService.getByUuid(UUID.UUIDFromString(ownerUuid)).getDepartment();
		model.addAttribute("deptUuid", department);
		model.addAttribute("ownerUuid", ownerUuid);
		model.addAttribute("users", userService.getAllUser(Arrays.asList(department)));
		return "datamanager/change_owner";
	}
	
	@RequestMapping("changeUser")
	@ResponseBody
	public String changeUser(HttpServletRequest request, String batchUuid, String deptUuid, String ownerUuid, String dataUuid, String newOwnUser, Model model){
		String[] datas = request.getParameterValues("dataUuids");
		//选择的转移目标是自己的话 直接返回成功
		if (ownerUuid.equals(newOwnUser)) {
			return new JSONObject().put("success", true).toString();
		}
		//如果需要转的数据不止一条的话表示批量转移
		if(datas != null && datas.length > 0){
			newUserDataService.changeUsers(deptUuid, ownerUuid, newOwnUser, datas);
		}else{
			newUserDataService.changeUser(batchUuid, deptUuid, ownerUuid, dataUuid, newOwnUser);
		}
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 全选所有
	 * 
	 * @return
	 */
	@RequestMapping("intentcheckAll")
	@ResponseBody
	public String checkAll(HttpServletRequest request,String userid,String dept) {

		JSONObject jsonObject2 = new JSONObject();
		List<String> dataIds = newUserDataService.getDataByOwnUser(userid,dept);
		JSONArray jsonArray = new JSONArray();
		for(String i : dataIds){
			jsonArray.put(i.toString());
		}
		jsonObject2.put("intentdata",jsonArray);
		return jsonObject2.toString();
	}
	
	
	
	/**
	 * 呼出弹屏
	 * @param request
	 * @param arg0
	 * @param arg1
	 * @param model
	 * @return
	 */
	@RequestMapping("newdatacall")
	public String newDataCall(HttpServletRequest request, String arg0, String arg1, Model model){
		arg0 = cstmPhoneService.deletZero(arg0);
		
		String callSession_uuid = "";
		if(arg0.contains("|")) {
			arg0 = arg0.substring(arg0.indexOf("|") + 1);//去除弹屏|三个字
		}
		
		String type = "";
		
		if(arg1.contains("(")){
			arg1 = arg1.replace("(", "{").replace(")", "}");
			JSONObject jsonObject = new JSONObject(arg1);
			callSession_uuid = jsonObject.optString("call_session_uuid");
			
			arg0 = jsonObject.optString("number");
			if(StringUtils.isBlank(arg0)) {
				arg0 = jsonObject.optString("caller_id");
			}
			type = jsonObject.optString("popType");
		}
		
		if(arg0.contains("(")){
			arg0 = arg0.replace("(", "{").replace(")", "}");
			arg1 = arg0;
			JSONObject jsonObject = new JSONObject(arg0);
			callSession_uuid = jsonObject.optString("call_session_uuid");
			arg0 = jsonObject.optString("caller_id");
		}
		
		User user = SessionUtil.getCurrentUser(request);
		String batchUuid = dataBatchService.getPhoneBatch(arg0);
		if (batchUuid == null && !"detail".equals(type)) {
			newUserDataService.createData(user.getDepartment(), user.getUid(), arg0);
		}
		
		UserData entry = newUserDataService.getUserDataByPhone(SessionUtil.getCurrentUser(request).getDepartment(), arg0);
		
		//不是自己的，则直接置为null
		if( entry != null && !SessionUtil.getCurrentUser(request).getUid().equals(entry.getOwnUser())) {
			entry = null;
		}
		
		model.addAttribute("intents", dataIntentService.getAll());
		model.addAttribute("entry", entry);
		model.addAttribute("currentUser", SessionUtil.getCurrentUser(request));
		
		UserConfig userconfig = userConfigService.getByUuid(user.getUid());
		//在个人配置中获取到默认选择的值,如果没有则给默认值
		String  defUserCfg = "{\"time\":\"5\",\"timing\":\"1\",\"nocall\":\"noanswer\",\"intent\":\"global_share\"}";
		
//		JSONObject uc = new JSONObject(userconfig==null?defUserCfg:userconfig.getConfig());
//		model.addAttribute("userconfig", uc.optString("intent","global_share"));
		
		if(userconfig != null){
			//默认显示的值
			JSONObject def = new JSONObject(userconfig.getConfig());
			if(StringUtils.isNotBlank(def.optString("intent"))){
				model.addAttribute("userconfig", def.optString("intent","global_share"));
			}
			if(StringUtils.isNotBlank(def.optString("nocall"))){
				model.addAttribute("defaultUserNocall", def.get("nocall").toString());
			}
		}else{
			//默认显示的值
			JSONObject def = new JSONObject(defUserCfg);
			if(StringUtils.isNotBlank(def.optString("intent"))){
				model.addAttribute("userconfig", def.optString("intent","global_share"));
			}
			if(StringUtils.isNotBlank(def.optString("nocall"))){
				model.addAttribute("defaultUserNocall", def.get("nocall").toString());
			}
		}
		
		if("detail".equals(type)) {
			model.addAttribute("type", "detail");
		}
		
		CustomerController customerController =  (CustomerController) ApplicationHelper.getApplicationContext().getBean("customerController");

		//根据编号或者电话查询客户信息
		customerController.getDataByIdOrPhone(arg0, model, request);
		
		String miniorNumber = setPhoneDetailfInfo(model,arg0,request);
		
		//根据号码查询匹配的客户数
		List<Map<String, Object>> map = customerService.getCustomersInfoByIdOrPhone(arg0);
		if(null != map && map.size()>=0 ){
			model.addAttribute("sameCount", map.size());
			List<Customer> list = new ArrayList<Customer>();
			for (int i = 0; i < map.size(); i++) {
				Customer customer = new Customer();
				customer.setCustomerId((String) map.get(i).get("customer_id"));
				customer.setCustomerName((String) map.get(i).get("cstm_name"));
				list.add(customer);
			}
			
			model.addAttribute("sameList", list);
		}
		
		getBussinessInfo(model);
		
		model.addAttribute("location", CallManager.getPhoneLocation(arg0));
		//呼叫日志信息
		getCallLogInfo(model);
		
		//存放电话详细信息
		saveCallInfoToModel(model,arg1);
		
		if(StringUtils.isNotBlank(callSession_uuid)){
			
			model.addAttribute("callsessionuuid", callSession_uuid);
		}

		//查询代办事项
		ScheduleReminder jsonObject = scheduleReminderService.getReminderByPhone(arg0,miniorNumber);
		if(null != jsonObject){
			
			model.addAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(jsonObject.getSchStartTime()));
			model.addAttribute("content", jsonObject.getScheduleContent());
			model.addAttribute("scheduleId", jsonObject.getUid());
		}
		
		//读取默认选中的意向类型
//		System.out.println(entry.getIntentType()+"------------------");
		
		model.addAttribute("iframecontent","screenpop/finalTab");
		return "iframe";
		
	}
	
	/**
	 * 获取业务逻辑信息
	 * @param model
	 */
	public void getBussinessInfo(Model model) {
		JSONObject detail = new JSONObject();
		detail.put("info",new JSONObject().put("poplog", "record"));
//		detail.put("info",new JSONObject().put("poplog", "record").put("order", "order").put("appointment", "appointment"));

		JSONObject jsonArray =detail.getJSONObject("info");
		Map<String, String> showTab = new LinkedHashMap<String, String>();
		for (Object str : jsonArray.keySet()) {
			showTab.put(String.valueOf(str), PopEventHandler.allBusiness.get(jsonArray.get(str.toString())));
		}
		
		model.addAttribute("showTabs", showTab);
	}

	/**
	 * 呼叫日志信息
	 * @param model
	 */
	public void getCallLogInfo(Model model) {
		//查询所有列
		JSONObject jsonObject =  callLogService.getTitleAndData(divTableService);
		divTableService.getTitles(model, jsonObject); 
		
		/* 得到所有列 */
		Map<String, ColumnDesign> mapss = customerDesignService.getAddedColumn("sys_call_log");
		model.addAttribute("dataRows", mapss);
	}
	
	private void saveCallInfoToModel(Model model, String arg1) {

		//如果arg1不为空
		if(StringUtils.isNotBlank(arg1)){
			try {
				JSONObject jsonObject = new JSONObject(arg1);
				getPhoneDetails(model, jsonObject);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getPhoneDetails(Model model, JSONObject jsonObject) {
		//呼叫时间
		model.addAttribute("timestamp", jsonObject.optString("timestamp",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
		//通话类型
		model.addAttribute("call_direction", jsonObject.optString("call_direction","无"));
		//来电描述
		model.addAttribute("ivr_desc", jsonObject.optString("ivr_desc","无"));
		//热线号码
		model.addAttribute("access_number", jsonObject.optString("access_number","无"));
		//查询第二个号码
//		customerService.selectSecondNumber(jsonObject.optString(""));
	}
	
	/**
	 *查询第二号码
	 * @param model
	 * @param arg0
	 */
	private String setPhoneDetailfInfo(Model model, String arg0,HttpServletRequest request) {
		
		String minorNumber = customerService.selectSecondNumber(arg0)==null?"":customerService.selectSecondNumber(arg0);
		
		model.addAttribute("secondNumber", minorNumber);
		
		return minorNumber;
	}
	
	
	
	
	
	
	@RequestMapping("checkAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, NewUserDataCondition condition,
			Model model) {
		if("1".equals(condition.getDetail())){
			condition.setDetail("1");
		}
		condition.setOwnUser(SessionUtil.getCurrentUser(request).getUid());
		condition.setDeptName(SessionUtil.getCurrentUser(request).getDepartment());
		
		JSONObject jsonObject2 = new JSONObject();
		condition.setRequest(request);
		JSONArray jsonArray = new JSONArray(newUserDataService.getAllUser(condition));
		jsonObject2.put("datas", jsonArray);
		return jsonObject2.toString();
	}
	
	public void sendMessageByAutoCallOut(User user, String exten, String number, String callSessionUuid, String timeStamp) throws IOException{

//		String autoBusy =  configService.getSysConfigByKey("autoBusy").getSysVal();
		
		JSONObject json = new JSONObject();
		json.put("agent_id", user.getLoginName()).put("agent_info", user.getUserDescribe()).put("exten", exten).put("number", number)
			.put("call_session_uuid", callSessionUuid).put("timestamp", timeStamp).put("openwindow", true).put("type", "callOutPop");
//			.put("autoBusy", autoBusy);
		
		boolean b = brokerService.sendToUser("/user", user.getLoginName(), json.toString());
	}
	
	
	//查看号码的详细信息，需要传入参数为：部门uuid，拥有者uuid即可，这两个放入arg1中
	@RequestMapping("phoneInfoDetail")
	public String detailInfo(HttpServletRequest request, String arg0, String arg1, Model model){
		//去除号码的0
		arg0 = PhoneUtil.getNumberPhone(arg0);
		String type = "";
		String userUuid = "";
		//把arg1格式化为json格式
		if(arg1.contains("(")){
			arg1 = arg1.replace("(", "{").replace(")", "}");
			JSONObject jsonObject = new JSONObject(arg1);
			arg0 = jsonObject.optString("number");
			if(StringUtils.isBlank(arg0)) {
				arg0 = jsonObject.optString("caller_id");
			}
			//从中获取用户的uuid
			userUuid = jsonObject.optString("userUuid");
			type = jsonObject.optString("popType");
		}
		
		User user = StringUtils.isBlank(userUuid)?SessionUtil.getCurrentUser(request):userService.getByUuid(UUID.UUIDFromString(userUuid));
//		String batchUuid = dataBatchService.getPhoneBatch(arg0);
		//对于detail的情况，这里应该是不会进去的，注释了保险一点
//		if (batchUuid == null && !"detail".equals(type)) {
//			newUserDataService.createData(user.getDepartment(), user.getUid(), arg0);
//		}
		//转移列表
		model.addAttribute("users", userService.getAllUser(Arrays.asList(user.getDepartment())));
		//这里的entry一定要存在，不存在会报错
		UserData entry = newUserDataService.getUserDataByPhone(user.getDepartment(), arg0);
		if (entry == null) {
			entry = new UserData();
		}
		model.addAttribute("intents", dataIntentService.getAll());
		model.addAttribute("entry", entry);
		model.addAttribute("currentUser", user);
		
		if("detail".equals(type)) {
			model.addAttribute("type", "detail");
		}
		
		CustomerController customerController =  (CustomerController) ApplicationHelper.getApplicationContext().getBean("customerController");

		//根据编号或者电话查询客户信息
		customerController.getDataByIdOrPhone(arg0, model, request);
		
		setPhoneDetailfInfo(model,arg0,request);
		
		//根据号码查询匹配的客户数
		List<Map<String, Object>> map = customerService.getCustomersInfoByIdOrPhone(arg0);
		if(null != map && map.size()>=0 ){
			model.addAttribute("sameCount", map.size());
			List<Customer> list = new ArrayList<Customer>();
			for (int i = 0; i < map.size(); i++) {
				Customer customer = new Customer();
				customer.setCustomerId((String) map.get(i).get("customer_id"));
				customer.setCustomerName((String) map.get(i).get("cstm_name"));
				list.add(customer);
			}
			model.addAttribute("sameList", list);
		}
		
		getBussinessInfo(model);
		
		model.addAttribute("location", CallManager.getPhoneLocation(arg0));
		//呼叫日志信息
		getCallLogInfo(model);
		
		//存放电话详细信息
		saveCallInfoToModel(model,arg1);
		
		//查询代办事项
		model.addAttribute("iframecontent","screenpop/finalDetailTab");
		return "iframe";
	}
	
	
	@RequestMapping("getPhoneJson")
	public String getPhoneJson(String phone,Model model){
		
//		if(StringUtils.isNotBlank(phone)){
			
			String a = newUserDataService.getPhoneJson(phone);
			
			if(a.length() > 2){
				
				Map<String, String> map =  QueryUtils.toHashMap(new JSONObject(a), new LinkedHashMap<String, String>());
				
				model.addAttribute("mapPhone", map);
			}
//		}
		
		return "data/data-phone-info";
		
	}
	
	
	
	
}
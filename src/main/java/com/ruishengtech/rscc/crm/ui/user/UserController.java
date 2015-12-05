package com.ruishengtech.rscc.crm.ui.user;

import java.io.IOException;
import java.util.ArrayList;
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
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.cstm.service.CustomerDesignService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.linkservice.UserDataLinkService;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.UserConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.UserConfigService;
import com.ruishengtech.rscc.crm.user.condition.UserCondition;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.GroupService;
import com.ruishengtech.rscc.crm.user.service.PermissionRoleService;
import com.ruishengtech.rscc.crm.user.service.PermissionService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *	
 *	model:模块名
 *  items:查找栏项目
 *  names:查找栏name
 *  titles:表格标题名
 *  columns:表格标题name属性
 *  
 *  all:父名
 *  title:弹出框名
 *  titles:需要输入项目
 *  names:输入项目name
 *  tree:tree的json
 *  entry:当前实体类
 *  group,permissionrole,datarangerole,permission,datarange
 *  所有内容
 */
@Controller
@RequestMapping("user/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private PermissionRoleService permissionRoleService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserDataLinkService userDataLinkService;
	
	@Autowired
	private UserConfigService userConfigService;
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@Autowired
	private NewUserDataService newUserDataService;
	
	@Autowired
	private CustomerDesignService customerDesignService;
	
	@Autowired
	private CustomerPoolService customerPoolService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("title", "用户");
		model.addAttribute("model", "user");
		model.addAttribute("items", new String[]{"用户名","姓名","角色","手机号","部门"});
		model.addAttribute("names", new String[]{"loginName","userDescribe","role","phone","departmentName"});
//		model.addAttribute("items", new String[]{"用户名","姓名","手机号","部门"});
//		model.addAttribute("names", new String[]{"loginName","userDescribe","phone","departmentName"});
		model.addAttribute("titles", new String[]{"用户名","姓名","角色","手机号","所属部门","工号","添加日期"});
		model.addAttribute("columns", new String[]{"loginName","userDescribe","role","phone","departmentName","jobNumber","date"});
		model.addAttribute("depts", datarangeService.getAllDatarange());
		
		model.addAttribute("iframecontent","user/user-user");
		return "iframe";
//		return "user/user-user";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, UserCondition userCondition,
			Model model) {
		userCondition.setRequest(request);
		userCondition.setIns(userService.getDepartments(SessionUtil.getCurrentUser(request).getUid()));
		if(StringUtils.isNotBlank(userCondition.getRole())){
			userCondition.setUuids(permissionRoleService.getUseridByRoleName(userCondition.getRole()));
		}
		PageResult<User> pageResult = userService.queryPage(userCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<User> list = pageResult.getRet();
		for (User i:list) {
//			i.setDepartment(i.getDepartmentName());
			JSONObject jsonObject = new JSONObject(i);
			jsonObject.put("date", DateUtils.getDateString(i.getDate()));
			jsonObject.put("role", permissionRoleService.getRoleByUser(i.getUid()).toString());
			jsonObject.put("type", "user");
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String saveUser(HttpServletRequest request,
			HttpServletResponse response, User user, String permissionRoles, String roledataranges, String fromBatch,
			Model model) {
		userDataLinkService.saveUser(user, permissionRoles.split(","), roledataranges.split(";"));
		if(StringUtils.isBlank(fromBatch)) {
			userService.refreshUserList();
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateUser(HttpServletRequest request,
			HttpServletResponse response, User user, String permissionRoles, String roledataranges, String fromBatch, 
			Model model) {
//		user.setDate(new Date());
//		user.setUuid(UUID.UUIDFromString(user.getUid()));
//		JSONObject jsonObject = new JSONObject();
//		JSONObject responseJson = AgentManager.addAgent(user);
//		if(responseJson.optInt("exti_code") != 0)
//			return jsonObject.put("success", false).toString();
//		if(!userService.update(user, permissionRoles.split(","), roledataranges.split(";"))){
//			AgentManager.addAgent(user);
//			return jsonObject.put("success", false).toString();
//		}
//		return jsonObject.put("success", true).toString();
		String ret = userDataLinkService.updateUser(user, permissionRoles, roledataranges);
		if(StringUtils.isBlank(fromBatch)) {
			userService.refreshUserList();
		}
		return ret;
	}
	
	@RequestMapping("resetPwd")
	@ResponseBody
	public String resetPwd(HttpServletRequest request,
			HttpServletResponse response,  String uuid) {
		if(StringUtils.isNotBlank(uuid)){
			User user = userService.getByUuid(UUID.UUIDFromString(uuid));
			user.setPassword("ruisheng123456");
			userService.update(user);
			return new JSONObject().put("success", true).toString();
		}
		return new JSONObject().put("success", false).toString();
	}
	
	/**
	 * 弹框
	 * @return
	 */
	@RequestMapping("add")
	public String addUser(HttpServletRequest request, Model model){
		model.addAttribute("model", "user");
		model.addAttribute("title", "添加用户");
		model.addAttribute("titles", new String[]{"用户名","密码","姓名"});
		model.addAttribute("names", new String[]{"loginName","password","userDescribe"});
//		model.addAttribute("notitles", new String[]{"手机号","工号", "呼叫名","呼叫号码"});
//		model.addAttribute("nonames", new String[]{"phone", "jobNumber", "callerIdName","callerIdNumber"});
		model.addAttribute("notitles", new String[]{"手机号","工号","邮箱"});
		model.addAttribute("nonames", new String[]{"phone", "jobNumber","mail"});
		model.addAttribute("permissionRoleTree",permissionRoleService.getPermissionRoleTree().toString());
		model.addAttribute("departments", datarangeService.getAllDatarange());
		model.addAttribute("hasDepartments",userService.getDepartments(SessionUtil.getCurrentUser(request).getUid()));
		model.addAttribute("datarangeTree",datarangeService.getDatarangeTree().toString());
		model.addAttribute("roleDatarangeTree",getRoleDatarangeTree());
		return "user/add-modal";
	}
	
	@RequestMapping("change")
	public String changeUser(HttpServletRequest request, UUID uuid, Model model){
		model.addAttribute("model", "user");
		model.addAttribute("title", "修改用户");
		model.addAttribute("titles", new String[]{"用户名","密码","姓名"});
		model.addAttribute("names", new String[]{"loginName","password","userDescribe"});
		model.addAttribute("notitles", new String[]{"手机号","工号","邮箱"});
		model.addAttribute("nonames", new String[]{"phone", "jobNumber","mail"});
//		model.addAttribute("notitles", new String[]{"手机号","工号", "呼叫名","呼叫号码"});
//		model.addAttribute("nonames", new String[]{"phone", "jobNumber", "callerIdName","callerIdNumber"});
		model.addAttribute("departments", datarangeService.getAllDatarange());
		model.addAttribute("permissionRoleTree",permissionRoleService.getPermissionRoleTree().toString());
		model.addAttribute("datarangeTree",datarangeService.getDatarangeTree().toString());
		model.addAttribute("permissionRole", userService.getPermissionRoles(uuid.toString()));
		model.addAttribute("datarange", userService.getAllDataranges(uuid.toString()));
		model.addAttribute("hasDepartments",userService.getDepartments(SessionUtil.getCurrentUser(request).getUid()));
		model.addAttribute("roleDatarange", userService.getDataranges(uuid.toString()));
		model.addAttribute("roleDatarangeTree",getRoleDatarangeTree());
		User u = userService.getByUuid(uuid);
		model.addAttribute("entry", u);
		return "user/update-modal";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteUser(UUID uuid, Model model){
		String ret = userDataLinkService.deleteUser(uuid);
		userService.refreshUserList();
		return ret;
	}
	
	@RequestMapping("batDelete")
	@ResponseBody
	public String deleteUsers(HttpServletRequest request, Model model){
		String[] uuids = request.getParameterValues("uuids[]");
		String result = userDataLinkService.batchDeleteUser(uuids);
		userService.refreshUserList();
		return result;
	}
	
	@RequestMapping("changename")
	@ResponseBody
	public String changeName(HttpServletRequest request, String userName) {
		if (StringUtils.isNotBlank(userName)) {
			User user = SessionUtil.getCurrentUser(request);
			if(!user.getUserDescribe().equals(userName)) {
				user.setUserDescribe(userName);
				userService.update(user);
			}
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("checkname/{type}")
	@ResponseBody
	public String checkName(HttpServletRequest request, String loginName) {
		if(userService.getUserByLoginName(loginName, false)!=null)
			return "false";
		return "true";
	}
	
	@RequestMapping("checkAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, UserCondition condition,
			Model model) {
		JSONObject jsonObject2 = new JSONObject();
		condition.setRequest(request);
		condition.setIns(userService.getDepartments(SessionUtil.getCurrentUser(request).getUid()));
		JSONArray jsonArray = new JSONArray(userService.getAllUser(condition));
		jsonObject2.put("users", jsonArray);
		return jsonObject2.toString();
	}
	
	@RequestMapping("forwardToAdminPage")
	public String forwardToAdminPage(){
		
		return "redirect:/user/user/getPage";
	}
	
	@RequestMapping("direct")
	public String direct(HttpServletRequest request){
		
		User oldUser = SessionUtil.getCurrentUser(request);
		//根据名字和ID查询原来的用户
		User loginedUser = userService.getByUuid(UUID.UUIDFromString(oldUser.getRealUserUuid()));
		SessionUtil.setSession(request, SessionUtil.CURRENTUSER, loginedUser);
		
		return "redirect:/doLogin";
		
	}
	
	@RequestMapping("getPage")
	public String getPage(){
		
		return "user/adminLoginPage";
	}
	
	@RequestMapping("updatePwd")
	public String updatePwd(HttpServletRequest request,Model model){
		model.addAttribute("user", SessionUtil.getCurrentUser(request));
		
		UserConfig userConfig = userConfigService.getByUuid(SessionUtil.getCurrentUser(request).getUid());
		String  defUserCfg = "{\"time\":\"5\",\"timing\":\"1\",\"nocall\":\"noanswer\",\"intent\":\"global_share\"}";
		model.addAttribute("config", userConfig == null?defUserCfg:userConfig.getConfig());
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
		
		return "user/userpwd_update";
	}
	
	@RequestMapping("savePwd")
	@ResponseBody
	public String savePwd(HttpServletRequest request,Model model,String password,String uid,String email){
		if(StringUtils.isNotBlank(uid)){
			
			User us = userService.getByUuid(UUID.UUIDFromString(uid));
			if(StringUtils.isNotBlank(password)){
				us.setPassword(password);
			}
			
			if(StringUtils.isNotBlank(email)){
				us.setMail(email);
			}
			
			userService.update(us);
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("checkoldPwd")
	@ResponseBody
	public void pNameRepeat(HttpServletRequest request, HttpServletResponse response, String oldPwd, String loginName, String uid)
			throws IOException {
		
		User user = userService.login(loginName,oldPwd);
		
		if(user != null){
            response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}		
	}
	
	/**
	 * 批量添加
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("batAdd")
	public String batAdd(HttpServletRequest request,Model model){
		model.addAttribute("permissionRoleTree",permissionRoleService.getPermissionRoleTree().toString());
		model.addAttribute("datarangeTree",datarangeService.getDatarangeTree().toString());
		model.addAttribute("departments", datarangeService.getAllDatarange());
		model.addAttribute("hasDepartments",userService.getDepartments(SessionUtil.getCurrentUser(request).getUid()));
		model.addAttribute("roleDatarangeTree",getRoleDatarangeTree());
		return "user/bat-add-modal";
	}
	
	/**
	 * 批量修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("batChange")
	public String batChange(HttpServletRequest request,HttpServletResponse response,Model model){

		model.addAttribute("permissionRoleTree",permissionRoleService.getPermissionRoleTree().toString());
		model.addAttribute("datarangeTree",datarangeService.getDatarangeTree().toString());
		model.addAttribute("departments", datarangeService.getAllDatarange());
		model.addAttribute("hasDepartments",userService.getDepartments(SessionUtil.getCurrentUser(request).getUid()));
		model.addAttribute("roleDatarangeTree",getRoleDatarangeTree());
		return "user/bat-change-modal";
	}
	/**
	 * @param request
	 * @param response
	 * @param prefix
	 * @param start
	 * @param end
	 * @param passwordType
	 * @param password
	 * @param jobNumber
	 * @param callerIdName
	 * @param callerIdNumber
	 * @param department
	 * @param permissionRoles
	 * @param roledataranges
	 * @param model
	 * @return
	 */
	@RequestMapping("batAddSave")
	@ResponseBody
	public String batAddSave(HttpServletRequest request,
			HttpServletResponse response, String prefix, String start,
			String end, Integer passwordType, String password,
			String jobNumber, String callerIdName, String callerIdNumber,
			String department, String permissionRoles, String roledataranges,Model model) {
		int startNum = Integer.parseInt(start);
		int endNum = Integer.parseInt(end);
		int length = Math.max(start.length(), end.length());
		for (int i = startNum ; i <= endNum ; i++) {
			User user = new User();
			String userName = prefix + String.format("%0" + length + "d", i);
			user.setLoginName(userName);
			user.setUserDescribe(userName);
			user.setPhone("");
			String finalPassword;
			switch (passwordType) {
			case 0:
				finalPassword = userName;
				break;
			case 1:
				finalPassword = String.format("%06d", (int)Math.floor(Math.random() * 1000000));break;
			default:
				finalPassword = password;
			}
			user.setPassword(finalPassword);
			user.setJobNumber(userName);
			user.setCallerIdName(callerIdName);
			user.setCallerIdNumber(callerIdNumber);
			user.setDepartment(department);
			
			User nuser = userService.getUserByLoginName(userName, false);
			if(nuser == null){
				this.saveUser(request, response, user, permissionRoles, roledataranges, "fromBatch", model);
			}else{
				user.setUid(nuser.getUid());
				this.updateUser(request, response, user, permissionRoles, roledataranges, "fromBatch", model);
			}
		}
		userService.refreshUserList();
		return new JSONObject().put("success", true).toString();
	}
	
	//获取树
	private String getRoleDatarangeTree() {
		JSONArray jsonArray = new JSONArray();
		
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "root");
        jsonObject.put("pId", "");
        jsonObject.put("name", "范围");
        jsonObject.put("open", true);
        jsonArray.put(jsonObject);
        
		JSONArray datarangeArray = datarangeService.getDatarangeRootTree();
		JSONArray queueArray = QueueManager.getQueueTree();
		for(int i = 0; i < datarangeArray.length(); i++) {
			jsonArray.put(datarangeArray.get(i));
		}
		
		for(int i = 0; i < queueArray.length(); i++) {
			jsonArray.put(queueArray.get(i));
		}
		return jsonArray.toString();
	}
	
	/**
	 * 展示用户数据详情
	 * @param userid
	 * @param model
	 * @return
	 */
	@RequestMapping("getUserData")
	@ResponseBody
	public String getUserData(HttpServletRequest request,String userid,Model model){
		JSONObject json = new JSONObject();
		String[] uuids = request.getParameterValues("uuids[]");
		String idstr = request.getParameter("uuidstr");
		JSONArray jsonArray = new JSONArray();
		int n = 0;
		
		if(StringUtils.isNotBlank(userid)){
			
			json = new JSONObject(userService.getUserDataByUserid(userid));
		}else if(uuids != null && uuids.length >0){
			
			for(String id : uuids){
				Map<String, Object> map = userService.getUserDataByUserid(id);
				if(Integer.parseInt(map.get("taskCount").toString()) > 0 || Integer.parseInt(map.get("intentCount").toString()) > 0 || Integer.parseInt(map.get("cstmCount").toString()) > 0){
					json.put("success", true);
				}
			}
			//下面是将不能删除的用户数据存为json数据，然后在页面上返回一个datatable，暂时不用
		}else if(StringUtils.isNotBlank(idstr)){
			String[] ids = idstr.split("-");
			for(String id : ids){
				Map<String, Object> map = userService.getUserDataByUserid(id);
				if(Integer.parseInt(map.get("taskCount").toString()) > 0 || Integer.parseInt(map.get("intentCount").toString()) > 0 || Integer.parseInt(map.get("cstmCount").toString()) > 0){
					json.put("success", true);
					n++;
					JSONObject jsonObject2 = new JSONObject(map);
					jsonObject2.put("userid", id);
					jsonObject2.put("username", userService.getByUuid(UUID.UUIDFromString(id)).getLoginName());
					jsonArray.put(jsonObject2);
				}
			}
			json.put("data", jsonArray);
			json.put("iTotalRecords", n);
			json.put("iTotalDisplayRecords", n);	
			
		}
		return json.toString();
//		{"iTotalDisplayRecords":1,"iTotalRecords":1,"data":[{"intentCount":1,"cstmCount":2,"username":"1201","taskCount":6,"userid":"192f58a32adf410ca793d447eb30a2ed"}],"success":true}
//		{"iTotalDisplayRecords":55,"iTotalRecords":55,"data":[{"uid":"0","mail":"417298302@qq.com","phone":"00000000","department":"01","adminFlag":"1","checkUrl":"1446435927000-36ef9fba2c54445484089f9a042f96d8","type":"user","date":"2015-09-17 16:50:30","password":"admin","departmentName":"默认部门","jobNumber":"000000","role":"[管理员]","uuid":{"uuid":"0"},"deleteFlag":"0","userDescribe":"超级管理员","loginName":"admin"},{"uid":"10420098002549c6b1db56ea167b3f23","phone":"","department":"01","type":"user","date":"2015-11-17 13:49:30","password":"1513","departmentName":"默认部门","jobNumber":"1513","role":"[坐席]","uuid":{"uuid":"10420098002549c6b1db56ea167b3f23"},"deleteFlag":"0","userDescribe":"1513","loginName":"1513"},{"uid":"10ab4141aa824b3cbf6fdcbc555b30fc","phone":"","department":"01","type":"user","date":"2015-11-17 13:49:29","password":"1506","departmentName":"默认部门","jobNumber":"1506","role":"[坐席]","uuid":{"uuid":"10ab4141aa824b3cbf6fdcbc555b30fc"},"deleteFlag":"0","userDescribe":"1506","loginName":"1506"},{"uid":"174ef14cc8334756bfec3a0924348dbb","phone":"","department":"01","type":"user","date":"2015-11-17 13:49:31","password":"1523","departmentName":"默认部门","jobNumber":"1523","role":"[坐席]","uuid":{"uuid":"174ef14cc8334756bfec3a0924348dbb"},"deleteFlag":"0","userDescribe":"1523","loginName":"1523"},{"uid":"192f58a32adf410ca793d447eb30a2ed","mail":"","phone":"","department":"71801b50f29c475688c4912e5304afef","type":"user","date":"2015-09-17 16:50:30","password":"1201","departmentName":"销售2组","jobNumber":"1201","role":"[坐席]","uuid":{"uuid":"192f58a32adf410ca793d447eb30a2ed"},"deleteFlag":"0","userDescribe":"销售2组_坐席1201","loginName":"1201"},{"uid":"19e2638bb9a84e6c95eec3b9cc4e4e69","phone":"","department":"01","type":"user","date":"2015-11-17 13:49:30","password":"1509","departmentName":"默认部门","jobNumber":"1509","role":"[坐席]","uuid":{"uuid":"19e2638bb9a84e6c95eec3b9cc4e4e69"},"deleteFlag":"0","userDescribe":"1509","loginName":"1509"},{"uid":"1a59c199871d418badf79c2ee5f52e29","phone":"","department":"01","type":"user","date":"2015-11-17 13:49:30","password":"1511","departmentName":"默认部门","jobNumber":"1511","role":"[坐席]","uuid":{"uuid":"1a59c199871d418badf79c2ee5f52e29"},"deleteFlag":"0","userDescribe":"1511","loginName":"1511"},{"uid":"1b1f49077d7c400b9c7bb80988e0eee2","mail":"","phone":"","department":"01","type":"user","date":"2015-09-22 11:27:40","password":"zuoxi","departmentName":"默认部门","jobNumber":"","role":"[坐席]","uuid":{"uuid":"1b1f49077d7c400b9c7bb80988e0eee2"},"deleteFlag":"0","userDescribe":"坐席","loginName":"zuoxi"},{"uid":"1d2b6befc09f47d9b47cc72f2faeecf6","phone":"","department":"01","type":"user","date":"2015-11-17 13:49:30","password":"1514","departmentName":"默认部门","jobNumber":"1514","role":"[坐席]","uuid":{"uuid":"1d2b6befc09f47d9b47cc72f2faeecf6"},"deleteFlag":"0","userDescribe":"1514","loginName":"1514"},{"uid":"1df07df0362747fd988c9835136fbdf5","phone":"","department":"01","type":"user","date":"2015-11-17 13:49:33","password":"1535","departmentName":"默认部门","jobNumber":"1535","role":"[坐席]","uuid":{"uuid":"1df07df0362747fd988c9835136fbdf5"},"deleteFlag":"0","userDescribe":"1535","loginName":"1535"}]}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userId 用户id
	 * @param dataType  需要查询的数据类型
	 * @return
	 */
	@RequestMapping("getTaskData")
	public String getTaskData(HttpServletRequest request,HttpServletResponse response,Model model,String userId,String dataType){
		if("task".equals(dataType)){
			model.addAttribute("user", userService.getByUuid(UUID.UUIDFromString(userId)));
			model.addAttribute("dataType", "task");
		}else if("intent".equals(dataType)){
			model.addAttribute("user", userService.getByUuid(UUID.UUIDFromString(userId)));
			
			UserConfig userConfig = userConfigService.getByUuid(userId);
			model.addAttribute("config", userConfig == null?null:userConfig.getConfig());
			
			//获取所有意向类型
			List<DataIntent> dataintent = dataIntentService.getAll();
			model.addAttribute("intents", dataintent);
			
			model.addAttribute("dataType", "intent");
		}else if("cstm".equals(dataType)){
			
			/* 获取所有不是默认的字段列 */
			Map<String, ColumnDesign> map = customerDesignService.getNotDefaultColumn();
			model.addAttribute("allMaps", map);
			
			// 获取所有用户
			List<User> users = userService.getAllUser();
			model.addAttribute("user", users);
			model.addAttribute("clickUser", userId);
			
			/* 所有客户池 */
			List<CustomerPool> customerPools = customerPoolService.getAllPools();
			model.addAttribute("allpools", customerPools);
			//所有意向类型
			model.addAttribute("allIntent", customerPoolService.getAllIntends());
			
			// 取到表头
			customerService.getTitles(model);
			//动态表要显示的表头
			customerService.getShowTitle(model);
			
			model.addAttribute("dataType", "cstm");
			model.addAttribute("level", "agent");
		}else{
			String[] uuids = request.getParameterValues("uuids[]");
			List<String> idstr = new ArrayList<>();
			if(uuids != null && uuids.length >0){
				for(String i : uuids ){
					Map<String, Object> map = userService.getUserDataByUserid(i);
					if(Integer.parseInt(map.get("taskCount").toString()) != 0 || Integer.parseInt(map.get("intentCount").toString()) != 0 || Integer.parseInt(map.get("cstmCount").toString()) != 0){
					
						idstr.add(userService.getByUuid(UUID.UUIDFromString(i)).getLoginName());
					}
				}
			}
			model.addAttribute("uuids", idstr);
		}
		return "user/user-getuserdata";
	}
	
}

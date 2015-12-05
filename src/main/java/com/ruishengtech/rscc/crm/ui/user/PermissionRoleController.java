package com.ruishengtech.rscc.crm.ui.user;

import java.util.Date;
import java.util.List;

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
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.user.condition.PermissionRoleCondition;
import com.ruishengtech.rscc.crm.user.model.PermissionRole;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.PermissionRoleService;
import com.ruishengtech.rscc.crm.user.service.PermissionService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("user/permissionrole")
public class PermissionRoleController {
	
	@Autowired
	private PermissionRoleService permissionRoleService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private DataContainerService dataContainerService;
	
	@Autowired
	private CustomerPoolService customerPoolService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("title", "角色");
		model.addAttribute("model", "permissionrole");
		model.addAttribute("items", new String[]{"权限角色名","角色描述"});
		model.addAttribute("names", new String[]{"permissionRoleName","permissionRoleDescribe"});
		model.addAttribute("titles", new String[]{"权限角色名","权限角色描述","添加日期"});
		model.addAttribute("columns", new String[]{"permissionRoleName","permissionRoleDescribe","date"});
		
		model.addAttribute("iframecontent","user/user-user");
		return "iframe";
		
//		return "user/user-user";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String savePermissionRole(HttpServletRequest request,
			HttpServletResponse response, PermissionRole permissionRole, String permissions, String roledataranges,
			Model model) {
		permissionRole.setDate(new Date());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", permissionRoleService.save(permissionRole, permissions.split(","), roledataranges.split(";")));
		return jsonObject.toString();
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updatePermissionRole(HttpServletRequest request,
			HttpServletResponse response, PermissionRole permissionRole, String permissions, String roledataranges,
			Model model) {
		permissionRole.setDate(new Date());
		permissionRole.setUuid(UUID.UUIDFromString(permissionRole.getUid()));
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", permissionRoleService.update(permissionRole, permissions.split(","), roledataranges.split(";")));
		return jsonObject2.toString();
	}
	
	/**
	 * 弹框
	 * @return
	 */
	@RequestMapping("add")
	public String addPermissionRole(Model model){
		model.addAttribute("model", "permissionrole");
		model.addAttribute("title", "添加权限角色");
		model.addAttribute("titles", new String[]{"权限角色名","角色描述"});
		model.addAttribute("names", new String[]{"permissionRoleName","permissionRoleDescribe"});
		model.addAttribute("permissionTree",permissionService.getPermissionTree().toString());
		model.addAttribute("roleDatarangeTree",getRoleDatarangeTree());
		
		return "user/add-modal";
	}
	
	@RequestMapping("change")
	public String changePermissionRole(UUID uuid, Model model){
		model.addAttribute("model", "permissionrole");
		model.addAttribute("title", "修改权限角色");
		model.addAttribute("titles", new String[]{"权限角色名","角色描述"});
		model.addAttribute("names", new String[]{"permissionRoleName","permissionRoleDescribe"});
		model.addAttribute("permissionTree",permissionService.getPermissionTree().toString());
		model.addAttribute("roleDatarangeTree",getRoleDatarangeTree());
		model.addAttribute("permission", permissionRoleService.getPermissions(uuid.toString()));
		model.addAttribute("roleDatarange", permissionRoleService.getRoleDataranges(uuid.toString()));
		PermissionRole r = permissionRoleService.getByUuid(uuid);
		model.addAttribute("entry", r);
		return "user/update-modal";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deletePermissionRole(UUID uuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", permissionRoleService.deleteById(uuid));
		return jsonObject2.toString();
	}
	
	@RequestMapping("batDelete")
	@ResponseBody
	public String deleteUsers(HttpServletRequest request, Model model){
		JSONObject jsonObject2 = new JSONObject();
		String[] uuids = request.getParameterValues("uuids[]");
		jsonObject2.put("success", permissionRoleService.batDelete(uuids));
		return jsonObject2.toString();
	}
	
	@RequestMapping("checkname/{type}")
	@ResponseBody
	public String checkName(HttpServletRequest request, String permissionRoleName, String uid) {
		if(uid == null && permissionRoleService.getPermissionRoleByName(permissionRoleName)==null){
			return "true";
		}else if(uid != null && permissionRoleName.equals(permissionRoleService.getByUuid(UUID.UUIDFromString(uid)).getPermissionRoleName())){
			return "true";
		}else if(permissionRoleService.getPermissionRoleByName(permissionRoleName)!=null){
			return "false";
		}
		return "true";
	}
	
	@RequestMapping("checkAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, PermissionRoleCondition condition,
			Model model) {

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray(permissionRoleService.getAllPermissionRole(condition));
//		List<PermissionRole> list = permissionRoleService.getAllPermissionRole();
//		for (PermissionRole i:list) {
//			jsonArray.put(i.getUid());
//		}
		jsonObject2.put("users", jsonArray);
		return jsonObject2.toString();
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, PermissionRoleCondition permissionRoleCondition,
			Model model) {
		permissionRoleCondition.setRequest(request);

		PageResult<PermissionRole> pageResult = permissionRoleService.queryPage(permissionRoleCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<PermissionRole> list = pageResult.getRet();
		for (PermissionRole i:list) {
			JSONObject jsonObject = new JSONObject(i);
			jsonObject.put("date", DateUtils.getDateString(i.getDate()));
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
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
        
//		JSONArray dataArray = dataContainerService.getDataContainerTree();
		JSONArray datarangeArray = datarangeService.getDatarangeRootTree();
		JSONArray queueArray = QueueManager.getQueueTree();
//		JSONArray customerPoolsArray = customerPoolService.getPoolsTree();
		
//		for(int i = 0; i < dataArray.length(); i++) {
//			jsonArray.put(dataArray.get(i));
//		}
		
		for(int i = 0; i < datarangeArray.length(); i++) {
			jsonArray.put(datarangeArray.get(i));
		}
		
		for(int i = 0; i < queueArray.length(); i++) {
			jsonArray.put(queueArray.get(i));
		}
		
//		for(int i = 0; i < customerPoolsArray.length(); i++) {
//			jsonArray.put(customerPoolsArray.get(i));
//		}
//		
		return jsonArray.toString();
	}

}

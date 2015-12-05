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
import com.ruishengtech.rscc.crm.user.condition.PermissionCondition;
import com.ruishengtech.rscc.crm.user.model.Action;
import com.ruishengtech.rscc.crm.user.model.Permission;
import com.ruishengtech.rscc.crm.user.service.ActionService;
import com.ruishengtech.rscc.crm.user.service.PermissionService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("user/permission")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private ActionService actionService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "permission");
		model.addAttribute("items", new String[]{"权限名","权限描述"});
		model.addAttribute("names", new String[]{"permissionName","permissionDescribe"});
		model.addAttribute("titles", new String[]{"权限名","权限动作","权限描述","父权限","添加日期"});
		model.addAttribute("columns", new String[]{"permissionName","permission","permissionDescribe","parentPermission","date"});
		
		model.addAttribute("iframecontent","user/user-user");
		return "iframe";
		
//		return "user/user-user";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String savePermission(HttpServletRequest request,
			HttpServletResponse response, Permission permission,
			Model model) {
		permission.setDate(new Date());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", permissionService.save(permission));
		return jsonObject.toString();
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updatePermission(HttpServletRequest request,
			HttpServletResponse response, Permission permission,
			Model model) {
		permission.setDate(new Date());
		permission.setUuid(UUID.UUIDFromString(permission.getUid()));
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", permissionService.update(permission));
		return jsonObject2.toString();
	}
	
	/**
	 * 弹框
	 * @return
	 */
	@RequestMapping("add")
	public String addPermission(Model model){
		model.addAttribute("model", "permission");
		model.addAttribute("title", "添加权限");
		model.addAttribute("titles", new String[]{"权限名","权限描述"});
		model.addAttribute("names", new String[]{"permissionName","permissionDescribe"});
		model.addAttribute("actions", actionService.getAllAction());
		model.addAttribute("all", permissionService.getAllPermission());
		return "user/add-modal";
	}
	
	@RequestMapping("change")
	public String changePermissionRole(UUID uuid, Model model){
		model.addAttribute("model", "permission");
		model.addAttribute("title", "添加权限");
		model.addAttribute("titles", new String[]{"权限名","权限描述"});
		model.addAttribute("names", new String[]{"permissionName","permissionDescribe"});
		model.addAttribute("all", permissionService.getAllPermission());
		model.addAttribute("actions", actionService.getAllAction());
		Permission p = permissionService.getByUuid(uuid);
		model.addAttribute("entry", p);
		return "user/update-modal";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deletePermissionRole(UUID uuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", permissionService.deleteById(uuid));
		return jsonObject2.toString();
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, PermissionCondition permissionCondition,
			Model model) {
		permissionCondition.setRequest(request);

		PageResult<Permission> pageResult = permissionService.queryPage(permissionCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<Permission> list = pageResult.getRet();
		for (Permission i:list) {
			JSONObject jsonObject = new JSONObject(i);
			Permission p = permissionService.getParent(i);
			Action a = actionService.getByUuid(UUID.UUIDFromString(i.getPermission()));
			jsonObject.put("permission", a==null?"无":a.getActionName());
			jsonObject.put("parentPermission", p==null?"系统":p.getPermissionName());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
}

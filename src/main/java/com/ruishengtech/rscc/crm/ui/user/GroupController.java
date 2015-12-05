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
import com.ruishengtech.rscc.crm.user.condition.GroupCondition;
import com.ruishengtech.rscc.crm.user.model.Group;
import com.ruishengtech.rscc.crm.user.service.DatarangeRoleService;
import com.ruishengtech.rscc.crm.user.service.GroupService;
import com.ruishengtech.rscc.crm.user.service.PermissionRoleService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("user/group")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private DatarangeRoleService datarangeRoleService;
	
	@Autowired
	private PermissionRoleService permissionRoleService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "group");
		model.addAttribute("items", new String[]{"分组名","分组描述"});
		model.addAttribute("names", new String[]{"groupName","groupDescribe"});
		model.addAttribute("titles", new String[]{"分组名","分组描述","父分组","添加日期"});
		model.addAttribute("columns", new String[]{"groupName","groupDescribe","parentGroup","date"});
		
		
		model.addAttribute("iframecontent","user/user-user");
		return "iframe";
//		return "user/user-user";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String saveGroup(HttpServletRequest request,
			HttpServletResponse response, Group group, String permissionRoles, String datarangeRoles,
			Model model) {
		group.setDate(new Date());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", groupService.save(group, permissionRoles.split(","), datarangeRoles.split(",")));
		return jsonObject.toString();
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateGroup(HttpServletRequest request,
			HttpServletResponse response, Group group, String permissionRoles, String datarangeRoles,
			Model model) {
		group.setDate(new Date());
		group.setUuid(UUID.UUIDFromString(group.getUid()));
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", groupService.update(group, permissionRoles.split(","), datarangeRoles.split(",")));
		return jsonObject2.toString();
	}
	
	/**
	 * 弹框
	 * @return
	 */
	@RequestMapping("add")
	public String addGroup(Model model){
		model.addAttribute("model", "group");
		model.addAttribute("title", "添加分组");
		model.addAttribute("titles", new String[]{"分组名","分组描述"});
		model.addAttribute("names", new String[]{"groupName","groupDescribe"});
		model.addAttribute("permissionRoleTree",permissionRoleService.getPermissionRoleTree().toString());
		model.addAttribute("datarangeRoleTree",datarangeRoleService.getDatarangeRoleTree().toString());
		model.addAttribute("all", groupService.getAllGroup());
		return "user/add-modal";
	}
	
	@RequestMapping("change")
	public String changeGroup(UUID uuid, Model model){
		model.addAttribute("model", "group");
		model.addAttribute("title", "添加分组");
		model.addAttribute("titles", new String[]{"分组名","分组描述"});
		model.addAttribute("names", new String[]{"groupName","groupDescribe"});
		model.addAttribute("permissionRoleTree",permissionRoleService.getPermissionRoleTree().toString());
		model.addAttribute("datarangeRoleTree",datarangeRoleService.getDatarangeRoleTree().toString());
		model.addAttribute("permissionRole", groupService.getPermissionRoles(uuid.toString()));
		model.addAttribute("datarangeRole", groupService.getDatarangeRoles(uuid.toString()));
		model.addAttribute("all", groupService.getAllGroup());
		Group g = groupService.getByUuid(uuid);
		model.addAttribute("entry", g);
		return "user/update-modal";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteGroup(UUID uuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", groupService.deleteById(uuid));
		return jsonObject2.toString();
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, GroupCondition groupCondition,
			Model model) {
		groupCondition.setRequest(request);

		PageResult<Group> pageResult = groupService.queryPage(groupCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<Group> list = pageResult.getRet();
		for (Group i:list) {
			JSONObject jsonObject = new JSONObject(i);
			Group g = groupService.getParent(i);
			jsonObject.put("parentGroup", g==null?"系统":g.getGroupName());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}

}

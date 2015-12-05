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
import com.ruishengtech.rscc.crm.user.condition.DatarangeRoleCondition;
import com.ruishengtech.rscc.crm.user.model.DatarangeRole;
import com.ruishengtech.rscc.crm.user.service.DatarangeRoleService;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("user/datarangerole")
public class DatarangeRoleController {
	
	@Autowired
	private DatarangeRoleService datarangeRoleService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "datarangerole");
		model.addAttribute("items", new String[]{"范围角色名","角色描述"});
		model.addAttribute("names", new String[]{"datarangeRoleName","datarangeRoleDescribe"});
		model.addAttribute("titles", new String[]{"范围角色名","范围角色描述","父角色","添加日期"});
		model.addAttribute("columns", new String[]{"datarangeRoleName","datarangeRoleDescribe","parentDatarangeRole","date"});
		
		
		model.addAttribute("iframecontent","user/user-user");
		return "iframe";
		
//		return "user/user-user";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String saveDatarangeRole(HttpServletRequest request,
			HttpServletResponse response, DatarangeRole datarangeRole, String dataranges,
			Model model) {
		datarangeRole.setDate(new Date());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", datarangeRoleService.save(datarangeRole, dataranges.split(",")));
		return jsonObject.toString();
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateDatarangeRole(HttpServletRequest request,
			HttpServletResponse response, DatarangeRole datarangeRole, String dataranges,
			Model model) {
		datarangeRole.setDate(new Date());
		datarangeRole.setUuid(UUID.UUIDFromString(datarangeRole.getUid()));
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", datarangeRoleService.update(datarangeRole, dataranges.split(",")));
		return jsonObject2.toString();
	}
	
	/**
	 * 弹框
	 * @return
	 */
	@RequestMapping("add")
	public String addDatarangeRole(Model model){
		model.addAttribute("model", "datarangerole");
		model.addAttribute("title", "添加范围角色");
		model.addAttribute("titles", new String[]{"范围角色名","角色描述"});
		model.addAttribute("names", new String[]{"datarangeRoleName","datarangeRoleDescribe"});
		model.addAttribute("datarangeTree",datarangeService.getDatarangeTree().toString());
		model.addAttribute("all", datarangeRoleService.getAllDatarangeRole());
		return "user/add-modal";
	}
	
	@RequestMapping("change")
	public String changeDatarangeRole(UUID uuid, Model model){
		model.addAttribute("model", "datarangerole");
		model.addAttribute("title", "添加范围角色");
		model.addAttribute("titles", new String[]{"范围角色名","角色描述"});
		model.addAttribute("names", new String[]{"datarangeRoleName","datarangeRoleDescribe"});
		model.addAttribute("datarangeTree",datarangeService.getDatarangeTree().toString());
		model.addAttribute("datarange", datarangeRoleService.getDataranges(uuid.toString()));
		model.addAttribute("all", datarangeRoleService.getAllDatarangeRole());
		DatarangeRole r = datarangeRoleService.getByUuid(uuid);
		model.addAttribute("entry", r);
		return "user/update-modal";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteDatarangeRole(UUID uuid, Model model){
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("success", datarangeRoleService.deleteById(uuid));
		return jsonObject2.toString();
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, DatarangeRoleCondition datarangeRoleCondition,
			Model model) {
		datarangeRoleCondition.setRequest(request);

		PageResult<DatarangeRole> pageResult = datarangeRoleService.queryPage(datarangeRoleCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DatarangeRole> list = pageResult.getRet();
		for (DatarangeRole i:list) {
			JSONObject jsonObject = new JSONObject(i);
			DatarangeRole p = datarangeRoleService.getParent(i);
			jsonObject.put("parentDatarangeRole", p==null?"系统":p.getDatarangeRoleName());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
}
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
import com.ruishengtech.rscc.crm.ui.linkservice.UserDataLinkService;
import com.ruishengtech.rscc.crm.user.condition.DatarangeCondition;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.DatarangeTypeService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("user/datarange")
public class DatarangeController {
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private DatarangeTypeService datarangeTypeService;
	
	@Autowired
	private UserDataLinkService userDataLinkService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("title", "部门");
		model.addAttribute("model", "datarange");
		model.addAttribute("items", new String[]{"部门名","部门描述"});
		model.addAttribute("names", new String[]{"datarangeName","datarangeDescribe"});
//		model.addAttribute("titles", new String[]{"部门名","部门类型","部门描述","上级部门","添加日期"});
//		model.addAttribute("columns", new String[]{"datarangeName","datarangeType","datarangeDescribe","parentDatarange","date"});
		model.addAttribute("titles", new String[]{"部门名","部门描述","上级部门","添加日期"});
		model.addAttribute("columns", new String[]{"datarangeName","datarangeDescribe","parentDatarange","date"});
		
		model.addAttribute("iframecontent","user/user-user");
		return "iframe";
		
//		return "user/user-user";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String saveDatarange(HttpServletRequest request,
			HttpServletResponse response, Datarange datarange,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		datarange.setDate(new Date());
		jsonObject.put("success", userDataLinkService.saveDatarange(datarange));
			// 创建两个和部门关联的表，用于放数据
		return jsonObject.toString();
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String updateDatarange(HttpServletRequest request,
			HttpServletResponse response, Datarange datarange,
			Model model) {
		return userDataLinkService.updateDatarange(datarange);
	}
	
	/**
	 * 弹框
	 * @return
	 */
	@RequestMapping("add")
	public String addDatarange(Model model){
		model.addAttribute("model", "datarange");
		model.addAttribute("title", "添加部门");
		model.addAttribute("titles", new String[]{"部门名","部门描述"});
		model.addAttribute("names", new String[]{"datarangeName","datarangeDescribe"});
//		model.addAttribute("types", datarangeTypeService.getTypes());
		model.addAttribute("all", datarangeService.getAllDatarange());
		model.addAttribute("datarangeTree",datarangeService.getDatarangeTree().toString());
//		model.addAttribute("all", new ArrayList<Datarange>());
		return "user/add-modal";
	}
	
	@RequestMapping("change")
	public String changeDatarangeRole(UUID uuid, Model model){
		model.addAttribute("model", "datarange");
		model.addAttribute("title", "修改部门");
		model.addAttribute("titles", new String[]{"部门名","部门描述"});
		model.addAttribute("names", new String[]{"datarangeName","datarangeDescribe"});
		Datarange p = datarangeService.getByUuid(uuid);
		model.addAttribute("entry", p);
//		model.addAttribute("all", datarangeService.getAllDatarange());
//		model.addAttribute("types", datarangeTypeService.getTypes());
//		model.addAttribute("all", datarangeService.getByType(UUID.UUIDFromString(p.getTypeUuid())));
		model.addAttribute("all", datarangeService.getAllDatarange());
		model.addAttribute("datarangeTree",datarangeService.getDatarangeTree().toString());
		return "user/update-modal";
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteDatarangeRole(UUID uuid, Model model){
//		JSONObject jsonObject2 = new JSONObject();
//		jsonObject2.put("success", datarangeService.deleteById(uuid));
		return userDataLinkService.deleteDatarange(uuid);
	}
	
	@RequestMapping("type")
	@ResponseBody
	public String onChange(HttpServletRequest request,
			HttpServletResponse response, UUID typeUuid,
			Model model) {
		JSONArray jsonArray = new JSONArray();
		List<Datarange> list = datarangeService.getByType(typeUuid);
		for(Datarange l:list){
			jsonArray.put(new JSONObject(l));
		}
		return jsonArray.toString();
	}
	
	@RequestMapping("batDelete")
	@ResponseBody
	public String deleteUsers(HttpServletRequest request, Model model){
		String[] uuids = request.getParameterValues("uuids[]");
		String result = userDataLinkService.batchDeleteDatarange(uuids);
		return result;
	}
	
	@RequestMapping("checkname/{type}")
	@ResponseBody
	public String checkName(HttpServletRequest request, String datarangeName,String uid) {
		if(uid == null && datarangeService.getDatarangeByName(datarangeName)==null){
			return "true";
		}else if(uid != null && datarangeName.equals(datarangeService.getByUuid(UUID.UUIDFromString(uid)).getDatarangeName())){
			return "true";
		}else if(datarangeService.getDatarangeByName(datarangeName)!=null){
			return "false";
		}
		return "true";
	}
	
	@RequestMapping("checkAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, DatarangeCondition condition,
			Model model) {

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray(datarangeService.getAllDatarange(condition));

		jsonObject2.put("users", jsonArray);
		return jsonObject2.toString();
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, DatarangeCondition datarangeCondition,
			Model model) {
		datarangeCondition.setRequest(request);

		PageResult<Datarange> pageResult = datarangeService.queryPage(datarangeCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<Datarange> list = pageResult.getRet();
		for (Datarange i:list) {
			JSONObject jsonObject = new JSONObject(i);
			Datarange p = datarangeService.getParent(i);
//			DatarangeType t = datarangeTypeService.getByUuid(UUID.UUIDFromString(i.getTypeUuid()));
			jsonObject.put("parentDatarange", p==null?"根":p.getDatarangeName());
//			jsonObject.put("datarangeType", t==null?"无":t.getTypeName());
			jsonObject.put("date", DateUtils.getDateString(i.getDate()));
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
}

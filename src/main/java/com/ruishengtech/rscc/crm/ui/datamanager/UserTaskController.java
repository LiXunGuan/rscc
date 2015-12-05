package com.ruishengtech.rscc.crm.ui.datamanager;

import java.io.IOException;
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
import com.ruishengtech.rscc.crm.datamanager.condition.UserTaskCondition;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.datamanager.service.imp.UserTaskServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * 人员数据管理
 * @author Frank
 */
@Controller
@RequestMapping("userManage")
public class UserTaskController {
	
	@Autowired
	private UserTaskServiceImp userTaskService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private SysConfigService configService;	
	
	/**
	 * @return
	 */
	@RequestMapping()
	public String userDataIndex(HttpServletRequest request, Model model){
		model.addAttribute("depts", datarangeService.getAllDatarange());
		
		
		model.addAttribute("iframecontent","datamanager/manageuser_index");
		return "iframe";
		
//		return "datamanager/manageuser_index";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request, UserTaskCondition userTaskCondition, Model model) {
		
		userTaskCondition.setRequest(request);
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
		userTaskCondition.setIns(departments);
		
		PageResult<UserTask> pageResult = userTaskService.queryPage(userTaskCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<UserTask> list = pageResult.getRet();
		for (UserTask dept : list) {
			JSONObject json = new JSONObject(dept);
			jsonArray.put(json);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("changeLimit")
	public String changeLimit(HttpServletRequest request, String userUuid, Model model) {
		UserTask userTask = userTaskService.getByUuid(userUuid);
		model.addAttribute("userUuid", userUuid);
		model.addAttribute("totalLimit", userTask.getTotalLimit());
		model.addAttribute("dayLimit", userTask.getDayLimit());
		model.addAttribute("singleLimit", userTask.getSingleLimit());
		model.addAttribute("intentLimit", userTask.getIntentLimit());
		return "datamanager/manageuser_changelimit";
	}
	
	@RequestMapping("changeLimitSave")
	@ResponseBody
	public String changeLimitSave(HttpServletRequest request, String userUuid, Integer totalLimit, Integer dayLimit, Integer singleLimit, Integer intentLimit, Model model) {
		userTaskService.changeLimit(userUuid, totalLimit, dayLimit, singleLimit, intentLimit);
		return new JSONObject().put("success", true).toString();
	}
	
	
	@RequestMapping("levelUp")
	@ResponseBody
	public String levelUp(){
		userTaskService.levelUp();
		return new JSONObject().put("success", true).toString();
	}
	
	
	/**
	 * 判断当日上限不能超过系统设置的系统当日上限
	 * @param request
	 * @param response
	 * @param limit
	 * @param dueTime 期限  单日 单次
	 * @throws IOException
	 */
	@RequestMapping("limitCompare")
	@ResponseBody
	public void dayLimitConpare(HttpServletRequest request , HttpServletResponse response, Integer dayLimit, Integer singleLimit, Integer totalLimit, String dueTime) throws IOException{
		
		Integer limit = dayLimit != null ? dayLimit : singleLimit != null ? singleLimit : totalLimit;
		
		String dataLimit = null;
		
		// 获取设置的系统上限  单日
		if("day".equals(dueTime)){
			
			dataLimit = configService.getSysConfigByKey("sys.data.getDataDayLimit").getSysVal();
		}else if("single".equals(dueTime)){
			//单次
			dataLimit = configService.getSysConfigByKey("sys.data.getDataSingleLimit").getSysVal();
		}else if("sum".equals(dueTime)){
			// 单日
			dataLimit = configService.getSysConfigByKey("sys.data.getDataTotalLimit").getSysVal();
		}
		//如果设置的个人设置大小小于系统设置的总量
		if(limit != null){
			if(Integer.parseInt(dataLimit) >= limit){
				response.getWriter().print(true);
				return ;
			}
		}
		
		response.getWriter().print(false);
	}
	
	@RequestMapping("getCheck")
	@ResponseBody
	public String getChecked(String direct,String num){
		
		//获取系统单日 单次上限
		String dataLimit = "";
		String msg = "";
		
		if("day".equals(direct)){
			dataLimit = userTaskService.compareLimit("day");
			msg = "有人的单日上限超过了你设置的值,请修改。";
		}else{
			dataLimit = userTaskService.compareLimit("single");
			msg = "有人的单次上限超过了你设置的值,请修改。";
		}
		if(StringUtils.isNotBlank(num)){
			if(Integer.parseInt(dataLimit) <= Integer.parseInt(num)){
				return new JSONObject().put("success", true).toString();
			}
		}

		return new JSONObject().put("success", false).put("message", msg).toString();
	}
	
	@RequestMapping("batChange")
	public String batChange(HttpServletRequest request, Model model) {
		
		Map<String, SysConfig> map = SysConfigManager.getInstance().getDataMap();
		model.addAttribute("totalLimit", Integer.parseInt(map.get("sys.data.getDataTotalLimit")==null?"1000":map.get("sys.data.getDataTotalLimit").getSysVal()));
		model.addAttribute("dayLimit", 	Integer.parseInt(map.get("sys.data.getDataDayLimit")==null?"1000":map.get("sys.data.getDataDayLimit").getSysVal()));
		model.addAttribute("singleLimit",Integer.parseInt(map.get("sys.data.getDataSingleLimit")==null?"500":map.get("sys.data.getDataSingleLimit").getSysVal()));
		model.addAttribute("intentLimit", Integer.parseInt(map.get("sys.data.getIntentTotalLimit")==null?"500":map.get("sys.data.getIntentTotalLimit").getSysVal()));
		return "datamanager/manageuser_batchange";
	}
	
	/**
	 * 批量修改上限值
	 */
	@RequestMapping("batChangeSubmit")
	@ResponseBody
	public String batChangeSubmit(HttpServletRequest request,String batchangedata, Integer totalLimit, Integer dayLimit, Integer singleLimit, Integer intentLimit, Model model) {
		String u = request.getParameterValues("uuids")[0];
		String[] uuids = u.split(",");
		
		if(uuids != null && uuids.length >0){
			
			userTaskService.changebat(uuids, batchangedata,totalLimit, dayLimit, singleLimit, intentLimit);
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("checkAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, UserTaskCondition condition,
			Model model) {
		JSONObject jsonObject2 = new JSONObject();
		condition.setRequest(request);
		//这里不能必须要传入condition参数，否则会全选错误
		JSONArray jsonArray = new JSONArray(userTaskService.getAll(condition));
		jsonObject2.put("alltask", jsonArray);
		return jsonObject2.toString();
	}
}

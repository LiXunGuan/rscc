package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.net.SocketException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.App;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.Page;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.data.condition.ItemCondition;
import com.ruishengtech.rscc.crm.ui.IndexService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

/**
 * @author Wangyao
 *
*/
@Controller
@RequestMapping("calllog")
public class CallLogController {
	
	
	@Autowired
	private CallLogService callLogService;

	@Autowired
	@Qualifier(value = "diyTableService")
	private DiyTableService divTableService;
	
	@Autowired
	private SysConfigService sysService;
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping
	public String index(Model model,HttpServletRequest request) {

		model.addAttribute("level", App.getDateRange(request)!=null?App.getDateRange(request):"dept");
		
		model.addAttribute("maps", callLogService.getSelectData());
		model.addAttribute("columns", callLogService.getColumns());
		model.addAttribute("selects", callLogService.getSelects());
		
		/* 获取所有不是默认的字段列 */
		Map<String, ColumnDesign> map = divTableService.getDiyColumns("sys_call_log");
		model.addAttribute("allMaps", map);
		
		//是否隐藏号码
		indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());
		
		
        model.addAttribute("iframecontent","call/call-log");
		return "iframe";

		
//		return "call/call-log";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, String projectUuid, ItemCondition itemCondition,String menutype,
			Model model) {
		itemCondition.setRequest(request);
		//添加到request中用于后续判断点击的是哪个菜单
		request.setAttribute("level", menutype);
		Map<String, ColumnDesign> map  = callLogService.getTableDef();
		PageResult<Map<String, Object>> pageResult = callLogService.queryPage(map, request);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<Map<String, Object>> tasks = pageResult.getRet();
		for (int i = 0; i < tasks.size(); i++) {
			jsonArray.put(callLogService.getJsonObject(tasks.get(i)));
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	@RequestMapping("change")
	public String change(HttpServletRequest request, HttpServletResponse response,Model model) {
//		JSONObject jsonObject = new JSONObject();
//		Map<String, String[]> map = request.getParameterMap();
		String uuid = request.getParameter("uuid");
//		String tableName = request.getParameter("tableName");
		
		Map<String, ColumnDesign> maps = divTableService.getDiyColumns("sys_call_log");
		
		Map<String, Object> map= callLogService.getCallLogById(UUID.UUIDFromString(uuid));
	
		String flag = "";
		if("呼出".equals(map.get("in_out_flag")))
			flag = "in";
		else
			flag = "out";
			
		model.addAttribute("maps", maps);
		
		model.addAttribute("map", map);
		
		model.addAttribute("flag", flag);
		
		return "call/update-log";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String update(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		JSONObject jsonObject = new JSONObject();
		Map<String, String[]> map = request.getParameterMap();
		String uuid = request.getParameter("uid");
		if (uuid == null)
			uuid = request.getParameter("uuid");
		if (StringUtils.isNotBlank(uuid)) {
			callLogService.update(map);
			return jsonObject.put("success", true).toString();
		}else{
			
			CallLog callLog = new CallLog();
			callLog.setAgent_id(SessionUtil.getCurrentUser(request).getUid());
			callLog.setAgent_name(SessionUtil.getCurrentUser(request).getLoginName());
			callLog.setCall_phone(request.getParameter("cPhone"));
			callLog.setText_log(request.getParameter("text_log"));
			callLog.setCall_time(new Date());
			callLog.setRecord_path("");
			callLog.setIn_out_flag("");
			
			callLogService.save(callLog);
			return jsonObject.put("success", true).toString();
		}
		
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response, String uuid) throws ParseException {
		JSONObject jsonObject = new JSONObject();
		callLogService.deleteById(UUID.UUIDFromString(uuid));
		return jsonObject.put("success", true).toString();
	}
	@RequestMapping("get")
	@ResponseBody
	public String getCallLog(HttpServletRequest request, HttpServletResponse response, String uuid) throws ParseException {
		CallLog callLog = callLogService.getCallLogBySession(uuid);
		if (callLog == null)
			return new JSONObject().toString();
		JSONObject jsonObject = new JSONObject(callLog).put("call_time", DateUtils.getDateString(callLog.getCall_time()));
		return new JSONObject().put("success", true).put("entry", jsonObject).toString();
	}
	
	@RequestMapping("getCallLog")
	@ResponseBody
	public String getCallLog(HttpServletRequest request, HttpServletResponse response,Page page, String phone,String phone1) throws ParseException {
		
		Map<String, ColumnDesign> map = divTableService.getTableDescByName("sys_call_log").getClomns();

//		request.setAttribute("record", true);
		request.setAttribute("phone", phone);
		request.setAttribute("phone1", phone1);
		PageResult<Map<String, Object>> pageResult = callLogService.queryPage(map, request);

		List<Map<String, Object>> customers = pageResult.getRet();

		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < customers.size(); i++) {
			
			JSONObject jsonObject2 = callLogService.getJsonObject(customers.get(i));
			
			array.put(jsonObject2);
		}
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords",pageResult.getiTotalDisplayRecords());
		    
		return jsonObject.put("success", true).toString();
	}
	
	@RequestMapping("player")
	public String getScopExt(HttpServletRequest request, HttpServletResponse response, String uid, Model model)
			throws SocketException {
    	try {
			if(uid != null) {
				CallLog calllog = callLogService.getByUuid(UUID.UUIDFromString(uid));
				if(calllog.getRecord_path() != null){
					//获得录音文件路径
					//	model.addAttribute("urls", calllog.getPlayUrl(SpringPropertiesUtil.getProperty("ngnix")));
					model.addAttribute("urls", calllog.getRecord_path());
				}else{
					model.addAttribute("urls", "");
				}
				SysConfig sys = sysService.getSysConfigByKey("sys.recording.play.config");
				model.addAttribute("playconfig", sys.getSysVal());
				return "call/calllog_player";
			}else{
				model.addAttribute("urls", "");
				return "call/calllog_player";
			}
		} catch (Exception e) {
			model.addAttribute("urls", "");
			return "call/calllog_player";
		}
	}

}


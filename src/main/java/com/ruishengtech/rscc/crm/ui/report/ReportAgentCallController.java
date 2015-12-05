package com.ruishengtech.rscc.crm.ui.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.report.condition.ReportAgentCallCondition;
import com.ruishengtech.rscc.crm.report.model.ReportAgentCall;
import com.ruishengtech.rscc.crm.report.service.imp.ReportAgentCallServiceImp;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Controller
@RequestMapping("report/agentcall")
public class ReportAgentCallController {
	
	@Autowired
	private ReportAgentCallServiceImp reportAgentCallService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping
	public String index(Model model){
		//日报默认值
		model.addAttribute("startTime", DateUtils.getDayStringByDate(new Date())+"  00:00:00");
		model.addAttribute("endTime", DateUtils.getNextDayString()+"  00:00:00");
		
		//周报默认值
		model.addAttribute("startWeekTime", DateUtils.getFirstDayOfWeek()+"  00:00:00");
		model.addAttribute("endWeekTime", DateUtils.getFirstDayOfNextWeek()+"  00:00:00");
		
		//月报默认值
		model.addAttribute("startMonthTime", DateUtils.getFirstDayOfMonth().substring(0,7));
		
		model.addAttribute("iframecontent","report/reportagentcall");
		return "iFrame";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,HttpServletResponse response,Model model,ReportAgentCallCondition agentCallCondition){
		agentCallCondition.setRequest(request);
		
		PageResult<ReportAgentCall> pageResult = reportAgentCallService.querypage(agentCallCondition);
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<ReportAgentCall> list = pageResult.getRet();
		for(ReportAgentCall d : list){
			jsonArray.put(reportAgentCallService.setJsonValue(d));
		}
				
		jsonObject.put("data", jsonArray);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());	
		
		return jsonObject.toString();
	}
}

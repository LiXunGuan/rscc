package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.text.ParseException;
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
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.condition.ReportCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.ReportAgentkpi;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.mw.service.ReportAgentKPIService;
import com.ruishengtech.rscc.crm.ui.mw.service.SYSConfigService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("report/reportagentkpi")
public class ReportAgentKPIController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ReportAgentKPIService reportAgentKPIService;
	 
	@Autowired
	private SYSConfigService sysConfigService;

    @RequestMapping
    public String Batch(HttpServletRequest request, HttpServletResponse response,Model model,String str) {
    	model.addAttribute("selectionDates", Domains.SELECTION2_DATES);
    	model.addAttribute("selectionDate", Domains.SELECTION_DAY);
		Date d=new Date();
		model.addAttribute("startTime", d);
		model.addAttribute("endTime", d);
		
		SysConfig config=sysConfigService.getSysConfig("agentbusy");
        if (config!=null) {
        	JSONObject jsob = new JSONObject(config.getVal());
        	model.addAttribute("state_1", jsob.optString("state_1","0"));
        	model.addAttribute("state_2", jsob.optString("state_2","0"));
        	model.addAttribute("state_3", jsob.optString("state_3","0"));
        	model.addAttribute("state_4", jsob.optString("state_4","0"));
        	model.addAttribute("state_5", jsob.optString("state_5","0"));
        	model.addAttribute("state_6", jsob.optString("state_6","0"));
        	model.addAttribute("state_7", jsob.optString("state_7","0"));
        	model.addAttribute("state_8", jsob.optString("state_8","0"));
        	model.addAttribute("state_9", jsob.optString("state_9","0"));
        	model.addAttribute("state_10", jsob.optString("state_10","0"));
        	model.addAttribute("name_1", jsob.optString("name_1","未命名"));
        	model.addAttribute("name_2", jsob.optString("name_2","未命名"));
        	model.addAttribute("name_3", jsob.optString("name_3","未命名"));
        	model.addAttribute("name_4", jsob.optString("name_4","未命名"));
        	model.addAttribute("name_5", jsob.optString("name_5","未命名"));
        	model.addAttribute("name_6", jsob.optString("name_6","未命名"));
        	model.addAttribute("name_7", jsob.optString("name_7","未命名"));
        	model.addAttribute("name_8", jsob.optString("name_8","未命名"));
        	model.addAttribute("name_9", jsob.optString("name_9","未命名"));
        	model.addAttribute("name_10", jsob.optString("name_10","未命名"));
        	
		}
        
        model.addAttribute("iframecontent","report/reportagentkpi");
		return "iframe";
		
//		return "report/reportagentkpi";
    }
    
    /**
     * 数据请求
     * @param request
     * @param response
     * @param model
     * @return
     * @throws ParseException 
     */
    @RequestMapping("data")
    @ResponseBody
    public String data(HttpServletRequest request,
                       HttpServletResponse response, ReportCondition reportCondition) throws  ParseException {
    	reportCondition.setRequest(request);
		String startTime=request.getParameter("startTime");	//获取开始时间
		String endTime=request.getParameter("endTime");	//获取结束时间
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String now=sdf.format(new Date());	//获取当前系统时间
		
		if (startTime==null||startTime=="") {	//如果未获取到开始时间或结束时间  那么赋值当前时间
			startTime=now;
		}
		if (endTime==null||endTime=="") {
			endTime=now;
		}
		
		//根据报表类型拼出完整时间
		if (reportCondition.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
			 reportCondition.setStime(startTime.substring(0,4)+"-01-01 00:00:00");
			 reportCondition.setEtime(endTime.substring(0,4)+"-12-30 23:59:59");
 		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
 			 reportCondition.setStime(startTime.substring(0,7)+"-01 00:00:00");
 			 reportCondition.setEtime(endTime.substring(0,7)+"-30 23:59:59");
 		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
 			
 		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
 			 reportCondition.setStime(startTime+" 00:00:00");
 			 reportCondition.setEtime(endTime+" 23:59:59");
 		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
 			 reportCondition.setStime(startTime+" 00:00:00");
 			 reportCondition.setEtime(endTime+" 23:59:59");
 		}
		
		// 获取当前用户所管辖部门的所有坐席信息
		reportCondition.setAgentsList(userService.getManagerUsernames(SessionUtil.getCurrentUser(request).getUid()));
		// 是否是admin
		reportCondition.setNameUid(SessionUtil.getCurrentUser(request).getUid());
	
		JSONArray jsonArray = new JSONArray();	//将数据放入json
	    JSONObject jsonObject = new JSONObject();
		PageResult<ReportAgentkpi> pageResult=null;
		List<ReportAgentkpi> list=null;
    	try {
    		pageResult= reportAgentKPIService.page(reportCondition);	//获取符合条件的数据
            list = (List<ReportAgentkpi>) pageResult.getRet();
		} catch (Exception e) {
			 jsonObject.put("aaData", jsonArray);
		     jsonObject.put("iTotalRecords", "0");
		     jsonObject.put("iTotalDisplayRecords","0");
		     jsonObject.put("selectionDate", Domains.SELECTION_DATES.get(reportCondition.getSelectionReport()));
		     return jsonObject.toString();
		}
        

       
        
        if (list.size()>0) {
        	for (int i = 0; i < list.size(); i++) {
           	 JSONObject jsob = new JSONObject(list.get(i));
           	 
           	jsob.put("busy_1", "");
           	jsob.put("busy_2", "");
           	jsob.put("busy_3", "");
           	jsob.put("busy_4", "");
           	jsob.put("busy_5", "");
           	jsob.put("busy_6", "");
           	jsob.put("busy_7", "");
           	jsob.put("busy_8", "");
           	jsob.put("busy_9", "");
           	jsob.put("busy_10", "");
           	
           	 
           	 //根据报表类型拼出相应格式时间
           	 if (reportCondition.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
           		 jsob.put("timestamp",StringUtils.trimToEmpty(String.valueOf(list.get(i).getYear())));
        		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
        			jsob.put("timestamp",StringUtils.trimToEmpty(String.valueOf(list.get(i).getYear()+"-"+StringSty(String.valueOf(list.get(i).getMonth())))));
        		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
        		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
        			jsob.put("timestamp",StringUtils.trimToEmpty(String.valueOf(list.get(i).getYear()+"-"+StringSty(String.valueOf(list.get(i).getMonth()))+"-"+StringSty(String.valueOf(list.get(i).getDay())))));
        		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
        			jsob.put("timestamp",StringUtils.trimToEmpty(String.valueOf(list.get(i).getYear()+"-"+StringSty(String.valueOf(list.get(i).getMonth()))+"-"+StringSty(String.valueOf(list.get(i).getDay())))));
        		}


           	 	jsob.put("online_time", DateUtils.getSToTime(list.get(i).getOnline_time()));
           	 	//计算话后/评分平均值
//           	 	jsob.put("words_avg",StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getAcw_duration())/Double.valueOf(list.get(i).getAcw_count())))));
           	 	jsob.put("score_avg",StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getScore_sum())/Double.valueOf(list.get(i).getScore_count())))));
           	 
               jsonArray.put(jsob);
           }
		}
        jsonObject.put("aaData", jsonArray);
        jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
        jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
        jsonObject.put("selectionDate", Domains.SELECTION_DATES.get(reportCondition.getSelectionReport()));
        
        return jsonObject.toString();

    }
    
    /**
     * 加载标题
     */
    @RequestMapping("menushow")
    @ResponseBody
    public String menushow(HttpServletRequest request, HttpServletResponse response, MWFsHost mwFsHost)
            throws IOException {
        JSONObject jsob = new JSONObject();
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>报表&nbsp;&nbsp;>&nbsp;<span>坐席-KPI报表</span>");
        return jsob.toString();
    }
    
    
    public String StringSty(String str){
    	if (Integer.valueOf(str)<10) {
			str="0"+str;
		}
    	return str;
    }
    
    /*坐席KPI导出*/
    @RequestMapping("export")
    public void export(HttpServletRequest request,HttpServletResponse response,ReportCondition reportCondition,String selectionReport,
    		String startTime,String endTime,String name) throws Exception{
    	
    	reportCondition.setRequest(request);
    	reportCondition.setSelectionReport(selectionReport);
    	reportCondition.setStime(startTime);
    	reportCondition.setEtime(endTime);
    	reportCondition.setName(name);
    	reportAgentKPIService.getExcelCreated(request, response, reportCondition);
    }
    
    
  
}

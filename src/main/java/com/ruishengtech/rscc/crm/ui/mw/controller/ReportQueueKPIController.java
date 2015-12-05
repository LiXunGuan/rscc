package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import com.ruishengtech.rscc.crm.ui.mw.model.ReportQueue;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.ui.mw.service.ReportQueueKPIService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("report/reportqueuekpi")
public class ReportQueueKPIController {

	@Autowired
	private ReportQueueKPIService reportQueueKPIService;
	
	@Autowired
	private UserService userService;

    @RequestMapping
    public String Batch(HttpServletRequest request, HttpServletResponse response,Model model,String str) {
    	model.addAttribute("selectionDates", Domains.SELECTION_DATES);
//    	model.addAttribute("selectionDate", Domains.SELECTION_PERIOD);
    	model.addAttribute("selectionDate", Domains.SELECTION_DAY);
		Date d=new Date();
		model.addAttribute("startTime", d);
		model.addAttribute("endTime", d);
		
		model.addAttribute("iframecontent","report/reportqueuekpi");
		return "iframe";
		
//        return "report/reportqueuekpi";
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
	
		
		User user = (User) SessionUtil.getCurrentUser(request);
    	JSONArray ja = QueueManager.getAllQueue();
    	Collection<String> queues = new ArrayList<String>();
    	for (int i = 0 ; i < ja.length() ; i++ ) {
    		if(ja.getJSONObject(i).opt("queue_id") == null){
    			continue;
    		}
    		if(userService.hasDatarange(user.getUid(), "queue", ja.getJSONObject(i).getInt("queue_id")+"")){
    			queues.add(ja.getJSONObject(i).getString("queue_name"));
    		}
		}
		
		// 获取当前用户所管辖的所有队列信息
		reportCondition.setQueuesList(queues);
		// 是否是admin
		reportCondition.setNameUid(SessionUtil.getCurrentUser(request).getUid());
		
    	
        PageResult<ReportQueue> pageResult = null;	//获取符合条件的数据
        List<ReportQueue> list = null;

        JSONArray jsonArray = new JSONArray();	//将数据放入json
        JSONObject jsonObject = new JSONObject();
        try {
        	 pageResult = reportQueueKPIService.page(reportCondition);	//获取符合条件的数据
             list = (List<ReportQueue>) pageResult.getRet();
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
           	 //根据报表类型拼出相应格式时间
           	 jsob.put("hour", "");
           	 if (reportCondition.getSelectionReport().equals(Domains.SELECTION_YEAR)) {	//年报
           		 jsob.put("timestamp",StringUtils.trimToEmpty(String.valueOf(list.get(i).getYear())));
        		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_MONTH)) {	//月报
        			jsob.put("timestamp",StringUtils.trimToEmpty(String.valueOf(list.get(i).getYear()+"-"+StringSty(String.valueOf(list.get(i).getMonth())))));
        		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_WEEK)) {	//周报
        		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_DAY)) {	//日报
        			jsob.put("timestamp",StringUtils.trimToEmpty(String.valueOf(list.get(i).getYear()+"-"+StringSty(String.valueOf(list.get(i).getMonth()))+"-"+StringSty(String.valueOf(list.get(i).getDay())))));
        		}else if (reportCondition.getSelectionReport().equals(Domains.SELECTION_PERIOD)) {	//时报
        			jsob.put("timestamp",StringUtils.trimToEmpty(String.valueOf(list.get(i).getYear()+"-"+StringSty(String.valueOf(list.get(i).getMonth()))+"-"+StringSty(String.valueOf(list.get(i).getDay())))));
        			if (list.get(i).getHour()!=null) {
        				jsob.put("hour",Domains.PARAGRAPHS.get(String.valueOf(list.get(i).getHour())));
        			}
        		}
           	 	
           	 	//计算呼入接通/未接通平均等待时长
           	 	jsob.put("in_t_wait_avg",DateUtils.getSToTime(Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getIn_t_wait())/Double.valueOf(list.get(i).getIn_t_callcount())))))));
           	 	jsob.put("in_f_wait_avg",DateUtils.getSToTime(Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getIn_f_wait())/Double.valueOf(list.get(i).getIn_f_callcount())))))));
           	 
           	 	//计算呼入平均通话时长
            	jsob.put("in_t_call_avg",DateUtils.getSToTime(Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getIn_t_duration())/Double.valueOf(list.get(i).getIn_t_callcount())))))));
               jsonArray.put(jsob);
           }
		}
        

        jsonObject.put("aaData", jsonArray);
        jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
        jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
        jsonObject.put("selectionDate", Domains.SELECTION_DATES.get(reportCondition.getSelectionReport()));
        
        JSONObject jsons =new JSONObject();
        jsons.put("selectionDate",reportCondition.getSelectionReport());
        jsonObject.put("jsons", jsons);
        
        
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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>报表&nbsp;&nbsp;>&nbsp;<span>技能组-KPI报表</span>");
        return jsob.toString();
    }
    
    
    
    public String StringSty(String str){
    	if (Integer.valueOf(str)<10) {
			str="0"+str;
		}
    	return str;
    }
    
    /*技能组KPI导出*/
    @RequestMapping("export")
    public void export(HttpServletRequest request,HttpServletResponse response,ReportCondition reportCondition,String selectionReport,
    		String startTime,String endTime,String name) throws Exception{
    	
    	reportCondition.setRequest(request);
    	reportCondition.setSelectionReport(selectionReport);
    	reportCondition.setStime(startTime);
    	reportCondition.setEtime(endTime);
    	reportCondition.setName(name);
    	reportQueueKPIService.getExcelCreated(request, response, reportCondition);
    }
 
    
    
  
}

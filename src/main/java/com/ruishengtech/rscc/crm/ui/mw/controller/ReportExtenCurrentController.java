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
import com.ruishengtech.rscc.crm.ui.mw.condition.ReportCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.ReportExten;
import com.ruishengtech.rscc.crm.ui.mw.service.ReportExtenTrafficService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("report/reportextentraffic")
public class ReportExtenCurrentController {

	@Autowired
	private ReportExtenTrafficService reportExtenTrafficService;

    @RequestMapping
    public String Batch(HttpServletRequest request, HttpServletResponse response,Model model,String str) {
    	model.addAttribute("selectionDates", Domains.SELECTION_DATES);
//    	model.addAttribute("selectionDate", Domains.SELECTION_PERIOD);
    	model.addAttribute("selectionDate", Domains.SELECTION_DAY);
		Date d=new Date();
		model.addAttribute("startTime", d);
		model.addAttribute("endTime", d);
		
		model.addAttribute("iframecontent","report/reportextentraffic");
		return "iframe";
		
//        return "report/reportextentraffic";
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
	

    	
        PageResult<ReportExten> pageResult = null;	//获取符合条件的数据
        List<ReportExten> list = null;

        JSONArray jsonArray = new JSONArray();	//将数据放入json
        JSONObject jsonObject = new JSONObject();
        try {
        	 pageResult = reportExtenTrafficService.page(reportCondition);	//获取符合条件的数据
             list = (List<ReportExten>) pageResult.getRet();
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
               		jsob.put("hour",Domains.PARAGRAPHS.get(String.valueOf(list.get(i).getHour())));
    		}

                list.get(i).setOut_t_duration(list.get(i).getOut_t_duration()%1000!=0?list.get(i).getOut_t_duration()/1000+1:list.get(i).getOut_t_duration()/1000);
                list.get(i).setIn_t_duration(list.get(i).getIn_t_duration() % 1000 != 0 ? list.get(i).getIn_t_duration() / 1000 + 1 : list.get(i).getIn_t_duration() / 1000);
                list.get(i).setIn_t_ringduration(list.get(i).getIn_t_ringduration() % 1000 != 0 ? list.get(i).getIn_t_ringduration() / 1000 + 1 : list.get(i).getIn_t_ringduration() / 1000);
                list.get(i).setIn_f_ringduration(list.get(i).getIn_f_ringduration()%1000!=0?list.get(i).getIn_f_ringduration()/1000+1:list.get(i).getIn_f_ringduration()/1000);


                jsob.put("out_t_duration", DateUtils.getSToTime(list.get(i).getOut_t_duration()));
                jsob.put("in_t_duration",  DateUtils.getSToTime(list.get(i).getIn_t_duration()));


                //计算呼入接通/未接通振铃平均时长
           	 	jsob.put("intringduration",DateUtils.getSToTime(Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getIn_t_ringduration())/Double.valueOf(list.get(i).getIn_t_callcount())))))));
           	 	jsob.put("infringduration",DateUtils.getSToTime(Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getIn_f_ringduration())/Double.valueOf(list.get(i).getIn_f_callcount())))))));
           	 
           	 	//计算呼入/呼出出平均时长
           	 	jsob.put("outavg",DateUtils.getSToTime(Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getOut_t_duration())/Double.valueOf(list.get(i).getOut_t_callcount())))))));
            	jsob.put("inavg",DateUtils.getSToTime(Integer.parseInt(StringUtils.trimToEmpty(String.valueOf(Math.round(Double.valueOf(list.get(i).getIn_t_duration())/Double.valueOf(list.get(i).getIn_t_callcount())))))));
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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>报表&nbsp;&nbsp;>&nbsp;<span>分机-话务报表</span>");
        return jsob.toString();
    }
    
    
    
    public String StringSty(String str){
    	if (Integer.valueOf(str)<10) {
			str="0"+str;
		}
    	return str;
    }
    
    /*分机--话务报表导出*/
    @RequestMapping("export")
    public void export(HttpServletRequest request,HttpServletResponse response,ReportCondition reportCondition,String selectionReport,
    		String startTime,String endTime,String name) throws Exception{
    	
    	reportCondition.setRequest(request);
    	reportCondition.setSelectionReport(selectionReport);
    	reportCondition.setStime(startTime);
    	reportCondition.setEtime(endTime);
    	reportCondition.setName(name);
    	reportExtenTrafficService.getExcelCreated(request, response, reportCondition);
    }

  
}

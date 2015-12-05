package com.ruishengtech.rscc.crm.ui.report;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.billing.condition.BillingCondition;
import com.ruishengtech.rscc.crm.report.condition.ReportBillCondition;
import com.ruishengtech.rscc.crm.report.model.ReportBill;
import com.ruishengtech.rscc.crm.report.service.ReportBillService;
import com.ruishengtech.rscc.crm.report.solution.ReportBillSolution;

@Controller
@RequestMapping("report/bill")
public class ReportBillController {
	
	@Autowired
	private ReportBillService rbService;
	
	SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdftime  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping
	public String index(BillingCondition billCondition,Model model){
		model.addAttribute("todaytime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		model.addAttribute("yesterdaystart", sdf.format(cal.getTime())+"  00:00:00");
		model.addAttribute("yesterdayend", sdf.format(new Date())+"  00:00:00");
		
//		Calendar calnext = Calendar.getInstance();
//		calnext.add(Calendar.DAY_OF_MONTH, 1);
//		model.addAttribute("nextdatend",sdf.format(calnext.getTime())+"  00:00:00");
		model.addAttribute("firstdayofmonth", DateUtils.getFirstDayOfMonth()+"  00:00:00");
		model.addAttribute("lastdayofmonth", sdf.format(new Date())+"  00:00:00");
		
		model.addAttribute("iframecontent","report/reportbill");
		return "iframe";
		
//		return "report/reportbill";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getdata(HttpServletRequest request,HttpServletResponse response,ReportBillCondition rbCondition,Model model){
		
		rbCondition.setRequest(request);
//		rbService.insertRBillByBill(); 插入昨天数据
//		rbService.getTodayData();	         得到今天数据
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if(rbCondition.getBilling_date().equals("todaydata")){
			List<ReportBill> rblist = rbService.getTodayData(rbCondition);
			String sumCost = "";
			JSONArray sips = new JSONArray();
			JSONArray queues = new JSONArray();
			for(ReportBill rb : rblist){
				Double charge = Double.parseDouble(rb.getCall_charge());
				BigDecimal b = new BigDecimal(charge);
				//进行保留两位小数的四舍五入计算，如果是0.22222=0.22，0.2000006=0.2
				rb.setCall_charge(String.valueOf(b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue()));
				sumCost = rb.getSumcost();
				JSONObject jsonObject2 = new JSONObject(rb);
				jsonArray.put(jsonObject2);
			}
//			Map<String, Object> map = rbService.queryResultForToday(rbCondition);
//			jsonObject.put("statistics", new JSONObject(map));
			JSONObject temp = new JSONObject();
			jsonObject.put("statistics", temp.put("sumcost", sumCost));
			jsonObject.put("data", jsonArray);
			jsonObject.put("iTotalRecords", rblist.size());
			jsonObject.put("iTotalDisplayRecords", rblist.size());
			
		} else {
			PageResult<ReportBill> pageResult = rbService.queryPage(rbCondition);
			List<ReportBill> list = pageResult.getRet();
			JSONArray sips = new JSONArray();
			JSONArray queues = new JSONArray();
			for(ReportBill rb : list){
				Double charge = Double.parseDouble(rb.getCall_charge());
				BigDecimal b = new BigDecimal(charge);
				rb.setCall_charge(String.valueOf(b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue()));
				
				JSONObject jsonObject2 = new JSONObject(rb);
				jsonArray.put(jsonObject2);
			}
			Map<String, Object> map = rbService.queryResultForHistory(rbCondition);
			jsonObject.put("statistics", new JSONObject(map));
			jsonObject.put("data", jsonArray);
			jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
			jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		}
		return jsonObject.toString();
	}
	
	@RequestMapping("export")
	@ResponseBody
	public void exportData(HttpServletRequest request,HttpServletResponse response,ReportBillCondition rbCondition,ReportBillSolution solution) throws Exception{
		
		rbCondition.setRequest(request);
		
		rbService.getExcelCreated(request, response, rbCondition);
	}
}

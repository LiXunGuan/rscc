package com.ruishengtech.rscc.crm.ui.billing;

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
import com.ruishengtech.rscc.crm.billing.model.Billing;
import com.ruishengtech.rscc.crm.billing.service.imp.BillServiceImp;

@Controller
@RequestMapping("billing/data")
public class BillingController {
	
	@Autowired
	private BillServiceImp billService;
	
	@RequestMapping
	public String index(Model model){
		model.addAttribute("model", "billing/data");
		
		model.addAttribute("iframecontent","billing/bill_index");
		return "iframe";
		
//		return "billing/bill_index";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, BillingCondition billCondition,
			Model model) {
		billCondition.setRequest(request);

		PageResult<Billing> pageResult = billService.queryPage(billCondition);
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<Billing> list = pageResult.getRet();
		for (Billing i:list) {
			JSONObject jsonObject = new JSONObject(i);
			jsonObject.put("startStamp", DateUtils.getDateString(i.getStartStamp()));
			jsonObject.put("endStamp", DateUtils.getDateString(i.getEndStamp()));
			jsonArray.put(jsonObject);
		}
		Map<String, Object> map = billService.queryResult(billCondition);
		jsonObject2.put("statistics", new JSONObject(map));

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
}

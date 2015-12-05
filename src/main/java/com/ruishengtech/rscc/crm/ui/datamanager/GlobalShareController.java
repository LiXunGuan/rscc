package com.ruishengtech.rscc.crm.ui.datamanager;

import java.text.SimpleDateFormat;
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
import com.ruishengtech.rscc.crm.datamanager.condition.DataGlobalShareCondition;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShare;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.DataGlobalShareService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;

@Controller
@RequestMapping("globalshare")
public class GlobalShareController {
	

	@Autowired
	private DataManagers dataManagers;
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@Autowired
	private DataGlobalShareService gsService;
	
	@Autowired
	private DataBatchService dbService;

	@RequestMapping
	public String index(HttpServletRequest request,HttpServletResponse response,Model model){
		
		model.addAttribute("iframecontent","datamanager/globalshare_index");
		return "iframe";
		
		
//		return "datamanager/globalshare_index";
	}

	/**
	 * 共享池获取数据
	 * @param request
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getGlobalShareData(HttpServletRequest request,HttpServletResponse response,DataGlobalShareCondition dgc,Model model) {
		dgc.setRequest(request);
		if(StringUtils.isNotBlank(dgc.getBatchName())){
			dgc.setBatchName(dbService.getDataBatchByBatchName(dgc.getBatchName())==null?"0":dbService.getDataBatchByBatchName(dgc.getBatchName()).getUid());
		}
		PageResult<DataGlobalShare> pageResult = gsService.queryPagedata(dgc);
		
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<DataGlobalShare> list = pageResult.getRet();
		for (DataGlobalShare dgs : list) {
			
			JSONObject json = new JSONObject(dgs);
			json.put("batchName", StringUtils.trimToEmpty(dgs.getBatchName()));
			json.put("phoneNumber", StringUtils.trimToEmpty(dgs.getPhoneNumber().toString()));
			json.put("json", StringUtils.trimToEmpty(dgs.getJson()));
			json.put("callCount", StringUtils.trimToEmpty(dgs.getCallCount().toString()));
			json.put("lastCallResult", StringUtils.trimToEmpty(dgs.getLastCallResult()));
			json.put("lastCallTime", dgs.getLastCallTime() == null ? "" : StringUtils.trimToEmpty(fmt.format(dgs.getLastCallTime())));
			json.put("intentType", StringUtils.trimToEmpty(StringUtils.isBlank(dgs.getIntentType())?"无":dataIntentService.getByUuid(dgs.getIntentType()).getIntentName()));
			json.put("intentTimestamp", dgs.getIntentTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(dgs.getIntentTimestamp())));
			jsonArray.put(json);
		}
		
		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
}

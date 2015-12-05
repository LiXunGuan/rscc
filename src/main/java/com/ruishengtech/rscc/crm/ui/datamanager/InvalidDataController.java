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
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.datamanager.condition.InvalidDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.PhoneResource;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.InvalidDataServiceImp;

@Controller
@RequestMapping("invalid/data")
public class InvalidDataController {
	
	@Autowired
	private InvalidDataServiceImp invalidService;
	
	@Autowired
	private DataBatchService dataBatchService;
	
	@RequestMapping
	public String index(Model model){
		
		model.addAttribute("iframecontent","datamanager/invalidData");
		return "iframe";
		
//		return "datamanager/invalidData";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,HttpServletResponse response,Model model,InvalidDataCondition invalidCondition){
		invalidCondition.setRequest(request);
		
		if(StringUtils.isNotBlank(invalidCondition.getBatchUuid())){
			invalidCondition.setBatchUuid(dataBatchService.getDataBatchByBatchName(invalidCondition.getBatchUuid()).getUid());
		}
		PageResult<PhoneResource> pageResult = invalidService.queryPage(invalidCondition);
		
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PhoneResource> list = pageResult.getRet();
		for (PhoneResource invalid : list) {
			JSONObject json = new JSONObject(invalid);
			json.put("batchName", dataBatchService.getByUuid(UUID.UUIDFromString(invalid.getBatchUuid())).getBatchName());
			json.put("phoneNumberState", invalid.getIsAbandon().equals("1")?"已废弃":invalid.getIsBlacklist().equals("1")?"黑名单":invalid.getIsFrozen().equals("1")?"已冻结":"有效数据");
			json.put("maketime", invalid.getIsAbandon().equals("1")?sdf.format(invalid.getAbandonTimestamp()):invalid.getIsBlacklist().equals("1")?sdf.format(invalid.getBlacklistimestamp()):invalid.getIsFrozen().equals("1")?sdf.format(invalid.getFrozenTimestamp()):"");
			jsonArray.put(json);
		}
		
		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
		
	}
	@RequestMapping("recover")
	@ResponseBody
	public String recover(HttpServletRequest request,HttpServletResponse response,Model model,String phonenumber,String batchUuid){
		System.out.println("无效号码恢复--------号码："+phonenumber+"/批次："+batchUuid);
		
		JSONObject json = new JSONObject();
		invalidService.recover(phonenumber, batchUuid);
		return json.put("success", true).toString();
	}
}

package com.ruishengtech.rscc.crm.ui.datamanager;

import java.util.List;

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
import com.ruishengtech.rscc.crm.datamanager.condition.DataBatchDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchDataService;

/**
 * @author Frank
 *
 */
@Controller
@RequestMapping("newdatabatch")
public class DataBatchDataController {

	@Autowired
	private DataBatchDataService batchDataService;
	
	/**
	 * 详情
	 * @return
	 */
	@RequestMapping
	public String toGetDetailsfos(String arg0,String arg1,Model model){
		
		model.addAttribute("batchName", arg1);
		model.addAttribute("batchUuid", arg0);
		model.addAttribute("detail", "1");
		
		model.addAttribute("iframecontent","datamanager/new_batch_detail");
		return "iframe";
		
//		return "datamanager/new_batch_detail";
	}
	
	/**
	 * 获取详情信息
	 * @param ownUser
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String toGetDataInfos(HttpServletRequest request , HttpServletResponse response,DataBatchDataCondition condition){
		
		condition.setRequest(request);
		PageResult<DataBatchData> pageResult =  batchDataService.queryPage(condition);
		List<DataBatchData> cstmservices = pageResult.getRet();
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < cstmservices.size(); i++) {
			JSONObject jsonObject2 = new JSONObject(cstmservices.get(i));
			array.put(jsonObject2);
		}
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	

	
	
}

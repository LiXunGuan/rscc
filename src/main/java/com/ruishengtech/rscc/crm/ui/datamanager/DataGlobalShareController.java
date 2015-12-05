package com.ruishengtech.rscc.crm.ui.datamanager;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.datamanager.condition.DataGlobalShareRecordCondition;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShare;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShareRecord;
import com.ruishengtech.rscc.crm.datamanager.service.DataGlobalShareRecordService;
import com.ruishengtech.rscc.crm.datamanager.service.DataGlobalShareService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.user.model.User;

@Controller
@RequestMapping("globalsharedata/globalshare")
public class DataGlobalShareController {
	
	@Autowired
	private DataManagers dataManagers;
	
	@Autowired
	private DataGlobalShareService dataGlobalShareService;
	
	@Autowired
	private DataGlobalShareRecordService dataGlobalShareRecordService;
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@RequestMapping()
	public String index(HttpServletRequest request, Model model) {
		
		User user = ((User) SessionUtil.getCurrentUser(request));
		
		int numlimit = Integer.parseInt(SysConfigManager.getInstance().getDataMap().get("sys.globalshare.getDataNum").getSysVal());
		
		// 当前用户今日获取的数据条数
		Integer dataNumber = dataGlobalShareService.getGlobalShareRecordByUserUuid(user.getUid());
		
		if(numlimit <= dataNumber){
			model.addAttribute("getdatas", true);
		}
		
		model.addAttribute("iframecontent","datamanager/dataglobalshare_index");
		return "iframe";
		
//		return "datamanager/dataglobalshare_index";
	}
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 共享池查询数据
	 * @param request
	 * @param dg
	 * @param model
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request, DataGlobalShareRecordCondition dg, Model model) {
		
		int numlimit = Integer.parseInt(SysConfigManager.getInstance().getDataMap().get("sys.globalshare.getDataNum").getSysVal());
		
		// 获取当前登陆用户
		User user = ((User) SessionUtil.getCurrentUser(request));
		dg.setRequest(request);
		dg.setUseruuid(user.getUid());
		dg.setDeptuuid(user.getDepartment());
//		dg.setLength(DataGlobalShareServiceImp.numlimit);
		dg.setLength(numlimit);
//		dg.setStart(0);
		
		return queryData(dg);
	}
	
	/**
	 * 共享池获取数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getGlobalShareData")
	@ResponseBody
	public String getGlobalShareData(HttpServletRequest request) {
		
		// 获取当前登陆用户
		User user = ((User) SessionUtil.getCurrentUser(request));
		//获取配置中设置的数据条数
		int numlimit = Integer.parseInt(SysConfigManager.getInstance().getDataMap().get("sys.globalshare.getDataNum").getSysVal());
		
		int result = dataGlobalShareService.getGlobalShare(user.getDepartment(), user.getUid(), numlimit);
		
		if(result > 0){
			return new JSONObject().put("success", true).put("datacount", result).put("islimit", numlimit - result).toString();
		}
		return new JSONObject().put("success", false).toString();
	}
	
	private String queryData(DataGlobalShareRecordCondition dgc) {
		
		PageResult<DataGlobalShareRecord> pageResult = dataGlobalShareRecordService.queryPage(dgc);
		
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataGlobalShareRecord> list = pageResult.getRet();
		for (DataGlobalShareRecord dgs : list) {
			
			JSONObject json = new JSONObject(dgs);
			json.put("batchName", StringUtils.trimToEmpty(dgs.getBatchName()));
			json.put("deptName", StringUtils.trimToEmpty(dgs.getDeptName()));
			json.put("ownDepartmentTimestamp", dgs.getOwnDepartmentTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(dgs.getOwnDepartmentTimestamp())));
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
	

	@RequestMapping("globalshareDataCount")
    @ResponseBody
    public String globalshareDataCount(HttpServletRequest request) {
    	 List<DataGlobalShare> list = dataGlobalShareService.getAllDataGlobalShare();
    	 JSONArray jsonArray = new JSONArray();
    	 if(list != null) {
			for (int i = 0; i < list.size(); i++) {
				jsonArray.put(list.get(i).getUid());
			}
		}
		return jsonArray.toString();
    }
	
	/**
	 * 批量获取数据
	 * @param dataUuid
	 * @return
	 */
	@RequestMapping("batchGetData")
	@ResponseBody
    public String batchReturnData(String dataUuid){
		String[] dataUuids = dataUuid.substring(0, dataUuid.length()).split(","); 
		if (dataUuids.length > 0) {
			for (String uid : dataUuids) {
				System.out.println(uid);
			}
		}
		return new JSONObject().put("success", true).toString();
    }
	
}

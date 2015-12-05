package com.ruishengtech.rscc.crm.ui.datamanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.util.MathUtil;
import com.ruishengtech.rscc.crm.datamanager.condition.DepartmentDataCondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DeptAllotDataCondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DeptDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentData;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.DeptDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataBatchDepartmentLinkServiceImp;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DepartmentTableServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.linkservice.DataCustomerLinkService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;
import com.ruishengtech.rscc.crm.user.service.imp.DatarangeServiceImp;


@Controller
@RequestMapping("deptdata/dept")
public class DeptDataController {
	
	@Autowired
	private DeptDataService deptDataService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private DataBatchService dbService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartmentTableServiceImp departmentTableService;
	
	@Autowired
	private DataCustomerLinkService dataCustomerLinkService;
	
	@Autowired
	private DataBatchDepartmentLinkServiceImp dataBatchDepartmentLinkService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("depts", datarangeService.getAllDatarange());
		
		model.addAttribute("iframecontent","datamanager/deptData_index");
		return "iframe";
		
//		return "datamanager/deptData_index";
	}
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request, DeptDataCondition deptDataCondition, Model model) {
		
		deptDataCondition.setRequest(request);
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
		deptDataCondition.setIns(departments);
		PageResult<DataBatchDepartmentLink> pageResult = deptDataService.queryPage(deptDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataBatchDepartmentLink> list = pageResult.getRet();
		for (DataBatchDepartmentLink dept : list) {
			JSONObject json = new JSONObject(dept);
			json.put("batchname", StringUtils.trimToEmpty(dept.getBatchname()));
			json.put("deptname", StringUtils.trimToEmpty(dept.getDeptname()));
			// 单次上限
			json.put("singleLimit", StringUtils.trimToEmpty(dept.getSingleLimit().toString()));
			// 单日上限
			json.put("dayLimit", StringUtils.trimToEmpty(dept.getDayLimit().toString()));
			// 总的数据上限
			json.put("totalLimit", StringUtils.trimToEmpty(dept.getTotalLimit().toString()));
			// 批次数据
			json.put("batchDataCount", StringUtils.trimToEmpty(dept.getBatchDataCount().toString()));
			// 部门数据量
			json.put("dataCount", (dept.getDataCount() - dept.getOwnCount()));
			// 二次领用的数据量
			json.put("ownCount", StringUtils.trimToEmpty(dept.getOwnCount().toString()));
			// 批次内转共享量
			json.put("shareCount", StringUtils.trimToEmpty(dept.getShareCount().toString()));
			// 意向客户量
			json.put("intentCount", StringUtils.trimToEmpty(dept.getIntentCount().toString()));
			// 成交客户量
			json.put("customerCount", StringUtils.trimToEmpty(dept.getCustomerCount().toString()));
			// 废号量
			json.put("abandonCount", StringUtils.trimToEmpty(dept.getAbandonCount().toString()));
			// 黑名单量
			json.put("blacklistCount", StringUtils.trimToEmpty(dept.getBlacklistCount().toString()));
			json.put("openflag", StringUtils.trimToEmpty(dept.getOpenFlag().toString()));
			
			DatarangeServiceImp.resultSet = new HashSet<>();
			Set<String> resultSet = datarangeService.getParentUuid(dept.getDepartmentUuid());
			if(resultSet != null){
				for (String str : resultSet) {
					if("0".equals(datarangeService.getOpenFlag(dept.getDataBatchUuid(), str))){
						json.put("openflagenable", true);
					}
				}
			}
			
			jsonArray.put(json);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	/**
	 * 分配数据
	 * @param dataBatchUuid 批次
	 * @param deptUuid		部门
	 * @param model
	 * @return
	 */
	@RequestMapping("allotData")
    public String allotData(String dataBatchUuid, String deptUuid, Integer dataCount, Model model){
		DataBatch db = dbService.getByUuid(UUID.UUIDFromString(dataBatchUuid));
		model.addAttribute("entry", db);
		model.addAttribute("dataBatchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		JSONArray treeArray = datarangeService.getChildrenDatarangeTree(deptUuid);
		List<User> temp = new ArrayList<>();
		for (int i = 0; i < treeArray.length() ; i++) {
			temp.addAll(datarangeService.getUsersByDatarange(treeArray.getJSONObject(i).getString("id")));
		}
		model.addAttribute("users", temp);// 当前部门下的所有人员(包含子部门)
		
		// 当前部门下的人员(不包含子部门)
//		model.addAttribute("users", datarangeService.getUsersByDatarange(deptUuid));
		
		model.addAttribute("numLimit", dataCount);// 单次上限
		
//		model.addAttribute("datarangeTree",datarangeService.getChildrenDatarangeTree(deptUuid));
		
		// (新功能)获取当前部门的所有子部门
		if(datarangeService.getChildrenDatarangeTree(deptUuid).length() > 0){
			model.addAttribute("datarangeTree",datarangeService.getChildrenDatarangeTree(deptUuid)); 
		}
		
        return "datamanager/allotData_index";
    }
	
	
	@RequestMapping("allotDeptData")
	public String allotDeptData(String dataBatchUuid, String deptUuid, Integer dataCount, Model model){
		DataBatch db = dbService.getByUuid(UUID.UUIDFromString(dataBatchUuid));
		model.addAttribute("entry", db);
		model.addAttribute("dataBatchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		model.addAttribute("users", datarangeService.getUsersByDatarange(deptUuid));
		model.addAttribute("numLimit", dataCount);// 判断部门内数据量是否存在
		
		// 获取当前部门的所有子部门
		model.addAttribute("datarangeTree",datarangeService.getChildrenDatarangeTree(deptUuid));
		model.addAttribute("childdeptnum",datarangeService.getChildrenDatarangeTree(deptUuid).length());
		
		return "datamanager/allotDeptData_index";
	}
	
	
	/**
	 * 回收数据
	 * @param dataBatchUuid 批次
	 * @param deptUuid		部门
	 * @param totalLimit	限制数
	 * @param model
	 * @return
	 */
	@RequestMapping("returnData")
    public String returnData(String dataBatchUuid, String deptUuid, Integer totalLimit, Model model){
		model.addAttribute("dataBatchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		model.addAttribute("totalLimit", totalLimit);
        return "datamanager/returnData_index";
    }
	
	//修改部门数据上限
	@RequestMapping("changeLimit")
	public String changeLimit(HttpServletRequest request, Model model){
		List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid()); // 当前用户的管辖部门
		List<DepartmentTable> depts = new ArrayList<DepartmentTable>();
		for (String dept : departments) {
			depts.add(departmentTableService.getByUuid(UUID.UUIDFromString(dept)));
		}
		if(depts.size() > 0){
//			model.addAttribute("allDept", departmentTableService.getAll());
			model.addAttribute("allDept", depts);
		}
		return "datamanager/dept_limit";
	}
	
	@RequestMapping("updateLimit")
	@ResponseBody
	public String updateLimit(String[] departmentUuid, Integer[] totalLimit){
		departmentTableService.updateLimit(departmentUuid, totalLimit);
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 回收数据
	 * @param dataBatchUuid 批次
	 * @param deptUuid		部门
	 * @param totalLimit	限制数
	 * @param model
	 * @return
	 */
	@RequestMapping("collectData")
    public String collectData(String dataBatchUuid, String departmentUuid, Model model){
		DataBatchDepartmentLink entry = deptDataService.getByLink(dataBatchUuid, departmentUuid);
		List<UserTask> list = deptDataService.getUserTaskInfo(dataBatchUuid, departmentUuid);
		model.addAttribute("dataBatchUuid", dataBatchUuid);
		model.addAttribute("departmentUuid", departmentUuid);
		model.addAttribute("entry", entry);
		
		// 二次领用的数据量
		int owndata = entry.getOwnCount();
		
		// 意向客户量
		int intentdata = entry.getIntentCount();
		
		// 剩余回收数据量
		model.addAttribute("overdata", (owndata - intentdata) <= 0 ? 0 : (owndata - intentdata));
		
		// 查询未拨打的数据
		List<UserTask> unCallList = deptDataService.getUserTaskInfoUnCall(dataBatchUuid, departmentUuid);
		
		// 查询已达已通的数据
		List<UserTask> callUnList = deptDataService.getUserTaskInfoCallUn(dataBatchUuid, departmentUuid);
		
		// 查询已呼通的数据
		List<UserTask> callList = deptDataService.getUserTaskInfoCall(dataBatchUuid, departmentUuid);
		
		for (UserTask ut : list) {
			ut.setUnCallCount(0);
			ut.setCallUnCount(0);
			ut.setCallCount(0);
			for (UserTask unCall : unCallList) {
				if(ut.getUserName().equals(unCall.getUserName())){
					ut.setUnCallCount(unCall.getDataCount());
				}
			}
			for (UserTask callUn : callUnList) {
				if(ut.getUserName().equals(callUn.getUserName())){
					ut.setCallUnCount(callUn.getDataCount());
				}
			}
			for (UserTask call : callList) {
				if(ut.getUserName().equals(call.getUserName())){
					ut.setCallCount(call.getDataCount());
				}
			}
		}
		
		model.addAttribute("userlist", list);
        
		return "datamanager/data-collection";
    }
	
	@RequestMapping("newCollectData")
	public String newCollectData(String dataBatchUuid, String departmentUuid, Model model){
		DataBatchDepartmentLink entry = deptDataService.getByLink(dataBatchUuid, departmentUuid);
		model.addAttribute("dataBatchUuid", dataBatchUuid);
		model.addAttribute("departmentUuid", departmentUuid);
		model.addAttribute("entry", entry);
		
		// -----------------------------------------------------------------------------------------
		// (新功能)获取当前部门的所有子部门
		if(datarangeService.getChildrenDatarangeTree(departmentUuid).length() > 0){
			model.addAttribute("datarangeTree",datarangeService.getChildrenDatarangeTree(departmentUuid)); 
		}
		
		JSONArray treeArray = datarangeService.getChildrenDatarangeTree(departmentUuid);
		List<User> temp = new ArrayList<>();
		for (int i = 0; i < treeArray.length() ; i++) {
			temp.addAll(datarangeService.getUsersByDatarange(treeArray.getJSONObject(i).getString("id")));
		}
		model.addAttribute("users", temp);// 当前部门下的所有人员(包含子部门)
		
		return "datamanager/data-collection";
	}
	
	@RequestMapping("collectDeptData")
	public String collectDeptData(String batchUuid, String deptUuid, Model model){
		DataBatchDepartmentLink entry = deptDataService.getByLink(batchUuid, deptUuid);
		model.addAttribute("entry", entry);
		DepartmentTable dt = departmentTableService.getByUuid(UUID.UUIDFromString(deptUuid)); // 获取相关部门信息
		model.addAttribute("totallimit", dt.getTotalLimit()); // 当前部门能获取的总的数据
		if(datarangeService.getChildrenDatarangeTree(deptUuid).length() > 1){
			model.addAttribute("datarangeTree", datarangeService.getChildrenDatarangeTree(deptUuid)); // 获取当前部门的所有子部门
		}
		return "datamanager/collectDeptData";
	}
	
	@RequestMapping("collectDeptDataSave")
	@ResponseBody
    public String collectDeptDataSave(String batchUuid, String deptUuid, String depts){
		int num = dataCustomerLinkService.collectDeptDataSave(batchUuid, deptUuid, depts);
		return new JSONObject().put("success", true).put("num", num).toString();
    }
	
	@RequestMapping("collect")
	@ResponseBody
    public String collect(String dataBatchUuid, String departmentUuid, String[] users, Integer[] collect, Model model){
		if (users == null) {
			return new JSONObject().put("success", true).toString();
		}
		deptDataService.collectData(dataBatchUuid, departmentUuid, users, collect);
		return new JSONObject().put("success", true).toString();
    }
	
	
	@RequestMapping("newCollectSave")
	@ResponseBody
    public String newCollectSave(String dataBatchUuid, String departmentUuid, String dpusers, Integer[] collect){
		String[] users = dpusers.split(",");
		if(users == null){
			return new JSONObject().put("success", true).toString();
		}
		int num = deptDataService.newCollectUserData(dataBatchUuid, departmentUuid, users, collect);
		return new JSONObject().put("success", true).put("num", num).toString();
    }
	
	/**
	 * 回收人下面的所有数据
	 * 用户管理中删除用户的时候需要用
	 * @param userId
	 * @return
	 */
	@RequestMapping("recycleByUser")
	@ResponseBody
	public String recycleByUser(String userId){
		boolean b = false;
		if(StringUtils.isNotBlank(userId)){
			b = deptDataService.recycleByUser(userId);
		}
		return new JSONObject().put("success", b).toString();
	}
	
	/**
	 * 保存分配到人的数据
	 * @param dataBatchUuid 批次
	 * @param deptUuid      部门
	 * @param allotDataNumber 分配数量
	 * @param deptusers     分配人员
	 * @return
	 */
	@RequestMapping("allotDataSave")
	@ResponseBody
    public String allotDataSave(DeptAllotDataCondition cond, String dpusers){
		String[] ds = dpusers.split(",");
		cond.setDeptusers(ds);
		int num = deptDataService.allotData(cond);
		return new JSONObject().put("success", true).put("num", num).toString();
    }
	
	/**
	 * 保存分配到部门的数据
	 * @param cond
	 * @param allocateMax
	 * @return
	 */
	@RequestMapping("allotDeptDataSave")
	@ResponseBody
	public String allotDeptDataSave(DeptAllotDataCondition cond, String depts){
//		String[] ds = depts.split(",");
//		List<String> list = new ArrayList<String>();
//		for (int i = 0; i < ds.length; i++) {
//			list.add(ds[i]);
//		}
//		for (int i = 0; i < list.size(); i++) {
//			DataBatchDepartmentLink dbd = dataBatchDepartmentLinkService.getByLink(cond.getDataBatchUuid(), list.get(i));
//			if(list.get(i).equals(cond.getDeptUuid())){  
//		    	list.remove(list.get(i));
//		    	--i;
//		    }else if(dbd == null){
//		    	list.remove(list.get(i));
//		    	--i;
//		    }  
//		}
//		
//		int num = 0;
//		if(list.size() > 0 ){
//			String[] dts = (String[])list.toArray(new String[list.size()]);
//			cond.setDepts(dts);
//			num = deptDataService.allotDeptData(cond);
//		}
		int num = dataCustomerLinkService.allotDeptDataSave(cond, depts);
		return new JSONObject().put("success", true).put("num", num).toString();
	}
	
	/**
	 * 批量分配数据
	 * @param dataUuid  数据集的UUID
	 * @param user      分配人
	 * @param batchUuid 批次
	 * @param deptUuid  部门
	 * @return
	 */
	@RequestMapping("batchAllotData")
	@ResponseBody
    public String batchAllotData(String dataUuid, String user, String batchUuid, String deptUuid){
		String[] dataUuids = dataUuid.substring(0, dataUuid.length()).split(",");
		if (dataUuids.length > 0) {
			deptDataService.allotBatchData(batchUuid, deptUuid, dataUuids, user);
		}
		return new JSONObject().put("success", true).toString();
    }
	
	/**
	 * 批量回收数据
	 * @param dataUuid  数据集的UUID
	 * @param batchUuid 批次
	 * @param deptUuid  部门
	 * @return
	 */
	@RequestMapping("batchReturnData")
	@ResponseBody
    public String batchReturnData(String dataUuid, String batchUuid, String deptUuid){
		String[] dataUuids = dataUuid.substring(0, dataUuid.length()).split(",");
		if (dataUuids.length > 0) {
			deptDataService.returnBatchData(batchUuid, deptUuid, dataUuids);
		}
		return new JSONObject().put("success", true).toString();
    }
	
	/**
	 * 保存回收数据
	 * @param dataBatchUuid    批次
	 * @param deptUuid         部门
	 * @param returnDataNumber 回收数量
	 * @return
	 */
	@RequestMapping("returnDataSave")
	@ResponseBody
    public String returnDataSave(String dataBatchUuid, String deptUuid, Integer returnDataNumber){
		deptDataService.returnData(dataBatchUuid, deptUuid, returnDataNumber);
		return new JSONObject().put("success", true).toString();
    }
	
	/**
	 * 获取数据
	 * @param dataBatchUuid 批次
	 * @param deptUuid		部门
	 * @param totalLimit	数量
	 * @param model
	 * @return
	 */
	@RequestMapping("achieveData")
    public String achieveData(String dataBatchUuid, String deptUuid, 
    		Integer singlelimit, Integer daylimit, Integer totallimit, Integer dataCount, Integer restData, Model model){
		// 已获取的数量
		int totalcounts = deptDataService.getDepartmentDataCount(deptUuid);
		// 单日已获取的数据量
		// int todayCounts = deptDataService.getDepartmentDataTodayData(deptUuid);
		int todayCounts = deptDataService.getDataBatchDataTodayData(dataBatchUuid, deptUuid);
		
		singlelimit = MathUtil.min(totallimit - totalcounts, daylimit - todayCounts, singlelimit, dataCount, restData);
		
		model.addAttribute("dataBatchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		model.addAttribute("totalLimit", singlelimit);// 单次上限
        return "datamanager/achieveData_index";
    }
	
	/**
	 * 保存获取数据
	 * @param dataBatchUuid     批次
	 * @param deptUuid			部门
	 * @param achieveDataNumber 数量
	 * @return
	 */
	@RequestMapping("achieveDataSave")
	@ResponseBody
    public String achieveDataSave(String dataBatchUuid, String deptUuid, Integer achieveDataNumber){
		deptDataService.achieveData(dataBatchUuid, deptUuid, achieveDataNumber);
		return new JSONObject().put("success", true).toString();
    }
	
	/**
	 * 点击部门内部数据量弹出的页面
	 * @param dataBatchUuid
	 * @param deptUuid
	 * @param model
	 * @return
	 */
	@RequestMapping("getDataCount")
    public String getDataCount(String arg1, Model model, String dataBatchUuid, String deptUuid, String islock){

//		arg1 = arg1.replace("(", "{").replace(")", "}");
//		JSONObject json = new JSONObject(arg1);
//		if(json.length() != 0){
//			model.addAttribute("batchUuid", json.get("dataBatchUuid"));
//			model.addAttribute("deptUuid", json.get("deptUuid"));
//			model.addAttribute("users", datarangeService.getUsersByDatarange(json.get("deptUuid").toString()));
//		}
//		return "datamanager/getDataCount_look";
		
		model.addAttribute("batchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		model.addAttribute("users", datarangeService.getUsersByDatarange(deptUuid));
		model.addAttribute("islock", islock);
        return "datamanager/getDataCount_tan";
    }
	
	/**
	 * 点击二次领用的数据量弹出的页面
	 * @param dataBatchUuid
	 * @param deptUuid
	 * @param model
	 * @return
	 */
	@RequestMapping("getOwnCount")
    public String getOwnCount(String arg1, Model model, String dataBatchUuid, String deptUuid, String islock){
		
//		arg1 = arg1.replace("(", "{").replace(")", "}");
//		JSONObject json = new JSONObject(arg1);
//		if(json.length() != 0){
//			model.addAttribute("batchUuid", json.get("dataBatchUuid"));
//			model.addAttribute("deptUuid", json.get("deptUuid"));
//		}
//		return "datamanager/getOwnCount_look";
		
		model.addAttribute("batchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		model.addAttribute("islock", islock);
        return "datamanager/getOwnCount_tan";
    }
	
	/**
	 * 点击废号量弹出的页面
	 * @param arg1
	 * @param model
	 * @return
	 */
	@RequestMapping("getAbandonCount")
    public String getAbandonCount(String arg1, Model model, String dataBatchUuid, String deptUuid){

//		arg1 = arg1.replace("(", "{").replace(")", "}");
//		JSONObject json = new JSONObject(arg1);
//		if(json.length() != 0){
//			model.addAttribute("batchUuid", json.get("dataBatchUuid"));
//			model.addAttribute("deptUuid", json.get("deptUuid"));
//		}
//        return "datamanager/getAbandonCount_look";
        
		model.addAttribute("batchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		
        return "datamanager/getAbandonCount_tan";
    }
	
	/**
	 * 点击黑名单弹出的信息
	 * @param arg1
	 * @param model
	 * @return
	 */
	@RequestMapping("getBlackListCount")
    public String getBlackListCount(String arg1, Model model, String dataBatchUuid, String deptUuid){

//		arg1 = arg1.replace("(", "{").replace(")", "}");
//		JSONObject json = new JSONObject(arg1);
//		if(json.length() != 0){
//			model.addAttribute("batchUuid", json.get("dataBatchUuid"));
//			model.addAttribute("deptUuid", json.get("deptUuid"));
//		}
//        return "datamanager/getBlackListCount_look";
        
        model.addAttribute("batchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		
		return "datamanager/getBlackListCount_tan";
    }
	
	/**
	 * 点击意向客户弹出的信息
	 * @param arg1
	 * @param model
	 * @return
	 */
	@RequestMapping("getIntentCount")
    public String getIntentCount(String arg1, Model model, String dataBatchUuid, String deptUuid){
		
//		arg1 = arg1.replace("(", "{").replace(")", "}");
//		JSONObject json = new JSONObject(arg1);
//		if(json.length() != 0){
//			model.addAttribute("batchUuid", json.get("dataBatchUuid"));
//			model.addAttribute("deptUuid", json.get("deptUuid"));
//		}
//        return "datamanager/getIntentCount_look";
        
        model.addAttribute("batchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		
		return "datamanager/getIntentCount_tan";
    }
	
	/**
	 * 部门内数据量信息
	 * @param request
	 * @param departmentDataCondition
	 * @param model
	 * @return
	 */
	@RequestMapping("getDataCountData")
	@ResponseBody
	public String getDataCountData(HttpServletRequest request, DepartmentDataCondition departmentDataCondition, Model model) {
		
		departmentDataCondition.setRequest(request);
		PageResult<DepartmentData> pageResult = deptDataService.queryDepartmentDataPage(departmentDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DepartmentData> list = pageResult.getRet();
		for (DepartmentData dept : list) {
			JSONObject json = new JSONObject(dept);
			json.put("batchname", StringUtils.trimToEmpty(dept.getBatchname()));
			json.put("deptname", StringUtils.trimToEmpty(dept.getDeptname()));
			json.put("phoneNumber", StringUtils.trimToEmpty(dept.getPhoneNumber().toString()));
			json.put("json", StringUtils.trimToEmpty(dept.getJson()));
			json.put("ownDepartment", StringUtils.trimToEmpty(dept.getOwnDepartment()));
			json.put("ownDepartmentTimestamp", StringUtils.trimToEmpty(fmt.format(dept.getOwnDepartmentTimestamp())));
			json.put("lockTimestamp", dept.getLockTimestamp() == null ? "": StringUtils.trimToEmpty(fmt.format(dept.getLockTimestamp())));
			jsonArray.put(json);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	/**
	 * 二次领用数据量信息
	 * @param request
	 * @param departmentDataCondition
	 * @param model
	 * @return
	 */
	@RequestMapping("getOwnCountData")
	@ResponseBody
	public String getOwnCountData(HttpServletRequest request, DepartmentDataCondition departmentDataCondition, Model model) {
		
		departmentDataCondition.setRequest(request);
		PageResult<UserData> pageResult = deptDataService.queryUserDataPage(departmentDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<UserData> list = pageResult.getRet();
		for (UserData ud : list) {
			JSONObject json = new JSONObject(ud);
			json.put("batchname", StringUtils.trimToEmpty(ud.getBatchname()));
			json.put("deptname", StringUtils.trimToEmpty(ud.getDeptname()));
			json.put("username", StringUtils.trimToEmpty(ud.getUsername()));
			json.put("phoneNumber", StringUtils.trimToEmpty(ud.getPhoneNumber().toString()));
			json.put("json", StringUtils.trimToEmpty(ud.getJson()));
			json.put("ownDepartment", StringUtils.trimToEmpty(ud.getOwnDepartment()));
			json.put("ownDepartmentTimestamp", ud.getOwnDepartmentTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(ud.getOwnDepartmentTimestamp())));
			json.put("ownUserTimestamp", ud.getOwnUserTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(ud.getOwnUserTimestamp())));
			json.put("callcount", StringUtils.trimToEmpty(ud.getCallCount().toString()));
			json.put("lastCallResult", StringUtils.trimToEmpty(ud.getLastCallResult()));
			json.put("lastCallTime", ud.getLastCallTime() == null ? "" : StringUtils.trimToEmpty(fmt.format(ud.getLastCallTime())));
			json.put("intentType", StringUtils.trimToEmpty(ud.getIntentTypeName()));
			json.put("intentTimestamp", ud.getIntentTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(ud.getIntentTimestamp())));
			jsonArray.put(json);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	/**
	 * 废号量信息
	 * @param request
	 * @param departmentDataCondition
	 * @param model
	 * @return
	 */
	@RequestMapping("getAbandonCountData")
	@ResponseBody
	public String getAbandonCountData(HttpServletRequest request, DepartmentDataCondition departmentDataCondition, Model model) {
		
		departmentDataCondition.setRequest(request);
		PageResult<DataBatchData> pageResult = deptDataService.queryAbandonDataPage(departmentDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataBatchData> list = pageResult.getRet();
		for (DataBatchData dbd : list) {
			JSONObject json = new JSONObject(dbd);
			// 批次名
			json.put("batchname", StringUtils.trimToEmpty(dbd.getDataBatchName()));
			// 号码
			json.put("phoneNumber", StringUtils.trimToEmpty(dbd.getPhoneNumber()));
			// 已领用部门
			json.put("deptname", StringUtils.trimToEmpty(dbd.getDeptName()));
			// 部门领用时间
			json.put("ownDepartmentTimestamp", StringUtils.trimToEmpty(fmt.format(dbd.getOwnDepartmentTimestamp())));
			// 部门分配给个人的名字
			json.put("username", StringUtils.trimToEmpty(dbd.getUserName()));
			// 部门分配给个人的时间
			json.put("ownUserTimestamp",dbd.getOwnUserTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(dbd.getOwnUserTimestamp())));
			jsonArray.put(json);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	/**
	 * 黑名单信息
	 * @param request
	 * @param departmentDataCondition
	 * @param model
	 * @return
	 */
	@RequestMapping("getBlackListCountData")
	@ResponseBody
	public String getBlackListCount(HttpServletRequest request, DepartmentDataCondition departmentDataCondition, Model model) {
		
		departmentDataCondition.setRequest(request);
		PageResult<DataBatchData> pageResult = deptDataService.queryBlackListDataPage(departmentDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataBatchData> list = pageResult.getRet();
		for (DataBatchData dbd : list) {
			JSONObject json = new JSONObject(dbd);
			// 批次名
			json.put("batchname", StringUtils.trimToEmpty(dbd.getDataBatchName()));
			// 号码
			json.put("phoneNumber", StringUtils.trimToEmpty(dbd.getPhoneNumber()));
			// 已领用部门
			json.put("deptname", StringUtils.trimToEmpty(dbd.getDeptName()));
			// 部门领用时间
			json.put("ownDepartmentTimestamp", StringUtils.trimToEmpty(fmt.format(dbd.getOwnDepartmentTimestamp())));
			// 部门分配给个人的名字
			json.put("username", StringUtils.trimToEmpty(dbd.getUserName()));
			// 部门分配给个人的时间
			json.put("ownUserTimestamp",dbd.getOwnUserTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(dbd.getOwnUserTimestamp())));
			jsonArray.put(json);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	
	/**
	 * 点击意向客户弹出的信息
	 * @param arg1
	 * @param model
	 * @return
	 */
	@RequestMapping("getCustomerCount")
    public String getCustomerCount(String arg1, Model model, String dataBatchUuid, String deptUuid){
		
//		arg1 = arg1.replace("(", "{").replace(")", "}");
//		JSONObject json = new JSONObject(arg1);
//		if(json.length() != 0){
//			model.addAttribute("batchUuid", json.get("dataBatchUuid"));
//			model.addAttribute("deptUuid", json.get("deptUuid"));
//		}
//        return "datamanager/getIntentCount_look";
        
        model.addAttribute("batchUuid", dataBatchUuid);
		model.addAttribute("deptUuid", deptUuid);
		
		return "datamanager/getCustomerCount_tan";
    }
	
	@RequestMapping("getCustomerCountData")
	@ResponseBody
	public String getCustomerCount(HttpServletRequest request, DepartmentDataCondition departmentDataCondition, Model model) {
		
		departmentDataCondition.setRequest(request);
		PageResult<DataBatchData> pageResult = deptDataService.queryCustomerDataPage(departmentDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataBatchData> list = pageResult.getRet();
		for (DataBatchData dbd : list) {
			JSONObject json = new JSONObject(dbd);
			// 批次名
			json.put("batchname", StringUtils.trimToEmpty(dbd.getDataBatchName()));
			// 号码
			json.put("phoneNumber", StringUtils.trimToEmpty(dbd.getPhoneNumber()));
			// 已领用部门
			json.put("deptname", StringUtils.trimToEmpty(dbd.getDeptName()));
			// 部门领用时间
			json.put("ownDepartmentTimestamp", StringUtils.trimToEmpty(fmt.format(dbd.getOwnDepartmentTimestamp())));
			// 部门分配给个人的名字
			json.put("username", StringUtils.trimToEmpty(dbd.getUserName()));
			// 部门分配给个人的时间
			json.put("ownUserTimestamp",dbd.getOwnUserTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(dbd.getOwnUserTimestamp())));
			jsonArray.put(json);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	
	/**
	 * 意向客户信息
	 * @param request
	 * @param departmentDataCondition
	 * @param model
	 * @return
	 */
	@RequestMapping("getIntentCountData")
	@ResponseBody
	public String getIntentCountData(HttpServletRequest request, DepartmentDataCondition departmentDataCondition, Model model) {
		
		departmentDataCondition.setRequest(request);
		PageResult<UserData> pageResult = deptDataService.queryIntentDataPage(departmentDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<UserData> list = pageResult.getRet();
		for (UserData ud : list) {
			JSONObject json = new JSONObject(ud);
			json.put("batchname", StringUtils.trimToEmpty(ud.getBatchname()));
			json.put("deptname", StringUtils.trimToEmpty(ud.getDeptname()));
			json.put("username", StringUtils.trimToEmpty(ud.getUsername()));
			json.put("phoneNumber", StringUtils.trimToEmpty(ud.getPhoneNumber().toString()));
			json.put("json", StringUtils.trimToEmpty(ud.getJson()));
			json.put("ownDepartment", StringUtils.trimToEmpty(ud.getOwnDepartment()));
			json.put("ownDepartmentTimestamp", ud.getOwnDepartmentTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(ud.getOwnDepartmentTimestamp())));
			json.put("ownUserTimestamp", ud.getOwnUserTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(ud.getOwnUserTimestamp())));
			json.put("callcount", StringUtils.trimToEmpty(ud.getCallCount().toString()));
			json.put("lastCallResult", StringUtils.trimToEmpty(ud.getLastCallResult()));
			json.put("lastCallTime", ud.getLastCallTime() == null ? "" : StringUtils.trimToEmpty(fmt.format(ud.getLastCallTime())));
//			json.put("intentType", StringUtils.trimToEmpty(ud.getIntentType()));
			json.put("intentType", StringUtils.trimToEmpty(ud.getIntentTypeName()));
			json.put("intentTimestamp", ud.getIntentTimestamp() == null ? "" : StringUtils.trimToEmpty(fmt.format(ud.getIntentTimestamp())));
			jsonArray.put(json);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	/**
	 * 全选部门内数据量
	 * @param request
	 * @param batchUuid
	 * @param deptUuid
	 * @return
	 */
	@RequestMapping("dataCountAll")
    @ResponseBody
    public String dataCountAll(HttpServletRequest request, String batchUuid, String deptUuid) {
    	 List<DepartmentData> list = deptDataService.getAllDepartmentData(batchUuid, deptUuid);
    	 JSONArray jsonArray = new JSONArray();
    	 if(list != null) {
			for (int i = 0; i < list.size(); i++) {
				jsonArray.put(list.get(i).getUid());
			}
		}
		return jsonArray.toString();
    }
	
	/**
	 * 全选二次领用数据
	 * @param request
	 * @param batchUuid
	 * @param deptUuid
	 * @return
	 */
	@RequestMapping("ownCountAll")
    @ResponseBody
    public String ownCountAll(HttpServletRequest request, String batchUuid, String deptUuid, String username, String lastCallResult) {
		DepartmentDataCondition cond = new DepartmentDataCondition();
		cond.setBatchUuid(batchUuid);
		cond.setDeptUuid(deptUuid);
		cond.setUserID(username);
		cond.setLastCallResult(lastCallResult);
		List<String> list = deptDataService.getUserDatas(cond);
    	 JSONArray jsonArray = new JSONArray();
    	 if(list != null) {
			for (int i = 0; i < list.size(); i++) {
				jsonArray.put(list.get(i));
			}
		}
		return jsonArray.toString();
    }
	
	/**
	 * 页面点击关闭/开启按钮
	 * @param batchUuid
	 * @param deptUuid
	 * @return
	 */
	@RequestMapping("updateOpenFlag")
	@ResponseBody
	public String updateOpenFlag(String batchUuid, String deptUuid){
		int k = 0;
		DataBatchDepartmentLink dd = deptDataService.getByLink(batchUuid, deptUuid);
		if(dd != null){
			if("0".equals(dd.getOpenFlag())){
				
				DatarangeServiceImp.resultSet = new HashSet<>();
				// 获取上级部门是否开启了获取
				Set<String> resultSet = datarangeService.getParentUuid(deptUuid);
				if(resultSet != null){
					for (String str : resultSet) {
						if("0".equals(datarangeService.getOpenFlag(batchUuid, str))){// 如果上级部门存在关闭开启
							k = 1;
						}
					}
				}
				
				if(k == 0){
					dd.setOpenFlag("1");
				}
			}else{
				
				// 关闭时修改相关子部门关闭
				Set<String> childDepts = datarangeService.getAllChildren(dd.getDepartmentUuid());
				
				for (String dept : childDepts) {
					if(datarangeService.getOpenFlag(dd.getDataBatchUuid(), dept) != null){
						DataBatchDepartmentLink db = dataBatchDepartmentLinkService.getByLink(batchUuid, dept);
						db.setOpenFlag("0");
						deptDataService.update(db);
					}
				}
				
				dd.setOpenFlag("0");
			}
		}
		
		deptDataService.update(dd);
		
		if(k == 1){
			return new JSONObject().put("success", false).put("msg", "您的上级部门已关闭了获取,暂不能开启获取！").toString();
		}
		return new JSONObject().put("success", true).toString();
    }
	
	@RequestMapping("dataStat")
	@ResponseBody
    public String dataStat(String dataBatchUuid, String deptUuid, Model model){
		JSONObject json = new JSONObject(deptDataService.getDataDeptStat(dataBatchUuid, deptUuid));
		return json.toString();
    }
}


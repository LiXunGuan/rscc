package com.ruishengtech.rscc.crm.ui.datamanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.data.service.ResultService;
import com.ruishengtech.rscc.crm.data.service.TaskService;
import com.ruishengtech.rscc.crm.datamanager.condition.DataBatchCondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DataBatchDataCondition;
import com.ruishengtech.rscc.crm.datamanager.manager.ExcelImporter;
import com.ruishengtech.rscc.crm.datamanager.manager.node.ShareNode;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchDataService;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataBatchDepartmentLinkServiceImp;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DepartmentDataServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;


@Controller
@RequestMapping("databatch/data")
public class DataBatchController {
	
	@Autowired
	private DataBatchService dataBatchService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ResultService resultService;
	
	@Autowired
	private DataBatchDataService batchDataService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BrokerService brokerService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private DepartmentDataServiceImp departmentDataService;
	
	@Autowired
	private DataBatchDepartmentLinkServiceImp dataBatchDepartmentLinkService;
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "data");
		model.addAttribute("items", new String[]{"批次名","文件名"});
		model.addAttribute("names", new String[]{"batchName","fileName"});
		model.addAttribute("titles", new String[]{"批次名","文件名","数据总量","已领用数量","创建人","导入时间"});
		model.addAttribute("columns", new String[]{"batchName","fileName","dataCount","ownCount","uploadUser","dataImportTimestamp"});
		
		model.addAttribute("iframecontent","datamanager/dataBatch_index");
		return "iframe";
		
//		return "datamanager/dataBatch_index";
	}
	
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, DataBatchCondition dataBatchCondition,
			Model model) {
		dataBatchCondition.setRequest(request);
		//获取当前登录用户id
		String loginUserId= SessionUtil.getCurrentUser(request).getUid();
		//获取当前登录用户的管辖部门
		List<String> departments = userService.getDepartments(loginUserId);
		//跟用户管辖部门相关联的批次ID
		Set<String> databatchs = dataBatchService.getDepartmentsDataBatchs(departments);
		dataBatchCondition.setIns(databatchs);
		//页面只能看到自己和超级管理员上传的批次
		PageResult<DataBatch> pageResult = dataBatchService.queryPage(dataBatchCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataBatch> list = pageResult.getRet();
		for (DataBatch i:list) {
			JSONObject jsonObject = new JSONObject(i);
            jsonObject.put("intentCount", StringUtils.trimToEmpty(String.valueOf(i.getIntentCount()==null?0:i.getIntentCount())));
            jsonObject.put("customerCount", StringUtils.trimToEmpty(String.valueOf(i.getCustomerCount()==null?0:i.getCustomerCount())));
            jsonObject.put("frozenCount", StringUtils.trimToEmpty(String.valueOf(i.getFrozenCount()==null?0:i.getFrozenCount())));
            jsonObject.put("abandonCount", StringUtils.trimToEmpty(String.valueOf(i.getAbandonCount()==null?0:i.getAbandonCount())));
            jsonObject.put("shareCount", StringUtils.trimToEmpty(String.valueOf(i.getShareCount()==null?0:i.getShareCount())));
            jsonObject.put("blacklistCount", StringUtils.trimToEmpty(String.valueOf(i.getBlacklistCount()==null?0:i.getBlacklistCount())));

			if (i.getDataTable() == null) {
				jsonObject.put("progress", "上传中"); 
			} else if (i.getDataCount() > 0){
				jsonObject.put("progress", "导入完成");
			} else {
				jsonObject.put("progress", ExcelImporter.getInstance().getStatus(i.getDataTable()));
			}
			jsonObject.put("dataImportTimestamp", DateUtils.getDateString(i.getDataImportTimestamp()));
			jsonObject.put("uploadUser", userService.getByUuid(UUID.UUIDFromString(i.getUploadUser())).getUserDescribe());
			jsonObject.put("uploadid", i.getUploadUser());
			jsonObject.put("loginid", loginUserId);
			
			jsonArray.put(jsonObject);
		}
		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	/**
	 * 点击上传
	 * @param request
	 * @param response
	 * @param currentDataTable
	 * @param model
	 * @return
	 */
	@RequestMapping("upload")
	public String addData(HttpServletRequest request,
			HttpServletResponse response, String currentDataTable,
			Model model) {
		model.addAttribute("model", "data");
		model.addAttribute("title", "上传数据");
        //设置单日和单次上限
        model.addAttribute("daylimit", "5000");
        model.addAttribute("singlelimit", "5000");
        
        
//		List<DepartmentTable> l = null;
//		//注意这里的admin判定
//		if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
//			l = dataBatchService.getAllDepartment();
//		} else {
//			List<String> list = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
//			l = dataBatchService.getAllDepartment();
//			for (int i = 0; i < l.size(); i++) {
//				if(!list.contains(l.get(i).getUid())) {
//					l.remove(i--);
//				}
//			}
//		}
//		model.addAttribute("departments", l);
		
        // 新加树行数据
        JSONArray json = null;
		if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
			json = datarangeService.getDatarangeTree();
		} else {
			json = userService.getDatarangePermissionTree(SessionUtil.getCurrentUser(request).getUid());
		}
		model.addAttribute("departments", json.toString());
		return "datamanager/dataBatch_upload";
	}
	
	/**
	 * 点击修改
	 * @param request
	 * @param response
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("change")
	public String changeData(HttpServletRequest request,HttpServletResponse response, String uuid,Model model) {
		
		model.addAttribute("title", "修改数据");
		
		//设置单日和单次上限
		model.addAttribute("singlelimit", "5000");
        model.addAttribute("daylimit", "5000");
        DataBatch entry = new DataBatch();
		if(StringUtils.isNotBlank(uuid)){
			entry = dataBatchService.getByUuid(UUID.UUIDFromString(uuid));
		}
		model.addAttribute("entry", entry);
		//根据批次uuid获取到部门关联表数据
		List<DataBatchDepartmentLink> dbdl = dataBatchDepartmentLinkService.getByBatchUuid(uuid);
		model.addAttribute("dataBatchDepartmentLink", dbdl);
		
		//使修改页面 部门后面的上限输入框自动显示已经设置的值
		List<String> deptAndLimit = new ArrayList<>();
		if(dbdl.size()!=0){
			for(DataBatchDepartmentLink dd : dbdl){
				//保存部门对象，和他对应的单次/单日上限
				deptAndLimit.add(dd.getDepartmentUuid()+"-"+dataBatchService.getLimitByDeptId(uuid, dd.getDepartmentUuid()));
			} 
		}
        model.addAttribute("deptAndLimit", deptAndLimit);
		
//        List<DepartmentTable> l = null;
//		//注意这里的admin判定
//		if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
//			l = dataBatchService.getAllDepartment();
//		} else {
//			List<String> list = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
//			l = dataBatchService.getAllDepartment();
//			for (int i = 0; i < l.size(); i++) {
//				if(!list.contains(l.get(i).getUid())) {
//					l.remove(i--);
//				}
//			}
//		}
//		model.addAttribute("departments", l);
		
		// 新加树行数据
		JSONArray json = null;
		if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
			json = datarangeService.getDatarangeTree();
		} else {
			json = userService.getDatarangePermissionTree(SessionUtil.getCurrentUser(request).getUid());
		}
		
		if(dbdl != null){
			for (DataBatchDepartmentLink ddl : dbdl) {
				for (int i = 0; i < json.length(); i++) {
				   JSONObject o = json.getJSONObject(i);
				   if(ddl.getDepartmentUuid().equals(o.get("id")) && "0".equals(ddl.getIsAuto())){
					   o.put("checked", true);
				   }
				}
			}
		}
		
		model.addAttribute("departments", json.toString());
		
		return "datamanager/dataBatch_update";
	}
	
	/**
	 * 修改确认
	 * @param request
	 * @param response
	 * @param batch
	 * @param dataInfo
	 * @param departments
	 * @param model
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public String updateData(HttpServletRequest request,
			HttpServletResponse response, DataBatch batch, String dataInfo, String[] departments, String undepartments,
			Model model) {
		dataBatchService.updateName(batch.getUid(), batch.getBatchName());
		if (departments != null)
			dataBatchService.bindDepartment(batch.getUid(), departments, false);
		if(undepartments != null)
			dataBatchService.unbindDepartment(batch.getUid(),undepartments.split(","));
		return new JSONObject().put("success", true).toString();
	}
	
	
	@RequestMapping("deleteBatch")
	@ResponseBody
	public String deleteBatch(HttpServletRequest request, String uuid, Model model) {
		dataBatchService.deleteBatch(uuid);
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 强制删除批次
	 * @param request
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("forceDeleteBatch")
	@ResponseBody
	public String forceDeleteBatch(HttpServletRequest request, String uuid, Model model) {
		boolean result = dataBatchService.forceDeleteBatch(uuid);
		if (!result) {
			return new JSONObject().put("success", false).put("message", "不能删除被群呼占用的数据").toString();
		}
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("batchDelete")
	@ResponseBody
	public String batchDelete(HttpServletRequest request, String batchUuid) {
		boolean result = dataBatchService.batchDeleteData(request, batchUuid);
		if(!result){
			return new JSONObject().put("success", false).put("msg", "勾选的数据有误,请检查！").toString();
		}
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 上传数据保存第一步
	 * @param request
	 * @param response
	 * @param batch
	 * @param departments
	 * @param model
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String saveDataLog(HttpServletRequest request,
			HttpServletResponse response, DataBatch batch,String[] departments,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		batch.setUploadUser(SessionUtil.getCurrentUser(request).getUid());
		//保存前先把批次名中的空格处理下
		batch.setBatchName(batch.getBatchName().trim().replaceAll(" ", "").replaceAll(" ", ""));
		jsonObject.put("success", dataBatchService.save(batch));
		jsonObject.put("uuid", batch.getUid());
		//最后的boolean参数，true:创建批次部门关联，false:更新数量
		dataBatchService.bindDepartment(batch.getUid(), departments, true);
		return jsonObject.toString();
	}
	
	/**
	 * 点击分配数据
	 * @param request
	 * @param response
	 * @param batchId
	 * @param model
	 * @return
	 */
	@RequestMapping("allocate")
	public String allocate(HttpServletRequest request,HttpServletResponse response, String batchId,Model model) {
		if(StringUtils.isNotBlank(batchId)){
			DataBatch entry = dataBatchService.getByUuid(UUID.UUIDFromString(batchId));
			Integer totalimit = entry.getDataCount()-entry.getOwnCount();
			model.addAttribute("totalimit", totalimit);
			model.addAttribute("entry", entry);
		}
		
		//获取到与该批次绑定的部门id
		List<String> d1 = dataBatchService.getDataBatchDepartments(batchId);
		//存放与该批次数据绑定的部门对象
		List<DepartmentTable> dept = new ArrayList<>();
		//存放 部门id=坐席对象
		Map<String,List<User>> m =new HashMap<>();
		 List<User> users = new ArrayList<>();
		for(String deptid : d1){
			  dept.add(dataBatchService.getDepartmentById(deptid));
			  users = dataBatchService.getUserBydeptId(deptid);
			  m.put(deptid, users);
		}
		
		if(dept.size()!=0){
			model.addAttribute("deptbeans", dept);
		}
		model.addAttribute("usermapKey", m.keySet());
		model.addAttribute("usermap", m);
		
		
		return "datamanager/dataBatch-allocate";
	}
	/**
	 * index页面的分配
	 * @param request
	 * @param batchUuid
	 * @param deptUuid
	 * @param count
	 * @param model
	 * @return
	 */
	@RequestMapping("allocateData")
	@ResponseBody
	public String allocateData(HttpServletRequest request,String batchUuid,String[] userUuid, Integer count,Model model){
		
		departmentDataService.getData(batchUuid, userUuid, count);
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 预览页面的分配
	 * @param request
	 * @param response
	 * @param model
	 * @param toid
	 * @param batchid
	 * @return
	 */
	@RequestMapping("importAllot")
	@ResponseBody
	public String importAllot(HttpServletRequest request,HttpServletResponse response,Model model,String toid,String batchid){
		String[] dataIds = request.getParameterValues("dataid[]");
		departmentDataService.getData(batchid, toid, dataIds);
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 上传页面的保存第二步保存上传的excel数据
	 * @param request
	 * @param multipartFile
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("uploader")
	@ResponseBody
    public String importExcel(HttpServletRequest request, MultipartFile multipartFile, String uuid, Model model) {
    	
    	if(multipartFile != null){
			// 上传Excel
    		String filePath;
			try {
				filePath = QueryUtils.uploadFile(multipartFile, ApplicationHelper.getContextPath() + "/upload");
//				if(!(filePath.endsWith(".xlsx") || filePath.endsWith(".xls")))
//					throw new IOException();
	    		File f = new File(filePath);
	    		DataBatch db = dataBatchService.getByUuid(UUID.UUIDFromString(uuid));
	    		db.setFileName(multipartFile.getOriginalFilename());
	    		db.setDataTable(uuid);
	    		db.setFilePath(f.getAbsolutePath());
	    		db.setDataCount(0);
	    		db.setOwnCount(0);
	    		db.setUploadUser(SessionUtil.getCurrentUser(request).getUid());
	    		dataBatchService.update(db);
	    		
	    		List<String> departments = dataBatchService.getDataBatchDepartments(uuid);
	    		Collection<String> users = userService.getDatarangesUsers(departments);
	    		Collection<String> relatedUsers = userService.getUsernamesByUuids(users);
	    		relatedUsers.add("admin");
	    		
	    		dataBatchService.importExcelToTable(uuid, filePath, SessionUtil.getCurrentUser(request).getLoginName(), db.getDataTable(), relatedUsers);
	    		//通知用户上传成功
	    		return new JSONObject().put("success", true).put("dataTable", db.getDataTable()).put("batchName", db.getBatchName()).toString();
			} catch (Exception e) {
				return new JSONObject().put("success", false).put("error_message", "上传文件格式错误").toString();
			}
    	}
    	return new JSONObject().put("success", false).toString();
    }
	
	/**
	 * 点击批次名查看数据
	 * @param request
	 * @param response
	 * @param dataTable
	 * @param model
	 * @param ownDepartment=global_share 为分配到共享池的数据
     * @param intentType = 1 表示需要查看intentType不为空的数据
     * @param customerUuid = 1表示需要查看customerUuid不为空的数据
	 * @return
	 */
	@RequestMapping("importModal")
    public String showImportData(HttpServletRequest request,HttpServletResponse response,
            String dataTable,String ownDepartment,String isFrozen,String isAbandon,String isBlacklist,String intentType,String customerUuid,
			Model model,String operation) {
		if(StringUtils.isNotBlank(ownDepartment) && ownDepartment.equals("global_share")){
			model.addAttribute("ownDepartment", "global_share");
		}
		if(StringUtils.isNotBlank(ownDepartment) && ownDepartment.equals("notnull")){
			model.addAttribute("ownDepartment", "notnull");
		}
		if(StringUtils.isNotBlank(isFrozen)){
			model.addAttribute("isType", "isFrozen");
		}
		if(StringUtils.isNotBlank(isAbandon)){
			model.addAttribute("isType", "isAbandon");
		}
		if(StringUtils.isNotBlank(isBlacklist)){
			model.addAttribute("isType", "isBlacklist");
		}
		if(StringUtils.isNotBlank(intentType)){
			model.addAttribute("intentType", intentType);
		}
		if(StringUtils.isNotBlank(customerUuid)){
			model.addAttribute("customerUuid", customerUuid);
		}
		DataBatch d = dataBatchService.getDataBatchByTable(dataTable);
		model.addAttribute("entry", d);
		String batchId=d.getUid();
		//获取到与该批次绑定的部门id
		List<String> d1 = dataBatchService.getDataBatchDepartmentsIgnoreLink(batchId);
		//存放与该批次数据绑定的部门对象
		List<DepartmentTable> dept = new ArrayList<>();
		//存放 部门id=坐席对象
		Map<String,List<User>> m =new HashMap<>();
		//获取所有绑定了部门的坐席对象，用于预览页面的筛选
		List<User> userss = new ArrayList<>();
		 List<User> users = new ArrayList<>();
		for(String deptid : d1){
			if(StringUtils.isNotBlank(deptid) && !"global_share".equals(deptid)){
			  if(dataBatchService.getDepartmentById(deptid) != null){
				  dept.add(dataBatchService.getDepartmentById(deptid));
			  }else{
				  System.out.println("被删除的部门UUID:" + deptid);
			  }
			  users  = dataBatchService.getUserBydeptId(deptid);
			  m.put(deptid, users);
			  
			  for(User u : users){
				  userss.add(u);
			  }
			  
			}
		}
		
		model.addAttribute("users", userss);
		if(dept.size()!=0){
			model.addAttribute("deptbeans", dept);
		}
		model.addAttribute("usermapKey", m.keySet());
		model.addAttribute("usermap", m);
		//获取所有意向类型
		List<DataIntent> dataintent = dataIntentService.getAll();
		model.addAttribute("intents", dataintent);
			
		
		if(StringUtils.isNotBlank(operation)){
			model.addAttribute("operation", "globalShare");
		}
		
		return "datamanager/dataBatch_look";
	}
	
	@RequestMapping("updateBatchDataDisplay")
	@ResponseBody
	public String updateBatchDataDisplay(String batchUuid){
		DataBatch d = dataBatchService.getDataBatchByTable(batchUuid);
		return new JSONObject().put("dataCount", d.getDataCount()).put("ownCount", d.getOwnCount()).toString();
	}
	
	
	/**
	 * 点击查看数据后需要加载的数据获取方法
	 * @param request
	 * @param response
	 * @param batchDataCondition
	 * @param model
	 * @return
	 */
	@RequestMapping("import")
	@ResponseBody
	public String importData(HttpServletRequest request,
			HttpServletResponse response, DataBatchDataCondition batchDataCondition,
			Model model) {
		batchDataCondition.setRequest(request);
		PageResult<DataBatchData> pageResult = batchDataService.queryPage(batchDataCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<DataBatchData> list = pageResult.getRet();
		for (DataBatchData dbd:list) {
			JSONObject jsonObject = new JSONObject(dbd);
			jsonObject.put("dataBatchName", dataBatchService.getByUuid(UUID.UUIDFromString(dbd.getBatchUuid())).getBatchName());
			if(StringUtils.isNotBlank(dbd.getOwnDepartment())){
				if (ShareNode.name.equals(dbd.getOwnDepartment())) {
					jsonObject.put("ownDepartment", "共享池");
				} else {
					jsonObject.put("ownDepartment",dataBatchService.getDepartmentById(dbd.getOwnDepartment()).getDepartmentName());
				}
			}
			if(StringUtils.isNotBlank(dbd.getOwnUser())){
				jsonObject.put("ownUser", userService.getByUuid(UUID.UUIDFromString(dbd.getOwnUser())).getUserDescribe());
			}
			if(StringUtils.isNotBlank(dbd.getIntentType())){
				DataIntent temp = dataIntentService.getByUuid(dbd.getIntentType());
				jsonObject.put("intentType", temp==null?"":temp.getIntentName());
			}
			//修改客户信息的显示方式
//			if(StringUtils.isNotBlank(dbd.getCustomerUuid())){
//				jsonObject.put("customerUuid", )
//			}
			if (dbd.getOwnDepartmentTimestamp()!=null)
				jsonObject.put("ownDepartmentTimestamp", DateUtils.getDateString(dbd.getOwnDepartmentTimestamp()));
			if (dbd.getOwnUserTimestamp()!=null)
				jsonObject.put("ownUserTimestamp", DateUtils.getDateString(dbd.getOwnUserTimestamp()));
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
	
	/**
	 * 预览数据中选择全部
	 * @param request
	 * @param response
	 * @param condition
	 * @param model
	 * @return
	 */
	@RequestMapping("importAll")
	@ResponseBody
	public String importAllData(HttpServletRequest request,
			HttpServletResponse response, DataBatchDataCondition condition,
			Model model) {
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray(dataBatchService.getBatchData(condition));

		jsonObject2.put("items", jsonArray);
		return jsonObject2.toString();
	}
	/**
	 * 校验批次名和数据上限
	 * @param request
	 * @param uid
	 * @param newDataName
	 * @param count
	 * @return
	 */
	@RequestMapping("checkname/{type}")
	@ResponseBody
	public String checkName(HttpServletRequest request, String uid,String newDataName,Integer count) {
		if(StringUtils.isNotBlank(uid)&&(count != null && count != 0)){
			DataBatch db =	dataBatchService.getByUuid(UUID.UUIDFromString(uid));
			Integer dataAll = db.getDataCount();
			Integer dataOwn = db.getOwnCount();
			if(count>(dataAll-dataOwn) || count<0){
				return "false";
			}else{
				return "true";
			}
		}
		if((uid == null || uid.equals("undefined") && dataBatchService.getDataBatchByBatchName(newDataName)==null)){
			return "true";
		}else if((uid == null || uid.equals("undefined") && dataBatchService.getDataBatchByBatchName(newDataName) != null)){
			return "false";
		}else if(uid != null && newDataName.equals(dataBatchService.getByUuid(UUID.UUIDFromString(uid)).getBatchName())){
			return "true";
		}else if(dataBatchService.getDataBatchByBatchName(newDataName)!=null){
			return "false";
		}
		return "true";
	}	
	
	/**
	 * index页面选择全部
	 * @param request
	 * @param response
	 * @param condition
	 * @param model
	 * @return
	 */
//	@RequestMapping("checkAll")
//	@ResponseBody
//	public String checkAll(HttpServletRequest request,
//			HttpServletResponse response, DataBatchCondition condition,
//			Model model) {
//
//		JSONObject jsonObject2 = new JSONObject();
//		condition.setRequest(request);
//		JSONArray jsonArray = new JSONArray(dbService.getDatas(condition));
//		jsonObject2.put("datas", jsonArray);
//		return jsonObject2.toString();
//	}
	
	/**
	 * 回收数据
	 */
	@RequestMapping("batRecoveryData")
	@ResponseBody
	public String batRecoveryData(HttpServletRequest request,Model model,String batchUuid){
		String[] dataIds = request.getParameterValues("uuids[]");
		dataBatchService.batRecoveryData(batchUuid, dataIds);
		return new JSONObject().put("success", true).toString();
	}
	
	
	
	@RequestMapping("batchStat")
	@ResponseBody
    public String dataStat(String dataBatchUuid, Model model){
		JSONObject json = new JSONObject(dataBatchService.getBatchStat(dataBatchUuid));
		return json.toString();
    }
	
	@RequestMapping("batchCollect")
	public String dataCollect(String dataBatchUuid, Model model){
		DataBatch dataBatch = dataBatchService.getByUuid(UUID.UUIDFromString(dataBatchUuid));
		model.addAttribute("entry", dataBatch);
		model.addAttribute("deptlist", dataBatchService.getBatchDepInfo(dataBatchUuid));
		return "datamanager/databatch-collection";
	}
	
	@RequestMapping("batchCollectData")
	@ResponseBody
	public String batchCollectData(String dataBatchUuid, String[] depts, Model model){
		dataBatchService.collectData(dataBatchUuid, depts);
		JSONObject json = new JSONObject().put("success", true);
		return json.toString();
	}
	
}


package com.ruishengtech.rscc.crm.ui.cstm.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.ruishengtech.framework.core.App;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.rscc.crm.cstm.condition.CstmPopLogCondition;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerCondition;
import com.ruishengtech.rscc.crm.cstm.cstmReport.ReportCstm;
import com.ruishengtech.rscc.crm.cstm.cstmReport.ReportCstmCondition;
import com.ruishengtech.rscc.crm.cstm.cstmReport.ReportCstmService;
import com.ruishengtech.rscc.crm.cstm.model.CstmLog;
import com.ruishengtech.rscc.crm.cstm.model.CstmPopLog;
import com.ruishengtech.rscc.crm.cstm.model.CstmReport;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.cstm.service.CstmLogService;
import com.ruishengtech.rscc.crm.cstm.service.CstmPhoneService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerDesignService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerTagLinkService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerTagService;
import com.ruishengtech.rscc.crm.cstm.service.imp.CstmPopLogService;
import com.ruishengtech.rscc.crm.cstm.solution.CustomerSolution;
import com.ruishengtech.rscc.crm.datamanager.model.UserDataLog;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchDataService;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.UserDataLogServiceImp;
import com.ruishengtech.rscc.crm.issue.model.CstmIssue;
import com.ruishengtech.rscc.crm.ui.ExportReportUtil;
import com.ruishengtech.rscc.crm.ui.IndexService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.cstm.service.CustomerSerialize;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.send.CallManager;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Frank
 *
 */
@Controller

@RequestMapping("cstm")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerTagService customerTagService;

	@Autowired
	private CustomerTagLinkService customerTagLinkService;

	@Autowired
	private CustomerPoolService customerPoolService;

	@Autowired
	private CustomerDesignService customerDesignService;

	@Autowired
	private CustomerSerialize customerSerialize;

	@Autowired
	private CstmPopLogService popLogService;

	@Autowired
	@Qualifier(value = "diyTableService")
	private DiyTableService divTableService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private DataBatchService databatchService;
	
	@Autowired
	private CstmLogService logService;

	@Autowired
	private CstmPhoneService phoneService;

	@Autowired
	private SysConfigService configService;

	@Autowired
	private CallLogService callLogService;
	
	@Autowired
	private UserDataLogServiceImp userDataLogService;
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private ReportCstmService cstmService;

	@Autowired
	private DataBatchDataService batchService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	//日志记录器
	private static final Logger logger = Logger.getLogger(CustomerController.class);
	
	/**
	 * 页面请求
	 * 
	 * @return
	 */
	@RequestMapping()
	public String toCstmIndex(Model model, HttpServletRequest request) {

		//是否隐藏号码
		indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());
		
		// 取到表头
		customerService.getTitles(model);

		//动态表要显示的表头
		customerService.getShowTitle(model);
		
		/* 获取所有不是默认的字段列 */
		Map<String, ColumnDesign> map = customerDesignService .getNotDefaultColumn();
		model.addAttribute("allMaps", map);

		/* 所有客户池 */
		List<CustomerPool> customerPools = customerPoolService.getAllPools();

		User user = SessionUtil.getCurrentUser(request);
		
		customerPools = customerService.getPermissionPools(request, customerPools, user);

		// 获取所有用户
		List<User> users = userService.getAllUser();
		model.addAttribute("user", users);
		
		//判断请求路径
		if(StringUtils.isNotBlank(App.getDateRange(request))){
			
			model.addAttribute("level", App.getDateRange(request));
		}else{
			
			model.addAttribute("level", "noagent");
		}
		
		//查询当前登录用户所能管辖的部门
		List<Datarange> dataranges = userService.getCurrentDataRange(SessionUtil.getCurrentUser(request).getUid());
		
		model.addAttribute("dataranges", dataranges);
		//所有客户池
		model.addAttribute("allpools", customerPools);
		//所有意向类型
		model.addAttribute("allIntent", customerPoolService.getAllIntends());
		model.addAttribute("iframecontent","cstm/cstm-index");

		return "iframe";
		
//		return "cstm/cstm-index";
	}


	/**
	 * 数据请求
	 * 
	 * @param request
	 * @param response
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, ColumnDesign> map = divTableService.getTableDescByName("cstm_customer").getClomns();
		String ownid = request.getParameter("own_id");
		if(request.getAttribute("own_id")==null){
			if(StringUtils.isNotBlank(ownid)){
				request.setAttribute("own_id", ownid);
			}else{
				request.setAttribute("own_id", SessionUtil.getCurrentUser(request).getUid());
			}
		}
		request.setAttribute("manageList", userService.getAllUsersByDeptId(request.getParameter("dataranges")));
		PageResult<Map> pageResult = customerService.queryPage(map, request);

		List<Map> customers = pageResult.getRet();

		// 获取默认设置
		String defVal = configService.getSysConfigByKey("addZero").getSysVal();

		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();

		for (int i = 0; i < customers.size(); i++) {

			JSONObject jsonObject2 = customerService.getJsonObject(customers.get(i));

			if (StringUtils.isBlank(customers.get(i).get("own_id")+"") || ("-1").equals(String.valueOf(customers.get(i).get("own_id")))) {
				jsonObject2.put("own_id", "暂无");
			} else {
				
				jsonObject2.put("own_id", userService.getByUuid(UUID.UUIDFromString(String.valueOf(customers.get(i).get("own_id")))).getUserDescribe());
			}

			//默认的是否是加0呼叫
			jsonObject2.put("defVal", defVal);

			array.put(jsonObject2);
		}
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords",pageResult.getiTotalDisplayRecords());

		// 获取默认设置
		jsonObject.put("defVal", configService.getSysConfigByKey("addZero").getSysVal());

		return jsonObject.toString();
	}

	/**
	 * 根据编号查询客户信息
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping("getTags")
	public String toTagsDialog(HttpServletRequest request,UUID uuid, String phone, String source, Model model,
			BindingResult bindingResult) {

		Map<String, ColumnDesign> map = customerDesignService.getAddedColumn();
		model.addAttribute("maps", map);

		/* 所有客户池 */
		List<CustomerPool> pools = customerPoolService.getAllPools();
		model.addAttribute("allPulls", pools);

		// 查询所有客户池信息
		List<CustomerPool> customerPools = customerPoolService.getAllPools();
		
		User user = SessionUtil.getCurrentUser(request);
 		customerPools = customerService.getPermissionPools(request, customerPools, user);
		
		model.addAttribute("pools", customerPools);
		
		//所有意向类型
		model.addAttribute("allIntent", customerPoolService.getAllIntends());
		
		Map<String, Object> customer = null;
		
		model.addAttribute("own_id", user.getUid());
	
		//获取客户信息并且放置值到
		customerService.getCustomerInfoAndSetPageValue(uuid, phone, model, customer);
		
		// 获取所有用户
		List<User> users = userService.getAllUser();
		model.addAttribute("user", users);

		model.addAttribute("serialized", customerSerialize.getCstmSerializeId());
		model.addAttribute("source", source);
		
		return "cstm/cstm-tags";
	}




	/**
	 * 保存或者是修改客户对象信息
	 * @param request
	 * @param response
	 * @param customerTags
	 * @param start_date
	 * @param cstm
	 * @param source
	 * @param bindingResult
	 * @param poptype
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("save")
	@ResponseBody
	public String saveTags(HttpServletRequest request, HttpServletResponse response, String customerTags,
			String start_date, Customer cstm,String source, String poptype , BindingResult bindingResult)
			throws Exception {

		/* 所有的参数和参数的值 */
		Map<String, String[]> map = request.getParameterMap();
		
		divTableService.releaseLock(map);
		
		//如果存在  为修改客户信息
		String uuid = request.getParameter("uid");
		if (StringUtils.isNotBlank(uuid)) {
			cstm.setUuid(UUID.UUIDFromString(uuid));
			cstm.setUid(uuid);
		}

		String cstm_uuid = null;
		String cstm_name = map.get("cstm_name")[0];
		String pool_id = map.get("pool_id")[0];

		// //删除关联表数据 如果有添加关联数据 没有 添加新的标签
		String[] aList = customerTags.split(",");
		List<String> tagList = Arrays.asList(aList);
		String cstmUuid = "";

		if(StringUtils.isBlank(map.get("status")[0])){
			map.put("status", new String[]{Customer.CUSTOMER_DEAL});
		}
		
		/* 修改 */
		if (null != cstm.getUuid() && "" != cstm.getUid()) {
			divTableService.update(map, Customer.class);

			Customer customer = customerService.getCustomerByUUID(uuid);

			// 判断号码是否变化
			customerTagLinkService.bindTagsToCustomer(cstm.getUid(), tagList);
			cstmUuid = cstm.getUid();
			// 记录 修改 操作日志 如果没有修改客户池 记录一条 如果客户池变化，总记录为三条
			getCstmLogRecord(request, uuid, cstm_name, pool_id, customer);
			
			if(!"task".equals(poptype)){
				//允许提交 符合条件 查找到对应的批次 改变拥有者
				batchService.updateDataBatchData(request, request.getParameter("phone_number"),cstmUuid);
				batchService.updateDataBatchData(request, request.getParameter("phone_number_2"),cstmUuid);
			}

		} else {
			
			//保存
			divTableService.save(map, Customer.class);

			cstm_uuid = customerService.getCstmUidByCstmSerilizeId(request.getParameter("customer_id"));
			
			cstmUuid = cstm_uuid;
			
			// 标签表添加标签 关联表插入数据
			customerTagLinkService.bindTagsForCustomer(cstm_uuid, tagList);

			logService.saveCustomerLogs(request, source, cstm_name, pool_id);
 
			if(!"task".equals(poptype)){
				//允许提交 符合条件 查找到对应的批次 改变拥有者
				batchService.updateDataBatchData(request, request.getParameter("phone_number"),cstmUuid);
				batchService.updateDataBatchData(request, request.getParameter("phone_number_2"),cstmUuid);
			}
			
		}
		
		/*保存第二个号码*/
		customerService.saveSecondNumber(request, cstm_uuid, cstmUuid);
		
		return new JSONObject().put("success", true).put("cstmUuid", cstmUuid).toString();
	}


	/**
	 * 丢弃
	 * 
	 * 改变主号码
	 * 
	 * @param phone
	 * @param model
	 * @return
	 */
	@RequestMapping("changePhone")
	@ResponseBody
	public String changePhone(String phone, Model model, String uid,
			String[] number, String pId) {

		//改变主号码
		customerService.changeMainNumber(phone, uid, number, pId);

		// 修改客户表主号码
		customerService.getChangeNumber(uid, phone);

		return new JSONObject().put("data", "success").toString();
	}

	/**
	 * 保存或者是修改一个客户对象
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("updata")
	@ResponseBody
	public String saveOrUpdataCustomer(HttpServletRequest request,
			HttpServletResponse response, Customer customer) {

		customerService.saveOrUpdate(customer);

		return new JSONObject().put("success", true).toString();
	}

	/**
	 * 检验客户编号不能重复
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @throws IOException
	 */
	@RequestMapping("cstmIdRepeat")
	@ResponseBody
	public void cstmIdRepeat(HttpServletRequest request,HttpServletResponse response, String customer_id) throws IOException {

		customerService.checkCstmIdRepeat(request, response, customer_id);
	}

	/**
	 * 检查电话号码是否重复
	 * 
	 * @param request
	 * @param phone
	 * @throws IOException
	 */
	@RequestMapping("checkPhoneRepeat")
	@ResponseBody
	public void checkPhoneRepeat(HttpServletRequest request, HttpServletResponse response, String number1, String number2) throws IOException {

		String phoneNumber = StringUtils.isNotBlank(number1) ? number1 : number2;

		customerService.checkPhoneRepeat(request, response, phoneNumber);

	}

	/*
	 * ===================================================管理员界面==================
	 */
	@RequestMapping("toAdminCstmIndex")
	public String toAdminCstmIndex(Model model, HttpServletRequest request) {

		// 查询所有部门
		List<CustomerPool> customerPools = customerPoolService.getAllPools();

		User user = SessionUtil.getCurrentUser(request);
		
		//是否隐藏号码
		indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());

		model.addAttribute("iframecontent","cstm/cstm-admin-index");
		return "iframe";

//		return "cstm/cstm-admin-index";
	}
//	@RequestMapping("toAdminCstmIndex")
//	public String toAdminCstmIndex(Model model, HttpServletRequest request) {
//		
//		// 查询所有部门
//		List<CustomerPool> customerPools = customerPoolService.getAllPools();
//		
//		User user = SessionUtil.getCurrentUser(request);
//		
//		//获取单个客户池的信息【标签信息统计 图标统计数据】
//		customerPoolService.getSinglePoolsInfo(model, request, customerPools, user);
//		
//		//是否隐藏号码
//		indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());
//		
//		model.addAttribute("iframecontent","cstm/Copy of cstm-admin-index");
//		return "iframe";
//		
////		return "cstm/Copy of cstm-admin-index";
//	}



	/**
	 * 根据客户池数据查询客户信息和客户统计
	 * 
	 * @param request
	 * @param response
	 * @param poolid
	 *            客户池编号
	 * @param model
	 * @return
	 */
	@RequestMapping("getCstmsByPoolId")
	public String getCstmsByPoolId(HttpServletRequest request,
			HttpServletResponse response, String poolid, Model model,
			RedirectAttributesModelMap modelMap) {

		modelMap.addFlashAttribute("pool_id", poolid);

		model.addAttribute("pool_id", poolid);

		/* 查询所有该客户池的所有操作日志 */
		List<CstmLog> cstmLogs = logService.getCstmLogsByUUID(poolid);
		model.addAttribute("logs", cstmLogs);
		model.addAttribute("XPool", "1");
		model.addAttribute("beExists", "1");
		
		return toCstmIndex(model, request);
	}

	/**
	 * 关于这个客户池的具体统计和操作日志信息
	 * 
	 * @param poolid
	 * @param model
	 * @return
	 */
	@RequestMapping("getCustomerInfoBypoolId")
	public String getCustomerInfoBypoolId(String poolId, Model model) {

		if (StringUtils.isNotBlank(poolId)) {

			List<CstmLog> cstmLogs = logService.getCstmLogsByUUID(poolId);
			model.addAttribute("logs", cstmLogs);
			/* 是否展示客户池 */

			model.addAttribute("poolUUID", poolId);

			CustomerPool customerPool = customerPoolService.getCustomerPool(UUID.UUIDFromString(poolId));
			model.addAttribute("pool", customerPool);

			List<CstmReport> customerPools = customerPoolService.getPoolReport(poolId);

			if (customerPools.size() > 0) {

				JSONArray array = new JSONArray(customerPools);
				model.addAttribute("reportDate", array);
			}

			// 得到本客户池中标签个数前10的标签
			List<CstmReport> list = customerService.getTopNumberTags(customerPool.getUid(), "5");
			model.addAttribute("counts",customerPoolService.getAllTags(customerPool.getUid()));

			model.addAttribute("tagList", list);

			List<String> list2 = customerService.getAllTags();

			model.addAttribute("tagSize", list2);
		}
		return "cstm/cstm-main-content";
	}

	/**
	 * 全选所有
	 * 
	 * @return
	 */
	@RequestMapping("checkAll")
	@ResponseBody
	public String checkAll(HttpServletRequest request) {

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		List<String> userUuid = userService.getDepartments(SessionUtil
				.getCurrentUser(request).getUid());
		Set<String> set = customerPoolService.getPoolsByDepartments(userUuid);

		for (String i : set) {
			jsonArray.put(i);
		}

		jsonObject2.put("customers", jsonArray);
		return jsonObject2.toString();
	}

	/**
	 * 删除客户
	 * 
	 * 成交客户的删除
	 * 
	 * 1、先根据电话号码从对应资源中拿到该资源信息，清空所属客户信息；
	 * 
	 * 2、在根据资源信息拿到对应批次信息，将批次中的数据回归初始值
	 * 
	 * DataBatchData.java
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String deleteCustomer(HttpServletRequest request,
			HttpServletResponse response, String uuid) {

		Customer customer = customerService.getCustomerByUUID(uuid);

		CstmLog cstmLog = new CstmLog();
		cstmLog.setOptUser(SessionUtil.getCurrentUser(request).getLoginName());
		cstmLog.setOptUserUUID(SessionUtil.getCurrentUser(request).getUid());
		cstmLog.setOptDate(new Date());
		cstmLog.setOptObjUUID(customer.getPoolId());
		cstmLog.setOptObj(customer.getCustomerName());
		cstmLog.setOptAction("删除了一个名为" + customer.getCustomerName() + "的客户");
		cstmLog.setObjPool(customer.getPoolId());
		cstmLog.setOptCount(-1);
		cstmLog.setOptType(CstmLog.OPT3);

		/* 添加删除日志 */
		logService.saveLog(cstmLog);
		
		/* 添加数据统计log （new_data_department_user_log）*/
		UserDataLog userDataLog = new UserDataLog();
		userDataLog.setOp_time(new Date());
		userDataLog.setOld_stat("customer");
		userDataLog.setOp_user(customer.getOwnId());
		//根据号码查询批次
		userDataLog.setBatch_uuid(databatchService.getPhoneBatch(customer.getPhoneNumber()));
		userDataLog.setPhone_number(customer.getPhoneNumber());
		userDataLog.setOp_type("deletecstm");
		userDataLogService.save(userDataLog);

		customerService.deleteCustomerByUUid(UUID.UUIDFromString(uuid),customer);

		return new JSONObject().put("success", true).toString();
	}

	/**
	 * 获取删除的信息
	 * 
	 * @return
	 */
	@RequestMapping("getDeleteInfo")
	@ResponseBody
	public String getDeleteInfo(HttpServletRequest request, String tag) {

		String[] customerId = request.getParameterValues("customers[]");

		Customer customer = customerService.getCustomerByUUID(customerId[0]);

		Integer num = 0;
		if ("1".equals(tag)) {

			num = customerService.batchDelete(customerId);

			CstmLog cstmLog = new CstmLog();
			cstmLog.setOptUser(SessionUtil.getCurrentUser(request)
					.getLoginName());
			cstmLog.setOptUserUUID(SessionUtil.getCurrentUser(request).getUid());
			cstmLog.setOptDate(new Date());
			cstmLog.setOptObjUUID(customer.getPoolId());
			cstmLog.setOptObj(customer.getCustomerName());
			cstmLog.setOptAction("删除了包含" + customer.getCustomerName() + "在内的"
					+ num + "个客户");
			cstmLog.setObjPool(customer.getPoolId());
			cstmLog.setOptType(CstmLog.OPT3);
			cstmLog.setOptCount(-num);

			/* 添加删除日志 */
			logService.saveLog(cstmLog);
		}

		// 返回受影响的行数
		return new JSONObject().put("success", true).put("name", customer.getCustomerName()).put("count", customerId.length).put("num", num).toString();

	}

	/**
	 * 
	 * 移动客户池
	 * 
	 * @param request
	 * @param tag
	 * @param poolName
	 *            目标客户池
	 * @return
	 */
	@RequestMapping("getMoveInfo")
	@ResponseBody
	public String getMoveInfo(HttpServletRequest request, String tag,
			String poolName) {

		String[] customerId = request.getParameterValues("customers[]");
		Customer customer = customerService.getCustomerByUUID(customerId[0]);

		// 查询所有客户池
		List<CustomerPool> customerPools = customerPoolService.getAllPool();

		User user = SessionUtil.getCurrentUser(request);
		
		customerPools = customerService.getNormalUserPools(request, customerPools, user);
		
		List<String> poolsNames = new ArrayList<String>();
		for (int i = 0; i < customerPools.size(); i++) {
			poolsNames.add(customerPools.get(i).getPoolName());
		}

		Integer num = 0;
		if ("1".equals(tag)) {

			CustomerPool customerPool = null;

			if (StringUtils.isNotBlank(poolName)) {

				// 移入的客户池
				customerPool = customerPoolService.getPoolByName(poolName);

				// 来自哪个客户池
				String poolNames = customerPoolService.getCustomerPool(UUID.UUIDFromString(customer.getPoolId())).getPoolName();

				num = getCstmLogRecord(request, poolName, customerId, customer, num, customerPool, poolNames);
			}
		}

		// 返回受影响的行数
		return new JSONObject().put("success", true).put("poolNames", poolsNames).put("name", customer.getCustomerName()).put("count", customerId.length).put("num", num).toString();

	}
	
	
	/**
	 * 批量修改
	 * @param request
	 * @param ownerId
	 * @param tag
	 * @param customers
	 * @return
	 */
	@RequestMapping("batchUpdate")
	@ResponseBody
	public String batchUpdate(HttpServletRequest request ,HttpServletResponse response,Model model,String ownerId,String tag,String customers){
		

		if("0".equals(tag)){
			
			List<User> array =  userService.getAllUser();
			List<String> alluserNames = new ArrayList<String>();
			for (int i = 0; i < array.size(); i++) {
				
				alluserNames.add(array.get(i).getUserDescribe());
			}
			model.addAttribute("user", array);
			return new JSONObject().put("success", true).put("user", alluserNames).
					put("name", customerService.getCustomerByUUID(customers.split(",")[0]).getCustomerName()).put("count", customers.split(",").length).toString();
		}
		
		Integer affectNumber = customerService.batchUpdateToUser(customers.split(","),ownerId);
		
		return new JSONObject().put("success", true).put("affectNumber", affectNumber).toString();
	}
	
	@RequestMapping("owner")
	public String owner(HttpServletRequest request,HttpServletResponse response,Model model,String cstmids){
		model.addAttribute("cstmids", cstmids);
		// 获取所有用户
		List<User> users = userService.getAllUser();
		model.addAttribute("user", users);
		return "cstm/owner";
	}
	/**
	 * 批量修改客户拥有者
	 * @param request
	 * @param response
	 * @param ownUser
	 * @param cstmids
	 * @return
	 */
	@RequestMapping("saveBatChange")
	@ResponseBody
	public String saveBatChange(HttpServletRequest request,HttpServletResponse response,String ownUser,String cstmids){
		
		customerService.batchUpdateToUser(cstmids.split(","), ownUser);
		
		return new JSONObject().put("success", true).toString();
	}

	public Integer getCstmLogRecord(HttpServletRequest request,
			String poolName, String[] customerId, Customer customer,
			Integer num, CustomerPool customerPool, String poolNames) {
		if (null != customerPool) {

			// 移动客户到指定客户池
			num = customerService.batchMove(request, customerId,customerPool.getUid());

			getCstmLogRecord(request, poolName, customer, num, customerPool,poolNames);

		}
		return num;
	}

	/**
	 * 
	 * 弹屏跳转 customerinfo 页面
	 * 根据编号或者电话查询客户信息
	 * @param params
	 * @return
	 */
	@RequestMapping("getCustomerInfoByPhone")
	public String getDataByIdOrPhone(String params, Model model, HttpServletRequest request) {
		
		//查询是否隐藏号码
		String hiddenPhoneNumber = configService.getSysConfigByKey("hiddenPhoneNumber").getSysVal();
		if(StringUtils.isNotBlank(hiddenPhoneNumber) && ("true").equals(hiddenPhoneNumber)){ //需要隐藏号码
			model.addAttribute("hidePhone", "hide");
		}
		
		// 先判断是否存在这个客户
		Customer customer2 = customerService.getCustomerInfoByPhone(params);

		Map<String, Object> customers = new HashMap<String, Object>();
		/* 如果客户存在 */
		if (null != customer2) {

			// title信息展示
			boolean b = userService.hasPermission(SessionUtil.getCurrentUser(request).getUid(), "90");
			if("true".equals(SysConfigManager.getInstance().getDataMap().get("hiddenPhoneNumber").getSysVal()) && !b){
				model.addAttribute("titleContent", PhoneUtil.hideNumber(customer2.getPhoneNumber()));
			}else{
				model.addAttribute("titleContent", customer2.getPhoneNumber());
			}

			customers = customerService.getCustomerMapByPhone(params);
			/* 如果客户不存在 保存客户基本信息 */
		} else {

			// 存放电话号码和产生编号
			model.addAttribute("newPhoneNumber", params);
			model.addAttribute("customer_id",customerSerialize.getCstmSerializeId());
			model.addAttribute("saveInfo", "保存为客户");
			model.addAttribute("cDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

			// title信息展示
//			model.addAttribute("titleContent", PhoneUtil.hideNumber(params));
			
			boolean b = userService.hasPermission(SessionUtil.getCurrentUser(request).getUid(), "90");
			if("true".equals(SysConfigManager.getInstance().getDataMap().get("hiddenPhoneNumber").getSysVal()) && !b){
				model.addAttribute("titleContent", PhoneUtil.hideNumber(params));
			}else{
				model.addAttribute("titleContent", params);
			}
			
		}

		/*---------- 放值 ---------*/
		/* 得到所有列 */
		Map<String, ColumnDesign> map = customerDesignService.getAddedColumn();
		model.addAttribute("maps", map);

		// 查询所有客户池信息
		List<CustomerPool> customerPools = customerPoolService.getAllPools();

		User user1 = SessionUtil.getCurrentUser(request);

		customerPools = customerService.getPermissionPools(request, customerPools, user1);

		model.addAttribute("pools", customerPools);

		//所有意向类型
		model.addAttribute("allIntent", customerPoolService.getAllIntends());
		
		String customeruid = (String) customers.get("uuid");
		if (StringUtils.isNotBlank(customeruid)) {

			model.addAttribute("pool_id", customers.get("pool_id"));

			//如果有归属人 显示当前归属人 
			if(customers.get("own_id") != null && StringUtils.isNotBlank(String.valueOf(customers.get("own_id")))){
				
				model.addAttribute("own_id", customers.get("own_id"));
				model.addAttribute("own_name", userService.getByUuid(UUID.UUIDFromString(customers.get("own_id").toString())).getLoginName());
			}else{//如果没有归属人 默认显示当前用户
				model.addAttribute("own_id", SessionUtil.getCurrentUser(request).getUid());
				model.addAttribute("own_name", SessionUtil.getCurrentUser(request).getLoginName());
			}
			model.addAttribute("customerType", customers.get("status"));
			// 存放tag
			List<String> tagsStr = customerTagService.getCustomerTags(customeruid);
			model.addAttribute("tagsStr",tagsStr.size() > 0 ? tagsStr.toString() : "");
			// 存放客户信息
			model.addAttribute("Acstm", customers);
			model.addAttribute("uid", customers.get("uuid").toString());

			// 查询第二号码
			String cstmPhone = phoneService.getAllCstmPhones(customers.get("phone_number") + "");
			if (null != cstmPhone) {

				model.addAttribute("secPhone", cstmPhone);
			}
			// 如果不存在客户，默认是当前用户为所属用户
		} else {

			model.addAttribute("own_id", SessionUtil.getCurrentUser(request).getUid());
			model.addAttribute("own_name", SessionUtil.getCurrentUser(request).getLoginName());
		}
		
		// 获取所有用户
		List<User> user = userService.getAllUser();
		model.addAttribute("user", user);

		Map<String, ColumnDesign> mapss = customerDesignService.getAddedColumn("sys_call_log");
		model.addAttribute("mapss", mapss);
		
		// 所有业务逻辑
		model.addAttribute("STATUSMAP", CstmIssue.STATUSMAP);

		//是否隐藏号码
		indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());
		
		return "screenpop/customerinfo";

	}

	/* 弹屏记录 */
	@RequestMapping("poplog")
	public String getSmallIssue(Model model) {

		JSONObject jsonObject =  callLogService.getTitleAndData(divTableService);
		divTableService.getTitles(model, jsonObject); 
		
		/* 得到所有列 */
		Map<String, ColumnDesign> mapss = customerDesignService.getAddedColumn("sys_call_log");
		model.addAttribute("dataRows", mapss);
		
		return "screenpop/mod-cstm-pop-log";
	}

	/* 弹屏记录 */
	@RequestMapping("poplogdata")
	@ResponseBody
	public String getSmallIssueData(HttpServletRequest request,
			HttpServletResponse response, CstmPopLogCondition logCondition) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		logCondition.setRequest(request);
		// 显示控制
		logCondition.setAgentId(SessionUtil.getCurrentUser(request).getUid());

		PageResult<CstmPopLog> pageResult = popLogService
				.queryPage(logCondition);

		List<CstmPopLog> customers = pageResult.getRet();
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < customers.size(); i++) {
			JSONObject jsonObject2 = new JSONObject(customers.get(i));

			jsonObject2.put("date",
					dateFormat.format(customers.get(i).getDate()));
			jsonObject2.put("textLog", customers.get(i).getTextLog());

			array.put(jsonObject2);
		}

		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords",
				pageResult.getiTotalDisplayRecords());
		return jsonObject.toString();
	}

	/**
	 * 根据号码或者是编号查询信息弹框
	 * 
	 * @param params
	 *            电话号码或者是编号
	 * @return
	 */
	@RequestMapping("checkExists")
	@ResponseBody
	public String getByPhoneOrId(String params, HttpServletRequest request) {

		Customer customer = customerService.getCustomerByPhoneOrNumber(params);

		if (null != customer) {

			// 如果是管理员，直接返回
			if (("1").equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {

				return new JSONObject().put("success", true).toString();
			}
			if (!customer.getOwnId().equals(SessionUtil.getCurrentUser(request).getUid())) {

				return new JSONObject().put("success", false)
						.put("error", "你没有权限查看该客户相关信息！").toString();
			}

			return new JSONObject().put("success", true).toString();
		}

		return new JSONObject().put("success", false)
				.put("error", "没有找到和 " + params + " 相关信息").toString();

	}

	@RequestMapping("makeCall")
	@ResponseBody
	public String makeCall(HttpServletRequest request, String number,
			String status) {

		
		//查询电话号码
		if ("on".equals(status)) {
			number = "0" + number;
		}

		JSONObject jsonObject = CallManager.makeCall(SessionUtil.getSession(request, SessionUtil.LOGINEXTEN).toString(), number, SessionUtil.getCurrentUser(request), "single_call");

		CallLog callLog = new CallLog();
		callLog.setAgent_id(SessionUtil.getCurrentUser(request).getUid());
		callLog.setAgent_name(SessionUtil.getCurrentUser(request).getLoginName());
		callLog.setCall_phone(number);
		callLog.setIn_out_flag("呼出");
		callLog.setCall_time(new Date());
		callLog.setCall_session_uuid(jsonObject.optString("call_session_uuid"));
		callLogService.save(callLog);

		return new JSONObject().put("exit_code", jsonObject.opt("exit_code")).put("call_session_uuid",jsonObject.optString("call_session_uuid")).toString();
	}

	/**
	 * 
	 * @param phone
	 *            电话号码
	 * @return
	 */
	@RequestMapping("callByNow")
	@ResponseBody
	public String callByNow(HttpServletRequest request, String phone) {

		if (StringUtils.isNotBlank(phone)) {
			
			if(null == SessionUtil .getSession(request, SessionUtil.LOGINEXTEN)){
				
				return new JSONObject().put("success", false).put("msg", "请先绑定分机！").toString();
			}
			
			JSONObject jsonObject = CallManager.makeCall(SessionUtil .getSession(request, SessionUtil.LOGINEXTEN).toString(),
					phone, SessionUtil.getCurrentUser(request), "single_call");

			if ("0".equals(jsonObject.optString("exit_code"))) {

				CallLog callLog = new CallLog();
				callLog.setAgent_id(SessionUtil.getCurrentUser(request).getUid());
				callLog.setAgent_name(SessionUtil.getCurrentUser(request).getLoginName());
				callLog.setCall_phone(phone);
				callLog.setIn_out_flag("呼出");
				callLog.setCall_time(new Date());
				callLog.setCall_session_uuid(jsonObject.optString("call_session_uuid"));
				callLogService.save(callLog);

				return new JSONObject().put("success", true).put("exit_code", jsonObject.opt("exit_code")).put("call_session_uuid",
								jsonObject.optString("call_session_uuid")).toString();
			} else {

				return new JSONObject().put("success", false).put("msg", "呼叫错误！").toString();
			}
		}

		return null;
	}

	/**
	 * 日志记录
	 * 
	 * @param request
	 * @param uuid
	 * @param cstm_name
	 * @param pool_id
	 * @param customer
	 */
	public void getCstmLogRecord(HttpServletRequest request, String uuid,
			String cstm_name, String pool_id, Customer customer) {
		CstmLog cstmLog = new CstmLog();
		cstmLog.setOptUser(SessionUtil.getCurrentUser(request).getLoginName());
		cstmLog.setOptUserUUID(SessionUtil.getCurrentUser(request).getUid());
		cstmLog.setOptDate(new Date());
		cstmLog.setOptObjUUID(uuid);
		cstmLog.setOptObj(cstm_name);
		cstmLog.setOptAction("修改了一个名为 <b>" + cstm_name + "</b> 的客户信息");
		cstmLog.setObjPool(pool_id);
		cstmLog.setOptType(CstmLog.OPT2);
		cstmLog.setOptCount(0);
		
		logService.saveLog(cstmLog);

		// 判断原来的客户池和现在是否一样，如果不一样，做记录
		if (!customer.getPoolId().equals(pool_id)) {

			// 记录日志 移除日志和移入日志
			CstmLog cstmLogIn = new CstmLog();
			cstmLogIn.setOptUser(SessionUtil.getCurrentUser(request)
					.getLoginName());
			cstmLogIn.setOptUserUUID(SessionUtil.getCurrentUser(request)
					.getUid());
			cstmLogIn.setOptDate(new Date());
			cstmLogIn.setOptObjUUID(uuid);
			cstmLogIn.setOptObj(cstm_name);
			cstmLogIn.setOptAction("修改了一个名为 <b>" + cstm_name + "</b> 的客户信息");
			cstmLogIn.setObjPool(pool_id);
			cstmLogIn.setOptType(CstmLog.OPT2);
			cstmLogIn.setOptCount(1);
			logService.saveLog(cstmLogIn);

			// 记录日志 移除日志和移入日志
			CstmLog cstmLogOut = new CstmLog();
			cstmLogOut.setOptUser(SessionUtil.getCurrentUser(request)
					.getLoginName());
			cstmLogOut.setOptUserUUID(SessionUtil.getCurrentUser(request)
					.getUid());
			cstmLogOut.setOptDate(new Date());
			cstmLogOut.setOptObjUUID(uuid);
			cstmLogOut.setOptObj(cstm_name);
			cstmLogOut.setOptAction("修改了一个名为 <b>" + cstm_name + "</b> 的客户信息");
			cstmLogOut.setObjPool(customer.getPoolId());
			cstmLogOut.setOptType(CstmLog.OPT2);
			cstmLogOut.setOptCount(-1);
			logService.saveLog(cstmLogOut);
		}
	}

	/**
	 * 
	 * 批量移动客户日志记录
	 * 
	 * @param request
	 * @param poolName
	 * @param customer
	 * @param num
	 * @param customerPool
	 * @param poolNames
	 */
	public void getCstmLogRecord(HttpServletRequest request, String poolName,
			Customer customer, Integer num, CustomerPool customerPool,
			String poolNames) {
		CstmLog cstmLog = new CstmLog();
		cstmLog.setOptUser(SessionUtil.getCurrentUser(request).getLoginName());
		cstmLog.setOptUserUUID(SessionUtil.getCurrentUser(request).getUid());
		cstmLog.setOptDate(new Date());
		cstmLog.setOptObjUUID(customerPool.getUid());
		cstmLog.setOptObj(customerPool.getPoolName());
		cstmLog.setOptAction("移进了包含" + customer.getCustomerName() + "在内的" + num + "个客户到" + poolName + "中");
		cstmLog.setObjPool(customerPool.getUid());
		cstmLog.setOptType(CstmLog.OPT_1);
		cstmLog.setOptCount(num);

		/* 记录日志 */
		logService.saveLog(cstmLog);

		CstmLog newCstmLog = new CstmLog();
		newCstmLog.setOptUser(SessionUtil.getCurrentUser(request)
				.getLoginName());
		newCstmLog.setOptUserUUID(SessionUtil.getCurrentUser(request).getUid());
		newCstmLog.setOptDate(new Date());
		newCstmLog.setOptObjUUID(customer.getPoolId());
		newCstmLog.setOptObj(poolNames);
		newCstmLog.setOptAction("移除了包含" + customer.getCustomerName() + "在内的" + num + "个客户到" + poolName + "中");
		newCstmLog.setObjPool(customer.getPoolId());
		newCstmLog.setOptType(CstmLog.OPT_3);
		newCstmLog.setOptCount(-num);

		/* 记录日志 */
		logService.saveLog(newCstmLog);
	}
	/**
	 * 导出数据
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("export")
	@ResponseBody
	public void exportData(HttpServletRequest request,HttpServletResponse response,CustomerSolution solution,CustomerCondition cstmCondition) throws IOException{
		ExportReportUtil excel = new ExportReportUtil();
		Map<String, ColumnDesign> map = customerDesignService.getAllColumns("cstm_customer");
		List<Map<String, Object>> values = excel.getDiyResult(map, request, solution);
		
		List<String> ch_list = new ArrayList<String>();
		List<String> en_list = new ArrayList<String>();
		
		Iterator<Entry<String, ColumnDesign>> entries = map.entrySet().iterator();
		//按照顺序查询自定义表中的字段信息
		while (entries.hasNext()) {  
		    Entry<String, ColumnDesign> entry = entries.next();  
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().getColumnName());  
		    en_list.add(entry.getKey());
		    ch_list.add(entry.getValue().getColumnName());
		}  

		en_list.add("customerTags");
		ch_list.add("客户标签");
		
		
//		String[] title = new String[9];
//		title[0]="客户编号";
//		title[1]="客户姓名";
//		title[2]="客户池";
//		title[3]="标记时间";
//		title[4]="客户描述";
//		title[5]="拥有者";
//		title[6]="电话号码";
//		title[7]="字符串";
//		title[8]="客户标签";
//		String[] key = new String[9];
//		key[0] = "customer_id";
//		key[1] = "cstm_name";
//		key[2] = "pool_id";
//		key[3] = "start_date";
//		key[4] = "customer_des";
//		key[5] = "own_id";
//		key[6] = "phone_number";
//		key[7] = "col_43";
//		key[8] = "tag_name";
		
//		excel.createExcel("客户信息表.xls", title, key, cstmCondition, values, response);
		excel.createExcel("客户信息表.xls", ch_list, en_list, values, response);

	}
	
	
	/**
	 * 报表页面跳转
	 * @return
	 */
	@RequestMapping("report")
	public String cstmReport(){
		
		return "cstm/cstm-report";
	}
	
	/**
	 * 获取报表数据
	 * @param request
	 * @param response
	 * @param condition
	 * @return
	 * @throws ParseException 
	 * @throws JSONException 
	 */
	@RequestMapping("reportData")
	@ResponseBody
	public String getReportData(HttpServletRequest request , HttpServletResponse response,ReportCstmCondition cstmCondition) throws JSONException, ParseException{
		
		cstmCondition.setRequest(request);
		PageResult<ReportCstm> pageResult = cstmService.queryPage(cstmCondition);
		List<ReportCstm> reportList = pageResult.getRet();
		
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		for (int i = 0; i < reportList.size(); i++) {
			
			JSONObject jsonObject2 = new JSONObject(reportList.get(i));
//			jsonObject2.put("opt_date", dateFormat.format(sdf1.parse(reportList.get(i).getOpt_date())));
			jsonObject2.put("opt_date", (reportList.get(i).getOpt_date()));
			array.put(jsonObject2);
		}
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	
	/**
	 * @return
	 */
	@RequestMapping("getCustomerInfoByCustomerId")
	public String getCustomerInfoByCustomerId(String customerId,HttpServletRequest request,Model model){
		
		Map<String, ColumnDesign> map = customerDesignService.getAddedColumn();
		model.addAttribute("maps", map);
		
		Map<String, Object> customer = customerService.getCustomerMapByPhone(customerId);
		model.addAttribute("Acstm", customer);
		
		return "cstm/cstm-info";
	}
	
	/**
	 * 判断是否是成交客户
	 * @param request
	 * @param response
	 * @param phone
	 * @param customerId
	 * @throws IOException
	 */
	@RequestMapping("beCustomer")
	@ResponseBody
	public String beCustomer(HttpServletRequest request , HttpServletResponse response ,String phone , String customerId) throws IOException{
		
		String retStatement;
		if(StringUtils.isNoneBlank(phone,customerId)){
			
			retStatement = customerService.beCustomer(phone,customerId);
			
			if(StringUtils.isNotBlank(retStatement)){
				
				return new JSONObject().put("success", true).toString();
			}
		}
		
		return new JSONObject().put("success", false).toString();
	}
	
}

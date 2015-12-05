package com.ruishengtech.rscc.crm.ui.cstm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerPoolCondition;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Frank
 *
 */
@Controller
@RequestMapping("cstmpool")
public class CustomerPoolController {

	@Autowired
	private CustomerPoolService customerPoolService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private DatarangeService datarangeService;
	
	
	@RequestMapping
	public String index(Model model){
		
		
		model.addAttribute("iframecontent","cstm/cstm-pool-index");
		return "iframe";
		
//		return "cstm/cstm-pool-index";
	}
	
	/**
	 * 返回请求数据
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request , HttpServletResponse response, CustomerPoolCondition customerPoolCondition){
		
		customerPoolCondition.setRequest(request);
		customerPoolCondition.setCreater(SessionUtil.getCurrentUser(request).getUid());
		
		List<String> userUuid = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
		Set<String> set = customerPoolService.getPoolsByDepartments(userUuid);
		customerPoolCondition.setIns(set);
		PageResult<CustomerPool> pageResult = customerPoolService.queryPage(customerPoolCondition);
		
		List<CustomerPool> customerpools = pageResult.getRet();

		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();

//		DateFormatUtils.ISO_DATE_FORMAT.format(date)；
		for (int i = 0; i < customerpools.size(); i++) {
			
			JSONObject json = new JSONObject(customerpools.get(i));
			
			json.put("poolDes", StringUtils.isBlank(customerpools.get(i).getPoolDes())?"":customerpools.get(i).getPoolDes());
			json.put("createTime", DateFormatUtils.ISO_DATE_FORMAT.format(customerpools.get(i).getCreateTime())+" "+DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(customerpools.get(i).getCreateTime()));

			array.put(json);
		}

		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	/**
	 * 查询单个对象
	 * @return
	 */
	@RequestMapping("get")
	public String get(HttpServletRequest request ,String uid ,Model model){
		
		List<Datarange> l = null;

		if(StringUtils.isNoneBlank(uid)){
			
			CustomerPool customerPool =  customerPoolService.getCustomerPool(UUID.UUIDFromString(uid));
			model.addAttribute("customerpool", customerPool);
			
			//根据池获取管辖的部门 中间表查询
			List<String> selectList =  customerPoolService.getDepartmentsByPool(customerPool.getUid());
			model.addAttribute("selectedList", selectList);
		}
		
		//查询该坐席所属角色能管辖的部门
		if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
			l = datarangeService.getAllDatarange();
		} else {

			List<String> list = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
			l = datarangeService.getDataranges(list);
			
		}
		model.addAttribute("departments", l);
		
		return "cstm/cstm-pool-dialog";
	}
	
	
	/**
	 * 保存和修改
	 * @param request
	 * @param response
	 * @param customerPool
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request , HttpServletResponse response , CustomerPool customerPool,String obj){
		//设置未选中的部门为空
		String[] undepartments = new String[]{};
		//获取全部的部门，使客户池绑定全部部门
		List<Datarange> s =datarangeService.getAllDatarange();
//		Object[] deptobj = s.toArray();
		String[] departments = new String[s.size()];
		for (int i = 0; i < s.size(); i++) {
			 departments[i]=s.get(i).getUid();
		}
		 //修改
		if(StringUtils.isNotBlank(obj)){
			JSONObject jsonObject = new JSONObject(obj);
			
			List<String> list = new ArrayList<>(jsonObject.keySet());
			for(String uid : list){
				//uid为空和默认批次不让修改
				if(StringUtils.isNotBlank(uid) && !"de001".equals(uid)){
					CustomerPool cstmPool = customerPoolService.getCustomerPool(UUID.UUIDFromString(uid));
					cstmPool.setPoolName(jsonObject.getJSONObject(uid).getString("name"));
					cstmPool.setPoolDes(jsonObject.getJSONObject(uid).getString("des"));
					customerPoolService.update(departments,cstmPool);
				}
			}
			return new JSONObject().put("success", true).toString();
		//添加
		}else{
			customerPool.setBeDefault("0");
			customerPool.setCreater(SessionUtil.getCurrentUser(request).getUid());
			customerPool.setCreateTime(new Date());
			customerPoolService.save(departments,customerPool);
		}
		
		return new JSONObject().put("success", true).put("uuid", customerPool.getUid()).toString();
	}
	
	
	/**
	 * 查询客户池信息
	 * @param poolId
	 * @return
	 */
	@RequestMapping("getCstmsByPool")
	public String getCstmsByPool(HttpServletRequest request , String poolId,Model model){
		
		List<Datarange> l = null;
		
		if(StringUtils.isNoneBlank(poolId)){
			
			CustomerPool customerPool =  customerPoolService.getCustomerPool(UUID.UUIDFromString(poolId));
			model.addAttribute("p", customerPool);
			
			//根据池获取管辖的部门 中间表查询
			List<String> selectList =  customerPoolService.getDepartmentsByPool(customerPool.getUid());
			model.addAttribute("selectedList", selectList);
			
			//查询该坐席所属角色能管辖的部门
			if("1".equals(SessionUtil.getCurrentUser(request).getAdminFlag())) {
				l = datarangeService.getAllDatarange();
			} else {

				List<String> list = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());
				l = datarangeService.getDataranges(list);
				
			}
			model.addAttribute("departments", l);
			
			return "cstm/cstm-pool-content";
			
			
			
		}
		
		return null;
	}
	
	/**
	 * 根据池编号查询池中客户信息
	 * @param poolId
	 * @return
	 */
	@RequestMapping("allPools")
	@ResponseBody
	public String getAllPools(String poolId){
		
		List<CustomerPool> customerPools = customerPoolService.getAllPool();
		List<Customer> customers = customerPoolService.getCustomersByPool(poolId);
		
		CustomerPool customerPool = customerPoolService.getCustomerPool(UUID.UUIDFromString(poolId));
		
		//如果存在客户
		if(null != customers){
			
			return new JSONObject().put("success", true).put("pools", customerPools).put("po", customerPool.getPoolName()).toString();
		}else{
			
			return new JSONObject().put("success", false).toString();
		}
		
	}
	
	
	/**
	 * 修改客户的客户池----删除客户池
	 * 
	 * @param poolName 要移动到的客户池名称
	 * @param poolId 要删除的客户池
	 * @return
	 */
	@RequestMapping("changePool")
	@ResponseBody
	public String changePool(String poolName,String poolId){
		
		if(StringUtils.isNotBlank(poolName)){
			
			//根据客户池查询客户池信息
			CustomerPool customerPool = customerPoolService.getPoolByName(poolName);
			if(null != customerPool){
				
				//移动客户到指定客户池 并删除客户池
 				customerPoolService.CustomerPoolchangePoolUsers(poolId, customerPool.getUid());
				
 				return new JSONObject().put("success", true).toString();
			}else{
				return new JSONObject().put("success", false).toString();
			}
		}
		
		return new JSONObject().put("success", false).toString();
	}
	
	/**
	 * 
	 * 删除空的客户池
	 * @param poolId
	 * @return
	 */
	@RequestMapping("deletepool")
	@ResponseBody
	public String deletePool(String poolId){

		if(StringUtils.isNotBlank(poolId)){
			
			List<Customer> customers = customerPoolService.getCustomersByPool(poolId);
			//如果不存在客户 不删除 直接返回
			if(null != customers){
				
				return new JSONObject().put("success", false).toString();
			}
			
			customerPoolService.deletePoolByUUId(UUID.UUIDFromString(poolId));
			return new JSONObject().put("success", true).put("uuid", poolId).toString();
		}
		return new JSONObject().put("success", false).put("uuid", poolId).toString();
	}
	
	
	@RequestMapping("checkPoolNameRepeat")
	public void checkPoolNameRepeat(HttpServletRequest request,HttpServletResponse response,String poolName) throws IOException{
		
		CustomerPool customerPool = customerPoolService.getPoolByName(poolName);
		
		customerPoolService.checkPoolNameRepeat(request, response, poolName, customerPool);
		
	}

	
}

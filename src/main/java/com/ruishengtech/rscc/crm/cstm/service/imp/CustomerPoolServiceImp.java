package com.ruishengtech.rscc.crm.cstm.service.imp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.CollectionUtil;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerPoolCondition;
import com.ruishengtech.rscc.crm.cstm.model.CstmLog;
import com.ruishengtech.rscc.crm.cstm.model.CstmReport;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.cstm.service.CstmLogService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.cstm.solution.CustomerPoolSolution;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.user.model.User;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class CustomerPoolServiceImp extends BaseService implements
		CustomerPoolService {

	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CstmLogService logService;
	
	/**
	 * 客户池map
	 */
	public Map<String, CustomerPool> cstompool = new HashMap<String, CustomerPool>();
	
	/**
	 * 加载所有客户池数据
	 */
	public void loadCustomerPools(){
		
		 List<CustomerPool> customerPools = super.getAll(CustomerPool.class);
		 cstompool.clear();
		 for (CustomerPool customerPool : customerPools) {
			 cstompool.put(customerPool.getUid(), customerPool);
		}
	}
	
	/**
	 * 取得所有客户池数据
	 * @return
	 */
	public Map<String, CustomerPool> getAllCustomerPools(){
		
		return cstompool;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruishengtech.rscc.crm.cstm.service.ICustomerPool#getCustomersByPool
	 * (java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getCustomersByPool(final String poolUuid) {

		if (StringUtils.isNotBlank(poolUuid)) {

			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append("SELECT * FROM cstm_customer c  WHERE c.pool_id in ( SELECT uuid FROM cstm_customer_pool p  WHERE p.uuid = ? );");
					params.add(poolUuid);
				}
			}, Customer.class);

			if (customers.size() > 0) {
				return customers;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruishengtech.rscc.crm.cstm.service.ICustomerPool#getCustomersByCustomer
	 * ()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getCustomersByCustomer() {

		List<Customer> customers = queryBean(new BeanHandler() {

			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * cstm_customer FROM WHERE uuid in ( SELECT customer_uuid FROM cstm_customer_pool ) ");
			}
		}, Customer.class);

		if (customers.size() > 0) {
			return customers;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruishengtech.rscc.crm.cstm.service.ICustomerPool#
	 * getCustomersByPoolAndOwner(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getCustomersByPoolAndOwner(final String owner) {

		if (StringUtils.isNotBlank(owner)) {

			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * cstm_customer FROM WHERE uuid in ( SELECT customer_uuid FROM cstm_customer_pool WHERE customer_owner = ?) ");
					params.add(owner);
				}
			}, Customer.class);

			if (customers.size() > 0) {
				return customers;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ruishengtech.rscc.crm.cstm.service.ICustomerPool#
	 * getCustomersByPoolAndOwner(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getCustomersByPoolAndOwner(final String poolUuid,
			final String owner) {

		if (StringUtils.isNotBlank(poolUuid) && StringUtils.isNotBlank(owner)) {

			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * cstm_customer FROM WHERE uuid in ( SELECT customer_uuid FROM cstm_customer_pool WHERE uuid = ? AND customer_owner = ? ) ");
					params.add(poolUuid);
					params.add(owner);
				}
			}, Customer.class);

			if (customers.size() > 0) {
				return customers;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService#queryPage(
	 * com.ruishengtech.rscc.crm.cstm.condition.CustomerCondition)
	 */
	@Override
	public PageResult<CustomerPool> queryPage(CustomerPoolCondition condition) {

		return super.queryPage(new CustomerPoolSolution(), condition,
				CustomerPool.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService#deletePoolByUUId
	 * (com.ruishengtech.framework.core.db.UUID)
	 */
	@Override
	public void deletePoolByUUId(UUID uuid) {

		super.deleteById(CustomerPool.class, uuid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService#getAllCustomers
	 * ()
	 */
	@Override
	public List<CustomerPool> getAllPools() {
	
		return super.getAll(CustomerPool.class);
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService#getCustomerPool(com.ruishengtech.framework.core.db.UUID)
	 */
	@Override
	public CustomerPool getCustomerPool(UUID uuid) {
		
		return super.getByUuid(CustomerPool.class, uuid);
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService#save(com.ruishengtech.rscc.crm.cstm.model.CustomerPool)
	 */
	@Override
	public void save(String[] departments,CustomerPool customerPool) {
	
		super.save(customerPool);
		bindDepartment(customerPool.getUid(), departments);
		
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService#update(com.ruishengtech.rscc.crm.cstm.model.CustomerPool)
	 */
	@Override
	public void update(String[] departments,CustomerPool customerPool) {
		super.update(customerPool);
		bindDepartment(customerPool.getUid(), departments);
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService#save(com.ruishengtech.rscc.crm.cstm.model.CustomerPool, java.lang.String[])
	 */
	@Override
	public void save(CustomerPool customerPool, String[] str) {

		super.save(customerPool, str);
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService#update(com.ruishengtech.rscc.crm.cstm.model.CustomerPool, java.lang.String[])
	 */
	@Override
	public void update(String[] departments,String[] undepartments,CustomerPool customerPool, String[] str) {

		super.update(customerPool, str);
		
		if(null != undepartments && undepartments.length > 0){
			
			//删除未勾选的
			unbindDepartment(customerPool.getUid(), undepartments);
		}
		if(null != departments){
			
			//添加勾选的
			bindDepartment(customerPool.getUid(), departments);
		}
		
	}
	
	
	/**
	 * 查询所有客户池
	 * @return
	 */
	public List<CustomerPool> getAllPool(){
		
		return super.getAll(CustomerPool.class);
	}

	
	/**
	 * 根据客户池名称信息查询客户池信息
	 * @param poolName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CustomerPool getPoolByName(final String poolName){
		
		List<CustomerPool> clazz = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT * FROM cstm_customer_pool WHERE pool_name = ? ;");
				params.add(poolName);
			}
		}, CustomerPool.class);
		
		if(clazz.size() > 0){
			return clazz.get(0);
		}
		return null;
	}
	
	
	/**
	 * 修改客户池中的客户
	 * @param fromPool
	 * @param toPool
	 * @return 
	 */
	public void CustomerPoolchangePoolUsers(String fromPool,String toPool){
		
		jdbcTemplate.update("UPDATE cstm_customer SET pool_id = ? WHERE pool_id = ? ;",toPool,fromPool);
		
		jdbcTemplate.update(" DELETE FROM cstm_customer_pool WHERE uuid = ? ",fromPool);
		
	}
	
	
	/**
	 * 统计报表当月的人数变化统计
	 * @param fromPool
	 * @param toPool
	 * @return 
	 */
	@SuppressWarnings({ "unchecked" })
	public List<CstmReport> getPoolDateReport(final String poolId){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		
		List<CstmReport> cstmReports = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {

				sql.append(" SELECT t.opt_date AS date  ,sum(opt_count) AS count FROM (SELECT * FROM cstm_log WHERE obj_pool =  ? AND opt_date >= ? "
						+ "AND opt_date <= ? "
						+ " ORDER BY opt_date DESC) t  GROUP BY substr(t.opt_date,1,10);");
				
				params.add(poolId);
				params.add(QueryUtils.getCurrentMonthSimple());
				params.add(QueryUtils.getCurrentMonthLastSimple());
			}
		}, CstmReport.class);
	
		for (int i = 0; i < cstmReports.size(); i++) {
			try {
				cstmReports.get(i).setDate(sdf.format(sdf1.parse(cstmReports.get(i).getDate())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return cstmReports;
	}
	
	/**
	 * 统计报表自定义的人数变化统计
	 * @param fromPool
	 * @param toPool
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<CstmReport> getPoolDateReportDate(String poolId,String dateStart,String dateEnd){
		
		return queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
			}
		}, CstmReport.class);
		
	}
	
	
	public List<CstmReport> getPoolReport(String poolId) {
		//统计报表数据
		List<CstmReport> cstmReports = getPoolDateReport(poolId);
		
		Comparator<CstmReport> comparator = new Comparator<CstmReport>() {
			@Override
			public int compare(CstmReport o1, CstmReport o2) {
				
				return o1.getDate().compareTo(o2.getDate());
			}
		};
		Collections.sort(cstmReports, comparator);

		Integer counts = 0;
		for (int i = 0; i < cstmReports.size(); i++) {
			counts +=Integer.valueOf(cstmReports.get(i).getCount());
			cstmReports.get(i).setAllCount(String.valueOf(counts));
		}
		
		return cstmReports;
	}

	
	/**
	 * 得到客户池的树形结构
	 * @return
	 */
	@Override
	public JSONArray getPoolsTree() {
		
		JSONArray ret = new JSONArray();
		JSONObject jsonRoot = new JSONObject();
		
        jsonRoot.put("id", "cstmPools");
        jsonRoot.put("pId", "root");
        jsonRoot.put("name", "客户池");
        jsonRoot.put("open", true);
        ret.put(jsonRoot);
        
        List<CustomerPool> pools = getAllPools();
		for (int i = 0; i < pools.size(); i++) {
			
			JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", pools.get(i).getUuid());
        	jsonObject.put("pId", "cstmPools");
        	jsonObject.put("name", pools.get(i).getPoolName());
        	jsonObject.put("open", true);
            ret.put(jsonObject);
		}
        
        
		return ret;
	}

	/**
	 * 取到指定客户的所有标签
	 * @param poolId
	 * @return
	 */
	@Override
	public Integer getAllTags(String poolId) {
		
		Integer list = jdbcTemplate.queryForObject("SELECT \n" +
				"	COUNT(*) AS count\n" +
				"FROM\n" +
				"	cstm_tag_link l,\n" +
				"	cstm_customer_tag g\n" +
				"WHERE\n" +
				"	g.uuid = l.tag_id\n" +
				"	and customer_uuid in (SELECT uuid FROM cstm_customer c WHERE c.pool_id = ? )",Integer.class,poolId);
		
		return list;
		
	}
	
	/**
	 * 保存中间表数据
	 * @param uuid
	 * @param departments
	 * @return
	 */
	public int bindDepartment(String uuid, String[] departments) {
		if(departments == null || departments.length == 0)
			return 0;
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for(String d:departments)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{uuid,d});
		int[] nums = jdbcTemplate.batchUpdate("REPLACE INTO cstm_pool_department_link (cstm_pool_uuid, department_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	/**
	 * 池关联的多个部门
	 * @param poolId
	 * @return
	 */
	public List<String> getDepartmentsByPool(String poolId) {
	
		return jdbcTemplate.queryForList("SELECT department_uuid FROM cstm_pool_department_link WHERE cstm_pool_uuid = ?", String.class, poolId);
	}
	
	
	/**
	 * 一个部门关联的所有池
	 * @param uuid
	 * @return
	 */
	public List<String> getDepartmentDataContainers(String uuid) {
		return jdbcTemplate.queryForList("SELECT cstm_pool_uuid FROM cstm_pool_department_link WHERE department_uuid = ?", String.class, uuid);
	}
	
	/**
	 * 多个部门编号关联的多个池  
	 * @param uuids
	 * @return
	 */
	public Set<String> getPoolsByDepartments(Collection<String> uuids) {
		Set<String> set = new HashSet<String>();
		for (String string : uuids) {
			set.addAll(getDepartmentDataContainers(string));
		}
		return set;
	}

	@Override
	public int unbindDepartment(String uuid, String[] departments) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for(String d:departments)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{uuid,d});
		int[] nums = jdbcTemplate.batchUpdate("DELETE from cstm_pool_department_link where cstm_pool_uuid = ? and department_uuid = ?", list);
		return CollectionUtil.sum(nums);
	}

	
	/**
	 * 查询当前用户创建的客户池
	 * @param arg0
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerPool> getPoolsByAgentId(final String arg0) {
		
		List<CustomerPool> list = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {

				sql.append("SELECT * FROM cstm_customer_pool WHERE creater = ?;");
				params.add(arg0);
				
			}
		}, CustomerPool.class);
		
		
		if(list.size() > 0){
			
			return list;
		}
		
		return null;
	}
	

	/**
	 * 检查客户名字重复
	 * @param request
	 * @param response
	 * @param poolName
	 * @param customerPool
	 * @throws IOException
	 */
	public void checkPoolNameRepeat(HttpServletRequest request,
			HttpServletResponse response, String poolName,
			CustomerPool customerPool) throws IOException {
		if(StringUtils.isNotBlank(poolName)){
			String uid = request.getParameter("uid");
			
			//根据号码查询客户信息
			if(null == customerPool){
				
				response.getWriter().print(true);
			}else{
				
				if ("".equals(uid)) {

					response.getWriter().print(false);
				} else {
					CustomerPool s = getByUuid(CustomerPool.class,UUID.UUIDFromString(uid));

					if (s != null && uid.equals(String.valueOf(customerPool.getUid()))) {
						
						response.getWriter().print(true);
					} else {

						response.getWriter().print(false);
					}
				}			
				
			}
			
		}else{
			
			response.getWriter().print(true);
		}
	}
	
	
	/**
	 * 查询默认客户池对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CustomerPool getDefaultPool() {
		
		List<CustomerPool> customerPools =  queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {

				sql.append("SELECT * FROM `cstm_customer_pool` WHERE be_default = 1 LIMIT 1 ");
			}
		}, CustomerPool.class);
		
		if(customerPools.size() > 0){
			
			return customerPools.get(0);
		}
		
		return null;
	}
	
	
	//获取所有意向类型和成交类型
	@SuppressWarnings("unchecked")
	public String attendCustomerTypeByUUID(Object uuid) {
		
		 if(StringUtils.isNotBlank(String.valueOf(uuid))){
			 
			 String attend = null ;
			 try {
				
				 attend = jdbcTemplate.queryForObject("SELECT intent_name AS intentName FROM `new_data_intent` WHERE uuid = ? LIMIT 1 OFFSET 0 ;", String.class,uuid);
				 
				 return attend;
			} catch (Exception e) {
				
				try {
					
					String poolName = jdbcTemplate.queryForObject("SELECT pool_name AS poolName FROM `cstm_customer_pool` WHERE uuid = ? LIMIT 1 OFFSET 0 ;", String.class,uuid);
					
					if(StringUtils.isNotBlank(poolName)){
						return poolName;
					}
					
				} catch (Exception e2) {
					
					return "未知";
				}
			}
		 }
		
		return "未知";
	}
	
	/**
	 * 获取所有意向
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataIntent> getAllIntends(){

		 List<DataIntent> dataintends = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT uuid , intent_name , intent_info , seq FROM `new_data_intent` ORDER BY seq ASC ");
			}
		}, DataIntent.class);

		 return dataintends;
	}

	@Override
	public void getSinglePoolsInfo(Model model, HttpServletRequest request,
			List<CustomerPool> customerPools, User user) {
		//获取普通用户可管辖客户池
		customerPools =customerService.getNormalUserPools(request, customerPools, user);

		if (customerPools.size() > 0) {

			CustomerPool customerPool = Collections.min(customerPools, new Comparator<CustomerPool>() {
				@Override
				public int compare(CustomerPool o1, CustomerPool o2) {
					return o1.getCreateTime().compareTo(
							o2.getCreateTime());
				}
			});

			/* 查询所有该客户池的所有操作日志 */
			List<CstmLog> cstmLogs = logService.getCstmLogsByUUID(customerPool.getUid());
			model.addAttribute("logs", cstmLogs);

			model.addAttribute("pools", customerPools);
			model.addAttribute("poolUUID", customerPool.getUid());

			JSONArray array = new JSONArray(getPoolReport(customerPool.getUid()));

			model.addAttribute("reportDate", array);

			// 得到本客户池中标签个数前10的标签
			List<CstmReport> list = customerService.getTopNumberTags(customerPool.getUid(), "5");

			model.addAttribute("counts",getAllTags(customerPool.getUid()));

			List<String> list2 = customerService.getAllTags();

			model.addAttribute("tagSize", list2);
			model.addAttribute("tagList", list);

		}
	}

	
}

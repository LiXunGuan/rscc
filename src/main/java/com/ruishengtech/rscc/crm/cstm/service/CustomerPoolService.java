package com.ruishengtech.rscc.crm.cstm.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerPoolCondition;
import com.ruishengtech.rscc.crm.cstm.model.CstmReport;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.user.model.User;

public interface CustomerPoolService {

	/**
	 * 查询指定客户池的所有客户
	 * 
	 * @param poolUuid
	 *            客户池编号
	 * @return 客户列表
	 */
	public abstract List<Customer> getCustomersByPool(String poolUuid);

	/**
	 * 查询池中所有客户
	 * @return
	 */
	public abstract List<Customer> getCustomersByCustomer();

	/**
	 * 所有池中的客户归属
	 * 
	 * @param owner
	 *            指派人
	 * @return 客户编号
	 */
	public abstract List<Customer> getCustomersByPoolAndOwner(String owner);

	/**
	 * 客户归属
	 * 
	 * @param poolUuid
	 *            客户池编号
	 * @param owner
	 *            指派人
	 * @return 客户集合
	 */
	public abstract List<Customer> getCustomersByPoolAndOwner(String poolUuid,
                                                              String owner);
	
	
	/**
	 * 查询客户池中所有客户
	 * @param condition
	 * @return
	 */
	public abstract PageResult<CustomerPool> queryPage(CustomerPoolCondition condition);
	
	
	/**
	 * 根据uuid删除客户
	 * @param uuid
	 */
	public abstract void deletePoolByUUId(UUID uuid);
	
	
	/**
	 * 获取所有客户池对象
	 * @return
	 */
	public abstract List<CustomerPool> getAllPools();
	

	/**
	 * 从缓存中获取对象
	 * @return
	 */
	public Map<String, CustomerPool> getAllCustomerPools();
	
	/**
	 * 数据库中获取所有池对象
	 */
	public void loadCustomerPools();
	
	/**
	 * 查询单个
	 * @param uuid
	 * @return
	 */
	public CustomerPool getCustomerPool(UUID uuid);
	

	/**
	 * 保存
	 * @param customerPool
	 */
	public void save(String[] departments,CustomerPool customerPool);
	
	
	/**
	 * @param customerPool
	 * @param str
	 */
	public void save(CustomerPool customerPool, String str[]);
	
	/**
	 * 修改
	 * @param departments 
	 * @param customerPool
	 */
	public void update(String[] departments, CustomerPool customerPool);
	
	/**修改
	 * @param customerPool
	 */
	public void update(String[] departments,String undepartments[],CustomerPool customerPool, String str[]);
	
	/**
	 * 查询所有客户池
	 * @return
	 */
	public List<CustomerPool> getAllPool();
	
	/**
	 * 根据客户池名称信息查询客户池信息
	 * @param poolName
	 * @return
	 */
	public CustomerPool getPoolByName(String poolName);
	
 
	/**
	 * 修改客户池中的客户
	 * @param fromPool
	 * @param toPool
	 * @return 
	 */
	public void CustomerPoolchangePoolUsers(String fromPool, String toPool);
	
	
	/**
	 * 统计报表当月的人数变化统计
	 * @param fromPool
	 * @param toPool
	 * @return 
	 */
	public List<CstmReport> getPoolDateReport(String poolId);
	
	/**
	 * 统计报表自定义的人数变化统计
	 * @param fromPool
	 * @param toPool
	 * @return 
	 */
	public List<CstmReport> getPoolDateReportDate(String poolId, String dateStart, String dateEnd);
	
	
	/**
	 * 获取统计数据
	 * @param poolId
	 * @param model
	 * @return
	 */
	public List<CstmReport> getPoolReport(String poolId);
	
	/**
	 * 得到客户池的树形结构
	 * @return
	 */
	public JSONArray getPoolsTree();
	

	/**
	 * 取到指定客户的所有标签
	 * @param poolId
	 * @return
	 */
	public Integer getAllTags(String poolId);
	
	public int bindDepartment(String uuid, String[] departments);
	
	/**
	 * 池关联的多个部门
	 * @param poolId
	 * @return
	 */
	public List<String> getDepartmentsByPool(String poolId);
	
	public int unbindDepartment(String uuid, String[] departments);
	
	public Set<String> getPoolsByDepartments(Collection<String> uuids);
	
	public List<String> getDepartmentDataContainers(String uuid) ;
	
	
	/**
	 * 
	 * 查询当前用户创建的客户池
	 * @param arg0
	 * @return
	 */
	public List<CustomerPool> getPoolsByAgentId(String arg0);
	
	
	/**
	 * 检查客户名字重复
	 * @param request
	 * @param response
	 * @param poolName
	 * @param customerPool
	 * @throws IOException
	 */
	public void checkPoolNameRepeat(HttpServletRequest request, HttpServletResponse response, String poolName, CustomerPool customerPool) throws IOException;
	
	/**
	 * 查询默认客户池对象
	 * @return
	 */
	public CustomerPool getDefaultPool();
	
	
	/**
	 * 顺序查询所有意向和成交客户类型
	 * @return
	 */
	public String attendCustomerTypeByUUID(Object uuid);
	
	/**
	 * 获取所有意向
	 * @return
	 */
	public List<DataIntent> getAllIntends();

	/**
	 * 获取单个客户池的信息
	 * @param model
	 * @param request
	 * @param customerPools
	 * @param user
	 */
	public abstract void getSinglePoolsInfo(Model model,
			HttpServletRequest request, List<CustomerPool> customerPools,
			User user);
	
}
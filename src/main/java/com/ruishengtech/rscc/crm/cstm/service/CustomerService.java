package com.ruishengtech.rscc.crm.cstm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.rscc.crm.cstm.model.CstmReport;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.user.model.User;

public interface CustomerService {

	/**
	 * 根据客户名字查询客户信息
	 * 
	 * @param csmtName
	 *            客户名字
	 * @return 客户对象
	 */
	public abstract Customer getCustomerByName(String csmtName);
	
	
	/**
	 * 获取该权限下的所有客户池
	 * @param request
	 * @param customerPools
	 * @param user
	 * @return
	 */
	public List<CustomerPool> getPermissionPools(HttpServletRequest request,
			List<CustomerPool> customerPools, User user);
	
	/**
	 * 去到所有title
	 * @param model
	 */
	public void getTitles(Model model);

	/**
	 * 根据客户池编号查询客户
	 * 
	 * @param poolId
	 *            客户池编号
	 * @return
	 */
	public abstract List<Customer> getCustomersByPoolId(String poolId);

	/**
	 * 根据客户池编号查询客户
	 * 
	 * @param str
	 *            [] 客户池编号数组
	 * @return
	 */
	public abstract List<Customer> getCustomersByPoolId(String[] poolId);

	/**
	 * 根据客户池编号查询客户
	 * 
	 * @param poolId
	 *            客户池编号list
	 * @return
	 */
	public abstract List<Customer> getCustomersByPoolId(List<String> poolId);

	/**
	 * 查符合条件的所有客户
	 * 
	 * @param condition
	 * @return
	 */
	public PageResult<Map> queryPage(Map<String, ColumnDesign> str, HttpServletRequest request);

	/**
	 * 根据UUID删除客户对象
	 * 
	 * @param uuid
	 * @param customer 
	 */
	public abstract void deleteCustomerByUUid(UUID uuid, Customer customer);

	/**
	 * 保存或者是修改一个客户
	 * 
	 * @param customer
	 */
	public abstract void saveOrUpdate(Customer customer);

	/**
	 * 保存或者是修改一个客户
	 * 
	 * @param customer
	 */
	public abstract void saveOrUpdate(Customer customer, String[] str);

	/**
	 * 根据编号去到客户信息
	 * 
	 * @param uuid
	 * @return
	 */
	public abstract Map<String, Object> getCustomerById(UUID uuid);
	
	/**
	 * 查询客户表的所有字段
	 * @return
	 */
	public abstract List<String> getCustomerColumns();
	
	
	/**
	 * 得到表头和对应数据字段
	 * @return
	 */
	public abstract JSONObject getTitleAndData(DiyTableService diyTableManager);

	/**
	 * 取值
	 * @param str
	 * @return
	 */
	public JSONObject getJsonObject(Map str);
	
	/**
	 * 获取所有titles
	 */
	public List<String> getTitles();
	
	/**
	 * 校验顺序重复
	 * @param request
	 * @param response
	 * @param str
	 * @throws java.io.IOException
	 */
	public void checkCstmIdRepeat(HttpServletRequest request, HttpServletResponse response, String str) throws IOException;

	/**
	 * 查询指定客户编号的uuid
	 * @param serilizeId
	 * @return
	 */
	public abstract String getCstmUidByCstmSerilizeId(String serilizeId);
	
	/**
	 * 根据UUID查找客户固定信息
	 * @param uuid
	 * @return
	 */
	public abstract Customer getCustomerByUUID(String uuid);
	
	/**
	 * 修改客户表主号码
	 * @param uuid
	 */
	public abstract void getChangeNumber(String uuid, String phone);
	
	/**
	 * 查询所有
	 * @param condition
	 * @return
	 */
	public List<String> getAll(HttpServletRequest request);
	
	/**
	 * 批量删除客户信息
	 * @param customerIds
	 * @return
	 */
	public Integer batchDelete(String[] customerIds);

	/**
	 * 
	 * 批量移动客户到指定客户池
	 * @param customerId
	 * @param uid
	 * @return
	 */
	public abstract Integer batchMove(HttpServletRequest request,String[] customerId, String uid);
	
	/**
	 * 根据phone查询客户信息
	 * @param phone
	 * @return
	 */
	public abstract Customer getCustomerInfoByPhone(String phone);
	
	/**
	 * 根据客户号码拿客户信息
	 * @param phone
	 * @return
	 */
	public abstract Map<String, Object> getCustomerMapByPhone(String phone);
	
	/**
	 * 获取所有号码 主号码 子号码
	 * @return
	 */
	public abstract List<String> getAllPhones();
	
	/**
	 * 根据编号或者号码查询
	 * @param param
	 * @return
	 */
	public abstract Customer getCustomerByPhoneOrNumber(String param);
	
	/**
	 * 得到试用频率次数前几的人数
	 * @param num
	 * @return
	 */
	public abstract List<CstmReport> getTopNumberTags(String poolId, String num);
	
	/**
	 *获取所有标签
	 * @return
	 */
	public abstract List<String> getAllTags();

	/**
	 * 根据坐席编号查询该坐席的所有客户
	 * @param agnetNum
	 * @return
	 */
	public abstract List<Customer> getOwnerByAgent(String agnetNum);
	
	
	/**
	 * 检查号码是否重复
	 * @param request
	 * @param response
	 * @param phoneNumber
	 * @throws IOException 
	 */
	public void checkPhoneRepeat(HttpServletRequest request,
			HttpServletResponse response, String phoneNumber) throws IOException;


	/**
	 * 保存第二个号码
	 * @param request
	 * @param cstm_uuid
	 * @param cstmUuid
	 */
	public void saveSecondNumber(HttpServletRequest request, String cstm_uuid,String cstmUuid);
	
	
	/**
	 * 根据号码或者是编号查询多个数据
	 * @param agrs
	 * @return
	 */
	public List<Map<String, Object>> getCustomersInfoByIdOrPhone(String agrs);
	
	
	/**
	 * 清空归属人
	 * @param phone 电话号码
	 * @return 受影响的行数
	 */
	public Integer emptyOwnerByPhone(String phone);

	Map<String, Object> getMapObject(Map str);
	
	
	/**
	 * 修改意向客户为成交客户
	 * 传递号码 拥有者
	 * @param phoneNumber
	 * @param ownerId
	 * @return
	 */
	public JSONObject updateIntendToCustomer(String phoneNumber , String ownerId);

	/**
	 * 批量修改拥有者
	 * @param toUpdate
	 * @param beUpdate
	 * @return
	 */
	public Integer batchUpdateCustomerOwner(String[] toUpdate , String beUpdate);
	
	
	/**
	 * 根据部分信息保存客户信息
	 * @param cstmName
	 * @param cstmType
	 * @param owner
	 * @param phone
	 * @param phone1
	 * @param status
	 * @return
	 */
	public JSONObject saveCustomerInfo(String cstmName , String owner , String phone , String phone1 , String status , String type);

	/**
	 * 批量移动到指定客户下
	 * @param customerId
	 * @param ownerId
	 * @return 
	 */
	abstract Integer batchUpdateToUser(String[] customerId, String ownerId);


	/**
	 * 显示的所有列
	 * @param model
	 */
	abstract void getShowTitle(Model model);


	/**
	 * 取到客户信息，将客户信息值保存 放置页面
	 * @param uuid
	 * @param phone
	 * @param model
	 * @param customer
	 */
	abstract void getCustomerInfoAndSetPageValue(UUID uuid,
			String phone, Model model, Map<String, Object> customer);


	/** 
	 * 获取普通用户可查询的池
	 * @param request
	 * @param customerPools
	 * @param user
	 * @return
	 */
	abstract List<CustomerPool> getNormalUserPools(
			HttpServletRequest request, List<CustomerPool> customerPools,
			User user);


	/**
	 * 改变主号码
	 * @param phone
	 * @param uid
	 * @param number
	 * @param pId
	 */
	abstract void changeMainNumber(String phone, String uid,
			String[] number, String pId);


	/**
	 * 根据主号码查询第二号码
	 * @param arg0
	 * @return 
	 */
	public abstract String selectSecondNumber(String arg0);


	/**
	 * 根据编号和号码验证是否是成交客户
	 * @param phone
	 * @param customerId
	 * @return 
	 */
	public abstract String beCustomer(String phone, String customerId);


}